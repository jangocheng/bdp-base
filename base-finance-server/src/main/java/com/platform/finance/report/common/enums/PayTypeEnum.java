package com.platform.finance.report.common.enums;

/**
 * @author wlhbdp
 * @date 2020/9/5 20:07
 * @description 还款类型
 */
public enum PayTypeEnum {

    TQJQ("tqjq", "提前结清"),
    TQHK("tqhk", "提前还款"),
    QT("qt", "其他");

    private String code;
    private String desc;

    PayTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFrom(String code) {
        for (PayTypeEnum e : PayTypeEnum.values()) {
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
