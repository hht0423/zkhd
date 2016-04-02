package com.zk.act.audi.action;

import com.zk.act.audi.dto.ActBean;
import com.zk.act.audi.service.interfaces.AudiService;
import com.zk.common.baseclass.BaseAction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inveno.util.JsonUtil;
import com.opensymphony.oscache.util.StringUtil;
import com.opensymphony.xwork2.ModelDriven;

public class AudiAction extends BaseAction  implements ModelDriven<Object>{
   private ActBean audiBean;
   private AudiService audiService;
   private SimpleDateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 54146461878494437L;
	public void actRegist(){
		HashMap<String,Object> returnMap=new HashMap<String,Object>();
		if(this.getModel().getActivity()==null||this.getModel().getActDetail()==null){
			returnMap.put("succeed", "0");
		    returnMap.put("rCode", "-1");
		    returnMap.put("msg", "参数错误！");
    		this.writeToClient(JsonUtil.getJsonStrByConfigFromMap(returnMap));
    		return;
		}
		logger.info(format.format(new Date())+"|register|"+convert(this.getModel().getActivity().getActCode())+"|"+convert(this.getModel().getActivity().getUserName())+"|"+convert(this.getModel().getActivity().getTelNo()));
		returnMap=audiService.actRegister(getModel());
		this.writeToClient(JsonUtil.getJsonStrByConfigFromMap(returnMap));
	}
	private String convert(String str){
		if(StringUtil.isEmpty(str)){
			return "";
		}
		return str;
	}
	@Override
	public ActBean getModel() {
		// TODO Auto-generated method stub
		if (null == audiBean){
			audiBean = new ActBean();
		}
		this.audiBean.getPagin().setToPage(this.getPage());
		this.audiBean.getPagin().setPageSize(this.getRows());
		this.audiBean.getPagin().setKey(this.getQ());
		return audiBean;
	}

	public ActBean getAudiBean() {
		return audiBean;
	}

	public void setAudiBean(ActBean audiBean) {
		this.audiBean = audiBean;
	}

	public AudiService getAudiService() {
		return audiService;
	}

	public void setAudiService(AudiService audiService) {
		this.audiService = audiService;
	}

}
