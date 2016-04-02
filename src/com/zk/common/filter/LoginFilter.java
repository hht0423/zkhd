package com.zk.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.inveno.util.CollectionUtils;
import com.inveno.util.JsonUtil;
import com.inveno.util.StringUtil;
//import com.zk.admin.user.service.interfaces.UserService;
//import com.zk.model.user.User;
/*防止非法用户通过路径访问来冒充三方平台登陆*/
public class LoginFilter implements Filter{
	protected final Log log = LogFactory.getLog(getClass());
	//private UserService userService;
    /** 不需要验证的路径 */
	private String[] noCheckUrl;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
	    final HttpServletRequest request = (HttpServletRequest) servletRequest;
	    final HttpServletResponse response = (HttpServletResponse) servletResponse;
	    HashMap<String,String> userMap=null;
	    Object userInfoMap=  request.getSession().getAttribute("userInfo");//先从session中取
		//判断memcached是否过期
	    if(userInfoMap!=null){
	    	userMap= (HashMap)userInfoMap;
	    }
        //访问路径
     	final String visitPath = request.getServletPath();

        //判断是否验证
        if (isNoCheckUrl(visitPath)){
        	 chain.doFilter(request, response);
             return;
        }

		if(CollectionUtils.isEmpty(userMap)||StringUtil.isEmpty(userMap.get("userId"))){//如果是未登陆，进入登陆界面
	     	System.out.println("ThirdPlatFilter（拦截路径）:"+visitPath);
			returnNoAuthenMsg(response,"非常抱歉，您还未登陆或当前会话已经结束，请刷新当前页面，重新登陆！");
	     	return;
		}else{//如果已经登陆，需要用户安全验证
/*			String userId=userMap.get("userId");
        	User user=userService.findByIdUser(userId);
        	if(user==null){
        		System.out.println("ThirdPlatFilter（拦截路径）:"+visitPath);
        		//response.sendRedirect(PropertyUtils.getProperty(Constants.DSP_NOAUTH_ERROR_URL));
        		returnNoAuthenMsg(response,"非常抱歉，您没有权限访问该地址！");
        		return;
        	}else{
         	    chain.doFilter(request, response);
         	    return;
        	} */
		} 
	}
	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = config.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
		//userService=(UserService)ac.getBean("userService");
        String urls = config.getInitParameter("noCheckUrl");
		if (urls!=null && !"".equals(urls)) {
			urls = urls.replaceAll("\\n", "").replaceAll("\\r", "").replaceAll("\\t", "");
			noCheckUrl = urls.split(",");
		}

		if (noCheckUrl != null) {
			for (int i = 0; i < noCheckUrl.length; i++) {
				noCheckUrl[i] = noCheckUrl[i].trim();
			}
		}
		
	}
	/**
	 * the method is to check out the request url need to check  or not
	 * @author Alex
	 * @since 2015-12-28
	 * */
	private boolean isNoCheckUrl(String url) {
		if (null==url || "".equals(url)){
			return false;
		}
		for (int i = 0; i < noCheckUrl.length; i++) {
			if (url.equalsIgnoreCase(noCheckUrl[i]) || url.startsWith(noCheckUrl[i])) {
				return true;
			}
		}
		return false;
	}
	private void returnNoAuthenMsg(HttpServletResponse response,String warnMsg){
    	Map<String,String> returnMap=new HashMap<String,String>();
    	returnMap.put("succeed", "0");
    	returnMap.put("rCode", "99");
    	returnMap.put("msg",warnMsg);
    	//跳转到用户资料补充页面
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(JsonUtil.getJsonStrByConfigFromObj(returnMap));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
