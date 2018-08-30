package com.sgaop.project;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/30
 */
public class FileUtil {

    final static int BUFFER = 1024;

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
        System.out.println(file.getAbsolutePath());
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
