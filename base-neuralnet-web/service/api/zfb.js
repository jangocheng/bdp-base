const { request } = require('../utils/request');

const REQ_PARAM = {
  startDate: '',
  endDate: '',
  dateType: 'day',
  targetType: '',
  sourceType: '',
};

/**
 * 获取 渠道数据
 * @returns {Promise<*>|*}
 */
function getFbdconditionQuery() {
  const param = {
    path: '/zfbParam/conditionQuery',
    method: 'post',
    base: 'zfb',
  };
  return request(param);
}

/**
 * 获取 申请环节数据量及比例
 * @returns {Promise<*>|*}
 */
function getFbdApplyConditionQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbApply/conditionQuery',
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
function getFbdApplyRegisterDownload({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbApply/register/exportDownload',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 获取 进件注册查询
 * @returns {Promise<*>|*}
 */
function getFbdApplyRegisterQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbApply/register/exportQuery',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

/**
 * 获取 进件数量查询
 * @returns {Promise<*>|*}
 */
function getFbdApplyAmtQuery({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbApply/amt/exportQuery',
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
function getFbdApplyAmtDownload({ ...REQ_PARAM }) {
  const param = {
    path: '/zfbApply/amt/exportDownload',
    method: 'post',
    base: 'zfb',
    data: {
      ...REQ_PARAM,
    },
  };
  return request(param);
}

module.exports = {
  getFbdconditionQuery,
  getFbdApplyConditionQuery,
  getFbdApplyRegisterQuery,
  getFbdApplyRegisterDownload,
  getFbdApplyAmtQuery,
  getFbdApplyAmtDownload,
};
