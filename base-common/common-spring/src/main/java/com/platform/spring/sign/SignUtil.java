package com.platform.spring.sign;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * Created by born on 2017/6/28.
 *
 */
public class SignUtil {

    private static Logger logger = LogManager.getLogger(SignUtil.class);

    public static String signTopHttpRequest(Map<String, String[]> params, String secret, String url) throws IOException {
        Map<String, String> result = new HashMap<String, String>();
        result.put("url", url);
        for (String key : params.keySet()) {
            String[] strings = params.get(key);
            result.put(key, strings[0]);
        }
        return signTopRequest(result, secret);
    }

    public static String signTopRequest(Map<String, String> params, String secret) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();

        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(key) & StringUtils.isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        logger.info("签名参数:{}", query.toString());
        bytes = encryptHMAC(query.toString(), secret);


        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    protected static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        logger.debug("签名结果:{}", sign.toString());
        return sign.toString();
    }
}
