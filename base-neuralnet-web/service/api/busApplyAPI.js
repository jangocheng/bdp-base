const { request } = require('../utils/request');

/**
 * 获取 实时申请数据 元数据
 * @param {String} startDate 20180101
 * @param {String} endDate 20180101
 */
function getApplyData(type, startDate, endDate) {
  const param = {
    path: `/business/apply_data/${type}/${startDate}/${endDate}`,
  };
  return request(param);
}

/**
 * 获取渠道数据(根据申请量排序)
 * @param {String} startDate 开始时间  不传入参数则表示不做限制条件
 * @param {String} endDate 结束时间
 */
function getApplyChanel(
  { startDate, endDate } = {
    startDate: '',
    endDate: '',
  },
) {
  const channelParam = {
    path: `/business/apply/channels?startDate=${startDate}&endDate=${endDate}`,
  };
  return request(channelParam);
}

module.exports = { getApplyData, getApplyChanel };
