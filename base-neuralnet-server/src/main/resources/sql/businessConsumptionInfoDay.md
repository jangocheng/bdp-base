selectConsumptionAmount
===
	select biz_date,consumption_type,sum(consumption_amount) consumption_amount 
	from business_consumption_info_day  where  biz_date BETWEEN #start# and #end# #use("condition")# 
	GROUP BY biz_date,consumption_type
    order by biz_date desc
    
selectConsumptionSum
=== 
 	select biz_date,consumption_type,sum(consumption_sum) consumption_sum 
 	from business_consumption_info_day  where  biz_date BETWEEN #start# and #end# #use("condition")# 
 	GROUP BY biz_date,consumption_type
    order by biz_date desc
   

selectMaxConsumption
===
    select biz_date,consumption_type,max(max_consumption_sum) max_consumption_sum 
    from business_consumption_info_day  where  biz_date BETWEEN #start# and #end# #use("condition")# 
    GROUP BY biz_date,consumption_type
    order by biz_date desc

selectConsumptionAmountSum
===
    select biz_date,consumption_type,sum(consumption_sum) consumption_sum,sum(consumption_amount) consumption_amount
    from business_consumption_info_day  where  biz_date BETWEEN #start# and #end# #use("condition")# 
    GROUP BY biz_date,consumption_type
    order by biz_date desc
    

selectSummaryByPayType
===
    select consumption_type,sum(consumption_sum) consumption_sum
    from business_consumption_info_day  where  biz_date BETWEEN #start# and #end# #use("condition")# 
    GROUP BY consumption_type
  



condition
===
	@if(!isEmpty(customerType)){
	 and customer_type=#customerType#
	@}	
	@if(!isEmpty(consumptionType)){
	 and consumption_type=#consumptionType#
	@}

	

	

	