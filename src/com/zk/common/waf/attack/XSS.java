/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.zk.common.waf.attack;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * XSS脚本攻击
 * <p>
 * @author   hubin
 * @Date	 2014-5-8 	 
 */
public class XSS implements Istrip {

	/**
	 * @Description XSS脚本内容剥离
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public String strip( String value ) {
		String rlt = null;

		if ( value != null ) {
			// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);

			// Avoid null characters
			rlt = value.replaceAll("", "");

			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");
			
			rlt = rlt.replaceAll("'", "''");
			rlt = rlt.replaceAll("\"", "\"\"");
			rlt = rlt.replaceAll("&", "&amp;");
			rlt = rlt.replaceAll("<", "&lt;");
			rlt = rlt.replaceAll(">", "&gt");
/*			rlt = rlt.replaceAll("'", "");
			rlt = rlt.replaceAll("\"", "");
			rlt = rlt.replaceAll("&", "");
			rlt = rlt.replaceAll("<", "");
			rlt = rlt.replaceAll(">", "");*/
		}

		return rlt;
	}
	/**
	 * @Description XSS脚本内容剥离
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public Map<String,String> strip_map( String value ) {
		Map<String,String> returnMap=new HashMap<String,String>();
		String rlt = null;
		if ( value != null &&!"".equals(value)) {
			// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters
			rlt = value.replaceAll(" ", "");
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"<script>\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"src=\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"<script>\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"<script>\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"<script>\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"eval(\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"expression\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"javascript\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			if(scriptPattern.matcher(rlt).find()){
    
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"vbscript:\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"onload\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}    
			rlt = scriptPattern.matcher(rlt).replaceAll("");
			
			scriptPattern = Pattern.compile("(<|>)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据不合法（录入数据中出现\"<、>\"等特殊字符），请重新输入！数据来源："+rlt);
				returnMap.put("succeed", "0");
				return returnMap;
			}
			rlt = scriptPattern.matcher(rlt).replaceAll("");
		}
		returnMap.put("succeed", "1");
		return returnMap;
	}
}
