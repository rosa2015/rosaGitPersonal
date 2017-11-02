package com.zhtx.goodsentity.consts;


/**
 * redis中的key
 * @author zhaohailong
 * @date 20160413
 */
public class RedissCacheKey {
    public static final String Menu_Auth = "zb:login:%s";//用户有权限的菜单
    public static final String Menu_Auth_All ="zb:login:all";//所有的菜单数据的redis的key
	public static final String HttpQuartz_Key = "HttpQuartz_Key_";// HttpQuartz,redis锁,防止服务重复执行
	
}
