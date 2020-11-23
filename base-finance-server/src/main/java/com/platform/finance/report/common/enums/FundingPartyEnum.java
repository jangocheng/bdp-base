package com.platform.finance.report.common.enums;

/**
 * @author wlhbdp
 * @date 2020/9/4 18:25
 * @description 资金方
 */
public enum FundingPartyEnum {
    FL("fl", "base"),
    ZGYH("zgyh", "中国银行");

    private String code;
    private String desc;

    FundingPartyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFrom(String code) {
        for (FundingPartyEnum e : FundingPartyEnum.values()) {
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
