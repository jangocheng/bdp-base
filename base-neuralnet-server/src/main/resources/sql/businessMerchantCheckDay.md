selectTotalByBizDate
===
	select
    sc.biz_date,
    (select  sum(apply_amount) from business_store_check_day  where biz_date <= sc.biz_date) apply_amount,
    (select  sum(apply_approved_amount) from business_store_check_day  where biz_date <= sc.biz_date) apply_approved_amount,
    (select  sum(access_first_trial_amount) from business_store_check_day  where biz_date <= sc.biz_date) access_first_trial_amount,
    (select  sum(return_supplementary_amount) from business_store_check_day  where biz_date <= sc.biz_date) return_supplementary_amount,
    (select  sum(access_apply_amount) from business_store_check_day  where biz_date <= sc.biz_date) access_apply_amount,
    (select  sum(approval_refuse_amount) from business_store_check_day  where biz_date <= sc.biz_date) approval_refuse_amount,
    (select  sum(first_trial_amount) from business_store_check_day  where biz_date <= sc.biz_date) first_trial_amount,
    (select  sum(close_amount) from business_store_check_day  where biz_date <= sc.biz_date) close_amount,
    (select  sum(activation_amount) from business_store_check_day  where biz_date <= sc.biz_date) activation_amount,
    (select  sum(refuse_amount) from business_store_check_day  where biz_date <= sc.biz_date) refuse_amount,
    (select  sum(new_amount) from business_store_check_day  where biz_date <= sc.biz_date) new_amount,
    (select  sum(access_amount) from business_store_check_day  where biz_date <= sc.biz_date) access_amount
    from business_merchant_check_day sc
    where biz_date BETWEEN #start# and #end#
    GROUP BY sc.biz_date