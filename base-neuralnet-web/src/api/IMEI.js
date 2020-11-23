import request from '@/utils/request';

export function getLoadCustomerUsedSameDevice(params) {
  return request({
    url: '/customer/loadCustomerUsedSameDevice',
    method: 'get',
    params: {
      imei: params,
    },
  });
}
