var development_env = require('./dev.env');
var test_env = require('./test.env');
var prod_env = require('./prod.env');

//根据不同的NODE_ENV，输出不同的配置对象，默认输出development的配置对象
module.exports = {
  development: development_env,
  test: test_env,
  prod: prod_env,
  dev: development_env,
}[process.env.NODE_ENV || 'development'];
