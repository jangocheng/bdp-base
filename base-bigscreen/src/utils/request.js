/*
 * @Author: wlhbdp
 * @Date: 2020-04-03 16:25:52
 *
 * 走Http协议的请求方式
 *
 */

import axios from 'axios';

let param;
switch (process.env.NODE_ENV) {
  // 生产环境
  case 'production':
    param = process.env.VUE_APP_HTTP_PRO;
    break;
  // 测试环境
  case 'test':
    param = process.env.VUE_APP_HTTP_TEST;
    break;
  // 开发环境
  case 'development':
    param = process.env.VUE_APP_HTTP_PRO;
    // param = '/api/';
    break;
  default:
    // console.error('process.env.NODE_ENV:有误');
    break;
}

// 创建axios实例
const service = axios.create({
  baseURL: param,
});

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非200是抛错 可结合自己业务进行修改
     */
    const res = response.data;
    return res;
  },
  error => {
    console.log(`err${error}`); // for debug
    return Promise.reject(error);
  },
);

export default service;
