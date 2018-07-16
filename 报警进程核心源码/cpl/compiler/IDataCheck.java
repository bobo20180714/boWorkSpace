package compiler;

import commom.TMParam;

public interface IDataCheck {		//数据检查接口
	/**
	 * 根据参数代号获取遥测参数对象
	 * 
	 * @param parameterCode
	 *            参数代号
	 * @return TMParam 遥测参数对象  如果不存在返回null
	 */
	public TMParam getParameter(String parameterCode);
}