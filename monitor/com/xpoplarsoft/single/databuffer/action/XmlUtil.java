/**
 * com.bydz.save.untm.util
 */
package com.xpoplarsoft.single.databuffer.action;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * xml字符串转为Map工具类
 * @author zhouxignlu
 * 2017年4月25日
 */
public class XmlUtil {

	/**
	 * 根节点为list的多条数据解析
	 * @param xmlStr
	 * @return
	 */
	public static List<Map<String,String>> xmlToListMap(String xmlStr){
		List<Map<String,String>> rs = new ArrayList<Map<String,String>>();
		SAXBuilder builder = new SAXBuilder();
		Reader reader = new StringReader(xmlStr);
		try {
			Document doc = builder.build(reader);
			List<Element> list = doc.getRootElement().getChildren("line");
			for(Element line : list){
				List<Element> l = line.getChildren();
				Map<String,String> f = new HashMap<String,String>();
				for(Element e : l){
					f.put(e.getName(),e.getValue());
				}
				rs.add(f);
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 单条数据解析
	 * @param xmlStr
	 * @return
	 */
	public static Map<String,String> xmlToMap(String xmlStr){
		Map<String,String> rs = new HashMap<String,String>();
		SAXBuilder builder = new SAXBuilder();
		Reader reader = new StringReader(xmlStr);
		try {
			Document doc = builder.build(reader);
			List<Element> list = doc.getRootElement().getChildren();
			for(Element e : list){
				rs.put(e.getName(),e.getValue());
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
