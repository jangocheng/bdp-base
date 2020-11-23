export function parseTime(time, cFormat) {
  if (arguments.length === 0) return null;
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
  let date;
  if (typeof time === 'object') {
    date = time;
  } else {
    if (`${time}`.length === 10) time = parseInt(time) * 1000;
    date = new Date(time);
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay(),
  };
  // noinspection RegExpSingleCharAlternation
  return format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key];
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value];
    }
    if (result.length > 0 && value < 10) {
      value = `0${value}`;
    }
    return value || 0;
  });
}

export function formatTime(time, option) {
  time = +time * 1000;
  const d = new Date(time);
  const now = Date.now();

  const diff = (now - d) / 1000;

  if (diff < 30) {
    return '刚刚';
  } else if (diff < 3600) {
    // less 1 hour
    return `${Math.ceil(diff / 60)}分钟前`;
  } else if (diff < 3600 * 24) {
    return `${Math.ceil(diff / 3600)}小时前`;
  } else if (diff < 3600 * 24 * 2) {
    return '1天前';
  }
  if (option) {
    return parseTime(time, option);
  }
  return `${d.getMonth() +
    1}月${d.getDate()}日${d.getHours()}时${d.getMinutes()}分`;
}

export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

/**
 * 深拷贝方法--可拷贝函数表达式
 * @param {any} obj  Object|Date|Array 被拷贝的目标对象
 */
export function objClone(obj) {
  let copy;

  // Handle the 3 simple types, and null or undefined
  if (obj === null || typeof obj !== 'object') return obj;

  // 是否是时间对象
  if (obj instanceof Date) {
    copy = new Date();
    copy.setTime(obj.getTime());
    return copy;
  }

  // 判断是否是数组
  if (obj instanceof Array) {
    copy = [];
    for (let i = 0, len = obj.length; i < len; i++) {
      copy[i] = objClone(obj[i]);
    }
    return copy;
  }

  // 是否是原始对象
  if (obj instanceof Object) {
    copy = {};
    for (const attr in obj) {
      if (obj.hasOwnProperty(attr)) copy[attr] = objClone(obj[attr]);
    }
    return copy;
  }

  throw new Error('传入类型有误');
}

/**
 * 双冒泡排序 从大到小
 * @param arr
 * @returns {*}
 */
export function bubbleSort2(arr) {
  const swap = (i, j, array) => {
    const temp = array[j];
    array[j] = array[i];
    array[i] = temp;
  };

  if (Array.isArray(arr)) {
    // 获取数组长度
    let tail = arr.length - 1;
    let i;
    for (i = 0; i < tail; tail--) {
      for (let j = tail; j > i; j--) {
        const arrj1 = arr[j - 1];
        const arrj = arr[j];
        //第一轮, 先将最小的数据冒泡到前面
        arrj1 < arrj && swap(j, j - 1, arr);
      }
      i++;
      for (let j = i; j < tail; j++) {
        const arrj1 = arr[j + 1];
        const arrj = arr[j];
        //第二轮, 将最大的数据冒泡到后面
        arrj < arrj1 && swap(j, j + 1, arr);
      }
    }
    return arr;
  }
}

/**
 * 将数字格式化为k,m
 * @param num 数字
 * @param digits 保留到几位小数
 * @returns {string}
 */
export function numFormatter(num, digits) {
  const si = [{ value: 1, symbol: '' }, { value: 1e3, symbol: 'K' }];
  // {
  //   value: 1e6,
  //   symbol: 'M',
  // },
  // {
  //   value: 1e9,
  //   symbol: 'G',
  // },
  // {
  //   value: 1e12,
  //   symbol: 'T',
  // },
  // {
  //   value: 1e15,
  //   symbol: 'P',
  // },
  // {
  //   value: 1e18,
  //   symbol: 'E',
  // },
  const rx = /\.0+$|(\.[0-9]*[1-9])0+$/;
  let i;
  for (i = si.length - 1; i > 0; i--) {
    if (num >= si[i].value) {
      break;
    }
  }
  return (num / si[i].value).toFixed(digits).replace(rx, '$1') + si[i].symbol;
}

/**
 * 千分位
 * @param num
 * @returns {string}
 */
export function toThousands(num) {
  num = (num || 0).toString();
  let result = '';
  while (num.length > 3) {
    result = `,${num.slice(-3)}${result}`;
    num = num.slice(0, num.length - 3);
  }
  if (num) {
    result = num + result;
  }
  return result;
}
