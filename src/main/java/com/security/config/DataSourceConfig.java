package com.security.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.security.datasource.DynamicDataSource;

@Configuration
public class DataSourceConfig {
	
	@Autowired
	private Environment environment;

	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	@Primary
	public DataSource masterDataSource() throws SQLException {
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		dataSource.setName("masterDataSource");
		dataSource.setInitialSize(1);
		dataSource.setMinIdle(1);
		dataSource.setMaxActive(20);
		dataSource.setMaxWait(60000);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setPoolPreparedStatements(false);
		dataSource.setRemoveAbandonedTimeoutMillis(180000);
		dataSource.setRemoveAbandoned(true);
		dataSource.setLogAbandoned(true);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		dataSource.setFilters("stat,wall,log4j");
		return dataSource;
	}
	
	@Bean(name="slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() throws SQLException {
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		dataSource.setName("slaveDataSource");
		dataSource.setFilters("stat,wall,log4j");
		dataSource.setInitialSize(1);
		dataSource.setMinIdle(1);
		dataSource.setMaxActive(20);
		dataSource.setMaxWait(60000);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setRemoveAbandonedTimeoutMillis(180000);
		dataSource.setRemoveAbandoned(true);
		dataSource.setLogAbandoned(true);
		dataSource.setPoolPreparedStatements(false);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		return dataSource;
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration gloConfiguration(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	public DynamicDataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,@Qualifier("slaveDataSource") DataSource slaveDataSource) {
		Map<Object,Object> targetMap = new HashMap<Object,Object>();
		targetMap.put("master", masterDataSource);
		targetMap.put("slave", slaveDataSource);
		return new DynamicDataSource(masterDataSource, targetMap);
	}
	
	@Bean
	public PlatformTransactionManager txManager(DynamicDataSource dynamicDataSource) {
		return new DataSourceTransactionManager(dynamicDataSource);
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DynamicDataSource dyDataSource,org.apache.ibatis.session.Configuration configuration) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dyDataSource);
		sqlSessionFactoryBean.setConfiguration(configuration);
		sqlSessionFactoryBean.setTypeAliasesPackage(environment.getProperty("mybatis.type-aliases-package"));// 指定基包
		sqlSessionFactoryBean.setMapperLocations(
	            new PathMatchingResourcePatternResolver().getResources(environment.getProperty("mybatis.mapper-locations")));//
		 //为MyBatis增加Plugin拦截器功能
        //sqlSessionFactoryBean.setPlugins(arrayOf(DynamicDataSourceInterceptor()))
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
	}
	
	
}