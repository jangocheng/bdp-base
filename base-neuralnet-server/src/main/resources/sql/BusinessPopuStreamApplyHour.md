selectRegisterRatio
===
	select psh.biz_date ,
    sum(psh.register_amount) as register_amount ,
    sum(psh.register_identified) as register_identified ,
    sum(psh.applied_amount) as applied_amount ,
    sum(psh.apply_pass) as apply_adopt,
    sum(psh.machine_refuse) as machine_refuse,
    sum(psh.withdraw_amount) as withdraw_amount
    	
    from business_popu_stream_apply_hour psh  
    where psh.biz_date  BETWEEN #start# and #end#
    GROUP BY psh.biz_date
    
 selectChannelRatio   
===
    select psh.biz_date ,
    psh.channel,
    sum(psh.register_amount) as register_amount 
    from business_popu_stream_apply_hour psh  
    where psh.biz_date BETWEEN #start# and #end#
    GROUP BY psh.biz_date,psh.channel ORDER BY biz_date desc,register_amount desc