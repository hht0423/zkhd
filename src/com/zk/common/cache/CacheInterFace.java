package com.zk.common.cache;

/**
 * 缓存接口
 * 
 * 2011-1-18
 * @author yaoyuan
 */
public interface CacheInterFace {
	/**
	 * 从缓存中取
	 * 
	 * 2011-1-18
	 * @author yaoyuan
	 * @param key
	 * @return
	 */
	public Object getFromCache(String key);
	/**
	 * 放入缓存
	 * 
	 * 2011-1-18
	 * 
	 * @author yaoyuan
	 * @param key
	 * @param obj
	 */
	public void putToCache(String key, Object obj);
	/**
	 * 同上，最后一个参数是在缓存中存放的时间
	 * 
	 * 2011-1-18
	 * @author yaoyuan
	 * @param key
	 * @param obj
	 * @param second
	 */
	public void putToCache(String key, Object obj, int second);
	/**
	 * 刷新对对应的对象，使其不至于失效
	 * 
	 * 2011-1-18
	 * @author yaoyuan
	 * @param key
	 */
	public void flushCache(String key);
	/**
	 * 手工从缓存中移走
	 * 
	 * 2011-1-18
	 * @author yaoyuan
	 * @param key
	 */
	public void removeFromCache(String key);
	/**
	 * 清除全部缓存
	 * 
	 * 2011-1-18
	 * @author yaoyuan
	 */
	public void flushAllCache();

}
