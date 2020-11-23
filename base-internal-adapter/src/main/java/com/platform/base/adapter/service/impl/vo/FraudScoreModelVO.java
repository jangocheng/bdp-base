package com.platform.base.adapter.service.impl.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FraudScoreModelVO {
    @JSONField(ordinal = 1)
    private Double fifteenDayPctCntOrgConsumptionStages;
    @JSONField(ordinal = 2)
    private Double sixtyDayPctCntOrgCash;
    @JSONField(ordinal = 3)
    private Double fifteenDayPctCntOrgAll;
    @JSONField(ordinal = 4)
    private Double fifteenDayPctCntOrgCash;
    @JSONField(ordinal = 5)
    private Integer twentyFourMonthPToPApplyCnt;
    @JSONField(ordinal = 6)
    private Integer sixtyMonthPToPApplyCnt;
    @JSONField(ordinal = 7)
    private Integer oneMonthSmallLoanApplyCnt;
    @JSONField(ordinal = 8)
    private Integer sixtyMonthGeneralConsumptionStagesApplyCnt;
    @JSONField(ordinal = 9)
    private Integer sixtyMonthSmallLoanApplyCnt;
    @JSONField(ordinal = 10)
    private Double sevenDayPctCntCash;
    @JSONField(ordinal = 11)
    private BigDecimal blackRatio;
    @JSONField(ordinal = 12)
    private Integer sixtyMonthBankConsumerFinanceApplyCnt;
    @JSONField(ordinal = 13)
    private Double sevenDayPctCntAll;
    @JSONField(ordinal = 14)
    private Integer thirtyDayCntOrgConsumptionStages;
    @JSONField(ordinal = 15)
    private Double eighteenMonthPctCntCreditCard;
    @JSONField(ordinal = 16)
    private BigDecimal pctBlackRatio;
    @JSONField(ordinal = 17)
    private Double ninetyDayPctCntOrgConsumptionStages;
    @JSONField(ordinal = 18)
    private Integer twentyFourMonthCntOrgCash;
    @JSONField(ordinal = 19)
    private Integer sixMonthSmallLoanApplyCnt;
    @JSONField(ordinal = 20)
    private Double thirtyDayPctCntOrgConsumptionStages;
    @JSONField(ordinal = 21)
    private Double twentyFourMonthPctCntCreditCard;
    @JSONField(ordinal = 22)
    private Integer cntPassiveNotFamiliarContact;
    @JSONField(ordinal = 23)
    private Integer threeMonthSmallLoanApplyCnt;
    @JSONField(ordinal = 24)
    private Integer twentyFourMonthBankConsumerFinanceApplyCnt;
    @JSONField(ordinal = 25)
    private Integer twelveMonthBankConsumerFinanceApplyCnt;
    @JSONField(ordinal = 26)
    private Integer sixtyMonthLargeConsumerFinanceApplyCnt;
    @JSONField(ordinal = 27)
    private Integer sixMonthCntOrg;
    @JSONField(ordinal = 28)
    private Double sixtyDayPctCntOrgConsumptionStages;
    @JSONField(ordinal = 29)
    private Double eighteenMonthPctCntOrgCreditCard;
    @JSONField(ordinal = 30)
    private Double pctCntBlack;
    @JSONField(ordinal = 31)
    private Double sevenDayPctCntConsumptionStages;
    @JSONField(ordinal = 32)
    private Integer cntPassiveContact;
    @JSONField(ordinal = 33)
    private BigDecimal pctRouterRatio;
    @JSONField(ordinal = 34)
    private Integer sixtyDayCntCash;
    @JSONField(ordinal = 35)
    private Integer eighteenMonthCreditCardCentreApplyCnt;
    @JSONField(ordinal = 36)
    private Integer sevenDayApplyCnt;
    @JSONField(ordinal = 37)
    private Integer nineMonthCntCash;
    @JSONField(ordinal = 38)
    private Integer sixtyMonthApplyCnt;
    @JSONField(ordinal = 39)
    private Integer sixMonthBankConsumerFinanceApplyCnt;
    @JSONField(ordinal = 40)
    private Integer gender;
    @JSONField(ordinal = 41)
    private Double pctCntRouter;
    @JSONField(ordinal = 42)
    private Integer threeMonthPToPApplyCnt;
    @JSONField(ordinal = 43)
    private Double pctCntAll;
    @JSONField(ordinal = 44)
    private Integer sixMonthIdCardApplyCnt;
    @JSONField(ordinal = 45)
    private Double phoneGrayScore;
    @JSONField(ordinal = 46)
    private Integer threeMonthApplyCnt;
    @JSONField(ordinal = 47)
    private Integer threeMonthGeneralConsumptionStagesApplyCnt;
    @JSONField(ordinal = 48)
    private Double sixtyDayPctCntOrgCreditCard;
    @JSONField(ordinal = 49)
    private Integer twelveMonthGeneralConsumptionStagesApplyCnt;
    @JSONField(ordinal = 50)
    private Double sixMonthPctCntCreditCard;

}
