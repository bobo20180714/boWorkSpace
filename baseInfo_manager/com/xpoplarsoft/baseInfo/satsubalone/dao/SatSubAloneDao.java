package com.xpoplarsoft.baseInfo.satsubalone.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.baseInfo.satsubalone.bean.SatSubAlone;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class SatSubAloneDao implements ISatSubAloneDao{

		public List<SatSubAlone> findGrantUserGroupEquipmentTree(DBParameter param){
			DBResult result = SQLFactory.getSqlComponent().queryInfo("sat_param",
				"findGrantUserGroupEquipmentTree", param);
			 List<SatSubAlone>  object = new ArrayList<SatSubAlone>(); 
			 SatSubAlone alone = null;
				for (int i=0;i<result.getRows();i++) {
					alone = new SatSubAlone();
					alone.setCode(result.getValue(i, "CODE"));
					alone.setCreate_time(result.getValue(i, "CREAT_TIME"));
					alone.setLeaf(result.getValue(i, "LEAF"));
					alone.setCreate_user(result.getValue(i, "CREAT_USER"));
					alone.setMid(Integer.parseInt(result.getValue(i, "MID")));
					alone.setName(result.getValue(i, "NAME"));
					alone.setOwner_id(result.getValue(i, "OWNER_ID"));
					alone.setStatus(result.getValue(i, "STATUS"));
					alone.setSys_resource_id(result.getValue(i, "SYS_RESOURCE_ID"));
					alone.setType(Integer.parseInt(result.getValue(i, "TYPE")));
					object.add(alone);
				}
			
		return object;
	}

		@Override
		public DBResult findSatTree(DBParameter param) {
			DBResult result = SQLFactory.getSqlComponent().queryInfo("sat_param",
					"findSatTree", param);
			return result;
		}
		
}

