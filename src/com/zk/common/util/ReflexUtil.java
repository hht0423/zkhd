package com.zk.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.commons.collections.CollectionUtils;
public class ReflexUtil<F,T> {
    /**
     * 通过放射机制来实现bean类给持久类的复制操作
     * @param form 源类对象
     * @param to 目标类对象
     * @param exceptParamsSet 指定不需要进行复制的属性集合
     * @return to 目标类对象
     * @throws Exception 
     */
    public  T dataExchange(F from,T to,String[] exceptParamsArra) throws Exception
    {
    	     if(from==null||to==null){
    	    	 throw new Exception("目标或源对象为空！");
    	     }
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields_f = from.getClass().getDeclaredFields();
            Field[] fields_t = to.getClass().getDeclaredFields();
            for (short i = 0; i < fields_f.length; i++) {
                Field field_f = fields_f[i];
                String field_FName = field_f.getName();  
                Set<String> strSet = new HashSet<String>();   //将数组转换为集合类型 
                if(exceptParamsArra!=null&&exceptParamsArra.length>0){
                    CollectionUtils.addAll(strSet, exceptParamsArra);  
                    if(strSet!=null&&strSet.contains(field_FName)){//如果排除参数列表不为空，则判定当前参数是否为排除赋值参数
                    	continue;
                    }
                }
                for(short j=0;j<fields_t.length;j++){
                	 Field field_t = fields_t[j];
                	 String field_TName=fields_t[j].getName();
                	 if(field_TName.equals(field_FName)&&!Modifier.isStatic(field_f.getModifiers())){//如果有相同的geter和setter方法就将通过getter和setter方法进行赋值。
                         String getMethodName_f = "get"+ field_FName.substring(0, 1).toUpperCase()+ field_FName.substring(1);//获取源对象的getter方法
                         String setMethodName_t="set"+field_TName.substring(0, 1).toUpperCase()+ field_TName.substring(1);//获取源对象的setter方法
                        // System.out.println("getMethodName_f:"+getMethodName_f+"---setMethodName_t:"+setMethodName_t);
                         try {
                             Class<?> fromCla=from.getClass();//获取源类的方法
                             Class<?> toCla=to.getClass();//获取目标类的类和方法
                             //判断方法是否存在
                             Method[] methods_t= toCla.getDeclaredMethods();
                             //Loop through the methods and print out their names
                             boolean flag=false;
                             for (Method method_t : methods_t) {
                                 if(setMethodName_t.equals(method_t.getName())){
                                	 method_t.getName();
                                	// System.out.println("setMethodName_t:"+setMethodName_t+"---method_t.getName():"+method_t.getName());
                                	 flag=true;
                                	 break;
                                 }
                             }
                             if(!flag){
                            	 break;
                             }
                             Method method_f = fromCla.getDeclaredMethod(getMethodName_f, new Class[]{});
                             Class<?> typeClass_f = field_f.getType();
                             Object value= method_f.invoke(from, new Object[]{});
                             
                             if(value==null||((value instanceof String)&&"".equals((String)value))){
                            	 break;//如果值为空，不做赋值操作
                             }

                             Class<?> typeClass_t = field_t.getType();
                             if(typeClass_f!=typeClass_t){//如果数据类型不一样，不能复制
                            	 break;
                             }
                             Method method_t = toCla.getDeclaredMethod(setMethodName_t, new Class[]{typeClass_t});
                             method_t.invoke(to, new Object[]{value});
                             
                         } catch (SecurityException e) {
                         	JOptionPane.showMessageDialog(null, "对象赋值出现错误!");
                            // throw new RuntimeException();
                         } catch (NoSuchMethodException e) {
                        	 JOptionPane.showMessageDialog(null, "对象赋值出现错误!");
                           //  throw new RuntimeException();
                         } catch (IllegalArgumentException e) {
                        	 JOptionPane.showMessageDialog(null, "对象赋值出现错误!");
                           //  throw new RuntimeException();
                         } catch (IllegalAccessException e) {
                        	 JOptionPane.showMessageDialog(null, "对象赋值出现错误!");
                            // throw new RuntimeException();
                         } catch (InvocationTargetException e) {
                        	 JOptionPane.showMessageDialog(null, "对象赋值出现错误!");
                            // throw new RuntimeException();
                         } finally {
                             //清理资源
                         }
                	 }
                }

            }
            return to;
            
    }
	    /**
	     * 通过class类型获取获取对应类型的值
	     * @param typeClass class类型
	     * @param value 值
	     * @return Object
	     */
	    private static Object getClassTypeValue(Class<?> typeClass, String value){
	        if(typeClass == int.class){
	            if(value.equals("")){
	                return 0;
	            }
	            return Integer.parseInt(value);
	        }else if(typeClass == short.class){
	            if(value.equals("")){
	                return 0;
	            }
	            return Short.parseShort(value);
	        }else if(typeClass == byte.class){
	            if(value.equals("")){
	                return 0;
	            }
	            return Byte.parseByte(value);
	        }else if(typeClass == double.class){
	            if(value.equals("")){
	                return 0;
	            }
	            return Double.parseDouble(value);
	        }else if(typeClass == boolean.class){
	            if(value.equals("")){
	                return false;
	            }
	            return Boolean.parseBoolean(value);
	        }else if(typeClass == float.class){
	            if(value.equals("")){
	                return 0;
	            }
	            return Float.parseFloat(value);
	        }else if(typeClass == long.class){
	            if(value.equals("")){
	                return 0;
	            }
	            return Long.parseLong(value);
	        }else {
	            return typeClass.cast(value);
	        }
	    }
	    /**
	     * @param obj
	     *            操作的对象
	     * @param att
	     *            操作的属性
	     * */
	    public static void getter(Object obj, String att) {
	        try {
	            Method method = obj.getClass().getMethod("get" + att);
	            System.out.println(method.invoke(obj));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    /**
	     * @param obj
	     *            操作的对象
	     * @param att
	     *            操作的属性
	     * @param value
	     *            设置的值
	     * @param type
	     *            参数的属性
	     * */
	    public static void setter(Object obj, String att, Object value,
	            Class<?> type) {
	        try {
	            Method method = obj.getClass().getMethod("set" + att, type);
	            method.invoke(obj, value);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
