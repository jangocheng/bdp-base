import request from '@/utils/request';

/**
 * 获取业务大盘--推广引流(渠道)--渠道注册报表
 * @param startDate 开始时间 20180911
 * @param endDate 结束时间 20180911
 * @param time
 * @param channel
 * @param osType
 * @param isChain
 * @param pageNum 当前页数 默认为1
 * @param pageSize 每页多少条 默认10条
 * @returns {AxiosPromise}
 */
// eslint-disable-next-line camelcase
export function getPDAppleTbleData({
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
    url: '/pdchannel/PDAppleTableData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      osType,
      isChain,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 获取渠道数据
 */
export function getChanel() {
  return request({
    url: '/pdchannel/getPDChanel',
    method: 'get',
  });
}

/**
 * 请求 渠道申请量占比及“环比”
 */
export function getPDBusRegisterData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  isChain,
}) {
  return request({
    url: '/pdchannel/PDChannelRegisterData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      osType,
      isChain,
    },
  });
}

/**
 * 请求 渠道申请量占比及“环比”
 */
export function getChannelApplyData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  isChain,
}) {
  return request({
    url: '/pdchannel/getChannelApplyData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      osType,
      isChain,
    },
  });
}

/**
 * 请求 推广引流消费数据
 */
export function getPopularizeConsumptionData({
  startDate,
  endDate,
  channel,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/pdchannel/popularizeConsumption',
    method: 'get',
    params: {
      startDate,
      endDate,
      channel,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 请求 推广引流 门店数据
 */
export function getPopularizeStore({
  startDate,
  endDate,
  channel,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/pdchannel/popularizeStore',
    method: 'get',
    params: {
      startDate,
      endDate,
      channel,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 请求 推广引流 门店数据
 */

export function getPopularizeBrand({ startDate, endDate, channel, isChain }) {
  return request({
    url: '/pdchannel/popularizeBrand',
    method: 'get',
    params: {
      startDate,
      endDate,
      channel,
      isChain,
    },
  });
}
