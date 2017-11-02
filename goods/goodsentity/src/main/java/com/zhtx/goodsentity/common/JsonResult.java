package com.zhtx.goodsentity.common;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.zhtx.goodscore.util.JsonUtil;
import com.zhtx.goodsentity.enums.HttpReturnRnums;

/**
 * 默认code为成功
 * @author zhaohailong
 *
 */
@ApiModel
public class JsonResult  {
    /**
     * 默认code为成功
     */
    public JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    /**
     * 默认code为成功
     */
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * 默认code为成功
     */
    public JsonResult(Object data) {
        this.data = data;
    }
    /**
     * 默认code为成功
     */
    public JsonResult() {
    }
	@ApiModelProperty(value = "状态码", notes = "1是成功,其他都是失败")
    private int code=HttpReturnRnums.Success.value();
	@ApiModelProperty(value = "状态码描述信息", notes = "例如失败，参数错误")
    private String msg= HttpReturnRnums.Success.desc();
	@ApiModelProperty(value = "具体的业务数据", notes = "")
	private Object data;
    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

    public String toJsonString() {
        String str = JsonUtil.obj2string(this);
        System.out.println(str);
        return str;
    }
}
