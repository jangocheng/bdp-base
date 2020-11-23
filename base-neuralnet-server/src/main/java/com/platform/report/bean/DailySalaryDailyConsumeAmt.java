package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;
@Getter
@Setter
@Table(name="base-bi.daily_salary_daily_consume_amt")
public class DailySalaryDailyConsumeAmt {

	private Long id ;
	private String bizDate ;
	private Double dailyConsumeAmt ;
	private String sjtname ;
	private Date beginTime ;
	private Date createTime ;
	private Date endTime ;
}
