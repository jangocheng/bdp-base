import request from '@/utils/request';

/**
 * 获取渠道数据
 */
export function getChanel() {
  return request({
    url: '/zfbParam/conditionQuery',
    method: 'get',
  });
}
