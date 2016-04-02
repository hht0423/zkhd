package com.zk.act.system.service.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.inveno.util.PropertyUtils;
import com.inveno.util.StringUtil;
import com.zk.act.system.dao.hibernate.LogDao;
import com.zk.act.system.service.interfaces.LogService;
import com.zk.common.baseclass.AbstractBaseService;
import com.zk.common.util.SendMailUtil;
import com.zk.model.system.LogDetail;

public class LogServiceImpl extends AbstractBaseService implements LogService{
	protected final Log logger = LogFactory.getLog(getClass());
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    private LogDao logDAO;
    private static final String PLAT="dsp";
    private static final String INIT_STATUS="1";//初始状态，位处理
    private static final String EMAIL_SENDED="2";//邮件发送
	public LogDao getLogDAO() {
		return logDAO;
	}

	public void setLogDAO(LogDao logDAO) {
		this.logDAO = logDAO;
	}
	/*产生日志*/
	public void addLog(LogDetail log){
		if(log==null){
			return;
		}
		try{
			//String userId=SysContext.getUser().getId();
			//log.setUserId(StringUtil.isEmpty(userId)?"10002":userId);
			log.setPlatType(PLAT);
			log.setCreateTime(new Date());
			log.setStatus(INIT_STATUS);//1表示未读，2表示已读
			logDAO.save(log);
		}catch(Exception e){
			logger.error("异常日志创建失败");
		}

	}

	public String sendLogEmail(){
		System.out.println(new Date()+"------------------------------------日志任务------------------------");
		String flag=sendLogEmail_thisWeek();
		if("0".equals(flag)){
			return sendLogEmail_lastWeek();
		}
		System.out.println(new Date()+"------------------------------------end 日志任务------------------------");
		return "1";
	}
	/*通过邮件的方式获取后台log信息--获取上周的日志*/
	public String sendLogEmail_lastWeek( ) {
		String mailContent = SendMailUtil.getMailTemplate(3);	
		StringBuffer buff=new StringBuffer();
		//获取日志信息
		DetachedCriteria criteria= DetachedCriteria.forClass(LogDetail.class);
		criteria.add(Restrictions.eq("logType","error"));
		criteria.add(Restrictions.eq("platType",PLAT));
		criteria.add(Restrictions.eq("status",INIT_STATUS));
        Calendar cal = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
        try {
            cal.add(Calendar.WEEK_OF_MONTH, -1); //获取上周日期
            cal.set(Calendar.DAY_OF_WEEK, 2);//上周一
			criteria.add(Restrictions.ge("createTime",format.parse(format.format(cal.getTime()))));
	        cal2.set(Calendar.DAY_OF_WEEK, 1);        //获取上周天日期
	        criteria.add(Restrictions.le("createTime",format.parse(format.format(cal2.getTime()))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return "0";
		}
		List<LogDetail> logList = logDAO.findByDetachedCriteria(criteria);
		if(CollectionUtils.isEmpty(logList)){//如果紧两周都没有日志，就不发送
			return "0";
		}
		for(Iterator<LogDetail> it=logList.iterator();it.hasNext();){
			LogDetail log=it.next();
			log.setStatus(EMAIL_SENDED);
			logDAO.update(log);
			buff.append("["+log.getCreateTime()+":"+log.getLogCode()+"]"+" "+log.getLogType()+" ("+log.getMsg()+")   <"+log.getReferId()+","+log.getReferTable()+">  "+log.getMsgDetail()+"</br>");
		}
		if(buff.length()==0){
			return "0";
		}
		mailContent = mailContent.replaceAll("logInfo", buff.toString());
		//发送邮件到用户邮箱	
		String emailList=PropertyUtils.getProperty("log.email");
		if(StringUtils.isNotEmpty(emailList)){
			for(String email:emailList.split(",")){
				SendMailUtil.assembleMailAndSend("DSP开放平台log信息",mailContent,email);
			}

		}
		return "1";
	}
	/*获取本周的日志*/
	public String sendLogEmail_thisWeek(){
		String mailContent = SendMailUtil.getMailTemplate(3);	
		StringBuffer buff=new StringBuffer();
		//获取日志信息
		DetachedCriteria criteria= DetachedCriteria.forClass(LogDetail.class);
		criteria.add(Restrictions.eq("logType","error"));
		criteria.add(Restrictions.eq("platType","dsp"));
		criteria.add(Restrictions.eq("status",INIT_STATUS));
        Calendar cal = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
        try {
             cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一时间
	       	 if(format.format(cal.getTime()).equals(format.format(cal2.getTime()))){//今天为本周的第一天
	       		 criteria.add(Restrictions.ge("createTime",format.parse(format.format(cal.getTime()))));
	       	 }else{
	           	 //获取当天第二天时间
	 	         cal.add(Calendar.DAY_OF_MONTH, 1);       
	           	 criteria.add(Restrictions.le("createTime",format.parse(format.format(cal.getTime()))));
	           	 criteria.add(Restrictions.ge("createTime",format.parse(format.format(cal2.getTime()))));
	       	 }
             
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return "0";
		}
		List<LogDetail> logList = logDAO.findByDetachedCriteria(criteria);
		if(CollectionUtils.isEmpty(logList)){//如果紧两周都没有日志，就不发送
			return "0";
		}
		for(Iterator<LogDetail> it=logList.iterator();it.hasNext();){
			LogDetail log=it.next();
			log.setStatus(EMAIL_SENDED);
			logDAO.update(log);
			buff.append("["+log.getCreateTime()+":"+log.getLogCode()+"]"+" "+log.getLogType()+" ("+log.getMsg()+")   <"+log.getReferId()+","+log.getReferTable()+">  "+log.getMsgDetail()+"</br>");
		}
		if(buff.length()==0){
			return "0";
		}
		mailContent = mailContent.replaceAll("logInfo", buff.toString());
		//发送邮件到用户邮箱	
		String emailList=PropertyUtils.getProperty("log.email");
		if(StringUtils.isNotEmpty(emailList)){
			for(String email:emailList.split(",")){
				SendMailUtil.assembleMailAndSend("DSP开放平台log信息",mailContent,email);
			}

		}
		return "1";
	}
}
