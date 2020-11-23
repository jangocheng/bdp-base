const {
  getFbdconditionQuery,
  getFbdApplyConditionQuery,
  getFbdApplyRegisterQuery,
  getFbdApplyAmtQuery,
} = require('../../api/zfb');
const { pagination } = require('../../utils/pagination');
const DateJs = require('./../../utils/DateJs');
const { zfbTableFormatter } = require('./zfbTableFormatter');

class Fbd {
  /**
   * 请求“渠道数据”
   * @param {Object} ctx
   */
  static async queryChanel(ctx) {
    try {
      // 获取条件字典
      const res = await getFbdconditionQuery();
      const {
        date_type,
        source_type,
        target_type,
        pay_type,
        popu_channel,
        quota_status,
        sjt_type,
        pay_ways,
        approve_status,
      } = res.data;
      const tem = [
        {
          value: '',
          label: '所有',
        },
      ];
      const tem_date_type = [];
      const tem_source_type = JSON.parse(JSON.stringify(tem)); //终端
      const tem_target_type = JSON.parse(JSON.stringify(tem)); //渠道
      const tem_pay_type = JSON.parse(JSON.stringify(tem)); //支付流水--支付类型
      const tem_popu_channel = JSON.parse(JSON.stringify(tem)); //推广渠道
      //额度状态
      const tem_quota_status = JSON.parse(
        JSON.stringify([
          {
            value: '',
            label: '已激活',
          },
        ]),
      );
      const tem_sjt_type = JSON.parse(JSON.stringify(tem)); //次部门
      const tem_pay_ways = JSON.parse(JSON.stringify(tem)); //商城支付--支付类型
      //审批状态
      const tem_approve_status = JSON.parse(
        JSON.stringify([
          {
            value: '',
            label: '审批通过',
          },
        ]),
      );
      for (const key in pay_ways) {
        if (pay_ways.hasOwnProperty(key)) {
          tem_pay_ways.push({
            value: pay_ways[key],
            label: key,
          });
        }
      }
      for (const key in date_type) {
        if (date_type.hasOwnProperty(key)) {
          tem_date_type.push({
            value: date_type[key],
            label: key,
          });
        }
      }
      for (const key in source_type) {
        if (source_type.hasOwnProperty(key)) {
          tem_source_type.push({
            value: source_type[key],
            label: key,
          });
        }
      }
      for (const key in target_type) {
        if (target_type.hasOwnProperty(key)) {
          tem_target_type.push({
            value: target_type[key],
            label: key,
          });
        }
      }
      for (const key in pay_type) {
        if (pay_type.hasOwnProperty(key)) {
          tem_pay_type.push({
            value: pay_type[key],
            label: key,
          });
        }
      }
      for (const key in popu_channel) {
        if (popu_channel.hasOwnProperty(key)) {
          tem_popu_channel.push({
            value: popu_channel[key],
            label: key,
          });
        }
      }
      for (const key in quota_status) {
        if (quota_status.hasOwnProperty(key)) {
          tem_quota_status.push({
            value: quota_status[key],
            label: key,
          });
        }
      }
      for (const key in sjt_type) {
        if (sjt_type.hasOwnProperty(key)) {
          tem_sjt_type.push({
            value: sjt_type[key],
            label: key,
          });
        }
      }
      for (const key in approve_status) {
        if (approve_status.hasOwnProperty(key)) {
          tem_approve_status.push({
            value: approve_status[key],
            label: key,
          });
        }
      }
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: {
          dateType: tem_date_type,
          sourceType: tem_source_type,
          targetType: tem_target_type,
          payType: tem_pay_type,
          popuChannel: tem_popu_channel,
          quotaStatus: tem_quota_status,
          sjtType: tem_sjt_type,
          payWays: tem_pay_ways,
          approveStatus: tem_approve_status,
        },
      };
      return ctx;
    } catch (error) {
      console.log(error);
    }
  }

  /**
   * 请求“申请环节数据量及比例”
   * @param {Object} ctx
   */
  static async conditionQuery(ctx) {
    try {
      let { startDate, endDate } = ctx.query;
      const { dateType, targetType, sourceType } = ctx.query;
      startDate = DateJs.DateFormatterYMD(startDate, 'YYYY/MM/DD');
      endDate = DateJs.DateFormatterYMD(endDate, 'YYYY/MM/DD');
      const res = await getFbdApplyConditionQuery({
        startDate,
        endDate,
        dateType,
        targetType,
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
   * 请求“支付宝进件注册报表”
   * @param {Object} ctx
   */
  static async registerQuery(ctx) {
    try {
      let { startDate, endDate } = ctx.query;
      const { dateType, targetType, sourceType, pageSize, pageNum } = ctx.query;
      startDate = DateJs.DateFormatterYMD(startDate, 'YYYY/MM/DD');
      endDate = DateJs.DateFormatterYMD(endDate, 'YYYY/MM/DD');
      // 获取渠道数据
      const res = await getFbdApplyRegisterQuery({
        startDate,
        endDate,
        dateType,
        targetType,
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

  /**
   * 请求“支付宝进件数量报表”
   * @param {Object} ctx
   */
  static async amtQuery(ctx) {
    try {
      let {
        startDate,
        endDate,
        dateType,
        targetType,
        sourceType,
        pageSize,
        pageNum,
      } = ctx.query;
      // 获取渠道数据
      const res = await getFbdApplyAmtQuery({
        startDate,
        endDate,
        dateType,
        targetType,
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

module.exports = Fbd;
