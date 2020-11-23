package com.platform.base.adapter.common.util;

import org.springframework.util.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-01-21 14:44
 */
public class FileUtils {

    public static List<String> readFile(String filePath) throws IOException {
        Assert.notNull(filePath, "File path must not be null!");
        File file = new File(filePath);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
        BufferedReader br = new BufferedReader(isr);
        String line ;
        List<String> message = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            message.add(line);
        }
        return message;
    }
}
