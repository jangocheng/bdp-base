# 大屏报表监控系统

## 技术选型
nvm use v12.18.3

> `vue-cli3.0`、`vue全家桶`、`less`
>
> 代码规范在使用 Aribnb 为基础的情况下略微进行了修改，具体可看`package.json`
>
> 格式化 vscode 编辑器请使用`prettier`插件，然后在 根目录 添加`.prettier`文件，对应配置如下
>
> 使用`json-server`做 mock 服务
>
> - `webSocket`使用`sockjs-client`、`stompjs`

```webSocket

     /**
       * 相关技术文档
       * stompjs文档
       * https://stomp-js.github.io/guide/stompjs/2018/06/28/using-stompjs-v5.html
       * https://segmentfault.com/a/1190000006617344
       */

```

## 访问

- 测试环境 master
- 生成环境 bsbi.fujfu.com

### .prettier

```bash
{
    "semi": true,
    "singleQuote": true,
    "trailingComma": "all",
    "bracketSpacing": true,
    "jsxBracketSameLine": false,
    "arrowParens": "avoid",
    "requirePragma": false,
    "proseWrap": "preserve",
    "alwaysParen": "always"
}
```

### 文件目录

```bash
│  .env
│  .gitignore
│  .prettierrc  //格式化风格配置文件
│  babel.config.js
│  package.json  //项目描述文件
│  README.md  //工程说明
│  vue.config.js  //自定义的配置文件
│  yarn.lock
├─api_server
│  └─db.json    //mock数据文件。
├─public    //公共文件，如index.html入口文件
│  │  favicon.ico
│  │  index.html
│  │  manifest.json
│  │  robots.txt
│  │
│  └─img
│      └─icons
└─src   //编码资源
    │  App.vue
    │  main.js  //主函数入口文件
    │  registerServiceWorker.js
    │  router.js
    │  store.js
    ├─assets
    │      logo.png
    ├─components    //功能级组件
    └─views     //页面级组件

```

## Project setup

```bash
yarn install
```

### 代码规范检查格式化

```bash
yarn run lint
```

### 本地热更新服务

```bash
yarn run serve
```

### 生产环境打包

```bash
yarn run build
```

### 测试环境打包

```bash
yarn run test
```

### mock

```bash

cd api_server

json-server db.json

```
v1.0.0