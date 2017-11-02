package com.zhtx.goodsentity.req;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SelGoodsSnapReq implements Serializable
{ 
	private int goodSnapId;
//	private int goodsId;
// 
//	private int goodsVersion;
// 
//	private String goodsIdAndVersion;
//
//	public int getGoodsId()
//	{
//		return goodsId;
//	}
//
//	public void setGoodsId(int goodsId)
//	{
//		this.goodsId = goodsId;
//	}
//
//	public int getGoodsVersion()
//	{
//		return goodsVersion;
//	}
//
//	public void setGoodsVersion(int goodsVersion)
//	{
//		this.goodsVersion = goodsVersion;
//	}
//
//	public String getGoodsIdAndVersion()
//	{
//		return goodsIdAndVersion;
//	}
//
//	public void setGoodsIdAndVersion(String goodsIdAndVersion)
//	{
//		this.goodsIdAndVersion = goodsIdAndVersion;
//	}

	public int getGoodSnapId()
	{
		return goodSnapId;
	}

	public void setGoodSnapId(int goodSnapId)
	{
		this.goodSnapId = goodSnapId;
	}
}
