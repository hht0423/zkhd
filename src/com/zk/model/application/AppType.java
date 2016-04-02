package com.zk.model.application;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.inveno.base.BaseModel;
/**
  * @Description:app type persistence class
  * @File AppType.java
  * @Date 2015年12月15日
  * @See AppType
  * @Version V1.0
  */
@Entity
@Table(name="t_app_type")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class AppType extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2950442408895190081L;
	//应用id
    /***
     * 应用类型ID,主键
     */
	@Id
	@GeneratedValue(generator = "appTypeGenerate", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "appTypeGenerate", strategy = "native")
	@Column(name="app_type_id")
	private String Id;
	//应用类型
	@Column(name="app_type")
	private String appType;
	//应用描述
	@Column(name="app_desc")
	private String appDesc;
	//创建时间
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	public String getId() {
		return Id;
	}
	public String getAppType() {
		return appType;
	}
	public String getAppDesc() {
		return appDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
