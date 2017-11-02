package com.zhtx.goodscore.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	
	 public static final SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
	 public static final SimpleDateFormat yyyymmFormat = new SimpleDateFormat("yyyymm");
	/**
	 * @para format 时间格式
	 * @return  按指定的时间格式返回时间，如果为空则按yyyy-MM-dd HH:mm:ss格式返回
	 */
	public static String getToday(String format) {
		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("-480"));

		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(c.getTime());
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat format= new SimpleDateFormat("yyyyMMddHHmm");
		return format.format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param date   待格式化的时间
	 *
	 * @param format   时间格式
	 *
	 * @return  返回格式化后的时间
	 */
	public static String formartDate(Date date, String format) {
		if (format == null)
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(date);
	}

	public static Date getDate(String date) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 当前时间与指定时间的间隔天数
	 * 
	 * @param enddate  时间
	 * @return  返回间隔的天数
	 */
	public static long getDateNum(Date enddate) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(new Date());
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(enddate);
		long subDay = (endDate.getTimeInMillis() - startDate.getTimeInMillis())
				/ 1000 / 60 / 60 / 24;
		System.out.println(subDay);

		return subDay;

	}

	/**
	 *任意两个时间段的间隔天数
	 * 
	 * @param startDate  开始时间
	 * @param endDate  截止时间
	 * @return  返回间隔的天数
	 */
	public static long getDateNum(Date startDate, Date endDate) {

		Calendar enddate = Calendar.getInstance();
		enddate.setTime(endDate);

		Calendar startdate = Calendar.getInstance();
		startdate.setTime(startDate);

		long subDay = (enddate.getTimeInMillis() - startdate.getTimeInMillis())
				/ 1000/ 60 / 60 / 24;

		return subDay;

	}


        


	/**
	 *任意两个时间段的间隔天数
	 * 
	 * @param start 开始时间
	 * @param end   截止时间
	 * @return  返回间隔天数
	 * @throws ParseException
	 */
	public static long getDateNum(String start, String end) {

		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
		Date endDate = null;
		try {
			startDate = simFormat.parse(start);
			endDate = simFormat.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getDateNum(startDate, endDate);

	}

	public static long getDateNum(String start, String end,String format) {
		if(format == null){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simFormat = new SimpleDateFormat(format);

		Date startDate = null;
		Date endDate = null;
		Calendar enddate = null;
		Calendar startdate = null;
		try {
			startDate = simFormat.parse(start);
			endDate = simFormat.parse(end);
			enddate = Calendar.getInstance();
			enddate.setTime(endDate);

			startdate = Calendar.getInstance();
			startdate.setTime(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return startdate.getTimeInMillis() - enddate.getTimeInMillis();

	}


	/**
	 * 两个时间进行比较
	 * 
	 * @param beginDate  第一个时间
	 * @param endDate  第二个时间
	 * @return
	 */
	public static boolean isBigDate(Date beginDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(beginDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);
		long value = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000 / 60
				/ 60 / 24;
		if (value > 0)
			return true;
		return false;
	}
	 /**
     * 检验时间格式是否合法
     * @param input 待检验的时间
     * @return
     */
    public static boolean isDateFormat(String input){
		boolean result = false;	
		String format1 =  "^(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})$";
		String format2 = "^(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$";
        Pattern p = Pattern.compile(format2); 
		Matcher m = p.matcher(input);     
		if (m.find()){
			   result = true;
			   return true;
		}
		p =  Pattern.compile(format1); 
		m = p.matcher(input);
		if(m.find())
		   	result = true;
		return result;
	}
    
    public static Date formatDate(String date){
    	Date  date1 = null;
    	try {
    		date1 = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date1;
    }
    
    public static String formatDate(long date){
    	Date date1 = new Date(date);
    	return format.format(date1);
    }
    
    public static String unDoTime(SimpleDateFormat format, String repairTime,String handleTime)
			throws ParseException {
		long repairTimeL = format.parse(repairTime).getTime();
		long unDoTimeL = 0L;
		if("".equals(handleTime)){
			unDoTimeL = System.currentTimeMillis() - repairTimeL;
		}else{
			unDoTimeL = format.parse(handleTime).getTime() - repairTimeL;
		}
		long days = unDoTimeL/(24*3600*1000L);
		long hours = unDoTimeL/(3600*1000L) - days*24;
		String unDoTime = days + "天" + hours + "小时";
		return unDoTime;
	}
    
    public static void main(String[] args) throws Exception{
    	//当前  1448608136841
    	//45天前  1444720136841
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis()-45*24*3600*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date(1444720136841L+2*60*60*1000L);
		String ss = sdf.format(d);
		System.out.println(ss);
		System.out.println(unDoTime(sdf,ss,""));
		System.out.println(formatDate(new Date()));
		System.out.println(Long.valueOf(formatDate(new Date()))-1);
		
		System.out.println(formartDate(new Date(),"yyyyMM"));
	}
    /*
     * 将日期中含T的时间类型转换为不含T的日期时间类型
     * wagnchao
     */
    public static Date stringToDate(String stringDateContainsT){
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = null;
		try {
			time = df.parse(stringDateContainsT.replace("T", " "));	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
    }
    
}
