package com.zhtx.goodscore.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.zhtx.goodscore.security.AES;
import com.zhtx.goodscore.util.ParseHelper;
import com.zhtx.goodscore.util.PropertyUtils;
import com.zhtx.goodscore.util.SystemUtils;
@DisconfFile(filename = "config.properties")
public class Config {
	//是否发邮件
    private static String isSendMail;
	//邮件服务器地址
    private static String emailHost;
	//邮件发送人
    private static String emailFrom;
	//收件人地址
    private static String emailTo;
	//邮件服务器账号
    private static String emailUserName;
	//邮件服务器密码
    private static String emailPwd;
	//.net版总部的域名
    private static String zbBaseUrl;
    //接口项目中的参数是否加密
    private static String interceptSwith;
    //quartz服务是否异步执行
    private static String isAsynQuartz;
    //订单接口域名
    private static String goodsBaseUrl;
    //静态文件域名
    private static String staticSitePath;
    //图片服务器域名
    private static String imgUrl;
    //图片上传服务器地址
    private static String uploaderHandler;
    //站点域名
	private static String sitePath;

	public static String getDebugCookie() {
		return "0|2|admin|2018-05-05 16:44:45";
	}
	public static boolean getIsDebug() {
		return Config.getSitePath().indexOf("localhost")>0;
	}
    @DisconfFileItem(name = "sitePath", associateField = "sitePath")
	public static String getSitePath() {
		return sitePath;
	}
    @DisconfFileItem(name = "isSendMail", associateField = "isSendMail")
	public static String getIsSendMail() {
		return isSendMail;
	}
	public static void setIsSendMail(String isSendMail) {
		Config.isSendMail = isSendMail;
	}
    @DisconfFileItem(name = "emailHost", associateField = "emailHost")
	public static String getEmailHost() {
		return emailHost;
	}
	public static void setEmailHost(String emailHost) {
		Config.emailHost = emailHost;
	}
    @DisconfFileItem(name = "emailFrom", associateField = "emailFrom")
	public static String getEmailFrom() {
		return emailFrom;
	}
	public static void setEmailFrom(String emailFrom) {
		Config.emailFrom = emailFrom;
	}
    @DisconfFileItem(name = "emailTo", associateField = "emailTo")
	public static String getEmailTo() {
		return emailTo;
	}
	public static void setEmailTo(String emailTo) {
		Config.emailTo = emailTo;
	}
    @DisconfFileItem(name = "emailUserName", associateField = "emailUserName")
	public static String getEmailUserName() {
		return emailUserName;
	}
	public static void setEmailUserName(String emailUserName) {
		Config.emailUserName = emailUserName;
	}
    @DisconfFileItem(name = "emailPwd", associateField = "emailPwd")
	public static String getEmailPwd() {
		if (emailPwd!=null&&!emailPwd.isEmpty()) {
			 return AES.aesDecrypt(emailPwd);
		}
		return emailPwd;
	}
	public static void setEmailPwd(String emailPwd) {
		Config.emailPwd = emailPwd;
	}
    @DisconfFileItem(name = "net.zb.baseurl", associateField = "zbBaseUrl")
	public static String getZbBaseUrl() {
		return zbBaseUrl;
	}
	public static void setZbBaseUrl(String zbBaseUrl) {
		Config.zbBaseUrl = zbBaseUrl;
	}
    @DisconfFileItem(name = "interceptSwith", associateField = "interceptSwith")
	public static String getInterceptSwith() {
		return interceptSwith;
	}
	public static void setInterceptSwith(String interceptSwith) {
		Config.interceptSwith = interceptSwith;
	}
    @DisconfFileItem(name = "isAsynQuartz", associateField = "isAsynQuartz")
	public static String getIsAsynQuartz() {
		return isAsynQuartz;
	}
	public static void setIsAsynQuartz(String isAsynQuartz) {
		Config.isAsynQuartz = isAsynQuartz;
	}
    @DisconfFileItem(name = "net.goods.baseurl", associateField = "goodsBaseUrl")
	public static String getgoodsBaseUrl() {
		return goodsBaseUrl;
	}
	public static void setgoodsBaseUrl(String goodsBaseUrl) {
		Config.goodsBaseUrl = goodsBaseUrl;
	}
    @DisconfFileItem(name = "static.baseurl", associateField = "staticSitePath")
	public static String getStaticSitePath() {
		return staticSitePath;
	}
	public static void setStaticSitePath(String staticSitePath) {
		Config.staticSitePath = staticSitePath;
	}
    @DisconfFileItem(name = "imgUrl", associateField = "imgUrl")
	public static String getImgUrl() {
		return imgUrl;
	}
	public static void setImgUrl(String imgUrl) {
		Config.imgUrl = imgUrl;
	}
    @DisconfFileItem(name = "uploaderHandler", associateField = "uploaderHandler")
	public static String getUploaderHandler() {
		return uploaderHandler;
	}
	public static void setUploaderHandler(String uploaderHandler) {
		Config.uploaderHandler = uploaderHandler;
	}
	public static void setSitePath(String sitePath) {
		Config.sitePath = sitePath;
	}

}
