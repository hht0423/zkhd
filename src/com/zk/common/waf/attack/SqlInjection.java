/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zk.common.waf.attack;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * SQL注入攻击
 * <p>
 * @author   hubin
 * @Date	 2014-5-8 	 
 */
public class SqlInjection implements Istrip {

	/**
	 * @Description SQL注入内容剥离
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public String strip(String value) {

		//剥离SQL注入部分代码
		value= value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
		//*String reg= "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
	     /*       + "(select|update|and|or|delete|insert|trancate|into|substr|"
	            + "ascii|declare|exec|count|master|into|drop|execute|mid|master|sitename|%|'|xp_cmdshell|information_schema.columns|table_schema|column_name|table_schema|by|master|"
	            + "truncate|from|grant|union|where|or|like)"; */
	            String reg= "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|%|'|"  
			            + "(\\s)*(select|update|and|or|delete|insert|trancate|char|into|substr|"
			            + "ascii|declare|exec|count|master|into|drop|execute|mid|master|sitename|xp_cmdshell|information_schema.columns|table_schema|column_name|table_schema|by|master|"
			            + "truncate|from|grant|union|where|or|like)(\\s)+"; 
		return  value.replaceAll(reg,"");
		//
	}
	/**
	 * @Description SQL注入内容剥离
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public Map<String,String> strip_map(String value) {
		Map<String,String> returnMap=new HashMap<String,String>();
		String rlt = null;
		if ( value != null &&!"".equals(value)) {
			// NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters
			//rlt = value.replaceAll(" ", "");
			rlt = value.trim();
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("('.+--)|(--)|(\\|)|(%7C)", Pattern.CASE_INSENSITIVE);
			if(scriptPattern.matcher(rlt).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据包含\"--、|、’等特殊字符\"，请验证后重新输入！数据来源："+value);
				returnMap.put("succeed", "0");
				rlt = scriptPattern.matcher(rlt).replaceAll("");
				returnMap.put("value", rlt);
				return returnMap;
			}
		
			
			//check the sql key word is legal
/*			String reg="(')|(and)|(exec)|(execute)|(insert)|(select)|(delete)|(update)|(count)|(drop)|(*)|(%)|(chr)|(mid)|(master)|(truncate)|" +
	                "(char)|(declare)|(sitename)|(net user)|(xp_cmdshell)|(;)|(or)|(like')|(and)|(exec)|(execute)|(insert)|(create)|(drop)|" +
	                "(table)|(from)|(grant)|(use)|(group_concat)|(column_name)|" +
	                "(information_schema.columns)|(table_schema)|(union)|(where)|(select)|(delete)|(update)|(order)|(by)|(count)|(*)|" +
	                "(chr)|(mid)|(master)|(truncate)|(char)|(declare)|(or)|(like)|(#)";*/
			String reg= "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|%|'|"  
		            + "(\\s)*(select|update|and|or|delete|insert|trancate|char|into|substr|"
		            + "ascii|declare|exec|count|master|into|drop|execute|mid|master|sitename|xp_cmdshell|information_schema.columns|table_schema|column_name|table_schema|by|master|"
		            + "truncate|from|grant|union|where|or|like)(\\s)+"; 
			Pattern scriptPattern_keyword = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
			if(scriptPattern_keyword.matcher(rlt.toLowerCase()).find()){
				returnMap.put("msg", "非常抱歉！您输入的数据包含\"insert、select、delete、update、count、drop等关键字，或特殊字符'、%、--、*等\"，请验证后重新输入！数据来源："+value);
				returnMap.put("succeed", "0");
				rlt = scriptPattern_keyword.matcher(rlt.toLowerCase()).replaceAll("");
				returnMap.put("value", rlt);
				return returnMap;
				
			}
/*			rlt = scriptPattern_keyword.matcher(rlt.toLowerCase()).replaceAll("");
			returnMap.put("value", rlt);*/


		}
		returnMap.put("succeed", "1");
		//剥离SQL注入部分代码
		return returnMap;
	}
	
}
