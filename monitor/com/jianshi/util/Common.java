package com.jianshi.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.sql.CLOB;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

public class Common {
	private static String path;
	
	public static String getPath(HttpServletRequest request) {
//		if(path==null){
			StringBuilder paths = new StringBuilder();
			paths.append(request.getScheme())
				.append("://")
				.append(request.getServerName())
				.append(":")
				.append(request.getServerPort())
				.append(request.getContextPath())
				.append("/");
			path = paths.toString();
//		}
		return path;
	}
	
	public static String getUtf8Str(String str){
		try {
			return new String(str.getBytes("ISO8859_1"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static String uploadFile(HttpServletRequest request,HttpSession session) {
		DiskFileItemFactory fac = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(fac);  
        upload.setHeaderEncoding("utf-8");  
        String fileName = null;
        Map<String, String> params=new HashMap<String, String>();
        FileItem fileItem=null;
        File targetFile =null;
        try {  
        	List<FileItem> fileItems = upload.parseRequest(request);      		
			String path=session.getServletContext().getRealPath("/upload")+"/";	
			for(FileItem fi : fileItems){				
				if(fi.isFormField()){
					params.put(fi.getFieldName(), fi.getString());					
				}
				else{
					fileItem=fi;
				}
			}
			if(params.get("Filetype").toString().equals("1")){
				fileName=params.get("Filename").toString();
				fileName=fileName.substring(fileName.lastIndexOf("."));
        		fileName=UUID.randomUUID().toString()+fileName;
			}
			else{
				fileName = UUID.randomUUID().toString()+".png";
			}
			targetFile =new File(path+fileName);
			fileItem.write(targetFile);
        } catch (Exception ex) {  
        	ex.printStackTrace();
            return null;  
        }		
		return fileName;
	}
	
	public static boolean delDir(File dir) {
		if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                if (!delDir(new File(dir, children[i]))) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
	}
	private static final int[] key={4,1,8,5,7,3,9,6,2};
	private static int mod10(int n) {
		int ret=n%10;
		if(ret<0)ret+=10;
		return ret;
	}

	public static String encrypt(String m) {
		StringBuffer sb=new StringBuffer();
		for (int i = 0,k=0; i < m.length(); i++) {
			char ch=m.charAt(i);
			if('0'<=ch&&ch<='9'){
				ch=(char)(mod10(mod10((ch-'0')+key[k++])*3+7)+'0');
				if(k>=key.length)k=0;
			}
			sb.append(ch);
		}
		return sb.toString();
	}
	public static String decrypt(String c) {
		StringBuffer sb=new StringBuffer();
		for (int i = 0,k=0; i < c.length(); i++) {
			char ch=c.charAt(i);
			if('0'<=ch&&ch<='9'){
				ch=(char)(mod10(mod10((ch-'0')*7+1)-key[k++])+'0');
				if(k>=key.length)k=0;
			}
			sb.append(ch);			
		}
		return sb.toString();
	}
	private static final int BUFFER_SIZE = 16 * 1024;
	public static boolean copy(String src,String target){
		BufferedInputStream bis = null; 
		BufferedOutputStream bos = null;  
        try { 
        	bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(target)); 
            byte[] buff = new byte[BUFFER_SIZE];  
            for (int len = -1; (len = bis.read(buff)) > -1;) {  
                bos.write(buff, 0, len);  
            }  
            bos.close();
            bis.close();            
            return true;
        } catch (IOException e) {  
            e.printStackTrace();
            return false;
        }
	}
	public static boolean mkdir(String dir) {
		File file =new File(dir); 
		if(!file.exists()&&!file.isDirectory()){
			return file.mkdir(); 
		}
		return true;
	}
	
	public static boolean deldir(String dir) {
        return deldir1(new File(dir));
    }
	
	private static boolean deldir1(File dir) {
		if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                if (!deldir1(new File(dir, children[i]))) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
	}
	public static String getConfigVal(String name,String... joins) {
		Properties prop = new Properties();
		try {
			prop.load(Common.class.getClassLoader().getResourceAsStream("config.properties"));
			String val=prop.getProperty(name);
			if(val!=null){
				if(joins.length==0)
					return val;
				val=val.replaceAll("/$", "");
				for(String join : joins){
					val+="/"+join;
				}
				return val;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static InputStream getJarFile(InputStream  is, String entryFile) {
		try {
			JarInputStream jarInput = new JarInputStream (is);
			JarEntry entry = jarInput.getNextJarEntry();  
	        while(entry != null) {  
	            if(entryFile.equals(entry.getName())) {  
	                return jarInput;      
	            }  
	            entry = jarInput.getNextJarEntry();  
	        }  
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return null;
	}
	public static InputStream getJarFile(String jarFile, String entryFile) {
		try {
			if(!new File(jarFile).exists()){
				throw new Exception("文件：‘"+jarFile+"’不存在！");
			}
			@SuppressWarnings("resource")
			JarFile jarFile1 = new JarFile(jarFile);
			JarEntry entry = jarFile1.getJarEntry(entryFile);
			return jarFile1.getInputStream(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	public static Map<String, Object> getJson(Map<String, Object> map,String dataField) {
		if(map==null)
			return null;
		Object data=map.get(dataField);
		if(data==null)
			return map;
		JSONObject json=JSONObject.parseObject(data.toString());
		for (String key : json.keySet()) {
			map.put(key, json.get(key));
		}
		map.remove(dataField);
		return map;
	}
	public static ByteArrayInputStream createImage() {		
		newRandCode();//生成随机码
        int w = 60,h = 22;//设置图片宽度、高度
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = image.createGraphics();//获取绘制对象  
        g.setColor(Color.WHITE);//设置颜色           
        g.fillRect(0, 0, w, h);//绘制背景
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, 0, w - 1, h -1);g.setColor(Color.BLUE);
        int fontSize=h-8;
        g.setFont(new Font("Algerian", Font.BOLD, fontSize));
        char[] chars = randCode.toCharArray();  
        for(int i = 0; i < codeSize; i++){  
        g.drawChars(chars, i, 1, ((w-8) / codeSize) * i + 4, h/2 + fontSize/2-2);  
        } 
        g.dispose();
        
		return image2Stream(image);  
		
	}
	public static String randCode;
	private static final int codeSize=4;//设置验证码个数
	private static Random random = new Random();
	private static void newRandCode(){//生成n位随机码
		String codes="23456789ABCDEFGHJKLMNPQRSTUVWXYZ"; 
        StringBuilder sb = new StringBuilder(codeSize);  
//        for(int i = 0; i < codeSize; i++)sb.append(codes.charAt(random.nextInt(codes.length()-1)));
        for(int i = 0; i < codeSize; i++)sb.append(codes.charAt(random.nextInt(7)));
        randCode=sb.toString();
	}
	private static ByteArrayInputStream image2Stream(BufferedImage image){//将BufferedImage转换成ByteArrayInputStream  
        ByteArrayInputStream inputStream = null;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);  
        try {  
            jpeg.encode(image);  
            byte[] bts = bos.toByteArray();  
            inputStream = new ByteArrayInputStream(bts);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return inputStream;  
    } 
	
	public static String getBase64(byte[] bytes) {
		return "data:image/png;base64,"+new BASE64Encoder().encode(bytes);
	}
	
	public static List<Map<String, Object>> base64(List<Map<String, Object>> data,String... fields) {
		for (Map<String, Object> dat : data) {
			for(String field : fields){
				if(dat.get(field)!=null){
					byte[] bytes=(byte[])dat.get(field);			
					dat.put(field, getBase64(bytes));
				}				
			}			
		}
		return data;
	}
	
	public static String md5(String s){  
        try{  
        	MessageDigest md5 = MessageDigest.getInstance("MD5"); 
            byte[] bytes = md5.digest(s.getBytes());  
            StringBuffer sb = new StringBuffer();  
            for (int i = 0; i < bytes.length; i++){  
                int val = ((int) bytes[i]) & 0xff;  
                if (val < 16)  
                    sb.append("0");  
                sb.append(Integer.toHexString(val));  
            }  
            return sb.toString();
        }catch (Exception e){  
            e.printStackTrace();  
        }  
        return null;   
    } 
	
	public static List<List<String>> parseDoc(String fileName,InputStream is) {
		List<List<String>> list=new ArrayList<List<String>>();
		String extend=fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		try{
			if(extend.equals("xls")||extend.equals("xlsx")){
				Workbook wb=WorkbookFactory.create(is);
				Sheet sheet = wb.getSheetAt(0);
				for(int i=sheet.getFirstRowNum();i<sheet.getLastRowNum();i++) {
					Row row = sheet.getRow(i);
					List<String> rowList=new ArrayList<String>();
					for(int j=row.getFirstCellNum();j<row.getPhysicalNumberOfCells();j++){
						rowList.add(row.getCell(j).toString());
					}
					list.add(rowList);
				}
			}
			else if(extend.equals("doc")){
				POIFSFileSystem pfs=new POIFSFileSystem(is);  
	            HWPFDocument hwpf=new HWPFDocument(pfs);  
	            Range range =hwpf.getRange();  
	            TableIterator it=new TableIterator(range);
	            Table table=null;
	            if(it.hasNext())
	            	table=it.next();
	            for(int i=0;i<table.numRows();i++){  
                    TableRow tr=table.getRow(i);
                    List<String> rowList=new ArrayList<String>();
                    for(int j=0;j<tr.numCells();j++){
                    	TableCell td=tr.getCell(j);
                    	String v="";
                    	for(int k=0;k<td.numParagraphs();k++){  
                            v+=td.getParagraph(k).text().trim();
                        }
                    	rowList.add(v);
                    }
                    list.add(rowList);
	            }
			}
			else if(extend.equals("docx")){
				XWPFDocument doc=new XWPFDocument(is);
				XWPFTable table=doc.getTables().get(0);
				List<XWPFTableRow> rows = table.getRows();
				for (XWPFTableRow row : rows) {
			        List<XWPFTableCell> tableCells = row.getTableCells();
			        List<String> rowList=new ArrayList<String>();
			        for (XWPFTableCell cell : tableCells) {
			             String text = cell.getText();
			             rowList.add(text);
			        }
			        list.add(rowList);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static Object getObject(DBResult result) {
		return result != null && result.getRows()>0?result.getObject(0, 0):null;
	}
	
	public static Map<String, Object> getMap(DBResult result) {
		Map<String, Object> map=new HashMap<String, Object>();
		if(result != null && result.getRows()>0){
			String[] column_names = result.getColName();
			for (String name : column_names) {
				map.put(name, result.getObject(0, name));
			}
			return map;
		}
		return null;
	}
	
	public static List<Map<String, Object>> getMaps(DBResult result) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		if(result != null && result.getRows()>0){
			String[] column_names = result.getColName();
			for(int i=0; i<result.getRows(); i++) {
				Map<String, Object> map=new HashMap<String, Object>();
				for (String name : column_names) {
					map.put(name, result.getObject(i, name));
				}
				list.add(map);
			}			
		}
		return list;
	}
	
	public static String getId(String seq) {
		@SuppressWarnings("deprecation")
		DBResult result = SQLFactory.getSqlComponent().queryInfo("SELECT "+seq+".nextval FROM dual");
		return getObject(result).toString();
	}
	
	public static Map<String, Object> getClob(DBResult result) {
		Map<String, Object> map=Common.getMap(result);
		CLOB clob=(CLOB)map.get("data");
		try {
			map.put("data", clob.getSubString(1, (int)clob.length()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static List<Map<String, Object>> getClobs(DBResult result) {
		if(result != null && result.getRows()>0){
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			String[] column_names = result.getColName();
			for(int i=0; i<result.getRows(); i++) {
				Map<String, Object> map=new HashMap<String, Object>();
				for (String name : column_names) {
					if(name.equals("data")){
						CLOB clob=(CLOB)result.getObject(i, name);
						try {
							map.put("data", clob.getSubString(1, (int)clob.length()));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					else{
						map.put(name, result.getObject(i, name));
					}
				}
				list.add(map);
			}			
			return list;
		}
		return null;
	}
}