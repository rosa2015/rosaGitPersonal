package com.zhtx.goodsapi.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @author zhaohailong
 * @date 20160414
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private Map<Object, Object> _targetDataSources;

	@Override
	protected Object determineCurrentLookupKey() {
		return DBContextHolder.getDbType();
	}

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this._targetDataSources = targetDataSources;
		super.setTargetDataSources(this._targetDataSources);
		afterPropertiesSet();
	}

	public void removeTargetDataSource(String key) {
		try {
			if (this._targetDataSources.containsKey(key)) {
				Object dataSource = this._targetDataSources.get(key);
				if (dataSource instanceof BasicDataSource) {
					((BasicDataSource) dataSource).close();
				}
				this._targetDataSources.remove(key);
				this.setTargetDataSources(this._targetDataSources);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addTargetDataSource(String key, DataSource dataSource) {
		if (this._targetDataSources.containsKey(key)) {
			System.out.println("DynamicDataSource-addTargetDataSource已经存在key="
					+ key + "的数据源！");
			return;
		}
		try {
			Connection con =dataSource.getConnection();
			con.close();
		} catch (Exception e) {
			throw new RuntimeException("数据库连接异常:" , e);
		}
		this._targetDataSources.put(key, dataSource);
		this.setTargetDataSources(this._targetDataSources);
	}

	public void addTargetDataSource(String key, String driverClassName,
			String url, String username, String password) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		// dataSource.setInitialSize(0);
		// dataSource.setMaxActive(1);
		// dataSource.setTestOnBorrow(false);
		// dataSource.setTestOnReturn(true);
		addTargetDataSource(key, dataSource);
	}
}