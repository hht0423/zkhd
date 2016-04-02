package com.zk.common.baseclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.inveno.base.BaseModel;
import com.inveno.base.Pagin;
import com.inveno.util.CollectionUtils;
import com.inveno.util.DetachedCriteriaUtil;
import com.inveno.util.StringUtil;
import com.zk.common.exception.BusinessException;
import com.zk.common.searchUtil.CriteriaUtil;
import com.zk.common.util.Constants;
import com.zk.common.util.ReflexUtil;
import com.zk.common.util.SysContext;
import com.zk.model.system.LogDetail;



/**
 * CRM实现顶层接口的抽象类
 * 
 * @author yaoyuan
 * @version 1.0 date 2008-04-27
 */
public abstract class AbstractBaseService implements IBaseService {

	public static final Logger logger = SysContext.getLogger();

	public void delete(Object obj) throws BusinessException {
		SysContext.anyDao.delete(obj);
	}

	public <T extends BaseModel> Object findById(Object id,Class<? extends BaseModel> pojoClass) throws BusinessException {
		return SysContext.anyDao.findById(id,pojoClass);
	}

	public <T extends BaseModel> Object save(Object obj) throws BusinessException {
		return SysContext.anyDao.save(obj);
	}

	public <T extends BaseModel> Object update(Object obj) throws BusinessException {
		return SysContext.anyDao.update(obj);
	}


	
	/**
	 * 该方法提供DetachedCriteria对查询字段的封装(适用于单表查询)， 2008-9-29
	 * 
	 * @author yaoyuan
	 * @param columnNames
	 *            字符串数组，以数据的形式接收要查询的字段属性，如String[] colum={"属性1","属性2","属性3"};
	 * @param pojoClass
	 *            实体类的clazz,如Mobile.class;
	 * @param aials
	 *            为要查询的POJO对象指定一个别名
	 * @return DetachedCriteria 的一个对象，如果需要查询条件，在些对象后追加查询条件。
	 */
	protected DetachedCriteria getDetachedCriteriaByColumn(String[] columnNames, Class<? extends BaseModel> pojoClass, String alias) {
		return getDetachedCriteriaByColumn(columnNames, pojoClass, alias, false);
	}
	
