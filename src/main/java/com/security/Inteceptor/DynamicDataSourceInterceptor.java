package com.security.Inteceptor;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.security.datasource.DynamicDataSource;
import com.security.enums.DataSourceType;

import lombok.extern.slf4j.Slf4j;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }), @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
@Component
@Slf4j
public class DynamicDataSourceInterceptor implements Interceptor {

	private static final String REGEX = ".*insert\\\\u0020.*|.*delete\\\\u0020.*|.*update\\\\u0020.*";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 判断当前是否被事务管理
		boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
		DataSourceType lockupKey = DataSourceType.masterDatasource;
		if (!actualTransactionActive) {
			// 如果是非事务的，则再判断是读或者写。
			// 获取SQL中的参数
			Object[] args = invocation.getArgs();
			MappedStatement mappedStatement = (MappedStatement) args[0];
			// 如果为读，且为自增id查询主键，则使用主库
			// 这种判断主要用于插入时返回ID的操作，由于日志同步到从库有延时
			// 所以如果插入时需要返回id，则不适用于到从库查询数据，有可能查询不到
			if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT) && mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
				lockupKey = DataSourceType.masterDatasource;
			} else {
				BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(args[1]);
				String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");

				if (sql.matches(REGEX)) {
					// 如果是写
					lockupKey = DataSourceType.masterDatasource;
				} else {
					lockupKey = DataSourceType.slaveDataSource;
				}
			}
		} else {
			// 如果是通过事务管理的，一般都是写语句,直接通过主库
			lockupKey = DataSourceType.masterDatasource;
		}
		log.info("在" + lockupKey + "中进行操作");
		DynamicDataSource.setDbType(lockupKey);
		// 最后直接执行SQL
		return invocation.proceed();
	}

	/**
	 * 返回封装好的对象或代理对象
	 */
	@Override
	public Object plugin(Object target) {
		// 如果存在增删改查，则直接拦截下来，否则直接返回
        if (target instanceof Executor) {
        	 return Plugin.wrap(target, this);
        }else {
        	return target;
        }       
	}

	/**
     * 类初始化的时候做一些相关的设置
     */
	@Override
	public void setProperties(Properties properties) {

	}

}
