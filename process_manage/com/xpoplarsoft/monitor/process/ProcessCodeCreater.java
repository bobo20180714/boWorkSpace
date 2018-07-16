/**
 * com.xpoplarsoft.monitor.process
 */
package com.xpoplarsoft.monitor.process;

import java.util.Collection;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;

/**
 * 进程标识生成器，获取所有已注册的进程对象，匹配进程类型，同类型中顺序号+1
 * 
 * @author zhouxignlu 2017年3月21日
 */
public class ProcessCodeCreater {

	/**
	 * 同步方法
	 * 轮询缓存中的进程对象，找出全部类型相同的进程，将进程标识号后3位最大的+1，形成新的进程标识
	 * @param type
	 * @return
	 */
	public static synchronized String createCode(String type) {
		Collection<ProcessBean> proList = ProcessObjectesControl
				.getProcessList();
		int maxNum = 0;
		for (ProcessBean pro : proList) {
			String code = pro.getCode();
			if (code.substring(0, code.length() - 3).equals(type)) {
				int temp = Integer.parseInt(code.substring(code.length() - 3));
				if(maxNum < temp){
					maxNum = temp;
				}
			}
		}
		String num = String.valueOf(++maxNum);
		while(num.length()<3){
			num = "0"+num;
		}
		return type + num;
	}
}
