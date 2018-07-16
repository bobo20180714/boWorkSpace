package com.xpoplarsoft.baseInfo.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName: FileUploadUtil
 * @Description: 文件上传
 * @author jingkewen
 * @data 2014-9-12 下午3:10:20
 *
 */
public class FileUploadUtil {
	public static final int CSV_FILE_SIZE = 10000;
	public static final int CSV_PAGE_SIZE = 1000;
	public static final String UTF_8 = "UTF-8";
	public static final String ISO8859 = "ISO-8859-1";
	public static final String GBK = "GBK";
	/**
	 * @Title: getData
	 * @Description: 获取excel数据
	 * @author jingkewen
	 * @throws
	 */
	public static String[][] getData(MultipartFile file,int ignoreRows) throws IOException{
		if(file.getContentType().equals("application/vnd.ms-excel")){
			return execl2003Read(file,ignoreRows);
		}else if(file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
			return execl2007Read(file,ignoreRows);
		}else{
			return null;
		}
	}
	
	public static String[][] getDataByFilePath(String filePath,int ignoreRows) throws IOException{
		
		if(filePath.endsWith("xls")){
			return execl2003ReadByFilePath(filePath,ignoreRows);
		}else if(filePath.endsWith("xlsx")){
			return execl2007ReadByFilePath(filePath,ignoreRows);
		}else{
			return null;
		}
		
//		if(file.getContentType().equals("application/vnd.ms-excel")){
//			return execl2003ReadByFilePath(filePath,ignoreRows);
//		}else if(file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
//			return execl2007ReadByFilePath(filePath,ignoreRows);
//		}else{
//			return null;
//		}
	}
	/**
	 * @Title: getCsvData
	 * @Description: 获取csv信息
	 * @author jingkewen
	 * @throws
	 */
	public static List<String> getCsvData(MultipartFile file) {
		String filename = file.getOriginalFilename();
		String fileType = filename.substring(filename.lastIndexOf('.')+1);
		if(fileType.equals("csv") || fileType.equals("text")){
			return csvRead(file);
		}else{
			return null;
		}
	}

	private static List<String> csvRead(MultipartFile file) {
		List<String> list=new ArrayList<String>();
		try {
			InputStreamReader reader=new InputStreamReader(file.getInputStream());
			BufferedReader bufferedReader=new BufferedReader(reader);
			String s=bufferedReader.readLine();
			while(s!=null){
				list.add(s);
				s=bufferedReader.readLine();
			}
		} catch (IOException e) {
		}
		return list;
	}

