/**
 * 根据相同时间来聚合指定数据
 * @param arr   传递的数据对象
 * @param keys  需要聚合的属性数组
 * @returns {*}
 * @constructor
 */
function MergeByTime(arr, keys) {
  if (arr.length === 0) return;
  try {
    let temObj = {};
    arr.forEach(item => {
      if (temObj[item.date]) {
        temObj[item.date].push(item);
      } else {
        temObj[item.date] = [item];
      }
    });
    let temArr = [];
    for (const key in temObj) {
      if (temObj.hasOwnProperty(key)) {
        const ele = temObj[key];
        let obj = {};
        keys.forEach(item => {
          obj = {
            date: key,
            [item]: ele.reduce((prev, current) => {
              return ~~current[item] + ~~prev;
            }, 0),
          };
        });
        temArr.push(obj);
      }
    }
    return temArr;
  } catch (error) {
    console.error('error', error);
  }
}

module.exports = MergeByTime;
