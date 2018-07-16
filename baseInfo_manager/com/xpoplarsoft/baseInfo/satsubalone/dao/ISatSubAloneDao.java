package com.xpoplarsoft.baseInfo.satsubalone.dao;

import java.util.List;

import com.xpoplarsoft.baseInfo.satsubalone.bean.SatSubAlone;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

public interface ISatSubAloneDao{

	public DBResult findSatTree(DBParameter param);

	public List<SatSubAlone> findGrantUserGroupEquipmentTree(DBParameter param);
	
}

