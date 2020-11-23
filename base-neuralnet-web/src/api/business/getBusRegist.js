import request from '@/utils/request';

/**
 * 获取业务大盘数据--渠道注册报表
 * @param {String} startDate 开始时间 20180911
 * @param {String} endDate 结束时间 20180911
 * @param {String} page_num 当前页数 默认为1
 * @param {String} page_size 每页多少条 默认10条
 */
// eslint-disable-next-line camelcase
export function getBusTableData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  isChain,
  pageNum,
  pageSize,
} = {}) {
  return request({
    url: '/business/getBusTableData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      os_type: osType,
      isChain,
      page_num: pageNum,
      page_size: pageSize,
    },
  });
}

/**
 * 获取渠道数据
 */
export function getChanel() {
  return request({
    url: '/business/getRegisterChanel',
    method: 'get',
  });
}

/**
 * 请求注册到进件各环节数量及环比
 */
export function getRegisterData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  isChain,
}) {
  return request({
    url: '/business/getRegisterData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      os_type: osType,
      isChain,
    },
  });
}

/**
 * 请求 渠道注册量占比及“环比”
 */
export function getChannelData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  isChain,
}) {
  return request({
    url: '/business/getChanelChartData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      os_type: osType,
      isChain,
    },
  });
}
