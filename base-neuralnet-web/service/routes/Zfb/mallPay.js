const { getUserPayTarge, getMallPayTable } = require('../../api/zfb-mallPay');
const { pagination } = require('../../utils/pagination');
const DateJs = require('./../../utils/DateJs');
const { zfbTableFormatter } = require('./zfbTableFormatter');

class MallPay {
  /**
   * 请求“推广渠道用户支付指标”
   * @param {Object} ctx
   */
  static async userPayTarge(ctx) {
    try {
      let { startDate, endDate } = ctx.query;
      const { dateType, popuChannel, payWays } = ctx.query;
      startDate = DateJs.DateFormatterYMD(startDate, 'YYYY/MM/DD');
      endDate = DateJs.DateFormatterYMD(endDate, 'YYYY/MM/DD');
      const res = await getUserPayTarge({
        startDate,
        endDate,
        dateType,
        popuChannel,
        payWays,
      });
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: zfbTableFormatter(res.data, { isTable: false, dateType }),
      };
      return ctx;
    } catch (error) {
      console.log(error);
    }
  }

  /**
   * 请求“商城支付报表”
   * @param {Object} ctx
   */
  static async getMallPayTable(ctx) {
    try {
      let { startDate, endDate } = ctx.query;
      const { dateType, popuChannel, payWays, pageNum, pageSize } = ctx.query;
      // 获取渠道数据
      const res = await getMallPayTable({
        startDate,
        endDate,
        dateType,
        popuChannel,
        payWays,
      });
      res.data = zfbTableFormatter(res.data, { dateType });
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: {
          list: pagination(res.data, pageNum, pageSize),
          total: res.data.length,
        },
      };
      return ctx;
    } catch (error) {
      console.log(error);
    }
  }
}

module.exports = MallPay;
