/*

 *
 * 业务大盘数据--实时申请数据
 *
 */

const _ = require('lodash');
const mathjs = require('mathjs');
const moment = require('moment');

const { customerENUM } = require('../../enmu.js');
const DateQUERY = require('../../../utils/DateQUERY');
const ChanelQUERY = require('../ChanelQUERY');
const OSTypeQUERY = require('../OSTypeQUERY');
const CustomerQUERY = require('../CustomerQUERY');
const { getApplyData, getApplyChanel } = require('../../../api/busApplyAPI');
const { isChain } = require('../../../utils/isChain');

class BusApply {
  /**
   * 渠道申请报表
   * @param {Object} ctx
   * @param {Function} next
   */
  static async queryBusApply(ctx, next) {
    try {
      // TODO:对条件先行校验
      // 此处可以校验一下时间
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
        const { channel, osType, time, customerType } = ctx.query;
        let isTable;
        // 渠道为所有
        isTable = channel === '所有';
        if (temArr.length > 0) {
          temArr = ChanelQUERY.channelFilter(temArr, channel, time, isTable);
        }
        // 系统终端为所有
        isTable = osType === '05';
        if (temArr.length > 0) {
          temArr = OSTypeQUERY.osTypeFilter(temArr, osType, time, isTable);
        }
        if (temArr.length > 0) temArr = DateQUERY.timeFilter(temArr, time);

        // 判断传入的客户类型条件来完成过滤
        if (customerType !== customerENUM.ALL) {
          temArr = CustomerQUERY.customerFilter(temArr, customerType);
        }
      }

      // 4、根据页数过滤
      const pageSize = ctx.query.pageSize || 10; //默认10条一爷
      const currentPage = ctx.query.pageNum || 1; //当前页数，默认第一页
      let resData = []; //声明传出的参数
      // //根据当前页数截取指定条数
      temArr.forEach((item, index) => {
        // 假如第二页，总数23条，就是11-20条
        if (
          (currentPage - 1) * pageSize <= index &&
          currentPage * pageSize > index
        ) {
          // 0不能做除数
          let pass;
          if (item.applyAmount !== 0) {
            pass = `${_.floor(
              _.divide(item.applyApprovedAmount, item.applyAmount) * 100,
              2,
            )}%`;
          } else {
            pass = '0%';
          }
          resData.push({
            ...item,
            pass,
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
    } catch (error) {
      console.error('error', error);
    }
  }

  /**
   * 申请环节数量及“环比”
   */
  static async applyforlink(ctx, next) {
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
      if (temArr.length === 0) {
        ctx.body = {
          code: 400,
          msg: '暂无数据',
          data: [],
        };
        return;
      }
      {
        let isTable;
        const { channel, osType, time, customerType } = ctx.query;
        // 渠道为所有
        isTable = channel === '所有';
        if (temArr.length > 0) {
          temArr = ChanelQUERY.channelFilter(temArr, channel, time, isTable);
        }
        // 系统终端为所有
        isTable = osType === '05';
        if (temArr.length > 0) {
          temArr = OSTypeQUERY.osTypeFilter(temArr, osType, time, isTable);
        }
        if (temArr.length > 0) temArr = DateQUERY.timeFilter(temArr, time);

        // 判断传入的客户类型条件来完成过滤
        if (customerType !== customerENUM.ALL) {
          temArr = CustomerQUERY.customerFilter(temArr, customerType);
        }
        // temArr = new Crossfilter(temArr, channel, osType, time).getVal();
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
            const {
              date,
              approvedCreditSum,
              applyApprovingAmount,
              applyAmount,
              applyApprovedAmount,
            } = ele;
            const applyApproved = mathjs
              .eval(`${applyApprovedAmount}/${applyAmount}*100`)
              .toFixed(2);
            return {
              date,
              approvedCreditSum,
              applyApprovingAmount,
              applyAmount,
              applyApprovedAmount,
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
    } catch (error) {
      // TODO:参数有误
      // ctx.body = {
      //   success: true,
      //   code: 200,
      //   msg: 'request successful',
      // };
      console.log(error);
    }
    return ctx;
  }

  /**
   * 向java层请求"渠道注册报表"元数据服务
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
        let res = await getApplyData(time, startDate, endDate);
        ctx.body = res.data;
        /**
         * java层返回的数据格式
         * {
         *   date: '20190508';                 //时间
         *   channel: '融360';                 //渠道
         *   osType: 'Android';                //终端类型
         *   customerType: '01';               //用户类型  01:内部员工 ||02:社会人士
         *   applyAmount: 120000;              //申请总数
         *   applyApprovedAmount: 110000;      //申请通过数
         *   applyApprovingAmount: 40000;      //申请审核中
         *   applyCancelledAmount: 1000;       //申请取消数
         *   applyRefusedAmount: 50000;        //申请拒绝数
         *   applyReturnAmount: 2000;          //申请退回件数
         *   approvedCreditSum: 123560000.67;  //获批额度之和
         *   serviceValidAmount: 7000;         //待客服校验
         *   machinePassAmount: 60000;         //机审通过数
         *   machineRefuseAmount: 40000;       //机审拒绝数
         * }
         */
        /**
         * 遍历处理数据，添加
         * artificialPassAmount 人工通过
         * artificialRefuseAmount 人工拒绝
         */
        if (res.data.length > 0) {
          ctx.body = res.data.map(item => {
            //   通过总数-机审通过=人工通过
            const artificialPassAmount = mathjs.eval(
              `${item.applyApprovedAmount}-${item.machinePassAmount}`,
            );
            //   拒绝总数-机审拒绝=人工拒绝
            const artificialRefuseAmount = mathjs.eval(
              `${item.applyRefusedAmount}-${item.machineRefuseAmount}`,
            );
            const customerName = customerENUM[item.customerType];
            return {
              ...item,
              artificialPassAmount,
              artificialRefuseAmount,
              customerName,
            };
          });
        }
      } else {
        ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        };
      }
    } catch (error) {
      console.error('error', error);
    }
    return ctx;
  }

  /**
   * TODO:接口未调试
   * 向java层请求“渠道数据”
   * @param {Object} ctx
   */
  static async queryChanel(ctx) {
    try {
      // TODO:接口打通 但无数据
      // 获取渠道数据
      let res = await getApplyChanel();
      let temArr = ['所有', ...res.data];
      ctx.body = {
        success: true,
        code: 200,
        msg: 'request successful',
        data: temArr,
      };
      return ctx;
    } catch (error) {
      console.log(error);
    }
  }

  /**
   * 渠道注册量占比 及 “环比”
   * @param {*} ctx
   * @param {*} next
   */
  static async getChanelApplyData(ctx, next) {
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
        const { time, channel, osType } = ctx.query;
        const { startDate, endDate } = lastDate;
        // 获取环比所求的历史数据
        historyData = await getChannelData({
          startDate,
          endDate,
          time,
          channel,
          osType,
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
        const { channel, osType, time, customerType } = ctx.query;
        let isTable;
        // 渠道为所有
        isTable = channel === '所有';
        if (temArr.length > 0) {
          temArr = ChanelQUERY.channelFilter(temArr, channel, time, isTable);
        }
        // 系统终端为所有
        isTable = osType === '05';
        if (temArr.length > 0) {
          temArr = OSTypeQUERY.osTypeFilter(temArr, osType, time, isTable);
        }
        if (temArr.length > 0) temArr = DateQUERY.timeFilter(temArr, time);

        // 判断传入的客户类型条件来完成过滤
        if (customerType !== customerENUM.ALL && temArr.length > 0) {
          temArr = CustomerQUERY.customerFilter(temArr, customerType);
        }
      }

      // 获取指定时间内的渠道排名
      const resChanelData = await getApplyChanel({
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
    } catch (error) {
      console.error('error', error);
    }
  }
}

/**
 * 当渠道跟终端是选择“所有”的情况下，聚合相同时间的数据
 * @param {*} arr
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
      if (!temObj.hasOwnProperty(key)) {
        continue;
      }
      const ele = temObj[key];
      const approvedCreditSum = ele.reduce((prev, current) => {
        return ~~current.approvedCreditSum + ~~prev;
      }, 0);
      const applyApprovingAmount = ele.reduce((prev, current) => {
        return ~~current.applyApprovingAmount + ~~prev;
      }, 0);
      const applyAmount = ele.reduce((prev, current) => {
        return ~~current.applyAmount + ~~prev;
      }, 0);
      const applyApprovedAmount = ele.reduce((prev, current) => {
        return ~~current.applyApprovedAmount + ~~prev;
      }, 0);
      temArr.push({
        date: key,
        approvedCreditSum,
        applyApprovingAmount,
        applyAmount,
        applyApprovedAmount,
      });
    }
    return temArr;
  } catch (error) {
    console.error('error', error);
  }
}

// /**
//  * 按VIEW层传入的条件来过滤数组
//  * @param {String} val 过滤的目标数组
//  * @param {String} channel 渠道
//  * @param {String} osType 终端系统
//  * @param {String} time 时间粒度
//  * @param {Boolean} isTable 是否是报表，报表不需要聚合的那么细节
//  */
// class Crossfilter {
//   constructor(val, channel, osType, time, isTable) {
//     this.val = val;
//     this.channel = channel;
//     this.osType = osType;
//     this.time = time;
//     this.isTable = isTable || false;
//   }
//   // 下列方法应该用继承
//   channelFilter() {
//     this.val = ChanelQUERY.channelFilter(
//       this.val,
//       this.channel,
//       this.time,
//       // this.isTable,
//     );
//     return this;
//   }
//   osTypeFilter() {
//     this.val = OSTypeQUERY.osTypeFilter(
//       this.val,
//       this.osType,
//       this.time,
//       // this.isTable,
//     );
//     return this;
//   }
//   timeFilter() {
//     this.val = DateQUERY.timeFilter(this.val, this.time);
//     return this;
//   }
//   getVal() {
//     this.channelFilter()
//       .osTypeFilter()
//       .timeFilter();
//     return this.val;
//   }
// }

/**
 * 加工渠道数据
 * @param {Array} arr java层返回过来的元数据
 * @param {Array} channelArr  渠道数组
 * @param {String} channel   视图层条件渠道
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
    // 根据当前的渠道来 过滤出不需要的数据
    // objByChannel = _.pick(objByChannel, channelData);

    channelData = registerByChannel(channelData, arr).channelData;
    objByChannel = registerByChannel(channelData, arr).objByChannel;

    return { channelData, objByChannel };
  } catch (error) {
    console.error('error', error);
  }
}

/**
 * 根据时间来聚合 相同时间下的注册量占比
 * @param {Object} channelData 已经按渠道分组过后的数据
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
          return cur.applyAmount + prev;
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
            const applyAmount = temChannelItem.reduce((prev, current) => {
              return ~~current.applyAmount + ~~prev;
            }, 0);
            resArr.push({
              date: timekey,
              channel: channelkey,
              applyAmount,
              totalChannelSum,
            });
          }
        }
      }
    }
    // 求出当前所有渠道的和;
    // const channelSum = resArr.reduce((prev, current) => {
    //   return ~~current.applyAmount + ~~prev;
    // }, 0);
    resArr = resArr.map(item => {
      return {
        date: item.date,
        channel: item.channel,
        registerApproved:
          item.totalChannelSum === 0
            ? '0.00'
            : _.floor(
                _.divide(item.applyAmount, item.totalChannelSum) * 100,
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

module.exports = BusApply;
