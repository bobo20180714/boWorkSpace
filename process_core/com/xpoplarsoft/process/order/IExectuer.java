package com.xpoplarsoft.process.order;

import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 进程指令、功能指令执行器接口
 * @author zhouxignlu
 * 2017年3月3日
 */
public interface IExectuer extends Runnable {

	public void execute(ProcessData msg);
}