	/**
	 * 该方法提供DetachedCriteria对查询字段的封装(用于多表查询，避免查出重复记录) Dec 3, 2008
	 * 
	 * @author yaoyuan
	 * @param columName
	 *            字符串数组，以数据的形式接收要查询的字段属性，如String[] colum={"属性1","属性2","属性3"};
	 * @param clazz
	 *            实体类的clazz,如Mobile.class;
	 * @param alias
	 *            为要查询的POJO对象指定一个别名
	 * @param forJoinTable 是否多表连接查询
	 * @return
	 */
	protected DetachedCriteria getDetachedCriteriaByColumn(String[] columnNames,
			Class<? extends BaseModel> clazz, String alias,boolean forJoinTable) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz, alias);
		DetachedCriteriaUtil.selectColumn(criteria, columnNames, clazz, forJoinTable);
		return criteria;
	}


	
	
	
	/**
	 * 把查询条件等放入PAGIN对象，DAO中进行组合、查询
	 * @author yaoyuan
	 * 2009-8-11
	 * @param pagin
	 * @param columnName
	 * @param pojoClass
	 * @param alias
	 * @return
	 */
	protected DetachedCriteria setConditions(Pagin pagin , String[] columnName,
			Class<? extends BaseModel> pojoClass, String alias){
		return setConditions(pagin, columnName, pojoClass, alias, false);
	}
	
	/**
	 * 把查询条件等放入PAGIN对象，DAO中进行组合、查询
	 * @author yaoyuan
	 * 2009-8-11
	 * @param pagin
	 * @param columnName
	 * @param pojoClass
	 * @param alias
	 * @param joinTable 是否联表查询
	 * @return
	 */
	protected DetachedCriteria setConditions(Pagin pagin , String[] columnName,
			Class<? extends BaseModel> pojoClass, String alias,boolean joinTable){
		DetachedCriteria criteria = DetachedCriteria.forClass(pojoClass,alias);
		pagin.setDetachedCriteria(criteria);
		pagin.setSelectedColumns(columnName);
		pagin.setPojoClass(pojoClass);
		pagin.setAlias(alias);
		pagin.setJoinTable(joinTable);
		return criteria;
	}
	
	
	


	public final <T extends BaseModel> List<T> findByIds(List<String> ids,Class<T> clazz,String[] clumns){
		DetachedCriteria criteria = getDetachedCriteriaByColumn(clumns, clazz, "_model");
		criteria.add(Restrictions.in("_model.id", ids));
		return SysContext.anyDao.findByDetachedCriteria(criteria);
	}

	public boolean isHaveMarketId(String marketId) {
		
		
		return false;
	}
    /**
     * 通过放射机制来实现bean类给持久类的复制操作
     * @param form 源类对象
     * @param to 目标类对象
     * @param exceptParamsSet 指定不需要进行复制的属性集合
     * @return t Object
     * @author Alex 2015-10-25
     */
	public <M,N> N assign(M from,N to,String[] exceptParamArra) {
		ReflexUtil<M,N> ad_reflexUtil=new ReflexUtil<M,N>();
		try{
			to=ad_reflexUtil.dataExchange(from, to,exceptParamArra);
		}catch(Exception e){
			addLog("assign","error","10001","system","对象"+from.toString()+">>>给对象"+to.toString()+"赋值的时候出现异常！","AbstractBaseService.assign", e);
			//e.printStackTrace();
		}
		return to;
	}
	/* 通用查询条件注入方法
	 * @author Alex 2015-10-28
	 * @return DetachedCriteria
	 * */
   public <M> DetachedCriteria  setCriterial(M obj,DetachedCriteria criteria){
	   if(obj==null){
		  return criteria; 
	   }
		CriteriaUtil<M> criteriaUtil=new CriteriaUtil<M>();
		try {
			criteria=criteriaUtil.setCriteria(obj, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addLog("setCriterial","error","10001","system","搜索模块设置广告的时候出错:"+obj.getClass().getName(),"AbstractBaseService.setCriterial", null);
			//e.printStackTrace();
		}
		return criteria;
   }
	/* 判定用户是否有修改数据的权限
	 * @param objId 对象id
	 * @pram pojo 要修改的对象
	 * @author Alex
	 * */
 /*	public   HashMap<String,Object>   isActionAuthored(String objId,Class<?> pojo,AbstractBaseDAO dao){
	    HashMap<String,Object> returnMap=new HashMap<String,Object>();
 		if(StringUtil.isEmpty(objId)||pojo==null||dao==null){
			returnMap.put("succeed", "0");
			returnMap.put("msg", "参数错误！");
			return returnMap;
 		}

		String userId=SysContext.getUser().getId();
		String userName=SysContext.getUser().getUsername();
		String parentId= SysContext.getUser().getParentId();
		if(parentId==null||parentId=="0"||"".equals(parentId)){
			parentId=userId;
		}//如果是子账户操作，将该数据归属于父账户
		DetachedCriteria criteria_op = DetachedCriteria.forClass(pojo);
		criteria_op.add(Restrictions.eq("id", objId));
		criteria_op.add(Restrictions.eq("userId",parentId ));
		List<Object> list=dao.findByDetachedCriteria(criteria_op);
		if(CollectionUtils.isNotEmpty(list)||(Constants.MANAGER_CHECK_TYPE.equals(SysContext.getManagerType(userName).get("type")))){//如果是管理员的话也可以放行
			returnMap.put("succeed", "1");
			return returnMap;
		}else{
			addLog("assign","error",StringUtil.isEmpty(objId)?"10001":objId,pojo.getName(),"用户非法访问("+userId+"试图访问id为"+objId+"的"+pojo.getName()+")","AbstractBaseService.isActionAuthored", null);
			returnMap.put("succeed", "0");
			returnMap.put("msg", pojo.getName()+":非常抱歉，您没有权限查看/修改该信息的权利！");
			return returnMap;
		}
	}*/
	/* 判定用户是否有修改数据的权限
	 * @param param 查询的条件key为查询字段名，value为条件值
	 * @pram pojo 要修改的对象
	 * @author Alex
	 * */
	public  void deleteBaseFile(Class<?> pojo,AbstractBaseDAO dao,Map<String,String> param){
		DetachedCriteria criteria = DetachedCriteria.forClass(pojo);
		for(Map.Entry<String, String> entry:param.entrySet()){
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		List<Object> list=dao.findByDetachedCriteria(criteria);
		if(CollectionUtils.isNotEmpty(list)){
			dao.delete(list);
		}
		
	}
    /* 判断是否为数值类型
     * @since 2015-12-1
     * @Author Alex
     * @param str字符串
     * */
	private boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	 } 
    /* @since 2015-12-1
     * @Author Alex
     * @param logCode日志类型(如方法名),logType 日志类型(info,warning,error),referId 参考主键,referTable参考表,msg 消息，e异常
     * */
    public void  addLog(String logCode,String logType,String referId,String referTable,String msg,String methodName,Exception e){
/*		String userId=SysContext.getUser().getId();
		String parentId= SysContext.getUser().getParentId();
		if(parentId==null||parentId=="0"||"".equals(parentId)){
			parentId=userId;
		}*///如果是子账户操作，将该数据归属于父账户
    	LogDetail log=new LogDetail();
    	log.setLogCode(truncatStr(logCode,200,"system"));//log代码
    	log.setUserId("1000000011");//用户id
    	log.setLogType(truncatStr(logType,100,"error"));//log类型
    	log.setReferId(truncatStr(referId,100,"10001"));//参考id
    	log.setMsg(truncatStr(msg,5000,"system"));//异常简介
    	log.setMsgDetail(truncatStr(getExMessage(e),10000,"system"));//异常详细信息
    	log.setReferTable(truncatStr(referTable,200,"system"));//参考表
    	log.setMethodName(truncatStr(methodName,100,"systemMethod"));//方法
    	ServiceFacade.getLogService().addLog(log);
    }
    /* truncate the string to the required length 
     * @since 2015-1-7
     * @Author Alex
     * @param str    -string value
     * @param length -required length
     * @defaultStr   -default value
     * */
    private String truncatStr(String str,int length,String defaultStr){
    	if(StringUtil.isEmpty(str)){//如果参考主键为空这只一个默认值
    		return defaultStr;
    	}
		if(str.length()>length){//如果参考主键超过数据库主键长度，截取前length个字符
			return str.substring(0,length-1);
		}else{
			return str;
		}
    }
    /* get the exception information from the exception stack 
     * @since 2015-1-7
     * @Author Alex
     * @param e    -exception object
     * */
    private String getExMessage(Exception e){
    	   if(e==null){
    		   return "";
    	   }
    	   StringBuffer buff=new StringBuffer();
    	   StackTraceElement [] messages=e.getStackTrace();
    	   int length=messages.length;
    	   //如果异常堆栈太大，获取栈顶的前30个异常，因为只有前几个会有用
    	   //if the msg stack too much items ,then just choose the 30 front  items,because only the top ones can be helpful 
    	   if(length>30){
    		  length=30;
    	   }
    	   for(int i=0;i<length;i++){
	    		//buff.append(messages[i].getClassName()+"."+messages[i].getMethodName()+"("+messages[i].getLineNumber()+"):");
	    		buff.append(messages[i].toString()+"<br>");
    	   }
    	   return buff.toString();
    }
	/* <span>check the plan name existed or not
	 * @param name advertisement plan name
	 * @param userId  the advertiser id
	 * @author Alex
	 * @Date 2016-2-13
	 * */
	public HashMap<String,Object>  checkPlanName(Class<?> pojo,Map<String,String> nameMap,AbstractBaseDAO dao,String tips){
		HashMap<String,Object> returnMap=new HashMap<String,Object>();
		DetachedCriteria criteria =DetachedCriteria.forClass(pojo);
		for(Map.Entry<String, String> en:nameMap.entrySet()){
			criteria.add(Restrictions.eq(en.getKey(), en.getValue()));
		}
		List<Object> list=dao.findByDetachedCriteria(criteria);
		if(CollectionUtils.isNotEmpty(list)){
			returnMap.put("succeed", "0");
			returnMap.put("msg", tips);
		}else{
			returnMap.put("succeed", "1");
		}
		return returnMap;
	}	  
	
}
