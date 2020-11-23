package com.platform.migration.entity;

import com.platform.migration.entity.vo.ContactDetailVO;

import java.util.List;

/**
 * 通讯录
 */
public class AppContactDO {
    /**
     * 创建时间，13位时间戳
     */
    private Long createTime;

    /**
     * 支付宝用户表中的userId
     */
    private String globalUserId;
    /**
     * 身份证
     */
    private String cardId;
    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * "00"-注册 "01"-额度申请 "0201"-支付成功 "0202"-提现成功 "03"-变更手机号 "04"-登录
     */
    private String stage;
    /**
     * 合同号
     */
    private String relId;

    private List<ContactDetailVO> contacts;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getGlobalUserId() {
        return globalUserId;
    }

    public void setGlobalUserId(String globalUserId) {
        this.globalUserId = globalUserId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public List<ContactDetailVO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDetailVO> contacts) {
        this.contacts = contacts;
    }
}
