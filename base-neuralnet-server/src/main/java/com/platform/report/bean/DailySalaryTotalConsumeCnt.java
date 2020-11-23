package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;


@Getter
@Setter
@Table(name="base-bi.daily_salary_total_consume_cnt")
public class DailySalaryTotalConsumeCnt   {
	
	private Long id ;
	private Long totalConsumeCnt ;
	private String bizDate ;
	private String sjtname ;
	private Date beginTime ;
	private Date createTime ;
	private Date endTime ;
	
}
