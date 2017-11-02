package com.zhtx.goodsapihttp.common;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.impl.ResponseImpl;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;

import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhtx.goodsapi.common.LogServiceBLL;
import com.zhtx.goodscore.config.Config;
import com.zhtx.goodscore.util.JsonUtil;
import com.zhtx.goodscore.util.ParseHelper;
import com.zhtx.goodscore.util.PropertyUtils;
import com.zhtx.goodscore.util.StringUtils;
import com.zhtx.goodscore.util.SystemUtils;
import com.zhtx.goodsentity.common.JsonResult;
import com.zhtx.goodsentity.domain.ActionLog;
import com.zhtx.goodsentity.enums.HttpReturnRnums;


public class RqeustInterceptor extends AbstractPhaseInterceptor<Message> {
	@Autowired
	LogServiceBLL logServiceBLL;
	protected static final Logger logger = LoggerFactory.getLogger(RqeustInterceptor.class);
	public RqeustInterceptor(String phase) {
		super(phase);
	}

	public RqeustInterceptor() {
		super(Phase.PRE_STREAM);
	}

	/**
	 * <请求拦截器> 创 建 人: zhaohailong 创建时间: 20151020
	 * 
	 * @param arg0
	 * @throws Fault
	 */
	public void handleMessage(Message message) throws Fault {
		// for (String iterable_element : message.keySet()) {
		// System.out.println("message------" + iterable_element + ":"+
		// message.get(iterable_element));
		// }
		// Exchange exchange = message.getExchange();
		// for (String iterable_element : exchange.keySet()) {
		// System.out.println("exchange------" + iterable_element + ":"+
		// exchange.get(iterable_element));
		// }
		// Message inputmsgMessage = message.getExchange().getInMessage();
		// for (String iterable_element : inputmsgMessage.keySet()) {
		// System.out.println("InMessage------" + iterable_element + ":"+
		// inputmsgMessage.get(iterable_element));
		// }
		// HttpServletResponse reponse = (HttpServletResponse)
		// message.get(AbstractHTTPDestination.HTTP_RESPONSE);//这句可以获取到request
		// final ByteArrayOutputStream os =
		// message.getContent(ByteArrayOutputStream.class);
		// String result = "";
		//
		// HttpResultModel<String> error =
		// JsonUtil.str2obj(result,HttpResultModel.class);
		try {
			Exchange exchange = message.getExchange();
			Message inMessage = exchange.getInMessage();
			Message outMessage = exchange.getOutMessage();
			Method methodName = (Method) inMessage.get("org.apache.cxf.resource.method");
			if(methodName==null){
				return;
			}
	
			String exceptionMsg = "";
			String stackTrace = "";
			String resultJson = "";
			String url = (String) inMessage.get(Message.REQUEST_URL);
			//如果quartz是异步执行的，则此处不记录请求日志，由服务自己记录
			//zhaohl，20160217
			String isAsynQuartz=Config.getIsAsynQuartz();
			if (isAsynQuartz.equals("1")&&url.indexOf("/quartzhttpservice/")>0) {
				return ;
			}
			//如果是swagger接口文档请求来的，则不记录日志
			if (url.indexOf("/api-docs")>=0||
				url.indexOf("/alert/heart")>=0||
				url.indexOf("/swagger")>=0) {
					return;
			}
			MessageContentsList messageContentsList = (MessageContentsList) outMessage.getContent(List.class);
			//系统异常时，返回值的类型为ResponseImpl
			if (messageContentsList.get(0) instanceof ResponseImpl) {
				Object responseEntity=((ResponseImpl)messageContentsList.get(0)).getEntity();
				if (responseEntity instanceof JsonResult) {
					JsonResult res = (JsonResult)responseEntity;
					if (res.getCode() == HttpReturnRnums.SystemError.value()) {
						exceptionMsg = res.getMsg();
						stackTrace = (String) res.getData();
					}
				}
			}else if (messageContentsList.get(0) instanceof JsonResult) {
				//正常返回时，返回值的类型为HttpResultModel<Object>)
				//此时需要记录返回值的json
				JsonResult res = (JsonResult)messageContentsList.get(0);
				try {
					resultJson = JsonUtil.obj2string(res);
				} catch (Exception e) {
					exceptionMsg = e.getMessage();
					stackTrace = StringUtils.getStackTrace(e);
					logger.error(exceptionMsg+stackTrace);
					//如果当前请求返回的结果没问题，但是序列化时出错了，
					//此时需要将outMessage.messageContentsList中的正确的方法返回值改为异常对象
					//否则，由于cxf框架在序列化时
					//是根据outMessage.messageContentsList转换为json输出流+GlobalExceptionHandler中返回的Response的json输出流
					//而outMessage.messageContentsList无法正确序列化，导致输出流中只输出了部分json（不包含出错的节点）
					//例如：一个方法返回值中的data对象中有两个属性total和count，而count属性导致了序列化失败，则最终输出的json：
					//{"code": 200,"msg": "success","data": {"total": 5}}{"code": -1,"msg": "系统错误","data": "堆栈信息。。"}
					//而不是期望的：{"code": -1,"msg": "系统错误","data": "堆栈信息。。"}
					
					JsonResult rep=new JsonResult();
			        rep.setCode(HttpReturnRnums.SystemError.value());
			        rep.setMsg(HttpReturnRnums.SystemError.desc());
			        rep.setData(stackTrace);
			        messageContentsList.remove(0);
			        messageContentsList.add(0, rep);
				}
			}
	
			TreeMap header = (TreeMap) inMessage.get(Message.PROTOCOL_HEADERS);
			String contentType = (String) inMessage.get(Message.CONTENT_TYPE);
			String httpRequestMethod = (String) inMessage.get(Message.HTTP_REQUEST_METHOD);
			
			HttpServletRequest request = (HttpServletRequest) inMessage.get(AbstractHTTPDestination.HTTP_REQUEST);// 这句可以获取到request
			String clientIp = SystemUtils.getClientIp(request);
			
			List<String> ipinfoList = SystemUtils.getLocalIpInfo();
			String appServerIP = JsonUtil.obj2string(ipinfoList);
	
			Date requestTime = (Date) exchange.get("requestTime");
			String param = (String) exchange.get("encryptMsg");
			String decryptMsg = (String) exchange.get("decryptMsg");
			
			ActionLog logEngity = new ActionLog();
			logEngity.setUserID(-1);
			logEngity.setUserName("");
			logEngity.setRequestType(0);
			logEngity.setClientIp(clientIp);
			logEngity.setSourceSys("goodsapihttp");
			logEngity.setRequestUrl(url);
			logEngity.setParam(param);
			logEngity.setDecryptMsg(decryptMsg);
			if (contentType==null) {
				contentType="";
			}
			logEngity.setContentType(contentType);
			logEngity.setHeader(header.toString());
			logEngity.setRequestMethod(httpRequestMethod);
			logEngity.setMethodName(methodName.toString());
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
			logger.debug("方法名称：" + methodName);
			logger.debug("方法入参：" + param);
			logger.debug("方法url:" + request.getServletPath());
			logger.debug("执行时间,精确到毫秒:" + (endDate.getTime() - requestTime.getTime()));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
