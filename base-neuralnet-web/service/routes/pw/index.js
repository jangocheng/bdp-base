const moment = require('moment');

const {
  getRatePaytypes,
  getConsumptionAmount,
  getConsumptionSum,
  getConsumptionMaximum,
  getRatePaytype,
  getRateProduct,
} = require('../../api/PW');
const DateQUERY = require('../../utils/DateQUERY.js');

class PW {
  // 请求元数据
  static async reqAppltTable(ctx) {
    try {
      // 判断时间先后顺序
      if (moment(ctx.query.startDate).isBefore(ctx.query.endDate)) {
        let { time, startDate, endDate } = ctx.query;
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        time = time === 'hour' ? 'hour' : 'day';
        let res = await getMerchantReport(time, startDate, endDate);
        if (res && res.data && res.data.length > 0) {
          ctx.body = res.data;
        } else {
          ctx.body = {
            code: 200,
            msg: '暂无数据',
            data: [],
          };
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

  //交易笔数
  static async queryPayAmount(ctx) {
    try {
      let {
        startDate,
        endDate,
        customerType,
        consumptionType,
        time,
      } = ctx.query;
      if (moment(startDate).isBefore(endDate)) {
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        customerType = customerType === '03' ? '' : customerType;
        consumptionType = consumptionType === '所有' ? '' : consumptionType;
        let res = await getConsumptionAmount(
          customerType,
          consumptionType,
          startDate,
          endDate,
        );
        if (res && res.data && res.data.length > 0) {
          let temArr = res.data;
          temArr = DateQUERY.timeFilter(temArr, time).reverse();
          const xDate = new Set();
          const legend = new Set();
          // 获取x轴数据
          temArr.forEach(x => xDate.add(x.date));
          // 获取给前端的legend类型数据
          temArr.forEach(x => legend.add(x.consumptionType));
          let temObj = {};
          // 更改结果
          temArr.forEach(item => {
            if (temObj[item.consumptionType]) {
              temObj[item.consumptionType].push(item);
            } else {
              temObj[item.consumptionType] = [item];
            }
          });
          ctx.body = {
            code: 200,
            msg: '请求成功',
            data: {
              arr: temObj,
              xDate: Array.from(xDate),
              legend: Array.from(legend),
            },
          };
        } else {
          ctx.body = {
            code: 200,
            msg: '暂无数据',
            data: [],
          };
        }
      } else {
        ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        };
      }
    } catch (e) {
      ctx.app.emit('error', ctx, e);
    }
  }

  // 交易金额
  static async queryPaySum(ctx) {
    try {
      let {
        startDate,
        endDate,
        customerType,
        consumptionType,
        time,
      } = ctx.query;
      if (moment(startDate).isBefore(endDate)) {
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        customerType = customerType === '03' ? '' : customerType;
        consumptionType = consumptionType === '所有' ? '' : consumptionType;
        let res = await getConsumptionSum(
          customerType,
          consumptionType,
          startDate,
          endDate,
        );
        if (res && res.data && res.data.length > 0) {
          let temArr = res.data;
          temArr = DateQUERY.timeFilter(temArr, time).reverse();
          const xDate = new Set();
          const legend = new Set();
          // 获取x轴数据
          temArr.forEach(x => xDate.add(x.date));
          // 获取给前端的legend类型数据
          temArr.forEach(x => legend.add(x.consumptionType));
          let temObj = {};
          // 更改结果
          temArr.forEach(item => {
            if (temObj[item.consumptionType]) {
              temObj[item.consumptionType].push(item);
            } else {
              temObj[item.consumptionType] = [item];
            }
          });
          ctx.body = {
            code: 200,
            msg: '请求成功',
            data: {
              arr: temObj,
              xDate: Array.from(xDate),
              legend: Array.from(legend),
            },
          };
        } else {
          ctx.body = {
            code: 200,
            msg: '暂无数据',
            data: [],
          };
        }
      } else {
        ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        };
      }
    } catch (e) {
      console.log(e);
      ctx.app.emit('error', ctx, e);
    }
  }

  // 每天最大单笔交易金额
  static async queryPayMaximum(ctx) {
    try {
      let {
        startDate,
        endDate,
        customerType,
        consumptionType,
        time,
      } = ctx.query;
      if (moment(startDate).isBefore(endDate)) {
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        customerType = customerType === '03' ? '' : customerType;
        consumptionType = consumptionType === '所有' ? '' : consumptionType;
        let res = await getConsumptionMaximum(
          customerType,
          consumptionType,
          startDate,
          endDate,
        );
        if (res && res.data && res.data.length > 0) {
          let temArr = res.data;
          temArr = DateQUERY.timeFilter(temArr, time).reverse();
          const xDate = new Set();
          const legend = new Set();
          // 获取x轴数据
          temArr.forEach(x => xDate.add(x.date));
          // 获取给前端的legend类型数据
          temArr.forEach(x => legend.add(x.consumptionType));
          let temObj = {};
          // 更改结果
          temArr.forEach(item => {
            if (temObj[item.consumptionType]) {
              temObj[item.consumptionType].push(item);
            } else {
              temObj[item.consumptionType] = [item];
            }
          });
          ctx.body = {
            code: 200,
            msg: '请求成功',
            data: {
              arr: temObj,
              xDate: Array.from(xDate),
              legend: Array.from(legend),
            },
          };
        } else {
          ctx.body = {
            code: 200,
            msg: '暂无数据',
            data: [],
          };
        }
      } else {
        ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        };
      }
    } catch (e) {
      ctx.app.emit('error', ctx, e);
    }
  }

  // 支付类型占比
  static async queryPayPaytype(ctx) {
    try {
      let {
        startDate,
        endDate,
        customerType,
        consumptionType,
        // time,
      } = ctx.query;
      if (moment(startDate).isBefore(endDate)) {
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        customerType = customerType === '03' ? '' : customerType;
        consumptionType = consumptionType === '所有' ? '' : consumptionType;
        let res = await getRatePaytype(
          customerType,
          consumptionType,
          startDate,
          endDate,
        );
        if (res && res.data && res.data.length > 0) {
          let temArr = res.data;
          ctx.body = {
            code: 200,
            msg: '请求成功',
            data: temArr,
          };
        } else {
          ctx.body = {
            code: 200,
            msg: '暂无数据',
            data: [],
          };
        }
      } else {
        ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        };
      }
    } catch (e) {
      ctx.app.emit('error', ctx, e);
    }
  }

