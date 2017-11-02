package com.zhtx.goodsapi.common;

import com.github.pagehelper.Page;
import com.zhtx.goodsentity.common.PagedResponse;
@SuppressWarnings({"rawtypes", "unchecked"})
public class PageConverter {
public static PagedResponse convert2PagedResponse(Page page) {
	if (page==null) {
		return null;
	}
	PagedResponse result=new PagedResponse();
	result.setPageIndex(page.getPageNum());
	result.setPageSize(page.getPageSize());
	result.setTotalPage(page.getPages());
	result.setTotalRecord(page.getTotal());
	result.setResultList(page.getResult());
	return result;
}
public static Page convert2Page(PagedResponse page) {
	if (page==null) {
		return null;
	}
	Page result=new Page();
	result.addAll(page.getResultList());
	result.setPageNum(page.getPageIndex());
	result.setPageSize(page.getPageSize());
	result.setTotal(page.getTotalRecord());
	return result;
}
}
