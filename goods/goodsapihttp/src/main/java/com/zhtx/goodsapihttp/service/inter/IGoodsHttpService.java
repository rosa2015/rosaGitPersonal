package com.zhtx.goodsapihttp.service.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.zhtx.goodsentity.common.JsonResult;
import com.zhtx.goodsentity.req.CreateGoodsSnapReq;
import com.zhtx.goodsentity.req.SelGoodsSnapReq;

@Path("/goodsservice")
@Consumes("application/json")//当前方法接收的参数类型
@Produces("application/json; charset=utf-8")//当前类的所有方法都返回json格式的数据
@Api("goodsservice(商品相关接口)")
public interface IGoodsHttpService
{
	@POST
	@Path("/creategoodsnap")
	@ApiOperation(value = "服务站商品审核后调用保存数据到mongo", httpMethod = "POST", notes = "wangchao")
	JsonResult createGoodSnap(CreateGoodsSnapReq req); 
	@POST
	@Path("/selgoodssnap")
	@ApiOperation(value = "根据商品ID查询商品信息", httpMethod = "POST", notes = "wangchao")
	JsonResult selGoodsSnap(SelGoodsSnapReq req);
}
