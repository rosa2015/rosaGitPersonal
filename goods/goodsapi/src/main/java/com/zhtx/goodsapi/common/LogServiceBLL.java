package com.zhtx.goodsapi.common;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;











import com.zhtx.goodsapi.activemq.ActiveMqService;
import com.zhtx.goodscore.config.Config;
import com.zhtx.goodscore.util.JsonUtil;
import com.zhtx.goodscore.util.PropertyUtils;
import com.zhtx.goodscore.util.StringUtils;
import com.zhtx.goodscore.util.SystemUtils;
import com.zhtx.goodsentity.domain.ActionLog;

@Component
public class LogServiceBLL {
	@Autowired
	private ActiveMqService activeMqService;

	private static Field[] fields = ActionLog.class.getDeclaredFields();
	//private static Logger logger = Logger.getLogger(LogServiceBLL.class);
	/**
	 * 系统级，记录方法的ActionLog（异步写入db和log文件）
	 * 
	 * @param
	 */
	public void SystemActionLog(ActionLog logEngity) {
		String isSendMail = Config.getIsSendMail();
		try {
			if (logEngity.getStackTrace()!=null&&!logEngity.getStackTrace().isEmpty()) {
				String alertBody=getAlertBody(logEngity);
				
				if (isSendMail.equals("1")&&alertBody!=null&&!alertBody.isEmpty()) {
					SystemUtils.sendAlertEmail(logEngity.getSourceSys()+"_java项目预警", alertBody);
				}
			}
			//initLog4DB(logEngity);
			String jsonMsg = JsonUtil.obj2string(logEngity);
			activeMqService.asynSendMessage(logEngity.getSourceSys(),jsonMsg);
			//logger.error(jsonMsg);
		} catch (Exception e) {
			if (isSendMail.equals("1")) {
				List<String> ipinfoList = SystemUtils.getLocalIpInfo();
				String appServerIP = JsonUtil.obj2string(ipinfoList);
				SystemUtils.sendAlertEmail(logEngity.getSourceSys()+ "_SystemActionLog_java项目预警", "appServerIP:"+appServerIP+"\n"+e.getMessage()+StringUtils.getStackTrace(e));
			}
		}
	}
	public void LogInfo(ActionLog logEngity) {
	}

	public void LogInfo(String msg) {
	}

	public void LogError(ActionLog logEngity) {
	}

	public void LogError(String msg) {

	}
	private String getAlertBody(ActionLog logEngity){
		try {
			StringBuilder sb = new StringBuilder();
			String stackTrace = "";
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getName().equals("stackTrace")) {
					stackTrace = field.getName() + ":"+ field.get(logEngity).toString();
				} else {
					sb.append(field.getName() + ":"+ field.get(logEngity).toString() + "\n");
				}
			}
			sb.append(stackTrace);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	private void initLog4DB(ActionLog logEngity) {
		try {
			MDC.clear();
			for (Field field : fields) {
				field.setAccessible(true);
				MDC.put(field.getName(), field.get(logEngity));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
