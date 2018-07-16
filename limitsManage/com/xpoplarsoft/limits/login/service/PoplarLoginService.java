package com.xpoplarsoft.limits.login.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.security.Md5;
import com.xpoplarsoft.limits.login.bean.LoginBean;
import com.xpoplarsoft.limits.login.dao.ILoginDao;
import com.xpoplarsoft.limits.resources.bean.Resources;
import com.xpoplarsoft.limits.resources.service.IResourcesService;
import com.xpoplarsoft.limits.role.service.IRoleService;

@Service
public class PoplarLoginService implements ILoginService {

	@Autowired
	public ILoginDao loginDao;

	@Autowired
	public IResourcesService resService;

	@Autowired
	public IRoleService roleService;

	/**
	 * 登陆验证
	 * 
	 * @param userId
	 * @param passwd
	 * @return
	 */
	public boolean loginUser(LoginBean loginBean) {

		loginBean.setPassword(Md5
				.encryptMd5(loginBean.getPassword().getBytes()));
		boolean status = loginDao.login(loginBean.getUserAccount(), loginBean
				.getPassword());
		return status;
	}

	/**
	 * 获取用户权限列表
	 * 
	 * @param loginBean
	 * @return
	 */
	public List<Resources> getLimit(LoginUserBean user) {
		
		// 获取指定角色的权限信息
		List<Resources> list = roleService.getResTreeByUserCode(user.getUserId());
			//循环list
			for (int i = 0;list!=null && i < list.size(); i++) {
				Resources resources = (Resources)list.get(i);
				if("".equals(resources.getResFather())){
					//1级菜单
					resources.setGrade(1);
					//查询其子节点
					getChildList(list,resources);
				}
			}
		return list;
	}

	/**
	 * 获取用户权限列表
	 * 
	 * @param loginBean
	 * @return
	 */
	public Map<String,List<Resources>> getLimitMenu(LoginUserBean user) {
		//菜单数据
		Map<String,List<Resources>> map = new HashMap<String, List<Resources>>();
		
		// 获取指定角色的权限信息
		List<Resources> list = roleService.getResTreeByUserCode(user.getUserId());
		//一级节点
		List<Resources> firstList = new ArrayList<Resources>();
		//二级节点
		List<Resources> secondList = new ArrayList<Resources>();
		//循环list
		for (int i = 0;list!=null && i < list.size(); i++) {
			Resources resources = (Resources)list.get(i);
			if("".equals(resources.getResFather())){
				//一级节点
				firstList.add(resources);
				//查询其子节点
				getSecondList(secondList,list,resources);
			}
		}
		map.put("firstList", firstList);
		map.put("secondList", secondList);
		return map;
	}
	
	/**
	 * 获取二级菜单
	 * @param secondList
	 * @param list
	 * @param resources
	 */
	private void getSecondList(List<Resources> secondList,List<Resources> list, Resources resources) {
		//该一级节点的二级节点个数
		int num = 0;
		//循环list
		for (int i = 0;list!=null && i < list.size(); i++) {
			Resources resourcesChild = (Resources)list.get(i);
			if(resources.getPkId().equals(resourcesChild.getResFather())){
				if("0".equals(resourcesChild.getResType())){
					num++;
					resourcesChild.setResFatherCode(resources.getResCode());
					secondList.add(resourcesChild);
				}
			}
		}
		resources.setSecondNum(num);
	}

	/**
	 * 获取其子节点
	 * @param list 用户的所有权限
	 * @param resources 父菜单对象
	 */
	private void getChildList(List<Resources> list, Resources resources) {
		//循环list
		for (int i = 0;list!=null && i < list.size(); i++) {
			Resources resourcesChild = (Resources)list.get(i);
			if(resources.getPkId().equals(resourcesChild.getResFather())){
				if("0".equals(resourcesChild.getResType())){
					//上级菜单级数+1
					resourcesChild.setGrade(resources.getGrade()+1);
					//查询其子节点
					getChildList(list,resourcesChild);
				}else if("1".equals(resourcesChild.getResType())){
					//末级节点赋值为-1
					resourcesChild.setGrade(-1);
				}
			}
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @param loginBean
	 * @return LoginUser
	 */
	public LoginUserBean getUserInfo(LoginBean loginBean) {
		
		LoginUserBean user = new LoginUserBean();
		
		DBResult result = loginDao.getUserInfo(loginBean);
		
		if (result != null) {
			user.setUserId(result.get(0,"user_id"));
			user.setUserName(result.get(0,"user_name"));
			user.setOrganizationId(result.get(0, "org_id"));
		}

		return user;
	}

}
