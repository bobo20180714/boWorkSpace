package com.xpoplarsoft.alarm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xpoplarsoft.framework.flowno.FlowNoFactory;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.framework.startup.FrameStartup;

/**
 * 报警工具类
 * @author Administrator
 *
 */
public class AlarmUtil {

	private static Log log = LogFactory.getLog("AlarmUtil");
	
	/**
	 * clob类型转化为字符串
	 * @author 孟祥超
	 * @param ruleContentClob
	 * @return
	 */
	public static String clobToString(Clob ruleContentClob){
		String rsStr = "";
		if(ruleContentClob != null){
			Reader inStream = null;
			BufferedReader br = null;
			try {
				inStream = ruleContentClob.getCharacterStream();
				br = new BufferedReader(inStream);
				String str = br.readLine();
				StringBuffer sb = new StringBuffer();
				while(str != null){
					sb.append(str);
					str = br.readLine();
				}
				rsStr = sb.toString();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(br != null){
						br.close();
					}
					if(inStream!=null){
						inStream.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return rsStr;
	}
	
	/**
	 * 创建空白execl文件，返回下载路径和全路径
	 * @param req
	 * @param response
	 * @return map
	 */
	public static Map<String,String> createExcel() {
		
		//生成的文件名称
		String flow_no = FlowNoFactory.getFlowNoComponent().getFlowNo();
		String name = flow_no+".xlsx";
		
		//生成文件相对路径
		String savePath = SystemParameter.getInstance().getParameter("uploadFilePath");
		//生成文件路径
		String path = FrameStartup.PROJECT_PATH + savePath + name;
		
		//返回下载路径
		String fileUrl = savePath + name;
		
		if(log.isDebugEnabled()){
			log.debug("返回的下载路径["+fileUrl+"]");
		}
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("downloadUrl", fileUrl);//下载路径
		map.put("filePath", path);//文件全路径
		map.put("fileName", name);//文件名称
		return map;
	}
	
	/**
	 * 往excel填写数据
	 * @param tempPath	模板路径
	 * @param outputPath	保存路径
	 * @param resultList  数据
	 * @param columnNameList 表头列名
	 * @return	boolean
	 */
	public static boolean addDataToExcel(String tempPath, String outputPath, List<Map<String,String>> resultList,
			List<String> columnNameList) {
		
		boolean flag = true;
		
		File excelFile = new File(outputPath);
		if (excelFile.exists()){
			excelFile.delete();
		}
		copyFile(new File(tempPath), excelFile);
		OutputStream out = null;
		try {
			//读取模板文件
			InputStream inpTemplate;
			XSSFWorkbook wb;
			//获取单元格css
			XSSFCellStyle cellCss = null;
			try {
				inpTemplate = new FileInputStream(new File(outputPath));
				//取得目标文件的文件输入流
				wb = (XSSFWorkbook) WorkbookFactory.create(inpTemplate);
				
				XSSFSheet sheetTemplate=wb.getSheetAt(0);
				//取得模板单元格的样式
				cellCss = sheetTemplate.getRow(0).getCell(0).getCellStyle();
			}catch (Exception e) {
				log.error("读取文件异常！");
				return false;
			} 
			
			//遍历信息数量
			int peopleSize= resultList.size();
			//共多少列
			int temp = columnNameList.size();
			//从第几行开始
			int rowIndex = 1;
			int currentRowIndex = 0;
			currentRowIndex = rowIndex;
			//取得目标文件的第一张表（sheet）
			XSSFSheet sheet = wb.getSheetAt(0);
			// 往单元格填内容
			for (int i = 0; i < peopleSize; i++) {
				//获取当前行
				XSSFRow rowThis =sheet.createRow(currentRowIndex);
				for(int j=0; j < temp; j++){
					rowThis.createCell(j);
				}
				Map<String,String> resultMap = resultList.get(i);
				for(int k=0; k < temp; k++){
					rowThis.getCell(k).setCellValue(resultMap.get(columnNameList.get(k)) == null?"":resultMap.get(columnNameList.get(k)));
					rowThis.getCell(k).setCellStyle(cellCss);
					rowThis.setHeight((short) 500);
				}
				currentRowIndex++;
			}
			out = new FileOutputStream(new File(outputPath));
			wb.write(out);
			out.close();
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try {
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	 /***************************************************************************
     * 复制文件
     * @param source 源文件,不可以是目录
     * @param dest 目标文件,不可以是目录
     * @return 执行是否成功状态
     */
    public static boolean copyFile(File source, File dest) {
        boolean flag = false;
        if (dest.exists() && dest.lastModified() == source.lastModified()) {
            log.error("文件已经存在，并且没有修改【" + dest.getAbsolutePath() + "】");
            return flag;
        }
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(dest);
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
            fis.close();
            fos.close();
            buffer = null;
            source = null;
            dest = null;
            flag = true;
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                log.error(ex);
            }

        }
        return flag;
    }
}
