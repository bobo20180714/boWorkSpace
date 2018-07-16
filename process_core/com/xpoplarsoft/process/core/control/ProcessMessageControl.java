package com.xpoplarsoft.process.core.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 进程报文缓存控制类。临时存储本进程发出、接收的报文。
 * 需要反馈的发出报文被存储在缓存中，满足以下3种条件后可以删除：
 * 	1、接收到对应反馈报文；
 *  2、超时后重新发送；
 *  3、超出重发次数；
 * 接收到的报文首先放入缓存中，在交付处理程序后删除。
 * @author zhouxignlu
 * 2017年2月27日
 */
public class ProcessMessageControl {

	private static Map<Integer,ProcessData> sendMsg = new Hashtable<Integer,ProcessData>();
	private static Map<Integer,ProcessData> receiveMsg = new Hashtable<Integer,ProcessData>();
	
	/**
	 * 向缓存中添加已发送的进程调度报文
	 * @param msg
	 */
	public static void putSendMsg(ProcessData msg){
		sendMsg.put(msg.getBody().getMessageId(), msg);
	}
	
	/**
	 * 向缓存中添加已接收的进程调度报文
	 * @param msg
	 */
	public static void putReceiveMsg(ProcessData msg){
		receiveMsg.put(msg.getBody().getMessageId(), msg);
	}
	
	/**
	 * 根据报文编号获取已发送进程调度报文
	 * @param msgId
	 * @return
	 */
	public static ProcessData getSendMsg(int msgId){
		return sendMsg.get(msgId);
	}
	
	/**
	 * 根据报文编号获取已收取进程调度报文
	 * @param msgId
	 * @return
	 */
	public static ProcessData getReceiveMsg(int msgId){
		return receiveMsg.get(msgId);
	}
	
	public static void removeSendMsg(int msgId){
		sendMsg.remove(msgId);
	}
	
	public static void removeReceiveMsg(int msgId){
		receiveMsg.remove(msgId);
	}
	
	public static Collection<ProcessData> removeAllSendMsg(){
		Collection<ProcessData> list = new ArrayList<ProcessData>();
		list.addAll(sendMsg.values());
		sendMsg.clear();
		return list;
	}
	
	public static Collection<ProcessData> removeAllReceiveMsg(){
		Collection<ProcessData> list = new ArrayList<ProcessData>();
		list.addAll(receiveMsg.values());
		receiveMsg.clear();
		return list;
	}
	
	public static Set<Integer> getAllSendMsgId(){
		return sendMsg.keySet();
	}
	
	public static Set<Integer> getAllReceiveMsgId(){
		return receiveMsg.keySet();
	}
}
