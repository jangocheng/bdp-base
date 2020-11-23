import request from '@/utils/request';
import reqDowload from '@/utils/reqDowload';

/**
 * 授信金额占比
 */
export function getPayIndicatorsByTimeQuery({
  startDate,
  endDate,
  dateType,
  sourceType,
  payType,
  isChain,
}) {
  return request({
    url: '/zfbConsumePipe/PayIndicatorsByTime',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      sourceType,
      payType,
      isChain,
    },
  });
}

/**
 * 请求支付流水报表
 */
export function getPay2waterTable({
  startDate,
  endDate,
  dateType,
  sourceType,
  payType,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/zfbConsumePipe/Pay2waterTable',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      sourceType,
      payType,
      pageNum,
      pageSize,
    },
  });
}

export function exportDownload({
  startDate,
  endDate,
  dateType,
  sourceType,
  payType,
}) {
  return reqDowload({
    url: '/zfbConsumePipe/exportDownload',
    responseType: 'blob',
    fileName: `支付流水报表-${startDate}/${endDate}`,
    method: 'post',
    params: {
      startDate,
      endDate,
      dateType,
      sourceType,
      payType,
    },
  });
}
