/*
 * @Description: token验证中间件
 */

const { checkToken } = require('../api/checkToken.js');

class CHECKTOKEN {
  static async init(ctx, next) {
    try {
      // 判断token是否存在，存在则验证，不存在即返回
      const xToken = ctx.request.header['x-token'];
      if (xToken) {
        let res = await checkToken(xToken);
        if (!res && !res.code) {
          return (ctx.body = {
            code: 500,
            msg: 'token验证失败',
          });
        }
        switch (res.code) {
          case 0:
            await next();
            break;
          case 401:
            return (ctx.body = {
              code: 401,
              msg: '非法的token',
            });
          // 黑名单 没有访问权限
          case 403:
            return (ctx.body = {
              code: 403,
              data: [],
              msg: '该账户没有访问权限',
            });
        }
      } else {
        if (process.env.NODE_ENV !== 'dev') {
          return (ctx.body = {
            code: 500,
            data: [],
            msg: '该账户登录过期,请重新登录。',
          });
        }
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }
}

module.exports = CHECKTOKEN;
