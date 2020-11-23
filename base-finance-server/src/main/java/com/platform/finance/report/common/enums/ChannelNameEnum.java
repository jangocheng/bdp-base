package com.platform.finance.report.common.enums;

/**
 * @author wlhbdp
 * @date 2020/9/4 18:42
 * @description 渠道名称
 */
public enum ChannelNameEnum {

    FBD("fbd", "电子钱包"),
    RRJC("rrjc", "人人聚财"),
    YKD("ykd", "云科贷"),
    XM("xm", "徙木"),
    HQXY("hqxy", "横琴星盈"),
    WLD("wld", "我来贷"),
    JZFQ("jzfq", "桔子分期");

    private String code;
    private String desc;

    ChannelNameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFrom(String code) {
        for (ChannelNameEnum e : ChannelNameEnum.values()) {
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
