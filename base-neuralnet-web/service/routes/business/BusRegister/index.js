/*
 * @Description: 业务大盘数据--实时注册数据
 */
const _ = require('lodash');
const mathjs = require('mathjs');
const moment = require('moment');

const { getRegisterData, getChanel } = require('../../../api/business');
const DateQUERY = require('../../../utils/DateQUERY');
const ChanelQUERY = require('../ChanelQUERY');
const OSTypeQUERY = require('../OSTypeQUERY');
const { isChain } = require('../../../utils/isChain');
const { division } = require('../../../utils/math');

class BusRegist {
  /**
   * 获取UI层传过来数据
   * @param {Object} ctx
   * @param {Function} next
   */
  static async queryBusRegist(ctx, next) {
    try {
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
        const { channel, os_type, time } = ctx.query;
        let isTable;
        // 渠道为所有
        isTable = ctx.query.channel === '所有';
        temArr = ChanelQUERY.channelFilter(temArr, channel, time, isTable);
        // 系统终端为所有
        isTable = ctx.query.os_type === '05';
        temArr = OSTypeQUERY.osTypeFilter(temArr, os_type, time, isTable);
        temArr = DateQUERY.timeFilter(temArr, time);
      }

      // 4、根据页数过滤
      const pageSize = ctx.query.page_size || 10; //默认10条一爷
      const currentPage = ctx.query.page_num || 1; //当前页数，默认第一页
      let resData = []; //声明传出的参数
      // //根据当前页数截取指定条数
      temArr.forEach((item, index) => {
        // 假如第二页，总数23条，就是11-20条
        if (
          (currentPage - 1) * pageSize <= index &&
          currentPage * pageSize > index
        ) {
          // 0不能做除数
          let pass; //申请通过率 = 申请通过数 / 申请件数
          let identifiedRate; //实名认证率 = 实名认证数 / 注册数
          pass = division({
            numerator: item.applyApprovedAmount,
            denominator: item.applyAmount,
          });
          identifiedRate = division({
            numerator: item.identifiedAmount,
            denominator: item.registerAmount,
          });
          resData.push({
            ...item,
            pass,
            identifiedRate,
          });
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
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 实时注册数据--渠道注册报表
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
        let res = await getRegisterData(time, startDate, endDate);
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

  /**
   * 渠道注册量占比 及 “环比”
   * @param {*} ctx
   * @param {*} next
   */
  static async getChanelChartData(ctx, next) {
    try {
      //根据是否"环比"来查询上期与本期时间
      let { lastDate, currDate } = isChain(ctx);
      let historyData;
      //根据 历史时间获取历史数据(与当前时间查询出来的数据流程一致)
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
        const { time, channel, os_type } = ctx.query;
        const { startDate, endDate } = lastDate;
        // 获取环比所求的历史数据
        historyData = await getChannelData({
          startDate,
          endDate,
          time,
          channel,
          os_type,
        });
        // 更改本期查询时间
        ctx.query.startDate = currDate.startDate;
        ctx.query.endDate = currDate.endDate;
      }
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
      {
        const { startDate, endDate, channel, os_type, time } = ctx.query;
        // view层未传'05'则表示当前有确定选择终端
        if (os_type !== '05') {
          // 根据时间粒度来改变date值
          temArr = new Crossfilter(
            temArr,
            channel,
            os_type,
            time,
            startDate,
            endDate,
          )
            .osTypeFilter()
            .timeFilter().val;
        } else {
          // 根据时间粒度来改变date值
          temArr = new Crossfilter(
            temArr,
            channel,
            os_type,
            time,
            startDate,
            endDate,
          ).timeFilter().val;
        }
      }

      // 获取指定时间内的渠道排名
      const resChanelData = await getChanel({
        startDate: ctx.query.startDate.replace(/-/g, ''),
        endDate: ctx.query.endDate.replace(/-/g, ''),
      });
      if (resChanelData && resChanelData.data.length > 5) {
        resChanelData.data = resChanelData.data.slice(0, 5);
      }

      // 当期数据
      let channelData = [];
      let objByChannel = [];
      //上期数据
      // noinspection JSMismatchedCollectionQueryUpdate
      let lastChannelData = [];
      let lastObjByChannel = [];
      // let temRes;
      /**
       * 判断是否是求环比数据
       */
      switch (ctx.query.isChain) {
        //环比情况下，根据 本期源数据 与 上期源数据 加工得到最终数据
        //本期数-上期数）/上期数×100%
        case 'true':
          // 拿到当期数据;
          temArr = processChannnelData(
            temArr,
            resChanelData.data,
            ctx.query.channel,
          );
          channelData = temArr.channelData;
          objByChannel = temArr.objByChannel;
          // 拿到上期数据
          temArr = processChannnelData(
            historyData,
            resChanelData.data,
            ctx.query.channel,
          );
          lastChannelData = temArr.channelData;
          lastObjByChannel = temArr.objByChannel;

          // 求环比
          for (const key in lastObjByChannel) {
            if (lastObjByChannel.hasOwnProperty(key)) {
              const laseEle = lastObjByChannel[key];
              const ele = objByChannel[key];
              objByChannel[key] = ele.map((item, index) => {
                const itemHis = laseEle[index];
                const registerApproved = chainMath(
                  item,
                  itemHis,
                  'registerApproved',
                );
                return {
                  date: item.date,
                  channel: item.channel,
                  registerApproved,
                };
              });
            }
          }
          break;
        // 非“环比”情况下
        case 'false':
          temArr = processChannnelData(
            temArr,
            resChanelData.data,
            ctx.query.channel,
          );
          channelData = temArr.channelData;
          objByChannel = temArr.objByChannel;
          break;
        default:
          ctx.body = {
            success: false,
            code: 400,
            msg: '请求参数有误',
          };
          return;
      }
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: {
          channelData,
          objByChannel,
        },
      };
    } catch (err) {
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
      const res = await getChanel();
      if (res && res.data) {
        let temArr = ['所有', ...res.data];
        ctx.body = {
          success: true,
          code: 200,
          msg: 'request successful',
          data: temArr,
        };
        return ctx;
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 获取 注册到进件各环节数量|环比
   *
   * 注册量、实名人数、申请总件数、申请通过数
   *
   * @param {Object} ctx
   * @param {Function} next
   */
  static async queryRegisterData(ctx, next) {
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
        const { time, channel, os_type } = ctx.query;
        const { startDate, endDate } = lastDate;
        historyData = await getChainDatObject({
          startDate,
          endDate,
          time,
          channel,
          os_type,
        });
      }
      ctx.query.startDate = currDate.startDate;
      ctx.query.endDate = currDate.endDate;
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
        const { channel, os_type, time, startDate, endDate } = ctx.query;
        temArr = new Crossfilter(
          temArr,
          channel,
          os_type,
          time,
          startDate,
          endDate,
        ).getVal();
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
              applyApprovedAmount,
              registerAmount,
              identifiedAmount,
            } = ele;
            const identifiedPer =
              registerAmount === 0
                ? 0
                : mathjs
                    .eval(`${identifiedAmount}/${registerAmount}*100`)
                    .toFixed(2);
            const applyApproved =
              applyAmount === 0
                ? 0
                : mathjs
                    .eval(`${applyApprovedAmount}/${applyAmount}*100`)
                    .toFixed(2);
            return {
              date,
              registerAmount,
              identifiedAmount,
              identifiedPer,
              applyAmount,
              applyApproved,
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
      ctx.app.emit('error', ctx, e);
    }
    return ctx;
  }
}

/**
 * 获取 "注册到进件各环节业务"的'环比'数据（上期时间区间查询的数据）
 * @param {String} startDate 开始时间
 * @param {String} endDate 结束时间
 * @param {String} time 时间粒度
 * @param {String} channel 通道
 * @param {String} os_type 系统类型
 */
async function getChainDatObject({
  startDate,
  endDate,
  time,
  channel,
  os_type,
}) {
  startDate = startDate.replace(/-/g, '');
  endDate = endDate.replace(/-/g, '');
  time = time === 'hour' ? 'hour' : 'day';
  let temData = await getRegisterData(time, startDate, endDate);
  let tem;
  let historyData;
  if (temData.data.length > 0) {
    historyData = new Crossfilter(
      temData.data,
      channel,
      os_type,
      time,
      startDate,
      endDate,
    ).getVal();

    tem = historyData.map(ele => {
      // 获取注册量、实名人数、申请通过数
      const { registerAmount, identifiedAmount, applyApprovedAmount } = ele;
      // 计算实名认证环比
      const identifiedPer = mathjs
        .eval(`${identifiedAmount}/${registerAmount}*100`)
        .toFixed(2);
      // 计算申请通过环比
      const applyApproved = mathjs
        .eval(`${applyApprovedAmount}/${registerAmount}*100`)
        .toFixed(2);
      return {
        ...ele,
        identifiedPer,
        applyApproved,
      };
    });
  }
  return tem;
}

/**
 * 获取 "渠道注册量占比"的'环比'数据（上期时间区间查询的数据）
 * @param {String} startDate 开始时间
 * @param {String} endDate 结束时间
 * @param {String} time 时间粒度
 * @param {String} channel 通道
 * @param {String} os_type 系统类型
 */

async function getChannelData({ startDate, endDate, time, channel, os_type }) {
  startDate = startDate.replace(/-/g, '');
  endDate = endDate.replace(/-/g, '');
  let temData = await getRegisterData(time, startDate, endDate);
  let tem;
  let historyData;
  if (temData.data.length > 0) {
    historyData = new Crossfilter(
      temData.data,
      channel,
      os_type,
      time,
      startDate,
      endDate,
    ).getVal();
    tem = historyData.map(ele => {
      // 获取注册量、实名人数、申请通过数
      const { registerAmount, identifiedAmount, applyApprovedAmount } = ele;
      // 计算实名认证环比
      const identifiedPer = mathjs
        .eval(`${identifiedAmount}/${registerAmount}*100`)
        .toFixed(2);
      // 计算申请通过环比
      const applyApproved =
        applyAmount === 0
          ? 0
          : mathjs.eval(`${applyApprovedAmount}/${applyAmount}*100`).toFixed(2);
      return {
        ...ele,
        identifiedPer,
        applyApproved,
      };
    });
  }
  return tem;
}

/**
 * 加工渠道数据
 * @param arr java层返回过来的元数据
 * @param channelArr 渠道数组
 * @param channel 视图层条件渠道
 * @returns {{channelData: string[], objByChannel}}
 */
function processChannnelData(arr, channelArr, channel) {
  try {
    let channelData; //需要返回到view层的 渠道数组
    let objByChannel = {}; //根据所选渠道将元数据进行分组

    /**
     * 1、确定展示渠道
     * 如果 查询渠道为‘所有’，则需要取排行前5的渠道默认展示
     * 否则代表当前查询条件有确定渠道
     */
    if (channel === '所有') {
      const temL = channelArr.length;
      channelData = temL > 5 ? channelArr.slice(0, 5) : channelArr;
    } else {
      channelData = [channel];
    }

    /**
     * 2、先按 channelArr 渠道来将元数据分组，然后再对时间进行聚合处理
     * 将元数据按渠道分组，然后与所需要展示的渠道进行对比过滤
     */
    arr.map(ele => {
      if (objByChannel[ele.channel]) {
        objByChannel[ele.channel].push(ele);
      } else {
        objByChannel[ele.channel] = [ele];
      }
    });

    channelData = registerByChannel(channelData, arr).channelData;
    objByChannel = registerByChannel(channelData, arr).objByChannel;

    return { channelData, objByChannel };
  } catch (error) {
    console.error('error', error);
  }
}

/**
 * 环比计算方法
 *
 * (本期-上期)/上期
 *
 * @param {Object} ele  本期对象
 * @param {Object} eleHis 上期对象
 * @param {String} key 需要计算环比的属性
 @ @returns tem
 */
function chainMath(ele, eleHis, key) {
  return mathjs
    .eval(`(${ele[key]}-${eleHis[key]})/${eleHis[key]}*100`)
    .toFixed(2);
}

/**
 * 当渠道跟终端是选择“所有”的情况下，聚合相同时间的数据
 * @param arr
 * @returns {Array}
 * @constructor
 */
function MergeByTime(arr) {
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
        const registerAmount = ele.reduce((prev, current) => {
          return ~~current.registerAmount + ~~prev;
        }, 0);
        const identifiedAmount = ele.reduce((prev, current) => {
          return ~~current.identifiedAmount + ~~prev;
        }, 0);
        const applyAmount = ele.reduce((prev, current) => {
          return ~~current.applyAmount + ~~prev;
        }, 0);
        const applyApprovedAmount = ele.reduce((prev, current) => {
          return ~~current.applyApprovedAmount + ~~prev;
        }, 0);
        temArr.push({
          date: key,
          registerAmount,
          identifiedAmount,
          applyAmount,
          applyApprovedAmount,
        });
      }
    }
    return temArr;
  } catch (error) {
    console.error('error', error);
  }
}

