package com.xpoplarsoft.limits.orguser.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Check{
//	public static void main(String[] args){
//		 System.out.println(checkTel("18658552313"));
//	}
	//电话号码验证
	public static Boolean checkTel(String str){
		boolean flag = true;
		try{
		String check_tel = "^(17[0-9]|13[0-9]|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";//电话号码验证
		Pattern reg = Pattern.compile(check_tel);
		Matcher te = reg.matcher(str);
		flag = te.matches();
		}catch(Exception e){
			flag = false;
		}
			return  flag;
	}
	//邮箱验证
	public static Boolean checkMail(String str){
		boolean flag = true;
		try{
		String check_mail = "^[a-z0-9A-Z][\\w\\-\\.]{3,12}@([\\w\\-]+\\.)+[\\w]{2,}$";//邮箱验证
		Pattern regx = Pattern.compile(check_mail);
		Matcher te = regx.matcher(str);
		flag = te.matches();
		}catch(Exception e){
			flag = false;
		}
			return  flag;
		}
	//截止时间验证
	/**孟祥超 update*/
	public static Boolean checkTime(Date date,String str2){
		boolean flag = true;
		flag = Pattern.matches("\\d{4}-([0][1-9]|[1][0,1,2])-([0][1-9]|[1,2][0-9]|[3][0,1])", str2);
		if(!flag){
			return flag;	
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		Date time2 = null;
		try {
			time2 = dateFormat.parse(str2);
			flag = date.before(time2);
		} catch (java.text.ParseException e1) {
			flag = false;
			e1.printStackTrace();
		}
		
		return flag;
	}
	//验证登录名称和用户名称
	public static Boolean checkName(String LoginName, String name){
		boolean flag = true;
		try{
			boolean f = LoginName.equalsIgnoreCase("0") || name.isEmpty();
			flag = !f;
			}catch(Exception e){
				flag = false;
				}
			return flag;
			}
	//验证登录密码
	public static Boolean checkPas(String password){
		boolean flag = true;
		try{
			boolean f = password.isEmpty();
			flag = !f;
			}catch(Exception e){
				flag = false;
				}
			return flag;
			}

	}