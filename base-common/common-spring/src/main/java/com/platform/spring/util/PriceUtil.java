package com.platform.spring.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wulinhao
 * @ClassName: PriceUtil
 * @Description: 金额处理工具
 * @date 2020/06/20下午3:47
 *
 */
public class PriceUtil {

    public static String convertPrice(String price){
        if (StringUtils.isBlank(price)){
            return "0";
        }else
        {
            float priceFloat = Float.parseFloat(price);
            priceFloat =  priceFloat * 100 + 0.01f;
            int priceInt = (int) priceFloat;
            return String.valueOf(priceInt);
        }
    }
}
