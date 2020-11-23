package com.platform.common.util;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-05-29 16:36
 */
public final class HdfsUtils {

    public static List<String> getFileList(FileSystem fs, String srcPath) throws IOException {
        Path path = new Path(srcPath);
        List<String> fileNames = new ArrayList<>();
        if (fs.exists(path) && fs.isDirectory(path)) {
            for (FileStatus fileStatus: fs.listStatus(path)) {
                String fileName = fileStatus.getPath().toString();
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    public static void createFile(FileSystem fs, String srcPath, byte[] contents) throws IOException {
        Path path = new Path(srcPath);
        try (FSDataOutputStream outputStream = fs.create(path)) {
            outputStream.write(contents);
        }
    }

    public static InputStream readFile(FileSystem fs, String srcPath) throws IOException {
        Path path = new Path(srcPath);
        return fs.open(path);
    }

    public static boolean deleteFile(FileSystem fs, String srcPath) throws IOException {
        Path path = new Path(srcPath);
        boolean isOk = false;
        if (fs.exists(path)) {
            isOk = fs.deleteOnExit(path);
        }
        return isOk;
    }

    /**
     * 将本地目录或文件上传的hdfs
     *
     * @param fs FileSystem
     * @param localSrc 本地文件或目录路径
     * @param dst hdfs路径
     * @throws IOException IOException
     * @return boolean
     */
    public static boolean uploadFile(FileSystem fs, String localSrc, String dst) throws IOException {

        File srcFile = new File(localSrc);
        boolean isOk;
        if (srcFile.isDirectory()) {
            isOk = copyDirectory(fs, localSrc, dst);
        } else {
            isOk = copyFile(fs, localSrc, dst);
        }
        return isOk;
    }

    /**
     * 拷贝本地文件hdfs目录下
     *
     * @param localSrc 本地文件路径
     * @param dst hdfs目录
     * @return boolean
     * @throws IOException IOException
     */
    private static boolean copyFile(FileSystem fs, String localSrc, String dst) throws IOException {
        File file = new File(localSrc);
        String dstFilePath = dst + file.getName();
        System.out.println("dstFilePath: " + dstFilePath);
        Path path = new Path(dst);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
        }
        try (InputStream in = new BufferedInputStream(new FileInputStream(file));
             OutputStream out = fs.create(new Path(dstFilePath))) {
            IOUtils.copyBytes(in, out, 4096, true);
        }
        return true;
    }

    /**
     * 拷贝本地目录到hdfs
     * @param srcDir 本地目录
     * @param dstDir hdfs目录
     * @return boolean
     * @throws IOException IOException
     */
    private static boolean copyDirectory(FileSystem fs, String srcDir, String dstDir) throws IOException {
        Path path = new Path(dstDir);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
        }
        File file = new File(srcDir);
        if (!file.exists() && file.isDirectory()) {
            throw new RuntimeException(srcDir + " is not exits or it's not a directory!");
        }
        File[] files = file.listFiles();
        if (files != null) {
            for (File f: files) {
                if (f.isDirectory()) {
                    String fname = f.getName();
                    if (dstDir.endsWith("/")) {
                        copyDirectory(fs, f.getPath(), dstDir + fname + "/");
                    } else {
                        copyDirectory(fs, f.getPath(), dstDir + "/" + fname + "/");
                    }
                } else {
                    copyFile(fs, f.getPath(), dstDir);
                }
            }
        }
        return true;
    }
}
