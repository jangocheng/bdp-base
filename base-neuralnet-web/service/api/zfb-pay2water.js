const { request } = require('../utils/request');

const REQ_PARAM = {
  startDate: '',
  endDate: '',
  dateType: 'day',
  sourceType: '',
  payType: '',
};

/**
 * 获取 支付流水随时间变化情况查询
 * @returns {Promise<*>|*}
 */
function getPayIndicatorsByTimeQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbConsumePipe/conditionQuery',
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
function getPay2waterTable({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbConsumePipe/exportQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

module.exports = { getPayIndicatorsByTimeQuery, getPay2waterTable };
