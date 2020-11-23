/**
 * 测试环境的配置内容
 * 192.168.99.56 sit SSO
 * 192.168.99.147    UAT
 */

module.exports = {
  env: 'http://mac', //大盘数据地址
  zfb: 'http://mac', //支付宝日报数据接口
  COMPLEXNETWORKS_API: 'http://mac',
  tokenUrl: 'http://192.168.99.147:8080', //token验证地址
};
