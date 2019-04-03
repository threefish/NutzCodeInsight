package com.sgaop.util;


import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * 创建人: 黄川
 * 创建时间: 2017/9/12  10:52
 * 描述此类：
 */
public class FileUtil {

    final static Charset utf8 = Charset.forName("UTF-8");

    final static int BUFFER = 1024;

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

    /**
     * 输入流转字符串
     *
     * @param inputStream
     * @param charsetName
     * @return
     * @throws IOException
     */
    public static String ioToString(InputStream inputStream, String charsetName) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(charsetName);
    }


    /**
     * 将文件流写到指定文件
     * 文件不存在则创建
     *
     * @param file
     * @param inputStream
     * @throws IOException
     */
    public static void writeFile(File file, InputStream inputStream) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        write(inputStream, new FileOutputStream(file));
    }

    private static void write(InputStream input, OutputStream output) {
        try {
            int bytesRead;
            byte[] buffer = new byte[BUFFER];
            while ((bytesRead = input.read(buffer, 0, BUFFER)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解压文件
     *
     * @param file
     * @param root
     */
    public static void extractZipFile(File file, File root) {
        byte[] buffer = new byte[BUFFER];
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file), Charset.forName("GBK"));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (!fileName.contains("__MACOSX")) {
                    File newFile = new File(root.getAbsolutePath() + File.separator + fileName);
                    if (entry.isDirectory()) {
                        newFile.mkdirs();
                    } else {
                        newFile.getParentFile().mkdirs();
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                }
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
