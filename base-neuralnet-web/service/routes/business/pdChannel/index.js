const _ = require('lodash');
const mathjs = require('mathjs');
const moment = require('moment');

// const { customerENUM, checkTypeENUM } = require('../../enmu.js');
const DateQUERY = require('../../../utils/DateQUERY');
const ChanelQUERY = require('../ChanelQUERY');
const OSTypeQUERY = require('../OSTypeQUERY');
const { isChain } = require('../../../utils/isChain');
const {
  getPDChannelData,
  getChannel,
  getPDConsumptionData,
  getPDStoreData,
  getPDBrandData,
} = require('../../../api/pdchannel');

class PDChannel {
  /**
   * 查询渠道申请报表数据
   * @param {Object} ctx
   * @param {Function} next
   */
  static async queryPDChannelTable(ctx, next) {
    try {
      // TODO:对条件先行校验
      // 此处可以校验一下时间
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
      {
        const { channel, osType, time } = ctx.query;
        let isTable;
        // 渠道为所有
        isTable = ctx.query.channel === '所有';
        if (resArr.length > 0) {
          resArr = ChanelQUERY.channelFilter(resArr, channel, time, isTable);
        }
        // 系统终端为所有
        isTable = ctx.query.osType === '05';
        if (resArr.length > 0) {
          resArr = OSTypeQUERY.osTypeFilter(resArr, osType, time, isTable);
        }
        if (resArr.length > 0) resArr = DateQUERY.timeFilter(resArr, time);
      }

      // 4、根据页数过滤
      const pageSize = 1 * ctx.query.pageSize || 10; //默认10条一爷
      const currentPage = 1 * ctx.query.pageNum || 1; //当前页数，默认第一页
      let resData = []; //声明传出的参数
      // //根据当前页数截取指定条数
      resArr.forEach((item, index) => {
        // 假如第二页，总数23条，就是11-20条
        if (
          (currentPage - 1) * pageSize <= index &&
          currentPage * pageSize > index
        ) {
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

  /**
   * 获取 推广引流(渠道)--渠道注册数量
   */
  static async getChannelRegisterData(ctx, next) {
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
        const { channel, osType, time } = ctx.query;
        // 渠道为所有
        isTable = channel === '所有';
        temArr = ChanelQUERY.channelFilter(temArr, channel, time, isTable);
        // 系统终端为所有
        isTable = osType === '05';
        temArr = OSTypeQUERY.osTypeFilter(temArr, osType, time, isTable);
        temArr = DateQUERY.timeFilter(temArr, time);

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
            // registerAmount: [],      //注册量
            // identifiedAmount: [],    //实名认证数
            // applyAmount: [],         //申请数
            // applyApprovedAmount: [],     //申请通过数
            // machineRefuseAmount: [], //机审拒绝
            // withdrawPassAmount: [],  //提现成功人数
            const {
              date,
              registerAmount,
              identifiedAmount,
              applyAmount,
              applyApprovedAmount,
              machineRefuseAmount,
              withdrawPassAmount,
            } = ele;
            return {
              date,
              registerAmount,
              identifiedAmount,
              applyAmount,
              applyApprovedAmount,
              machineRefuseAmount,
              withdrawPassAmount,
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
      ctx.app.emit('error', ctx, err);
    }
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
      if (!temArr || temArr.length === 0) {
        return (ctx.body = {
          code: 400,
          msg: '暂无数据',
          data: [],
        });
      }
      {
        const { channel, osType, time } = ctx.query;
        let isTable;
        // 渠道为所有
        isTable = channel === '所有';
        temArr = ChanelQUERY.channelFilter(temArr, channel, time, isTable);
        // 系统终端为所有
        isTable = osType === '05';
        temArr = OSTypeQUERY.osTypeFilter(temArr, osType, time, isTable);
        temArr = DateQUERY.timeFilter(temArr, time);
      }

      // 获取指定时间内的渠道排名
      const resChanelData = await getChannel({
        startDate: ctx.query.startDate.replace(/-/g, ''),
        endDate: ctx.query.endDate.replace(/-/g, ''),
      });

      // 当期数据
      let channelData = [];
      let objByChannel = [];
      //上期数据
      // let lastChannelData = [];
      // let lastObjByChannel = [];
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
   * 推广引流申请报告
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
        let res = await getPDChannelData(time, startDate, endDate);
        /**
         * 加工一下数据，计算其他字段
         * {
         *  "date": "Duis",                                 //时间
         *  "channel": "aliqua mollit qui tempor",          //渠道名称
         *  "osType": "fugiat irure",                       //终端
         *  "registerAmount": "nostrud voluptate",          //注册人数
         *  "registerIdentifiedAmount": "deserunt in ut",   //实名认证数
         *  "registerIdentifiedRate": -48027068.40299909,   //实名认证率=registerIdentifiedAmount/registerAmount
         *  "applyAmount": -87024320.3063875,               //申请进件数
         *  "applyApprovedAmount": -46923380.58079062,      //申请进件通过数
         *  "applyApprovedRate": "consectetur et",          //申请通过率=applyApprovedAmount/applyAmount
         *  "registerToApplyRate": -17023657.4012924,       //注册转化率=applyApprovedAmount/registerAmount
         *  "refuseAmount":''                               //拒绝件数=applyRefuseAmount
         *  "applyRefuseAmount" : "",                       //申请拒绝数
         *  "machineRefuseAmount": "nisi Ut",               //机审拒绝数
         *  "machineRefuseRate": "sunt ullamco",            //机审拒绝率=machineRefuseAmount/applyAmount
         *  "withdrawPassAmount": "cupidatat qui ipsum ullamco",   // 提现成功人数
         *  "withdrawPassRage": "adipisicing minim ut quis" //提现成功率=withdrawPassAmount/applyAmount
         * }
         */
        const _division = ({ numerator, denominator }) => {
          try {
            if (denominator === 0) return '0.00%';
            return (
              mathjs.eval(`${numerator}/${denominator} * 100`).toFixed(2) + '%'
            );
          } catch (error) {
            console.log(error);
            return '0.00%';
          }
        };
        if (res && res.data && res.data.length > 0) {
          ctx.body = res.data.map(ele => {
            // 实名认证率
            const registerIdentifiedRate = _division({
              numerator: ele.registerIdentifiedAmount,
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
              denominator: ele.registerAmount,
            });

            // 机审拒绝率
            const machineRefuseRate = _division({
              numerator: ele.machineRefuseAmount,
              denominator: ele.applyAmount,
            });
            // 提现成功率
            const withdrawPassRage = _division({
              numerator: ele.withdrawPassAmount,
              denominator: ele.applyAmount,
            });
            return {
              ...ele,
              registerIdentifiedRate,
              applyApprovedRate,
              registerToApplyRate,
              refuseAmount: ele.applyRefuseAmount,
              machineRefuseRate,
              withdrawPassRage,
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
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 获取推广引流消费数据
   * @param ctx
   * @param next
   * @returns {Promise<*>}
   */
  static async getPopularizeConsumptionData(ctx, next) {
    try {
      let { startDate, endDate, channel, pageSize, pageNum } = ctx.query;
      channel = channel === '所有' ? '' : channel;
      let resArr = await getPDConsumptionData(channel, startDate, endDate);
      if (resArr && resArr.data && resArr.data.length > 0) {
        ctx.body = {
          success: true,
          code: 200,
          msg: 'request successful',
          data: {
            total: resArr.data.length,
            list: PageTurning(resArr.data, pageNum, pageSize),
          },
        };
      } else {
        ctx.body = {
          success: true,
          code: 400,
          msg: '暂无数据',
        };
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 获取 推广引流门店数据
   * @param ctx
   * @param next
   * @returns {Promise<*>}
   */
  static async getPopularizeStoreData(ctx, next) {
    try {
      let { startDate, endDate, channel, pageSize, pageNum } = ctx.query;
      channel = channel === '所有' ? '' : channel;
      let resArr = await getPDStoreData(channel, startDate, endDate);
      if (resArr && resArr.data && resArr.data.length > 0) {
        //根据页数过滤
        pageSize = 1 * pageSize || 10; //默认10条一爷
        pageNum = 1 * pageNum || 1; //当前页数，默认第一页
        const resData = []; //声明传出的参数
        // //根据当前页数截取指定条数
        resArr.data.forEach((item, index) => {
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
            total: resArr.data.length,
            list: resData,
          },
        };
      } else {
        ctx.body = {
          success: true,
          code: 400,
          msg: '暂无数据',
        };
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }

  /**
   * 品牌消费金额排行
   * @param {*} ctx
   * @param {*} next
   */
  static async getPopularizeBrandData(ctx, next) {
    try {
      let { startDate, endDate, channel } = ctx.query;
      channel = channel === '所有' ? '' : channel;
      let resArr = await getPDBrandData(channel, startDate, endDate);

      if (resArr.data && resArr.data.length > 0) {
        ctx.body = {
          success: true,
          code: 200,
          msg: '请求成功',
          data: resArr.data,
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
}

/**
 * 翻页函数，用来从整个数据中获取指定页数的数据
 * @param {*} arr 原数组
 * @param {*} curPage 当前页数
 * @param {*} pageSize 一页多少条
 */
function PageTurning(arr, curPage, pageSize) {
  try {
    //根据页数过滤
    pageSize = 1 * pageSize || 10; //默认10条一爷
    curPage = 1 * curPage || 1; //当前页数，默认第一页
    let resData = []; //声明传出的参数
    // //根据当前页数截取指定条数
    arr.forEach((item, index) => {
      // 假如第二页，总数23条，就是11-20条
      if ((curPage - 1) * pageSize <= index && curPage * pageSize > index) {
        const consumeAmount = mathjs.add(
          item.paymentAmount,
          item.withdrawAmount,
        );
        const consumeSum = mathjs.add(item.paymentSum, item.withdrawSum);
        resData.push({
          consumeAmount,
          consumeSum,
          ...item,
        });
      }
    });
    return resData;
  } catch (error) {
    console.error('error', error);
    return [];
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
        // 注册人数
        const registerAmount = ele.reduce((prev, current) => {
          return ~~current.registerAmount + ~~prev;
        }, 0);
        // 实名认证数
        const identifiedAmount = ele.reduce((prev, current) => {
          return ~~current.registerIdentifiedAmount + ~~prev;
        }, 0);
        // 申请进件数
        const applyAmount = ele.reduce((prev, current) => {
          return ~~current.applyAmount + ~~prev;
        }, 0);
        // 申请进件通过数
        const applyApprovedAmount = ele.reduce((prev, current) => {
          return ~~current.applyApprovedAmount + ~~prev;
        }, 0);
        // 机审拒绝人数
        const machineRefuseAmount = ele.reduce((prev, current) => {
          return ~~current.machineRefuseAmount + ~~prev;
        }, 0);
        // 提现成功人数
        const withdrawPassAmount = ele.reduce((prev, current) => {
          return ~~current.withdrawPassAmount + ~~prev;
        }, 0);
        temArr.push({
          date: key,
          registerAmount,
          identifiedAmount,
          applyAmount,
          applyApprovedAmount,
          machineRefuseAmount,
          withdrawPassAmount,
        });
      }
    }
    return temArr;
  } catch (error) {
    console.error('error', error);
  }
}

/**
 * 加工渠道数据
 * @param arr java层返回过来的元数据
 * @param channelArr  渠道数组
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
module.exports = PDChannel;
