# 项目简介

npm use v10.15.0
> 神经网络查询系统
>
> > 技术选型:前端使用`vue`框架实现 UI 层,搭配`element` 三方组件库完成,用`koa2`实现 node 中间层，用来处理数据与接口合并转发。
@(功能列表)

- [x] 神经网络
  - [x] 同一设备登录用户查询
  - [x] 客户一度人脉逾期查询
- [x] 业务大盘数据
  - [x] 实时注册数据
  - [x] 实时进件数据
  - [x] 推广引流(渠道)
  - [x] 推广引流(时间)
  - [x] 支付提现
  - [x] 门店审核
  - [x] 商户审核
- [x] 支付宝日报
  - [x] 进件注册
  - [x] 支付提现
  - [x] 支付流水
  - [x] 商城支付
  - [x] 电商收入

## 目录结构

```目录结构
├── README.md
├── babel.config.js
├── config
│   ├── dev.env.js  //开发环境的请求配置文件
│   ├── prod.env.js //生产环境的请求配置文件
│   └── index.js
├── package.json
├── service         //node中间层
│   ├── dist        //静态资源目录
│   ├── config      //服务相关配置
│   ├── util        //工具类
│   ├── routes      //接口模块
│   └── app.js    //根启动文件
├── public
│   └── index.html
├── src
│   ├── api         //接口模块
│   ├── assets      //全局静态资源
│   ├── components  //基础组件
│   ├── utils       //工具函数
│   ├── main.js
│   ├── router.js   //router相关
│   ├── store       //vuex相关
│   ├── views       //视图层
│   └── App.vue     //入口文件
└── favicon.ico
```

## 命名规范

> RT 是 realtime 的简写，即为实时 
>
> 状态码：200:正确 、401:非法、 403:没有权限

## 资料

### BASE_URL

- 测试环境：http://master

- 生产环境：http://master

## 访问

- 测试环境 http://master

## Project setup

```bash
npm install
```

### Compiles and hot-reloads for development

```bash
npm run serve
```

### Compiles and minifies for production

```打包
npm run build
```

### Run your testsCompiles and minifies for test

```测试
npm run test
```
