/**
 * com.xpoplarsoft.service.client
 */
package com.xpoplarsoft.service.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;

import com.xpoplarsoft.common.util.JsonToMap;
import com.xpoplarsoft.process.order.IExectuer;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessOrderBody;

/**
 * 进程启动更能实现类，可启动exe程序、java程序
 * 
 * @author zhouxignlu 2017年3月23日
 */
public class RuntimeStartupProcess implements IExectuer {
	private static Log log = LogFactory.getLog(RuntimeStartupProcess.class);
	ProcessOrderBody body;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xpoplarsoft.process.order.IExectuer#execute(com.xpoplarsoft.process
	 * .pack.ProcessData)
	 */
	/**
	 * 启动jar包方法 <br>
	 * test.jar的MANIFEST.MF中添加Main-Class:test.Test <br>
	 * java -jar test.jar <br>
	 * 启动class的方法 <br>
	 * java Test 参数1 参数2 参数3
	 */
	@Override
	public void execute(ProcessData msg) {
		if(log.isDebugEnabled()){
			log.debug("RuntimeStartupProcess进程启动业务处理开始执行");
		}
		body = (ProcessOrderBody) msg.getBody();
	}

	/**
	 * 组装进程启动命令
	 * 
	 * @param map
	 *            key: type value:
	 *            exe（windows应用程序）、class（java应用程序）、jar（java应用程序jar包
	 *            ）、bat（批处理程序）、sh（linux应用程序） key: path value:
	 *            (启动文件的完整路径，包含文件名、后缀) key: param value:
	 *            List<</>Object>（程序启动参数，顺序填入）
	 * @return
	 */
	private String command(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		String type = map.get("type").toString();
		switch (type) {
		case "exe":
			break;
		case "class":
			sb.append("java -cp ");
			break;
		case "jar":
			sb.append("startupProcess.bat ");
			break;
		case "bat":

			break;
		case "sh":

			break;
		default:
			break;
		}
		String path = map.get("path").toString();
		sb.append(path);
		sb.append(" "+map.get("processCode"));
		sb.append(" "+map.get("processName"));
		
		List<Map<String, Object>> params = (List<Map<String, Object>>) map
				.get("params");
		if(params != null){
			//由于命令行参数的特殊性，仅取前3个参数
			if(params.size() >= 3){
				for (int i=0;i < 3;i++) {
					Map<String, Object> param = params.get(i);
					sb.append(" ");
					sb.append(param.get("param").toString());
				}
			}else{
				for (int i=0;i<params.size();i++) {
					Map<String, Object> param = params.get(i);
					sb.append(" ");
					sb.append(param.get("param").toString());
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		RuntimeStartupProcess mp = new RuntimeStartupProcess();
		ProcessData msg = new ProcessData();
		ProcessOrderBody body = new ProcessOrderBody();
		body.setLength(10);
		body.setContent("{type:class,path:\"e:/ Test\",processCode:5001,params:[{param:1},{param:2}]}");
		msg.setBody(body);
		// System.out.println(mp.command(JsonToMap.jsonToMap(body.getContent())));
		mp.execute(msg);
		new Thread(mp).start();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (log.isDebugEnabled()) {
			log.debug("进程启动业务处理开始执行！");
		}
		
		if (body.getLength() <= 0 || "".equals(body.getContent())) {
			if (log.isDebugEnabled()) {
				log.debug("进程启动业务报文的报文内容为空。");
			}
			return;
		}
		String json = body.getContent();
		String command = "";
		try {
			Map<String, Object> startup = JsonToMap.jsonToMap(json);
			command = this.command(startup);
			if (command == null || "".equals(command)) {
				log.error("进程启动异常：无启动命令！");
				return;
			}
			if (log.isDebugEnabled()) {
				log.debug("启动命令:["+command+"]");
			}
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
			
		} catch (IOException e) {
			log.error("业务进程启动异常，启动命令：" + command, e);
		} catch (JSONException e) {
			log.error("业务进程启动异常，报文格式有误：" + json, e);
		}
	}
}
