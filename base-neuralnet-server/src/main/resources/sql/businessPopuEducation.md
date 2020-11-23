selectTotal
===
	select
    (case when substring(type,1,9) = 'STATUS001' or substring(type,1,9) = 'STATUS002' then '硕士及以上' 
     when substring(type,1,9) = 'STATUS003' then '本科'
     when substring(type,1,9) = 'STATUS004' then '大专'
     when substring(type,1,9) = 'STATUS006' then '高中'
     else '初中及以下(包含中专)' end) type,
    count(distinct customer_id) amount
    from business_popu_education where biz_date BETWEEN #start# and #end#  #use("condition")#
    GROUP BY (case when substring(type,1,9) = 'STATUS001' or substring(type,1,9) = 'STATUS002' then '硕士及以上' 
              when substring(type,1,9) = 'STATUS003' then '本科'
              when substring(type,1,9) = 'STATUS004' then '大专'
              when substring(type,1,9) = 'STATUS006' then '高中'
              else '初中及以下(包含中专)' end)  
   
    

condition
===
	
	@if(!isEmpty(channel)){
	 and channel=#channel#
	@}