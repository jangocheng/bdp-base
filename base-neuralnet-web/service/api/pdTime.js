const { request } = require('../utils/request');

/**
 * 获取 推广引流(时间)申请报告
 * @param type
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getPDTimeData(type, startDate, endDate) {
  const param = {
    path: `/business/popularize/${type}/${startDate}/${endDate}`,
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
 * 获取 推广引流(时间)申请报告
 * @param type
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getPDTimeSummary({ channel, startDate, endDate, summaryField } = {}) {
  const param = {
    path: '/business/popularize/channel/summary',
    method: 'post',
    data: {
      startDate,
      endDate,
      channel,
      summaryField,
    },
  };
  return request(param);
}

module.exports = {
  getPDTimeData,
  getChannel,
  getPDTimeSummary,
};
