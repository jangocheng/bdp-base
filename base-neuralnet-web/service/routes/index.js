const router = require('koa-router')();
const CHECKTOKEN = require('../middlewares/checkToken'); //检查Token标识
const TimeFilter = require('../middlewares/timeFilter'); //日期中间件--先过滤一次日期
const BusRegist = require('./business/BusRegister/index'); //业务大盘数据--实时注册数据
const BusApply = require('./business/BusApply/index'); //业务大盘数据--实时申请数据
const PDChannel = require('./business/pdChannel/index'); //业务大盘数据--推广引流(渠道)相关
const PDTime = require('./business/pdTime'); //业务大盘数据--推广引流(时间)相关
const IMEI = require('./IMEI'); //设备号入口相关
const Customer = require('./customer'); //客户模块相关
const PW = require('./pw/index'); //支付提现模块相关
const StoreAudit = require('./StoreAudit'); //门店审核
const MerchantAudit = require('./MerchantAudit'); //商户审核
const Fbd = require('.Zfb'); //支付宝日报
const ZfbPay2Draw = require('.Zfb/pay2draw'); //支付宝日报支付提现模块
const Pay2Water = require('.Zfb/pay2water'); //支付宝日报支付流水模块
const MallPay = require('.Zfb/mallPay'); //支付宝日报--商城支付模块
const DailyWage = require('.Zfb/dailyWage'); //支付宝日报--电商收入模块

