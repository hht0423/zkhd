package com.zk.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.inveno.util.CollectionUtils;
import com.inveno.util.FileUtil;
import com.inveno.util.MemCachedUtil;
import com.zk.common.baseclass.ServiceFacade;

/***
 * 该工具类主要用来缓存系统的数据词�?和系统配置信�?
 * 
 * @author LQ
 * @since 2011-2-10
 */
public final class SystemCacheUtil {

	/*private SystemCacheUtil() {
	}

	*//**
	 * 将数据字典导入缓�?
	 * 
	 * @param parentid
	 *            --父ID
	 * @param childtype
	 *            --子类型描�?
	 * @return
	 */
	//@SuppressWarnings("unchecked")
/*	public static List<Dd> getDdCache() {
		List<Dd> ddCache = (List<Dd>) MemCachedUtil
				.getMemCache(Constants.DICTIONRY_DD_CACHE);
		Map<String, List<Dd>> ddMapForList = (Map<String, List<Dd>>) MemCachedUtil.getMemCache(Constants.DICTIONRY_DD_MAP_CACHE);
		//if (CollectionUtils.isEmpty(ddCache)) {
		List<Dd> 	ddCache = ServiceFacade.getDdService().findDbs();
			//MemCachedUtil.setMemCache(Constants.DICTIONRY_DD_CACHE, ddCache,null);
		//}
		// 封装map
		if (CollectionUtils.isEmpty(ddMapForList)) {
			ddMapForList = new ConcurrentHashMap<String, List<Dd>>();
			if (CollectionUtils.isNotEmpty(ddCache)) {
				for (Dd dd : ddCache) {
					List<Dd> list = ddMapForList.get(dd.getParentId());
					if (CollectionUtils.isNotEmpty(list)) {
						list.add(dd);
					} else {
						list = new ArrayList<Dd>(10);
						list.add(dd);
					}
					ddMapForList.put(dd.getParentId(), list);
					//MemCachedUtil.setMemCache(Constants.DICTIONRY_DD_MAP_CACHE,ddMapForList, null);
				}
			}
		}
		return ddCache;
	}*/

	/**
	 * 获得数据词典各个类型对应的描�?
	 * 
	 * @param parentid
	 *            --父ID
	 * @param childtype
	 *            --子类型描�?
	 * @return
	 */
/*	public static String getDdTypeName(String parentid, String childtype) {
		String rvt = "";
		List<Dd> ddCacheList = getDdCache();
		for (Dd dd : ddCacheList) {
			if (dd.getParentId().equals(parentid)
					&& dd.getChildType().equals(childtype)) {
				rvt = dd.getTypeName();
			}
		}
		return rvt;
	}*/

	/**
	 * 以code值与 paretId值得到数据词典�?
	 * 
	 * @author zhiwen.wang
	 * @date 2011-12-01
	 * @param paretId
	 *            父ID
	 * @param code
	 *            �?
	 * @return
	 */
/*	public static Dd getDdByCodeParentId(String paretId, String code) {
		List<Dd> ddCacheList = getDdListByParentId(paretId);
		if(CollectionUtils.isNotEmpty(ddCacheList)){
			for (Dd dd : ddCacheList) {
				if (dd.getCode().equals(code)) {
					return dd;
				}
			}
		}
		return null;
	}*/

	/**
	 * 根据parentId得到些类型的�?��集合
	 * 
	 * 2011-2-12
	 * 
	 * @author yaoyuan
	 * @param parentId
	 *            类别id
	 * @return
	 */
/*	@SuppressWarnings("unchecked")
	public static List<Dd> getDdListByParentId(String parentId) {
		Map<String, List<Dd>> ddMapForList = (Map<String, List<Dd>>) MemCachedUtil.getMemCache(Constants.DICTIONRY_DD_MAP_CACHE);
		if (CollectionUtils.isEmpty(ddMapForList)) {
			getDdCache();
			ddMapForList = (Map<String, List<Dd>>) MemCachedUtil.getMemCache(Constants.DICTIONRY_DD_MAP_CACHE);
		}
		return ddMapForList.get(parentId);
	}*/


}
