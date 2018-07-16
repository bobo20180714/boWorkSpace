/**
 * com.xpoplarsoft.compute.classload
 */
package com.xpoplarsoft.compute.classload;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.loadxml.LoadConfigFile;

/**
 * 特需计算jar包，class文件加载器
 * 
 * @author zhouxignlu 2017年4月28日
 */
public class ComputeJarLoader {

	private URLClassLoader loader = (URLClassLoader) URLClassLoader
			.getSystemClassLoader();

	/**
	 * jar文件加载，不使用文件路径，加载默认路径
	 */
	public void jarLoad() throws Exception {
		String filePath = SystemParameter.getInstance().getParameter("jarPath");
		filePath = System.class.getResource("/").getPath()+filePath;
		jarLoad(filePath);
		//jarLoad("E:/lib");
	}

	/**
	 * jar文件加载，指定文件路径
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public void jarLoad(String filePath) throws Exception {
		loadjar(filePath);
	}

	private void loadjar(String path) throws Exception {
		File libdir = new File(path);
		if (libdir != null) {

			File[] files = libdir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".jar");
				}
			});
			if(files == null){
				throw new Exception("文件夹["+path+"]不存在");
			}
			for (File jar : files) {
				addUrl(jar.toURI().toURL());
			}
		}
	}

	private void addUrl(URL url) throws Exception {
		Method addURL = URLClassLoader.class.getDeclaredMethod("addURL",
				URL.class);
		addURL.setAccessible(true);
		addURL.invoke(loader, url);
	}

	public static void main(String[] args) {
		// 加载配置文件
		LoadConfigFile.loadFile();
		ComputeJarLoader cl = new ComputeJarLoader();
		try {
			cl.jarLoad();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
