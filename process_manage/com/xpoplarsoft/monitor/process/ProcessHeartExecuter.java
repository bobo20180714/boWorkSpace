package com.xpoplarsoft.monitor.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHeartbeatBody;
import com.xpoplarsoft.service.IServiceExecuter;

/**
 * 处理进程发过来的心跳
 * @author mengxiangchao
 *
 */
public class ProcessHeartExecuter implements IServiceExecuter {

	private static Log log = LogFactory.getLog(ProcessHeartExecuter.class);
	
	private ProcessHeartbeatBody msg;
	
	private int processId;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		//解析进程发过来的心跳报文
		Document doc = null;
		try {
			if(msg !=null && msg.getContent() != null){
				//获取信源对应的进程对象
				ProcessBean processBean = ProcessObjectesControl.getProcess(String.valueOf(processId));
				if(processBean != null){
					doc = DocumentHelper.parseText(msg.getContent());
					Element root = doc.getRootElement();
					
					//判断异常信息是否有变化
					Element eleExcep = root.element("exception");
					if(eleExcep != null){
						String exceptionStr = eleExcep.getStringValue();
						if(exceptionStr != null && !exceptionStr.equals(processBean.getErrMessage())){
							processBean.setErrMessage(exceptionStr);
						}
					}
					//判断是否是主进程是否有变化 （是否是主业务进程，0主进程，1备用进程，-1其他）
					Element eleMainService = root.element("mainService");
					if(eleMainService != null){
						String mainService = eleMainService.getStringValue();
						int isMainInt = Integer.parseInt(mainService);
						if(isMainInt != processBean.getIsMain()){
							processBean.setIsMain(isMainInt);
						}
					}
					//判断主进程标识是否有变化
					Element eleMainProcessCode = root.element("processCode");
					if(eleMainProcessCode != null){
						String mainProcessCode = eleMainProcessCode.getStringValue();
						if(mainProcessCode != null && !mainProcessCode.equals(processBean.getMainProcessCode())){
							processBean.setMainProcessCode(mainProcessCode);
						}
					}
				}
			}
		} catch (DocumentException e) {
			if(log.isErrorEnabled()){
				log.error("组件[ProcessHeartExecuter]解析心跳信息发生异常！");
			}
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws DocumentException {
		String content = "<content><exception><![CDATA[异常信息内容]]></exception><mainService></mainService><processCode></processCode></content>";
		Document doc = DocumentHelper.parseText(content);
		Element root = doc.getRootElement();
		//判断异常信息是否有变化
		Element eleExcep = root.element("exception");
		if(eleExcep != null){
			String exceptionStr = eleExcep.getStringValue();
			System.out.println(exceptionStr);
		}
	}

	@Override
	public void setProcessServiceBody(ProcessData msgData) {
		this.msg = (ProcessHeartbeatBody) msgData.getBody();
		this.processId = msgData.getHead().getSource();
	}

	@Override
	public void setIsThread(boolean isThread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isThread() {
		// TODO Auto-generated method stub
		return false;
	}

}
