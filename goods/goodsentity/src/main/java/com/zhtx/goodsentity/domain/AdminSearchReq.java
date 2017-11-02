package com.zhtx.goodsentity.domain;

import com.zhtx.goodsentity.common.PagedRequestBase;

import org.springframework.format.annotation.DateTimeFormat;

import javax.print.DocFlavor;

import java.util.Date;

/**
 * Created by maqian on 16/4/12.
 */
public class AdminSearchReq extends PagedRequestBase {
    /**
     * 开始日期——开始
     * //
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    String beginDateS;
    /**
     * 开始日期-结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    String beginDateE;
    /**
     * 结束时间-开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    String endDateS;
    /**
     * 结束时间-结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    String endDateE;
    /**
     * 广告名称
     */
    String name;
    /**
     * 广告id
     */
    Integer adId;
    /**
     * 服务站名称
     */
    String ssName;
    /**
     * 审核状态
     */
    Integer checkState;

    public Integer getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(Integer onlineState) {
        this.onlineState = onlineState;
    }

    public String getBeginDateS() {
        return beginDateS;
    }

    public void setBeginDateS(String beginDateS) {
        this.beginDateS = beginDateS;
    }

    public String getBeginDateE() {
        return beginDateE;
    }

    public void setBeginDateE(String beginDateE) {
        this.beginDateE = beginDateE;
    }

    public String getEndDateS() {
        return endDateS;
    }

    public void setEndDateS(String endDateS) {
        this.endDateS = endDateS;
    }

    public String getEndDateE() {
        return endDateE;
    }

    public void setEndDateE(String endDateE) {
        this.endDateE = endDateE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //
    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getSsName() {
        return ssName;
    }

    public void setSsName(String ssName) {
        this.ssName = ssName;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    /**
     * 在线状态
     */
    Integer onlineState;
}
