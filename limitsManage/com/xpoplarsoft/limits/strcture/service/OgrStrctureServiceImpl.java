package com.xpoplarsoft.limits.strcture.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.strcture.IOrganizationDao;
import com.xpoplarsoft.limits.strcture.bean.OrgStructure;
import com.xpoplarsoft.limits.strcture.dao.IOrgStrctureDao;

/**
 * 组织机构管理的 接口
 * 
 * @author 王晓东
 * @date 2014-01-09
 */
@Service
public class OgrStrctureServiceImpl implements IOrgStrctureService {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(OgrStrctureServiceImpl.class);

	@Autowired
	public IOrgStrctureDao dao;

	@Autowired
	public IOrganizationDao getDao;

	@Override
	public ResultBean addStructure(OrgStructure org) {

		ResultBean result = new ResultBean();
		boolean status = dao.addStructure(org);

		if (status) {
			result.setSuccess("true");
			result.setMessage("新增数据成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("新增数据失败！");
		}

		return result;
	}

	/**
	 * 更新组织结构信息
	 * 
	 * @param OrgStructure
	 * @return ResultBean
	 */
	@Override
	public ResultBean updateStructure(OrgStructure org) {

		ResultBean result = new ResultBean();

		boolean status = dao.updateStructure(org);
		if (status) {
			result.setSuccess("true");
			result.setMessage("修改数据成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("修改数据失败！");
		}

		return result;
	}

	

	/**
	 * 删除组织结构信息
	 * 
	 * @param orgCode
	 * @return ResultBean
	 */
	@Override
	public ResultBean deleteStrcture(String pk_id, String company_id) {

		ResultBean result = new ResultBean();
		boolean status = dao.deleteStrcture(pk_id, company_id);
		if (status) {
			result.setSuccess("true");
			result.setMessage("删除数据成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("删除数据失败！");
		}
		return result;
	}

	@Override
	public ResultBean checkCode(String orgCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[OgrStrctureService][checkCode]开始执行");
		}
		ResultBean result = new ResultBean();
		// 获取机构信息
		DBResult resultOrg = dao.getOrg(orgCode);
		if (resultOrg != null && resultOrg.getRows() > 0) {
			result.setSuccess("false");
			result.setMessage("组织机构信息编号重复！");
			return result;
		}
		// 记录日志
		if (log.isDebugEnabled()) {
			log.debug("返回数据为:" + result + "");
		}
		return result;
	}

	@Override
	public String viewStructure(String id) {
		Map<String, Object> cellMap = new HashMap<String, Object>();
		Gson json = new Gson();
		DBResult dbResult = dao.viewStructure(id);
		if (dbResult != null) {
			String[] resName = dbResult.getColName();

			for (int j = 0; j < resName.length; j++) {
				// 查询列对应的值
				String resValue = dbResult.getValue(0, resName[j]);
				// 放入map中
				cellMap.put(resName[j].toLowerCase(), resValue);
			}

			String retJson = json.toJson(cellMap);

			if (log.isDebugEnabled()) {
				log.debug("返回json数据为[" + retJson + "]");
			}

			return retJson;
		} else {
			return json.toJson(cellMap);
		}
	}

	/**
	 * 获取组织机构集合
	 * 
	 * @return List<OrgStrcture>
	 */
	@Override
	public List<Map<String, Object>> getStrctureList() {

		List<DBResult> result = getDao.getStrctureList();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (result != null) {
			for (int i = 0; i < result.get(0).getRows(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(0).get(i, "PK_ID"));
				map.put("text", result.get(0).get(i, "COMPANY_NAME"));
				map.put("pid", "");
				map.put("company_id", result.get(0).get(i, "PK_ID"));
				list.add(map);
			}
			for (int j = 0; j < result.get(1).getRows(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(1).get(j, "PK_ID"));
				map.put("text", result.get(1).get(j, "ORG_NAME"));
				map.put("company_id", result.get(1).get(j, "company_id"));
				if (result.get(1).get(j, "PARENT_ID") == null) {
					map.put("pid", result.get(1).get(j, "COMPANY_ID"));
				} else {
					map.put("pid", result.get(1).get(j, "PARENT_ID"));
				}
				list.add(map);
			}
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getStrctureByName(String org_name) {
		List<DBResult> result = getDao.getStrctureByName(org_name);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (result != null) {
			for (int i = 0; i < result.get(0).getRows(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(0).get(i, "PK_ID"));
				map.put("text", result.get(0).get(i, "ORG_NAME"));
				map.put("company_id", result.get(0).get(i, "COMPANY_ID"));
				if (result.get(0).get(i, "PARENT_ID") == null) {
					map.put("pid", result.get(0).get(i, "COMPANY_ID"));
				} else {
					map.put("pid", result.get(0).get(i, "PARENT_ID"));
				}
				list.add(map);
			}
			for (int j = 0; j < result.get(1).getRows(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(1).get(j, "PK_ID"));
				map.put("text", result.get(1).get(j, "ORG_NAME"));
				map.put("company_id", result.get(1).get(j, "company_id"));
				if (result.get(1).get(j, "PARENT_ID") == null) {
					map.put("pid", result.get(1).get(j, "COMPANY_ID"));
				} else {
					map.put("pid", result.get(1).get(j, "PARENT_ID"));
				}
				list.add(map);
			}
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getStrctureByCode(String org_code) {
		List<DBResult> result = getDao.getStrctureByCode(org_code);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (result != null && result.size()>0) {
			for (int i = 0; i < result.get(0).getRows(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(0).get(i, "PK_ID"));
				map.put("text", result.get(0).get(i, "ORG_NAME"));
				map.put("company_id", result.get(0).get(i, "COMPANY_ID"));
				if (result.get(0).get(i, "PARENT_ID") == null) {
					map.put("pid", result.get(0).get(i, "COMPANY_ID"));
				} else {
					map.put("pid", result.get(0).get(i, "PARENT_ID"));
				}
				list.add(map);
			}
			for (int j = 0; j < result.get(1).getRows(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(1).get(j, "PK_ID"));
				map.put("text", result.get(1).get(j, "ORG_NAME"));
				map.put("company_id", result.get(1).get(j, "company_id"));
				if (result.get(1).get(j, "PARENT_ID") == null) {
					map.put("pid", result.get(1).get(j, "COMPANY_ID"));
				} else {
					map.put("pid", result.get(1).get(j, "PARENT_ID"));
				}
				list.add(map);
			}
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getStrctureByID(String org_id) {
		List<DBResult> result = getDao.getStrctureByID(org_id);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (result != null && result.size()>0) {
			for (int i = 0; i < result.get(0).getRows(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(0).get(i, "PK_ID"));
				map.put("text", result.get(0).get(i, "ORG_NAME"));
				map.put("company_id", result.get(0).get(i, "COMPANY_ID"));
				if (result.get(0).get(i, "PARENT_ID") == null) {
					map.put("pid", result.get(0).get(i, "COMPANY_ID"));
				} else {
					map.put("pid", result.get(0).get(i, "PARENT_ID"));
				}
				list.add(map);
			}
			for (int j = 0; j < result.get(1).getRows(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", result.get(1).get(j, "PK_ID"));
				map.put("text", result.get(1).get(j, "ORG_NAME"));
				map.put("company_id", result.get(1).get(j, "company_id"));
				if (result.get(1).get(j, "PARENT_ID") == null) {
					map.put("pid", result.get(1).get(j, "COMPANY_ID"));
				} else {
					map.put("pid", result.get(1).get(j, "PARENT_ID"));
				}
				list.add(map);
			}
		}

		return list;
	}
}
