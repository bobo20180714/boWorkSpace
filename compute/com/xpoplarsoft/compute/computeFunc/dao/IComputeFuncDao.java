package com.xpoplarsoft.compute.computeFunc.dao;

import com.xpoplarsoft.compute.computeFunc.bean.ComputeFuncBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IComputeFuncDao {

	boolean addCompute(ComputeFuncBean bean);

	DBResult computeList(String computeName,String className, CommonBean bean);

	boolean deleteCompute(String computeId);

	boolean updateCompute(ComputeFuncBean bean);

	DBResult view(String computeId);

	DBResult queryAllComputeList(String computeName);

	DBResult getFunctionInfo(String computeId);

}
