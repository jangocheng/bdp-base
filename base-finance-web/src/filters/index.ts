// Set utils function parseTime to filter
import {IEnmu} from '@/enmu.ts'

export { parseTime } from '@/utils'

// 文章状态过滤器
export const articleStatusFilter = (status: string) => {
  const statusMap: { [key: string]: string } = {
    published: 'success',
    draft: 'info',
    deleted: 'danger'
  }
  return statusMap[status]
}

// Filter to uppercase the first character
export const uppercaseFirstChar = (str: string) => {
  return str.charAt(0).toUpperCase() + str.slice(1)
}

/**
 * 枚举过滤器，方便在表格中显示
 * @param str
 * @param enmuArr
 */
export const enmuFilter = (str: string, enmuArr: IEnmu): (string | null) => {
  const res: any = enmuArr.list.find((ele) => {
    return ele.key === str
  })
  if (res) return res.value
  return null
}
