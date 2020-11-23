package com.platform.config;

import com.platform.common.util.HdfsUtils;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * author: wlhbdp
 * create: 2020-05-29 09:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HDFSConfigTest {
    @Autowired
    private FileSystem fileSystem;

    @Test
    public void fileSystem() throws IOException {
        String srcPath = "/user/hdfs/hbase";
        List<String> fileList = HdfsUtils.getFileList(fileSystem, srcPath);
        fileList.forEach(System.out::println);
    }

    @Test
    public void testCreateFile() throws IOException {
        String srcPath = "/user/hdfs/model/test.txt";
        HdfsUtils.createFile(fileSystem,srcPath, "This is a test file".getBytes());
    }

    @Test
    public void testReadFile() {
        String srcPath = "/user/hdfs/model/test.txt";
        InputStream in = null;
        try {
            in = HdfsUtils.readFile(fileSystem,srcPath);
            IOUtils.copyBytes(in, System.out, 4096, false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(in);
        }
    }

    @Test
    public void testDeleteFile() throws IOException {
        String srcPath = "/user/spark/model/test.txt";
        boolean isOk = HdfsUtils.deleteFile(fileSystem,srcPath);
        if (isOk) {
            System.out.println("delete file: " + srcPath + " success!");
        } else {
            System.out.println("delete file: " + srcPath + " failure!");
        }
    }

    @Test
    public void testUploadFile() throws IOException {
        String fileLocalPath = "/Users/wlhbdp/Downloads/airbnb.model.rf.zip";
        String dstPath = "/user/spark/model/";
        if (HdfsUtils.uploadFile(fileSystem, fileLocalPath, dstPath)) {
            System.out.println("File [" + fileLocalPath + "] has been uploaded successfully!");
        }
    }
}