const { request } = require('../utils/request');
const config = require('../config/index');

/**
 * 获取token验证信息
 */
function checkToken(token) {
  const param = {
    status: true,
    path: `${config.tokenUrl}/sso/token/loadPayload?token=${token}`,
  };
  return request(param);
}

module.exports = { checkToken };
