package com.zhtx.goodsapi.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class DaoBase {
	@Autowired
	private SqlSessionFactory readOnlyMySqlSessionFactory;

	@Autowired
	private SqlSessionFactory mySqlSessionFactory;
	
//	public SqlSessionUtil getReadOnlySqlSessionUtil() {
//		return SqlSessionUtil.wapperSession(readOnlyMySqlSessionFactory);
//	}
//	public SqlSessionUtil getMasterSqlSessionUtil() {
//		return SqlSessionUtil.wapperSession(mySqlSessionFactory);
//	}
}
