selectChannels
===
    select 
    aa.biz_date,
    aa.channel,
    aa.applied_amount
    from
    (select biz_date,channel,sum(applied_amount) applied_amount  
    from business_apply_day GROUP BY biz_date,channel 
    ORDER BY biz_date desc, applied_amount desc) aa
    where aa.applied_amount >0 ORDER BY aa.biz_date desc, aa.applied_amount DESC
    	
    	
    	
selectChannelsByBizDate
===
    select 
    sum(applied_amount) applied_amount,
    channel
    from business_apply_day where biz_date BETWEEN #start# and #end# 
    GROUP BY channel 
    ORDER BY applied_amount desc; 