const DateJs = require('../utils/DateJs');

/**
 * 根据时间粒度来过滤的中间件
 */
class TimeFilter {
  static async filter(ctx, next) {
    try {
      let { startDate, endDate } = ctx.query;
      switch (ctx.query.time) {
        case 'day':
          // 如果结束日期是今天，则需要从昨天开始取
          const today = `${DateJs.formatter(DateJs.now())}`;
          if (today === endDate) {
            endDate = DateJs.GetDateStr({
              AddDayCount: -1,
              date: DateJs.DateFormatterYMD(endDate),
            });
          }
          break;
        case 'week':
          endDate = DateJs.DateFormatterYMD(endDate);
          endDate = DateJs.weekDate(endDate)[0];
          // 获取到上周末的日期
          endDate = DateJs.GetDateStr({
            AddDayCount: -1,
            date: endDate,
          });

          // 判断传入时间是否是周一，如果不是则获取下周一的时间
          if (
            DateJs.DateFormatterYMD(startDate) !== DateJs.weekDate(startDate)[0]
          ) {
            startDate = DateJs.GetDateStr({
              AddDayCount: 1,
              date: DateJs.weekDate(startDate)[1],
            });
          }
          break;
        case 'month':
          endDate = DateJs.DateFormatterYMD(endDate);
          endDate = DateJs.monthDate(endDate)[0];
          // 获取到上周末的日期
          endDate = DateJs.GetDateStr({
            AddDayCount: -1,
            date: endDate,
          });

          // 判断传入时间是否是周一，如果不是则获取下周一的时间
          if (
            DateJs.DateFormatterYMD(startDate) !==
            DateJs.monthDate(startDate)[0]
          ) {
            startDate = DateJs.GetDateStr({
              AddDayCount: 1,
              date: DateJs.monthDate(startDate)[1],
            });
          }
          break;
      }
      ctx.query.endDate = endDate;
      ctx.query.startDate = startDate;
      await next();
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }
}

module.exports = TimeFilter;
