'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"http://mac"',
  LOGIN_URL: '"http://mac"', //门户登录测试环境
  DOWNLOAD_URL: '"http://mac"',
});
