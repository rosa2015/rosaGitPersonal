package com.zhtx.goodscore.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhtx.goodscore.config.Config;

public class SystemUtils {
	private volatile static   List<String> localIpInfo;
	private final  static Logger log = LoggerFactory.getLogger("com.zhtx.zbscore.util.SystemUtils");
	/**
	 * 获取本机的内网ip，本机名，外网ip
	 * @date 20151022
	 * @author zhaohl
	 * @return
	 * @throws Exception 
	 */
	public static List<String> getLocalIpInfo() {
		if (localIpInfo == null) {
			synchronized (SystemUtils.class) {
				if (localIpInfo == null) {
					localIpInfo = getLocalInfo();
				}
			}
		}
		return localIpInfo;
	}
		public static String getLocalIp(){
		List<String> ipinfoList = getLocalIpInfo();
		if (!ipinfoList.get(2).isEmpty()) {
			return ipinfoList.get(2);
		}
		if (!ipinfoList.get(0).isEmpty()) {
			return ipinfoList.get(0);
		}
		return "";
	}
	private static List<String> getLocalInfo(){
		String localip = null;// 本地IP，如果没有配置外网IP则返回它  
        String netip = null;// 外网IP  
        String hostName = null;// 本机名称
        try {  
            InetAddress ip = null;  
            boolean finded = false;// 是否找到外网IP  
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();  
            while (netInterfaces.hasMoreElements() && !finded) {  
                NetworkInterface ni = netInterfaces.nextElement();  
                Enumeration<InetAddress> address = ni.getInetAddresses();  
                while (address.hasMoreElements()) {  
                    ip = address.nextElement();  
                    if (!ip.isLoopbackAddress()&&ip.getHostAddress().indexOf(":") == -1) {
						if (ip.isSiteLocalAddress()) {
	                        localip = ip.getHostAddress();// 内网IP 
	                        hostName = ip.getHostName();
						}else {
	                        netip = ip.getHostAddress();// 外网IP  
	                        finded = true;  
	                        break; 
						}
					}
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        	log.info("获取本机ip时异常:"+e.getMessage());
        } 
        if (localip==null) {
        	localip="";
		}
        if (netip==null) {
        	netip="";
		}
        if (hostName==null) {
        	hostName="";
		}
        List<String> listInfo = new ArrayList<>();
        listInfo.add(localip);
        listInfo.add(hostName);
        listInfo.add(netip);
		return listInfo;
	}
	public static String getClientIp(HttpServletRequest request){
		if (request==null) {
			return "";
		}
		String clientIp = request.getHeader("x-forwarded-for");
		if (clientIp==null||clientIp.isEmpty()||clientIp.toLowerCase().equals("unknown")) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}
	/**
	 * 发送邮件
	 * @param title
	 * @param body
	 * @param emailHost
	 * @param emailFrom
	 * @param emailTo
	 * @param emailUserName
	 * @param emailPwd
	 */
	public static void sendEmailTo(String title,String body,String emailHost,String emailFrom,
			String emailTo,String emailUserName,String emailPwd){
		try {
		    Properties props = new Properties();
		    // Setup mail server
		    //否则发送邮件时，可能会报错： 501 Syntax: HELO hostname
		    props.put("mail.smtp.localhost", "localHostAdress"); 
		    props.put("mail.smtp.host", emailHost);// 设置smtp主机
		    props.put("mail.smtp.auth", "true");// 使用smtp身份验证
		    Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(emailFrom));
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
		    message.setSubject(title, "utf-8");//标题
		    message.setText(body,"utf-8");//内容
		    //http协议部分会自动转换成超链接
		    message.saveChanges();
		    // Send message
		    Transport transport = session.getTransport("smtp");
		    transport.connect(emailHost, emailUserName, emailPwd);
		    transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			log.info(title+"发送邮件时异常:"+e.getMessage());
		}
		
	}
	/**
	 * 发送报警邮件
	 * @param title
	 * @param body
	 */
	public static void sendAlertEmail(String title,String body){
		try {
			String emailHost = Config.getEmailHost();//"smtp.263.net";//发送邮件服务器
		    String emailFrom = Config.getEmailFrom();//"edssys@etao.cn";
		    String emailTo = Config.getEmailTo();//"zhao.hailong@etao.cn";
		    String emailUserName = Config.getEmailUserName();//"edssys@etao.cn";
		    String emailPwd = Config.getEmailPwd();//"eds1234";
		    sendEmailTo(title,body,emailHost,emailFrom,emailTo,emailUserName,emailPwd);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static String getSitePath() {
		String result="";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String staticUrl=request.getRequestURL().toString();
		if (staticUrl.indexOf("http://localhost")==0) {
			String mkkString = staticUrl.substring("http://".length());
			int index = mkkString.indexOf("/");
			if(index>0){
				String url = mkkString.substring(index + 1);
				int end = url.indexOf("/");
				result= staticUrl.substring(0, "http://".length()+index+end+1);
			}
		}else {
			result="http://"+request.getHeader("host");
		}
		return result;
	}
}
