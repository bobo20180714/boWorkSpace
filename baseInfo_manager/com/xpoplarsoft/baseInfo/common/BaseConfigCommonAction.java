package com.xpoplarsoft.baseInfo.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.framework.startup.FrameStartup;

@Controller
@RequestMapping("/baseConfig")
public class BaseConfigCommonAction {

	private static Log log = LogFactory.getLog(BaseConfigCommonAction.class);
	
	/**
	 * 上传文件
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadFile")
	public @ResponseBody
	String uploadTmFile(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) {
		
		String retStr = null;
		if (file.isEmpty()) {
			if (log.isDebugEnabled()) {
				log.debug("file为空,上传文件失败");
			}
			retStr = "{ success: false, fileUrl:'' }";
			return retStr;
		}
		
		String fileName = file.getOriginalFilename();

		String filePath = new StringBuilder(this.createFileDir()).append(
				File.separator).append(fileName).toString();

		if (log.isDebugEnabled()) {
			log.debug("上传文件路径filePath=[" + filePath + "]");
		}

		boolean uploadStatus = this.wirteFileData(filePath, file);

		if (uploadStatus) {
			if (log.isDebugEnabled()) {
				log.debug("上传文件成功,filePath=[" + filePath + "]");
			}
			retStr = "{ success: true, fileUrl:'"+filePath+"' }";
		} else {
			if (log.isErrorEnabled()) {
				log.error("上传文件失败");
			}
			retStr = "{ success: false, fileUrl:'' }";
		}

		return retStr;
	}
	
	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 */
	private boolean wirteFileData(String filePath, MultipartFile uploadFile) {

		FileOutputStream fos = null;
		InputStream is = null;
		try {
			File file = new File(FrameStartup.PROJECT_PATH + File.separator
					+ filePath);

			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			is = uploadFile.getInputStream();
			byte[] readByte = new byte[1024];
			int readLen = 0;
			while ((readLen = is.read(readByte)) > 0) {
				fos.write(readByte, 0, readLen);
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
				}
			}
		}

		return true;
	}
	
	/**
	 * 创建上传目录
	 * 
	 * @return
	 */
	private String createFileDir() {
		String filePath = new StringBuffer(getUploadFilePath()).append(
				File.separator).append(
				String.valueOf(System.currentTimeMillis())).toString();

		// 判断文件路径是否存在
		File parentDir = new File(FrameStartup.PROJECT_PATH + File.separator
				+ filePath);
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		return filePath;
	}
	
	
	/**
	 * 获取服务器上传目录
	 * 
	 * @return
	 */
	private String getUploadFilePath() {

		String uploadFilePath = SystemParameter.getInstance().getParameter(
				"uploadFilePath");

		if (null == uploadFilePath) {
			uploadFilePath = "/fileupload/";
		}

		return uploadFilePath;
	}
	
}
