const { request } = require('../utils/request');

const REQ_PARAM = {
  startDate: '',
  endDate: '',
  dateType: 'day',
  sourceType: '',
};

/**
 * 获取 授信金额及其占比条件查询
 * @returns {Promise<*>|*}
 */
function getFbdConsumeAmtPassQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbConsumeAmt/passQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 获取 提现次数金额及占比条件查询
 * @returns {Promise<*>|*}
 */
function getFbdConsumeAmtWithdrawQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbConsumeAmt/withdrawQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 获取 首次-提现次数金额及占比条件查询
 * @returns {Promise<*>|*}
 */
function getFbdConsumeAmtFirstWithdrawQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbConsumeAmt/firstWithdrawQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 获取 申请环节数据量及比例
 * @returns {Promise<*>|*}
 */
function getFbdP2WQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbConsumeAmt/exportQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

module.exports = {
  getFbdConsumeAmtPassQuery,
  getFbdConsumeAmtWithdrawQuery,
  getFbdConsumeAmtFirstWithdrawQuery,
  getFbdP2WQuery,
};
