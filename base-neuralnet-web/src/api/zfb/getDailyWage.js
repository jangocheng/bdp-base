import reqDowload from '@/utils/reqDowload';
import request from '@/utils/request';

/**
 * 日结薪用户申请情况查询-信用额度申请情况
 */
export function creditQuotaApplyQuery({
  startDate,
  endDate,
  dateType,
  quotaStatus,
  sjtType,
  approveStatus,
}) {
  return request({
    url: '/zfbDailySalary/getTotalApplyQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      quotaStatus,
      sjtType,
      approveStatus,
    },
  });
}

/**
 * 日结薪用户-------薪资额度支付情况查询
 */
export function quotaPayQuery({
  startDate,
  endDate,
  dateType,
  quotaStatus,
  sjtType,
  approveStatus,
}) {
  return request({
    url: '/zfbDailySalary/getQuotaPayQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      quotaStatus,
      sjtType,
      approveStatus,
    },
  });
}

/**
 * 日结薪用户-------电商收入报表查询
 */
export function exportQuery({
  startDate,
  endDate,
  dateType,
  quotaStatus,
  sjtType,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/zfbDailySalary/getDailySalaryTable',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      quotaStatus,
      sjtType,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 日结薪用户-------信用额度提现情况
 */
export function quotaWithdrawQuery({
  startDate,
  endDate,
  dateType,
  quotaStatus,
  sjtType,
}) {
  return request({
    url: '/zfbDailySalary/getQuotaWithdrawQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      quotaStatus,
      sjtType,
    },
  });
}

/**
 * 日结薪用户-------新增信用额度提现情况
 */
export function quotaNewWithdrawQuery({
  startDate,
  endDate,
  dateType,
  quotaStatus,
  sjtType,
}) {
  return request({
    url: '/zfbDailySalary/getQuotaNewWithdrawQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      quotaStatus,
      sjtType,
    },
  });
}

/**
 * 下载电商收入报表
 */
export function exportDownload({
  startDate,
  endDate,
  dateType,
  quotaStatus,
  sjtType,
}) {
  return reqDowload({
    url: '/zfbDailySalary/exportDownload',
    responseType: 'blob',
    fileName: `支付宝支付提现报表-${startDate}/${endDate}`,
    method: 'post',
    params: {
      startDate,
      endDate,
      dateType,
      quotaStatus,
      sjtType,
    },
  });
}
