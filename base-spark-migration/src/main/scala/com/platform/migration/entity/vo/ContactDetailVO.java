package com.platform.migration.entity.vo;

/**
 * 通讯录数据
 */
public class ContactDetailVO {

    /**
     * 号码备注
     */
    private String phoneName;
    /**
     * 原始的phoneNumber为List(若大于1个)，则转换为123|234|345(使用竖线进行分隔)
     */
    private String phoneNumber;
    /**
     * TYPE_HOME (住宅)
     * TYPE_MOBILE (手机)
     * TYPE_WORK (单位)
     * TYPE_FAX_WORK (单位传真)
     * TYPE_FAX_HOME (住宅传真)
     * TYPE_PAGER (寻呼机)
     * TYPE_OTHER (其他)
     * 号码类型
     */
    private String phoneType;
    /**
     * 通话次数
     */
    private String timeContacted;

    /**
     * 最近一次打电话的时间
     */
    private String lastTimeContacted;

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getTimeContacted() {
        return timeContacted;
    }

    public void setTimeContacted(String timeContacted) {
        this.timeContacted = timeContacted;
    }

    public String getLastTimeContacted() {
        return lastTimeContacted;
    }

    public void setLastTimeContacted(String lastTimeContacted) {
        this.lastTimeContacted = lastTimeContacted;
    }
}
