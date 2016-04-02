package com.zk.common.baseclass;

import com.danga.MemCached.MemCachedClient;
import com.zk.act.system.service.interfaces.DdService;
import com.zk.act.system.service.interfaces.LogService;
import com.zk.common.util.SysContext;
/**
 * Spring业务层接口门面模式类，Action层的业务层和业务层与业务层之音的相互调用都通过此类来完成。
 * @since 2015-11-28
 * @author Alex
 */

public class ServiceFacade {
	@SuppressWarnings("unchecked")
	private static <T> T getBean(String beanName) {
		return (T) SysContext.getBean(beanName);
	}
	/**
	 * MemCached服务类
	 * 
	 * 2011-2-12
	 * @author yaoyuan
	 * @return
	 */
	public static MemCachedClient getMemCachedClient(){
		return getBean("memCachedClient");
	}

	/**
	 * 取得数据字典服务类
	 * 
	 * 2011-2-12
	 * @author yaoyuan
	 * @return
	 */
	public static DdService getDdService(){
		return getBean("ddService");
	}

	/*获取日志服务类*/
	public static LogService getLogService(){
		return getBean("logService");
	}
}
