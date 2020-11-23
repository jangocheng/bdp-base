/*
 *
 * 日期查询函数
 *
 */
const moment = require('moment');
const DateJs = require('./DateJs');
class DateQUERY {
  /**
   * 根据时间粒度条件来过滤
   * @param {Array} arr  目标数组
   * @param {String} time 时间粒度
   */
  static timeFilter(arr, time) {
    if (arr.length === 0) return;
    // let temArr = [];
    let res; //返回的数组
    try {
      // time = 'week';
      //判断参数是否是数组
      if (Array.isArray(arr)) {
        // let curDate = arr[0].date;
        switch (time) {
          /**
           * 按日：按日历天统计，不足一日的数据不统计、不展示；
           */
          case 'day':
            // const currentTime = DateJs.DateFormatterYMD(curDate);
            // res = timeOriented(currentTime, arr, 'date');
            res = times(arr);
            // return res;
            return res;
          /**
           * 按周：按日历周统计，不足一周的数据不统计、不展示；
           * 因为java服务只能统计到昨日的数据，所以直接取enddate的上周末时间即可
           */
          case 'week':
            // endDate = DateJs.DateFormatterYMD(endDate);
            // endDate = DateJs.weekDate(endDate)[0];
            // // 获取到上周末的日期
            // const dateOfToday = DateJs.GetDateStr({
            //   AddDayCount: -1,
            //   date: endDate,
            // });
            // 上周所有的历史数据
            // res = timeOriented(dateOfToday, arr, 'date');
            // TODO:可以优化成链式结构
            res = times(arr);
            res = getDateData(res, time);
            return res;
          /**
           * TODO:BUG
           * 按月：按日历月统计，不足一月的数据不统计、不展示；
           */
          case 'month':
            // 获取当月的第一天
            // const month_start = DateJs.monthDate(
            //   DateJs.DateFormatterYMD(curDate),
            // );
            // res = timeOriented(month_start[0], arr, 'date');
            res = times(arr);
            res = getDateData(res, time);
            return res;
          default:
            break;
        }
      }
    } catch (error) {
      console.log(error);
      return [];
    }
  }

