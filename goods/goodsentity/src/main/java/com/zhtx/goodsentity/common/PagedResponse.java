package com.zhtx.goodsentity.common;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
@ApiModel
public class PagedResponse<T>  extends ResponseBase {
	@ApiModelProperty(value = "总记录数", notes = "",required=true)
	private long totalRecord;
	@ApiModelProperty(value = "总页数", notes = "",required=true)
	private int totalPage;
	@ApiModelProperty(value = "当前页码", notes = "",required=true)
	private int pageIndex;
	@ApiModelProperty(value = "每页条数", notes = "",required=true)
	private int pageSize;
	@ApiModelProperty(value = "结果集", notes = "",required=true)
	private List<T> resultList;
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
