package com.zk.common.util;

/**
 * 
 * 系统常量定义接口
 * 此类中变量默认为 public final static 类型
 * 2011-1-12
 * @author yaoyuan
 *
 */
public interface Constants{
	
	/**定义读取属属性文件名�?/
	String PROPERTIES_NAME="/pmsConfig.properties";
	
	/**字符集转�?/
    String CHAR_SET="ISO-8859-1";
    
    String UTF_CHAR_SET="UTF-8";
    
    String GBK_CHAR_SET = "GBK";
    
    /**校验码图片宽�?/
	int PICTURE_WIDTH = 60;
	
	 /**校验码图片高�?/
	int PICTURE_HEIGHT = 20;
	
	int PICTURE_SIZE = 300 ;
	
	String PICTURE_MSG ="图片大小不能超过300kb";
	
	/** 验证�?*/
	String CHECK_CODE = "checkCode";
	
	String SSO_PATH = "/sso";
	
	/** SSO登录cookie key */
	String ADMIN_LOGIN_KEY = "adminLoginKeySSO";
	
	/**合作厂商账户登录*/
	String FIRM_LOGIN_KEY = "adminLoginKeyFirmAdPms";
	
	/** 商户登陆 cookie key */
	String MERCHANT_LOGIN_KEY="merchant_login_key";
	
	String PMS_USER_TYPE = "pmsUserType";
	
	String PMS_USER_ID="pmsUserId";
	
	String MARKET_ID="marketId";
	
	int AD_TYPE_INFO = 1;
	
	int AD_TYPE_RSS = 2;
	
	int AD_TYPE_PD = 3;
	
	
	/**得到合作厂商的权限菜�?/
	String FIRM_PERMISSION_LIST="firmpermissionList";
	
	/**前后台cookie登陆保存30分钟,30分钟�?0�?系统�?�?memcache过期后就从db find出用�?再放入memcache�?/
	int LOING_TIME_EXPIRY = 24*60*60  ;
	
	/**数据缓存key*/
	String DICTIONRY_DD_CACHE="dictionaryDd_adpms";
	
	/**数据词典 map 缓存*/
	String DICTIONRY_DD_MAP_CACHE="dictionaryDdMap_adpms";
	
	String CATEGORY_CACHE="categoryCache_pf";
	
	String TOPPRODUCTLIST_CACHE="topproductCache_pf";
	
	/**管理员名�?/
	String ADMIN="admin";
	
	/**系统菜单list key名字*/
	String PERMISSION_LIST="permissionList";
	
	String ORIGIN="origin";
	
	/**账户类型*/
	String CITY_ACCOUNT="1";
	String AREA_ACCOUNT="2";
	String OFFICE_ACCOUNT="3";
	String PERSION_ACCOUNT="4";
	
	/**单击查询按钮类型*/
	String BUTTON_ANY="1";
	String BUTTON_TODAY="2";
	String BUTTON_WEEK="3";
	String BUTTON_MONTH="4";
	
	/**mta数据库连�?(xjr) */
	String DB_PROPERTIES_NAME="/db-connection.properties";
	
	/**省份城市缓存key�?wangdesheng 2013-01-18*/
	String PROVINCE_CITY_CACHE="province_city_cache_pms";
	
	/**系统（内部）账户的firmId*/
	String SYS_USER_FIRMID="118";
	
	/** 广告操作类型 1-创建�?-审核�?-编辑�?-设置策略�?-设置权重�?-上线�?-下线   xjr20140603*/
	int OPERADTYPE1=1;
	int OPERADTYPE2=2;
	int OPERADTYPE3=3;
	int OPERADTYPE4=4;
	int OPERADTYPE5=5;
	int OPERADTYPE6=6;
	int OPERADTYPE7=7;
	
	/** 广告下线状�?更新 **/
	
