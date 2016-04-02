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
package com.zk.common.waf;

import java.util.Map;

import com.zk.common.waf.attack.SqlInjection;
import com.zk.common.waf.attack.XSS;

/**
 * Web防火墙工具类
 * <p>
 * @author   hubin
 * @Date	 2014-5-8 	 
 */
public class WafHelper {

	/**
	 * @Description 过滤XSS脚本内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripXSS(String value) {
		if (value == null) {
			return null;
		}

		return new XSS().strip(value);
	}
	/**
	 * @Description 过滤XSS脚本内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static Map<String,String> stripXSS_map(String value) {
		if (value == null) {
			return null;
		}
		return new XSS().strip_map(value);
	}
	/**
	 * @Description 过滤SQL注入内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripSqlInjection(String value) {
		if (value == null) {
			return null;
		}

		return new SqlInjection().strip(value);
	}
	/**
	 * @Description 过滤SQL注入内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static  Map<String,String>  stripSqlInjection_map(String value) {
		if (value == null) {
			return null;
		}

		return new SqlInjection().strip_map(value);
	}
	/**
	 * @Description 过滤SQL/XSS注入内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripSqlXSS(String value) {
		if (value == null) {
			return null;
		}

		return stripXSS(stripSqlInjection(value));
	}

}
