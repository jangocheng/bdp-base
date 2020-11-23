/*
 * @Description: log输出中间件
 */

const logUtil = require('../utils/log_util');

module.exports = async (ctx, next) => {
  const start = new Date(); // 响应开始时间
  let ms; //响应间隔时间
  try {
    await next();
    // 进入到下一个中间件
    ms = new Date() - start;
    logUtil.logResponse(ctx, ms);
  } catch (error) {
    ms = new Date() - start;
    // 记录异常日志
    logUtil.logError(ctx, error, ms);
  }
};
