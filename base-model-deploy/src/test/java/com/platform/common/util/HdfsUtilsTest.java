package com.platform.common.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.*;

/**
 * author: wlhbdp
 * create: 2020-05-29 18:36
 */
public class HdfsUtilsTest {

    @Test
    public void testPathSeperator() {
        String localPath = "/tmp/model" + File.separator + "xgboost.zip";
        System.out.println(localPath);
    }

    @Test
    public void testBooleanToInteger() {
        String v = "true";
        boolean b = Boolean.parseBoolean(v);
        Integer i = b ?1:0;
        System.out.println(i);
    }

}