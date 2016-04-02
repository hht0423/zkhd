package com.zk.act.system.action;

import com.zk.act.system.service.interfaces.LogService;
import com.zk.common.baseclass.BaseAction;

public class LogAction extends BaseAction{
	private static final long serialVersionUID = -3906460901469766215L;
	LogService logService;
    public void sendLogEmail(){
    	String flag=logService.sendLogEmail();
    	if("1".equals(flag)){
    		this.writeToClient("邮件发�?�成功！");
    	}else{
    		this.writeToClient("没有异常信息�?");
    	}
    	
    }
	public LogService getLogService() {
		return logService;
	}
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
