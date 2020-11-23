import Axios from 'axios';
import { MessageBox } from 'element-ui';
import { getToken } from '@/utils/auth.js';

Axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';

const service = Axios.create({
  baseURL: process.env.DOWNLOAD_URL, // url = base url + request url
  timeout: 5000,
});

service.interceptors.request.use(
  config => {
    if (getToken()) {
      config.headers['X-Token'] = getToken(); // 让每个请求携带自定义token 请根据实际情况自行修改
    } else {
      MessageBox.alert('缺失Token信息', '确定登出', {
        confirmButtonText: '重新登录',
        type: 'warning',
      }).then(() => {
        // 判断当前环境
        // @ts-ignore
        window.location.href = process.env.LOGIN_URL;
      });
    }
    config.data = JSON.stringify(config.params);
    return config;
  },
  error => {
    console.log(error);
    Promise.reject(error);
  },
);

// Response interceptors
service.interceptors.response.use(
  response => {
    const res = response.data;
    // 判断是否是需要下载的请求
    if (response.config && response.config.responseType === 'blob') {
      // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet这里表示xlsx类型
      const blob = new Blob([res], {
        type:
          'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8',
      });
      const filename = response.config.fileName || 'excel.xls';
      if ('download' in document.createElement('a')) {
        const downloadElement = document.createElement('a');
        let href;
        if (window.URL) {
          href = window.URL.createObjectURL(blob);
        } else {
          href = window.webkitURL.createObjectURL(blob);
        }
        downloadElement.href = href;
        downloadElement.download = filename;
        document.body.appendChild(downloadElement);
        downloadElement.click();
        if (window.URL) {
          window.URL.revokeObjectURL(href);
        } else {
          window.webkitURL.revokeObjectURL(href);
        }
        document.body.removeChild(downloadElement);
      } else {
        navigator.msSaveBlob(blob, filename);
      }
      return;
    }
    if (res.code !== 200) {
      return Promise.reject(new Error(res.message || 'Error'));
    }
    return response.data;
  },
  error => Promise.reject(error),
);

// @ts-ignore
export default service;