	String MARKET_OFFLINE = "updateMarketSetStateOffline";
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<开放平台相关定义  hht 2015-8-13>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/** token */
	String INVENOTOKEN="invenoToken";
	String PLAT_ID = "1";
	//开放平台主页面
	String OPENPLAT_MAIN_URL="openPlatMainUrl";
	//开放平台错误提示页
	String OPENPLAT_404ERROR_URL="openPlat404ErrorUrl";
	String LOGIN_TYPE="loginType";
	String OPENPLAT_INDEX_URL="openplatIndexUrl";
	//######################SDK下载
	//数据统计SDK下载文件名
	String OPENPLAT_ANDROID_PLAT="1";//应用平台为android
	String OPENPLAT_IOS_PLAT="2";//应用平台为IOS
	
	String OPENPLAT_DATASTAT_SDK="1";//数据统计sdk
	String OPENPLAT_CONTENT_SDK="2";//内容统计sdk下载
	String OPENPLAT_CONTENT_AND_DATASTAT_SDK="12";//(数据,内容统计)sdk下载
	
	String OPENPLAT_ANDROID_DATASTAT_FILE_NAME="androidDataStatFileName";//(android,数据统计sdk)的文件名称
    String OPENPLAT_ANDROID_CONTENT_FILE_NAME="androidContentStatFileName";//(android,内容统计sdk)的文件名称 
    String OPENPLAT_ANDROID_CONTENT_AND_DATASTAT_FILE_NAME="androidContentAndDataStatFileName";//(android,内容和数据统计sdk)的文件名称 
    
	String OPENPLAT_IOS_DATASTAT_FILE_NAME="iosDataStatFileName";//(ios,数据统计sdk)的文件名称
    String OPENPLAT_IOS_CONTENT_FILE_NAME="iosContentStatFileName";//(ios,内容统计sdk)的文件名称 
    String OPENPLAT_IOS_CONTENT_AND_DATASTAT_FILE_NAME="iosContentAndDataStatFileName";//(ios,内容和数据统计sdk)的文件名称 
    
    //
    //#######################
	//SDK下载路径
	String DOWNLOAD_PATH="downloadPath";
	//三方平台登陆用户id
	String ZK_USER="zkUser";
	String INVENO_USER = "invenoUser";
	//没有权限提示页面
	String OPENPLAT_NOAUTH_ERROR_URL="openPlatNoAuthErrorUrl";
	/**私钥*/
	String AUTHCODE_KEY = "inveno";
	/*接口调用，返回参数定义*/
	//接口调用成功标志
	String RPC_SUCCESS_FLAG_KEY="success";
	String RPC_SUCCESS_VALUE="1";
	//接口调用失败标志
	String RPC_ERROR_VALUE="0";
	//接口调用失败标志
	String RPC_ERROR_MESSAGE="msg";
	//接口数据返回参数
    String RPC_RETURN_DATA="rpcData";
    //方法调用异常代码
    String FUNCTION_OPENPLAT_ERROR_CODE="-1";
    //用户登陆成功后跳转的路径
    String REDIRECT_URL="redirect_uri";
	//###############################
	String SDK_DOWNLOAD_EXCEPTION="sdk下载异常";
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<end 开放平台>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<end 开放平台>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//-------------不同文件的代码-------------
	String AD_CREAT_IMG1="adCreatImg1";
	String AD_CREAT_IMG2="adCreatImg2";
	String AD_CREAT_IMG3="adCreatImg3";
	//------------管理员信息的定义---------------
	//审核管理员列表
	String ADMIN_MANAGER_USER_LIST="admin.manager.user.list";

	String ADMIN_MANAGER_USER_PAGE="admin.manager.user.page";
	//审核管理员页面
			//admin.check.manager.user.list=admin
	String	ADMIN_CHECK_MANAGER_USER_PAGE="admin.check.manager.user.page";
	
	
	
	//the type to manage check business
	String MANAGER_CHECK_TYPE="check";
	
	//------------dsp platform business check type id-----------
	String DSP_BUSINESS_TYPE_LIST="dsp.business.type.list";
}
