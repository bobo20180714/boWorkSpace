package com.xpoplarsoft.baseInfo.addressManager.service;

import java.util.Map;

import com.xpoplarsoft.baseInfo.addressManager.bean.AddressBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IAddressManagerService {

	Map<String, Object> list(CommonBean bean, String satId);

	boolean add(AddressBean bean);

	AddressBean view(String pkId);

	boolean update(AddressBean bean);

	boolean delete(String[] pkIdArr);

	Map<String, Object> queryAddressByType(String satId, String type,
			String addressId);

}
