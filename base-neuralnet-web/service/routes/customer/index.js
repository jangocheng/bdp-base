/*
 * @Description: 客户查询相关模块
 */

// const request = require('request');
const { request } = require('../../utils/request');

class Customer {
  /**
   * 获取view层传递的数据(电话号码)
   * @param {*} ctx
   * @param {*} next
   */
  static async queryCustomer(ctx, next) {
    await next();
    try {
      // 处理数据
      // 定义一个类型枚举
      const emnu = {
        phone: 0, //设备
        customer: 1, //客户
      };
      let nodeData = []; //view层 graph图所需节点数据
      let nodelinks = []; //view层 graph图所需节点关系数据
      // 判断是否有值
      if (!ctx.body.data) {
        return;
      }
      const currData = ctx.body.data;
      // 遍历java层返回的数据
      // 组装节点数据
      for (let i = 0, len = currData.nodes.length; i < len; i++) {
        const ele = currData.nodes[i];
        nodeData.push({
          name: ele.id.toString(),
          category: emnu[ele.group].toString(),
          data: {
            label: ele.label.toString(), //用户名称
            group: ele.group, //用户角色
            staffStatus: ele.data.staffStatus || '', //用户工作状态  mall内部员工|社会人士 TODO:此处应该给一个枚举
            overdueStatus: ele.data.overdueStatus || '', //逾期状态
          },
        });
      }
      // 组装nodelinks数据
      for (let i = 0, len = currData.edges.length; i < len; i++) {
        const ele = currData.edges[i];
        nodelinks.push({
          source: ele.from.toString(),
          target: ele.to.toString(),
          value: ele.label,
          data: ele.data,
        });
      }
      // TODO:应该添加一个处理返回值的中间件
      console.log(nodelinks);
      ctx.body = {
        code: 200,
        data: {
          data: nodeData,
          links: nodelinks,
        },
      };
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }
  /**
   * 转发数据请求到第三方服务
   * @param {*} ctx
   * @param {*} next
   */
  static async reqCustomer(ctx, next) {
    try {
      const param = {
        base: 'cnw',
        path: `/customer/loadSuspiciousCustomersByPhoneNumber?phoneNumber=${
          ctx.query.phoneNumber
        }`,
      };
      const data = await request(param);
      if (data) {
        ctx.body = data;
      } else {
        ctx.throw(500);
      }
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }
}

module.exports = Customer;
