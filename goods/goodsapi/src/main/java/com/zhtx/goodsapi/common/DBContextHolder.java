package com.zhtx.goodsapi.common;

/**
 * Created by LianJiePan on 2016/4/8.
 */
public class DBContextHolder {
    /**
     * 线程threadlocal
     */
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static String getDbType() {
    	return contextHolder.get();
    }

    /**
     *
     * 设置本线程的dbtype
     * @param dbtype
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void setDbType(String dbtype) {
        contextHolder.set(dbtype);
    }

    /**
     * clearDBType
     *
     * @Title: clearDBType
     * @Description: 清理连接类型
     */
    public static void clearDBType() {
        contextHolder.remove();
    }
}