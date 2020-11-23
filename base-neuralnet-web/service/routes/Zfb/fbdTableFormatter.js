const Datejs = require('../../utils/DateJs');

// 不需要处理的list
const NO_DEALWITH_KEY_LIST = [
  'bizDate',
  'bizDate2',
  'targetType',
  'sourceType',
  'payType',
  'popuChannel',
  'payWays',
  'quotaStatus',
  'sjtType',
  'sjtType2',
];

function toThousands(num) {
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

/**
 * 百分比保存2位
 * @return {string}
 */
function PercentageFormat(str) {
  return Number(str * 100).toFixed(2);
}

/**
 * 支付宝 数据 格式化
 * @param arr 源数组
 * @param isTable true则代表是得返回给table所用，需要进行千分位处理，false得返回给图表所用，不能进行千分位处理
 * @param dateType 时间日期，day跟week、month格式不一样
 * @returns {*}
 */
function zfbTableFormatter(arr, { isTable = true, dateType = 'day' }) {
  try {
    if (Array.isArray(arr) && arr.length > 0) {
      arr.forEach(ele => {
        for (const key in ele) {
          if (ele.hasOwnProperty(key)) {
            // 对日期格式进行过滤
            if (key === 'bizDate') {
              switch (dateType) {
                case 'day':
                  ele[key] = Datejs.DateFormatterYMD(ele[key]);
                  break;
                case 'week':
                  ele[key] = ele[key].replace('/', ' ~ ');
                  break;
                case 'month':
                  if (!ele[key].includes('-')) {
                    ele[key] = `${ele[key].slice(0, 4)}-${ele[key].slice(
                      4,
                      6,
                    )}`;
                  }
                  break;
              }
            }
            if (NO_DEALWITH_KEY_LIST.indexOf(key) < 0) {
              //判断当前obj[key]是否需要处理
              /**
               * 正则匹配，判断当前key 在末尾是否包含 Rate | Ratio
               * 是 ： 需要保留两位小数切转换成百分比
               * 否 ： 则四舍五入后进行千分位处理
               */
              if (key.endsWith('Rate') || key.endsWith('Ratio')) {
                ele[key] = isTable
                  ? `${PercentageFormat(ele[key])}%`
                  : PercentageFormat(ele[key]);
              } else {
                ele[key] = isTable
                  ? toThousands(Number(ele[key]).toFixed())
                  : Number(ele[key]).toFixed();
              }
            }
          }
        }
      });
    }
    return arr;
  } catch (e) {
    console.log('zfbTableFormatter', e);
  }
}

module.exports = { zfbTableFormatter, PercentageFormat };
