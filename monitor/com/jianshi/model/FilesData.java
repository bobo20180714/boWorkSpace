package com.jianshi.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class FilesData {
	private Map<String, MultipartFile> files;

	public Map<String, MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(Map<String, MultipartFile> files) {
		this.files = files;
	}
	
	public Map<String, byte[]> getBytes(){
		Map<String, byte[]> bytes=new HashMap<String, byte[]>();
		if(files==null) return bytes;		
		for(String key : files.keySet()){
			try {
				bytes.put(key, files.get(key).getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}
}
