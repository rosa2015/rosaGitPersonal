package com.zhtx.goodsentity.common;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.zhtx.goodsentity.enums.HttpReturnRnums;

@ApiModel
public class HttpResultModel<T> {
	@ApiModelProperty(value = "状态码", notes = "1是成功,其他都是失败")
	private int code = HttpReturnRnums.Success.value();
	@ApiModelProperty(value = "状态码描述信息", notes = "例如失败，参数错误")
	private String msg = HttpReturnRnums.Success.desc();
	@ApiModelProperty(value = "具体的业务数据", notes = "")
	private T data;

	public int getCode() {
		return code;
	}

	public HttpResultModel<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public HttpResultModel<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public HttpResultModel<T> setData(T data) {
		this.data = data;
		return this;
	}

}
