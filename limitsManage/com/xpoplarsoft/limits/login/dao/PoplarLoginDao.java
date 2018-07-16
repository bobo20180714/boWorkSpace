package com.xpoplarsoft.limits.login.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.limits.login.bean.LoginBean;

/**
 * 类功能: 登陆dao类
 * 
 * @author chenjie
 * @date 2013-6-28
 */
@Repository
public class PoplarLoginDao implements ILoginDao {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(PoplarLoginDao.class);

	/**
	 * 登陆验证
	 * 
	 * @param userId
	 * @param passwd
	 */
	public boolean login(String userAccount, String userPass) {

		if (log.isInfoEnabled()) {
			log.info("组件[LoginDao][login]开始执行");
		}

		
		DBParameter para = new DBParameter();
		para.setObject("user_account", userAccount);
		para.setObject("user_pwd", userPass);

		DBResult result = SQLFactory.getSqlComponent().queryInfo("login","check_user", para);

		if (result != null && result.getTotal() > 0) {

			if (log.isDebugEnabled()) {
				log.debug("用户验证通过");
			}
			return true;
		} else {

			if (log.isDebugEnabled()) {
				log.debug("用户验证失败");
			}
			return false;
		}

	}

	/**
	 * 获取用户信息
	 * 
	 * @param loginBean
	 * @return DBResult
	 */
	public DBResult getUserInfo(LoginBean loginBean) {

		if (log.isInfoEnabled()) {
			log.info("组件[LoginDao][getUserInfo]开始执行");
		}


		DBParameter para = new DBParameter();
		para.setObject("user_account", loginBean.getUserAccount());
		DBResult result = SQLFactory.getSqlComponent().queryInfo("login","get_user", para);
		
		return result;
	}

}
