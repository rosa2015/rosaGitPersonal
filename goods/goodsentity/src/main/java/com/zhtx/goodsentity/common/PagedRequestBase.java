package com.zhtx.goodsentity.common;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
public class PagedRequestBase extends RequestBase {
	@ApiModelProperty(value = "页码", notes = "从1开始")
	private int page_index;
	@ApiModelProperty(value = "每页条数", notes = "")
	private int page_size;
	

	public int getPage_index() {
		if (page_index <= 0) {
			return 1;
		}
		return page_index;
	}

	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	public int getPage_size() {
		if (page_size <= 0) {
			return 15;
		}
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

}
