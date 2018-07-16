package com.xpoplarsoft.limits.orguser.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.utils.BeanTools;
import com.xpoplarsoft.limits.orguser.bean.OrgView;
import com.xpoplarsoft.limits.orguser.bean.UserInfoView;
import com.xpoplarsoft.limits.orguser.dao.IOrgUserDao;

@Service
public class OrgUserService implements IOrgUserService {
	@Autowired
	private IOrgUserDao orgUserDao;

	@Override
	public List<OrgView> findOrgTree(DBParameter param) {
		
		DBResult result = orgUserDao.findOrgTree(param);
/*		for(int i=0;i<result.getRows();i++){
			str += ",{id:"+result.get(i, "id")+","
					+"name:"+result.get(i, "text")
					+ "}";
		}
		if (str.length() > 0)
			str = str.substring(1);
		str= "[" + str + "]";
		return str;*/
		List<OrgView> orgViews = new ArrayList<OrgView>();
		for(int i=0;i<result.getRows();i++){
			OrgView alone = new OrgView();
			alone.setId(result.get(i, "pk_id"));
			alone.setOrg_parent_id(result.get(i, "parent_id"));
			alone.setOrg_name(result.get(i, "org_name"));
			alone.setOrg_code(result.get(i, "org_code"));
			alone.setText(result.get(i, "text"));
			orgViews.add(alone);
		}
		return orgViews;
	}

//	@SuppressWarnings("rawtypes")
	@Override
	public List<UserInfoView> findUserQueryPage(DBParameter param) {
		
		DBResult result = orgUserDao.findUserQueryPage(param);
		List<UserInfoView> users = new ArrayList<UserInfoView>();
		for(int i=0; result!=null && i<result.getRows();i++){
			UserInfoView alone = new UserInfoView();
			alone.setUser_id(result.get(i, "user_id"));
			alone.setOrg_id(result.get(i, "org_id"));
			alone.setOrg_name(result.get(0, "org_name"));
			alone.setLogin_name(result.get(i, "user_account"));
			alone.setUser_name(result.get(i, "user_name"));
			alone.setZw(result.get(i, "zw"));
			alone.setTelephone(result.get(i, "telephone"));
			alone.setDanwei(result.get(i, "danwei"));
			alone.setBumen(result.get(i, "bumen"));
			alone.setEnd_time(result.get(i, "end_time"));
			alone.setCreate_time(result.get(i, "create_time"));
			alone.setCreate_user_name(result.get(i, "create_user_name"));
			users.add(alone);
		}
		return users;
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Boolean orgAdd(DBParameter param) {
		// 改造了service的接口，操作用户信息和IP地址都必须在controller中获取
		Boolean flag = false;
//		String ug_id = param.getObject("org_id").toString();
//		param.setObject("ug_id", ug_id);

//		flag = orgUserDao.userGroupAdd(param);
		flag =  orgUserDao.orgAdd(param);
		return flag;
	}

	@Override
	public Boolean orgUpdate(DBParameter param) {

		return orgUserDao.orgUpdate(param);

	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String orgDelete(DBParameter param) {
		String retstr = null;
//		String org_id = param.getObject("org_id").toString();
		// 判断是否有下级机构
		if (orgUserDao.getCountOfSubOrg(param) > 0) {
			retstr = "{\"success\":\"false\",\"message\":\"该机构还有使用中的下级机构！\"}";
			return retstr;
		}
		// 判断是否有使用中的用户
		if (orgUserDao.getCountOfUsers(param) > 0) {
			retstr = "{\"success\":\"false\",\"message\":\"该机构还有使用中的用户账户！\"}";
			return retstr;
		}
		// 判断是否有使用中的角色
		if (orgUserDao.getCountOfRoles(param) > 0) {
			retstr = "{\"success\":\"false\",\"message\":\"该机构还有使用中的角色！\"}";
			return retstr;
		}
		// 判断是否有使用中的数据权限
//		if (orgUserDao.getCountOfLimits(org_id) > 0) {
//			retstr = "{\"success\":\"false\",\"message\":\"该机构还有使用中的数据权限，不能被移除！\"}";
//			return retstr;
//		}

//		Map map = new HashMap();
//		map.put("ug_id", "");
//		map.put("sysId", null);
//		limitDao.grantRoleResourceDelete(map);
		Boolean flag = orgUserDao.orgDelete(param);
		if (flag) {
			retstr = "{\"success\":\"true\",\"message\":\"移除成功\"}";
		} else {
			retstr = "{\"success\":\"false\",\"message\":\"移除失败\"}";
		}
		return retstr;

	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean userInfoAdd(DBParameter param) {
		Boolean flag = orgUserDao.userInfoAdd(param);
		if (flag)
			return true;
		return false;
	}

	@Override
	public Boolean userInfoUpdate(UserInfoView userInfoView) {
		DBParameter param = new DBParameter();
		param.setObject("user_id", userInfoView.getUser_id());
		param.setObject("login_name", userInfoView.getLogin_name());
		param.setObject("user_name", userInfoView.getUser_name());
		param.setObject("telephone", userInfoView.getTelephone());
		param.setObject("danwei", userInfoView.getDanwei());
		param.setObject("bumen", userInfoView.getBumen());
		param.setObject("zw", userInfoView.getZw());
		param.setObject("end_time", userInfoView.getEnd_time());

		return orgUserDao.userInfoUpdate(param);
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Boolean userInfoDelete(String ids) {

//		int rs = 0;
//
//		String[] list = ids.split(",");
//		for (String id : list) {
//			Map map = new HashMap();
//			String user_id = id.replace('\'', ' ').trim();
//			map.put("ug_id", user_id);
//			map.put("sysId", null);
//			limitDao.grantRoleResourceDelete(map);
//			rs = orgUserDao.userInfoDelete(id);
//
//		}
		String aa[]=ids.split(",");
		List<DBParameter> paramList = new ArrayList<DBParameter>();
		for(int i=0;i<aa.length;i++){
			DBParameter param = new DBParameter();
			param.setObject("id", aa[i]);
			paramList.add(param);
		}
		Boolean flag = orgUserDao.userInfoDelete(paramList);
		return flag;
	}

	@Override
	public Boolean setPassword(DBParameter param) {

		
		Boolean flag = orgUserDao.setPassword(param);

		return flag;
	}

	@Override
	public Boolean userOrgchange(String org_id, String ids) {
		List <DBParameter> parList = new ArrayList<DBParameter>();
		String []str = ids.split(",");
		for(int i = 0; i< str.length; i++){
			DBParameter param = new DBParameter();
			param.setObject("id", str[i]);
			param.setObject("org_id", org_id);
			parList.add(param);
		}
		Boolean flag = orgUserDao.userOrgchange(parList);
		return flag;
	}

	/**
	 * 文件为空不让导入；若有一行出错，其前面的正确的可以导入,其后的不让导入;
	 * 登录名；姓名；电话；单位；部门；职位；截止时间
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String userDaoru(List<String> records, UserInfoView userInfoView) {

		Map<String,String> map = new HashMap<String,String>();
		
		if(records == null || records.size() < 2){
			return "{\"success\":\"false\",\"message\":\"导入失败，文件内容为空!\"}";
		}
		
		List<UserInfoView> userList = null;
		for (int i = 1; records != null && i < records.size(); i++) {
			String[] rowData = records.get(i).split("\\,");
			if (rowData.length < 1 || rowData[0] == null || "".equals(rowData[0].trim())
					|| rowData[0].trim().length() == 0){
				continue;
			}
			userList = orgUserDao.selectLoginName(rowData[0].trim(),userInfoView.getOrg_id());
			if (userList.size() > 0){
				//用户名称已经存在
				continue;
			}
			SimpleDateFormat fp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//用户ID
			userInfoView.setUser_id(UUID.randomUUID().toString());
			map.put("ug_id", userInfoView.getUser_id());
			
			userInfoView.setCreate_time(fp.format( new Date()));
			userInfoView.setLogin_name(rowData[0].trim());
			userInfoView.setUser_name(rowData.length > 1?rowData[1].trim():"");
			userInfoView.setTelephone(rowData.length > 2?rowData[2].trim():"");
//			userInfoView.setDanwei(rowData.length > 3?rowData[3].trim():"");
//			userInfoView.setBumen(rowData.length > 4?rowData[4].trim():"");
			userInfoView.setZw(rowData.length > 3?rowData[3].trim():"");
			//截止时间
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(new Date());
			cal.add(Calendar.YEAR, 1);
			userInfoView.setEnd_time(rowData.length > 4?rowData[4].trim():dateFormat.format(cal.getTime()));
			
			Map<String,String> result = new HashMap<String,String>();
			result = userInfoCheck(userInfoView);
			if ("false".equals(result.get("rs"))) {
				String a = "{\"success\":\"false\",\"message\":\"导入失败，第" + (i+1) + "行出现错误:";
				for (Object key : result.keySet()) {
					if (key.equals("rs")) {
						continue;
					}
					a += "&"+(result.get(key)).toString();
				}
				a = a + "\"}";
				return a;
			}

			 DBParameter param = new DBParameter();
			 param = param.mapToDBParameter(BeanTools.beanToMap(userInfoView));
			
			Boolean flg =  orgUserDao.userInfoAdd(param);
			if (!flg) {
				return "{\"success\":\"false\",message:\"导入失败，第" + (i+1) + "行导入出现错误!\"}";
			}
		}
		return "{\"success\":\"true\",\"message\":\"导入成功!\"}";
	}

//	@SuppressWarnings("rawtypes")
	@Override
	public String findUserByLoginName(DBParameter param) {
		DBResult result = orgUserDao.findUserByLoginName(param);
/*		UserInfoView user = new UserInfoView();
		user.setLogin_name(result.get(0, "user_account"));
		user.setUser_name(result.get(0, "user_name"));
		user.setTelephone(result.get(0, "telephone"));
		user.setDanwei(result.get(0, "danwei"));
		user.setBumen(result.get(0, "bumen"));
		user.setZw(result.get(0, "zw"));
		user.setEnd_time(result.get(0, "end_time"));
		return user;*/
		if(result!=null && result.getRows()>0){
			return "{\"success\":\"true\"}";
		}else
			return "{\"success\":\"false\"}";
//		return JSONObject.toJSONString(DBResultUtil.objectToJson(result));
	}
	
	@Override
	public String findOrgUserByLoginName(DBParameter param) {
		DBResult result = orgUserDao.findUserByLoginName(param);
		UserInfoView user = new UserInfoView();
		user.setLogin_name(result.get(0, "user_account"));
		user.setUser_name(result.get(0, "user_name"));
		user.setTelephone(result.get(0, "telephone"));
		user.setDanwei(result.get(0, "danwei"));
		user.setBumen(result.get(0, "bumen"));
		user.setZw(result.get(0, "zw"));
		user.setEnd_time(result.get(0, "end_time"));
		return JSONObject.toJSONString(user);
	}

	@Override
	public String getUserExportFile(UserInfoView userInfoView, String path) {
		// TODO Auto-generated method stub
		List<UserInfoView> users = findUserQuery(userInfoView);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("用户列表");
		XSSFRow head = sheet.createRow(0);
		XSSFCell cell = head.createCell(0);
		cell.setCellValue("登录名称");
		cell = head.createCell(1);
		cell.setCellValue("用户名称");
		cell = head.createCell(2);
		cell.setCellValue("用户职位");
		cell = head.createCell(3);
		cell.setCellValue("联系方式");
//		cell = head.createCell(4);
//		cell.setCellValue("用户单位");
//		cell = head.createCell(5);
//		cell.setCellValue("用户部门");
		cell = head.createCell(4);
		cell.setCellValue("截止日期");
		cell = head.createCell(5);
		cell.setCellValue("所属机构");
		File outFile = new File(path);
		if (users != null) {
			XSSFCellStyle style = getTextStyle(wb);
			XSSFRow row = null;
			for (int i = 0; i < users.size(); i++) {
				UserInfoView user = users.get(i);
				row = sheet.createRow(i + 1);
				XSSFCell xcell = row.createCell(0);
				xcell.setCellValue(user.getLogin_name());
				xcell.setCellStyle(style);
				xcell = row.createCell(1);
				xcell.setCellValue(user.getUser_name());
				xcell.setCellStyle(style);
				xcell = row.createCell(2);
				xcell.setCellValue(user.getZw());
				xcell.setCellStyle(style);
				xcell = row.createCell(3);
				xcell.setCellValue(user.getTelephone());
				xcell.setCellStyle(style);
				/*		xcell = row.createCell(4);
				xcell.setCellValue(user.getDanwei());
				xcell.setCellStyle(style);
				xcell = row.createCell(5);
				xcell.setCellValue(user.getBumen());
				xcell.setCellStyle(style);*/
				xcell = row.createCell(4);
				xcell.setCellValue(user.getEnd_time());
				xcell.setCellStyle(style);
				xcell = row.createCell(5);
				xcell.setCellValue(user.getOrg_name());
				xcell.setCellStyle(style);
			}
			for (int i = 0; i < 9; i++) {
				sheet.setColumnWidth(i, 8000);
			}
			FileOutputStream fout;
			try {
				fout = new FileOutputStream(outFile);
				wb.write(fout);
				fout.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return outFile.getName();
	}

	public XSSFCellStyle getTextStyle(XSSFWorkbook wb) {
		XSSFCellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// headerStyle.setWrapText(true);
		headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
		headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
		headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
		return headerStyle;
	}

	@Override
	public List<UserInfoView> findUserQuery(UserInfoView userInfoView) {
		DBParameter param = new DBParameter();
		param.setObject("user_id", userInfoView.getUser_id());
		param.setObject("org_id", userInfoView.getOrg_id());
		param.setObject("login_name", userInfoView.getLogin_name());
		param.setObject("user_name", userInfoView.getUser_name());
		param.setObject("bumen", userInfoView.getBumen());
		param.setObject("create_time_start", userInfoView.getCreate_time_start());
		param.setObject("end_time", userInfoView.getEnd_time());
		param.setObject("end_time_end", userInfoView.getCreate_time_end());
		return orgUserDao.findUserQuery(param);
	}

	public Map<String,String> userInfoCheck(UserInfoView userInfoView) {
		Map<String,String> result = new HashMap<String,String>();
		boolean flag = true;
		result.put("rs", String.valueOf(flag));
		//验证电话号码
		flag = Check.checkTel(userInfoView.getTelephone());
		if(!"".equals(userInfoView.getTelephone()) && !flag){
			result.put("rs", "false");
			result.put("lianxifangshi", "用户电话号码验证失败!");
		}
		flag = Check.checkTime(new Date(), userInfoView.getEnd_time());
		if (!"".equals(userInfoView.getEnd_time()) && !flag) {
			result.put("rs", "false");
			result.put("jiezhishijian", "截止时间验证失败!");
		}
		return result;
	}

//	@Override
//	public String getFindOrgCount(DBParameter param) {
//		return orgUserDao.getFindOrgCount(param);
//	}

	@Override
	public String getFindUserCount(DBParameter param) {
		return orgUserDao.getFindUserCount(param);
	}

	@Override
	public OrgView findOrgById(DBParameter param) {
		DBResult res = orgUserDao.findOrgById(param);
		OrgView org = new OrgView();
		org.setOrg_id(res.get(0, "pk_id"));
		org.setOrg_name(res.get(0, "org_name"));
		org.setOrg_code(res.get(0, "org_code"));
		org.setOrg_desc(res.get(0, "org_desc"));
		return org;
	}

	@Override
	public String getUserId(DBParameter par) {
		return orgUserDao.getUserId(par);
	}

	/**
	 * 根据用户ID查询用户信息
	 * @param user_id
	 * @return
	 */
	@Override
	public Map<String, Object> getUserById(String user_id) {
		DBResult res = orgUserDao.getUserById(user_id);
		
		Map<String, Object> rsMap = DBResultUtil.dbResultToMap(res);
		return rsMap;
	}

	@Override
	public boolean judgeLoginAccountExit(String login_name) {
		DBResult dbr = orgUserDao.getUserInfoByAccount(login_name);
		if(dbr != null && dbr.getRows() == 0){
			return false;
		}
		return true;
	}



}
