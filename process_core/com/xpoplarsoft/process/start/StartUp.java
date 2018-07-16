package com.xpoplarsoft.process.start;



public class StartUp {
	
	public static void main(String[] args) {
		BlankProcess blankPro = new BlankProcess();
		//初始化进程
		blankPro.init();
		
		
		//设置卫星任务号
		blankPro.getProcessBean().setSat_num("101");
		blankPro.getProcessBean().setState("1");
		//启动报文接收
		blankPro.receiveProcessMessage();
		/*//发送进程注册
		ProcessJoin pj = new ProcessJoin(blankPro.getProcessBean()); 
		blankPro.sendProcessOrder(pj.createJoinMsg());*/
		//启动心跳发生器，发送心跳
		blankPro.sendHeartbeat();
		//启动报文处理轮询器
		blankPro.executeProcessOrder();
	}
}
