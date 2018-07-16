package com.xpoplarsoft.processManager.dao;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.processManager.bean.ProcessInfoBean;

public interface IProcessManagerDao {

	DBResult list(CommonBean bean, String processName, String processCode);

	DBResult queryProcessByType(CommonBean bean, String processType);

	boolean add(ProcessInfoBean bean);

	boolean deleteProcess(String processCode);

	DBResult viewProcess(String processCode);

	boolean updateProcess(ProcessInfoBean bean);

	boolean updateProcessState(String processCode,int state);

	DBResult queryProcessByTypeAndMain(String processType,String satMid);

	DBResult getMainProcess(String processType,String satMid);

	DBResult judgeIpExit(String computerIp);

}
