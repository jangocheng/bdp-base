import request from '@/utils/request';
import reqDowload from '@/utils/reqDowload';

/**
 * 授信金额占比
 */
export function getCreditAmountQuery({
  startDate,
  endDate,
  dateType,
  sourceType,
  isChain,
}) {
  return request({
    url: '/zfbConsumeAmt/creditAmount',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      sourceType,
      isChain,
    },
  });
}

/**
 * 提现基础、金额及占比
 */
export function withdrawal({
  startDate,
  endDate,
  dateType,
  sourceType,
  isChain,
}) {
  return request({
    url: '/zfbConsumeAmt/withdrawal',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      sourceType,
      isChain,
    },
  });
}

/**
 * 首次提现基础、金额及占比
 */
export function firstWithdrawal({
  startDate,
  endDate,
  dateType,
  sourceType,
  isChain,
}) {
  return request({
    url: '/zfbConsumeAmt/firstWithdrawal',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      sourceType,
      isChain,
    },
  });
}

/**
 * 首次提现基础、金额及占比
 */
export function exportQuery({
  startDate,
  endDate,
  dateType,
  sourceType,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/zfbConsumeAmt/exportQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      sourceType,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 支付宝支付提现报表--下载
 */
export function getExportDownload({
  startDate,
  endDate,
  dateType,
  targetType,
  sourceType,
}) {
  return reqDowload({
    url: '/zfbConsumeAmt/exportDownload',
    responseType: 'blob',
    fileName: `支付宝支付提现报表-${startDate}/${endDate}`,
    method: 'post',
    params: {
      startDate,
      endDate,
      dateType,
      targetType,
      sourceType,
    },
  });
}
