const {
  getTotalApplyQuery,
  getQuotaPayQuery,
  getQuotaWithdrawQuery,
  getQuotaNewWithdrawQuery,
  getDailySalaryTable,
} = require('../../api/zfb-dailyWage');
const { pagination } = require('../../utils/pagination');
const { zfbTableFormatter } = require('./zfbTableFormatter');

class Fbd {
  /**
   * 请求“日结薪用户申请情况”
   * @param {Object} ctx
   */
  static async totalApplyQuery(ctx) {
    try {
      const {
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
        approveStatus,
      } = ctx.query;
      // 获取渠道数据
      const res = await getTotalApplyQuery({
        approveStatus,
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
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
   * 请求“薪资额度支付情况查询”
   * @param {Object} ctx
   */
  static async quotaPayQuery(ctx) {
    try {
      const {
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
        approveStatus,
      } = ctx.query;
      // 获取渠道数据
      const res = await getQuotaPayQuery({
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
        approveStatus,
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
   * 请求“信用额度提现情况”
   * @param {Object} ctx
   */
  static async quotaWithdrawQuery(ctx) {
    try {
      const { startDate, endDate, dateType, quotaStatus, sjtType } = ctx.query;
      // 获取渠道数据
      const res = await getQuotaWithdrawQuery({
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
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
   * 请求“新增信用额度提现情况”
   * @param {Object} ctx
   */
  static async quotaNewWithdrawQuery(ctx) {
    try {
      const { startDate, endDate, dateType, quotaStatus, sjtType } = ctx.query;
      // 获取渠道数据
      const res = await getQuotaNewWithdrawQuery({
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
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
   * 请求“电商收入报表”
   * @param {Object} ctx
   */
  static async dailySalaryTable(ctx) {
    try {
      const {
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
        pageSize,
        pageNum,
      } = ctx.query;
      // 获取渠道数据
      const res = await getDailySalaryTable({
        startDate,
        endDate,
        dateType,
        quotaStatus,
        sjtType,
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

module.exports = Fbd;
