// const _ = require('lodash');
const mathjs = require('mathjs');
const moment = require('moment');
const DateQUERY = require('../../../utils/DateQUERY');
const ChanelQUERY = require('../ChanelQUERY');
const { isChain } = require('../../../utils/isChain');
// const { customerENUM, checkTypeENUM } = require('../../enmu.js');
// const OSTypeQUERY = require('../OSTypeQUERY');
// const MergeByTime = require('../../../utils/MergeByTime.js');
const {
  getPDTimeData,
  getChannel,
  getPDTimeSummary,
} = require('../../../api/pdTime');

const { division } = require('../../../utils/math');

class PDTime {
  /**
   * 查询渠道申请报表数据
   * @param {Object} ctx
   * @param {Function} next
   */
  static async queryPDTimeTable(ctx, next) {
    try {
      await next();
      /**
       * 处理java层来的源数据
       */
      let resArr = ctx.body;
      if (!resArr || resArr.length === 0) {
        return (ctx.body = {
          code: 400,
          msg: '暂无数据',
          data: [],
        });
      }
      const { channel, time } = ctx.query;
      let isTable;
      // 渠道为所有
      isTable = ctx.query.channel === '所有';
      if (resArr.length > 0) {
        resArr = ChanelQUERY.channelFilter(resArr, channel, time, isTable);
      }
      if (resArr.length > 0) resArr = DateQUERY.timeFilter(resArr, time);

      // 4、根据页数过滤
      const { pageSize, pageNum } = ctx.query;
      let resData = []; //声明传出的参数
      // //根据当前页数截取指定条数
      resArr.forEach((item, index) => {
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
          total: resArr.length,
          list: resData,
        },
      };
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  static async queryPDTimeApprovedAmount(ctx, next) {
    try {
      // 根据是否 环比 来查询上期与本期时间
      let { lastDate, currDate } = isChain(ctx);
      let historyData;
      // 2、根据 历史时间获取历史数据(与当前时间查询出来的数据流程一致)
      // 获取历史数据;
      if (ctx.query.isChain === 'true') {
        // 判断环比状态下  是否能满足时间粒度查询
        if (!moment(lastDate.startDate).isBefore(lastDate.endDate)) {
          ctx.body = {
            code: 400,
            msg: '请求时间范围与颗粒度不匹配',
            data: [],
          };
          return ctx;
        }
        const { time, channel, osType } = ctx.query;
        const { startDate, endDate } = lastDate;
        historyData = await getApplyData({
          startDate,
          endDate,
          time,
          channel,
          osType,
        });
      }
      ctx.query.startDate = currDate.startDate;
      ctx.query.endDate = currDate.endDate;
      await next();
      /**
       * 处理java层来的源数据
       */
      let temArr = ctx.body;
      if (!temArr || temArr.length === 0) {
        return (ctx.body = {
          code: 400,
          msg: '暂无数据',
          data: [],
        });
      }
      {
        let isTable;
        const { channel, time } = ctx.query;
        // 渠道为所有
        isTable = channel === '所有';
        temArr = ChanelQUERY.channelFilter(temArr, channel, time, isTable);
        // 系统终端为所有
        // isTable = osType === '05';
        // temArr = OSTypeQUERY.osTypeFilter(temArr, osType, time, isTable);
        temArr = DateQUERY.timeFilter(temArr, time);
        // 根据 条件来处理聚合数据
        temArr = MergeByTime(temArr);
      }

      //判断是否是求环比数据
      switch (ctx.query.isChain) {
        case 'true':
          /**
           *  环比情况下，根据 本期源数据 与 上期源数据 加工得到最终数据
           * （本期数-上期数）/上期数×100%
           */
          temArr = temArr.map((ele, index) => {
            const { date } = ele;
            const eleHis = historyData[index];
            const applyAmount = chainMath(ele, eleHis, 'applyAmount'); //环比注册量
            const applyApprovedAmount = chainMath(
              ele,
              eleHis,
              'applyApprovedAmount',
            );
            ele.applyApproved = mathjs
              .eval(`${ele.applyApprovedAmount}/${ele.applyAmount}*100`)
              .toFixed(2);
            eleHis.applyApproved = mathjs
              .eval(`${eleHis.applyApprovedAmount}/${eleHis.applyAmount}*100`)
              .toFixed(2);
            const applyApprovingAmount = chainMath(
              ele,
              eleHis,
              'applyApprovingAmount',
            );
            const approvedCreditSum = chainMath(
              ele,
              eleHis,
              'approvedCreditSum',
            ); //环比 实名认证率
            return {
              date,
              applyAmount,
              applyApprovedAmount,
              applyApproved,
              applyApprovingAmount,
              approvedCreditSum,
            };
          });
          break;
        case 'false':
          temArr = temArr.map(ele => {
            // applyApprovedAmount: [],      //通过人数
            // identifiedAmount: [],    //转化率
            const { date, applyApprovedAmount, applyApprovedRate } = ele;
            return {
              date,
              applyApprovedAmount,
              applyApprovedRate,
            };
          });
          break;
        default:
          ctx.body = {
            success: false,
            code: 200,
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
    } catch (err) {
      console.log(err);
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 向java层请求“渠道数据”
   * @param {Object} ctx
   */
  static async queryChanel(ctx) {
    try {
      // 获取渠道数据
      let res = await getChannel();
      if (res.data && res.data.length > 0) {
        const resArr = ['所有', ...res.data];
        ctx.body = {
          success: true,
          code: 200,
          msg: 'request successful',
          data: resArr,
        };
      } else {
        ctx.body = {
          success: true,
          code: 400,
          msg: '暂无数据',
          data: [],
        };
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 推广渠道(时间)--报表数据
   * @param {*} ctx
   */
  static async reqBusRegist(ctx) {
    try {
      // 判断时间先后顺序
      if (moment(ctx.query.startDate).isBefore(ctx.query.endDate)) {
        /*=============1、此处向java层请求源数据========================== */
        let { time, startDate, endDate } = ctx.query;
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        time = time === 'hour' ? 'hour' : 'day';
        let res = await getPDTimeData(time, startDate, endDate);
        /**
         * 加工一下数据，计算其他字段
         * {
         *  "date": "Duis",                                 //时间
         *  "channel": "aliqua mollit qui tempor",          //渠道名称
         *  "osType": "fugiat irure",                       //终端
         *  "registerAmount": "nostrud voluptate",          //注册人数
         *  "identifiedAmount": "deserunt in ut",           //实名认证数
         *  "registerIdentifiedRate": -48027068.40299909,   //实名认证率=identifiedAmount/registerAmount
         *  "applyAmount": -87024320.3063875,               //申请进件数
         *  "applyApprovedAmount": -46923380.58079062,      //申请进件通过数
         *  "applyApprovedRate": "consectetur et",          //申请通过率=applyApprovedAmount/applyAmount
         *  "registerToApplyRate": -17023657.4012924,       //注册转化率=applyApprovedAmount/registerAmount
         *  "machinePassAmount": -17023657.4012924,         //机审通过数
         *  "machinePassRate": -17023657.4012924,           //机审通过率=machinePassAmount/applyAmount
         *  "applyRefusedAmount":''                          //拒绝件数
         *  "applyRefuseRate" : "",                         //申请拒绝率=applyRefusedAmount/applyAmount
         *  "machineRefuseAmount": "nisi Ut",               //机审拒绝数
         *  "machineRefuseRate": "sunt ullamco",            //机审拒绝率=machineRefuseAmount/applyAmount
         *  "withdrawAmount": "cupidatat qui ipsum ullamco" //提现成功人数
         *  "withdrawPassRage": "adipisicing minim ut quis" //提现成功率=approvedWithdrawSum/approvedCreditSum
         *  "repaymentWithdrawAmount": ""                   //复贷提现用户数
         *  "repaymentWithdrawRate" : "",                   //复贷提现用户数占比=repaymentWithdrawAmount/applyApprovedTotalAmount
         *  "withdrawSum": ""                               //当天提现金额
         *  "approvedWithdrawMean": ""                      //额度提现均值= withdrawSum/withdrawAmount
         *  "totalWithdrawAmount": ""                       //累积提现次数 totalWithdrawTimes
         *  "totalWithdrawSum": ""                          //累积提现金额
         *  "firstOverdueRate": ""                          //首逾率=firstOverdueAmount/withdrawAmount
         *  "m1OverdueRate": ""                          //M1逾期率=m1OverdueAmount/withdrawAmount
         *  "m2OverdueRate": ""                          //M2逾期率=m2OverdueAmount/withdrawAmount
         *  "m3OverdueRate": ""                          //M3逾期率=m3OverdueAmount/withdrawAmount
         *  "badLogRate": ""                             //坏账率=badLogAmount/withdrawAmount
         * }
         */
        const _division = ({ numerator, denominator }) => {
          try {
            if (denominator === 0) return '0.00%';
            return (
              mathjs.eval(`${numerator}/${denominator}*100`).toFixed(2) + '%'
            );
          } catch (error) {
            return '0.00%';
          }
        };
        if (res && res.data && res.data.length > 0) {
          ctx.body = res.data.map(ele => {
            // 实名认证率
            const registerIdentifiedRate = _division({
              numerator: ele.identifiedAmount,
              denominator: ele.registerAmount,
            });
            // 申请通过率
            const applyApprovedRate = _division({
              numerator: ele.applyApprovedAmount,
              denominator: ele.applyAmount,
            });
            // 注册转化率
            const registerToApplyRate = _division({
              numerator: ele.applyApprovedAmount,
              denominator: ele.applyAmount,
            });
            // 机审通过率
            const machinePassRate = _division({
              numerator: ele.machinePassAmount,
              denominator: ele.applyAmount,
            });
            // 申请拒绝率
            const applyRefuseRate = _division({
              numerator: ele.applyRefusedAmount,
              denominator: ele.applyAmount,
            });
            // 机审拒绝率
            const machineRefuseRate = _division({
              numerator: ele.machineRefuseAmount,
              denominator: ele.applyAmount,
            });
            // 提现成功率
            const withdrawPassRage = _division({
              numerator: ele.approvedWithdrawSum,
              denominator: ele.approvedCreditSum,
            });
            // 复贷提现用户数占比
            const repaymentWithdrawRate = _division({
              numerator: ele.repaymentWithdrawAmount,
              denominator: ele.applyApprovedTotalAmount,
            });
            // 额度提现均值(去除比例，此处是求平均数)
            const approvedWithdrawMean =
              ele.withdrawAmount === 0
                ? 0
                : mathjs
                    .eval(`${ele.withdrawSum}/${ele.withdrawAmount}`)
                    .toFixed(2);
            // 首逾率
            const firstOverdueRate = _division({
              numerator: ele.firstOverdueAmount,
              denominator: ele.withdrawAmount,
            });
            // M1逾期率
            const m1OverdueRate = _division({
              numerator: ele.m1OverdueAmount,
              denominator: ele.withdrawAmount,
            });
            // M2逾期率
            const m2OverdueRate = _division({
              numerator: ele.m2OverdueAmount,
              denominator: ele.withdrawAmount,
            });
            // M3逾期率
            const m3OverdueRate = _division({
              numerator: ele.m3OverdueAmount,
              denominator: ele.withdrawAmount,
            });
            // 坏账率
            const badLogRate = _division({
              numerator: ele.badLogAmount,
              denominator: ele.withdrawAmount,
            });
            return {
              ...ele,
              registerIdentifiedRate,
              applyApprovedRate,
              registerToApplyRate,
              applyRefuseRate,
              machineRefuseRate,
              withdrawPassRage,
              repaymentWithdrawRate,
              approvedWithdrawMean,
              firstOverdueRate,
              m1OverdueRate,
              m2OverdueRate,
              m3OverdueRate,
              badLogRate,
              machinePassRate,
            };
          });
        }
      } else {
        return (ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        });
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 请求汇总数据
   * @param ctx
   * @returns {Promise<void>}
   */
  static async queryPDTimeSummary(ctx) {
    try {
      let { startDate, endDate, channel, summaryField } = ctx.query;
      channel = channel === '所有' ? '' : channel;
      let res = await getPDTimeSummary({
        startDate,
        endDate,
        channel,
        summaryField,
      });
      if (res && res.data) {
        let temRes = res.data;
        switch (summaryField) {
          case 'mobileModel':
            temRes = mobileModel(temRes);
            break;
          case 'gender':
            temRes = userGender(temRes);
            break;
          // 用户年龄分布
          case 'age':
            temRes = userAgent(temRes);
            break;
          // 下列逻辑一样
          case 'province':
            temRes = userProvince(temRes);
            break;
          case 'customerLevel':
            temRes = userEducation(temRes);
            break;
          case 'loanPeriod':
            temRes = userEducation(temRes);
            break;
          case 'educationalBackground':
            temRes = userEducation(temRes);
            break;
        }
        ctx.body = {
          success: true,
          code: 200,
          msg: 'request successful',
          data: temRes,
        };
      } else {
        ctx.body = {
          success: true,
          code: 400,
          msg: '暂无数据',
          data: [],
        };
      }
    } catch (e) {
      console.log(e);
      ctx.app.emit('error', ctx, e);
    }
  }
}
/**
 * 用户手机机型Top10逻辑
 * @param obj
 * @returns {{arr: T[], xDate: *[]}}
 */
function mobileModel(obj) {
  let temArr = [];
  for (const key in obj) {
    if (obj.hasOwnProperty(key)) {
      temArr.push({
        type: key,
        amount: 1 * obj[key],
      });
    }
  }
  temArr = temArr
    .sort((a, b) => {
      return b.amount - a.amount;
    })
    .slice(0, 10);
  return {
    xDate: temArr.map(ele => ele.type),
    arr: temArr,
  };
}

/**
 * 用户年龄分布
 * @param obj
 * @returns {{arr: T[], xDate: *[]}}
 */
function userAgent(obj) {
  try {
    let temArr = [];
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        temArr.push({
          type: key,
          amount: 1 * obj[key],
        });
      }
    }
    // 按年龄段分组
    const xDate = ['0-18', '18-25', '25-35', '35-45', '45-55', '55>'];
    let xDate1 = 0,
      xDate2 = 0,
      xDate3 = 0,
      xDate4 = 0,
      xDate5 = 0,
      xDate6 = 0;
    temArr.forEach(ele => {
      if (18 > ele.type && ele.type >= 0) {
        xDate1 += ele.amount;
      } else if (25 > ele.type && ele.type >= 18) {
        xDate2 += ele.amount;
      } else if (35 > ele.type && ele.type >= 25) {
        xDate3 += ele.amount;
      } else if (45 > ele.type && ele.type >= 35) {
        xDate4 += ele.amount;
      } else if (55 > ele.type && ele.type >= 45) {
        xDate5 += ele.amount;
      } else if (ele.type >= 55) {
        xDate6 += ele.amount;
      }
    });
    return {
      xDate,
      arr: [xDate1, xDate2, xDate3, xDate4, xDate5, xDate6],
    };
  } catch (e) {
    console.log(e);
  }
}

/**
 * 用户年龄分布
 * @param obj
 * @returns {{arr: T[], xDate: *[]}}
 */
function userEducation(obj) {
  try {
    let temArr = [];
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        temArr.push({
          type: key,
          amount: 1 * obj[key],
        });
      }
    }
    return {
      xDate: temArr.map(ele => ele.type),
      arr: temArr,
    };
  } catch (e) {
    console.log(e);
  }
}

/**
 * 用户地域分布图
 * @param obj
 * @returns {{arr: Array, xDate: *[]}}
 */
function userProvince(obj) {
  try {
    let temArr = [];
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        temArr.push({
          type: key,
          amount: 1 * obj[key],
        });
      }
    }
    // TODO:去掉省、市、自治区字样，方便前端做匹配
    temArr.forEach(ele => {
      ele.type = ele.type.replace(/省|市|自治区/g, '');
    });
    return {
      xDate: temArr.map(ele => ele.type),
      arr: temArr,
    };
  } catch (e) {
    console.log(e);
  }
}

function userGender(obj) {
  try {
    let temArr = [];
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        temArr.push({
          consumptionType: key,
          consumptionSum: 1 * obj[key],
        });
      }
    }
    return temArr;
  } catch (e) {
    console.log(e);
  }
}

/**
 * 当渠道跟终端是选择“所有”的情况下，聚合相同时间的数据
 * @param {*} arr
 */
function MergeByTime(arr) {
  if (arr.length === 0) return;
  try {
    let temObj = {};
    arr.forEach(item => {
      if (temObj[item.date]) {
        temObj[item.date].push(item);
      } else {
        temObj[item.date] = [item];
      }
    });
    let temArr = [];
    for (const key in temObj) {
      if (temObj.hasOwnProperty(key)) {
        const ele = temObj[key];
        // 应求出每天的  申请通过数 applyApprovedAmount/ 申请进件数applyAmount 之和
        const applyApprovedAmount = ele.reduce((prev, current) => {
          return ~~current.applyApprovedAmount + ~~prev;
        }, 0);
        const applyAmount = ele.reduce((prev, current) => {
          return ~~current.applyAmount + ~~prev;
        }, 0);

        // 当前的申请通过率
        const applyApprovedRate = division({
          numerator: applyApprovedAmount,
          denominator: applyAmount,
        }).replace(/%/, '');
        temArr.push({
          date: key,
          applyApprovedAmount,
          applyApprovedRate,
        });
      }
    }
    return temArr;
  } catch (error) {
    console.error('error', error);
  }
}

module.exports = PDTime;
