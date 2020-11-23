package com.platform.report.common.utils;


public final class DigitUtils {
    private DigitUtils() {

    }


    /**
     * 当obj为null的时候返回0L
     *
     * @param obj
     * @return
     */
    public static Long getValidValue(Long obj) {
        return null == obj ? 0L : obj;
    }

    /**
     * 当obj为null的时候返回0.0
     *
     * @param obj
     * @return
     */
    public static Double getValidValue(Double obj) {
        return null == obj ? 0.0 : obj;
    }

    /**
     * 判断分母的有效性
     *
     * @param value 只针对大于0的Long类型的数据
     * @return
     */
    public static boolean validDenominator(Long value) {
        return null != value && value > 0;
    }

    /**
     * 判断分母的有效性
     *
     * @param value 只针对大于0.0的Double类型的数据
     * @return
     */
    public static boolean validDenominator(Double value) {
        return null != value && value > 0.0;
    }


}
