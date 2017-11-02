package com.zhtx.goodsapihttp.common;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import com.zhtx.goodsapi.common.IJobDo;
import com.zhtx.goodsapi.common.LogServiceBLL;
import com.zhtx.goodsapi.redis.RedisService;
import com.zhtx.goodscore.config.Config;
import com.zhtx.goodscore.util.JsonUtil;
import com.zhtx.goodscore.util.ParseHelper;
import com.zhtx.goodscore.util.PropertyUtils;
import com.zhtx.goodscore.util.SpringBeanHelper;
import com.zhtx.goodscore.util.StringUtils;
import com.zhtx.goodscore.util.SystemUtils;
import com.zhtx.goodsentity.common.JsonResult;
import com.zhtx.goodsentity.consts.RedissCacheKey;
import com.zhtx.goodsentity.domain.ActionLog;
import com.zhtx.goodsentity.enums.HttpReturnRnums;



public class QuartzServiceHelper {
	private static RedisService redisService=SpringBeanHelper.getCustomBeanByType(RedisService.class);
	private static LogServiceBLL logServiceBLL=SpringBeanHelper.getCustomBeanByType(LogServiceBLL.class);
	
	/**
	 * 根据beanName同步或异步调用run方法，并记录日志
	 * @author hailongzhao
	 * @date 20160217
	 * @return
	 */
	public static JsonResult doRun(String beanName){
		String isAsynQuartz=Config.getIsAsynQuartz();
		if (isAsynQuartz.equals("1")) {
			return asynDo(beanName);
		}else {
			JsonResult result = synDo(beanName);
			if (result.getCode()!=HttpReturnRnums.Success.value()) {
				throw new RuntimeException(result.getMsg()+(result.getData()==null?"":result.getData().toString()));
			}
			return result;
		}
	}
	/**
	 * 同步调用业务的服务
	 * @date 20160217
	 * @author hailongzhao
	 * @param beanName
	 * @return
	 */
	private static JsonResult synDo(String beanName) {
		String key=RedissCacheKey.HttpQuartz_Key+beanName;
		String quartzObj=redisService.get(key);
		//redis中存在这个key，则表示当前服务正在执行，防止套圈执行
		if (quartzObj!=null&&quartzObj.equals("1")) {
			return new JsonResult(HttpReturnRnums.ParaError.value(),"服务正在执行，不能重复调用");
		}
		redisService.set(key, "1",4,TimeUnit.HOURS);
		try {
			Object obj = SpringBeanHelper.getCustomBean(beanName);
			if (obj != null && obj instanceof IJobDo) {
				((IJobDo) obj).run();
			} else {
				return new JsonResult(HttpReturnRnums.ParaError.value(),
						"给定的bean:" + beanName+ "不存在或没有实现com.zhtx.goodsapi.common.IJobDo接口");
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
			String stackTrace = StringUtils.getStackTrace(e);
			return new JsonResult(HttpReturnRnums.ParaError.value(),
					"执行给定的bean:" + beanName+ "的run方法时异常:"+e.getMessage(),stackTrace);
		}finally{
			redisService.remove(key);
		}
	}
	/**
	 * 异步调用业务的服务
	 * @date 20160217
	 * @author hailongzhao
	 * @param beanName
	 * @return
	 */
	private static JsonResult asynDo(String beanName){
		//由于记录日志是异步执行的，因此需要将request中的属性先拿出来
		//否则当方法返回时，异步方法中可能访问不到request中的属性
		ActionLog logEngity=getActionLog();
		Thread dThread = new Thread(new Runnable() {
			@Override
			public void run() {
				String exceptionMsg = "";
				String stackTrace = "";
				String resultJson="";
				Date requestTime=new Date();
				JsonResult result =synDo(beanName);
				if (result.getCode()!=HttpReturnRnums.Success.value()) {
					exceptionMsg=result.getMsg();
					stackTrace=result.getData()==null?"":result.getData().toString();
				}
				resultJson=JsonUtil.obj2string(result);
				writelog(logEngity,requestTime,exceptionMsg,stackTrace,resultJson);
			}
		});
		dThread.setDaemon(false);
		dThread.start();
		return new JsonResult();
	}
	/**
	 * 从cxf框架的Message对象中获取请求相关的属性
	 * @date 20160217
	 * @author hailongzhao
	 * @param beanName
	 * @return
	 */
	private static ActionLog getActionLog(){
		Message message = PhaseInterceptorChain.getCurrentMessage();
		Message inMessage = message.getExchange().getInMessage();
		Method methodName = (Method) inMessage.get("org.apache.cxf.resource.method");
		TreeMap header = (TreeMap) inMessage.get(Message.PROTOCOL_HEADERS);
		String contentType = (String) inMessage.get(Message.CONTENT_TYPE);
		String httpRequestMethod = (String) inMessage.get(Message.HTTP_REQUEST_METHOD);
		String url = (String) inMessage.get(Message.REQUEST_URL);
		HttpServletRequest request = (HttpServletRequest)inMessage.get(AbstractHTTPDestination.HTTP_REQUEST);
		String clientIp = SystemUtils.getClientIp(request);
		ActionLog logEngity = new ActionLog();
		logEngity.setClientIp(clientIp);
		logEngity.setRequestUrl(url);
		logEngity.setContentType(contentType);
		logEngity.setHeader(header.toString());
		logEngity.setRequestMethod(httpRequestMethod);
		logEngity.setMethodName(methodName.toString());
		return logEngity;
	}
	/**
	 * 将异步执行的日志写入mq
	 * @date 20160217
	 * @author hailongzhao
	 * @param beanName
	 * @return
	 */
 	private static void writelog(ActionLog logEngity,Date requestTime,String exceptionMsg,String stackTrace,String resultJson){
		try {
			List<String> ipinfoList = SystemUtils.getLocalIpInfo();
			String appServerIP = JsonUtil.obj2string(ipinfoList);
			logEngity.setUserID(-1);
			logEngity.setUserName("");
			logEngity.setRequestType(0);
			logEngity.setSourceSys("goodsapihttp");
			logEngity.setParam("");
			logEngity.setDecryptMsg("");
			logEngity.setResultJson(resultJson);
			logEngity.setAppServer(appServerIP);
			logEngity.setException(exceptionMsg);
			logEngity.setStackTrace(stackTrace);
			if (!stackTrace.isEmpty()) {
				logEngity.setHasError(1);
			}
			Date endDate = new Date();
			logEngity.setExecuteTime(endDate.getTime() - requestTime.getTime());
			logEngity.setRequestTime(ParseHelper.ToDateString(requestTime, ""));
			logServiceBLL.SystemActionLog(logEngity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