  // 商品种类分布
  static async queryPayProduct(ctx) {
    try {
      let { startDate, endDate, customerType, consumptionType } = ctx.query;
      if (moment(startDate).isBefore(endDate)) {
        startDate = startDate.replace(/-/g, '');
        endDate = endDate.replace(/-/g, '');
        customerType = customerType === '03' ? '' : customerType;
        consumptionType = consumptionType === '所有' ? '' : consumptionType;
        let res = await getRateProduct(
          customerType,
          consumptionType,
          startDate,
          endDate,
        );
        if (res && res.data && res.data.length > 0) {
          let temArr = res.data;
          ctx.body = {
            code: 200,
            msg: '请求成功',
            data: temArr,
          };
        } else {
          ctx.body = {
            code: 200,
            msg: '暂无数据',
            data: [],
          };
        }
      } else {
        ctx.body = {
          code: 400,
          msg: '请求时间范围与颗粒度不匹配',
          data: [],
        };
      }
    } catch (e) {
      ctx.app.emit('error', ctx, e);
    }
  }

  // 获取消费类型
  static async getRatePaytypes(ctx) {
    try {
      let res = await getRatePaytypes();
      if (res && res.data && res.data.length > 0) {
        ctx.body = res;
      } else {
        ctx.body = {
          code: 200,
          msg: '暂无数据',
          data: [],
        };
      }
    } catch (e) {
      ctx.app.emit('error', ctx, e);
    }
  }
}

module.exports = PW;
