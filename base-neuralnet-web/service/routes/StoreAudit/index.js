/*

 * @Description: 门店审核
 */

const moment = require('moment');
const { getStoreReport } = require('../../api/storeAudit');
const DateQUERY = require('../../utils/DateQUERY');

class StoreAudit {
  // 渠道申请报表
  static async queryApplyTable(ctx, next) {
    try {
      await next();
      let temArr = ctx.body;
      if (temArr.length === 0) {
        ctx.body = {
          code: 400,
          msg: '暂无数据',
          data: [],
        };
        return;
      }
      let { time, pageSize, pageNum } = ctx.query;
      temArr = DateQUERY.timeFilter(temArr, time);
      pageSize = pageSize || 10; //默认10条一爷
      pageNum = pageNum || 1; //当前页数，默认第一页
      let resData = []; //声明传出的参数
      // //根据当前页数截取指定条数
      temArr.forEach((item, index) => {
        // 假如第二页，总数23条，就是11-20条
        if ((pageNum - 1) * pageSize <= index && pageNum * pageSize > index) {
          resData.push(item);
        }
      });
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: {
          total: temArr.length,
          list: resData,
        },
      };
    } catch (e) {
      ctx.app.emit('error', ctx, e);
    }
  }

  // 注册量趋势图
  static async queryRegisterData(ctx, next) {
    try {
      // // 根据是否 环比 来查询上期与本期时间
      // let { lastDate, currDate } = isChain(ctx);
      // let historyData;
      // // 2、根据 历史时间获取历史数据(与当前时间查询出来的数据流程一致)
      // // 获取历史数据;
      // if (ctx.query.isChain === 'true') {
      //   // 判断环比状态下  是否能满足时间粒度查询
      //   if (!moment(lastDate.startDate)
      //     .isBefore(lastDate.endDate)) {
      //     ctx.body = {
      //       code: 400,
      //       msg: '请求时间范围与颗粒度不匹配',
      //       data: [],
      //     };
      //     return ctx;
      //   }
      //   const { time } = ctx.query;
      //   const { startDate, endDate } = lastDate;
      //   historyData = await getChainDatObject({
      //     startDate,
      //     endDate,
      //     time,
      //   });
      // }
      // ctx.query.startDate = currDate.startDate;
      // ctx.query.endDate = currDate.endDate;
      await next();

      /**
       * 处理java层来的源数据
       */
      let temArr = ctx.body;
      if (temArr.length === 0) {
        ctx.body = {
          code: 400,
          msg: '暂无数据',
          data: [],
        };
        return;
      }
      {
        const { time } = ctx.query;
        temArr = DateQUERY.timeFilter(temArr, time);
      }

      //判断是否是求环比数据
      switch (ctx.query.isChain) {
        case 'true':
          /**
           *  环比情况下，根据 本期源数据 与 上期源数据 加工得到最终数据
           * （本期数-上期数）/上期数×100%
           */
          temArr = temArr.map((ele, index) => {
            const eleHis = historyData[index];
            const registerAmount = chainMath(ele, eleHis, 'registerAmount'); //环比注册量
            const identifiedAmount = chainMath(ele, eleHis, 'identifiedAmount'); //环比实名认证数
            // 环比申请件数
            const applyApprovedAmount = chainMath(
              ele,
              eleHis,
              'applyApprovedAmount',
            );
            // 本期的实名认证率
            ele.identifiedPer = mathjs
              .eval(`${ele.identifiedAmount}/${ele.registerAmount}*100`)
              .toFixed(2);
            // 上期的实名认证率
            eleHis.identifiedPer = mathjs
              .eval(`${eleHis.identifiedAmount}/${eleHis.registerAmount}*100`)
              .toFixed(2);
            const identifiedPer = chainMath(ele, eleHis, 'identifiedPer'); //环比 实名认证率

            // 本期的申请通过率
            ele.applyApproved =
              ele.applyAmount === 0
                ? 0
                : mathjs
                    .eval(`${ele.applyApprovedAmount}/${ele.applyAmount}*100`)
                    .toFixed(2);
            // 上期的申请通过率
            eleHis.applyApproved = mathjs
              .eval(`${eleHis.applyApprovedAmount}/${eleHis.applyAmount}*100`)
              .toFixed(2);
            const applyApproved = chainMath(ele, eleHis, 'applyApproved'); //环比 实名认证率
            return {
              date: ele.date,
              registerAmount,
              identifiedAmount,
              identifiedPer,
              applyApprovedAmount,
              applyApproved,
            };
          });
          break;
        case 'false':
          temArr = temArr.map(ele => {
            const {
              date,
              applyAmount,
              accessFirstTrialAmount,
              accessApplyAmount,
              approvalRefuseAmount,
              applyApprovedAmount,
            } = ele;
            return {
              date,
              applyAmount,
              accessFirstTrialAmount,
              accessApplyAmount,
              approvalRefuseAmount,
              applyApprovedAmount,
            };
          });
          break;
        default:
          ctx.body = {
            success: false,
            code: 400,
            msg: '请求参数有误',
            data: temArr,
          };
          break;
      }
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: temArr,
      };
    } catch (e) {
      // TODO:参数有误
      ctx.app.emit('error', ctx, e);
    }
    return ctx;
  }

  // 通过与激活门店趋势
  static async queryActiveStoreData(ctx, next) {
    try {
      // // 根据是否 环比 来查询上期与本期时间
      // let { lastDate, currDate } = isChain(ctx);
      // let historyData;
      // // 2、根据 历史时间获取历史数据(与当前时间查询出来的数据流程一致)
      // // 获取历史数据;
      // if (ctx.query.isChain === 'true') {
      //   // 判断环比状态下  是否能满足时间粒度查询
      //   if (!moment(lastDate.startDate)
      //     .isBefore(lastDate.endDate)) {
      //     ctx.body = {
      //       code: 400,
      //       msg: '请求时间范围与颗粒度不匹配',
      //       data: [],
      //     };
      //     return ctx;
      //   }
      //   const { time } = ctx.query;
      //   const { startDate, endDate } = lastDate;
      //   historyData = await getChainDatObject({
      //     startDate,
      //     endDate,
      //     time,
      //   });
      // }
      // ctx.query.startDate = currDate.startDate;
      // ctx.query.endDate = currDate.endDate;
      await next();

      /**
       * 处理java层来的源数据
       */
      let temArr = ctx.body;
      if (temArr.length === 0) {
        ctx.body = {
          code: 400,
          msg: '暂无数据',
          data: [],
        };
        return;
      }
      {
        const { time } = ctx.query;
        temArr = DateQUERY.timeFilter(temArr, time);
      }

      //判断是否是求环比数据
      switch (ctx.query.isChain) {
        case 'true':
          /**
           *  环比情况下，根据 本期源数据 与 上期源数据 加工得到最终数据
           * （本期数-上期数）/上期数×100%
           */
          temArr = temArr.map((ele, index) => {
            const eleHis = historyData[index];
            const registerAmount = chainMath(ele, eleHis, 'registerAmount'); //环比注册量
            const identifiedAmount = chainMath(ele, eleHis, 'identifiedAmount'); //环比实名认证数
            // 环比申请件数
            const applyApprovedAmount = chainMath(
              ele,
              eleHis,
              'applyApprovedAmount',
            );
            // 本期的实名认证率
            ele.identifiedPer = mathjs
              .eval(`${ele.identifiedAmount}/${ele.registerAmount}*100`)
              .toFixed(2);
            // 上期的实名认证率
            eleHis.identifiedPer = mathjs
              .eval(`${eleHis.identifiedAmount}/${eleHis.registerAmount}*100`)
              .toFixed(2);
            const identifiedPer = chainMath(ele, eleHis, 'identifiedPer'); //环比 实名认证率

            // 本期的申请通过率
            ele.applyApproved =
              ele.applyAmount === 0
                ? 0
                : mathjs
                    .eval(`${ele.applyApprovedAmount}/${ele.applyAmount}*100`)
                    .toFixed(2);
            // 上期的申请通过率
            eleHis.applyApproved = mathjs
              .eval(`${eleHis.applyApprovedAmount}/${eleHis.applyAmount}*100`)
              .toFixed(2);
            const applyApproved = chainMath(ele, eleHis, 'applyApproved'); //环比 实名认证率
            return {
              date: ele.date,
              registerAmount,
              identifiedAmount,
              identifiedPer,
              applyApprovedAmount,
              applyApproved,
            };
          });
          break;
        case 'false':
          temArr = temArr.map(ele => {
            const {
              date,
              newAmount,
              firstTrialAmount,
              accessApplyAmount,
              approvalRefuseAmount,
              closeAmount,
              activationAmount,
            } = ele;
            return {
              date,
              newAmount,
              firstTrialAmount,
              accessApplyAmount,
              approvalRefuseAmount,
              closeAmount,
              activationAmount,
            };
          });
          break;
        default:
          ctx.body = {
            success: false,
            code: 400,
            msg: '请求参数有误',
            data: temArr,
          };
          break;
      }
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: temArr,
      };
    } catch (e) {
      // TODO:参数有误
      ctx.app.emit('error', ctx, e);
    }
    return ctx;
  }

  // 请求元数据
  static async reqAppltTable(ctx) {
    try {
      // 判断时间先后顺序
      if (moment(ctx.query.startDate).isBefore(ctx.query.endDate)) {
        let { time, startDate, endDate } = ctx.query;
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        time = time === 'hour' ? 'hour' : 'day';
        let res = await getStoreReport(time, startDate, endDate);
        if (res && res.data && res.data.length > 0) {
          ctx.body = res.data;
        } else {
          ctx.body = [];
        }
      } else {
        ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        };
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }
}

module.exports = StoreAudit;
