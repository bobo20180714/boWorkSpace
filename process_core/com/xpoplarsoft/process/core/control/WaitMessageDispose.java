package com.xpoplarsoft.process.core.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.IProcessCore;
import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 待反馈报文对象处理器,获取并删除缓存中的所有待反馈报文对象，轮询每一个报文对象。
 * 时限内未收到反馈，且(重发报文次数未达到上限或无无限重发)，重新发送报文，重发次数加1。
 * 
 * @author zhouxignlu 2017年3月8日
 */
public class WaitMessageDispose implements Runnable {
	private static Log log = LogFactory.getLog(WaitMessageDispose.class);

	ProcessBean proBean = null;
	IProcessCore pro = null;
	long waitTime = 1000;

	public WaitMessageDispose(ProcessBean proBean, IProcessCore pro) {
		this.pro = pro;
		this.proBean = proBean;
		this.waitTime = proBean.getReedbackInterval();
	}

	@Override
	public void run() {
		SimpleDateFormat sp = new SimpleDateFormat("");
		while (true) {
			if (log.isDebugEnabled()) {
				log.debug("开始处理待反馈报文。");
			}
			Collection<ProcessData> msgs = new ArrayList<ProcessData>();
			msgs.addAll(ProcessMessageControl.removeAllSendMsg());
			for (ProcessData msg : msgs) {
				if (!msg.getBody().isHasReedBack()) {
					try {
						long oldTimes = sp.parse(msg.getHead().getDateTime())
								.getTime();
						int sendNum = msg.getBody().getSendNum();
						// 计算该报文多久未收到反馈报文
						long t = System.currentTimeMillis() - oldTimes;
						// 时限内未收到反馈，且(重发报文次数未达到上限或无无限重发)，重新发送报文，重发次数加1
						if (t >= msg.getBody().getRbLimitTimes()
								&& (proBean.getLimit() > 0
										&& sendNum < proBean.getLimit() || proBean
										.getLimit() == -1)) {
							sendNum++;
							msg.getBody().setSendNum(sendNum);
							pro.sendProcessOrder(msg);
						}
					} catch (ParseException e) {
						log.error("待反馈报文" + msg + "重新发送失败！", e);
					}

				}
			}
			if (log.isDebugEnabled()) {
				log.debug("待反馈报文处理完成。");
			}
			try {
				///wait(1000);
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				log.error("待反馈报文处理线程异常中断！", e);
				e.printStackTrace();
			}
		}
	}

}
