const { request } = require('../utils/request');

/**
 * 获取渠道数据(根据注册量排序)
 * @param {String} startDate 开始时间  不传入参数则表示不做限制条件
 * @param {String} endDate 结束时间
 */
function getChanel(
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
 * 获取 实时注册数据 元数据
 * @param {String} startDate 20180101
 * @param {String} endDate 20180101
 */
function getRegisterData(type, startDate, endDate) {
  const param = {
    path: `/business/register_data/${type}/${startDate}/${endDate}`,
  };
  return request(param);
}

module.exports = { getRegisterData, getChanel };
