package com.zhtx.goodscore.util;

import java.util.UUID;

/**
 * 单号生成帮助类
 * 
 * @author CaoHeYang
 * @Date 20150818
 */
public class NumberHelper {

	public static String getcode() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
