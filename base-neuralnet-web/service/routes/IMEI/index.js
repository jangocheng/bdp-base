/*

 * @Description: IEMI设备号查询相关模块
 */

const { loadCustomerUsedSameDevice } = require('../../api/imei');

class IMEI {
  /**
   * 获取UI层传过来数据
   * @param {*} ctx
   * @param {*} next
   */
  static async queryIMEI(ctx, next) {
    try {
      await next();
      // 处理数据
      // 定义一个类型枚举
      const emnu = {
        device: 0, //设备
        customer: 1, //客户
      };
      let nodeData = []; //view层 graph图所需节点数据
      let nodelinks = []; //view层 graph图所需节点关系数据
      // 判断是否有值
      if (!ctx.body.data) {
        // TODO:输出日志
        return;
      }
      const currData = ctx.body.data;
      console.log(currData);
      // 遍历java层返回的数据
      // 组装nodeData数据
      for (let i = 0, len = currData.nodes.length; i < len; i++) {
        const ele = currData.nodes[i];
        nodeData.push({
          name: ele.id.toString(),
          label: ele.label.toString(),
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
      ctx.body = {
        code: 200,
        data: {
          data: nodeData,
          links: nodelinks,
        },
      };
    } catch (error) {
      console.error('error', error);
      ctx.body = {
        code: 400,
        msg: '服务有误',
        data: [],
      };
      return ctx;
    }
  }
  /**
   * 转发数据请求到第三方服务
   * @param {*} ctx
   */
  static async reqIMEI(ctx) {
    try {
      let res;
      res = await loadCustomerUsedSameDevice(ctx.query.imei);
      ctx.body = res;
    } catch (err) {
      ctx.app.emit('error', ctx, err);
    }
  }
}

module.exports = IMEI;
