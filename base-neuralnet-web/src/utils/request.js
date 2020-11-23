import axios from 'axios';
import { Message, MessageBox } from 'element-ui';
import { getToken } from '@/utils/auth.js';

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API,
});

console.log(process.env);

// request拦截器
service.interceptors.request.use(
  config => {
    if (getToken('token')) {
      config.headers['X-Token'] = getToken('token');
    } else {
      MessageBox.alert('缺失Token信息', '确定登出', {
        confirmButtonText: '重新登录',
        type: 'warning',
      }).then(() => {
        // 判断当前环境
        window.location.href = process.env.LOGIN_URL;
      });
    }
    return config;
  },
  error => {
    console.log(error);
    Promise.reject(error);
  },
);

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非200是抛错 可结合自己业务进行修改
     */
    const res = response.data;
    if (res.code !== 200) {
      // 401:非法的token
      if (res.code === 401 || res.code === 403 || res.code === 500) {
        MessageBox.alert(res.msg, '确定登出', {
          confirmButtonText: '重新登录',
          type: 'warning',
        }).then(() => {
          if (process.env.NODE_ENV === 'production') {
            window.location.href = process.env.LOGIN_URL;
          }
        });
        return;
      }
      Message({
        message: res.msg,
        type: 'error',
        duration: 5 * 1000,
      });
      return Promise.reject(res);
    } else if (res.code === 200) {
      return Promise.resolve(res);
    }
  },
  error => {
    console.log(`err${error}`); // for debug
    if (error && error.response) {
      const res = {};
      res.code = error.response.staus;
      return Promise.reject(res);
    }
    return Promise.reject(error);
  },
);

export default service;
