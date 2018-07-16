/**
 * com.xpoplarsoft.compute.result
 */
package com.xpoplarsoft.compute.result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.adapter.IAdapter;
import com.bydz.fltp.connector.adapter.IUDPAdapter;
import com.bydz.fltp.connector.adapter.config.AdapterConfig;
import com.bydz.fltp.connector.tools.ConnectorTools;
import com.xpoplarsoft.compute.IComputeExecuter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.utils.ClassFactory;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.packages.bean.NoParamBody;
import com.xpoplarsoft.packages.bean.NoParamData;
import com.xpoplarsoft.packages.bean.NoParamHead;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.packages.pack.impl.NoParamPackImpl;
import com.xpoplarsoft.process.core.GetProcessCoreObj;

/**
 * 轨道计算结果处理类，将结果发送到UDP网络中。\br
 * 输入的结算结果数据1: Integer格式卫星任务代号mid \br
 * 输入的结算结果数据2: Map格式，其中必须包含相关信息编号type
 * 
 * @author zhouxignlu 2017年4月26日
 */
public class ResultManager implements IComputeExecuter {
	private static Log log = LogFactory.getLog(ResultManager.class);
	private Map<String, String> data = null;
	private Integer mid = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpoplarsoft.compute.IComputeExecuter#execute()
	 */
	@Override
	public Object execute() {
		if (this.mid == null || this.mid == -1 || this.data == null
				|| this.data.size() == 0) {
			if (log.isDebugEnabled()) {
				log.debug("输入的结果数据内容为空！");
			}
			return null;
		}
		String type = this.data.get("type");

		// 组报文
		NoParamData noParamData = new NoParamData();
		NoParamHead headData = new NoParamHead();
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		headData.setDateTime(sp.format(new Date()));
		headData.setInfoType(type);
		headData.setMid(this.mid);
		int sourId = Integer.parseInt(GetProcessCoreObj.getProcessCore()
				.getProcessBean().getCode());
		headData.setSource(sourId);
		noParamData.setHead(headData);
		NoParamBody body = new NoParamBody();
		body.setXmlData(createXML(type));
		noParamData.setBody(body);
		// 发送报文
		sendData(this.mid, noParamData);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xpoplarsoft.compute.IComputeExecuter#setParameters(java.lang.Object
	 * [])
	 */
	@Override
	public void setParameters(Object... param) {
		this.mid = (Integer) param[0];
		this.data = (Map<String, String>) param[1];

	}

	private String createXML(String type) {
		StringBuilder sb = new StringBuilder();
		sb.append("<list><line>");
		String unTMfiled = "select f.field_id, f.jsjg_id, f.field_name,f.field_code, f.field_type, f.field_length, f.fiel_dscale, f.field_comment from orbit_related_fields f, orbit_related o where f.jsjg_id=o.jsjg_id";
		unTMfiled += " and o.jsjg_code='" + type + "'";
		DBResult fileds = SQLFactory.getSqlComponent().queryInfo(unTMfiled);
		if (fileds != null && fileds.getRows() > 0) {
			for (int j = 0; j < fileds.getRows(); j++) {
				String field_name = fileds.get(j, "field_code");
				String value = "";
				if (this.data.containsKey(field_name)) {
					value = this.data.get(field_name);
				}
				sb.append(createXmlNode(field_name, value));
			}
		}
		sb.append("</line></list>");
		return sb.toString();
	}

	private String createXmlNode(String nodeName, String value) {
		return "<" + nodeName + ">" + value + "</" + nodeName + ">";
	}

	/**
	 * 根据卫星id获取发送地址及端口，并调用数据发送组件
	 * 
	 * @param mid
	 */
	private void sendData(int mid, NoParamData noParamData) {
		String satPort = "select a.pk_id, a.sat_id, a.address, a.port, a.content, a.create_time, a.type,s.mid from sat_address_table a,satellite s "
				+" where a.sat_id = s.sat_id and s.mid = "+mid;
		String address = "";
		String port = "";
		DBResult rs = SQLFactory.getSqlComponent().queryInfo(satPort);
		if (rs != null && rs.getRows() > 0) {
			for (int j = 0; j < rs.getRows(); j++) {
				if (rs.get(j, "type").equals("2")) {
					address = rs.get(j, "address");
					port = rs.get(j, "port");
				}
			}
		} else {
			return;
		}
		AdapterConfig config = new AdapterConfig();
		config.setClassImpl("com.bydz.fltp.connector.adapter.impl.UDPAdapterImpl");
		config.setParameter("address", address);
		config.setParameter("port", port);

		IPack pack = new NoParamPackImpl();
		byte[] send = pack.pack(noParamData);
		// 添加 通信头
		byte[] sendData = ConnectorTools.addProtocolHead(2, send);
		IAdapter adapter = (IAdapter) ClassFactory.getInstance(config
				.getClassImpl());
		adapter.init(config.getParameter());
		IUDPAdapter udpadapter = (IUDPAdapter) adapter;
		udpadapter.multicastSend(sendData);
		if (log.isDebugEnabled()) {
			log.debug("发送的16进制数据为[" + DataTools.bytesToHesString(sendData)
					+ "]");
		}
	}
}
