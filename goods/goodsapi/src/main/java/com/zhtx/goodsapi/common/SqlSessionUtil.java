package com.zhtx.goodsapi.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhtx.goodsentity.domain.ConnectionInfo;



@Component
public class SqlSessionUtil {

	@Autowired
	private SqlSessionFactory factory;
	@Autowired
	private DataSourceUtil dataSourceUtil;
	private String getPrefix() {
		return "com.zhtx.goodsapi.dao.";
	}

	public SqlSessionUtil setCustomerDataSource(ConnectionInfo conInfo) {
		dataSourceUtil.addCustomerDataSource(conInfo);
		String key = DataSourceUtil.getKey(conInfo);
		DBContextHolder.setDbType(key);
		return this;
	}

	private void clear(SqlSession innerSession) {
		innerSession.close();
		DBContextHolder.clearDBType();
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		SqlSession innerSession=factory.openSession();
		try {
			return innerSession.selectList(getPrefix() + statement,
					parameter);
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public <E> List<E> selectList(String statement) {
		SqlSession innerSession=factory.openSession();
		try {
			return innerSession.selectList(getPrefix() + statement);
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param statement
	 * @param parameter
	 * @author CaoHeYang
	 * @Date 20150729
	 * @return
	 */
	// public <E> PagedResponse<E> selectPageList(String statement, Object
	// parameter) {
	// try {
	// if (parameter==null) {
	// throw new RuntimeException("request不能为null");
	// }
	// if (!(parameter instanceof PagedRequestBase)) {
	// throw new RuntimeException("分页请求必须继承于PagedRequestBase");
	// }
	// PagedRequestBase basemodel= (PagedRequestBase)parameter;
	// if (basemodel.getCurrentPage()==0) {
	// basemodel.setCurrentPage(1); //默认第一页
	// }
	// if(basemodel.getPageSize()==0){
	// basemodel.setPageSize(15); //默认页容量
	// }
	// PagedResponse<E> result=new PagedResponse<E>();
	// result.setResultList(innerSession.selectList(getPrefix()+statement,
	// parameter));
	// result.setCurrentPage(basemodel.getCurrentPage());
	// result.setPageSize(basemodel.getPageSize());
	// result.setTotalPage(basemodel.getTotalPage());
	// result.setTotalRecord(basemodel.getTotalRecord());
	// return result;
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// innerSession.close();
	// }
	// }

	public <T> T selectOne(String statement, Object parameter) {
		SqlSession innerSession=factory.openSession();
		try {
			return innerSession.selectOne(getPrefix() + statement,
					parameter);
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public <T> T selectOne(String statement) {
		SqlSession innerSession=factory.openSession();
		try {
			return innerSession.selectOne(getPrefix() + statement);
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public int delete(String statement, Object parameter) {
		SqlSession innerSession=factory.openSession();
		try {
			int result = innerSession.delete(getPrefix() + statement,
					parameter);
			innerSession.commit();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public int delete(String statement) {
		SqlSession innerSession=factory.openSession();
		try {
			int result = innerSession.delete(getPrefix() + statement);
			innerSession.commit();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public int update(String statement, Object parameter) {
		SqlSession innerSession=factory.openSession();
		try {
			int result = innerSession.update(getPrefix() + statement,
					parameter);
			innerSession.commit();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public int update(String statement) {
		SqlSession innerSession=factory.openSession();
		try {
			int result = innerSession.update(getPrefix() + statement);
			innerSession.commit();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public int insert(String statement, Object parameter) {
		SqlSession innerSession=factory.openSession();
		try {
			int result = innerSession.insert(getPrefix() + statement,
					parameter);
			innerSession.commit();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	public int insert(String statement) {
		SqlSession innerSession=factory.openSession();
		try {
			int result = innerSession.insert(getPrefix() + statement);
			innerSession.commit();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}

	/**
	 * 在指定的db中执行动态查询sql
	 * 
	 * @param conInfo
	 * @author hailongzhao
	 * @date 20151201
	 * @param sql
	 * @return
	 */
	public List<Map<String, String>> dynamicSelectList(String sql) {
		SqlSession innerSession=factory.openSession();
		try {
			return innerSession.selectList(
					getPrefix() + "ICustomerSqlDao.select", sql);
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}
	/**
	 * 在指定的db中执行动态查询sql
	 * 
	 * @param conInfo
	 * @author hailongzhao
	 * @date 20151201
	 * @param sql
	 * @return
	 */
	public int dynamicUpdate(String sql) {
		SqlSession innerSession=factory.openSession();
		try {
			return innerSession.update(
					getPrefix() + "ICustomerSqlDao.update", sql);
		} catch (Exception e) {
			throw e;
		} finally {
			clear(innerSession);
		}
	}
}
