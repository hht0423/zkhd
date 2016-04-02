package com.zk.common.searchUtil.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/*时间查询用的注解
 *@author Alex 2015-10-28
 * */
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface DateSearchField {
	   //枚举值分别代表：今天，昨天，这周，上周，自定义开始时间，自定义结束时间，空值
	 /*  DateType dateSearchType() default DateType.NONE;*/
	   String paramName() default "";//设置参数的名称,用于设置条件的参数名，用于sql中的 paramName=value,
}
