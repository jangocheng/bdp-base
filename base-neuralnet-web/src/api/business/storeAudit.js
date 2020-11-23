import request from '@/utils/request';

/**
 * 获取业务大盘数据--门店审核报表
 * @param {String} startDate 开始时间 20180911
 * @param {String} endDate 结束时间 20180911
 * @param {String} time 时间粒度
 * @param {String} pageNum 当前页数 默认为1
 * @param {String} pageSize 每页多少条 默认10条
 */
// eslint-disable-next-line camelcase
export function getStoreAuditApplyTableData({
  startDate,
  endDate,
  time,
  pageNum,
  pageSize,
} = {}) {
  return request({
    url: '/StoreAudit/getStoreAuditApplyTableData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 注册量趋势图 数据|环比
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param time  时间粒度
 * @param isChain 是否环比
 * @returns {AxiosPromise}
 */
export function getRegisterData({ startDate, endDate, time, isChain }) {
  return request({
    url: '/StoreAudit/getRegisterData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      isChain,
    },
  });
}

export function getActiveStoreData({ startDate, endDate, time, isChain }) {
  return request({
    url: '/StoreAudit/getActiveStoreData',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      isChain,
    },
  });
}
