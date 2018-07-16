package com.xpoplarsoft.monitor.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.xpoplarsoft.monitor.bean.ColumnBean;
import com.xpoplarsoft.monitor.bean.TableBean;

/**
 * 解析业务报文中附加内容工具
 * @author mengxiangchao
 *
 */
public class AnalysisContentUtil {

	private static Log log = LogFactory.getLog(AnalysisContentUtil.class);
	
	/**
	 * 解析二维表
	 * @param content
  			<table>
	  			<head><name>time</name><title>时间</title></head>
				<head><name>content</name><title>日志内容</title></head>
				<row>
					<col><name>time</name><value></value></col>
					<col><name>content</name><value></value></col>
				</row>
			</table>
	 * @return
	 */
	public static TableBean getTableBean(String content){
		if(log.isDebugEnabled()){
			log.debug("组件[AnalysisContentUtil][getTableBean]开始执行！");
			log.debug("content=["+content+"]");
		}
		TableBean tableBean = new TableBean();
		try {
			Document doc = DocumentHelper.parseText(content);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> eleheadList = root.elements("head");
			//列头集合
			List<ColumnBean> columnData = new ArrayList<ColumnBean>();
			ColumnBean colBean = null;
			for (Element closEle : eleheadList) {
				colBean = new ColumnBean();
				colBean.setDisplay(closEle.elementText("title"));
				colBean.setName(closEle.elementText("name"));
				columnData.add(colBean);
			}
			tableBean.setColumnData(columnData);
			

			@SuppressWarnings("unchecked")
			List<Element> eleRowList = root.elements("row");
			//数据集合
			List<Map<String,String>> rowData = new ArrayList<Map<String,String>>();
			Map<String,String> rowBean = null;
			for (Element rowEle : eleRowList) {
				rowBean = new HashMap<String, String>();
				@SuppressWarnings("unchecked")
				List<Element> eleColList = rowEle.elements("col");
				for (Element colEle : eleColList) {
					rowBean.put(colEle.elementText("name"), colEle.elementText("value"));
				}
				rowData.add(rowBean);
			}
			tableBean.setRowData(rowData);
		} catch (DocumentException e) {
			if(log.isErrorEnabled()){
				log.equals("组件[AnalysisContentUtil][getTableBean]执行异常！");
			}
			e.printStackTrace();
		}
		if(log.isDebugEnabled()){
			log.debug("组件[AnalysisContentUtil][getTableBean]执行完成！");
		}
		return tableBean;
	}
}
