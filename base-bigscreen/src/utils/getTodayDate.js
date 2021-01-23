/**
 * 公有方法：获取前num天的日期
 *
 * @param {Number} num 自动向上取整 传1指当前
 * @param {boolean} order true是日期从大到小，false是从小到大
 * @returns MMdd
 */
function getTodayDate(num, order = false) {
  const temNum = Math.ceil(num);
  let result;
  const arrDate = [];
  for (let i = 0; i < temNum; i++) {
    const day = i * 24 * 60 * 60 * 1000;
    const date = new Date(new Date().getTime() - day);
    const currMonth = new Date(date).getMonth() + 1;
    const currDay = new Date(date).getDate();
    result = `${currMonth.toString().length < 2 ? `0${currMonth}` : currMonth}${
      currDay.toString().length < 2 ? `0${currDay}` : currDay
    }`;
    if (order) {
      arrDate.push(result);
    } else {
      arrDate.unshift(result);
    }
  }
  return arrDate;
}

export default getTodayDate;
