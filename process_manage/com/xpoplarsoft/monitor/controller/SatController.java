package com.xpoplarsoft.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.monitor.service.ISatService;

/**
 * 卫星信息查询
 * @author mengxiangchao
 *
 */
@Controller
@RequestMapping("/satController")
public class SatController {


	private static Log log = LogFactory.getLog(SatController.class);
	
	private static Gson gson = new Gson();

	@Autowired
	private ISatService service;
	
	@RequestMapping("getSatGroupTree")
	public @ResponseBody String getSatGroupTree(String satMid){
		
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("id", "1");
		dataMap.put("name", "试验号卫星组");
		dataMap.put("parent_id", "");
		dataMap.put("type", "group");
		dataMap.put("childMids", "1;2");
		rsList.add(dataMap);
		
		dataMap = new HashMap<String,Object>();
		dataMap.put("id", "11");
		dataMap.put("name", "试验一号(XY-1)");
		dataMap.put("satMid", "1");
		dataMap.put("parent_id", "1");
		dataMap.put("type", "sat");
		rsList.add(dataMap);
		dataMap = new HashMap<String,Object>();
		dataMap.put("id", "22");
		dataMap.put("name", "试验二号(XY-2)");
		dataMap.put("parent_id", "1");
		dataMap.put("satMid", "2");
		dataMap.put("type", "sat");
		rsList.add(dataMap);

		dataMap = new HashMap<String,Object>();
		dataMap.put("id", "2");
		dataMap.put("name", "风云号卫星组");
		dataMap.put("parent_id", "");
		dataMap.put("type", "group");
		dataMap.put("childMids", "3");
		rsList.add(dataMap);

		
		dataMap = new HashMap<String,Object>();
		dataMap.put("id", "33");
		dataMap.put("name", "风云一号(FY-1)");
		dataMap.put("parent_id", "2");
		dataMap.put("satMid", "3");
		dataMap.put("type", "sat");
		rsList.add(dataMap);
		
		return gson.toJson(rsList);
	}
}
