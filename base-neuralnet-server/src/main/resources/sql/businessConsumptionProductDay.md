selectSummaryByProductType
===
	select product_type,sum(consumption_sum) consumption_sum 
	from business_consumption_product_day  where  biz_date BETWEEN #start# and #end# #use("condition")# 
    GROUP By product_type

condition
===
	@if(!isEmpty(customerType)){
	 and customer_type=#customerType#
	@}	
	@if(!isEmpty(consumptionType)){
	 and consumption_type=#consumptionType#
	@}