package com.platform.common.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class GZipUtils {
  
    private static final int BUFFER_SIZE = 1024;
    private static final String EXT = ".gz";
  
    /** 
     * 数据压缩 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public static byte[] compress(byte[] data) throws Exception {  
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
  
        // 压缩  
        compress(bais, baos);  
  
        byte[] output = baos.toByteArray();  
  
        baos.flush();  
        baos.close();  
  
        bais.close();  
  
        return output;  
    }  
  
    /** 
     * 文件压缩 
     *  
     * @param file 
     * @throws Exception 
     */  
    public static void compress(File file) throws Exception {  
        compress(file, true);  
    }  
  
    /** 
     * 文件压缩 
     *  
     * @param file 待压缩文件
     * @param delete 是否删除源文件
     *            是否删除原始文件 
     * @throws Exception 
     */  
    public static void compress(File file, boolean delete) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);
  
        compress(fis, fos);  
  
        fis.close();  
        fos.flush();  
        fos.close();  
  
        if (delete) {  
            file.delete();  
        }  
    }  
  
    /** 
     * 数据压缩 
     *  
     * @param is 
     * @param os 
     * @throws Exception 
     */  
    public static void compress(InputStream is, OutputStream os)  
            throws Exception {  
  
        GZIPOutputStream gos = new GZIPOutputStream(os);
  
        int count;
        byte[] data = new byte[BUFFER_SIZE];
        while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
            gos.write(data, 0, count);  
        }  
  
        gos.finish();  
  
        gos.flush();  
        gos.close();  
    }  
  
    /** 
     * 文件压缩 
     *  
     * @param path 
     * @throws Exception 
     */  
    public static void compress(String path) throws Exception {  
        compress(path, true);  
    }  
  
    /** 
     * 文件压缩 
     *  
     * @param path 
     * @param delete 
     *            是否删除原始文件 
     * @throws Exception 
     */  
    public static void compress(String path, boolean delete) throws Exception {  
        File file = new File(path);  
        compress(file, delete);  
    }  
  
    /** 
     * 数据解压缩 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decompress(byte[] data) throws Exception {  
        ByteArrayInputStream bais = new ByteArrayInputStream(data);  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
  
        // 解压缩  
  
        decompress(bais, baos);
  
        data = baos.toByteArray();
  
        baos.flush();
        baos.close();
  
        bais.close();  
  
        return data;  
    }  
  
    /** 
     * 文件解压缩 
     *  
     * @param file 
     * @throws Exception 
     */  
    public static void decompress(File file) throws Exception {  
        decompress(file, true);  
    }  
  
    /** 
     * 文件解压缩 
     *  
     * @param file 
     * @param delete 是否删除原始文件
     * @throws IOException IOException
     */  
    public static void decompress(File file, boolean delete) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""))
        ) {
            decompress(fis, fos);
            fos.flush();

            if (delete) {
                file.delete();
            }
        }
    }  
  
    /** 
     * 数据解压缩
     *
     * @param is 输入流
     * @param os 输出流
     * @throws IOException IOException
     */  
    public static void decompress(InputStream is, OutputStream os) throws IOException {

        try (GZIPInputStream gis = new GZIPInputStream(is)) {
            int count;
            byte[] data = new byte[BUFFER_SIZE];
            while ((count = gis.read(data, 0, BUFFER_SIZE)) != -1) {
                os.write(data, 0, count);
            }
        }
    }
  
    /** 
     * 文件解压缩 
     *  
     * @param path 待解压文件路径
     * @throws IOException IOException
     */  
    public static void decompress(String path) throws IOException {
        decompress(path, true);  
    }  
  
    /** 
     * 文件解压缩 
     *  
     * @param path 待解压文件路径
     * @param delete 是否删除原始文件
     * @throws Exception 
     */  
    public static void decompress(String path, boolean delete) throws IOException {
        File file = new File(path);  
        decompress(file, delete);  
    }  
  
}