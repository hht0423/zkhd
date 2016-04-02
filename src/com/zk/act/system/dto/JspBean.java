package com.zk.act.system.dto;

import java.io.File;

import com.zk.common.baseclass.BaseFormBean;

public class JspBean extends BaseFormBean{

	/**jsp页面*/
	private File jspPage;
	private String jspPageContentType;
	private String jspPageFileName;
	
	private String message;
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public File getJspPage() {
		return jspPage;
	}
	public void setJspPage(File jspPage) {
		this.jspPage = jspPage;
	}
	public String getJspPageContentType() {
		return jspPageContentType;
	}
	public void setJspPageContentType(String jspPageContentType) {
		this.jspPageContentType = jspPageContentType;
	}
	public String getJspPageFileName() {
		return jspPageFileName;
	}
	public void setJspPageFileName(String jspPageFileName) {
		this.jspPageFileName = jspPageFileName;
	}
	
	
}
