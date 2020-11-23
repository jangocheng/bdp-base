import request from '@/utils/request';
import reqDowload from '@/utils/reqDowload';

/**
 * 进件申请环节数量
 */
export function getConditionQuery({
  startDate,
  endDate,
  dateType,
  targetType,
  sourceType,
  isChain,
}) {
  return request({
    url: '/zfbApply/conditionQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      targetType,
      sourceType,
      isChain,
    },
  });
}

/**
 * 支付宝进件注册报表
 */
export function getRegisterQuery({
  startDate,
  endDate,
  dateType,
  targetType,
  sourceType,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/zfbApply/registerQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      targetType,
      sourceType,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 支付宝进件注册报表--下载
 */
export function getRegisterDownload({
  startDate,
  endDate,
  dateType,
  targetType,
  sourceType,
}) {
  return reqDowload({
    url: '/zfbApply/register/exportDownload',
    responseType: 'blob',
    fileName: `支付宝进件注册报表-${startDate}/${endDate}`,
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

/**
 * 支付宝进件数量报表
 */
export function getAmtQuery({
  startDate,
  endDate,
  dateType,
  targetType,
  sourceType,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/zfbApply/amtQuery',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      targetType,
      sourceType,
      pageNum,
      pageSize,
    },
  });
}

/**
 * 支付宝进件数量报表--下载
 */
export function getAmtDownload({
  startDate,
  endDate,
  dateType,
  targetType,
  sourceType,
}) {
  return reqDowload({
    url: '/zfbApply/amt/exportDownload',
    responseType: 'blob',
    fileName: `支付宝进件数量报表-${startDate}/${endDate}`,
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
