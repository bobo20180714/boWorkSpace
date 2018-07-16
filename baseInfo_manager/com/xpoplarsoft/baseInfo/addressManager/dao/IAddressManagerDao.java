package com.xpoplarsoft.baseInfo.addressManager.dao;

import com.xpoplarsoft.baseInfo.addressManager.bean.AddressBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IAddressManagerDao {

	DBResult list(CommonBean bean, String satId);

	boolean add(AddressBean bean);

	DBResult view(String pkId);

	boolean update(AddressBean bean);

	boolean delete(String[] pkIdArr);

	DBResult queryAddressByType(String satId, String type, String addressId);

}
