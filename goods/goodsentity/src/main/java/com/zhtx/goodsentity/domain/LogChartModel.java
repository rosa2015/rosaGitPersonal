package com.zhtx.goodsentity.domain;

import java.util.List;

public class LogChartModel {
private List<String> urls;
private List<Double> timeData;
private List<Long> minTimeData;
private List<Long> maxTimeData;
private List<Double> rateData;
private List<Integer> numData;
public List<String> getUrls() {
	return urls;
}
public void setUrls(List<String> urls) {
	this.urls = urls;
}
public List<Double> getTimeData() {
	return timeData;
}
public void setTimeData(List<Double> timeData) {
	this.timeData = timeData;
}
public List<Double> getRateData() {
	return rateData;
}
public void setRateData(List<Double> rateData) {
	this.rateData = rateData;
}
public List<Integer> getNumData() {
	return numData;
}
public void setNumData(List<Integer> numData) {
	this.numData = numData;
}
public List<Long> getMinTimeData() {
	return minTimeData;
}
public void setMinTimeData(List<Long> minTimeData) {
	this.minTimeData = minTimeData;
}
public List<Long> getMaxTimeData() {
	return maxTimeData;
}
public void setMaxTimeData(List<Long> maxTimeData) {
	this.maxTimeData = maxTimeData;
}
}
