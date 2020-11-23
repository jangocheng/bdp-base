package com.platform.finance.report.common.enums;

/**
 * @author wlhbdp
 * @date 2020/9/4 18:45
 * @description 资产渠道类型
 */
public enum  ChannelTypeEnum {
    ZD("zd", "助贷"),
    RZ("rz", "融资"),
    ZYFYL("zyfyl", "自营(非引流)"),
    ZYYL("zyyl", "自营(引流)");

    private String code;
    private String desc;

    ChannelTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFrom(String code) {
        for (ChannelTypeEnum e : ChannelTypeEnum.values()) {
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