/**
 * 根据时间来聚合 相同时间下的注册量占比
 * @param {Object} channelData 需要展示的渠道
 * @param {Array} arr   java层返回过来的元数据
 */
function registerByChannel(channelData, arr) {
  try {
    let temReasonTimeObj = {}; //将元数据 按时间进行分割后的对象
    let resArr = [];
    /**
     * 先按时间 将对比数组分割，将一个数组按时间来分割并赋值到一个对象
     */
    arr.forEach(item => {
      if (temReasonTimeObj[item.date]) {
        temReasonTimeObj[item.date].push(item);
      } else {
        temReasonTimeObj[item.date] = [item];
      }
    });
    /**
     * 将每个时间下的数据按渠道来聚合，不用理会系统类型
     * 得出 时间-渠道-都统一的注册量
     */
    for (const timekey in temReasonTimeObj) {
      if (temReasonTimeObj.hasOwnProperty(timekey)) {
        // 1、先根据时间拆分元数据
        const eleArr = temReasonTimeObj[timekey];
        let temChannelArr = {}; //用来储存 eleArr里面所有的渠道种类
        // 2、遍历每个时间下的数组，根据渠道继续拆分格式
        // 求当前时间所有渠道的和,方便后续每项渠道求当天的注册占比
        const totalChannelSum = eleArr.reduce((prev, cur) => {
          return cur.registerAmount + prev;
        }, 0);
        eleArr.map(item => {
          if (temChannelArr[item.channel]) {
            temChannelArr[item.channel].push(item);
          } else {
            temChannelArr[item.channel] = [item];
          }
        });
        // 3、遍历，将同一渠道下的数据进行聚合
        for (const channelkey in temChannelArr) {
          if (temChannelArr.hasOwnProperty(channelkey)) {
            const temChannelItem = temChannelArr[channelkey];
            const registerAmount = temChannelItem.reduce((prev, current) => {
              return ~~current.registerAmount + ~~prev;
            }, 0);
            resArr.push({
              date: timekey,
              channel: channelkey,
              registerAmount,
              totalChannelSum,
            });
          }
        }
      }
    }

    resArr = resArr.map(item => {
      return {
        date: item.date,
        channel: item.channel,
        registerApproved:
          item.totalChannelSum === 0
            ? '0.00'
            : _.floor(
                _.divide(item.registerAmount, item.totalChannelSum) * 100,
                2,
              ),
      };
    });
    let resObj = {};
    resArr.map(item => {
      if (resObj[item.channel]) {
        resObj[item.channel].push(item);
      } else {
        resObj[item.channel] = [item];
      }
    });
    resObj = _.pick(resObj, channelData);
    return { objByChannel: resObj, channelData: Object.keys(resObj) };
  } catch (error) {
    console.error('error', error);
  }
}

