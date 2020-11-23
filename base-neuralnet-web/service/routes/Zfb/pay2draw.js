const {
  getFbdConsumeAmtPassQuery,
  getFbdConsumeAmtWithdrawQuery,
  getFbdConsumeAmtFirstWithdrawQuery,
  getFbdP2WQuery,
} = require('../../api/zfb-pay2draw');
const { pagination } = require('../../utils/pagination');
const DateJs = require('./../../utils/DateJs');
const { zfbTableFormatter } = require('./zfbTableFormatter');

class ZfbPay2Draw {
  /**
   * 请求“授信金额及占比”
   * @param {Object} ctx
   */
  static async getFbdCreditAmount(ctx) {
    try {
      let { startDate, endDate, dateType, sourceType } = ctx.query;
      startDate = DateJs.DateFormatterYMD(startDate, 'YYYY/MM/DD');
      endDate = DateJs.DateFormatterYMD(endDate, 'YYYY/MM/DD');
      // 获取渠道数据
      const res = await getFbdConsumeAmtPassQuery({
        startDate,
        endDate,
        dateType,
        sourceType,
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
   * 请求“提现次数、金额及占比”
   * @param {Object} ctx
   */
  static async getFbdWithdrawal(ctx) {
    try {
      let { startDate, endDate, dateType, sourceType } = ctx.query;
      startDate = DateJs.DateFormatterYMD(startDate, 'YYYY/MM/DD');
      endDate = DateJs.DateFormatterYMD(endDate, 'YYYY/MM/DD');
      // 获取渠道数据
      const res = await getFbdConsumeAmtWithdrawQuery({
        startDate,
        endDate,
        dateType,
        sourceType,
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
   * 请求“首次提现次数、金额及占比”
   * @param {Object} ctx
   */
  static async getFbdFirstWithdrawal(ctx) {
    try {
      let { startDate, endDate, dateType, sourceType } = ctx.query;
      startDate = DateJs.DateFormatterYMD(startDate, 'YYYY/MM/DD');
      endDate = DateJs.DateFormatterYMD(endDate, 'YYYY/MM/DD');
      // 获取渠道数据
      const res = await getFbdConsumeAmtFirstWithdrawQuery({
        startDate,
        endDate,
        dateType,
        sourceType,
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
   * 请求“支付宝支付提现报表”
   * @param {Object} ctx
   */
  static async P2WQuery(ctx) {
    try {
      let { startDate, endDate } = ctx.query;
      const { dateType, sourceType, pageSize, pageNum } = ctx.query;
      // 获取渠道数据
      const res = await getFbdP2WQuery({
        startDate,
        endDate,
        dateType,
        sourceType,
      });
      res.data = zfbTableFormatter(res.data, { dateType });
      // 根据翻页来算
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

module.exports = ZfbPay2Draw;
