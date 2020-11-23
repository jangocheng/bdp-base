const {
  getPayIndicatorsByTimeQuery,
  getPay2waterTable,
} = require('../../api/zfb-pay2water');
const { pagination } = require('../../utils/pagination');
const DateJs = require('./../../utils/DateJs');
const { zfbTableFormatter } = require('./zfbTableFormatter');

class Pay2Water {
  static async getPayIndicatorsByTimeQuery(ctx) {
    try {
      let { startDate, endDate, dateType, sourceType, payType } = ctx.query;
      startDate = DateJs.DateFormatterYMD(startDate, 'YYYY/MM/DD');
      endDate = DateJs.DateFormatterYMD(endDate, 'YYYY/MM/DD');
      // 获取渠道数据
      const res = await getPayIndicatorsByTimeQuery({
        startDate,
        endDate,
        dateType,
        sourceType,
        payType,
      });
      res.data = zfbTableFormatter(res.data, { isTable: false, dateType });
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: res.data,
      };
      return ctx;
    } catch (error) {
      console.log(error);
    }
  }

  /**
   * 请求“支付流水报表”
   * @param {Object} ctx
   */
  static async getPay2waterTable(ctx) {
    try {
      let {
        startDate,
        endDate,
        dateType,
        sourceType,
        payType,
        pageNum,
        pageSize,
      } = ctx.query;
      // 获取渠道数据
      const res = await getPay2waterTable({
        startDate,
        endDate,
        dateType,
        sourceType,
        payType,
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

module.exports = Pay2Water;
