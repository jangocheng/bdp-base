
/**
 * 终端设备过滤
 */
const { osTypeENUM } = require('../enmu');
class OSTypeQUERY {
  /**
   *
   * @param {Array} arr 目标数组
   * @param {String} osType 系统名称
   * @param {String} time 时间粒度
   * @param {Boolean} isTable 是否是报表，报表不需要聚合渠道数据
   */
  static osTypeFilter(arr, osType, time, isTable) {
    let res = []; //临时数组
    try {
      arr.forEach(item => {
        // 当前如果是‘天’颗粒度，那么终端不需要汇总成所有
        let curOstype;
        //============isTable参数专门为 前端的表格显示服务，没有确定终端情况下 不需要聚合数据
        if (isTable) {
          curOstype = item.osType;
          item.osType = osTypeENUM[curOstype];
          res.push(item);
        } else {
          curOstype = time === 'day' ? item.osType : osType;
          if (osType === '05') {
            item.osType = osTypeENUM[curOstype];
            res.push(item);
          } else if (item.osType === osType) {
            item.osType = osTypeENUM[osType];
            res.push(item);
          }
        }
      });
    } catch (error) {
      res = [];
      console.log(error);
    }
    return res;
  }
}

module.exports = OSTypeQUERY;
