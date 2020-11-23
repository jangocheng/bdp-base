selectGroupByChannel
===
	select psc.channel,sum(psc.payment_amount) payment_amount,
    sum(psc.payment_sum) payment_sum,
    sum(psc.withdraw_amount) withdraw_amount,
    sum(psc.withdraw_sum) withdraw_sum
    from business_popu_stream_consume_day  psc
    where #use("condition")#
    group by psc.channel 
    order by sum(payment_sum + withdraw_sum) DESC
    

selectGroupByRNameAndStoreName
===
	select psc.r_name,psc.store_name,
	sum(psc.payment_amount) payment_amount,
    sum(psc.payment_sum) payment_sum,
    sum(psc.withdraw_amount) withdraw_amount,
    sum(psc.withdraw_sum) withdraw_sum
    from business_popu_stream_consume_day  psc
    where  #use("condition")#
    group by psc.r_name,psc.store_name
    order by sum(payment_sum + withdraw_sum) DESC
selectGroupByBrandName
===
	select psc.brand_name,
    sum(psc.payment_sum) payment_sum,    
    sum(psc.withdraw_sum) withdraw_sum
    from business_popu_stream_consume_day  psc
    where #use("condition")#
    group by psc.brand_name
    order by sum(payment_sum + withdraw_sum) DESC
	

condition
===
    psc.biz_date BETWEEN #start# and #end#	
	@if(!isEmpty(channel)){
	 and psc.channel=#channel#
	@}
		