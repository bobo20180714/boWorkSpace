package com.xpoplarsoft.processManager.dao;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.processManager.bean.ProcessInfoBean;

@Component
public class ProcessManagerDao implements IProcessManagerDao {

	@Override
	public DBResult list(CommonBean bean, String processName, String processCode) {
		DBParameter param = new DBParameter();
		param.setObject("process_name", processName);
		param.setObject("process_code", processCode);
		return SQLFactory.getSqlComponent().pagingQueryInfo("processManager", "process_list", param, bean.getPage(), bean.getPagesize());
	}

	@Override
	public DBResult queryProcessByType(CommonBean bean, String processType) {
		DBParameter param = new DBParameter();
		param.setObject("process_type", processType);
		return SQLFactory.getSqlComponent().pagingQueryInfo("processManager", "queryProcessByType", param, bean.getPage(), bean.getPagesize());
	}

	@Override
	public boolean add(ProcessInfoBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("process_code", bean.getProcessCode());
		param.setObject("process_name", bean.getProcessName());
		param.setObject("process_type", bean.getProcessType());
		param.setObject("computer_ip", bean.getComputerIp());
		param.setObject("startup_type", bean.getStartupType());
		param.setObject("startup_path", bean.getStartupPath());
		param.setObject("startup_param", bean.getStartupParam());
		param.setObject("agency_process_code", bean.getServiceProcessCode());
		param.setObject("is_main_process", bean.getIsMainProcess());
		param.setObject("main_process_code", bean.getMainProcessCode());
		param.setObject("sat_mid", bean.getSatId());
		return SQLFactory.getSqlComponent().updateInfo("processManager", "add", param);
	}

	@Override
	public boolean deleteProcess(String processCode) {
		DBParameter param = new DBParameter();
		param.setObject("processCode", processCode);
		return SQLFactory.getSqlComponent().updateInfo("processManager", "deleteProcess", param);
	}

	@Override
	public DBResult viewProcess(String processCode) {
		DBParameter param = new DBParameter();
		param.setObject("process_code", processCode);
		return SQLFactory.getSqlComponent().queryInfo("processManager", "viewProcess", param);
	}

	@Override
	public boolean updateProcess(ProcessInfoBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("pk_id", bean.getPkId());
		param.setObject("process_code", bean.getProcessCode());
		param.setObject("process_name", bean.getProcessName());
		param.setObject("process_type", bean.getProcessType());
		param.setObject("computer_ip", bean.getComputerIp());
		param.setObject("startup_type", bean.getStartupType());
		param.setObject("startup_path", bean.getStartupPath());
		param.setObject("startup_param", bean.getStartupParam());
		param.setObject("agency_process_code", bean.getServiceProcessCode());
		param.setObject("is_main_process", bean.getIsMainProcess());
		param.setObject("main_process_code", bean.getMainProcessCode());
		param.setObject("sat_mid", bean.getSatId());
		return SQLFactory.getSqlComponent().updateInfo("processManager", "updateProcess", param);
	}

	@Override
	public boolean updateProcessState(String processCode,int state) {
		DBParameter param = new DBParameter();
		param.setObject("processCode", processCode);
		param.setObject("processInfoState", state);
		return SQLFactory.getSqlComponent().updateInfo("processManager", "updateProcessState", param);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult queryProcessByTypeAndMain(String processType,String satMid) {
		String sql = "select * from process_info where  process_type = "+processType+"  and is_main_process = 0 ";
		if(satMid != null && !"".equals(satMid)){
			sql = sql + " and sat_mid = "+satMid;
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
		/*DBParameter param = new DBParameter();
		param.setObject("processType", processType);
		param.setObject("satMid", satMid);
		param.setObject("isMain", 0);
		return SQLFactory.getSqlComponent().queryInfo("processManager", "queryProcessByTypeAndMain", param);*/
	}

	@Override
	public DBResult getMainProcess(String processType,String satMid) {
		DBParameter param = new DBParameter();
		param.setObject("processType", processType);
		if(satMid == null || "".equals(satMid)){
			return SQLFactory.getSqlComponent().queryInfo("processManager", "getMainProcess", param);
		}
		param.setObject("satMid", satMid);
		return SQLFactory.getSqlComponent().queryInfo("processManager", "getMainProcess_bySat", param);
	}
	
	@Override
	public DBResult judgeIpExit(String computerIp) {
		DBParameter param = new DBParameter();
		param.setObject("computerIp", computerIp);
		return SQLFactory.getSqlComponent().queryInfo("processManager", "judgeIpExit", 
				param);
	}

}
