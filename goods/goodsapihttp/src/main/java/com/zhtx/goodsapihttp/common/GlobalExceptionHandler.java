package com.zhtx.goodsapihttp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhtx.goodsapi.common.TransactionalRuntimeException;
import com.zhtx.goodscore.util.StringUtils;
import com.zhtx.goodsentity.common.JsonResult;
import com.zhtx.goodsentity.enums.HttpReturnRnums;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import java.util.Locale;

/**
 * 全局异常处理
 * 
 * @author zhaohailong
 */
@Component
public class GlobalExceptionHandler implements ExceptionMapper {
	protected static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Override
    public Response toResponse(Throwable ex) {
		String stackTrace = StringUtils.getStackTrace(ex);
		logger.error(ex.getMessage()+stackTrace);
		JsonResult rep=new JsonResult();
    	if (ex instanceof TransactionalRuntimeException||
        	ex instanceof InvalidFormatException) {
	        rep.setCode(HttpReturnRnums.ParaError.value());
	        rep.setMsg(HttpReturnRnums.ParaError.desc());
	        rep.setData(ex.getMessage());
		}else {
	        rep.setCode(HttpReturnRnums.SystemError.value());
	        rep.setMsg(HttpReturnRnums.SystemError.desc());
	        rep.setData(StringUtils.getStackTrace(ex));
		}
        
        ResponseBuilder rb = Response.status(Response.Status.OK);
        rb.type("application/json;charset=UTF-8");
        rb.entity(rep);
        rb.language(Locale.SIMPLIFIED_CHINESE);
        Response r = rb.build();
        return r;
    }  
}


