package com.zhtx.goodsapihttp.service.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.zhtx.goodsentity.common.JsonResult;


@Path("/quartzhttpservice")
@Consumes("application/json")//当前方法接收的参数类型
@Produces("application/json; charset=utf-8")//当前类的所有方法都返回json格式的数据
@Api("quartzservice(quartz定时服务)")
public interface IQuartzHttpService {
//	@POST
//	@Path("/goodstimeupdatetask")
//	@ApiOperation(value = "修改广告投放状态服务", httpMethod = "POST", notes = "")
//	JsonResult goodsTimeUpdateTask();
}
