package com.xpoplarsoft.compute.common;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

public class CommonUtil {


	@SuppressWarnings("deprecation")
	public static int getComputePkId(){
		int computPkId = -1;
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo("select COMPUTE_SEQ.nextval pk_id from dual");
		if(dbr != null && dbr.getRows() > 0){
			computPkId = Integer.parseInt(dbr.get(0, "pk_id"));
		}
		return computPkId;
	}
	
}
