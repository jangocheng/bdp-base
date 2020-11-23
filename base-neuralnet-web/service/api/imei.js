const { request } = require('../utils/request');

/**
 * 同一设备登录用户查询接口;
 */
function loadCustomerUsedSameDevice(imei) {
  const Param = {
    base: 'cnw',
    path: `/customer/loadCustomerUsedSameDevice?imei=${imei}`,
  };
  return request(Param);
}

module.exports = { loadCustomerUsedSameDevice };
