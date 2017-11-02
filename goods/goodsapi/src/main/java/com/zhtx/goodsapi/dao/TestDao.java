package com.zhtx.goodsapi.dao;

import com.zhtx.goodsapi.common.DBContextDbType;
import com.zhtx.goodsapi.common.DataSourceType; 

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
 

/**
 * Created by LianJiePan on 2016/4/8.
 */


@DataSourceType(DBContextDbType.Master)
@Repository
public interface TestDao {

    @Select("select v from test where id=1")
    String getid(); 
}
