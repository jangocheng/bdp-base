const DateQUERY = require('./DateQUERY');
/**
 * 在环比情况下获取上期时间区间与 处理本期时间区间
 * @param {Object} ctx
 * @returns {Object} historyData  上期数据
 * @returns {Object} currDate  本期时间
 */
function isChain(ctx) {
  try {
    let currDate = {};
    let lastDate = {};
    if (ctx.query.isChain === 'true') {
      // 1、根据时间粒度获取 环比对比的时间周期
      let { startDate, endDate, time } = ctx.query;
      // 获取到上一期的开始时间与本期的时间区间
      ({ lastDate, currDate } = DateQUERY.getChainTime({
        startDate,
        endDate,
        time,
      }));
      // ({ startDate, endDate } = lastDate);
    } else {
      currDate.startDate = ctx.query.startDate;
      currDate.endDate = ctx.query.endDate;
    }
    return {
      lastDate,
      currDate,
    };
  } catch (error) {
    console.error(error);
  }
}
exports.isChain = isChain;
