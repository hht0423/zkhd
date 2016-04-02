package com.zk.common.hibernate.resulttranformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;

import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

import com.inveno.util.StringUtil;
import com.opensymphony.xwork2.ognl.OgnlUtil;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

/**
 * 支持属性为自定义对象的结果集转换的部份属性查询
 * 2009-3-30
 * @author yaoyuan
 */
@SuppressWarnings({"serial","unchecked"})
public class AliasToBean implements ResultTransformer {

	private static final OgnlUtil ognlUntil = new OgnlUtil();
	private static final Map<String,Boolean> context = new HashMap<String,Boolean>(1);
	static{
		context.put(ReflectionContextState.CREATE_NULL_OBJECTS, true);
	}
	
	/** POJO的class */
	private final Class resultClass;
//	/** 本次查询所用到的POJO中相应属性的对应SET方法集合 */
//	private Setter[] setters;
//	/** 属性访问器 */
//	private PropertyAccessor propertyAccessor;
//	/** POJO中的关联对象的属性名集合 */
//	private Map<String,Object> childPros;
//	/** POJO中的关联对象的属性名集合 */
//	private Map<String,Object> newChildPros;
//	/** POJO中的关联对象的属性值集合 */
//	private Map<Integer,Setter> tupleIndex;
	
	public AliasToBean(Class pojoClass) {
		if(pojoClass==null) throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = pojoClass;
//		propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] { PropertyAccessorFactory.getPropertyAccessor(resultClass,null), PropertyAccessorFactory.getPropertyAccessor("field")}); 		
	}

	public List transformList(List collection) {
		return collection;
	}

	/**
	 * 结果集转换
	 * 2009-4-7
	 * @author yaoyuan
	 * @param tuple 属性值集合
	 * @param aliases 属性名集合
	 * @return 单个POJO实例--查询结果
	 */
	public Object transformTuple(Object[] tuple, String[] aliases) {
		try {
			Object root = resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				if(StringUtil.isNotEmpty(aliases[i]))
				{
					Ognl.setValue(ognlUntil.compile(aliases[i]), context, root, tuple[i]);
				}
			}
			return root;
		} catch (Exception e) {
			throw new HibernateException(e.getMessage(),e);
		}
	}

}
