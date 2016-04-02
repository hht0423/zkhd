package com.zk.act.system.service.interfaces;

import com.zk.model.system.LogDetail;

public interface  LogService {
	/*产生日志*/
	public void addLog(LogDetail log);
	/*通过邮件的方式获取后台log信息--这周的log信息*/
	public String sendLogEmail_thisWeek();
	/*通过邮件的方式获取后台log信息--上周的log信息*/
	public String sendLogEmail_lastWeek();
	public String sendLogEmail();
}
