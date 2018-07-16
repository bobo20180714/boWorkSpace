package com.xpoplarsoft.process.core;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.bydz.fltp.connector.adapter.AdapterFactory;
import com.bydz.fltp.connector.adapter.IUDPAdapter;
import com.bydz.fltp.connector.config.ConnectConfig;
import com.bydz.fltp.connector.config.ConnectObj;
import com.bydz.fltp.connector.tools.ConnectorTools;
import com.bydz.fltp.connector.udp.UDPServer;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.bean.ProgramBean;
import com.xpoplarsoft.process.config.ExecuterConfig;
import com.xpoplarsoft.process.config.ProAdapterConfigLoad;
import com.xpoplarsoft.process.config.ProConnectorConfigLoad;
import com.xpoplarsoft.process.core.control.ProcessMessageControl;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.core.control.WaitMessageDispose;
import com.xpoplarsoft.process.core.heartbeat.Heartbeat;
import com.xpoplarsoft.process.order.ProcessOrderManager;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessHeartbeatBody;
import com.xpoplarsoft.process.pack.ProcessPack;
import com.xpoplarsoft.service.IServiceExecuter;
import com.xpoplarsoft.service.ServiceExecuterFactory;

/**
 * 线程调度抽象实现类，已实现进程信息初始化，心跳信息产生及发出，心跳信息接收及更新缓存中已有进程状态，报文发出功能，报文接收功能，指令报文处理执行功能
 * 
 * @author zhouxignlu 2017年2月20日
 */
public abstract class ProcessCoreAbstract implements IProcessCore {
	private static Log log = LogFactory.getLog(ProcessCoreAbstract.class);
	protected ProcessBean pro = null;

	/**
	 * 解析processConfig.xml配置文件，生成本地进程信息对象<br>
	 * 将本地进程对象存入进程信息对象管理缓存中<br>
	 * 加载adapter.xml配置文件、connector.xml配置文件<br>
	 * 可通过GetProcessCoreObj.getProcessCore获取进程核心对象
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void init() {
		if (log.isDebugEnabled()) {
			log.debug("[ProcessCoreAbstract]启动开始！");
		}
		pro = new ProcessBean();
		// 自动初始化为空闲进程
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(ProAdapterConfigLoad.class
					.getClassLoader().getResourceAsStream("processConfig.xml"));
			load(doc);
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("解析进程调度[processConfig.xml]配置文件发生异常", e);
			}
			return;
		}
		// 将初始化的本地进程存入缓存中
		ProcessObjectesControl.putLocalProcess(pro);
		// 初始化进程使用的UDP配置
		ProAdapterConfigLoad ac = new ProAdapterConfigLoad();
		ac.load(new HashMap());
		ProConnectorConfigLoad cc = new ProConnectorConfigLoad();
		cc.load(new HashMap());
		// 全局获取进程对象
		GetProcessCoreObj.putIProcessCore(this);
	}

	/**
	 * 轮询本地进程信息对象的状态，产生进程心跳报文，并发送
	 */
	@Override
	public void sendHeartbeat() {
		Heartbeat hb = new Heartbeat(pro);
		hb.start();
	}

	@Override
	public void sendProcessOrder(ProcessData msg) {
		// 将需要反馈的报文存入报文缓存器
		if (msg.getHead().getNeedReedback() == 1) {
			ProcessMessageControl.putSendMsg(msg);
		}
		// 向网络中广播指令
		IPack pack = new ProcessPack();
		byte[] send = pack.pack(msg);
		// 添加 通信头
		byte[] sendData = ConnectorTools.addProtocolHead(3, send);
		IUDPAdapter adapter = (IUDPAdapter) AdapterFactory.getFactory()
				.getAdapterComponent(pro.getAdapterName());
		adapter.multicastSend(sendData);
	}

	/**
	 * 启动报文接收线程，将接收到的字节流转为报文对象，放入报文缓存区等待后续处理
	 */
	@Override
	public void receiveProcessMessage() {
		// 启动进程调度报文接受线程
		ConnectObj obj = ConnectConfig.getInstance().getParameter(
				pro.getConnectorName());

		UDPServer udpServer = new UDPServer();
		udpServer.init(obj);
		new Thread(udpServer).start();
	}

