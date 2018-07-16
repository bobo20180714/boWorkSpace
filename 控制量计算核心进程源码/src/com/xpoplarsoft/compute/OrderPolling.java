/**
 * com.xpoplarsoft.compute
 */
package com.xpoplarsoft.compute;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.compute.bean.OrderBean;
import com.xpoplarsoft.compute.classload.ComputeJarLoader;
import com.xpoplarsoft.compute.dao.ComputeDao;
import com.xpoplarsoft.compute.log.ComputeLogFactory;

/**
 * @author zhouxignlu 2017年8月8日
 */
public class OrderPolling extends Thread {
	private static Log log = LogFactory.getLog(OrderPolling.class);

	public void run() {
		this.polling();
	}

	private void polling() {
		// 连接数据库获取计算模块定义内容
		ComputeDao dao = new ComputeDao();
		while (true) {
			ComputeJarLoader cl = new ComputeJarLoader();
			try {
				if (log.isDebugEnabled()) {
					log.debug("加载默认文件夹下的jar包.");
				}
				cl.jarLoad();
			} catch (Exception e1) {
				log.error("jar文件加载失败。" + e1);
			}
			List<OrderBean> orders = dao.get2Order();
			for (OrderBean order : orders) {
				if (order != null && order.getComput_id() != null) {
					OrderServiceExecute orderServiceExecute = new OrderServiceExecute(
							order);
					orderServiceExecute.start();
				} else {
					log.error("未找到控制量计算任务" + order.getOrder_content());
					ComputeLogFactory.getIComputeLog().err(order.getOrder_id(),
							"控制量计算任务[" + order.getOrder_name() + "]未找到。");
				}
			}
			// 休眠10秒
			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				log.error("控制量计算任务轮询线程休眠异常。", e);
			}
		}
	}
}
