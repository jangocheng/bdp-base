selectByBizDate
===
    select tmp.sjtname,sum(IFNULL(tmp.total_consume_amt,0.0)) total_consume_amt
	from (select #use("cols")# from daily_salary_total_consume_amt  
	where sjtname not in ("H","J") and id in 
	(select max(id) from daily_salary_total_consume_amt where biz_date=#bizDate# GROUP BY sjtname)) tmp
    GROUP BY tmp.sjtname
cols
===
	id,(case WHEN sjtname = "FG" then "FII" else sjtname end ) sjtname,total_consume_amt


	


	
	