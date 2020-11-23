const Koa = require('koa');
const app = new Koa();
const path = require('path'); //路径管理
const json = require('koa-json');
const onerror = require('koa-onerror');
const bodyparser = require('koa-bodyparser');
const logger = require('koa-logger');
const cors = require('koa2-cors');
const Static = require('koa-static'); //静态资源服务插件
const router = require('./routes');

// logger
const logsUtil = require('./utils/logs.js');

// error handler
onerror(app);
const staticPath = '../dist';

app.use(
  bodyparser({
    enableTypes: ['json', 'form', 'text'],
  }),
);
app.use(json());
app.use(cors());
app.use(logger());
app.use(Static(path.join(__dirname, staticPath)));

app.use(async (ctx, next) => {
  const start = new Date(); // 响应开始时间
  let intervals; // 响应间隔时间
  try {
    await next();
    intervals = new Date() - start;
    logsUtil.logResponse(ctx, intervals); //记录响应日志
  } catch (error) {
    intervals = new Date() - start;
    logsUtil.logError(ctx, error, intervals); //记录异常日志
  }
});

// error-handling
app.on('error', (ctx, err) => {
  logsUtil.logError(ctx, err);
});

// routes
app.use(router.routes(), router.allowedMethods());

module.exports = app;
