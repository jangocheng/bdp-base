const throwErr = (code, response) => {
  let message = '请求错误';
  switch (code) {
    case 400:
      message = '请求错误';
      break;
    case 401:
      message = '未授权，请登录';
      break;
    case 403:
      message = '拒绝访问';
      break;
    case 404:
      message = `请求地址出错: ${response.config.url}`;
      break;
    case 408:
      message = '请求超时';
      break;
    case 500:
      message = '服务器内部错误';
      break;
    case 501:
      message = '服务未实现';
      break;
    case 502:
      message = '网关错误';
      break;
    case 503:
      message = '服务不可用';
      break;
    case 504:
      message = '网关超时';
      break;
    case 505:
      message = 'HTTP版本不受支持';
      break;
    default:
  }
  return message;
};

export default throwErr;
