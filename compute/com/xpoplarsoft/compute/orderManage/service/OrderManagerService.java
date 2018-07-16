package com.xpoplarsoft.compute.orderManage.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bydz.fltp.connector.adapter.AdapterFactory;
import com.bydz.fltp.connector.adapter.IUDPAdapter;
import com.bydz.fltp.connector.tools.ConnectorTools;
import com.xpoplarsoft.baseinfoquery.bean.TmparamsBean;
import com.xpoplarsoft.baseinfoquery.service.IBaseInfoQueryService;
import com.xpoplarsoft.compute.orderManage.bean.FuncParam;
import com.xpoplarsoft.compute.orderManage.bean.OrderBean;
import com.xpoplarsoft.compute.orderManage.dao.IOrderManagerDao;
import com.xpoplarsoft.constant.BusinessTypeEnum;
import com.xpoplarsoft.constant.ProcessConstant;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessPack;
import com.xpoplarsoft.process.pack.ProcessServiceBody;
import com.xpoplarsoft.query.utils.FlowNoFactory;
import com.xpoplarsoft.tool.DBResultUtil;

@Service
public class OrderManagerService implements IOrderManagerService {

	private static Log log = LogFactory.getLog(OrderManagerService.class);
	
	@Autowired
	private IOrderManagerDao dao;
	
	@Autowired
	private IBaseInfoQueryService baseInfoQueryService;
	
	@Override
	public Map<String, Object> list(CommonBean bean,String satId,String computeId,  String typeId,String orderName,
			String timeStart, String timeEnd) {
		DBResult dbr = dao.list(bean,satId,computeId, typeId,orderName,timeStart,timeEnd);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean add(OrderBean bean) {
		
		//生成订单编号
		bean.setOrderId(FlowNoFactory.getInstance().getFlowNo());
		
		return dao.add(bean);
	}

	@Override
	public boolean publish(String orderId,String satMid, String orderName) {
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerService][publish]开始执行！");
		}
		//进程调度对象
		ProcessData processData = new ProcessData();
		
		//业务类型
		int businessType = BusinessTypeEnum.BUSINESS_MESSAGE.getType();
		if(log.isInfoEnabled()){
			log.info("初始化业务包头！");
		}
		ProcessHead head = new ProcessHead();
		head.setDateTime(String.valueOf(System.currentTimeMillis()));
		//业务类型
		head.setType(businessType);
		//是否需要反馈
		head.setNeedReedback(ProcessConstant.REBACK_NO_NEED);
		//信源，
		head.setSource(-1);
		//信宿，接收方进程标识，系统预先定义
		String target = SystemParameter.getInstance().getParameter("computerCode");
		head.setTarget(target==null?0:Integer.parseInt(target));
		processData.setHead(head);
		
		if(log.isDebugEnabled()){
			log.debug("指令信息head=["+head.toString()+"]");
		}
		
		if(log.isInfoEnabled()){
			log.info("初始化业务包体！");
		}
		ProcessServiceBody body = new ProcessServiceBody();
		body.setServiceCode("compute");
		//业务类型
		body.setType(businessType);
		//航天器任务号
		body.setSatNum(Integer.parseInt(satMid));
		//发送次数
		body.setSendNum(1);
		String content = "{order_name:'"+orderName+"',order_id:'"+orderId+"'}";
		body.setContent(content);
		body.setLength(content.length());
		processData.setBody(body);
		if(log.isDebugEnabled()){
			log.debug("指令信息body=["+body.toString()+"]");
		}
		// 向网络中广播指令
		IPack pack = new ProcessPack();
		byte[] send = pack.pack(processData);
		// 添加 通信头
		byte[] sendData = ConnectorTools.addProtocolHead(3, send);
		IUDPAdapter adapter = (IUDPAdapter) AdapterFactory.getFactory()
				.getAdapterComponent("orderAdapter");
		adapter.multicastSend(sendData);
		
