package com.xpoplarsoft.processManager.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.monitor.process.ProcessCodeCreater;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.processManager.bean.ProcessInfoBean;
import com.xpoplarsoft.processManager.dao.IProcessManagerDao;
import com.xpoplarsoft.processType.service.ProcessTypeUtil;

@Service
public class ProcessManagerService implements IProcessManagerService {


	@Autowired
	private IProcessManagerDao dao;
	
	@Override
	public Map<String, Object> list(CommonBean bean, String processName,
			String processCode) {
		DBResult dbr = dao.list(bean,processName,processCode);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public Map<String, Object> queryProcessByType(CommonBean bean,
			String processType) {
		DBResult dbr = dao.queryProcessByType(bean,processType);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean add(ProcessInfoBean bean) {
		String processCode = bean.getProcessCode();
		if(processCode == null || "".equals(processCode)
				|| "null".equals(processCode)){
			if("5".equals(bean.getProcessType()) && "0".equals(bean.getIsMainProcess())){
				//特需计算
				processCode = SystemParameter.getInstance().getParameter("computerCode");
			}else{
				processCode = ProcessCodeCreater.createCode(bean.getProcessType());
			}
		}
		//存储进程
		bean.setProcessCode(processCode);
		boolean flag = dao.add(bean);
		if(flag){
			flag = startProcess(processCode);
		}
		return flag;
	}

	@Override
	public boolean deleteProcess(String processCode) {
		 if(dao.deleteProcess(processCode)){
			 ProcessObjectesControl.remove(processCode);
		 }
		return true;
	}

	@Override
	public ProcessInfoBean viewProcess(String processCode) {
		DBResult dbr = dao.viewProcess(processCode);
		ProcessInfoBean pro = new ProcessInfoBean();
		if (dbr != null && dbr.getRows() > 0) {
			pro.setPkId(dbr.get(0, "pk_id"));
			pro.setProcessType(dbr.get(0, "process_type"));
			pro.setProcessCode(dbr.get(0, "process_code"));
			pro.setProcessName(dbr.get(0, "process_name"));
			pro.setComputerIp(dbr.get(0, "computer_ip"));
			pro.setIsMainProcess(dbr.get(0, "is_main_process"));
			pro.setMainProcessCode(dbr.get(0, "main_process_code"));
			pro.setServiceProcessCode(dbr.get(0, "agency_process_code"));
			pro.setServiceProcessName(dbr.get(0, "f_process_name"));
			pro.setSatMid(dbr.get(0, "mid"));
			pro.setSatId(dbr.get(0, "sat_id"));
			pro.setSatCode(dbr.get(0, "sat_code"));
			pro.setStartupType(dbr.get(0, "startup_type"));
			pro.setStartupPath(dbr.get(0, "startup_path"));
			pro.setStartupParam(dbr.get(0, "startup_param"));
		}
		return pro;
	}

	@Override
	public boolean updateProcess(ProcessInfoBean bean) {
		boolean flag = dao.updateProcess(bean);
		if(flag){
			ProcessBean processBean = ProcessObjectesControl.getProcess(bean.getProcessCode());
			processBean.setName(bean.getProcessName());
			processBean.setAgencyProcessCode(bean.getServiceProcessCode());
			processBean.setComputerIp(bean.getComputerIp());
			ProcessInfoBean infoBean = viewProcess(bean.getProcessCode());
			processBean.setSat_num(infoBean.getSatMid());
		}
		return flag;
	}

	@Override
	public boolean startProcess(String processCode) {
		//启用
		boolean flag = dao.updateProcessState(processCode,2);
		if(flag){
			putToCache(processCode);
		}
		return flag;
	}
	
	/**
	 * 将当前线程放入内存
	 * @param processCode
	 */
	public void putToCache(String processCode){
		ProcessInfoBean infoBean = viewProcess(processCode);
		//将进程放入内存中
		ProcessBean pb = new ProcessBean();
		pb = new ProcessBean();
		pb.setName(infoBean.getProcessName());
		pb.setId(processCode);
		String sat_num = infoBean.getSatMid();
		if(sat_num != null && !"".equals(sat_num) ){
			pb.setSat_num(sat_num);
		}
		String engecyProcessCode = infoBean.getServiceProcessCode();
		if(engecyProcessCode != null && !"".equals(engecyProcessCode) ){
			pb.setAgencyProcessCode(engecyProcessCode);
		}
		String isBei = infoBean.getIsMainProcess();
		if(isBei != null && !"".equals(isBei) ){
			pb.setIsMain(Integer.parseInt(isBei));
		}
		String mainProcessCode = infoBean.getMainProcessCode();
		if(mainProcessCode != null && !"".equals(mainProcessCode) ){
			pb.setMainProcessCode(mainProcessCode);
		}
		String computerId = infoBean.getComputerIp();
		if(computerId != null && !"".equals(computerId) ){
			pb.setComputerIp(computerId);;
		}
		pb.setCode(processCode);
		pb.setType(infoBean.getProcessType());
		pb.setState("0");
		pb.setClass_name(ProcessTypeUtil.getInstance().getParameter(infoBean.getProcessType()));
		ProcessObjectesControl.putProcess(pb);
	}

	@Override
	public boolean judgeIsHaveMain(String processType,String satMid) {
		DBResult dbr = dao.queryProcessByTypeAndMain(processType,satMid);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getMainProcess(String processType,String satMid) {
		DBResult dbr =  dao.getMainProcess(processType,satMid);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public boolean judgeIpExit(String computerIp) {
		boolean flag = false;
		DBResult dbr =  dao.judgeIpExit(computerIp);
		if(dbr != null && dbr.getRows() > 0){
			flag = true;
		}
		return flag;
	}

}
