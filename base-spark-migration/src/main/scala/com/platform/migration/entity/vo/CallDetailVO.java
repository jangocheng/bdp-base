package com.platform.migration.entity.vo;

/**
 * 通话记录数据
 */
public class CallDetailVO {
    /***
     * 联系人姓名
     */
    private String contactName;
    /***
     * 通话开始时间
     */
    private String startContactTime;
    /***
     * 通话时长
     */
    private String contactDuration;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getStartContactTime() {
        return startContactTime;
    }

    public void setStartContactTime(String startContactTime) {
        this.startContactTime = startContactTime;
    }

    public String getContactDuration() {
        return contactDuration;
    }

    public void setContactDuration(String contactDuration) {
        this.contactDuration = contactDuration;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /***
     * 通话类型
     */
    private String contactType;
    /***
     * 通话号码
     */
    private String phoneNumber;

}
