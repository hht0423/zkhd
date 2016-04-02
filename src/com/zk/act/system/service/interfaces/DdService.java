package com.zk.act.system.service.interfaces;

import java.util.List;

import com.zk.act.system.dto.DdBean;
import com.zk.common.baseclass.IBaseService;
import com.zk.model.system.Dd;

public interface DdService extends IBaseService{
   
	List<Dd> findDbs();
	
	Dd findOnlineStatById();
	
	boolean updateDb(DdBean bean);
	
	/**
	 * 查询数据词典页面初使化信息
	 * 2011-3-9
	 * @param ddBean
	 */
	void noTranLoadConfig(DdBean ddBean);
	
	/**
	 * 查询得到数据词典中单一词典的信息
	 * 2011-3-10
	 * @param ddBean
	 */
	void findDdById(DdBean ddBean);
	
	/**
	 * 添加新的数据词典对象，添加的对象以父ID与本身Key为唯一，并且本身ID是在同类的ID上加1
	 * 2011-3-10
	 * @param ddBean
	 */
	void saveDdDetails(DdBean ddBean);
	
	/**
	 * 修改父类型名称
	 * 2011-3-31
	 * @param ddBean
	 */
	boolean updateMome(DdBean ddBean);
}
