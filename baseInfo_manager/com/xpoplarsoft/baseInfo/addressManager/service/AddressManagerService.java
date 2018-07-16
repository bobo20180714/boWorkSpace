package com.xpoplarsoft.baseInfo.addressManager.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseInfo.addressManager.bean.AddressBean;
import com.xpoplarsoft.baseInfo.addressManager.dao.IAddressManagerDao;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class AddressManagerService implements IAddressManagerService {

	@Autowired
	private IAddressManagerDao dao; 	
	
	@Override
	public Map<String, Object> list(CommonBean bean, String satId) {
		DBResult dbr = dao.list(bean,satId);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean add(AddressBean bean) {
		return dao.add(bean);
	}

	@Override
	public AddressBean view(String pkId) {
		AddressBean bean = new AddressBean();
		DBResult dbr = dao.view(pkId);
		if(dbr != null && dbr.getRows() > 0){
			bean.setSat_id(dbr.get(0, "sat_id"));
			bean.setType(dbr.get(0, "type"));
			bean.setAddress(dbr.get(0, "address"));
			bean.setPort(dbr.get(0, "port"));
			bean.setContent(dbr.get(0, "content"));
		}
		return bean;
	}

	@Override
	public boolean update(AddressBean bean) {
		return dao.update(bean);
	}

	@Override
	public boolean delete(String[] pkIdArr) {
		return dao.delete(pkIdArr);
	}

	@Override
	public Map<String, Object> queryAddressByType(String satId, String type,
			String addressId) {
		DBResult dbr = dao.queryAddressByType(satId,type,addressId);
		return DBResultUtil.dbResultToMap(dbr);
	}

}
