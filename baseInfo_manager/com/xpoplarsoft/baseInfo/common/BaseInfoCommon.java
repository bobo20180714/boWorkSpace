package com.xpoplarsoft.baseInfo.common;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

public class BaseInfoCommon {

	@SuppressWarnings("deprecation")
	public static String getSatOrTmId(){
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo("select SAT_TM_ID_SEQ.nextval pkid from dual");
		if(dbr != null && dbr.getRows() > 0){
			return dbr.get(0, "pkid");
		}
		return "-1";
	}
	
}
