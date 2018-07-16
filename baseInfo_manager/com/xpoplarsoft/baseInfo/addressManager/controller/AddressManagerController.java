package com.xpoplarsoft.baseInfo.addressManager.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.addressManager.bean.AddressBean;
import com.xpoplarsoft.baseInfo.addressManager.service.IAddressManagerService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

@Controller
@RequestMapping("/addressManager")
public class AddressManagerController {

	private Gson gson = new Gson();
	
	@Autowired
	private IAddressManagerService service;
	
	/**
	 * 分页列表查询
	 * @param request
	 * @param sat_id
	 * @return
	 */
	@RequestMapping("list")
	public @ResponseBody String list(HttpServletRequest request,String satId,CommonBean bean){
		Map<String,Object> result = service.list(bean,satId);
		return gson.toJson(result);
	}
	
	/**
	 * 新增
	 * @param request
	 * @param AddressBean
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody String add(HttpServletRequest request,AddressBean bean){
		ResultBean rb = new ResultBean();
		boolean result = service.add(bean);
		if(result){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 修改
	 * @param request
	 * @param AddressBean
	 * @return
	 */
	@RequestMapping("update")
	public @ResponseBody String update(HttpServletRequest request,AddressBean bean){
		ResultBean rb = new ResultBean();
		boolean result = service.update(bean);
		if(result){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 查看
	 * @param request
	 * @param pkId
	 * @return
	 */
	@RequestMapping("view")
	public @ResponseBody String view(HttpServletRequest request,String pkId){
		AddressBean result = service.view(pkId);
		return gson.toJson(result);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param ids 多个英文逗号分隔
	 * @return
	 */
	@RequestMapping("delete")
	public @ResponseBody String delete(HttpServletRequest request,String ids){
		ResultBean rb = new ResultBean();
		boolean result = service.delete(ids.split(","));
		if(result){
			rb.setSuccess("true");
			rb.setMessage("删除成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("删除失败！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 判断卫星是否已经存在该类型的组播地址
	 * @param request
	 * @param satId
	 * @param type
	 * @param addressId
	 * @return
	 */
	@RequestMapping("judgeType")
	public @ResponseBody String judgeType(HttpServletRequest request,String satId,String type,String addressId){
		ResultBean rb = new ResultBean();
		Map<String,Object> result = service.queryAddressByType(satId,type,addressId);
		if(result.size() > 0){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return gson.toJson(rb);
	}
	
}
