package com.zk.act.system.dto;

import java.util.List;

import com.zk.common.baseclass.BaseFormBean;
import com.zk.model.system.Dd;
public class DdBean extends BaseFormBean{
	
	private String typeName;
	
	private String parentName;
	
	private String code;
	
	private Dd dd;
	
	private List<Dd> parentIdList;

	public Dd getDd() {
		return dd;
	}

	public void setDd(Dd dd) {
		this.dd = dd;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Dd> getParentIdList() {
		return parentIdList;
	}

	public void setParentIdList(List<Dd> parentIdList) {
		this.parentIdList = parentIdList;
	}	
	
}
