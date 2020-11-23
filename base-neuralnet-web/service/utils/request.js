const Axios = require('axios');
const config = require('../config/index');

Axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';

/**
 *
 * @param path  接口路径，status参数为false时，接口前缀统一从配置文件拿，若status=true，则需要传入全部地址
 * @param data  请求参数
 * @param method  请求方法 GET|POST
 * @param status  接口前缀状态，默认false
 * @param base  url的基础地址，根据请求不同的java服务来切换   cnw: 大盘报表服务 | zfb: 支付宝日报服务
 * @returns {Promise<*>}
 */
async function request({
  path,
  data = {},
  method = 'get',
  status = false,
  base,
}) {
  try {
    let url;
    switch (base) {
      case 'cnw':
        url = status ? path : `${config.COMPLEXNETWORKS_API}${path}`;
        break;
      case 'zfb':
        url = status ? path : `${config.zfb}${path}`;
        break;
      default:
        url = status ? path : `${config.env}${path}`;
    }
    const res = await Axios({
      method,
      url,
      data: JSON.stringify(data),
    });
    /**
     * 0:是验证token的服务器
     * 200:是数据正常的的服务器
     */
    if ([0, 200].includes(1 * res.data.code)) return res.data;
    return {
      code: 400,
      msg: '请求错误',
    };
  } catch (error) {
    console.error(error);
    return {
      code: 500,
      msg: '请求有误,请重新登录',
    };
  }
}

//添加一个请求拦截器
Axios.interceptors.request.use(config => config, err => Promise.reject(err));

module.exports = { request };
