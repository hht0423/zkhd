package com.zk.act.system.dao.hibernate;

import com.zk.common.baseclass.AbstractBaseDAO;
import com.zk.model.system.LogDetail;

public class LogDao extends AbstractBaseDAO{
	@Override
	protected Class<LogDetail> getPojoClass() {
		return LogDetail.class;
	}
}
