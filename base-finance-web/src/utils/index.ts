/**
 * 解析时间到字符串
 * @param time
 * @param cFormat
 */
export const parseTime = (
  time?: object | string | number,
  cFormat?: string
): string | null => {
  if (time === undefined) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date: Date
  if (typeof time === 'object') {
    date = time as Date
  } else {
    if (typeof time === 'string' && /^[0-9]+$/.test(time)) {
      time = parseInt(time)
    }
    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj: { [key: string]: number } = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  return format.replace(/{([ymdhisa])+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      return '0' + value
    }
    return String(value) || '0'
  })
}

//
/**
 * 使用filterKeys数组格式化和过滤json数据
 * @param filterKeys
 * @param jsonData
 */
export const formatJson = (filterKeys: any, jsonData: any) =>
  jsonData.map((data: any) => filterKeys.map((key: string) => {
    if (key === 'timestamp') {
      return parseTime(data[key])
    } else {
      return data[key]
    }
  }))

//
/**
 * 检查元素是否有class
 * @param ele
 * @param className
 */
export const hasClass = (ele: HTMLElement, className: string) => {
  return !!ele.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'))
}

//
/**
 * 给元素增加class
 * @param ele
 * @param className
 */
export const addClass = (ele: HTMLElement, className: string) => {
  if (!hasClass(ele, className)) ele.className += ' ' + className
}

//
/**
 * 移除元素的class
 * @param ele
 * @param className
 */
export const removeClass = (ele: HTMLElement, className: string) => {
  if (hasClass(ele, className)) {
    const reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
    ele.className = ele.className.replace(reg, ' ')
  }
}

// Toggle class for the selected element
export const toggleClass = (ele: HTMLElement, className: string) => {
  if (!ele || !className) {
    return
  }
  let classString = ele.className
  const nameIndex = classString.indexOf(className)
  if (nameIndex === -1) {
    classString += '' + className
  } else {
    classString =
      classString.substr(0, nameIndex) +
      classString.substr(nameIndex + className.length)
  }
  ele.className = classString
}

// 获取上个月月份
export const getdatatime = () => {
  const nowdays = new Date()
  let year: number | string = nowdays.getFullYear()
  let month: number | string = nowdays.getMonth()
  if (month === 0) {
    month = 12
    year = year - 1
  }
  if (month < 10) {
    month = `0${month}`
  }
  return `${year}/${month}`// 上个月
}

/**
 * 获取当前时间
 * @param param true，year/month/day | false, year/month
 */
export const getCurTime = (param: boolean = true) => {
  const nowdays = new Date()
  let year: number | string = nowdays.getFullYear()
  let month: number | string = nowdays.getMonth() + 1
  let day: number | string = nowdays.getDate()
  if (month < 10) {
    month = `0${month}`
  }
  if (day < 10) {
    day = `0${day}`
  }

  if (param) return `${year}/${month}/${day}`
  return `${year}/${month}`
}
