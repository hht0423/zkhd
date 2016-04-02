package com.zk.model.act;

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

@Entity
@Table(name = "t_zk_act")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Activity extends BaseModel{

	    /**
	 * 
	 */
	private static final long serialVersionUID = -655464726685237511L;
		/***
	     * 应用ID,主键
	     */
		@Id
		@Column(name = "act_id")
		@GeneratedValue(generator = "activityGenerate", strategy = GenerationType.IDENTITY)
		@GenericGenerator(name = "activityGenerate", strategy = "native")
		private String id;
		//
		@Column(name="act_desc")
		private String actDesc;//   活动描述
		
		@Column(name="user_name")
		private String userName;//  姓名
		
		@Column(name="gender")
		private String gender;// 性别
		
		@Column(name="tel_no")
		private String telNo;//    手机号码
		
		@Column(name="act_type")
		private String actType;//    活动类型，1-注册类型  
		
		@Column(name="act_code")
		private String actCode;//  活动代码，如AUDI_DRIVE（Audi试驾）
		
		@Column(name = "create_time")
		@Temporal(TemporalType.TIMESTAMP)
		private Date createTime;//创建时间
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getActDesc() {
			return actDesc;
		}

		public void setActDesc(String actDesc) {
			this.actDesc = actDesc;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getTelNo() {
			return telNo;
		}

		public void setTelNo(String telNo) {
			this.telNo = telNo;
		}

		public String getActType() {
			return actType;
		}

		public void setActType(String actType) {
			this.actType = actType;
		}

		public String getActCode() {
			return actCode;
		}

		public void setActCode(String actCode) {
			this.actCode = actCode;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}


}
