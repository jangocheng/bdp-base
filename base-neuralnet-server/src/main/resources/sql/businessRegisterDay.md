selectChannels
===
    select 
    aa.biz_date,
    aa.channel,
    aa.register_amount
    from
    (select biz_date,channel,sum(register_amount) register_amount  
    from business_register_day GROUP BY biz_date,channel 
    ORDER BY biz_date desc, register_amount desc) aa
    where aa.register_amount >0 ORDER BY aa.biz_date desc, aa.register_amount DESC
    	
    	
    	
selectChannelsByBizDate
===
    select 
    sum(register_amount) register_amount,
    channel
    from business_register_day where biz_date BETWEEN #start# and #end# 
    GROUP BY channel 
    ORDER BY register_amount desc; 
	
	