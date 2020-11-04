package com.platform.spring.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wulinhao
 * @ClassName: StringCustomUtil
 * @Description: 字符串自定义工具类
 * @date 2020/12/181:55 PM
 *
 */
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
