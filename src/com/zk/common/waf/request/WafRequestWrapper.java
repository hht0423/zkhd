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
package com.zk.common.waf.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

import com.zk.common.waf.WafHelper;

/**
 * Request请求过滤包装
 * <p>
 * @author   hubin
 * @Date	 2014-5-8 	 
 */
public class WafRequestWrapper extends HttpServletRequestWrapper {

	private boolean filterXSS = true;
	private boolean filterSQL = true;
	private HttpServletRequest request;
/*	private  byte[] body; // 报文
*/	public WafRequestWrapper( HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
		super(request);
		this.filterXSS = filterXSS;
		this.filterSQL = filterSQL;
		this.request=request;
	}
	public WafRequestWrapper( HttpServletRequest request) {
		this(request, true, true);
/*		body = StreamUtil.readBytes(request.getInputStream());*/
	}		
	@Override
	public ServletInputStream getInputStream() throws IOException {
	        String bizBindMsg = "";  
            ServletInputStream stream = null;  
            try {  
                stream = request.getInputStream();  
                bizBindMsg = IOUtils.toString(stream, "UTF-8");  
              } catch (IOException e) {  
                e.printStackTrace();  
            }  
	        byte[] buffer = null;  
	        try {  
	            buffer = bizBindMsg.toString().getBytes("UTF-8");  
	        } catch (UnsupportedEncodingException e) {  
	           e.printStackTrace();  
	        }  
	        final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);  
	        ServletInputStream newStream = new ServletInputStream() {  
	            @Override  
	            public int read() throws IOException {  
	                return bais.read();  
	            }  
	        };  
	        return newStream;
		}


	/**
	 * @Description 数组参数过滤
	 * @param parameter
	 * 				过滤参数
	 * @return
	 */
	@Override
	public String[] getParameterValues( String parameter ) {
		String[] values = super.getParameterValues(parameter);
		if ( values == null ) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for ( int i = 0 ; i < count ; i++ ) {
			encodedValues[i] = filterParamString(values[i]);
		}

		return encodedValues;
	}


	/**
	 * @Description 参数过滤
	 * @param parameter
	 * 				过滤参数
	 * @return
	 */
	@Override
	public String getParameter( String parameter ) {
		String value=super.getParameter(parameter);
		return filterParamString(value);
	}


	/**
	 * @Description 请求头过滤 
	 * @param name
	 * 				过滤内容
	 * @return
	 */
	@Override
	public String getHeader( String name ) {//---1   ---3
		return filterParamString(super.getHeader(name));
	}


	/**
	 * @Description Cookie内容过滤
	 * @return
	 */
	@Override
	public Cookie[] getCookies() {
		Cookie[] existingCookies = super.getCookies();
		if ( existingCookies != null ) {
			for ( int i = 0 ; i < existingCookies.length ; ++i ) {
				Cookie cookie = existingCookies[i];
				cookie.setValue(filterParamString(cookie.getValue()));
			}
		}
		return existingCookies;
	}
	/**
	 * @Description 过滤字符串内容
	 * @param rawValue
	 * 				待处理内容
	 * @return
	 */
	protected String filterParamString( String rawValue ) {//--2
		if ( rawValue == null ) {
			return null;
		}
		String tmpStr = rawValue;
		if ( this.filterXSS ) {
			tmpStr = WafHelper.stripXSS(rawValue);
		}
		if ( this.filterSQL ) {
			tmpStr = WafHelper.stripSqlInjection(tmpStr);
		}
		return tmpStr;
	}
}
