const { request } = require('../utils/request');

const REQ_PARAM = {
  startDate: '',
  endDate: '',
  dateType: 'day',
  quotaStatus: '',
  sjtType: '',
};

/**
 * 日结薪用户申请情况查询-信用额度申请情况
 * @returns {Promise<*>|*}
 */
function getTotalApplyQuery({ approveStatus, ...REQ_PARAM }) {
  const param = {
    path: '/zfbDailySalary/totalApplyQuery',
    method: 'post',
    base: 'zfb',
    data: {
      approveStatus,
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 日结薪用户-------薪资额度支付情况查询
 * @returns {Promise<*>|*}
 */
function getQuotaPayQuery({ approveStatus, ...REQ_PARAM }) {
  const param = {
    path: '/zfbDailySalary/quotaPayQuery',
    method: 'post',
    base: 'zfb',
    data: {
      approveStatus,
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 日结薪用户-------信用额度提现情况
 * @returns {Promise<*>|*}
 */
function getQuotaWithdrawQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbDailySalary/quotaWithdrawQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 日结薪用户-------新增信用额度提现情况
 * @returns {Promise<*>|*}
 */
function getQuotaNewWithdrawQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbDailySalary/quotaNewWithdrawQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 日结薪用户-------新增信用额度提现情况
 * @returns {Promise<*>|*}
 */
function getDailySalaryTable({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbDailySalary/exportQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

module.exports = {
  getTotalApplyQuery,
  getQuotaPayQuery,
  getQuotaWithdrawQuery,
  getQuotaNewWithdrawQuery,
  getDailySalaryTable,
};
