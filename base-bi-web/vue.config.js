/* eslint-disable global-require */
const path = require('path');
const objOS = require('os');

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  /** 区分打包环境与开发环境
   * process.env.NODE_ENV==='production'  (打包环境)
   * process.env.NODE_ENV==='development' (开发环境)
   * baseUrl: process.env.NODE_ENV==='production'?"https://cdn.didabisai.com/front/":'front/',
   */

  // 项目部署的基础路径

  // 我们默认假设你的应用将会部署在域名的根部,

  // 例如 https://www.my-app.com/

  // 如果你的应用部署在一个子路径下，那么你需要在这里

  // 指定子路径。比如将你的应用部署在

  // https://www.foobar.com/my-app/

  // 那么将这个值改为 '/my-app/'

  baseUrl: process.env.NODE_ENV === 'production' ? './' : '/',

  // 当运行 vue-cli-service build 时生成的生产环境构建文件的目录。注意目标目录在构建之前会被清除 (构建时传入 --no-clean 可关闭该行为)。
  outputDir: 'dist',

  lintOnSave: true,

  runtimeCompiler: false, // babel-loader默认会跳过`node_modules`依赖. // 通过这个选项可以显示转译一个依赖

  transpileDependencies: [
    /* string or regex */
  ], // 是否为生产环境构建生成sourceMap?

  productionSourceMap: true, // 调整内部的webpack配置. // see https://github.com/vuejs/vue-cli/blob/dev/docs/webpack.md

  //添加别名配置
  chainWebpack: config => {
    config.resolve.alias
      .set('@', resolve('src'))
      .set('public', resolve('public'));
    config.optimization.delete('splitChunks');
  },

  configureWebpack: () => {}, // CSS 相关选项

  css: {
    // 将组件内部的css提取到一个单独的css文件（只用在生产环境）

    // 也可以是传递给 extract-text-webpack-plugin 的选项对象

    extract: true, // 允许生成 CSS source maps?

    sourceMap: false, // pass custom options to pre-processor loaders. e.g. to pass options to // sass-loader, use { sass: { ... } }

    loaderOptions: {}, // Enable CSS modules for all css / pre-processor files. // This option does not affect *.vue files.

    modules: false,
  }, // use thread-loader for babel & TS in production build // enabled by default if the machine has more than 1 cores

  parallel: objOS.cpus().length > 1, // PWA 插件相关配置 // see https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-pwa

  pwa: {}, // configure webpack-dev-server behavior

  devServer: {
    open: process.platform === 'darwin',
    disableHostCheck: true,
    host: '0.0.0.0',
    port: 8088,
    https: false,
    hotOnly: false, // See https://github.com/vuejs/vue-cli/blob/dev/docs/cli-service.md#configuring-proxy
    proxy: {
      '/api': {
        target: 'http://master',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '',
        },
      },
    }, // string | Object
    before: app => {},
  }, // 第三方插件配置

  pluginOptions: {
    // ...
  },
};