  /**
   * 根据颗粒度与传入时间来得出 当前有效时间区间 与 “环比”时间区间
   *
   * ps:在环比规则计算条件下若返回的end_date若小于start_date，函数是无误的，说明当前传入时间不能按对应的时间粒度来计算 上期时间区间
   * @param {String} startDate 开始时间
   * @param {String} endDate 结束时间
   * @param {String} time 时间粒度
   * @return {Object} currDate 本期查询时间对象
   * @return {Object} lastDate 上期查询时间对象
   */
  static getChainTime({ startDate, endDate, time }) {
    let lastDate = {
      startDate: '',
      endDate: '',
    };
    let currDate = {
      startDate: '',
      endDate: '',
    };
    // 根据传入时间区间去除不完善的时间
    switch (time) {
      case 'day':
        currDate.startDate = moment(startDate).format('YYYY-MM-DD');
        currDate.endDate = moment(endDate).format('YYYY-MM-DD');
        lastDate.startDate = moment(startDate)
          .subtract(1, 'days')
          .format('YYYY-MM-DD');
        lastDate.endDate = moment(endDate)
          .subtract(1, 'day')
          .format('YYYY-MM-DD');
        break;
      case 'week':
        // 获取当前开始时间在当周内是周几
        const wStartDays = moment(startDate, 'YYYY-MM-DD').format('E');
        const wEndDays = moment(endDate, 'YYYY-MM-DD').format('E');
        /**
         * 是：表示当前传入开始时间为周一，那么本期查询开始时间就为周一，上期查询开始时间为上周周一
         * 否：当时传入开始时候不为周一，那么本期查询开始时间为下周周一，上期查询开始时候为本周周一
         */
        if (wStartDays === '1') {
          currDate.startDate = moment(startDate).format('YYYY-MM-DD');
          lastDate.startDate = moment(startDate, 'YYYY-MM-DD')
            .subtract(wStartDays - 1 + 7, 'days')
            .format('YYYY-MM-DD');
        } else {
          currDate.startDate = moment(startDate, 'YYYY-MM-DD')
            .add(7 - wStartDays, 'days')
            .format('YYYY-MM-DD');
          lastDate.startDate = moment(startDate, 'YYYY-MM-DD')
            .subtract(wStartDays - 1, 'days')
            .format('YYYY-MM-DD');
        }
        /**
         * 是：当前传入结束时间为周末，本期查询开始时间为当前周末，上期查询开始时间为上周末
         * 否：当前传入结束时间不为周末，本期查询开始时间为上周周末，上去查询开始时候为上周周末
         */
        if (wEndDays === '7') {
          lastDate.endDate = moment(endDate, 'YYYY-MM-DD')
            .add(-wEndDays, 'days')
            .format('YYYY-MM-DD');
          currDate.endDate = moment(endDate, 'YYYY-MM-DD').format('YYYY-MM-DD');
        } else {
          lastDate.endDate = moment(endDate, 'YYYY-MM-DD')
            .add(7 - wEndDays, 'days')
            .subtract(7 + 7, 'days')
            .format('YYYY-MM-DD');
          currDate.endDate = moment(endDate, 'YYYY-MM-DD')
            .add(7 - wEndDays, 'days')
            .subtract(7, 'days')
            .format('YYYY-MM-DD');
        }
        break;
      case 'month':
        // 获取当前开始时间 在当月的第一天
        const mStartDate = moment(startDate, 'YYYY-MM-DD')
          .startOf('month')
          .format('YYYY-MM-DD');
        // 获取当前结束时间 在当月的最后一天
        const mEndDate = moment(endDate, 'YYYY-MM-DD')
          .endOf('month')
          .format('YYYY-MM-DD');
        //如果传入开始时间为当前月第一天，则取上个月第一天，若不是则取当前月第一天
        /**
         * 是：如果传入开始时间为当月第一天，则本期查询开始时间也为当月第一天，上期查询开始时间为上个月第一天
         * 否：如果传入开始时间不为当月第一天，则本期查询开始时间为下个月第一天，上期查询开始时间为本月第一天
         */
        if (
          mStartDate === moment(startDate, 'YYYY-MM-DD').format('YYYY-MM-DD')
        ) {
          currDate.startDate = mStartDate;
          lastDate.startDate = moment(startDate, 'YYYY-MM-DD')
            .subtract(1, 'months')
            .format('YYYY-MM-DD');
        } else {
          lastDate.startDate = mStartDate;
          currDate.startDate = moment(startDate, 'YYYY-MM-DD')
            .add(1, 'months')
            .format('YYYY-MM-DD');
        }
        // 如果传入结束时间为当前月最后一天，则取上个月最后一天，若不是则取上上个月最后一天
        /**
         * 是：如果传入结束时间为当月最后一天，则本期查询结束时间也为当月最后一天，上期查询开始时间为上个月最后一天
         * 否：如果传入结束时间不为当月最后一天，则本期查询结束时间为上个月最后一天，上期查询开始时间为上上个月最后一天
         */
        if (mEndDate === moment(endDate, 'YYYY-MM-DD').format('YYYY-MM-DD')) {
          currDate.endDate = mEndDate;
          lastDate.endDate = moment(endDate, 'YYYY-MM-DD')
            .subtract(1, 'months')
            .endOf('month')
            .format('YYYY-MM-DD');
        } else {
          currDate.endDate = moment(endDate, 'YYYY-MM-DD')
            .subtract(1, 'months')
            .endOf('month')
            .format('YYYY-MM-DD');
          lastDate.endDate = moment(endDate, 'YYYY-MM-DD')
            .subtract(2, 'months')
            .endOf('month')
            .format('YYYY-MM-DD');
        }
        break;
      default:
        break;
    }
    return {
      lastDate,
      currDate,
    };
  }
}

/**
 * TODO:此处可以优化
 * 实现数据 按指定粒度聚合
 * @param {Array} arr 传入的数组
 * @param {Array} time 时间粒度 week|month
 */
