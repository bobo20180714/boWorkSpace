package com.xpoplarsoft.baseInfo.tmparams.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.baseInfo.tmparams.bean.TmParamView;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class TmParamsDao implements ITmParamsDao{
	
	/**
	 * @Description: 获取遥测参数
	 * @author 孟祥超
	 * @date 2015.08.06
	 */
	@Override
	public DBResult queryParamsInfo(DBParameter param,CommonBean commonBean) {
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo("sat_param",
				"queryParamsInfo", param,commonBean.getPage(),commonBean.getPagesize());
		return result;
	}	
	
		public DBResult findGrantUserGroupTmParam(DBParameter param){
			DBResult result = SQLFactory.getSqlComponent().queryInfo("sat_param",
					"findGrantUserGroupTmParam", param);
			return result;
		}
		
		public String getSatParamInfoCount(DBParameter param){
			String sqlId = "getSatParamInfoCount";
			if("true".equals(param.getObject("querybykey").toString())){
				sqlId = "getSatParamInfoCount_keyquery";
			}
			String cou = SQLFactory.getSqlComponent().queryInfo("sat_param",
					sqlId,param).get(0, 0);
			return cou;
		}

		public DBResult findGrantUserGroupTmParamById(DBParameter param){
			String sqlId = "findGrantUserGroupTmParamById";
			if("true".equals(param.getObject("querybykey").toString())){
				sqlId = "findGrantUserGroupTmParamById_keyquery";
			}
			DBResult result = SQLFactory.getSqlComponent().queryInfo("sat_param",
					sqlId, param);
			return result;
		}
		
		public Boolean tmParamUpdate(DBParameter param){
			Boolean flag =  SQLFactory.getSqlComponent().updateInfo("sat_param",
					"tmParamUpdate", param);
			return flag;
		}
		
		public DBResult getTmParamsInfoById(DBParameter param){
			DBResult result = SQLFactory.getSqlComponent().queryInfo("sat_param",
					"getTmParamsInfoById", param);
			return result;		
		}
		
		public Boolean tmParamDelete(List<DBParameter> paramList){
			return SQLFactory.getSqlComponent().batchUpdate("sat_param", "tmParamDelete", paramList);
		}
		
		public DBResult findSubNoParamBySubQueryPage(DBParameter param,int page,int pagesize){
			return SQLFactory.getSqlComponent().
					pagingQueryInfo("sat_param", "findSubNoParamBySubQueryPage", param,page,pagesize);
		}
		
		public String getSubNoParamCount(DBParameter param){
			return SQLFactory.getSqlComponent().queryInfo("sat_param",
					"getSubNoParamCount",param).get(0, 0);	
		}
		
		public String getSubHasParamCount(DBParameter param){
			return SQLFactory.getSqlComponent().queryInfo("sat_param",
					"getSubHasParamCount",param).get(0, 0);	
		}
//		public String getTmInfoCount(DBParameter param){
//			return SQLFactory.getSqlComponent().queryInfo("sat_param",
//					"getTmInfoCount",param).get(0, 0);
//		}

		@Override
		public DBResult findStandHasParamByStandQueryPage(DBParameter param) {
			return SQLFactory.getSqlComponent().queryInfo("sat_param", "findStandHasParamByStandQueryPage", param);
		}

		@Override
		public String getStandHasParamCount(DBParameter param) {
			return SQLFactory.getSqlComponent().queryInfo("sat_param",
					"getStandHasParamCount",param).get(0, 0);
		}


		@Override
		public Boolean paramResourceUpdate(DBParameter param) {
			return SQLFactory.getSqlComponent().updateInfo("sat_param", "paramResourceUpdate", param);
		}

		@Override
		public Boolean paramDropToRightAll(LinkedHashMap<String, List<DBParameter>> map) {
			return SQLFactory.getSqlComponent().
					batchUpdate("sat_param",map);
		}

		@Override
		public Boolean aloneParamDropToRightAll(DBParameter param) {
			return SQLFactory.getSqlComponent().updateInfo("sat_param", "aloneParamDropToRightAll", param);
		}

		@Override
		public Boolean paramDropToLeftAll(DBParameter param) {
			return SQLFactory.getSqlComponent().updateInfo("sat_param", "paramDropToLeftAll", param);
		}

		@Override
		public Boolean ParamDropToLeft(List<DBParameter> paramList) {
			return SQLFactory.getSqlComponent().batchUpdate("sat_param", "ParamDropToLeft", paramList);
		}

		@Override
		public DBResult findSubHasParamBySubQueryPage(
				DBParameter param) {
			return SQLFactory.getSqlComponent().queryInfo("sat_param", "findSubHasParamBySubQueryPage", param);
		}

		@Override
		public String getfindTmParamCount(DBParameter param) {
			return SQLFactory.getSqlComponent().queryInfo("sat_param",
					"getfindTmParamCount",param).get(0, 0);
		}

		@Override
		public boolean add(TmParamView tmParamView) {
			DBParameter param = new DBParameter();
			int tmId = getTmId();
			param.setObject("tm_param_id", tmId);
			param.setObject("tm_param_name",tmParamView.getTm_param_name());
			param.setObject("sat_id",tmParamView.getSat_id());
			param.setObject("tm_param_code",tmParamView.getTm_param_code());
			String num = tmParamView.getTm_param_num();
			param.setObject("tm_param_num",(num == null || "".equals(num))?tmId:num);
			param.setObject("tm_param_type",tmParamView.getTm_param_type());
			return SQLFactory.getSqlComponent().updateInfo("sat_param", "add", param);
		}
		
		public int getTmId(){
			int pkId = -1;
			DBResult dbr = SQLFactory.getSqlComponent().queryInfo("sat_param", "getTmId", new DBParameter());
			if(dbr != null && dbr.getRows() > 0){
				pkId = Integer.parseInt(dbr.get(0, "pk_id"));
			}
			return pkId;
		}

		@SuppressWarnings("deprecation")
		@Override
		public DBResult queryTmByCode(String satId,String tmCode,String tmId) {
			String sql = "select * from tm where status = 0 and sat_id = "+satId+" and tm_param_code = '"+tmCode+"' ";
			if(tmId != null){
				sql = sql + " and tm_param_id != "+tmId;
			}
			return SQLFactory.getSqlComponent().queryInfo(sql);
		}

		@SuppressWarnings("deprecation")
		@Override
		public DBResult queryTmByNum(String satId,String tmNum,String tmId) {
			String sql = "select * from tm where status = 0 and   sat_id = "+satId+" and tm_param_num = '"+tmNum+"' ";
			if(tmId != null){
				sql = sql + " and tm_param_id != "+tmId;
			}
			return SQLFactory.getSqlComponent().queryInfo(sql);
		}
}

