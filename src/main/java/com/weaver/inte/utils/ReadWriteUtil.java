/** 
  *      
  * 
  */
package com.weaver.inte.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * date: 2018年6月18日 下午4:14:25<br/>
 * 
 * @version 1.0
 * @since JDK
 * 
 */
public class ReadWriteUtil {

	/**
	 * 根据流读取内容
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static final String read(InputStream in) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = -1;
		while((len = in.read(bytes))!= -1){
			os.write(bytes, 0, len);
		}
		in.close();
		os.close();
		return os.toString("UTF-8");
	}

	/**
	 * 根据文件读取内容
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static final String read(File file) throws Exception {
		return read(new FileInputStream(file));
	}

	/**
	 * 根据文件名称读取内容
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static final String read(String fileName) throws Exception {
		if (fileName == null || fileName.trim().equals("")) {
			return "";
		}
		return read(new File(fileName));
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static final void write(File file, String fileContent,boolean isAppend) throws Exception {
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file, isAppend);
		byte[] bytes = fileContent.getBytes("UTF-8");
		fos.write(bytes, 0, bytes.length);
		fos.close();
	}

	public static final void write(String filePath, String fileContent,boolean isAppend) throws Exception {
		write(new File(filePath), fileContent, isAppend);
	}
	public static final void write(String filePath, String fileContent) throws Exception {
		write(new File(filePath), fileContent, false);
	}
}