	private static String[][] execl2003Read(MultipartFile file, int ignoreRows) throws IOException {
		List<String[]> result=new ArrayList<String[]>();
		int rowSize=0;
		POIFSFileSystem fs=new POIFSFileSystem(file.getInputStream());
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFCell cell=null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st=wb.getSheetAt(sheetIndex);
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row=st.getRow(rowIndex);
				if(row==null) continue;
				int tempRowSize=row.getLastCellNum()+1;
				if(tempRowSize>rowSize) rowSize=tempRowSize;
				String[] values=new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue=false;
				for (short columuIndex = 0;columuIndex <= row.getLastCellNum(); columuIndex++) {
					String value="";
					cell=row.getCell(columuIndex);
					if(cell!=null){
						switch(cell.getCellType()){
							case HSSFCell.CELL_TYPE_STRING:
								value=cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if(HSSFDateUtil.isCellDateFormatted(cell)){
									Date date=cell.getDateCellValue();
									if(date!=null){
										value=new SimpleDateFormat("yyyy-MM-dd").format(date);
									}else{
										value="";
									}
								}else{
									value=new DecimalFormat("0").format(cell.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								if(!cell.getStringCellValue().equals("")){
									value=cell.getStringCellValue();
								}else{
									value=cell.getNumericCellValue()+"";
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value="";
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value=(cell.getBooleanCellValue()==true?"Y":"N");
								break;
							default:
								value="";
						}
						if(columuIndex==0&&value.trim().equals("")) break;
						values[columuIndex]=rightTrim(value);
						hasValue=true;
					}
				}
				if(hasValue) result.add(values);
			}
		}
		String[][] returnArray=new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i]=(String[])result.get(i);
		}
		return returnArray;
	}
	
	private static String[][] execl2003ReadByFilePath(String filePath, int ignoreRows) throws IOException {
		List<String[]> result=new ArrayList<String[]>();
		int rowSize=0;
		POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(new File(filePath)));
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFCell cell=null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st=wb.getSheetAt(sheetIndex);
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row=st.getRow(rowIndex);
				if(row==null) continue;
				int tempRowSize=row.getLastCellNum()+1;
				if(tempRowSize>rowSize) rowSize=tempRowSize;
				String[] values=new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue=false;
				for (short columuIndex = 0;columuIndex <= row.getLastCellNum(); columuIndex++) {
					String value="";
					cell=row.getCell(columuIndex);
					if(cell!=null){
						switch(cell.getCellType()){
						case HSSFCell.CELL_TYPE_STRING:
							value=cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								Date date=cell.getDateCellValue();
								if(date!=null){
									value=new SimpleDateFormat("yyyy-MM-dd").format(date);
								}else{
									value="";
								}
							}else{
								value=new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							if(!cell.getStringCellValue().equals("")){
								value=cell.getStringCellValue();
							}else{
								value=cell.getNumericCellValue()+"";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value="";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value=(cell.getBooleanCellValue()==true?"Y":"N");
							break;
						default:
							value="";
						}
						if(columuIndex==0&&value.trim().equals("")) break;
						values[columuIndex]=rightTrim(value);
						hasValue=true;
					}
				}
				if(hasValue) result.add(values);
			}
		}
		String[][] returnArray=new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i]=(String[])result.get(i);
		}
		return returnArray;
	}

	private static String[][] execl2007ReadByFilePath(String filePath, int ignoreRows) throws IOException {
		List<String[]> result=new ArrayList<String[]>();
		int rowSize=0;
		XSSFWorkbook wb=new XSSFWorkbook(new FileInputStream(new File(filePath)));
		XSSFCell cell=null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet st=wb.getSheetAt(sheetIndex);
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				XSSFRow row=st.getRow(rowIndex);
				if(row==null) continue;
				int tempRowSize=row.getLastCellNum()+1;
				if(tempRowSize>rowSize) rowSize=tempRowSize;
				String[] values=new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue=false;
				for (short columuIndex = 0;columuIndex <= row.getLastCellNum(); columuIndex++) {
					String value="";
					cell=row.getCell(columuIndex);
					if(cell!=null){
						switch(cell.getCellType()){
							case HSSFCell.CELL_TYPE_STRING:
								value=cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if(HSSFDateUtil.isCellDateFormatted(cell)){
									Date date=cell.getDateCellValue();
									if(date!=null){
										value=new SimpleDateFormat("yyyy-MM-dd").format(date);
									}else{
										value="";
									}
								}else{
									value=new DecimalFormat("0").format(cell.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								if(!cell.getStringCellValue().equals("")){
									value=cell.getStringCellValue();
								}else{
									value=cell.getNumericCellValue()+"";
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value="";
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value=(cell.getBooleanCellValue()==true?"Y":"N");
								break;
							default:
								value="";
						}
						if(columuIndex==0&&value.trim().equals("")) break;
						values[columuIndex]=rightTrim(value);
						hasValue=true;
					}
				}
				if(hasValue) result.add(values);
			}
		}
		String[][] returnArray=new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i]=(String[])result.get(i);
		}
		return returnArray;
	}
	
	private static String[][] execl2007Read(MultipartFile file, int ignoreRows) throws IOException {
		List<String[]> result=new ArrayList<String[]>();
		int rowSize=0;
		XSSFWorkbook wb=new XSSFWorkbook(file.getInputStream());
		XSSFCell cell=null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet st=wb.getSheetAt(sheetIndex);
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				XSSFRow row=st.getRow(rowIndex);
				if(row==null) continue;
				int tempRowSize=row.getLastCellNum()+1;
				if(tempRowSize>rowSize) rowSize=tempRowSize;
				String[] values=new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue=false;
				for (short columuIndex = 0;columuIndex <= row.getLastCellNum(); columuIndex++) {
					String value="";
					cell=row.getCell(columuIndex);
					if(cell!=null){
						switch(cell.getCellType()){
						case HSSFCell.CELL_TYPE_STRING:
							value=cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								Date date=cell.getDateCellValue();
								if(date!=null){
									value=new SimpleDateFormat("yyyy-MM-dd").format(date);
								}else{
									value="";
								}
							}else{
								value=new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							if(!cell.getStringCellValue().equals("")){
								value=cell.getStringCellValue();
							}else{
								value=cell.getNumericCellValue()+"";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value="";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value=(cell.getBooleanCellValue()==true?"Y":"N");
							break;
						default:
							value="";
						}
						if(columuIndex==0&&value.trim().equals("")) break;
						values[columuIndex]=rightTrim(value);
						hasValue=true;
					}
				}
				if(hasValue) result.add(values);
			}
		}
		String[][] returnArray=new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i]=(String[])result.get(i);
		}
		return returnArray;
	}
	
	public static String rightTrim(String str){
		if(str==null) return "";
		int length=str.length();
		for (int i = length-1; i>=0;i--) {
			if(str.charAt(i)!=0x20) break;
			length--;
		}
		return str.substring(0,length);
	}
	
	public static String zipFiles(String path, String zipFileName){
		if(path == null || "".equals(path)){
			return null;
		}
        String outFileName = path+"/"+zipFileName+".zip";
        File zipFile = new File(outFileName);
        
        ZipOutputStream zipOut = null;
        try{
	        zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
	        startCompress(zipOut,path,path);
	        
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try {
				zipOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        return outFileName;
    }
	
	private static void startCompress(ZipOutputStream zipOut, String path, String directory){
		File file = new File(directory);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i=0;i<files.length;i++){
				File aFile = files[i];
				if(aFile.isDirectory()){
					String newPath = path+"/"+aFile.getName();
					compressPath(zipOut,newPath);
					startCompress(zipOut,newPath,aFile.getPath());
				}else{
					compressFile(zipOut,path,aFile);
				}
			}
		}else{
			compressFile(zipOut,path,file);
		}
	}
	
	private static void compressFile(ZipOutputStream zipOut, String path, File file){
		if(file.getPath().indexOf(".zip") > 0){
			return;
		}
		ZipEntry entry = new ZipEntry(path+"/"+file.getName());
		InputStream input = null;
		try {
			zipOut.putNextEntry(entry);
			input = new FileInputStream(file);
			int temp = 0;
            while ((temp = input.read()) != -1) {
                zipOut.write(temp);
            }
            zipOut.closeEntry();
            
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(input != null)input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void compressPath(ZipOutputStream zipOut, String path){
		ZipEntry entry = new ZipEntry(path+"/");
		
		try {
			zipOut.putNextEntry(entry);
			zipOut.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


