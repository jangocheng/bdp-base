/* eslint-disable import/prefer-default-export */
import request from '@/utils/request';

/**
 * 每日实时消费金额
 */
export function dailyRealTimeConsumption() {
  return request({
    url: '/rtbi/elecwallet/dailyRealTimeConsumption',
    method: 'get',
  });
}
