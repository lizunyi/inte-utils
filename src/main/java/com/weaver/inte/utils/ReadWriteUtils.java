/** 
  *      
  * 
  */
package com.weaver.inte.utils;

import java.io.ByteArrayInputStream;
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
public class ReadWriteUtils {

	/**
	 * 根据流读取内容
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static final String read(InputStream in,String encoding) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = -1;
		while((len = in.read(bytes))!= -1){
			os.write(bytes, 0, len);
		}
		in.close();
		os.close();
		return os.toString(encoding);
	}
	
	public static final String read(InputStream in) throws Exception {
		return read(in,"UTF-8");
	}
	
	
	public static final String read(File file) throws Exception {
		return read(new FileInputStream(file));
	}

	public static final String read(File file,String encoding) throws Exception {
		return read(new FileInputStream(file),encoding);
	}

	public static final String read(String fileName) throws Exception {
		if (fileName == null || fileName.trim().equals("")) {
			return "";
		}
		return read(new File(fileName));
	}

	public static final String read(String fileName,String encoding) throws Exception {
		if (fileName == null || fileName.trim().equals("")) {
			return "";
		}
		return read(new File(fileName),encoding);
	}
	
	/**
	 * 写文件
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static final void write(File file, byte[] bytes,boolean isAppend) throws Exception {
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file, isAppend);
		InputStream in = new ByteArrayInputStream(bytes);
		byte[] bys = new byte[4096];
		int len = -1;
		while((len = in.read(bys))!= -1){
			fos.write(bys, 0, len);
		}
		fos.close();
		in.close();
	}
	
	public static final void write(File file, InputStream in,boolean isAppend) throws Exception {
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file, isAppend);
		byte[] bys = new byte[4096];
		int len = -1;
		while((len = in.read(bys))!= -1){
			fos.write(bys, 0, len);
		}
		fos.close();
		in.close();
	}
	
	public static final void write(File file, String fileContent) throws Exception {
		write(file, fileContent.getBytes("UTF-8"), false);
	}
	public static final void write(File file, byte[] bytes) throws Exception {
		write(file, bytes, false);
	}
	
	public static final void write(File file, InputStream is) throws Exception {
		write(file, is, false);
	}
	public static final void write(String filePath, String fileContent) throws Exception {
		write(new File(filePath), fileContent.getBytes("UTF-8"), false);
	}
	public static final void write(String filePath, byte[] bytes) throws Exception {
		write(new File(filePath), bytes, false);
	}
	
	
	public static final void write(File file, String fileContent,boolean isAppend) throws Exception {
		write(file, fileContent.getBytes("UTF-8"), isAppend);
	}
	public static final void write(String filePath, String fileContent,boolean isAppend) throws Exception {
		write(new File(filePath), fileContent.getBytes("UTF-8"), isAppend);
	}
}
