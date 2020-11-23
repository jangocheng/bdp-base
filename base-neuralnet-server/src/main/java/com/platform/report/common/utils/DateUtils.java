package com.platform.report.common.utils;

import java.util.Calendar;


public final class DateUtils {
    public static String getBizDate(Long millisecond){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisecond);
        Integer year = c.get(Calendar.YEAR);
        Integer month = c.get(Calendar.MONTH)+1;
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        return ""+year+(month<10?"0"+month:month)+(day<10?"0"+day:day);
    }

}
