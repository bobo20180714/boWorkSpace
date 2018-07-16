package com.xpoplarsoft.compute;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.xpoplarsoft.compute.bean.OrderBean;
import com.xpoplarsoft.compute.dao.ComputeDao;
import com.xpoplarsoft.compute.log.ComputeLogFactory;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.service.IServiceExecuter;

/**
 * 控制量计算任务处理类
 * 
 * @author zhouxignlu 2017年3月10日
 */
public class OrderServiceExecute extends Thread implements IServiceExecuter {
	private static Log log = LogFactory.getLog(OrderServiceExecute.class);
	private ProcessData msg;
	private OrderBean order;
	private boolean isThread = true;

	public OrderServiceExecute() {

	}

	public OrderServiceExecute(OrderBean order) {
		this.order = order;
	}

	public void run() {
		this.execute();
	}

	@Override
	public void execute() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 连接数据库获取计算模块定义内容
		ComputeDao dao = new ComputeDao();

		// 获取订单内容
		String orderStr = order.getOrder_content();
		if (log.isDebugEnabled()) {
			log.debug("控制量计算任务" + orderStr + "处理开始");
		}
		// 发送处理开始日志信息
		ComputeLogFactory.getIComputeLog().log(order.getOrder_id(),
				"控制量计算任务[" + order.getOrder_name() + "]处理开始。");
		// 更改控制量计算任务状态为处理中
		dao.setOrderState(order.getOrder_id(), "3", "");
		try {
			// 最大循环次数
			int maxNum = -1;
			// 循环间隔
			int space = -1;
			// 循环停止时刻
			String endtime = null;
			String orderClass = order.getOrder_class();
			// 循环类型
			if (orderClass.equals("1")) {
				maxNum = 1;
			} else if (orderClass.equals("2")) {
				space = Integer.parseInt(order.getLoop_space());
				maxNum = Integer.parseInt(order.getLoop_maxnum());
			} else if (orderClass.equals("3")) {
				space = Integer.parseInt(order.getLoop_space());
				endtime = order.getLoop_endtime();
			} else if (orderClass.equals("4")) {
				space = Integer.parseInt(order.getLoop_space());
				maxNum = Integer.parseInt(order.getLoop_maxnum());
				endtime = order.getLoop_endtime();
			}

			// 循环计数器
			int count = 0;
			while (maxNum == -1 || count < maxNum) {
				doExecure();
				count++;
				if (endtime != null && !"".equals(endtime)) {
					long end = 0l;
					long now = System.currentTimeMillis();
					try {
						end = sf.parse(endtime).getTime();
					} catch (ParseException e) {
						log.error(e);
					}
					if (now >= end) {
						break;
					}
				}
				if (space > 0) {
					// 休眠
					try {
						Thread.sleep(space);
					} catch (InterruptedException e) {
						log.error("控制量计算执行线程休眠异常。", e);
					}
				}
			}
			// 设置控制量计算任务状态为成功
			dao.setOrderState(order.getOrder_id(), "4", "");
		} catch (Exception e) {
			// 设置控制量计算任务状态为失败
			dao.setOrderState(order.getOrder_id(), "5", e.getLocalizedMessage());
			// 发送处理失败日志信息
			log.error("控制量计算任务" + orderStr + "处理失败！", e);
			ComputeLogFactory.getIComputeLog().err(
					order.getOrder_id(),
					"控制量计算任务[" + order.getOrder_name() + "]处理出现异常："
							+ e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("控制量计算任务处理结束!");
		}
	}

	@Override
	public void setProcessServiceBody(ProcessData msgData) {
		this.msg = msgData;
	}

	@Override
	public void setIsThread(boolean isThread) {
		this.isThread = isThread;
	}

	@Override
	public boolean isThread() {
		return this.isThread;
	}

	private void doExecure() throws Exception {
		String className = "";
		List<Object> obj = new ArrayList<Object>();
		if ("1".equals(order.getIs_get_data())) {
			// 参数数据获取
			// 参数数据获取组件类名
			className = order.getGet_data_class_name();
			// 计算参数获取规则定义
			Map<String, Object> inParam = this.xmlToParam(order
					.getGet_data_param());
			// 取得的计算用参数值
			Object paramValue = doExecute(className, inParam);
			obj.add(paramValue);
		}
		// 计算组件类名
		className = order.getComput_class_name();
		// 生成计算对象，并调用计算方法。
		obj.add(this.xmlToParam(order.getComput_param()));
		Object result = doExecute(className, obj.toArray());
		// 计算结果处理
		if ("1".equals(order.getIs_result())) {
			List<Object> inParam = new ArrayList<Object>();
			inParam.add(result);
			// 计算结果处理组件类名
			className = order.getResult_class_name();
			inParam.add(this.xmlToParam(order.getResult_param()));
			doExecute(className, inParam.toArray());
		}
		// 发送处理成功日志信息
		ComputeLogFactory.getIComputeLog().log(order.getOrder_id(),
				"控制量计算任务[" + order.getOrder_name() + "]处理完成。");
	}

	private Object doExecute(String className, Object... param)
			throws Exception {
		IComputeExecuter resultExecuter = null;
		resultExecuter = (IComputeExecuter) Class.forName(className)
				.newInstance();
		resultExecuter.setParameters(param);
		if (log.isDebugEnabled()) {
			log.debug("类[" + className + "]开始运行！");
		}
		return resultExecuter.execute();
	}

	private Map<String, Object> xmlToParam(String xmlStr) throws Exception {
		Map<String, Object> p = new HashMap<String, Object>();
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new ByteArrayInputStream(xmlStr
				.getBytes("UTF-8")));
		List<Element> params = doc.getRootElement().getChildren();
		for (Element e : params) {
			String nodName = e.getName();
			switch (nodName) {
			case "TM":
				List<Element> tms = e.getChild("tmList").getChildren();
				List<String> tmids = new ArrayList<String>();
				for (Element tmid : tms) {
					tmids.add(tmid.getValue());
				}
				p.put(nodName, tmids);
				break;
			case "UNTM":
				List<Element> untms = e.getChildren();
				for (Element untm : untms) {
					List<String> untmids = new ArrayList<String>();
					List<Element> objs = untm.getChildren();
					for (Element untmid : objs) {
						untmids.add(untmid.getValue());
					}
					p.put(untm.getName(), untmids);
				}
				break;
			default:
				p.put(nodName, e.getValue());
				break;
			}
		}
		return p;
	}

	public static void main(String[] args) {
		OrderServiceExecute o = new OrderServiceExecute();

		try {
			Map<String, Object> l = o
					.xmlToParam("<param><satMid>1</satMid><TM><tmList><tmId>1</tmId><tmId>2</tmId></tmList></TM><UNTM><tc><valueID>123456789</valueID><valueID>123456789</valueID></tc><time><valueID>123456789</valueID><valueID>123456789</valueID></time></UNTM><abc>1</abc><qwe>2.2</qwe><timeStart>2001-01-01 01:01:01.000</timeStart><timeEnd>2001-01-01 01:01:02.000</timeEnd></param>");
			for (Object a : l.values()) {
				System.out.println(a);
			}
			o.doExecute("com.xpoplarsoft.compute.test.ComputeTest", l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
