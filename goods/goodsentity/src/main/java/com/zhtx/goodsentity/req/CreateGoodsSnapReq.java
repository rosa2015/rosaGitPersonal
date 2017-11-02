package com.zhtx.goodsentity.req;

import java.util.List;

import com.zhtx.goodsentity.GoodsInfoEntity;

public class CreateGoodsSnapReq
{
	private List<GoodsInfoEntity> goodsInfoEntity;

	public List<GoodsInfoEntity> getGoodsInfoEntity()
	{
		return goodsInfoEntity;
	}

	public void setGoodsInfoEntity(List<GoodsInfoEntity> goodsInfoEntity)
	{
		this.goodsInfoEntity = goodsInfoEntity;
	} 
}


 