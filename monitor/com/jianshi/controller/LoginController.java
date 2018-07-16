package com.jianshi.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianshi.service.ILoginService;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private int i = 0;
	
	@Autowired
	private ILoginService loginService;
	
	@RequestMapping("login")
	public @ResponseBody String login(HttpSession session,String name,String password,String rand){
		
		if(i != 0){
			i = 0;
			return "";
		}

		int ret=loginService.exist(name);
		if(ret==0){
			return "{success:false,msg:'用户名不存在！'}";
		}
		if(ret==-1){
			return "{success:false,msg:'数据库异常！'}";
		}		
		Map<String, Object> user=loginService.getUser(name,password);
		if(user!=null){
			LoginUserBean loginBean = new LoginUserBean();
			loginBean.setUserId(user.get("id").toString());
			loginBean.setUserName(user.get("name").toString());
			/*Object randObj=session.getAttribute("randCode");
			if(randObj!=null&&!rand.toUpperCase().equals(randObj)){
				return "{success:false,msg:'验证码错误！'}";
			}*/
			session.setAttribute("loginInfo", "您好！"+user.get("name"));
			session.setAttribute("LoginUser", loginBean);
			i++;
			return "{success:true,userType:'0'}";
		}
		return "{success:false,msg:'密码错误！'}";
	}
	
	@RequestMapping("editPsd")
	public @ResponseBody String editPsd(HttpSession session,String oldPsd,String newPsd){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		return loginService.editPsd(user.getUserId(),oldPsd,newPsd);
	}
}
