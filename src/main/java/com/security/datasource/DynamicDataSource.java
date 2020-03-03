package com.security.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.security.enums.DataSourceType;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource{

	private static final ThreadLocal<DataSourceType> contentHolder = new ThreadLocal<DataSourceType>();

	public DynamicDataSource(DataSource defaultDataSouce,Map<Object,Object> targetMap) {
		super.setDefaultTargetDataSource(defaultDataSouce);
		super.setTargetDataSources(targetMap);
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceType dbType = contentHolder.get();
		if(contentHolder.get()==null) {
			log.info("当前数据源是:[{}]",DataSourceType.masterDatasource);
		}
		return dbType;
	}

	public static void setDbType(DataSourceType dataSourceType) {
		log.info("所使用的数据源为:[{}]",dataSourceType);
		contentHolder.set(dataSourceType);
	}

	public static DataSourceType getDbType() {
		return contentHolder.get();
	}

	public static void removeDbType() {
		contentHolder.remove();
	}


}
