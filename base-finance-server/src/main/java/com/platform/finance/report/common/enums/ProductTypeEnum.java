package com.platform.finance.report.common.enums;

/**
 * @author wlhbdp
 * @date 2020/9/4 19:09
 * @description 产品类型
 */
public enum ProductTypeEnum {

    XFD("xfd", "消费贷"),
    ZD("zd", "助贷"),
    XJD("xjd", "现金贷"),
    FQCP("fqcp", "分期产品"),
    TXCP("txcp", "提现产品");

    private String code;
    private String desc;

    ProductTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFrom(String code) {
        for (ProductTypeEnum e : ProductTypeEnum.values()) {
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
