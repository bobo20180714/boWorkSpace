/**
 * com.xpoplarsoft.process.alarm
 */
package com.xpoplarsoft.process.alarm;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.alarm.result.AlarmResultLoad;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.loadxml.LoadConfigFile;
import com.xpoplarsoft.loadxml.impl.LoadxmlLoad;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.ProcessCoreAbstract;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 门限判读进程<br>
 * 重写心跳发生器，根据启动参数生成主业务进程心跳报文，或者备用业务进程心跳报文<br>
 * 启动初始化加载业务配置文件，如果为主进程，则直接启动业务处理功能，如果为备用业务进程则不启动业务处理功能<br>
 * 作为备用进程，心跳接收时需要接收处理主进程的心跳信息，当无法接收到主进程的心跳信息时，自身转换为主进程
 * 
 * @author zhouxignlu 2017年3月22日
 */
public class AlarmProcess extends ProcessCoreAbstract {

	private static Log log = LogFactory.getLog(AlarmProcess.class);
	private String proCode;
	private int isMain = -1;
	private String mainProCode;

	public AlarmProcess() {

	}

	public AlarmProcess(String proCode, int isMain, String mainProCode) {
		this.proCode = proCode;
		this.isMain = isMain;
		this.mainProCode = mainProCode;
	}

	/**
	 * 启动业务进程初始化
	 * 
	 * @param t
	 *            为1时本地进程为备用进程，不加载业务处理
	 */
	public void init(int t) {
		if (log.isDebugEnabled()) {
			log.debug("[AlarmProcess]启动开始！");
		}
		super.init();
		ProcessObjectesControl.remove(this.pro.getId());
		this.pro.setCode(proCode);
		this.pro.setId(proCode);
		this.pro.setState("1");
		this.pro.setIsMain(isMain);
		
		//加载配置文件
		LoadConfigFile.loadFile();
		
		if (t == 1) {
			this.pro.setMainProcessCode(mainProCode);
			//查询主进程信息，加载到内存
			ProcessBean mainProcess = getMainProcess(mainProCode);
			ProcessObjectesControl.putProcess(mainProcess);
		}
		ProcessObjectesControl.putLocalProcess(this.pro);
		
		
		if (t == 0 || t == -1) {
			// 加载load.xml
			LoadxmlLoad.load();
			AlarmResultLoad alarmLoad = new AlarmResultLoad();
			alarmLoad.load(new HashMap<Object, Object>());
		}
	}

	/**
	 * 查询主进程信息，加载到内存
	 * @param mainProCode1
	 * @return
	 */
	private ProcessBean getMainProcess(String mainProCode1) {
		ProcessBean mainProcess = new ProcessBean();
		String sql = "select * from PROCESS_INFO where process_code = '"+mainProCode1+"' and PROCESS_INFO_STATE = 2";
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		mainProcess.setId(dbr.get(0, "process_code"));
		mainProcess.setCode(dbr.get(0, "process_code"));
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpoplarsoft.process.core.IProcessCore#sendOperatLog()
	 */
	@Override
	public void sendOperatLog() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpoplarsoft.process.core.IProcessCore#sendOperatData()
	 */
	@Override
	public void sendOperatData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 门限判读进程启动函数，启动参数最少一个--进程标识；最多三个--进程标识、备用进程、主进程标识
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AlarmProcess ap = null;
		String proCode;
		int isMain = -1;
		String mainProCode = "";
//		args = new String[]{"4002","1","4001"}; //测试代码
		if (args != null && args.length > 0) {
			proCode = args[0];
			if (args.length > 1) {
				isMain = Integer.valueOf(args[1]);
			}
			if (args.length > 2) {
				mainProCode = args[2];
			}
			ap = new AlarmProcess(proCode, isMain, mainProCode);
			ap.init(isMain);
		}

		// 启动报文接收
		ap.receiveProcessMessage();
		// 启动报文处理轮询器
		ap.executeProcessOrder();
		// 启动心跳发生器，发送心跳
		ap.sendHeartbeat();
		if (isMain == 1) {
			// 备用进程启动监视主进程心跳
			new AlarmHeartbeatTask().start();
		}
	}
}
