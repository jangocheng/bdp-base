selectRegisterRatio
===
	select arh.biz_date ,   
    sum(arh.applied_amount) as applied_amount ,
    sum(arh.apply_pass) as apply_pass,
    sum(arh.approval_sum) as approval_sum,
    sum(arh.apply_ing) as apply_ing
    from business_apply_hour arh  
    where arh.biz_date BETWEEN #start# and #end#
    GROUP BY arh.biz_date
    
 selectChannelRatio   
===
    select arh.biz_date ,
    arh.channel,
    sum(arh.applied_amount) as applied_amount    
    from business_apply_hour arh  
	where arh.biz_date BETWEEN #start# and #end#
    GROUP BY arh.biz_date,arh.channel ORDER BY biz_date desc,applied_amount desc