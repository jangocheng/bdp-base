package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;



@Table(name="base-bi.daily_salary_group_complete_pct")
@Getter
@Setter
public class DailySalaryGroupCompletePct   {
	
	private Long id ;
	private Long activeAmt ;
	private String bizDate ;
	private String sjtname ;
	private Date beginTime ;
	private Date createTime ;
	private Date endTime ;
}
