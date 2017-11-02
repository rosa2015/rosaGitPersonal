package com.zhtx.goodsapihttp.service.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.zhtx.goodsentity.common.JsonResult;

@Path("/alert")
@Consumes("application/json")//当前方法接收的参数类型
@Produces("application/json; charset=utf-8")//当前类的所有方法都返回json格式的数据
@Api("alert(当前站点心跳地址)")
public interface IAlertHttpService {
	/**
	 * 当前站点心跳地址，path不能修改为其他地址
	 * @return
	 */
	@POST
    @Path("/heart")
    @ApiOperation(value = "当前站点心跳地址", httpMethod = "POST", notes = "赵海龙")
    JsonResult heart();

}
