selectRegisterRatio
===
	select rrh.biz_date ,
    sum(rrh.register_amount) as register_amount ,
    sum(rrh.register_identified) as register_identified ,
    sum(rrh.applied_amount) as applied_amount ,
    sum(rrh.apply_pass) as apply_pass
    from business_register_hour rrh  
    where rrh.biz_date BETWEEN #start# and #end#
    GROUP BY rrh.biz_date
    
 selectChannelRatio   
===
    select rrh.biz_date ,
    rrh.channel,
    sum(rrh.register_amount) as register_amount 
    from business_register_hour rrh  
    where rrh.biz_date BETWEEN #start# and #end#
    GROUP BY rrh.biz_date,rrh.channel ORDER BY biz_date desc,register_amount desc

	
	