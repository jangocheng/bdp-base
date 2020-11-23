package com.platform.report.common.utils;


public final class BusinessComputeUtils {
    public static boolean validBizDate(String bizDate, String start) {
        return Long.valueOf(bizDate) >= Long.valueOf(start);
    }

    /**
     * 计算比值
     *
     * @param numerator   分子 必须大于等于0.0
     * @param denominator 分母 分母必须大于0.0
     * @return 当分子分母无效时返回0.0，否则返回具体的计算结果
     */
    public static Double computeRatio(Double numerator, Double denominator) {
        Double newNumerator = DigitUtils.getValidValue(numerator);
        //判断分数的有效性
        if (!DigitUtils.validDenominator(denominator) || newNumerator < 0) {
            return 0.0;
        }
        return newNumerator / denominator;
    }

    /**
     * 计算比值
     *
     * @param numerator   分子 必须大于等于0L
     * @param denominator 分母 分母必须大于0L
     * @return 当分子分母无效时返回0.0，否则返回具体的计算结果
     */
    public static Double computeRatio(Long numerator, Long denominator) {
        Double newNumerator = DigitUtils.getValidValue(numerator) * 1.0;
        //判断分数的有效性
        if (!DigitUtils.validDenominator(denominator) || newNumerator < 0) {
            return 0.0;
        }
        return newNumerator / denominator;
    }

    /**
     * 环比的计算方法：（当前时间段的数据-上一个时间段的数据）/上一个时间段的数据
     *
     * @param pre
     * @param curr
     * @return 当分母无效时返回0.0，否则返回具体的计算结果
     */
    public static Double computeChainRatio(Double pre, Double curr) {

        //判断分分母的有效性(大于0.0)
        if (!DigitUtils.validDenominator(pre)) {
            return 0.0;
        }
        //判断分子的有效性
        Double numerator = DigitUtils.getValidValue(curr) - pre;
        if (numerator < 0) {
            return 0.0;
        }

        return numerator / pre;
    }

    /**
     * 环比的计算方法：（当前时间段的数据-上一个时间段的数据）/上一个时间段的数据
     *
     * @param pre
     * @param curr
     * @return 当分母无效时返回0.0，否则返回具体的计算结果
     */
    public static Double computeChainRatio(Long pre, Long curr) {
        //判断分分母的有效性(大于0.0)
        if (!DigitUtils.validDenominator(pre)) {
            return 0.0;
        }
        //判断分子的有效性
        Double numerator = (DigitUtils.getValidValue(curr) - pre) * 1.0;
        if (numerator < 0) {
            return 0.0;
        }

        return numerator / pre;
    }
}
