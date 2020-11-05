package com.platform.bean.factory;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wlhbdp
 * @ClassName: SalaryCommon
 * @Description: SalaryCommon
 *
 */
@Data
public class SalaryCommon {

    /**
     * 无尘室岗位津贴
     */
    @JSONField(name = "clean_room_allowance")
    private String cleanRoomAllowance;

    /**
     * 论件计酬
     */
    @JSONField(name = "payment_by_piece")
    private String paymentByPiece;

    /**
     * 考核岗位津贴
     */
    @JSONField(name = "evaluation_allowance")
    private String evaluationAllowance;


    /**
     * 数据类型  （库名.表名）
     */
    private String dataType;

    /**
     * 特种岗位津贴
     */
    @JSONField(name = "specialized_workers_allowance")
    private String specializedWorkersAllowance;

    /**
     * 奖金
     */
    private String prize;

    /**
     * 關鍵工藝品質自感知獎金
     */
    @JSONField(name = "handicraft_prize")
    private String handicraftPrize;

    /**
     * 基本工资
     */
    @JSONField(name = "base_salary")
    private String baseSalary;

    /**
     * 工号
     */
    @JSONField(name = "emp_no")
    private String empNo;

    /**
     * 标准薪资
     */
    @JSONField(name = "standard_salary")
    private String standardSalary;

    /**
     * 包工分红
     */
    @JSONField(name = "contractor_bonuses")
    private String contractorBonuses;

    private String id;

    /**
     * 社保暂扣
     */
    @JSONField(name = "social_insurance")
    private String socialInsurance;

    /**
     * 职务津贴
     */
    @JSONField(name = "duty_allowance")
    private String dutyAllowance;

    /**
     * 特殊岗位津贴
     */
    @JSONField(name = "special_allowance")
    private String specialAllowance;

    /**
     * 工作日
     */
    @JSONField(name = "work_date")
    private String workDate;

    /**
     * 夜班津贴
     */
    @JSONField(name = "nights_allowance")
    private String nightsAllowance;

    /**
     * 津贴
     */
    private String allowance;

    /**
     * 个人所得税
     */
    @JSONField(name = "personal_taxes")
    private String personalTaxes;

    /**
     * G1加班费
     */
    @JSONField(name = "g1_overtime_fee")
    private String g1OvertimeFee;

    /**
     * 伙食费暂扣
     */
    @JSONField(name = "board_wages")
    private String boardWages;

    /**
     * KB岗位津贴
     */
    @JSONField(name = "kb_allowance")
    private String kbAllowance;

    /**
     * 创建时间
     */
    @JSONField(name = "create_time")
    private String createTime;

    /**
     * G2加班费
     */
    @JSONField(name = "g2_overtime_fee")
    private String g2OvertimeFee;

    /**
     * 减项
     */
    private String deduction;

    /**
     * 缺勤扣款
     */
    @JSONField(name = "absent_chargebacks")
    private String absentChargebacks;

    /**
     * 地区津贴
     */
    @JSONField(name = "area_allowance")
    private String areaAllowance;

    /**
     * G3加班费
     */
    @JSONField(name = "g3_overtime_fee")
    private String g3OvertimeFee;

    /**
     * 住宿费暂扣
     */
    private String accommodation;

    /**
     * 负薪资
     */
    @JSONField(name = "negative_salary")
    private String negativeSalary;

    /**
     * 住房公积金暂扣
     */
    @JSONField(name = "housing_fund")
    private String housingFund;

    /**
     * 加班费
     */
    @JSONField(name = "overtime_fee")
    private String overtimeFee;

    /**
     * 平日加班時數
     */
    @JSONField(name = "non_work_ot_num")
    private String nonWorkOtNum;

    /**
     * 休息日上班未補休時數
     */
    @JSONField(name = "rest_markup_num")
    private String restMarkupNum;

    /**
     * 法定假日上班時數
     */
    @JSONField(name = "holiday_work_num")
    private String holidayWorkNum;


}
