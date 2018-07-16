package com.xpoplarsoft.queryBylimit.dao;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

public class QueryResourceDao implements IQueryResourceDao {

	@SuppressWarnings("deprecation")
	@Override
	public DBResult querySatList(String userId, String key) {
		return SQLFactory.getSqlComponent().queryInfo(getSql(userId, key));
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult querySatByPage(String userId, String key, int page,
			int pageSize) {
		return SQLFactory.getSqlComponent().pagingQueryInfo(getSql(userId, key), page, pageSize);
	}
	
	private String getSql(String userId,String key){
		key = key == null?"":key;
		return "select distinct * from ( "+
				"	select sat.sat_name,sat.sat_code,sat.sat_id,sat.mid from GRANT_ROLE_RESOURCE grr,satellite sat"+
				" 	where grr.sys_resource_id = sat.sat_id and grr.grant_type = 0"+
				"	and grr.ug_id = '"+userId+"'"+
				"	union all"+
				"	select sat.sat_name,sat.sat_code,sat.sat_id,sat.mid from GRANT_ROLE_RESOURCE grr,satellite sat"+
				"	where grr.sys_resource_id = sat.sat_id and grr.grant_type = 0 "+
				"	and grr.ug_id = (select ORG_ID from users where users.user_id = '"+userId+"' and rownum = 1)"+
				"	) "
				+ " where islike(sat_name,'"+key+"')>0  or islike(sat_code,'"+key+"')>0"
				+ "order by mid";
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult findSatTree(String userId, String key) {
		String sql = "select distinct t.* from ( "+
		       "select sv.* from ( "+
		       "	 select sat.*,sat.name sat_name,sat.code sat_code,sat.sys_resource_id sat_id from GRANT_ROLE_RESOURCE grr,sat_sub_alone_view sat  "+
		       "  where grr.sys_resource_id = sat.SYS_RESOURCE_ID and grr.grant_type = 0  "+
		       "   and grr.ug_id = '"+userId+"'  "+
		       "  union all  "+
		       "  select sat.*,sat.name sat_name,sat.code sat_code,sat.sys_resource_id sat_id from GRANT_ROLE_RESOURCE grr,sat_sub_alone_view sat  "+
		       "  where grr.sys_resource_id = sat.SYS_RESOURCE_ID and grr.grant_type = 0  "+
		       "  and grr.ug_id = (select ORG_ID from users where users.user_id = '"+userId+"' and rownum = 1) "+
		       "  ) sv  "+
		       "   where  sv.type = 0   and ( islike(sv.code,'"+key+"')>0  or islike(sv.name,'"+key+"')>0 )  "+
		       "  union all "+
			   "    select son.*,father.name sat_name,father.code sat_code,father.sys_resource_id sat_id "+ 
			   "    from sat_sub_alone_view son,sat_sub_alone_view father "+
			   "    where son.type = 5 and father.type = 0 and son.owner_id = father.sys_resource_id "+
			   "    and ( islike(father.code,'"+key+"')>0  or islike(father.name,'"+key+"')>0 ) "+
		       "  union all  "+
			   "     select lastson.*,secondson.sat_name,secondson.sat_code,secondson.sat_id from sat_sub_alone_view lastson, "+
			   "       ( "+
			   "          select son.*,father.name sat_name,father.code sat_code,father.sys_resource_id sat_id "+ 
			   "           from sat_sub_alone_view son,sat_sub_alone_view father "+
			   "            where son.type = 5 and father.type = 0 and son.owner_id = father.sys_resource_id "+    
			   "            and ( islike(father.code,'"+key+"')>0  or islike(father.name,'"+key+"')>0 ) "+
			   "       ) secondson "+
			   "	    where lastson.type = 6 and lastson.owner_id = secondson.sys_resource_id "+
		       "	) t "+
			   "	order by t.name";
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult findGrantUserGroupEquipmentTree(String userId,
			String sys_resource_id, String key) {
		
		String sql = "select distinct sv.* from ( 	"+ 
				" select sat.*,sat.name sat_name,sat.code sat_code,sat.sys_resource_id sat_id "+
				" from GRANT_ROLE_RESOURCE grr,sat_sub_alone_view sat    "+
				" where grr.sys_resource_id = sat.SYS_RESOURCE_ID and grr.grant_type = 0     and grr.ug_id = '"+userId+"' "+  
				"  union all    "+
				"  select sat.*,sat.name sat_name,sat.code sat_code,sat.sys_resource_id sat_id from GRANT_ROLE_RESOURCE grr,sat_sub_alone_view sat"+   
				"   where grr.sys_resource_id = sat.SYS_RESOURCE_ID and grr.grant_type = 0    "+
				" and grr.ug_id = (select ORG_ID from users where users.user_id = '"+userId+"' and rownum = 1)"+  
				"    ) sv     where  sv.type = 0   and ( islike(sv.code,'"+key+"')>0  or islike(sv.name,'"+key+"')>0 ) ";
		if(!"-1".equals(sys_resource_id)){
			sql = " select  sv.*,sv.name sat_name,sv.code sat_code,sv.sys_resource_id sat_id  from sat_sub_alone_view sv "
					+ " where sv.owner_id='"+sys_resource_id+"' and (islike(code,'"+key+"')>0 or islike(name,'"+key+"')>0)";
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean judgeHaveSatLimit(String userAccount, String satMid) {
		String sql = "select grr.* from GRANT_ROLE_RESOURCE grr "+
					"	where grr.sys_resource_id in (select sat_id from satellite where mid = '"+satMid+"'  and STATUS = 0) "+
					"	and grr.ug_id in (select user_id from users where user_account = '"+userAccount+"' and STATUS = 0 ) ";
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}

}
