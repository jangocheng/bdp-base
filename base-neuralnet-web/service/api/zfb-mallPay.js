const { request } = require('../utils/request');

const REQ_PARAM = {
  startDate: '',
  endDate: '',
  dateType: 'day',
  popuChannel: '',
  payWays: '',
};

/**
 * 获取 推广渠道用户支付指标
 * @returns {Promise<*>|*}
 */
function getUserPayTarge({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbShopPay/conditionQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 获取 支付流水报表
 * @returns {Promise<*>|*}
 */
function getMallPayTable({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbShopPay/exportQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

module.exports = { getUserPayTarge, getMallPayTable };
