import request from '@/utils/request';

export function login(username, password) {
  return request({
    url: '/user/login',
    method: 'post',
    data: {
      username,
      password,
    },
  });
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token },
  });
}

/**
 * 登出请求
 */
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post',
  });
}
