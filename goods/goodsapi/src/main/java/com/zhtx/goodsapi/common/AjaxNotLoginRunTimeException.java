package com.zhtx.goodsapi.common;

public class AjaxNotLoginRunTimeException extends RuntimeException{
	public AjaxNotLoginRunTimeException(String msg) {
		super(msg);
	}
	public AjaxNotLoginRunTimeException() {
		super();
	}
}
