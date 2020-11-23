import request from '@/utils/request';

/**
 * 获取业务大盘数据--渠道申请报表
 */
// eslint-disable-next-line camelcase
export function getBusApplyData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  customerType,
  checkType,
  pageNum,
  pageSize,
} = {}) {
  return request({
    url: '/business/getBusApplyData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      osType,
      customerType,
      checkType,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 获取渠道数据
 */
export function getApplyChanel() {
  return request({
    url: '/business/getApplyChanel',
    method: 'get',
  });
}

/**
 * 请求申请到进件各环节数量及环比
 */
export function getApplyData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  isChain,
}) {
  return request({
    url: '/business/getApplyData',
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
 * 请求 渠道申请量占比及“环比”
 */
export function getApplyEchartData({
  startDate,
  endDate,
  time,
  channel,
  osType,
  customerType,
  checkType,
  isChain,
}) {
  return request({
    url: '/business/getApplyEchartData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      osType,
      customerType,
      checkType,
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
  customerType,
  checkType,
  isChain,
}) {
  return request({
    url: '/business/getChannelApplyData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      osType,
      customerType,
      checkType,
      isChain,
    },
  });
}