	/**
	 * 启动调度指令、业务指令处理线程，轮询报文缓存区，启动处理程序。
	 */
	@Override
	public void executeProcessOrder() {
		// 启动指令处理执行线程，扫描报文缓存器
		WaitMessageDispose wmd = new WaitMessageDispose(pro, this);
		new Thread(wmd).start();
		ProcessOrderManager proOrder = new ProcessOrderManager(pro, this);
		new Thread(proOrder).start();
	}

	@Override
	public ProcessBean getProcessBean() {
		return pro;
	}

	private void load(Document doc) {
		try {

			Element rootEl = doc.getRootElement();
			Element propertys = rootEl.getChild("property");
			String beatInterval = propertys.getChild("beatInterval")
					.getAttributeValue("value");
			String code = propertys.getChild("code").getAttributeValue("value");
			String protype = propertys.getChild("type").getAttributeValue(
					"value");
			String class_name = propertys.getChild("class_name")
					.getAttributeValue("value");
			String targetId = propertys.getChild("targetId").getAttributeValue(
					"value");
			String adapterName = propertys.getChild("adapterName")
					.getAttributeValue("value");
			String connectorName = propertys.getChild("connectorName")
					.getAttributeValue("value");
			String reedbackInterval = propertys.getChild("reedbackInterval")
					.getAttributeValue("value");
			String limit = propertys.getChild("limit").getAttributeValue(
					"value");

			pro.setAdapterName(adapterName);
			pro.setType(protype);
			pro.setClass_name(class_name);
			pro.setCode(code);
			pro.setConnectorName(connectorName);
			pro.setRound(beatInterval);
			pro.setTargetId(targetId);
			pro.setReedbackInterval(Integer.valueOf(reedbackInterval));
			pro.setLimit(Integer.valueOf(limit));

			List<Element> programList = rootEl.getChildren("program");
			for (Element e2 : programList) {
				String classPath = e2.getAttributeValue("classPath");
				String method = e2.getAttributeValue("method");
				String isThtead = e2.getAttributeValue("isThtead");
				String size = e2.getAttributeValue("size");

				ProgramBean programBean = new ProgramBean();
				programBean.setClassPath(classPath);
				programBean.setMethod(method);
				programBean.setSize(Integer.parseInt(size));
				programBean.setThtead("true".equals(isThtead));
				List<Element> argList = rootEl.getChildren("arg");
				for (Element e1 : argList) {
					String type = e1.getAttributeValue("type");
					programBean.addArg(type);
				}

				pro.addProgramBean(programBean);
			}
			List<Element> executers = rootEl.getChild("executers").getChildren(
					"executer");
			for (Element e1 : executers) {
				ExecuterConfig.putExecuter(
						Integer.valueOf(e1.getAttributeValue("type")),
						e1.getAttributeValue("class"));
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("解析[processConfig.xml]配置文件发生异常", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xpoplarsoft.process.core.IProcessCore#receiveHeartbeat(com.xpoplarsoft
	 * .process.pack.ProcessData)
	 */
	@Override
	public void receiveHeartbeat(ProcessData processData) {
		ProcessHead head = processData.getHead();
		String sourceProcessIdStr = String.valueOf(head.getSource());
		ProcessBean processBean = ProcessObjectesControl
				.getProcess(sourceProcessIdStr);
		if (processBean == null)
			return;
		ProcessHeartbeatBody body = (ProcessHeartbeatBody) processData
				.getBody();
		String processState = String.valueOf(body.getState());
		processBean.setLife_time(System.currentTimeMillis());
		// 进程状态是否有变化
		if (!processState.equals(processBean.getState())) {
			processBean.setState(processState);
		}
		IServiceExecuter service = ServiceExecuterFactory
				.create(IProcessCore.HEART_SERVICE);
		if (service != null) {
			service.setProcessServiceBody(processData);
			service.execute();
		}
	}
}
