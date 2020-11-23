const { request } = require('../utils/request');

/**
 * 获取 推广引流申请报告
 * @param type
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getPDChannelData(type, startDate, endDate) {
  const param = {
    path: `/business/popularize/apply/${type}/${startDate}/${endDate}`,
  };
  return request(param);
}

/**
 * 获取渠道数据(根据注册量排序)
 * @param {String} startDate 开始时间  不传入参数则表示不做限制条件
 * @param {String} endDate 结束时间
 */
function getChannel(
  { startDate, endDate } = {
    startDate: '',
    endDate: '',
  },
) {
  const channelParam = {
    path: `/business/register/channels?startDate=${startDate}&endDate=${endDate}`,
  };
  return request(channelParam);
}

/**
 * 获取 推广引流消费数据
 * @param channel
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getPDConsumptionData(channel, startDate, endDate) {
  const param = {
    path: `/business/popularize/consumption?start_date=${startDate}&end_date=${endDate}&channel=${channel}`,
  };
  return request(param);
}

/**
 * 获取 推广引流门店数据
 * @param channel
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getPDStoreData(channel, startDate, endDate) {
  const param = {
    path: `/business/popularize/store?start_date=${startDate}&end_date=${endDate}&channel=${channel}`,
  };
  return request(param);
}

/**
 * 获取 推广引流消费数据
 * @param channel
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getPDBrandData(channel, startDate, endDate) {
  const param = {
    path: `/business/popularize/brand?start_date=${startDate}&end_date=${endDate}&channel=${channel}`,
  };
  return request(param);
}

module.exports = {
  getPDChannelData,
  getChannel,
  getPDConsumptionData,
  getPDStoreData,
  getPDBrandData,
};
