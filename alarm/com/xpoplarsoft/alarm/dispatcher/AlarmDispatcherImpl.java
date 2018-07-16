package com.xpoplarsoft.alarm.dispatcher;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.save.ISave;
import com.xpoplarsoft.alarm.operate.AlarmOperate;
import com.xpoplarsoft.alarm.operate.IOperate;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.packages.bean.Param;
import com.xpoplarsoft.packages.bean.ParamData;

/**
 * 报警系统调度器
 * 
 * @author zhouxignlu 2015年9月9日
 */
public class AlarmDispatcherImpl implements IAlarmDispatch, ISave {
	private static Log log = LogFactory.getLog(AlarmDispatcherImpl.class);

	public AlarmDispatcherImpl() {
		
	}

	@Override
	public void dispather(ParamData paramData) {
		// 获取实时遥测工程值
		if (log.isDebugEnabled()) {
			log.debug("接收到参数包：" + paramData.getHead().getDateTime() + "("
					+ paramData.getHead().getDeviceId() + ")");
		}
		String deviceid = null;
		int mid = paramData.getHead().getSatId();

		if (!AlarmCacheUtil.isInit(mid)) {
			return;
		}else{
			deviceid = String.valueOf(AlarmCacheUtil.geiDeviceByMid(mid).getId());
		}
		if(deviceid == null){
			log.error("航天器mid["+mid+"]初始化失败！");
			return;
		}
		synchronized (AlarmDispatcherImpl.class) {
			Map<Integer, Param> params = paramData.getBody().getParamContain();
			if (log.isDebugEnabled()) {
				log.debug("开始执行遥测报警判断，数据时间：" + paramData.getHead().getDateTime());
			}
			for (Integer id : params.keySet()) {
				Param tm = params.get(id);
				alarmOperate(tm, deviceid);
				// 处理规则变更
				// 判断规则缓存中是否有变动的规则
				if (AlarmCacheUtil.isRuleChangeed(deviceid, String.valueOf(id))) {
					alarmOperate(tm, deviceid);
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("参数包：" + paramData.getHead().getDateTime() + "("
						+ paramData.getHead().getDeviceId() + ")处理完成");
			}
		}
		
	}

	private void alarmOperate(Param tm, String deviceid) {
		// 调用报警规则计算组件
		IOperate operate = new AlarmOperate();
		try {
			operate.setAlarmParam(tm, deviceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*// 报警结果管理组件，处理报警结果
		if (operate.isAlarm()) {
			String[] ors = operate.getOperateResult();
			
			
			AlarmResultManager arsm = new AlarmResultManager();
			
			//根据KEY获取报警结果
			AlarmResult alarmResult = AlarmResultCache.getAlarmResult(deviceid + AlarmConst.SPLIT + ors[0]);
			
			if((alarmResult.getLevel() == -1 || alarmResult.getLevel() == 0) && "0".equals(ors[3])){
				//若之前没报警，并且是正常，直接返回
				if (log.isDebugEnabled()) {
					log.debug("参数：[" + tm.getParamId() + "之前没报警，并且是正常，不添加报警结果！]");
				}
				return;
			}
			
			//setAlarmResult中存储日志，孟祥超 update
			//报警时间
			long putTimes = new Date().getTime();
			//插入报警日志
			arsm.saveAlarmLog(deviceid + AlarmConst.SPLIT + ors[0],
					Integer.parseInt(ors[3]), ors[2],putTimes);
			
			arsm.setAlarmResult(deviceid + AlarmConst.SPLIT + ors[0],
					Integer.parseInt(ors[3]), ors[2],
					ors[4].equals(AlarmCacheConst.UPPER));
		}*/
	}

	@Override
	public void save(Comparable<Object> obj) {

		dispather((ParamData) obj);
	}

	// public static void main(String str[]){
	// IAlarmDispatch dispatch = AlarmDispatcherImpl.getDispatcher();
	// ParamData paramData = new ParamData();
	// dispatch.setParamData(paramData);
	// dispatch.dispather();
	// }
}