/**
 * 按VIEW层传入的条件来过滤数组
 * @param {String} val 过滤的目标数组
 * @param {String} channel 渠道
 * @param {String} os_type 终端系统
 * @param {String} time 时间粒度
 * @param {String} startDate 开始时间
 * @param {String} endDate 结束时间
 * @param {Boolean} isTable 是否是报表，报表不需要聚合的那么细节
 */
class Crossfilter {
  constructor(val, channel, os_type, time, startDate, endDate, isTable) {
    this.val = val;
    this.channel = channel;
    this.os_type = os_type;
    this.time = time;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isTable = isTable || false;
  }
  // 下列方法应该用继承
  channelFilter() {
    this.val = ChanelQUERY.channelFilter(
      this.val,
      this.channel,
      this.time,
      this.isTable,
    );
    return this;
  }
  osTypeFilter() {
    this.val = OSTypeQUERY.osTypeFilter(
      this.val,
      this.os_type,
      this.time,
      this.isTable,
    );
    return this;
  }
  timeFilter() {
    this.val = DateQUERY.timeFilter(
      this.val,
      this.time,
      this.startDate,
      this.endDate,
    );
    return this;
  }
  getVal() {
    this.channelFilter()
      .osTypeFilter()
      .timeFilter();
    return this.val;
  }
}

module.exports = BusRegist;
