package com.xpoplarsoft.baseInfo.addressManager.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.baseInfo.addressManager.bean.AddressBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class AddressManagerDao implements IAddressManagerDao {

	@Override
	public DBResult list(CommonBean bean,String satId) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("satId", satId);
		return SQLFactory.getSqlComponent().pagingQueryInfo("addressManager", "list", jParameter, bean.getPage(), bean.getPagesize());
	}

	@Override
	public boolean add(AddressBean bean) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("type", bean.getType());
		jParameter.setObject("address", bean.getAddress());
		jParameter.setObject("port", bean.getPort());
		jParameter.setObject("content", bean.getContent());
		jParameter.setObject("sat_id", bean.getSat_id());
		return SQLFactory.getSqlComponent().updateInfo("addressManager", "add", jParameter);
	}

	@Override
	public DBResult view(String pkId) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("pkId", pkId);
		return SQLFactory.getSqlComponent().queryInfo("addressManager", "view", jParameter);
	}

	@Override
	public boolean update(AddressBean bean) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("pkId", bean.getPk_id());
		jParameter.setObject("type", bean.getType());
		jParameter.setObject("address", bean.getAddress());
		jParameter.setObject("port", bean.getPort());
		jParameter.setObject("content", bean.getContent());
		return SQLFactory.getSqlComponent().updateInfo("addressManager", "update", jParameter);
	}

	@Override
	public boolean delete(String[] pkIdArr) {
		List<DBParameter> plist = new ArrayList<DBParameter>();
		DBParameter jParameter = null;
		for (int i = 0; i < pkIdArr.length; i++) {
			jParameter = new DBParameter();
			jParameter.setObject("pkId", pkIdArr[i]);
			plist.add(jParameter);
			
		}
		return SQLFactory.getSqlComponent().batchUpdate("addressManager", "delete", plist);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult queryAddressByType(String satId, String type,
			String addressId) {
		String sql = "select * from SAT_ADDRESS_TABLE where  sat_id = "+satId+" and type = '"+type+"' ";
		if(addressId != null){
			sql = sql + " and pk_id != "+addressId;
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

}
