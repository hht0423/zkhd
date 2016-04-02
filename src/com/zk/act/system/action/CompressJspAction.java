package com.zk.act.system.action;

import java.io.File;

import com.inveno.util.FileUtil;
import com.zk.act.system.dto.JspBean;
import com.zk.common.baseclass.BaseAction;

/**
 * 压缩jsp页面
 * @date 2011-10-28
 */
public class CompressJspAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JspBean jspBean;
	
	/**
	 * 压缩jsp页面
	 * @return
	 * @date  2011-10-28
	 */
	public String compressJsp(){
		try {
			
			File src = jspBean.getJspPage();
			FileUtil.UnZip(src);
			
			jspBean.setMessage("处理jsp页面成功，文件解压在d:/zipout/");
		} catch (Exception e) {
			e.printStackTrace();
			jspBean.setMessage(e.getMessage());
		}
		return SUCCESS;
	}

	public JspBean getJspBean() {
		return jspBean;
	}

	public void setJspBean(JspBean jspBean) {
		this.jspBean = jspBean;
	}
	
	
}
