package com.zk.act.system.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.inveno.util.MemCachedUtil;
import com.inveno.util.StringUtil;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import com.zk.act.system.dao.hibernate.D_dDAO;
import com.zk.act.system.dto.DdBean;
import com.zk.act.system.service.interfaces.DdService;
import com.zk.common.baseclass.AbstractBaseService;
import com.zk.common.util.SystemCacheUtil;
import com.zk.model.system.Dd;
import com.zk.common.util.Constants;

/***
 * 数据词典Service
 * 
 * @author LQ
 * 
 */
public class DdServiceImpl extends AbstractBaseService implements DdService {

	private D_dDAO ddDAO;

	/***
	 * 查询所有数据词典列表信息
	 * @date 2012-12-12 修改
	 */
	@Override
	public List<Dd> findDbs() {
		return ddDAO.findByHql("from Dd", null);
	}

	/***
	 * 查找数据词典在线统计数据
	 */
	@Override
	public Dd findOnlineStatById() {
		List<String> list = new ArrayList<String>();
		//list.add(Dictionary.ONLINE_STAT_PARENTID);
		//list.add(Dictionary.ONLINE_STAT_CHILDID);
		return (Dd) ddDAO.findByHql("from Dd where parentId=? and childType=?",
				list).get(0);
	}

	/***
	 * 修改数据词典信息，把数据保存到数据库中，并且更新DdCache 2011-03-09
	 * @param bean
	 *            return boolean
	 */
	@Override
	public boolean updateDb(DdBean bean) {
		Dd dd = bean.getDd();
		if (!isDdEqual(bean)) {// 判断修改的词典是否与已经存在的数据一样，一样就返回，重新修改
			return false;
		}
		dd.setLastUpdTime(new Date());// 添加修改时间
		ddDAO.update(dd);// 更新数据库
		//MemCachedUtil.delete(Constants.DICTIONRY_DD_CACHE);// 删除数据词典在内存中的数据
		//SystemCacheUtil.getDdCache();// 更新数据词典到内存中
		return true;
	}

	public D_dDAO getDdDAO() {
		return ddDAO;
	}

	public void setDdDAO(D_dDAO ddDAO) {
		this.ddDAO = ddDAO;
	}

	/**
	 * 查询数据词典页面初使化信息 2011-3-9
	 * @param ddBean
	 */
	@Override
	public void noTranLoadConfig(DdBean ddBean) {
		String strSql = "from Dd group by parentId";

		List<Dd> listDd = ddDAO.findByHql(strSql, null);
		ddBean.setParentIdList(listDd);
		/** 得到数据词典中父类的类型数据 **/
	}


	/**
	 * 组合数据词典查询条件 2011-3-9
	 * @param ddBean
	 */
	private void assembleConditions(DetachedCriteria detachedCriteria,
			DdBean ddBean, final String alias) {
		Dd dd = ddBean.getDd();

		if (dd != null) {
			String tempStr = "";// 定义临时字符串变量

			// 数据词典查询条件
			tempStr = dd.getParentId();
			if (StringUtil.isNotEmpty(tempStr)) {
				detachedCriteria.add(Restrictions.eq(alias + ".parentId",
						tempStr.trim()).ignoreCase());
			}
		}
	}

	/**
	 * 查询得到数据词典中单一词典的信息 2011-3-10
	 * @param ddBean
	 */
	public void findDdById(DdBean ddBean) {
		String strSql = "from Dd where  id='" + ddBean.getDd().getId() + "'";
		List<Dd> listDd = ddDAO.findByHql(strSql, null);
		ddBean.setDd(listDd.get(0));
	}

	/**
	 * 添加新的数据词典对象，添加的对象以父ID与本身Key为唯一，并且本身ID是在同类的ID上加1 2011-3-10
	 * @param ddBean
	 */
	public void saveDdDetails(DdBean ddBean) {
		String strSql = "";
		Object[] tempObj;
		if (!"".equals(ddBean.getParentName())) {// 判断是否是新添加父子类型 ，父子类型一起添加
			strSql = "select max(parent_id) from dd_table";
			List<Object> listObj = ddDAO.findBySql(strSql, null);
			tempObj = (Object[]) listObj.get(0);
			ddBean.getDd().setParentId(
					(Integer.parseInt(tempObj[0].toString()) + 1) + "");
			ddBean.getDd().setChildType("1");
			ddBean.getDd().setMemo(ddBean.getParentName());
		} else {// 在已有父类下添加子类
			strSql = "select max(child_type) childType,memo from dd_table where parent_id='"
					+ ddBean.getDd().getParentId() + "'";
			List<Object> listObj = ddDAO.findBySql(strSql, null);
			tempObj = (Object[]) listObj.get(0);
			ddBean.getDd().setChildType(
					(Integer.parseInt(tempObj[0].toString()) + 1) + "");
			ddBean.getDd().setMemo(tempObj[1].toString());
		}

		ddBean.getDd().setCode(
				Util.isEmptyString(ddBean.getCode()) ? "" : ddBean.getCode());
		ddBean.getDd().setTypeName(ddBean.getTypeName());
		ddBean.getDd().setStatus(0);
		ddBean.getDd().setCreateTime(new Date());
		ddBean.getDd().setLastUpdTime(null);

		ddDAO.save(ddBean.getDd());// 保存添加的词典信息
		// 更新DdCache
		//MemCachedUtil.delete(Constants.DICTIONRY_DD_CACHE);
		//SystemCacheUtil.getDdCache();
	}

	/**
	 * 修改父类型名称 2011-3-31
	 * @param ddBean
	 */
	public boolean updateMome(DdBean ddBean) {
		String strSql = "select id from dd_table where memo='"
				+ ddBean.getDd().getMemo() + "'";
		List<Object> list = ddDAO.findBySql(strSql, null);
		if (list.size() == 0) {
			StringBuffer strBfSql = new StringBuffer();
			strBfSql.append("update dd_table set memo='");
			strBfSql.append(ddBean.getDd().getMemo());
			strBfSql.append("' where parent_id = ?");
			ddDAO.excuteSql(strBfSql.toString(), Arrays.asList(ddBean.getDd()
					.getParentId()));
			// 更新DdCache
			//MemCachedUtil.delete(Constants.DICTIONRY_DD_CACHE);
			//SystemCacheUtil.getDdCache();
			return true;
		}

		return false;
	}

	/**
	 * 判断修改的词典类型名，父ID与子ID是否都相同 2011-3-10
	 * @param ddBean
	 * @return
	 */
	private boolean isDdEqual(DdBean ddBean) {

		String strSql = "select id from dd_table where parent_id ="
				+ ddBean.getDd().getParentId() + " and id !="
				+ ddBean.getDd().getId() + " and type_name='"
				+ ddBean.getDd().getTypeName() + "'";
		List<Object> list = ddDAO.findBySql(strSql, null);
		if (list.size() > 0) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		List<Dd> ddList = new DdServiceImpl().findDbs();
		for (Dd dd : ddList) {
			System.out.println(dd.getTypeName());
		}
	}
}
