/*

 *
 * 客户类型
 *
 */

const { customerENUM } = require('../enmu');

class CustomerQUERY {
  static customerFilter(arr, customerType) {
    let res = []; //临时数组
    try {
      //   过滤相同的客户类型
      arr.forEach(item => {
        if (item.customerType === customerType) {
          res.push(item);
        }
      });
    } catch (error) {
      console.error('error', error);
      return res;
    }
    return res;
  }
}

module.exports = CustomerQUERY;
