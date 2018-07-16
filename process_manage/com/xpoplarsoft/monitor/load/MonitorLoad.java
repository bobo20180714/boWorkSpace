package com.xpoplarsoft.monitor.load;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.load.Load;
import com.xpoplarsoft.monitor.process.MonitorProcess;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.processManager.service.IProcessManagerService;
import com.xpoplarsoft.processType.service.ProcessTypeUtil;

/**
 * 进程监视load
 * @author mengxiangchao
 *
 */
public class MonitorLoad  implements Load{

	private static Log log = LogFactory.getLog(MonitorLoad.class);
	
	private static String sql = "select p.*,s.mid,s.sat_code from process_info p "+
					" left join satellite s on p.sat_mid = s.sat_id  "+
					" where  p.process_info_state = 2";
	
	@Autowired
	private IProcessManagerService service;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void load(Map map) {
		//启动本进程相关信息
		MonitorProcess.getInstance().startup();
		//加载数据进程表里的信息
		loadProcessInfo();
	}

	/**
	 * 从数据库中查询所有进程信息
	 */
	private void loadProcessInfo() {
		if(log.isDebugEnabled()){
			log.debug("开始加载进程信息。");
		}
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		ProcessBean pro = null;
		for (int i = 0; dbr != null && i < dbr.getRows(); i++) {
			pro = new ProcessBean();
			pro.setName(dbr.get(i, "process_name"));
			pro.setId(dbr.get(i, "process_code"));
			pro.setCode(dbr.get(i, "process_code"));
			pro.setSat_num(dbr.get(i, "mid"));
			pro.setState("0");
			pro.setType(dbr.get(i, "process_type"));
			pro.setClass_name(ProcessTypeUtil.getInstance().getParameter(dbr.get(i, "process_type")));
			pro.setAgencyProcessCode(dbr.get(i, "agency_process_code"));
			pro.setMainProcessCode(dbr.get(i, "main_process_code"));
			pro.setComputerIp(dbr.get(i, "computer_ip"));
			pro.setIsMain(dbr.getObject(i, "is_main_process")==null?-1:Integer.parseInt(dbr.get(i, "is_main_process")));
			ProcessObjectesControl.putProcess(pro);
		}
		if(log.isDebugEnabled()){
			log.debug("进程信息加载完成。");
		}
	}

}
