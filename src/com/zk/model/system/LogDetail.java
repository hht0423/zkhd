package com.zk.model.system;

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

/***
 * 详细日志记录表
 * @since 2015-21-1;
 * 
 */
@Entity
@Table(name = "t_dsp_log_detail")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogDetail extends BaseModel{
	private static final long serialVersionUID = 3416273957859065145L;
    /***
     * 应用ID,主键
     */
	@Id
	@Column(name = "log_id")
	@GeneratedValue(generator = "logGenerate", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "logGenerate", strategy = "native")
	private String id;
	@Column(name="user_id")
	private String userId;

	@Column(name="plat_type")//平台类型 如 dsp ssp
	private String platType;
	
	@Column(name="refer_table")//参考表/实体类，如要记录那个广告的表t_dsp_ad/Advertisement
	private String referTable;
	
	@Column(name="refer_id")//参考表的主键，如广告id
	private String referId;
	
	@Column(name="log_code")//日志代码
	private String logCode;
	
	@Column(name="log_type")//日志类型，info,warn,error
	private String logType;
	
	@Column(name="msg")
	private String msg;
	
	@Column(name="status")
	private String status;//日志状态，正对erro的日志，1未处理、2邮件发出
	
	@Column(name="method_name")
	private String methodName;//异常出现的方法
	@Column(name="msg_detail")
	private String msgDetail;
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getPlatType() {
		return platType;
	}
	public String getReferTable() {
		return referTable;
	}
	public String getReferId() {
		return referId;
	}
	public String getLogCode() {
		return logCode;
	}
	public String getLogType() {
		return logType;
	}
	public String getMsg() {
		return msg;
	}
	public String getMsgDetail() {
		return msgDetail;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPlatType(String platType) {
		this.platType = platType;
	}
	public void setReferTable(String referTable) {
		this.referTable = referTable;
	}
	public void setReferId(String referId) {
		this.referId = referId;
	}
	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setMsgDetail(String msgDetail) {
		this.msgDetail = msgDetail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	


}