// 用户模块API
router
  /**
   * --------------------------同一设备登录用户查询-------------------
   */
  .get(
    '/customer/loadCustomerUsedSameDevice',
    CHECKTOKEN.init,
    IMEI.queryIMEI,
    IMEI.reqIMEI,
  )
  /**
   * --------------------------客户一度人脉逾期查询-------------------
   */
  .get(
    '/customer/loadSuspiciousCustomersByPhoneNumber',
    CHECKTOKEN.init,
    Customer.queryCustomer,
    Customer.reqCustomer,
  )
  /**
   * ----------------------业务大盘--注册数据-----------------------
   */
  // 业务大盘数据----获取注册渠道信息
  .get('/business/getRegisterChanel', CHECKTOKEN.init, BusRegist.queryChanel)
  //渠道注册报表数据
  .get(
    '/business/getBusTableData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    BusRegist.queryBusRegist,
    BusRegist.reqBusRegist,
  )
  //注册到进件各环节数量
  .get(
    '/business/getRegisterData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    BusRegist.queryRegisterData,
    BusRegist.reqBusRegist,
  )
  //渠道注册量占比及“环比”
  .get(
    '/business/getChanelChartData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    BusRegist.getChanelChartData,
    BusRegist.reqBusRegist,
  )
  /**
   * --------------------业务大盘--申请数据---------------------
   */
  // 渠道申请报表
  .get(
    '/business/getBusApplyData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    BusApply.queryBusApply,
    BusApply.reqBusRegist,
  )
  // 申请环节“数量”
  .get(
    '/business/getApplyEchartData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    BusApply.applyforlink,
    BusApply.reqBusRegist,
  )
  // 渠道申请量占比&“环比”
  .get(
    '/business/getChannelApplyData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    BusApply.getChanelApplyData,
    BusApply.reqBusRegist,
  )
  // 获取注册渠道信息
  .get('/business/getApplyChanel', CHECKTOKEN.init, BusApply.queryChanel)
  /**
   * ------------------------推广引流(渠道)-------------------------
   */
  .get('/pdchannel/getPDChanel', CHECKTOKEN.init, PDChannel.queryChanel)
  .get(
    '/pdchannel/PDAppleTableData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PDChannel.queryPDChannelTable,
    PDChannel.reqBusRegist,
  )
  // 推广引流(渠道)--渠道注册数量
  .get(
    '/pdchannel/PDChannelRegisterData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PDChannel.getChannelRegisterData,
    PDChannel.reqBusRegist,
  )
  // 渠道注册量占比
  .get(
    '/pdchannel/getChannelApplyData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PDChannel.getChanelApplyData,
    PDChannel.reqBusRegist,
  )
  .get(
    '/pdchannel/popularizeConsumption',
    CHECKTOKEN.init,
    PDChannel.getPopularizeConsumptionData,
  )
  .get(
    '/pdchannel/popularizeStore',
    CHECKTOKEN.init,
    PDChannel.getPopularizeStoreData,
  )
  .get(
    '/pdchannel/popularizeBrand',
    CHECKTOKEN.init,
    PDChannel.getPopularizeBrandData,
  )
  /**
   * -------------------推广渠道(时间)------------------
   */
  // 获取表格数据
  .get(
    '/pdtime/PDAppleTableData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PDTime.queryPDTimeTable,
    PDTime.reqBusRegist,
  )
  // 获取通过人数和转化率
  .get(
    '/pdtime/applyApprovedAmount',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PDTime.queryPDTimeApprovedAmount,
    PDTime.reqBusRegist,
  )
  // 汇总数据
  .get(
    '/pdtime/summary',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PDTime.queryPDTimeSummary,
  )
  /**
   * ------------------------门店审核模块-----------------
   */
  .get(
    '/StoreAudit/getStoreAuditApplyTableData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    StoreAudit.queryApplyTable,
    StoreAudit.reqAppltTable,
  )
  // 注册量趋势图
  .get(
    '/StoreAudit/getRegisterData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    StoreAudit.queryRegisterData,
    StoreAudit.reqAppltTable,
  )
  .get(
    '/StoreAudit/getActiveStoreData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    StoreAudit.queryActiveStoreData,
    StoreAudit.reqAppltTable,
  )
  /**
   * 商户审核
   */
  .get(
    '/MerchantAudit/getMerchantAuditApplyTableData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    MerchantAudit.queryApplyTable,
    MerchantAudit.reqAppltTable,
  )
  // 注册量趋势图
  .get(
    '/MerchantAudit/getRegisterData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    MerchantAudit.queryRegisterData,
    MerchantAudit.reqAppltTable,
  )
  .get(
    '/MerchantAudit/getActiveMerchantData',
    CHECKTOKEN.init,
    TimeFilter.filter,
    MerchantAudit.queryActiveMerchantData,
    MerchantAudit.reqAppltTable,
  )
  /**
   * 支付提现
   */
  // 获取消费类型
  .get('/payAndDraw/paytypes', CHECKTOKEN.init, PW.getRatePaytypes)
  // 查询交易笔数
  .get(
    '/payAndDraw/PayAmount',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PW.queryPayAmount,
  )
  // 查询交易金额
  .get('/payAndDraw/PaySum', CHECKTOKEN.init, TimeFilter.filter, PW.queryPaySum)
  // 每天最大单笔交易金额
  .get(
    '/payAndDraw/maximum',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PW.queryPayMaximum,
  )
  // 支付类型占比
  .get(
    '/payAndDraw/paytype',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PW.queryPayPaytype,
  )
  // 商品种类分布
  .get(
    '/payAndDraw/product',
    CHECKTOKEN.init,
    TimeFilter.filter,
    PW.queryPayProduct,
  )
  /**
   * ------------------------支付宝日报模块-----------------
   */
  .get('/zfbParam/conditionQuery', CHECKTOKEN.init, Fbd.queryChanel)
  // 进件注册模块
  .get('/zfbApply/conditionQuery', CHECKTOKEN.init, Fbd.conditionQuery)
  .get('/zfbApply/registerQuery', CHECKTOKEN.init, Fbd.registerQuery)
  .get('/zfbApply/amtQuery', CHECKTOKEN.init, Fbd.amtQuery)
  // 支付提现模块
  .get(
    '/zfbConsumeAmt/creditAmount',
    CHECKTOKEN.init,
    ZfbPay2Draw.getFbdCreditAmount,
  )
  .get(
    '/zfbConsumeAmt/withdrawal',
    CHECKTOKEN.init,
    ZfbPay2Draw.getFbdWithdrawal,
  )
  .get(
    '/zfbConsumeAmt/firstWithdrawal',
    CHECKTOKEN.init,
    ZfbPay2Draw.getFbdFirstWithdrawal,
  )
  .get('/zfbConsumeAmt/exportQuery', CHECKTOKEN.init, ZfbPay2Draw.P2WQuery)
  // 支付流水模块
  .get(
    '/zfbConsumePipe/PayIndicatorsByTime',
    CHECKTOKEN.init,
    Pay2Water.getPayIndicatorsByTimeQuery,
  )
  .get(
    '/zfbConsumePipe/Pay2waterTable',
    CHECKTOKEN.init,
    Pay2Water.getPay2waterTable,
  )
  // 商城支付
  .get('/zfbShopPay/userPayTarget', CHECKTOKEN.init, MallPay.userPayTarge)
  .get('/zfbShopPay/mallPayTable', CHECKTOKEN.init, MallPay.getMallPayTable)
  // 电商收入
  .get(
    '/zfbDailySalary/getTotalApplyQuery',
    CHECKTOKEN.init,
    DailyWage.totalApplyQuery,
  )
  .get(
    '/zfbDailySalary/getQuotaPayQuery',
    CHECKTOKEN.init,
    DailyWage.quotaPayQuery,
  )
  .get(
    '/zfbDailySalary/getQuotaWithdrawQuery',
    CHECKTOKEN.init,
    DailyWage.quotaWithdrawQuery,
  )
  .get(
    '/zfbDailySalary/getQuotaNewWithdrawQuery',
    CHECKTOKEN.init,
    DailyWage.quotaNewWithdrawQuery,
  )
  .get(
    '/zfbDailySalary/getDailySalaryTable',
    CHECKTOKEN.init,
    DailyWage.dailySalaryTable,
  );

module.exports = router;
