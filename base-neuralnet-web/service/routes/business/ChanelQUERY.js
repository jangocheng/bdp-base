/*
 * 按 渠道过滤 的类
 *
 */

class ChanelQUERY {
  /**
   *
   * @param {Array} arr 目标数组
   * @param {String} channel 系统名称
   * @param {String} time 时间粒度
   * @param {Boolean} isTable 是否是报表，报表不需要聚合渠道数据
   */
  static channelFilter(arr, channel, time, isTable) {
    let res = []; //临时数组
    try {
      if (!isTable) {
        arr.forEach(item => {
          const collect = '所有';
          // 当前如果是‘天’颗粒度，那么终端不需要汇总成所有
          let curOstype = time === 'day' ? item.channel : collect;
          if (channel === collect) {
            item.channel = curOstype;
            res.push(item);
          } else if (item.channel === channel) {
            res.push(item);
          }
        });
      } else {
        res = arr;
      }
    } catch (error) {
      console.log(error);
    }
    return res;
  }
}

module.exports = ChanelQUERY;
