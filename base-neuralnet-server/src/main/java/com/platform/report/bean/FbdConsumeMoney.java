package com.platform.report.bean;

import lombok.Getter;
import lombok.Setter;
import org.beetl.sql.core.annotatoin.Table;


@Getter
@Setter
@Table(name="base-bi.fbd_consume_money")
public class FbdConsumeMoney   {
	private Long id ;
    private String amtType;
    private String typeName;
    private Double amount;
	private String bizDate ;
	private Integer bizHour ;


}
