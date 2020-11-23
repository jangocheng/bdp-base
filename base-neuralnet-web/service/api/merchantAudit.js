const { request } = require('../utils/request');

/**
 * 获取 门店审核报表
 * @param type 时间粒度类型
 * @param startDate 20180101
 * @param endDate 20180101
 * @returns {Promise<*>|*}
 */
function getMerchantReport(type, startDate, endDate) {
  const param = {
    path: `/business/merchant/report/${type}/${startDate}/${endDate}`,
  };
  return request(param);
}

module.exports = {
  getMerchantReport,
};
