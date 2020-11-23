/*
 *
 * 自定义时间工具类
 *
 */

const moment = require('moment');

class DateJs {
  /**
   * 时间戳格式化
   * @param dateStr 时间戳
   * @param format 格式，默认yyyy-mm-dd
   * @returns {string}
   * @constructor
   */
  static DateFormatterYMD(dateStr, format) {
    if (!format) format = 'YYYY-MM-DD';
    return moment(dateStr).format(format);
  }

  static now() {
    return moment()
      .locale('zh-cn')
      .format('YYYY-MM-DD');
  }

  /**
   *
   *
   * 将指定字符删除并格式化成数字
   * @param {String} param 格式为2019-01-01
   * @param {String} str   要删除的指定字符，默认为'-'
   */
  static formatter(param, str) {
    try {
      let temp = param;
      str = str || '-';
      const reg = new RegExp(str, 'g');
      return temp.replace(reg, '') * 1;
    } catch (error) {
      console.log('formatter', error);
    }
  }

  /**
   * 获取当前时候前后天数
   * @param AddDayCount 指定天数 例:往前推1天，即传入-1
   * @param date
   * @returns {string}
   * @constructor
   */
  static GetDateStr({ AddDayCount, date }) {
    const dd = date ? new Date(date) : new Date();
    dd.setDate(dd.getDate() + AddDayCount); //获取AddDayCount天后的日期
    let y = dd.getFullYear();
    let m =
      dd.getMonth() + 1 < 10 ? '0' + (dd.getMonth() + 1) : dd.getMonth() + 1; //获取当前月份的日期，不足10补0
    let d = dd.getDate() < 10 ? '0' + dd.getDate() : dd.getDate(); //获取当前几号，不足10补0
    return y + '-' + m + '-' + d;
  }

  /**
   * 获取当前日期的周一与周日
   * @param {String} now  传入的日期yyyy-MM-dd
   * @returns [周一，周日]
   */
  static weekDate(now) {
    const weekOfday = moment(now, 'YYYY-MM-DD').format('E');
    const lastStart = moment(now)
      .subtract(weekOfday - 1, 'days')
      .format('YYYY-MM-DD'); //周一日期
    const lastEnd = moment(now)
      .add(7 - weekOfday, 'days')
      .format('YYYY-MM-DD'); //周日日期
    return [lastStart, lastEnd];
  }

  /**
   * 获取当前时间的月一与月末日期
   * @param {String} now 传入日期yyyy--MM-dd
   */
  static monthDate(now) {
    now = DateJs.DateFormatterYMD(now);
    // 分割传入的日期
    let temArr = now.split('-');
    let day = new Date(temArr[0], temArr[1] * 1, 0);
    // 获取第一天
    let lastStart = `${temArr[0]}-${temArr[1]}-01`;
    // 获取最后一天
    let lastEnd = `${temArr[0]}-${temArr[1]}-${day.getDate()}`;
    return [lastStart, lastEnd];
  }
}

module.exports = DateJs;
