selectTotal
===
	select
    type_name type ,
    count(distinct customer_id) amount
    from business_popu_loan_period where biz_date BETWEEN #start# and #end#  #use("condition")#
    GROUP BY type_name  
   
    

condition
===
	
	@if(!isEmpty(channel)){
	 and channel=#channel#
	@}
	