package com.platform.bean.bbe;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wlhbdp
 * @ClassName: WokeAttendance
 *
 */

@Data
public class WokeAttendance {


    /**
     * 主键
     */
    private String id;

    /**
     * 工号
     */
    @JSONField(name = "emp_no")
    private String empNo;

    /**
     * 员工姓名
     */
    @JSONField(name = "user_name")
    private String userName;

    /**
     * 工作日
     */
    @JSONField(name = "work_date")
    private String workDate;

    /**
     * 班别
     */
    @JSONField(name = "work_type")
    private String workType;

    /**
     * BU
     */
    @JSONField(name = "to_company")
    private String bu;

    /**
     * 分发
     */
    @JSONField(name = "from_company")
    private String fromCompany;

    /**
     * 是否上班
     */
    @JSONField(name = "is_work")
    private String isWork;

    /**
     * 未出勤原因
     */
    @JSONField(name = "not_work_reason")
    private String notWorkReason;

    /**
     * 原因说明
     */
    @JSONField(name = "reason_detail")
    private String reasonDetail;

    /**
     * 上班时间
     */
    @JSONField(name = "work_time")
    private String workTime;

    /**
     * 上班刷卡时间
     */
    @JSONField(name = "start_work_time")
    private String startWorkTime;

    /**
     * 下班时间
     */
    @JSONField(name = "off_work_time")
    private String offWorkTime;

    /**
     * 下班刷卡时间
     */
    @JSONField(name = "end_work_time")
    private String endWorkTime;

    /**
     * 创建时间
     */
    @JSONField(name = "create_time")
    private String createTime;

    /**
     * 数据类型  （库名.表名）
     */
    private String dataType;

}
