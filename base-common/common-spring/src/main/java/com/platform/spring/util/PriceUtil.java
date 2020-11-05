package com.platform.spring.util;

import org.apache.commons.lang3.StringUtils;

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
