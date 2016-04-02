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
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.inveno.base.BaseModel;
import com.zk.common.searchUtil.annotation.CriteriaType;
import com.zk.common.searchUtil.annotation.DataType;
import com.zk.common.searchUtil.annotation.DateSearchField;
import com.zk.common.searchUtil.annotation.SearchField;
/**
  * @Description:系统用户实体类
  * @File Application.java
  * @Date 2015年12月15日
  * @See Application
  * @Version V1.0
  */
@Entity
@Table(name = "t_dsp_app")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Application extends BaseModel{
	private static final long serialVersionUID = 9188255730515138162L;
    /***
     * 应用ID,主键
     */
	@Id
	@Column(name = "app_id")
	@GeneratedValue(generator = "applicationGenerate", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "applicationGenerate", strategy = "native")
	private String id;
	//用户id
	@Column(name="user_id")
	private String userId;
	//应用名称
	@Column(name="app_name")
	private String appName;
	//应用包名
	@Column(name="app_package_name")
	private String appPackageName;
	//应用版本
	@Column(name="app_version")
	private String appVersion;
	//应用类型
	@Column(name="app_type")
	@SearchField(paramName="appType",criteriaType=CriteriaType.EQ)
	private String appType;
	//平台类型
	@Column(name="plat_type")
	private String platType;//1 Android  2 IOS 3 windows 4 Android wear 5 tencent os 6 yun os for wear）
	@Column(name="status")
	@SearchField(paramName="status",criteriaType=CriteriaType.EQ)
	private String status;//审核状态  1-待审核 ；2-审核通过；3-审核未通过

	//应用描述
	@Column(name="app_desc")
	private String appDesc;
	@Column(name="app_apk_file_code")
	private String appApkFileCode;//apk存储路径----参照t_dsp_file
	@Column(name="app_icon_file_code")
	private String appIconFileCode;//app上传图标文件代码----参照t_dsp_file
	@Column(name="app_screenshot_file_code")
	private String appScreenshotFileCode;//应用截图文件代码（多个图片以分号隔开）----参照t_dsp_file
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;//创建时间
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;//修改时间
	@Column(name = "operator")
	private String operator;//操作人
	@Column(name="no_pass_reason")
	private String noPassReason;
	//标识应用是否有效
	@Column(name="is_valid")
	private String isValid;
	//标识应用是否有效
	@Column(name="download_type")
	private String downloadType;//应用下载类型，1-inveno下载地址，2-三方应用下载地址
	
	//----------非映射字段变量定义-----------
	@Transient
	private String appIconFilePath;
	@Transient
	private String screenshotFile1Path;//应用截图
	@Transient
	private String screenshotFile2Path;
	@Transient
	private String screenshotFile3Path;
	@Transient
	private String apkFilePath;
	@Transient
	private String appTypeName;
	@Transient
	private String appIcon;
	@Transient
	private String screenshotFiles;
	
	/*-------------列表上方的查询组件设置------------------*/
    // public enum Type{ 1.TODAY,2.YESTODAY,3.THISWEEK,4.LASTWEEK,5.THISMONTH};//枚举值分别代表：今天，昨天，这周，上周，本月
	@DateSearchField(paramName="createTime")
	@Transient
	private String searchDateType;
	
	
	//自定义开始时间
	@SearchField(paramName="createTime",dataType=DataType.DATE,criteriaType=CriteriaType.GE)
	@Transient
	private String searchDateStart;
	
	
	//自定义结束时间
	@SearchField(paramName="createTime",dataType=DataType.DATE,criteriaType=CriteriaType.LE)
	@Transient
    private String searchDateEnd;
	
	
	//模糊查询字段
	@SearchField(paramNames={"appName","appPackageName"},multiple=true,criteriaType=CriteriaType.ANY_LIKE)//用于模糊查询
	@Transient
    private String fuzzySearch;
	/*-------------列表上方的查询组件设置------------------*/
	
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getAppName() {
		return appName;
	}
	public String getAppPackageName() {
		return appPackageName;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public String getAppDesc() {
		return appDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public void setAppPackageName(String appPackageName) {
		this.appPackageName = appPackageName;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAppType() {
		return appType;
	}
	public String getAppApkFileCode() {
		return appApkFileCode;
	}
	public String getAppIconFileCode() {
		return appIconFileCode;
	}
	public String getAppScreenshotFileCode() {
		return appScreenshotFileCode;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public String getScreenshotFile1Path() {
		return screenshotFile1Path;
	}
	public String getScreenshotFile2Path() {
		return screenshotFile2Path;
	}
	public String getScreenshotFile3Path() {
		return screenshotFile3Path;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public void setAppApkFileCode(String appApkFileCode) {
		this.appApkFileCode = appApkFileCode;
	}
	public void setAppIconFileCode(String appIconFileCode) {
		this.appIconFileCode = appIconFileCode;
	}
	public void setAppScreenshotFileCode(String appScreenshotFileCode) {
		this.appScreenshotFileCode = appScreenshotFileCode;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setScreenshotFile1Path(String screenshotFile1Path) {
		this.screenshotFile1Path = screenshotFile1Path;
	}
	public void setScreenshotFile2Path(String screenshotFile2Path) {
		this.screenshotFile2Path = screenshotFile2Path;
	}
	public void setScreenshotFile3Path(String screenshotFile3Path) {
		this.screenshotFile3Path = screenshotFile3Path;
	}
	public String getAppIconFilePath() {
		return appIconFilePath;
	}
	public String getApkFilePath() {
		return apkFilePath;
	}
	public void setAppIconFilePath(String appIconFilePath) {
		this.appIconFilePath = appIconFilePath;
	}
	public void setApkFilePath(String apkFilePath) {
		this.apkFilePath = apkFilePath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNoPassReason() {
		return noPassReason;
	}
	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}
	public String getAppTypeName() {
		return appTypeName;
	}
	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}
	
	public String getPlatType() {
		return platType;
	}
	public void setPlatType(String platType) {
		this.platType = platType;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getSearchDateType() {
		return searchDateType;
	}
	public void setSearchDateType(String searchDateType) {
		this.searchDateType = searchDateType;
	}
	public String getSearchDateStart() {
		return searchDateStart;
	}
	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}
	public String getSearchDateEnd() {
		return searchDateEnd;
	}
	public void setSearchDateEnd(String searchDateEnd) {
		this.searchDateEnd = searchDateEnd;
	}
	public String getFuzzySearch() {
		return fuzzySearch;
	}
	public void setFuzzySearch(String fuzzySearch) {
		this.fuzzySearch = fuzzySearch;
	}
	public String getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}
	public String getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}
	public String getScreenshotFiles() {
		return screenshotFiles;
	}
	public void setScreenshotFiles(String screenshotFiles) {
		this.screenshotFiles = screenshotFiles;
	}
}
