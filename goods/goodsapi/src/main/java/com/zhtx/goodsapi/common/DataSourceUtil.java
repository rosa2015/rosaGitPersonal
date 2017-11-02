package com.zhtx.goodsapi.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhtx.goodsentity.domain.ConnectionInfo;
import com.zhtx.goodsentity.enums.ServerType;


/**
 * 根据配置更新动态数据源
 * @author hailongzhao
 * @date 20151130
 *
 */
@Component
public class DataSourceUtil {
	@Autowired
	private DynamicDataSource dynamicDataSource;
	public  void addCustomerDataSource(ConnectionInfo conInfo) {
		if (conInfo.getConfigtype()==ServerType.MySql.value()) {
			 addMySqlCustomerDataSource(conInfo);
		}else {
			 addSqlServerCustomerDataSource(conInfo);
		}
	}
	public static String getKey(ConnectionInfo conInfo){
		String key=String.format("%s_%s_%s_%s_%s_%s", conInfo.getConfigtype(),
														conInfo.getHost(),
														conInfo.getPort(),
														conInfo.getDataBase(),
														conInfo.getUserName(),
														conInfo.getPassWord());
		return key;
	}
	/**
	 * 根据配置创建sqlserver连接工厂
	 * @param conInfo
	 * @date 20151130
	 * @return
	 */
	private  void addSqlServerCustomerDataSource(ConnectionInfo conInfo) {
		try {
			String sqlserver = "jdbc:sqlserver://%s:%s;DatabaseName=%s;";
			String url= String.format(sqlserver,conInfo.getHost(),conInfo.getPort(),conInfo.getDataBase());
			String driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
//			BasicDataSource newDataSource=new BasicDataSource();
//			newDataSource.setDriverClassName(driverClassName);
//			newDataSource.setUrl(url);
//			newDataSource.setUsername(conInfo.getUserName());
//			newDataSource.setPassword(conInfo.getPassWord());
			//DynamicDataSource  dynamicDataSource =(DynamicDataSource )SpringBeanHelper.getCustomBean("dynamicDataSource");
			dynamicDataSource.addTargetDataSource(getKey(conInfo), driverClassName,url,conInfo.getUserName(),conInfo.getPassWord());
		} catch (Exception e) {
			throw new RuntimeException("动态给dynamicDataSource添加SQLSERVER数据源时出错:" ,e);
		}
	}
	private  void addMySqlCustomerDataSource(ConnectionInfo conInfo) {
		try {
			String mysql = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&connectTimeout=60000&socketTimeout=60000&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
			String url= String.format(mysql,conInfo.getHost(),conInfo.getPort(),conInfo.getDataBase());
			String driverClassName="com.mysql.jdbc.Driver";
//			BasicDataSource newDataSource=new BasicDataSource();
//			newDataSource.setDriverClassName(driverClassName);
//			newDataSource.setUrl(url);
//			newDataSource.setUsername(conInfo.getUserName());
//			newDataSource.setPassword(conInfo.getPassWord());
			//DynamicDataSource  dynamicDataSource =(DynamicDataSource )SpringBeanHelper.getCustomBean("dynamicDataSource");
			dynamicDataSource.addTargetDataSource(getKey(conInfo), driverClassName,url,conInfo.getUserName(),conInfo.getPassWord());
		} catch (Exception e) {
			throw new RuntimeException("动态给dynamicDataSource添加MYSQL数据源时出错:" ,e);
		}
	}
}
