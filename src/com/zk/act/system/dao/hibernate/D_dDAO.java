package com.zk.act.system.dao.hibernate;

import com.zk.common.baseclass.AbstractBaseDAO;
import com.zk.model.system.Dd;

/***
 * 数据词典DAO类
 * @author LQ
 */
public class D_dDAO extends AbstractBaseDAO{
	@Override
	protected Class<Dd> getPojoClass() {
		return Dd.class;
	}
}
