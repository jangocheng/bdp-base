package com.platform.spring.util;

import org.apache.commons.lang3.StringUtils;

public class StringCustomUtil {


    /**
     * 移除字符串头部尾部数字
     * @return
     */
    public static String removeHeadEndNumber(String str){
        if (StringUtils.isBlank(str)){
            return str;
        }else{
            String resultStr = str.trim();
            resultStr = resultStr.replaceAll("^\\d+", "");
            resultStr = resultStr.replaceAll("\\d+$", "");
            return resultStr.trim();
        }

    }


}