function getDateData(arr, time) {
  let tempSum = []; //临时总存储
  let temp = []; //临时存储当周数据
  let currDateArr = []; //当前周时间区间

  // 根据传入的区间来配置不同的参数调用
  let timeFoo;
  try {
    // 根据不同的时间粒度调用不同的参数
    switch (time) {
      case 'week':
        timeFoo = DateJs.weekDate;
        break;
      case 'month':
        timeFoo = DateJs.monthDate;
        break;
      default:
        break;
    }
    /**
     * 处理函数，用来组装数据 生成最终的返回结果
     * @param {Array} temp
     * @returns
     */
    const _getResData = function(temp) {
      // const tempSum = [];
      let temCurrObj = {}; //声明一个临时对象，处理后添加到总储存使用
      // 遍历生成最终对象
      // {
      //   "date": "20180910", //时间
      //   "channel": "渠道一", //通道
      //   "osType": "H5", //系统类型
      //   "registerAmount": "100", //注册量
      //   "identifiedAmount": "50", //实名人数
      //   "applyAmount": "1000", //申请总件数
      //   "applyApprovedAmount": "300", //申请通过件数
      //   "applyRefusedAmount": "600", //申请拒件
      //   "applyCancelledAmount": "80", //申请取消
      //   "applyApprovingAmount": "20" //申请审核中
      // },
      temp.forEach((item, index) => {
        if (index === 0) {
          temCurrObj = item;
        } else {
          // 叠加各类数据，以配合时间区间
          temCurrObj.registerAmount += item.registerAmount; //注册量
          temCurrObj.identifiedAmount += item.identifiedAmount; //实名人数
          temCurrObj.applyAmount += item.applyAmount; //申请总件数
          temCurrObj.applyApprovedAmount += item.applyApprovedAmount; //申请通过件数
          temCurrObj.applyRefusedAmount += item.applyRefusedAmount; //申请拒件
          temCurrObj.applyCancelledAmount += item.applyCancelledAmount; //申请取消
          temCurrObj.applyApprovingAmount += item.applyApprovingAmount; //申请审核中
        }
        // const curDate = moment(item.date).format('YYYY-MM-DD');
        temCurrObj.date = `${timeFoo(item.date)[0]}---${timeFoo(item.date)[1]}`;
        // tempSum.push(temCurrObj);
      });
      return temCurrObj;
    };
    // 处理逻辑
    arr.forEach((item, index) => {
      const currDate = item.date;
      /**
       * 判断当前时间是否在 当期时间范围内
       * index===0 则表示当前item是第一位。直接根据当前时间生成 当期时间区间，且存入此区间,
       * 接着到第二位，如果在此区间，继续存入。若不在 则表示此区间已收集完毕，将该区间数据存入要返回的数组，且重置该区间
       */
      if (index === 0) {
        currDateArr = timeFoo(currDate);
        temp.push(item);
      } else if (
        currDate >= currDateArr[0].replace('/-/g', '') &&
        currDate <= currDateArr[1].replace('/-/g', '')
      ) {
        temp.push(item);
      } else {
        // 处理上个区间的数据,生成最终item添加到
        tempSum.push(_getResData(temp));
        temp = [];
        // 重新生成时间区间
        currDateArr = timeFoo(currDate);
        temp.push(item);
      }
      // 如果是最后一个参数,直接添加就好
      if (index === arr.length - 1) {
        tempSum.push(_getResData(temp));
      }
    });
  } catch (error) {
    tempSum = [];
    console.log(error);
  }
  return tempSum;
}

// noinspection JSUnusedLocalSymbols
/**
 * 根据时间由大到小排序
 *
 * 🍹排序
 * @param {Array} arr
 * @returns arr 排序后的数组
 */
function times(arr) {
  if (Array.isArray(arr)) {
    // 获取数组长度
    let tail = arr.length - 1;
    let i;
    // let isSwap = false;
    for (i = 0; i < tail; tail--) {
      for (var j = tail; j > i; j--) {
        const arrj1 = arr[j - 1].date;
        const arrj = arr[j].date;
        //第一轮, 先将最小的数据冒泡到前面
        // arrj1 < arrj && (isSwap = true) && swap(j, j - 1, arr);
        arrj1 < arrj && swap(j, j - 1, arr);
      }
      i++;
      for (j = i; j < tail; j++) {
        const arrj1 = arr[j + 1].date;
        const arrj = arr[j].date;
        //第二轮, 将最大的数据冒泡到后面
        // arrj < arrj1 && (isSwap = true) && swap(j, j + 1, arr);
        arrj < arrj1 && swap(j, j + 1, arr);
      }
    }
    return arr;
  }
}

/**
 * 备份数据--配合排序方法使用
 * @param {Number} i
 * @param {Number} j
 * @param {*} array
 */
function swap(i, j, array) {
  var temp = array[j];
  array[j] = array[i];
  array[i] = temp;
}

/**
 * 获取当天格式化日期，yyyy-HH-mm
 */
// function now(param) {
//   return DateJs.DateFormatterYMD(param);
//   const currentTime = new Date(param);
//   const month =
//     currentTime.getMonth() >= 10
//       ? currentTime.getMonth() + 1
//       : `0${currentTime.getMonth() + 1}`;
//   const year = currentTime.getFullYear();
//   const day =
//     currentTime.getDate() >= 10
//       ? currentTime.getDate()
//       : `0${currentTime.getDate()}`;
//   return `${year}-${month}-${day}`;
// }

// noinspection JSUnusedLocalSymbols
/**
 * 根据传入的目标日期来过滤掉后面不需要的数据
 * @param {String} date 传入的目标日期，格式为2019-01-01
 * @param {Array} arr 需要过滤的数组
 * @param {String} key item的日期属性
 */
function timeOriented(date, arr, key) {
  let temArr = [];
  date = date.replace(/-/g, '');
  try {
    // 遍历数据
    arr.forEach(item => {
      // const temItemDate = formatter(item[key]);
      // const temTarget = formatter(date);
      if (item[key] <= date) {
        temArr.push(item);
      }
    });
    return temArr;
  } catch (error) {
    console.log(error);
  }
}

module.exports = DateQUERY;
