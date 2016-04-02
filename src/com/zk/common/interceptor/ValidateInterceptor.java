package com.zk.common.interceptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;  
import java.util.Map;  
import java.util.Set;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;  
import org.apache.struts2.StrutsStatics;
import com.zk.common.waf.WafHelper;
import com.inveno.util.JsonUtil;
import com.inveno.util.PropertyUtils;
import com.opensymphony.oscache.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;  
import com.opensymphony.xwork2.interceptor.Interceptor;  
import com.opensymphony.xwork2.util.ValueStack;
  
public class ValidateInterceptor implements Interceptor {  
    private static final long serialVersionUID = -2578561479301489061L;   
    public void destroy() {  
    }  
    /*  
     * @Description:拦截所有参数,去掉参数空格 
     * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation) 
     */  
    public String intercept(ActionInvocation invocation) throws Exception {  
        Map map=invocation.getInvocationContext().getParameters();  
        Set keys = map.keySet();  
        ActionContext ac = invocation.getInvocationContext();
        ValueStack stack = ac.getValueStack();
        Iterator iters = keys.iterator();  
        while(iters.hasNext()){  
            Object key = iters.next();  
            Object value = map.get(key);
            Object[] tmpObj=(Object[])value;
            if(value instanceof  String[]){ 
            	    int len=tmpObj[0].toString().length();
            	    Map<String,String>  tmpMap=filterParamString(tmpObj[0].toString());
            	    //if(len>0&&tmpMap.get("value").trim().length()==0){//剥离掉后，为空字符
            	   // if(tmpMap.get("value").trim().length()!=len){//剥离掉后，为空字符
            	    if("0".equals(tmpMap.get("succeed"))){
                    	Map<String,String> returnMap=new HashMap<String,String>();
                    	returnMap.put("succeed", "0");
                    	returnMap.put("msg", StringUtil.isEmpty(tmpMap.get("msg"))?"非常抱歉，数据中包含不合法的字符，请验证后再操作！":tmpMap.get("msg"));
                        HttpServletResponse response=(HttpServletResponse)ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE); 
    					response.setContentType("application/json;charset=utf-8");
    					PrintWriter out = response.getWriter();
    					out.print(JsonUtil.getJsonStrByConfigFromObj(returnMap));
    					out.flush();
    					out.close();
                        return null;
            	    }else{
            	    	//将过滤的数据重新赋值
            	    	tmpObj[0]=tmpMap.get("value");
            	    	value=tmpObj;
            	        stack.setValue((String)key, value);
            	    }
            }
        }  

        return invocation.invoke();  
    }  
    /** 
     * @Description: 遍历参数数组里面的数据，取出空格 
     * @param params 
     * @return 
     */  
    private String[] transfer(String[] params){  
        for(int i=0;i<params.length;i++){  
            if(StringUtils.isEmpty(params[i]))continue;  
            params[i]=params[i].trim();  
        }  
        return params;  
    }  
	/**
	 * @Description 过滤字符串内容
	 * @param rawValue
	 * 				待处理内容
	 * @return
	 */
	protected Map<String,String> filterParamString( String rawValue ) {//--2
		Map<String,String> returnMap=new HashMap<String,String>();
		if ( rawValue == null ) {
			return null;
		}
		String tmpStr = rawValue;
		Map<String,String> map=WafHelper.stripXSS_map(rawValue);
		if ("true".equals(PropertyUtils.getProperty("filterXSS"))&&"0".equals(map.get("succeed"))) {//如果是非法注入，则进行过滤
			tmpStr = WafHelper.stripXSS(rawValue);
			returnMap.put("msg",map.get("msg") );
			returnMap.put("succeed",map.get("succeed"));
			returnMap.put("value", tmpStr);
			return returnMap;
		}
		map=WafHelper.stripSqlInjection_map(rawValue);
		if ("true".equals(PropertyUtils.getProperty("filterSQL"))&&"0".equals(map.get("succeed"))) {
			tmpStr = WafHelper.stripSqlInjection(tmpStr);
			//tmpStr=map.get("value");
			returnMap.put("msg",map.get("msg") );
			returnMap.put("succeed",map.get("succeed"));
		}
		returnMap.put("value", tmpStr);
		return returnMap;
		
	}
    public void init() {  
  
    }  
  
} 