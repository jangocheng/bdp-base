package com.platform.report.controller.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BusinessPopularizeChannelReportVO {
    /**
     * eg: 20180910, 时间
     */
    private String date;
    /**
     * eg: 渠道一,   通道
     */
    private String channel;
    /**
     * eg: 100, 注册量
     */
    private Long registerAmount;
    /**
     * eg: 50, 实名人数
     */
    private Long identifiedAmount;
    /**
     * eg: 1000,  申请件数
     */
    private Long applyAmount;
    /**
     * eg: 300,   申请通过数
     */
    private Long applyApprovedAmount;
    /**
     * 此渠道所有有额度的用户
     */
    private Long applyApprovedTotalAmount;
    /**
     * eg: 100,     机审通过数
     */
    private Long machinePassAmount;
    /**
     * eg: 600,        申请拒绝数
     */
    private Long applyRefusedAmount;
    /**
     * eg:3432 ,       机审拒绝数
     */
    private Long machineRefuseAmount;
    /**
     * eg: 20, 提现成功人数
     */
    private Long withdrawAmount;
    /**
     * eg: 1000283.23,  获批额度之和
     */
    private Double approvedCreditSum;
    /**
     * eg:1233.33,  当天申请进件成功且当天提现的金额
     */
    private Double approvedWithdrawSum;
    /**
     * eg: 80,        复贷用户数
     */
    private Long repaymentWithdrawAmount;
    /**
     * eg:124324.22,提现金额
     */
    private Double withdrawSum;
    /**
     * eg:124324,累计提现成功用户数
     */
    private Long totalWithdrawAmount;
    /**
     * eg:124324,累计提现成功次数
     */
    private Long totalWithdrawTimes;
    /**
     * eg:124324.344,累计提现成功金额
     */
    private Double totalWithdrawSum;
    /**
     * eg:124324,处于第一次逾期的用户数
     */
    private Long firstOverdueAmount;
    /**
     * eg:124324,逾期处于m1的数量
     */
    private Long m1OverdueAmount;
    /**
     * eg:124324,逾期处于m2的数量
     */
    private Long m2OverdueAmount;
    /**
     * eg:124324,逾期处于m3的数量
     */
    private Long m3OverdueAmount;
    /**
     * eg：43232        逾期处于坏账的数量
     */
    private Long badLogAmount;
}
