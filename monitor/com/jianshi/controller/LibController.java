package com.jianshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianshi.service.ILibService;

@Controller
@RequestMapping("/lib")
public class LibController {
	
	@Autowired
	private ILibService libService;
	
	@RequestMapping("addLib")
	public @ResponseBody String addLib(String name,String comment){		
		return libService.addLib(name,comment);
	}
	
	@RequestMapping("editLib")
	public @ResponseBody String editLib(String id,String name,String comment){		
		return libService.editLib(id,name,comment);
	}
	
	@RequestMapping("delLib")
	public @ResponseBody String delLib(String id){		
		return libService.delLib(id)?"T":"F";
	}
	
	@RequestMapping("getLib")
	public @ResponseBody String getLib(String key){		
		return libService.getLib(key!=null?key:"");
	}
	
	@RequestMapping("addLibPlug")
	public @ResponseBody String addLibPlug(String values){		
		return libService.addLibPlug(values)?"T":"F";
	}
	
	@RequestMapping("delLibPlug")
	public @ResponseBody String delLibPlug(String id){		
		return libService.delLibPlug(id)?"T":"F";
	}
	
	@RequestMapping("getSelectLib")
	public @ResponseBody String getSelectLib(String proId){		
		return libService.getSelectLib(proId);
	}
}