		updateSate(orderId, 3);
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerService][publish]执行结束！");
		}
		return true;
	}

	@Override
	public boolean updateSate(String orderId,int orderState) {
		return dao.updateSate(orderId,orderState);
	}

	@Override
	public Map<String, Object> view(String orderId) {
		DBResult dbr = dao.view(orderId);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public OrderBean getOrderDetail(String orderId) {
		OrderBean bean = new OrderBean();
		
		DBResult dbr = dao.view(orderId);
		if(dbr != null && dbr.getRows() > 0){
			bean.setPkId(dbr.get(0, "pk_id"));
			bean.setOrderId(dbr.get(0, "order_id"));
			bean.setOrderName(dbr.get(0, "order_name"));
			bean.setSatId(dbr.get(0, "sat_id"));
			bean.setSatMid(dbr.get(0, "sat_mid"));
			bean.setSatName(dbr.get(0, "sat_name"));
			bean.setOrderContent(dbr.get(0, "order_content"));
			bean.setIsGetData(dbr.get(0, "is_get_data"));
			bean.setGetDataId(dbr.get(0, "get_data_id"));
			bean.setGetDataFunName(dbr.get(0, "getdata_func_name"));
			bean.setGetDataParam(dbr.get(0, "get_data_param"));
			bean.setComputId(dbr.get(0, "comput_id"));
			bean.setComputFunName(dbr.get(0, "comput_func_name"));
			bean.setComputParam(dbr.get(0, "comput_param"));
			bean.setIsResult(dbr.get(0, "is_result"));
			bean.setResultId(dbr.get(0, "result_id"));
			bean.setResultFunName(dbr.get(0, "result_func_name"));
			bean.setResultParam(dbr.get(0, "result_param"));
			//解析获取数据函数参数
			if("1".equals(bean.getIsGetData())){
				bean.setGetDataParamList(analysisParam(bean.getGetDataParam()));
			}
			//解析数据计算函数参数
			bean.setComputParamList(analysisParam(bean.getComputParam()));
			//解析数据处理函数参数
			if("1".equals(bean.getIsResult())){
				bean.setResultParamList(analysisParam(bean.getResultParam()));
			}
		}
		
		
		return bean;
	}

	/**
	 * 解析获取数据函数参数
	 * @param getDataParam
	 * 
	 *    <param>
				<satMid>1</satMid>
				<TM>
					<tmList>
						<tmId>1</tmId>
						<tmId>2</tmId>
					</tmList>
				</TM>
				<paramValue>1</paramValue>
				<timeStart>2001-01-01 01:01:01.000</timeStart>
				<timeEnd>2001-01-01 01:01:02.000</timeEnd>
				<paramValue>1</paramValue>
			</param>
	 * @return
	 */
	private List<FuncParam> analysisParam(String xmlStr) {
		List<FuncParam> getDataParamList = new ArrayList<FuncParam>();
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try {
			doc = builder.build(new ByteArrayInputStream(xmlStr.getBytes()));
			List<Element> paramsEleList = doc.getRootElement().getChildren();
			FuncParam fp = null;
			Element ele = null;
			for (int m = 0; m < paramsEleList.size(); m++) {
				ele = paramsEleList.get(m);
				fp = new FuncParam();
				if("satMid".equals(ele.getName())){
					fp.setSatMid(ele.getTextTrim());
					fp.setParamType("0");
				}else if("TM".equals(ele.getName())){
					Element tmList = ele.getChild("tmList");
					List<Element> tmIdList = tmList.getChildren("tmId");
					String codes = "";
					String paramIdStrs = "";
					Element tmIdEle = null;
					for (int i = 0; i < tmIdList.size(); i++) {
						tmIdEle = tmIdList.get(i);
						String tmId = tmIdEle.getValue();
						//查询参数编号
						TmparamsBean paramBean = baseInfoQueryService.getTMParamById(tmId);
						if(i == tmIdList.size() - 1){
							paramIdStrs = paramIdStrs + tmId;
							codes = codes + paramBean.getTm_param_code();
						}else{
							paramIdStrs = paramIdStrs + tmId + ";";
							codes = codes + paramBean.getTm_param_code() + ";";
						}
					}
					fp.setParamName(ele.getName());
					fp.setParamValue(codes);
					fp.setParamHideValue(paramIdStrs);
					fp.setParamType("1");
				}else if("UNTM".equals(ele.getName())){
					List<Element> eleList = ele.getChildren();
					
					String codes = "";
					Element tmIdEle = null;
					for (int i = 0; i < eleList.size(); i++) {
						tmIdEle = eleList.get(i);
						//相关信息编号
						String typeCode = tmIdEle.getName();
						codes = codes + typeCode;
						List<Element> valueIDEleList = tmIdEle.getChildren();
						Element valueIDEle = null;
						for (int j = 0; j < valueIDEleList.size(); j++) {
							valueIDEle = valueIDEleList.get(j);
							String valueID = valueIDEle.getValue();
							if(j == 0){
								codes = codes + ":" + valueID;
							}else{
								codes = codes + "," + valueID;
							}
						}
					}
					fp.setParamName(ele.getName());
					fp.setParamValue(codes);
					fp.setParamHideValue(codes);
					fp.setParamType("3");
				}else{
					fp.setParamName(ele.getName());
					fp.setParamValue(ele.getTextTrim());
					fp.setParamHideValue(ele.getTextTrim());
					fp.setParamType("2");
				}
				getDataParamList.add(fp);
			}
		}catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("[analysisGetDataParam]解析["+xmlStr+"]发生异常！");
			}
			e.printStackTrace();
		}
		return getDataParamList;
	}

	@Override
	public boolean update(OrderBean bean) {

		return dao.update(bean);
	}

	@Override
	public OrderBean viewByPkId(String pkId) {
		OrderBean bean = new OrderBean();
		
		DBResult dbr = dao.viewByPkId(pkId);
		if(dbr != null && dbr.getRows() > 0){
			bean.setPkId(dbr.get(0, "pk_id"));
			bean.setOrderId(dbr.get(0, "order_id"));
			bean.setOrderName(dbr.get(0, "order_name"));
			bean.setSatId(dbr.get(0, "sat_id"));
			bean.setSatMid(dbr.get(0, "sat_mid"));
			bean.setOverTime(dbr.get(0, "over_time"));
			bean.setComputeCount(dbr.get(0, "compute_count"));
			bean.setSatName(dbr.get(0, "sat_name"));
			bean.setOrderContent(dbr.get(0, "order_content"));
			bean.setComputId(dbr.get(0, "comput_id"));
			bean.setComputFunName(dbr.get(0, "comput_func_name"));
			bean.setComputParam(dbr.get(0, "comput_param"));
			bean.setTime(dbr.get(0, "time"));
			
			bean.setOrder_class(dbr.get(0, "order_class"));
			bean.setLoop_space(dbr.get(0, "loop_space"));
			bean.setLoop_maxnum(dbr.get(0, "loop_maxnum"));
			bean.setLoop_endtime(dbr.get(0, "loop_endtime"));
			
			//解析数据计算函数参数
			bean.setComputParamList(analysisParam(bean.getComputParam()));
		}
		
		return bean;
	}

}
