package com.zk.common.listener;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.inveno.util.PropertyUtils;
import com.inveno.util.RunnablePoolUtil;
import com.zk.common.baseclass.ServiceFacade;
import com.zk.common.util.SysContext;
import com.zk.common.util.SystemCacheUtil;

/**
 * 任务监听器
 *
 * 2011-1-22
 * @author yaoyuan
 */
public class ScheduleListener implements ServletContextListener {
	static{
		PropertyUtils.setFilePath("/zkhdConfig.properties");
	}
	
	private static Logger log = SysContext.getLogger();
	public static Timer NEWEST_TIMER = new Timer();
	
	public void contextDestroyed(ServletContextEvent event) {
		NEWEST_TIMER.cancel();
	}
	public void contextInitialized(ServletContextEvent event) {
		//注入servletContext
		SysContext.putServletContext(event.getServletContext());
		log.warn("zk后台开始.....");
		RunnablePoolUtil.initRunnaBlePool();
	}

}
