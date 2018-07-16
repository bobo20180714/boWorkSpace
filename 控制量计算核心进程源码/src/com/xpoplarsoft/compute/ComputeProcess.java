package com.xpoplarsoft.compute;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.compute.dao.ComputeDao;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.ProcessCoreAbstract;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 特需计算线程核心
 * @author zhouxignlu
 * 2017年3月9日
 */
public class ComputeProcess extends ProcessCoreAbstract {
	
	private static Log log = LogFactory.getLog(ComputeProcess.class);
	
	private String proCode;
	private int isMain = -1;
	private String mainProCode;
	
	public ComputeProcess(String proCode,  int isMain,
			String mainProCode) {
		this.proCode = proCode;
		this.isMain = isMain;
		this.mainProCode = mainProCode;
	}
	
	public void init(int t) {
		if (log.isDebugEnabled()) {
			log.debug("[ComputeProcess]启动开始！");
		}
		super.init();
		ProcessObjectesControl.remove(this.pro.getId());
		this.pro.setCode(proCode);
		this.pro.setSat_num("-1");
		this.pro.setId(proCode);
		this.pro.setState("1");
		this.pro.setIsMain(isMain);
		if (t == 1) {
			this.pro.setMainProcessCode(mainProCode);
			//查询主进程信息，加载到内存
			ProcessBean mainProcess = getMainProcess(mainProCode);
			ProcessObjectesControl.putProcess(mainProcess);
		}
		ProcessObjectesControl.putLocalProcess(this.pro);
		
		if (t == 0 || t == -1) {
			//启动订单处理
			
			OrderPolling poll = new OrderPolling();
			poll.start();
		}
		
	}
	
	/**
	 * 查询主进程信息，加载到内存
	 * @param mainProCode1
	 * @return
	 */
	private ProcessBean getMainProcess(String mainProCode1) {
		ProcessBean mainProcess = new ProcessBean();
		ComputeDao dao = new ComputeDao();
		Map<String, Object> processMap = dao.getProcessInfoByCode(mainProCode1);
		mainProcess.setId(processMap.get("process_code").toString());
		mainProcess.setCode(processMap.get("process_code").toString());
		mainProcess.setState("0");
		mainProcess.setIsMain(0);
		mainProcess.setLife_time(System.currentTimeMillis());
		return mainProcess;
	}

	@Override
	public void receiveHeartbeat(ProcessData process) {
		int source = process.getHead().getSource();
		if(mainProCode != null && !"".equals(mainProCode)
				&& source == Integer.parseInt(mainProCode)){
			ProcessBean mainProcess = ProcessObjectesControl.getProcess(mainProCode);
			mainProcess.setState("1");
			mainProcess.setLife_time(System.currentTimeMillis());
		}
	}

	@Override
	public void sendOperatData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendOperatLog() {
		// TODO Auto-generated method stub

	}

	/*public static void main(String[] args) {
		ComputeProcess cp = new ComputeProcess();
		cp.init();
		cp.sendHeartbeat();
		cp.receiveProcessMessage();
		cp.executeProcessOrder();
	}*/
}
