package com.xpoplarsoft.compute.functionManage.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionParam;
import com.xpoplarsoft.compute.functionManage.service.IFieldService;
import com.xpoplarsoft.compute.functionManage.service.IFunctionManageService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

/**
 * 注册计算模块管理
 * @author mengxiangchao
 *
 */
@Controller
@RequestMapping("/functionManage")
public class FunctionManageController {

	private static Log log = LogFactory.getLog(FunctionManageController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IFunctionManageService service;

	@Autowired
	private IFieldService fieldService;
	
	/**
	 * 分页查询函数列表
	 * @param functionName
	 * @param functionCode
	 * @param className
	 * @param bean
	 * @return
	 */
	@RequestMapping("/functionList")
	public @ResponseBody String functionList(String functionName,String functionCode,String className,CommonBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][functionList]开始执行！");
		}
		Map<String,Object> rsMap = service.functionList(functionName,functionCode,className,bean);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][functionList]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	
	/**
	 * 获取计算模块信息
	 * @param fid
	 * @return
	 */
	@RequestMapping("/queryFunction")
	public @ResponseBody String queryFunction(String fid){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][queryFunction]开始执行！");
		}
		FunctionBean rsMap = service.queryFunction(fid);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][queryFunction]执行完成！");
		}
		return gson.toJson(rsMap);
	}

	/**
	 * 移除函数
	 * @param functionId
	 * @return
	 */
	@RequestMapping("/deleteFunction")
	public @ResponseBody String deleteFunction(String functionId){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][deleteFunction]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.deleteFunction(functionId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][deleteFunction]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 分页查询函数的参数列表
	 * @param functionId
	 * @param bean
	 * @return
	 */
	@RequestMapping("/paramList")
	public @ResponseBody String paramList(String functionId,CommonBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][paramList]开始执行！");
		}
		Map<String,Object> rsMap = service.paramList(functionId,bean);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][paramList]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	
	
	/**
	 * 新增函数
	 * @param FunctionBean
	 * @return
	 */
	@RequestMapping("/addFunction")
	public @ResponseBody String addFunction(FunctionBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][addFunction]开始执行！");
		}
		ResultBean rb = new ResultBean();
		
		/*String tableName = null;
		if("1".equals(bean.getIsSaveResult())){
			//存库，生成表名称
			tableName = bean.getFctCode()+"_"+service.getPkId();
			bean.setTableName(tableName);
		}*/
		
		int fid = service.addFunction(bean);
		
		/*//获取字段信息
		List<Map<String,String>> list = bean.getFieldList();
		if(list != null && tableName != null){
			List<FieldBean> fieldBeanlist = new ArrayList<FieldBean>();
			FieldBean fieldBean = null;
			for (int i = 0; i < list.size(); i++) {
				fieldBean = new FieldBean();
				fieldBean.setFid(fid);
				fieldBean.setFieldComment(list.get(i).get("fieldComment"));
				fieldBean.setFieldLength(Integer.parseInt(list.get(i).get("fieldLength")));
				if(list.get(i).get("fieldScale") != null
						&& !"".equals(list.get(i).get("fieldScale"))){
					fieldBean.setFieldScale(Integer.parseInt(list.get(i).get("fieldScale")));
				}
				fieldBean.setFieldName(list.get(i).get("fieldName"));
				fieldBean.setFieldType(Integer.parseInt(list.get(i).get("fieldType")));
				fieldBeanlist.add(fieldBean);
			}
			if(fieldBeanlist.size() > 0){
				fieldService.addFieldList(fieldBeanlist);
				service.createTable(tableName,fieldBeanlist);
			}
		}*/
		
		if(fid != -1){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		rb.setData(fid);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][addFunction]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 修改函数
	 * @param FunctionBean
	 * @return
	 */
	@RequestMapping("/updateFunction")
	public @ResponseBody String updateFunction(FunctionBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][updateFunction]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.updateFunction(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][updateFunction]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 查询参数信息
	 * @param FunctionBean
	 * @return
	 */
	@RequestMapping("/getFunctionParam")
	public @ResponseBody String getFunctionParam(String fieldId){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][getFunctionParam]开始执行！");
		}
		FunctionParam param = service.getFunctionParam(fieldId);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][getFunctionParam]执行完成！");
		}
		return gson.toJson(param);
	}
	
	/**
	 * 修改参数信息
	 * @param FunctionBean
	 * @return
	 */
	@RequestMapping("/updateParam")
	public @ResponseBody String updateParam(FunctionParam bean){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][updateParam]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.updateParam(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][updateParam]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 上移
	 * @param fieldId
	 * @return
	 */
	@RequestMapping("/upParam")
	public @ResponseBody String upParam(int fieldId,int paramOrder,int fid){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][upParam]开始执行！");
		}
		ResultBean rb = new ResultBean();

		int newParamOrder = paramOrder - 1;
		//查询前一参数的fieldId
		FunctionParam param = service.queryParamByOrder(fid,newParamOrder);
		//修改前一参数的序号
		service.updateParamOrder(param.getFieldId(),paramOrder);
		//修改当前参数的序号
		service.updateParamOrder(fieldId,newParamOrder);
		
		rb.setSuccess("true");
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][upParam]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 下移
	 * @param fieldId
	 * @return
	 */
	@RequestMapping("/downParam")
	public @ResponseBody String downParam(int fieldId,int paramOrder,int fid){
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][downParam]开始执行！");
		}
		ResultBean rb = new ResultBean();

		int newParamOrder = paramOrder + 1;
		//查询前一参数的fieldId
		FunctionParam param = service.queryParamByOrder(fid,newParamOrder);
		//修改前一参数的序号
		service.updateParamOrder(param.getFieldId(),paramOrder);
		//修改当前参数的序号
		service.updateParamOrder(fieldId,newParamOrder);
		rb.setSuccess("true");
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageController][downParam]执行完成！");
		}
		return gson.toJson(rb);
	}
	
}
