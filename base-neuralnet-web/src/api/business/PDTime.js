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
  isChain,
  pageNum,
  pageSize,
} = {}) {
  return request({
    url: '/pdtime/PDAppleTableData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
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
 * 获取通过人数和转化率情况
 * @param startDate
 * @param endDate
 * @param time
 * @param channel
 * @param isChain
 * @returns {AxiosPromise}
 */
export function applyApprovedAmount({
  startDate,
  endDate,
  time,
  channel,
  isChain,
} = {}) {
  return request({
    url: '/pdtime/applyApprovedAmount',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      isChain,
    },
  });
}

/**
 * 推广渠道(时间)--汇总数据
 * @param startDate
 * @param endDate
 * @param time
 * @param channel
 * @param isChain
 * @param summaryField  获取手机机型数据 mobileModel
 *                      性别数据 gender
 *                      年龄 age
 *                      省份 province
 *                      客户等级 customerLevel
 *                      贷款期数 loanPeriod
 *                      学历 educationalBackground
 * @returns {AxiosPromise}
 */
export function getSummary({
  startDate,
  endDate,
  time,
  channel,
  isChain,
  summaryField,
} = {}) {
  return request({
    url: '/pdtime/summary',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      channel,
      summaryField,
      isChain,
    },
  });
}
