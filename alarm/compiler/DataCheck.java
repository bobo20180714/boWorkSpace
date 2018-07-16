package compiler;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import commom.TMParam;

public class DataCheck implements IDataCheck {

	private static String tmsql = "select tm_param_code, tm_param_name, tm_param_num, tm_param_type from tm where tm_param_code = ";
	@Override
	public TMParam getParameter(String parameterCode) {
//		tmsql = tmsql +"'"+parameterCode+"'";
		@SuppressWarnings("deprecation")
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo(tmsql +"'"+parameterCode+"'");
		
		if(dbs != null && dbs.getRows()>0){
			TMParam tm = new TMParam();
			tm.setParamCode(dbs.get(0, "tm_param_code"));
			tm.setParamName(dbs.get(0, "tm_param_name"));
			tm.setParamNo(Integer.valueOf(dbs.get(0, "tm_param_num")));
			if(dbs.get(0, "tm_param_type").equals("1")
					|| dbs.get(0, "tm_param_type").equals("2")){
				tm.setDataType(AlarmParser.INT);
			}else if(dbs.get(0, "tm_param_type").equals("3")){
				tm.setDataType(AlarmParser.STRING);
			}
			return tm;
		}
		
		return null;
	}

}
