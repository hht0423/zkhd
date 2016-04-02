package com.zk.test.apptest;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zk.act.audi.dto.ActBean;
import com.zk.act.audi.service.interfaces.AudiService;
import com.zk.model.act.Activity;
import com.zk.model.act.ActivityDetail;


public class AudiManage {
   private ApplicationContext getApplicationContext(){
	   String xmlPath[]= new String[]{"applicationContext-resources.xml",
			   "applicationContext-service.xml",
			   "com/zk/act/system/applicationContext-system.xml",
			   "com/zk/act/system/struts-system.xml",
			   "com/zk/act/audi/applicationContext-audi.xml",
	           "com/zk/act/audi/struts-audi.xml"};
       ApplicationContext appContext = new ClassPathXmlApplicationContext(xmlPath);  
       return appContext;
   }
   @Test
   public void actRegister(){
	   ApplicationContext ac = getApplicationContext();
	   AudiService audiService=(AudiService)ac.getBean("audiService");
       Activity act=new Activity();
       act.setActCode("AUDI_DRIVE");
       act.setActDesc("奥迪试驾活动。。");
       act.setActType("1");
       act.setCreateTime(new Date());
       act.setGender("f");
       act.setTelNo("15904232222");
       act.setUserName("Alex");
       
       
       ActivityDetail actDetail=new ActivityDetail();
       actDetail.setActDetail("audi  activity detail");
       actDetail.setBookTime(new Date());
       actDetail.setCity("1");
       actDetail.setPrdModel("A3");
       actDetail.setPrdName("奥迪");
       actDetail.setUndertaker("深圳奥迪经销商");
       ActBean bean=new ActBean();
       bean.setActDetail(actDetail);
       bean.setActivity(act);
       audiService.actRegister(bean);
       
       
    
   }
}
