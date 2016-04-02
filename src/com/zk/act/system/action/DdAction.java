package com.zk.act.system.action;

import java.io.IOException;

import com.zk.act.system.dto.DdBean;
import com.zk.act.system.service.interfaces.DdService;
import com.zk.common.baseclass.BaseAction;
import com.zk.model.system.Dd;
/***
 * 数据词典Action�?
 * @author new
 *
 */
@SuppressWarnings("serial")
public class DdAction extends BaseAction{
    
	DdService ddService;
	
	DdBean ddBean;
	
	/**
	 * 进入查询数据词典页面
	 * 
	 * @return
	 */
	public String findDdPage(){
		Dd dd=ddService.findOnlineStatById();
		getDdBean().setDd(dd);
		return SUCCESS;
	}
	
	/**
	 * 查询�?有父结点信息
	 * 2011-3-10
	 * @return
	 */
	public String findDdList(){
		ddService.noTranLoadConfig(getDdBean());
		return SUCCESS;
	}
	
	/**
	 * 查询到所要修改的词典信息，显示到修改词典信息页面
	 * 2011-3-10
	 * @return
	 */
	public String findDdById(){
		ddService.findDdById(getDdBean());
		return SUCCESS;
	}
	
	/**
	 * 进入修改数据词典信息页面
	 * @return
	 */
	public String toDdUpdate(){
		findDdPage();
		return SUCCESS;
	}
	
	/**
	 * 添加数据词典信息
	 * 2011-3-11
	 * @return
	 */
	public String saveDdDetails(){
		ddService.saveDdDetails(getDdBean());
		return SUCCESS;
	}
	
	/**
	 * 修改数据词典
	 * 2011-3-11
	 * @return
	 */
	public String updateDd() {
		//保存修改后的词典信息,修改的数据是否正确，如果修改的数据父ID、代号与类型名字都与词典中的记录�?样时，返回提示信�?
		if(ddService.updateDb(getDdBean())){
			return SUCCESS;
		}else{
			saveMessage("父Id与类型名字不能完全一�?!");
			return ERROR;
		}
		
	}
	
	/**
	 * 修改父类型名�?
	 * 2011-3-31
	 * @return
	 * @throws IOException
	 */
	public String updateParentMemoAction() throws IOException{
		try{
			if(!ddService.updateMome(getDdBean())){
				sendClient("修改父类名称失败!可能输入的名字已经存�?!");
			}
		}catch(Exception ex){
			sendClient("修改父类名称失败!");
		}
		return NONE;
	}

	public DdService getDdService() {
		return ddService;
	}

	public void setDdService(DdService ddService) {
		this.ddService = ddService;
	}

	public DdBean getDdBean() {
		if(null==ddBean){
			ddBean=new DdBean();
		}
		return ddBean;
	}

	public void setDdBean(DdBean ddBean) {
		this.ddBean = ddBean;
	}
	
}
