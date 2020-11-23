/**
 * 🍹排序
 * @param arr 一维数组
 * @param key 排序的key
 * @returns {*}
 */
function bubbleSort(arr, key) {
  if (Array.isArray(arr)) {
    // 获取数组长度
    let tail = arr.length - 1;
    let i;
    // let isSwap = false;
    for (i = 0; i < tail; tail--) {
      for (var j = tail; j > i; j--) {
        const arrj1 = arr[j - 1][key];
        const arrj = arr[j][key];
        //第一轮, 先将最小的数据冒泡到前面
        // arrj1 < arrj && (isSwap = true) && swap(j, j - 1, arr);
        arrj1 < arrj && swap(j, j - 1, arr);
      }
      i++;
      for (j = i; j < tail; j++) {
        const arrj1 = arr[j + 1][key];
        const arrj = arr[j][key];
        //第二轮, 将最大的数据冒泡到后面
        // arrj < arrj1 && (isSwap = true) && swap(j, j + 1, arr);
        arrj < arrj1 && swap(j, j + 1, arr);
      }
    }
    return arr;
  }
}

/**
 * 备份数据--配合排序方法使用
 * @param {Number} i
 * @param {Number} j
 * @param {*} array
 */
function swap(i, j, array) {
  var temp = array[j];
  array[j] = array[i];
  array[i] = temp;
}

module.exports = bubbleSort;
