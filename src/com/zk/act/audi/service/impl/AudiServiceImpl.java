package com.zk.act.audi.service.impl;

import java.util.Date;
import java.util.HashMap;

import com.zk.act.audi.dao.hibernate.AudiDao;
import com.zk.act.audi.dto.ActBean;
import com.zk.act.audi.service.interfaces.AudiService;
import com.zk.common.baseclass.AbstractBaseService;
import com.zk.model.act.Activity;
import com.zk.model.act.ActivityDetail;

public class AudiServiceImpl extends AbstractBaseService  implements AudiService{
    private AudiDao audiDao;
	@Override
	public HashMap<String, Object> actRegister(ActBean actBean) {
		HashMap<String,Object> returnMap=new HashMap<String,Object>();
		// TODO Auto-generated method stub
		Activity act=actBean.getActivity();
		act.setCreateTime(new Date());
		act.setActCode("AUDI_DRIVE");//奥迪试驾
		act.setActType("1");//注册类型
		act.setActDesc("奥迪A3试驾活动");
		audiDao.save(act);
		
		ActivityDetail actDetail=actBean.getActDetail();
		actDetail.setActId(act.getId());
		actDetail.setCreateTime(new Date());
		audiDao.save(actDetail);
		returnMap.put("succeed", "1");
		return returnMap;
		
	}
	public AudiDao getAudiDao() {
		return audiDao;
	}
	public void setAudiDao(AudiDao audiDao) {
		this.audiDao = audiDao;
	}


}
