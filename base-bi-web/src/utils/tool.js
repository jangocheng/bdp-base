/**
 * 传入的数字千分位格式化
 * @param {Number} num
 */
export function toThousands(num) {
  const temNum = (num || 0).toString();
  let result = '';
  // 将temNum按小数点分割成前后两段
  const temArr = temNum.split('.');
  while (temArr[0].length > 3) {
    result = `,${temArr[0].slice(-3)}${result}`;
    // ',' + temArr[0].slice(-3) + result
    temArr[0] = temArr[0].slice(0, temArr[0].length - 3);
  }
  if (temArr[0]) {
    result = temArr[0] + result;
  }
  return temArr[1] ? `${result}.${temArr[1]}` : `${result}`;
}

/**
 * 根据key的层级关系获取指定的value
 *
 * getObjKey(['key1', 'key2', 'key3'], Obj) = Obj.key1.key2.key3
 *
 * @param {Array} keys   对象层级关系 [key1,key2,key3]
 * @param {Object} Obj 目标对象   obj
 * @return obj.key1.key2.key3
 *
 */
const getObjKey = (keys, Obj) =>
  keys.reduce((ele, key) => (ele && ele[key] ? ele[key] : null), Obj);

export default { toThousands, getObjKey };
