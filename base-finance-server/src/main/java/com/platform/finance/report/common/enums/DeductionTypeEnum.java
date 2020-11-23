package com.platform.finance.report.common.enums;

/**
 * @author wlhbdp
 * @date 2020/9/9 17:38
 * @description 减免类型
 */
public enum DeductionTypeEnum {

    SDJM("sdjm", "手动减免"),
    ZDJM("zdjm", "自动减免"),
    YHQJM("yhqjm", "优惠券减免");

    private String code;
    private String desc;

    DeductionTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFrom(String code) {
        for (DeductionTypeEnum e : DeductionTypeEnum.values()) {
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
