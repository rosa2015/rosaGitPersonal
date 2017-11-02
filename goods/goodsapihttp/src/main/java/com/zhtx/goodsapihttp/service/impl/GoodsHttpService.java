package com.zhtx.goodsapihttp.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhtx.goodsapi.mongo.MongoService;
import com.zhtx.goodsapihttp.service.inter.IGoodsHttpService;
import com.zhtx.goodscore.util.JsonUtil;
import com.zhtx.goodsentity.GoodsInfoEntity;
import com.zhtx.goodsentity.common.JsonResult;
import com.zhtx.goodsentity.req.CreateGoodsSnapReq;
import com.zhtx.goodsentity.req.SelGoodsSnapReq;
@Service
public class GoodsHttpService implements IGoodsHttpService
{ 
	@Autowired
	private MongoService mongoService;
	@Override
	public JsonResult createGoodSnap(CreateGoodsSnapReq req)
	{
		for (int i = 0; i < req.getGoodsInfoEntity().size(); i++)
		{
			String str= JsonUtil.obj2string(req.getGoodsInfoEntity().get(i));
			mongoService.saveJsonInfo(getGoodsTableName(req.getGoodsInfoEntity().get(i).getId()),str);
		}
		return new JsonResult(1,"成功",1); 
	}
	/*
	 * 生成mongo表名
	 */
	private String getGoodsTableName(int id){
		return "goodssnap_"+(id%100);
	}

	@Override
	public JsonResult selGoodsSnap(SelGoodsSnapReq req)
	{
//		if(req.getGoodsId()<=0 || req.getGoodsVersion() <0){
//			return new JsonResult(-1,"参数错误",null);
//		}
		if(req.getGoodSnapId()<=0){
			return new JsonResult(-1,"参数错误",null);
		}
		String tableName = getGoodsTableName(req.getGoodSnapId());
		List<GoodsInfoEntity> goodsInfoEntity= mongoService.selectGoodsSanp(tableName, req);
		if(goodsInfoEntity!=null && goodsInfoEntity.size()>0)
		{
			return new JsonResult(1,"成功",goodsInfoEntity.get(0));			
		}else{
			return new JsonResult(1,"成功",null);
		}
	}
}
