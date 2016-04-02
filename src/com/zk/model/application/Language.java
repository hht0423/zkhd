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
  * @Description:app language persistence class
  * @File Language.java
  * @Date 2015年12月15日
  * @See Language
  * @Version V1.0
  */
@Entity
@Table(name = "t_language")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Language extends BaseModel{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 5863554064880561596L;
	//语言id
    /***
     * 应用类型ID,主键
     */
	@Id
	@GeneratedValue(generator = "langGenerate", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "langGenerate", strategy = "native")
	@Column(name="lang_id")
	private String id;
	//语言代码
	@Column(name="lang_code")
	private String langCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	//语言名称
	@Column(name="lang_name")
	private String langName;
	//创建时间
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	public String getLangCode() {
		return langCode;
	}
	public String getLangName() {
		return langName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public void setLangName(String langName) {
		this.langName = langName;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
