package com.platform.realtimereport.common.constant

/**
  *
  * author: wlhbdp
  */
object ApplicationCommon {
  /**
    * mongo-shake相关配置
    */
  val MONGO_SHAKE_ZFB_USER = "mongo.shake.zhifubao.user"

  val MONGO_SHAKE_ZFB_APPLY = "mongo.shake.zhifubao.apply"

  val MONGO_SHAKE_LOANORDER = "mongo.shake.loan.order"

  val MONGO_SHAKE_OPERATION_INSERT = "mongo.shake.operation.insert"

  val MONGO_SHAKE_OPERATION_UPDATE = "mongo.shake.operation.update"

  val NAMESPACE_KEY = "dataType"

  val OBJECT_WRAPPER_KEY = "object"

  val OBJECT_QUERY_KEY = "query"

  val OBJECT_$SET = "$set"

  val OPERATION_KEY = "dmlType"
  /**
    * 支付宝相关常量配置
    */
  val ZFB_CARD_ID_KEY = "cardId"

  val ZFB_APPLY_STEP_KEY = "step"

  val ZFB_APPLY_STEP_IDENTIFIED = 11

  val ZFB_CREATED_TIME_KEY = "createdTime"

  val REGISTER_AMOUNT_KEY = "zfb_register_amount"

  val IDENTIFIED_AMOUNT_KEY = "zfb_identified_amount"

  val EXPIRE_24H_IN_SECS: Int = 24 * 60 * 60

  val EXPIRE_12H_IN_SECS: Int = 12 * 60 * 60
  /**
    * Mall相关常量配置
    */
  val LOANTOTAL_PAYMENT_KEY = "totalPayment"

  val LOANPAYMENT_STATUS_KEY = "paymentStatus"

  val LOANPAYMENT_STATUS_VALUE = "0"

  val LOANACTUAL_PAYMENT = "actualPayment"

  val LOANRECEIVER_ADDRESS = "receiverAddress"

  val LOANCITY_RANK = "zfb_shop_city_rank"

  val LOANPAID_AMOUNT = "zfb_shop_paid_amount"

  val LOANPAID_NUM = "zfb_shop_paid_num"


  /**
    * 核心相关配置
    */

  /** 删除指定的前n天(当天)redis数据 */
  val REDIS_OTHER_DAY_DELETE = -30

  /** 合同数据类型 */
  val NAMESPACE_KEY_CHANNEL_CONTRACT ="mysql.hxdb.business_contract"

  /** 近n天渠道分布-redis key前缀 */
  var REDIS_KEY_CHANNEL_CONTRACT_PREFIX="nearlydaysChannelAmount:"

  /** 近n天渠道分布-渠道标记字段 */
  val CHANNEL_CONTRACT_SYSTEMCHANNELFLAG ="SYSTEMCHANNELFLAG"

  /** 近n天消费情况-redis key前缀 */
  var REDIS_KEY_CONSUMPTION_CONTRACT_PREFIX="nearlydaysConsumptionAmount:"

  /** 近n天消费情况-redis key field-支付 */
  var REDIS_KEY_CONSUMPTION_CONTRACT_PAYMENT="payment"

  /** 近n天消费情况-redis key field-提现 */
  var REDIS_KEY_CONSUMPTION_CONTRACT_WITHDRAW="withdraw"

  /** 近n天消费情况-消费日期字段  */
  val CONSUMPTION_CONTRACT_OCCURDATE = "OCCURDATE"

  /** 近n天消费情况-合同状态字段  */
  val CONSUMPTION_CONTRACT_CONTRACTSTATUS = "CONTRACTSTATUS"

  /** 近n天消费情况-合同状态字段值(020.已签合同)  */
  val CONSUMPTION_CONTRACT_CONTRACTSTATUS_VALUE = "020"

  /** 近n天消费情况-金融服务状态字段  */
  val CONSUMPTION_CONTRACT_BUSINESSTYPE = "BUSINESSTYPE"

  /** 近n天消费情况-金融服务状态提现字段值  */
  val CONSUMPTION_CONTRACT_BUSINESSTYPE_WITHDRAW_VALUE = "5"

  /** 近n天消费情况-金融服务状态支付字段值  */
  val CONSUMPTION_CONTRACT_BUSINESSTYPE_PAYMENT_VALUE = "3"

  /** 近n天消费情况-合同金额字段  */
  val CONSUMPTION_CONTRACT_BUSINESSSUM = "BUSINESSSUM"

  /** 近n天消费情况-折扣金额字段  */
  val CONSUMPTION_CONTRACT_DISCOUNT = "DISCOUNT"

  /** 近n天消费情况-免单金额字段  */
  val CONSUMPTION_CONTRACT_FREEBILLAMOUNT = "freeBillAmount"

  /** 近n天消费情况-退款金额字段  */
  val CONSUMPTION_CONTRACT_RETURNMONEY = "returnMoney"

  /** 每日实时消费金额-redis key */
  var REDIS_KEY_HOURLY_CONSUMPTION = "zfb_hourly_consumption"

  /** 每日实时消费金额-消费时间字段  */
  val CONSUMPTION_CONTRACT_DEALDATE = "dealDate"


  /**
    * canal相关配置
    */
  val MYSQL_OPERATION_INSERT = "mysql.operation.insert"

  val MYSQL_OPERATION_UPDATE = "mysql.operation.update"

  val MYSQL_HXDB_IND_INFO = "mysql.hxdb.ind_info"

  /**
    * App相关配置
    */
  val APP_DEVICE_LOCATION_DATATYPE = "app.deviceLocation.datatype"

  val APP_DEVICE_LOCATION_STAGE = "app.deviceLocation.stagecode"

  val APP_DEVICE_LOCATION_STAGE_PAYMENT = "app.deviceLocation.stage.payment"

  val APP_DEVICE_LOCATION_STAGE_WITHDRAW = "app.deviceLocation.stage.withdraw"

  val APP_DEVICE_LOCATION_LONGITUDE = "app.deviceLocation.longitude"

  val APP_DEVICE_LOCATION_LATITUDE = "app.deviceLocation.latitude"


  /**
    * 包名
    */
  val PROCESSOR_PACKAGE_NAME = "com.platform.realtimereport.streaming"
}
