/**
 *
 */
package com.weaver.inte.utils;

import java.io.*;

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
    public static final String read(InputStream in, String encoding) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            in.close();
            os.close();
            return os.toString(encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String read(InputStream in) {
        return read(in, "UTF-8");
    }


    public static final String read(File file) {
        try {
            return read(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String read(File file, String encoding) {
        try {
            return read(new FileInputStream(file), encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String read(String fileName) {
        if (fileName == null || fileName.trim().equals("")) {
            return "";
        }
        return read(new File(fileName));
    }

    public static final String read(String fileName, String encoding) {
        if (fileName == null || fileName.trim().equals("")) {
            return "";
        }
        return read(new File(fileName), encoding);
    }

    /**
     * 写文件
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static final void write(File file, byte[] bytes, boolean isAppend) {
        try {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file, isAppend);
            InputStream in = new ByteArrayInputStream(bytes);
            byte[] bys = new byte[4096];
            int len = -1;
            while ((len = in.read(bys)) != -1) {
                fos.write(bys, 0, len);
            }
            fos.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void write(File file, InputStream in, boolean isAppend) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file, isAppend);
            byte[] bys = new byte[4096];
            int len = -1;
            while ((len = in.read(bys)) != -1) {
                fos.write(bys, 0, len);
            }
            fos.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void write(File file, String fileContent) {
        try {
            write(file, fileContent.getBytes("UTF-8"), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void write(File file, byte[] bytes) {
        write(file, bytes, false);
    }

    public static final void write(File file, InputStream is) {
        write(file, is, false);
    }

    public static final void write(String filePath, String fileContent) {
        try {
            write(new File(filePath), fileContent.getBytes("UTF-8"), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void write(String filePath, byte[] bytes) {
        write(new File(filePath), bytes, false);
    }


    public static final void write(File file, String fileContent, boolean isAppend) {
        try {
            write(file, fileContent.getBytes("UTF-8"), isAppend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void write(String filePath, String fileContent, boolean isAppend) {
        try {
            write(new File(filePath), fileContent.getBytes("UTF-8"), isAppend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
