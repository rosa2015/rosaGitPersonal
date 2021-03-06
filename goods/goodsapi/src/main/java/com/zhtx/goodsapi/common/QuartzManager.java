/**
 * @Description: 
 *
 * @Title: QuartzManager.java
 * @Package com.joyce.quartz
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:15:52
 * @version V2.0
 */
package com.zhtx.goodsapi.common;

import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Description: 定时任务管理类
 * 
 * @ClassName: QuartzManager
 * @Copyright: Copyright (c) 2014
 * 
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:15:52
 * @version V2.0
 */
public class QuartzManager {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param cls
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:47:44
	 * @version V2.0
	 */
	@SuppressWarnings("unchecked")
	public static void addJob(String jobName, Class cls, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, cls);// 任务名，任务组，任务执行类
			// 触发器
			CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
			trigger.setCronExpression(time);// 触发器时间设定
			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @author haichao
	 * @date 2015年12月8日 14:39:58  判断当前调度是否存在
	 *  state的值代表该任务触发器的状态：
		STATE_BLOCKED 	4
		STATE_COMPLETE 	2
		STATE_ERROR 	3
		STATE_NONE 	-1
		STATE_NORMAL 	0
		STATE_PAUSED 	1
	 * */
	@SuppressWarnings("unchecked")
	public static int checkJob(String jobName) {
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			int state = scheduler.getTriggerState(jobName, TRIGGER_GROUP_NAME);
			return state;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 3;
	}

	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:48:15
	 * @version V2.0
	 */
	@SuppressWarnings("unchecked")
	public static void addJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName, Class jobClass,
			String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
			// 触发器
			CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
			trigger.setCronExpression(time);// 触发器时间设定
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:49:21
	 * @version V2.0
	 */
	@SuppressWarnings("unchecked")
	public static void modifyJobTime(String jobName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName,
					TRIGGER_GROUP_NAME);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobDetail jobDetail = sched.getJobDetail(jobName,
						JOB_GROUP_NAME);
				Class objJobClass = jobDetail.getJobClass();
				removeJob(jobName);
				addJob(jobName, objJobClass, time);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:49:37
	 * @version V2.0
	 */
	public static void modifyJobTime(String triggerName,
			String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName,
					triggerGroupName);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.setCronExpression(time);
				// 重启触发器
				sched.resumeTrigger(triggerName, triggerGroupName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:49:51
	 * @version V2.0
	 */
	public static void removeJob(String jobName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
			sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
			sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:50:01
	 * @version V2.0
	 */
	public static void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
			sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
			sched.deleteJob(jobName, jobGroupName);// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:启动所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:50:18
	 * @version V2.0
	 */
	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:50:26
	 * @version V2.0
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**     
	 * 判断cron时间表达式正确性     
	 * @param cronExpression  
	 * @author hailongzhao
	 * @date 20151211   
	 * @return      
	 */     
	public static boolean isValidExpression(final String cronExpression){   
		return CronExpression.isValidExpression(cronExpression);
//        try {     
//    		CronTrigger trigger=new CronTrigger("testname","testgroup");
//    		trigger.setCronExpression(cronExpression);// 触发器时间设定    
//			Date date = trigger.computeFirstFireTime(null);      
//	        return date != null && date.after(new Date());        
//		} catch (Exception e) { 
//		}      
//        return false;     
	}  
	/**     
	 * 获取cron表达式的下次执行时间
	 * @param cronExpression  
	 * @author hailongzhao
	 * @date 20151211   
	 * @return      
	 */     
	public static Date getFirstFireTime(final String cronExpression){     
        try {     
    		CronTrigger trigger=new CronTrigger("testname","testgroup");
    		trigger.setCronExpression(cronExpression);// 触发器时间设定    
			Date date = trigger.computeFirstFireTime(null);
			if (date != null && date.after(new Date())) {
				return date;
			}      
		} catch (Exception e) {     
		}      
        return null;     
	} 
}
