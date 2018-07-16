package com.xpoplarsoft.baseInfo.aloneinfo.service;

import com.xpoplarsoft.baseInfo.aloneinfo.bean.StandAloneInfo;

public interface IAloneInfoService {

	public String standAloneInfoAdd(String stand_alone_name, String stand_alone_code,String sub_system_id);

	public StandAloneInfo standAloneInfoById(String stand_alone_id);

	public Boolean standAloneInfoUpdate(StandAloneInfo standAloneInfo);

	public Boolean standAloneInfoDeleteById(String stand_alone_id);

	public boolean judgeCodeExit(String sub_system_id, String stand_alone_code,
			String stand_alone_id);

	public boolean judgeNameExit(String sub_system_id, String stand_alone_name,
			String stand_alone_id);
	
}
