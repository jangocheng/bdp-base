/**
 *
 *  获取请求地址前缀
 *
 * @param {String} param .env文件内的key
 * @returns
 */
function getUrl(param) {
  let url;
  if (typeof param === 'string') {
    switch (process.env.NODE_ENV) {
      // 生产环境
      case 'production':
        url = `${process.env.VUE_APP_PRO}/websocket-simple`;
        break;
      // 测试环境
      case 'test':
        url = `${process.env.VUE_APP_TEST}/websocket-simple`;
        break;
      // 开发环境
      case 'development':
        url = `${process.env.VUE_APP_PRO}/websocket-simple`;
        // url = `${process.env[param]}/websocket-simple`;
        break;
      default:
        // console.error('process.env.NODE_ENV:有误');
        break;
    }
  }
  // url = process.env.VUE_APP_TEST;
  // url = `${process.env.VUE_APP_TEST}/websocket-simple`;
  // url = 'http://192.168.96.141:8080/websocket-simple';
  return url;
}

export default getUrl;
