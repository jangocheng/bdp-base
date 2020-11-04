package com.platform.spring.encrypt;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MD5Utils {
    private static Logger logger = LogManager.getLogger(MD5Utils.class);

    //加密密码的MD5算法
    public static String getMD5String(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0x0f];
                str[k++] = hexDigits[byte0 & 0x0f];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String MD5(byte[] data) {
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst ;
        try {
            mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(data);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuilder hexString = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5摘要(适用于一般字串,sign MD5推荐 MD5(HashMap<String,String> params))
     *
     * @param input 待加密内容
     * @return 加密的数据
     */
    public static String MD5(String input) {
        byte[] data = null;
        try {
            data = input.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            logger.info("不支持的编码格式！");
        }
        return MD5(data);
    }

    /**
     * MD5摘要 适用于sign签名,会先进行字典序升序排序
     *
     * @param params 签名参数
     * @return 待sign的字串
     */
    public static String getSignString(HashMap<String, String> params) throws Exception {
        String input = hastToSortSigh(params);
        return MD5(input);
    }

    /**
     * 获取待签名(sign)的字串
     * 规则:按参数名的字典序升序进行排序
     * k1=v1k2=v2k3=v3
     *
     * @param params
     * @return
     */
    private static String hastToSortSigh(HashMap<String, String> params) {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        //遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        return basestring.toString();
    }
}