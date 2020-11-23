import request from '@/utils/request';

export function loadSuspiciousCustomersByPhoneNumber(params) {
  return request({
    url: '/customer/loadSuspiciousCustomersByPhoneNumber',
    method: 'get',
    params: {
      phoneNumber: params,
    },
  });
}
