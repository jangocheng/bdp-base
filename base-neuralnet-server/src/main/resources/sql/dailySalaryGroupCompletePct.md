selectByBizDate
===
    select tmp.sjtname,sum(IFNULL(tmp.active_amt,0)) active_amt
	from (select #use("cols")# from daily_salary_group_complete_pct  where sjtname not in ("H","J") and  id in
	(select max(id) from daily_salary_group_complete_pct where biz_date=#bizDate# GROUP BY sjtname)) tmp 
	GROUP BY tmp.sjtname
cols
===
	id,(case WHEN sjtname = "FG" then "FII" else sjtname end ) sjtname,active_amt


	