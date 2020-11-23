package com.platform.finance.report.common.enums;

/**
 * @author wlhbdp
 * @date 2020/9/4 19:12
 * @description 产品子类型
 */
public enum ProductChildTypeEnum {

    FBDZDFQ("fbdzdfq", "电子钱包-账单分期"),
    FBDTX("fbdtx", "电子钱包-提现"),
    JSFQ("jsfq", "及时分期"),
    FBBZF("fbbzf", "电子钱包支付"),
    ZDCP("zdcp", "助贷产品"),
    YSD("ysd", "优速贷"),
    XZCP("xzcp", "薪资产品"),
    YXD("yxd", "优享贷");

    private String code;
    private String desc;

    ProductChildTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFrom(String code) {
        for (ProductChildTypeEnum e : ProductChildTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e.desc;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
