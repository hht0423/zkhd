package com.zk.common.searchUtil.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/*自定义注解类型，用于查询的通用型
 *@author Alex 2015-10-28
 * */
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface SearchField {
	   /*面向对象条件查询的参数名，即sql语句的条件查询 columnname=value中columnname在持久类中的属性名称paramname*/
	   String paramName() default "";
	   /*条件查询类型*/
	   CriteriaType criteriaType() default CriteriaType.EQ;
	   /*同时对多个参数进行设置，如一个参数值同时对id和名称进行模糊查询，跟*/
	   String[] paramNames() default {};
	   /*模糊查询时，判断是否多列共用一个模糊值查询，如果为true，多列的名称从paramNames中设置*/
	   boolean multiple() default false;
	   /*持久类对应主键的属性名称*/
	   String pkName() default "id";
	   DataType dataType() default DataType.STRING;
	   
}
