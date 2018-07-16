/**
 * com.xpoplarsoft.util
 */
package com.xpoplarsoft.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json字符串转为Map<</>String,Object>,List<</>Object>嵌套模式
 * 
 * @author zhouxignlu 2017年3月23日
 */
public class JsonToMap {

	public static Map<String, Object> jsonToMap(String json)
			throws JSONException {
		// 转换报文内容json串为Map
		JSONObject jo = new JSONObject(json);
		return jsonToMap(jo);
	}

	/**
	 * 转换JSONObject为Map
	 * 
	 * @param jo
	 * @return
	 * @throws JSONException
	 */
	private static Map<String, Object> jsonToMap(JSONObject jo)
			throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator keys = jo.keys();
		while (keys.hasNext()) {
			String key = keys.next().toString();
			Object obj = jo.get(key);
			if (obj instanceof JSONArray) {
				map.put(key, jsonToList((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				map.put(key, jsonToMap((JSONObject) obj));
			} else {
				map.put(key, obj);
			}
		}
		return map;
	}

	/**
	 * 转换JSONArray为List
	 * 
	 * @param ja
	 * @return
	 * @throws JSONException
	 */
	private static List<Map<String, Object>> jsonToList(JSONArray ja)
			throws JSONException {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ja.length(); i++) {
			Object obj = ja.get(i);
			l.add(jsonToMap((JSONObject) obj));
		}
		return l;
	}
}
