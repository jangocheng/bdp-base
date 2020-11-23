import request from '@/utils/request'

export const register = (data: any) =>
  request({
    url: '/users/register',
    method: 'post',
    data
  })
