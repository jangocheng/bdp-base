import request from '@/utils/request';
import reqDowload from '@/utils/reqDowload';

/**
 * 进件申请环节数量
 */
export function getUserPayTarget({
  startDate,
  endDate,
  dateType,
  popuChannel,
  payWays,
  isChain,
}) {
  return request({
    url: '/zfbShopPay/userPayTarget',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      popuChannel,
      payWays,
      isChain,
    },
  });
}

export function getMallPayTable({
  startDate,
  endDate,
  dateType,
  popuChannel,
  payWays,
  pageNum,
  pageSize,
}) {
  return request({
    url: '/zfbShopPay/mallPayTable',
    method: 'get',
    params: {
      startDate,
      endDate,
      dateType,
      popuChannel,
      payWays,
      pageNum,
      pageSize,
    },
  });
}

export function exportDownload({
  startDate,
  endDate,
  dateType,
  popuChannel,
  payWays,
}) {
  return reqDowload({
    url: '/zfbShopPay/exportDownload',
    responseType: 'blob',
    fileName: `商城支付报表-${startDate}/${endDate}`,
    method: 'post',
    params: {
      startDate,
      endDate,
      dateType,
      popuChannel,
      payWays,
    },
  });
}
