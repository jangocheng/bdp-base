/* eslint-disable import/extensions */
/* eslint-disable import/no-unresolved */
/* eslint-disable space-before-function-paren */
/* eslint-disable no-var */
/* eslint-disable prefer-arrow-callback */
/* eslint-disable func-names */

const Mock = require('mockjs');
const util = require('./util'); //自定义工具模块

//返回一个函数
module.exports = function(app) {
  //监听http请求
  app.get('/dailySalary/syncData', function(rep, res) {
    //每次响应请求时读取mock data的json文件
    //util.getJsonFile方法定义了如何读取json文件并解析成数据对象
    var json = util.getJsonFile('./syncData.json');
    //将json传入 Mock.mock 方法中，生成的数据返回给浏览器
    res.json(Mock.mock(json));
  });
};
