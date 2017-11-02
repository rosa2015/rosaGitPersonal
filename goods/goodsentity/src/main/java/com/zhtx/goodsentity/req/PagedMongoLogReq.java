package com.zhtx.goodsentity.req;

import com.mongodb.BasicDBObject;
import com.zhtx.goodsentity.common.PagedRequestBase;

public class PagedMongoLogReq extends PagedRequestBase{
private String sourceSys;
private int requestType;
private String requestUrl;
private int yearInfo;
private String monthInfo;
private int exceptionShowType;
private int goodsType;
private String goodsBy;
private String appversion;
private String beginDay;
private String endDay;

private BasicDBObject queryObject;
private BasicDBObject sortObject;
public BasicDBObject getQueryObject() {
	return queryObject;
}

public void setQueryObject(BasicDBObject queryObject) {
	this.queryObject = queryObject;
}

public BasicDBObject getSortObject() {
	return sortObject;
}

public void setSortObject(BasicDBObject sortObject) {
	this.sortObject = sortObject;
}

public int getRequestType() {
	return requestType;
}

public void setRequestType(int requestType) {
	this.requestType = requestType;
}

public String getSourceSys() {
	return sourceSys;
}

public void setSourceSys(String sourceSys) {
	this.sourceSys = sourceSys;
}

public String getRequestUrl() {
	return requestUrl;
}

public void setRequestUrl(String requestUrl) {
	this.requestUrl = requestUrl;
}

public String getMonthInfo() {
	return monthInfo;
}

public void setMonthInfo(String monthInfo) {
	this.monthInfo = monthInfo;
}

public int getExceptionShowType() {
	return exceptionShowType;
}

public void setExceptionShowType(int exceptionShowType) {
	this.exceptionShowType = exceptionShowType;
}

public int getgoodsType() {
	return goodsType;
}

public void setgoodsType(int goodsType) {
	this.goodsType = goodsType;
}

public String getgoodsBy() {
	return goodsBy;
}

public void setgoodsBy(String goodsBy) {
	this.goodsBy = goodsBy;
}

public String getAppversion() {
	return appversion;
}

public void setAppversion(String appversion) {
	this.appversion = appversion;
}

public int getYearInfo() {
	return yearInfo;
}

public void setYearInfo(int yearInfo) {
	this.yearInfo = yearInfo;
}

public String getBeginDay() {
	return beginDay;
}

public void setBeginDay(String beginDay) {
	this.beginDay = beginDay;
}

public String getEndDay() {
	return endDay;
}

public void setEndDay(String endDay) {
	this.endDay = endDay;
}

}
