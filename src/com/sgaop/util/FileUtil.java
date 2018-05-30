package com.sgaop.util;


import java.io.*;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * 创建人: 黄川
 * 创建时间: 2017/9/12  10:52
 * 描述此类：
 */
public class FileUtil {

    final static Charset utf8 = Charset.forName("UTF-8");

    /**
     * 读取文本文件字符串
     *
     * @param path
     * @return
     */
    public static String readStringByPlugin(String path) {
        StringBuilder results = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(path), utf8))) {
            reader.lines().forEach(s -> results.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results.toString();
    }

    /**
     * 读取文本文件字符串
     *
     * @param file
     * @return
     */
    public static String readStringByFile(File file) {
        StringBuilder results = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), utf8))) {
            reader.lines().forEach(s -> results.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results.toString();
    }

    /**
     * 字符串转文件
     *
     * @param fileContent
     * @param file
     * @throws IOException
     */
    public static void strToFile(String fileContent, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, utf8)) {
            osw.write(fileContent);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
