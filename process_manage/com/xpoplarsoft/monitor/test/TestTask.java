package com.xpoplarsoft.monitor.test;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.xpoplarsoft.framework.task.AbstractTask;
import com.xpoplarsoft.framework.task.exception.TaskException;
import com.xpoplarsoft.monitor.bean.TableBean;
import com.xpoplarsoft.monitor.cache.ProcessLogCache;
import com.xpoplarsoft.monitor.cache.ProcessResultCache;
import com.xpoplarsoft.monitor.cache.ProcessRunParamCache;
import com.xpoplarsoft.monitor.process.AnalysisContentUtil;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;

public class TestTask extends AbstractTask{

	private int i = 0;

	//日志时间格式
	public final static String PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private String processCode = "4005";
	
	private static boolean isLoad = false;
	
	@Override
	public void run() throws TaskException {
		i++;
		if(i > 200){
			i = 1;
		}
		String content = "<table><head><name>time</name><title>时间</title></head>"
				+ "<head><name>content</name><title>日志内容</title></head>"
				+ "<row><col><name>time</name><value>"+
				DateFormatUtils.format(new Date(), PATTERN)
				+"</value></col><col><name>content</name><value>"
				+ "业务处理"+i
				+"</value></col></row></table>";
		TableBean tableBean = AnalysisContentUtil.getTableBean(content);
		//将日志信息放入内存中
		ProcessLogCache.getInstance().putLog(System.currentTimeMillis(),processCode, tableBean);
			
		content = "<table><head><name>time</name><title>参数名1</title></head>"
				+ "<head><name>content</name><title>参数名2</title></head>"
				+ "<row><col><name>time</name><value>"+
				DateFormatUtils.format(new Date(), PATTERN)
				+"</value></col><col><name>content</name><value>"
				+ "参数值"+i
				+"</value></col></row></table>";
		TableBean tableBean_param = AnalysisContentUtil.getTableBean(content);
		//将日志信息放入内存中
		ProcessRunParamCache.getInstance().putRunParam(processCode, tableBean_param);
		
		content = "<table><head><name>time</name><title>结果名1</title></head>"
				+ "<head><name>content</name><title>结果名2</title></head>"
				+ "<row><col><name>time</name><value>"+
				"是否报警(IS_ALARM)</value></col><col><name>content</name><value>"
				+ "0"
				+"</value></col></row></table>";
		TableBean tableBean_result= AnalysisContentUtil.getTableBean(content);
		//将日志信息放入内存中
		ProcessResultCache.getInstance().putResult(processCode, tableBean_result);

		if(!isLoad){
			setProcess();
		}
		
		if(i % 5 == 0){
			ProcessBean pro = new ProcessBean();
			pro.setId("4005");
			pro.setCode("4005");
			pro.setSat_num("17");
			if(i % 3 == 0){
				pro.setState("4");
				pro.setErrMessage("报警发生了异常");
			}else{
				pro.setState("0");
			}
			pro.setType("6");
			pro.setClass_name("门限报警");
			ProcessObjectesControl.putProcess(pro);
		}else{
			ProcessBean pro = new ProcessBean();
			pro.setId("4005");
			pro.setCode("4005");
			pro.setSat_num("17");
			if(i % 2 != 0){
				pro.setState("1");
			}else{
				pro.setState("0");
			}
			pro.setType("6");
			pro.setClass_name("门限报警");
			ProcessObjectesControl.putProcess(pro);
		}
	}
	
	private void setProcess() {
		isLoad = true;
		ProcessBean pro = new ProcessBean();
		pro.setId("4005");
		pro.setCode("4005");
		pro.setSat_num("17");
		pro.setState("1");
		pro.setType("6");
		pro.setClass_name("门限报警");
		ProcessObjectesControl.putProcess(pro);
	}
}
