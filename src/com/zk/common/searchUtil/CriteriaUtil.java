package com.zk.common.searchUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.inveno.util.StringUtil;
import com.zk.common.searchUtil.annotation.CriteriaType;
import com.zk.common.searchUtil.annotation.DataType;
import com.zk.common.searchUtil.annotation.DateSearchField;
import com.zk.common.searchUtil.annotation.SearchField;
/* 通用查询条件注入
 * */
public class CriteriaUtil<F> {
	/* 通用查询条件注入方法
	 * @return DetachedCriteria
	 * */
	 public DetachedCriteria setCriteria(F from,DetachedCriteria criteria) throws Exception{
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd hh:mm");
    	 // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
        Field[] fields_f = from.getClass().getDeclaredFields();
        for (short i = 0; i < fields_f.length; i++) {
		             Field field_f = fields_f[i];
		             String field_FName = field_f.getName();
                     String getMethodName_f = "get"+ field_FName.substring(0, 1).toUpperCase()+ field_FName.substring(1);//获取源对象的getter方法
                     try {
                         Class<?> fromCla=from.getClass();//获取类
                         Method[] methods_f= fromCla.getDeclaredMethods();
                         boolean flag=false;
                         if (!field_f.isAnnotationPresent(DateSearchField.class)&&!field_f.isAnnotationPresent(SearchField.class)){//如果当前字段不是查询相关的注解字段
                        	continue;
                         }
                         for (Method method : methods_f) {//判断是否存在getter方法
                             if(getMethodName_f.equals(method.getName())){
                            	 flag=true;
                            	 break;
                             }
                         }
                         if(!flag){
                        	 continue;
                         }
                         //获取查询参数值
                         Method method_f = fromCla.getDeclaredMethod(getMethodName_f, new Class[]{});
                         Object value= method_f.invoke(from, new Object[]{});
                          if(value==null){
                        	 continue;
                          }
                         //判断是否有指定的注解
                         if (field_f.isAnnotationPresent(DateSearchField.class)&&(value instanceof String)) {//针对日期查询：今天，昨天，上周，本周
                             //AnnotatedElement接口中的方法getAnnotation(),获取传入注解类型的注解
                        	 DateSearchField dateSearchField = field_f.getAnnotation(DateSearchField.class);
                             //拿到注解中的值，即查询参数名称
                             String paraName = dateSearchField.paramName();
                             int paramValue=-1;
                             //if(value!=null&&(value instanceof Integer)){
                                 paramValue=Integer.parseInt((String)value);//参数值
                                 Calendar cal = Calendar.getInstance();
    						     Calendar cal2 = Calendar.getInstance();
                                 switch(paramValue){
	                                 case 1://今天
	                                	 //criteria.add(Restrictions.ge(paraName,format.parse(format.format(cal.getTime()))));
	                                	 criteria.add(Restrictions.ge(paraName, format2.parse(dataFormat(format.format(cal.getTime()),"min"))));
	                                	 criteria.add(Restrictions.le(paraName, format2.parse(dataFormat(format.format(cal.getTime()),"max"))));
	    	                             break;
	                                 case 2://昨天
	    	                             cal.add(Calendar.DATE,-1);
	    	                             //criteria.add(Restrictions.gt(paraName,format.parse(format.format(cal.getTime()))));
	    	                             criteria.add(Restrictions.ge(paraName, format2.parse(dataFormat(format.format(cal.getTime()),"min"))));
	    	                             criteria.add(Restrictions.le(paraName, format2.parse(dataFormat(format.format(cal.getTime()),"max"))));
	    	                             break;
	                                 case 3://本周
		                                	//获取本周一日期
	                                     cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
	                                     cal.add(Calendar.DATE,+1);//设置第二天的00：00点
	                                	 if(format.format(cal.getTime()).equals(format.format(cal2.getTime()))){//今天为本周的第一天
	                                		 criteria.add(Restrictions.ge(paraName,format.parse(format.format(cal.getTime()))));
	                                	 }else{
		                                	 //获取当天日期
		                                	 criteria.add(Restrictions.le(paraName,format.parse(format.format(cal.getTime()))));
		                                	 criteria.add(Restrictions.ge(paraName,format.parse(format.format(cal2.getTime()))));
	                                	 }
	                                	 break;
	                                 case 4://上一周
	                                	 //获取上周一日期
	                                     cal.add(Calendar.WEEK_OF_MONTH, -1);
	                                     cal.set(Calendar.DAY_OF_WEEK, 2);
	                                     criteria.add(Restrictions.ge(paraName,format.parse(format.format(cal.getTime()))));
	                                     //获取上周天日期
	                                     cal2.set(Calendar.DAY_OF_WEEK, 1);
	                                     criteria.add(Restrictions.le(paraName, format2.parse(dataFormat(format.format(cal2.getTime()),"max"))));
	                                     break;
	                                 case 5://本月
	                                     
	                                     //获取当前月第一天： 
	                                     cal.add(Calendar.MONTH, 0);
	                                     cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
	                                     criteria.add(Restrictions.ge(paraName,format.parse(format.format(cal.getTime()))));
		                                   
	                                     //获取当前月最后一天 
	                                     cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH)); 
	                                     criteria.add(Restrictions.le(paraName, format2.parse(dataFormat(format.format(cal2.getTime()),"max"))));
	                                	 break;
	                                 default:
	                                	 break;
	                                 }
                            // }

                          }else if (field_f.isAnnotationPresent(SearchField.class)) {
    	       		    	  if((value instanceof String)&&"".equals((String)value)){//
    	    		    		  continue;
    	    		    	  }
                        	  SearchField searchField=field_f.getAnnotation(SearchField.class);
                        	  boolean multi=searchField.multiple();
                        	  CriteriaType criteriaType =searchField.criteriaType();//获取查询类型
                        	  String paramNames[]=searchField.paramNames();
                        	  int paramNum= paramNames.length;
                        	  if(multi&&paramNum>0&&criteriaType==CriteriaType.ANY_LIKE&&(value instanceof String)){//如果是多个属性共用一个参数值进行模糊查询
	                                  //if(value!=null&&(value instanceof String)){
	                                	  if(paramNum==1&&StringUtil.isNotEmpty(paramNames[0])){//如果只有一个参数
	                                		  criteria.add(Restrictions.like(paramNames[0],(String)value, MatchMode.ANYWHERE).ignoreCase());
	                                	  }if(paramNum==2&&StringUtil.isNotEmpty(paramNames[1])){//如果只有两个参数，则被视为单参数的模糊查询
	                        				  criteria.add(Restrictions.or(Restrictions.like(paramNames[0],(String)value, MatchMode.ANYWHERE).ignoreCase(),Restrictions.like(paramNames[1],(String)value, MatchMode.ANYWHERE).ignoreCase()));
	                        			  }else{
	                        				  StringBuffer buff=new StringBuffer();
	                        				  buff.append(  "("+paramNames[0]+" like '%"+(String)value+"%' ");
			                        		  for(int m=1;m<paramNum;m++){
				                        			//如果是多个参数
			                                        buff.append("or "+paramNames[m]+" like '%"+(String)value+"%' ");
				                        					
				                        	     }
			                        		  buff.append(")");
			                        		   criteria.add(Restrictions.sqlRestriction(buff.toString()));
	                        			  } 
		                           //  }
                        	  }else if(!multi){
                        		  String paramName=searchField.paramName();
                        		  DataType dataType=searchField.dataType();
                        		 
                                  //如果是非字符串，停止运行
        	       		    	  if(!(value instanceof String)||((value instanceof String)&&"".equals((String)value))){//
        	    		    		  continue;
        	    		    	  }
        	       		    	  
                        		  switch(criteriaType){
                        		      case EQ:
                        		    	  switch(dataType){  
                        		    	     case DATE:
                        		    	    	 criteria.add(Restrictions.ge(paramName, format2.parse((String)value)));
                        		    	    	 criteria.add(Restrictions.le(paramName, format2.parse(dataFormat((String)value,"max"))));
                        		    	    	 break;
                        		    	     default:
                        		    	    	 criteria.add(Restrictions.eq(paramName, value));
                        		    	    	 break;
                        		    	  }
                        		    	
                        		          break;
                        		      case LE:
                         		    	  switch(dataType){  
	                     		    	     case DATE:
	                     		    	    	 criteria.add(Restrictions.le(paramName, format2.parse(dataFormat((String)value,"max"))));
	                     		    	    	 break;
	                     		    	     default:
	                     		    	    	 criteria.add(Restrictions.le(paramName, value));
	                     		    	    	 break;
                     		    	     }
                    		              break;
                        		      case LT:
                         		    	  switch(dataType){  
	                     		    	     case DATE:
	                     		    	    	 criteria.add(Restrictions.lt(paramName, format2.parse(dataFormat((String)value,"max"))));
	                     		    	    	 break;
	                     		    	     default:
	                     		    	    	 criteria.add(Restrictions.lt(paramName, value));
	                     		    	    	 break;
                     		    	     }
                    		              break;
                        		      case GE:
                         		    	  switch(dataType){  
	                     		    	     case DATE:
	                     		    	    	 criteria.add(Restrictions.ge(paramName, format2.parse(dataFormat((String)value,"min"))));
	                     		    	    	 break;
	                     		    	     default:
	                     		    	    	 criteria.add(Restrictions.ge(paramName, value));
	                     		    	    	 break;
                     		    	     }
                    		              break;
                        		      case GT:
                         		    	  switch(dataType){  
	                     		    	     case DATE:
	                     		    	    	 criteria.add(Restrictions.gt(paramName, format2.parse(dataFormat((String)value,"min"))));
	                     		    	    	 break;
	                     		    	     default:
	                     		    	    	 criteria.add(Restrictions.gt(paramName, value));
	                     		    	    	 break;
                     		    	     }
                    		              break;
                        		      case BEFOR_LIKE:
                        		    	  if((value instanceof String)&&"".equals((String)value)){
                            		    	  criteria.add(Restrictions.like(paramName, (String)value, MatchMode.START));
                        		    	  }

                    		              break;
                        		      case END_LIKE:
                        		    	  if((value instanceof String)&&"".equals((String)value)){
                        		    	      criteria.add(Restrictions.like(paramName, (String)value, MatchMode.END));
                        		    	  }
                    		              break;
                        		      case ANY_LIKE:
                        		    	  if((value instanceof String)&&"".equals((String)value)){
                        		    	      criteria.add(Restrictions.like(paramName, (String)value, MatchMode.ANYWHERE));
                        		    	  }
                    		              break;
/*                        		      case IN:
                        		    	  String valueStr=(String)value;
                        		    	  @SuppressWarnings("null")
										  String valueArr[]=valueStr.split(",");
                        		    	  criteria.add(Restrictions.in(paramName, valueArr));
                    		              break;
                        		      case NOTIN:
                        		    	  String valueStr_ontin=(String)value;
                        		    	  @SuppressWarnings("null")
										  String valueArr_ontin[]=valueStr_ontin.split(",");
                        		    	  criteria.add(Restrictions.not(Restrictions.in(paramName, valueArr_ontin)));
                    		              break;*/
								    default:
									     break;
                        		  }
                        	  }
                     }
                     } catch (SecurityException e) {
                     	JOptionPane.showMessageDialog(null, "代码1：条件设置错误!");
                        // throw new RuntimeException();
                     } catch (NoSuchMethodException e) {
                    	 JOptionPane.showMessageDialog(null, "代码2：条件设置错误!");
                        // throw new RuntimeException();
                     } catch (IllegalArgumentException e) {
                    	 JOptionPane.showMessageDialog(null, "代码3：条件设置错误!");
                       //  throw new RuntimeException();
                     } catch (IllegalAccessException e) {
                    	 JOptionPane.showMessageDialog(null, "代码4：条件设置错误!");
                        // throw new RuntimeException();
                     } catch (InvocationTargetException e) {
                    	 JOptionPane.showMessageDialog(null, "代码5：条件设置错误!");
                         //throw new RuntimeException();
                     }  catch (ParseException e) {
							// TODO Auto-generated catch block
                    	 JOptionPane.showMessageDialog(null, "日期转换出错!");
                    	 //throw new RuntimeException();
					 }finally {
                         //清理资源
                     }

        }
    	return criteria;
    }
	 private String dataFormat(String dateStr,String type){
    	 if(((String)dateStr).split(" ").length>1){
    		 return dateStr;
    	 }
		 if("min".equals(type)){
			 dateStr=dateStr+" 00:00";//将时间设置为截止时间的00：00分 
		 }else if("max".equals(type)){
			 dateStr=dateStr+" 23:59";//将时间设置为截止时间的23：59分 
		 }
		 return dateStr;
		 
	 }
}
