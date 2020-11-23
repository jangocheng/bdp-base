import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import {getToken} from '@/utils/cookies'

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  timeout: 5000,
  withCredentials: true
})

// 非本地运行情况下需要做登录态判断
if (process.env.NODE_ENV !== 'development') {
  service.interceptors.request.use(
    config => {
      if (getToken()) {
        config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
      } else {
        MessageBox.alert('缺失Token信息', '确定登出', {
          confirmButtonText: '重新登录',
          type: 'warning'
        }).then(() => {
          // 判断当前环境
          // @ts-ignore
          window.location.href = process.env.VUE_APP_LOGIN_URL
        })
      }
      return config
    },
    error => {
      console.log(error)
      Promise.reject(error)
    }
  )
}

// Response interceptors
service.interceptors.response.use(
  (response) => {
    const res = response.data
    // 判断是否是需要下载的请求
    if (response.config && response.config.responseType === 'blob') {
      const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' }) // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet这里表示xlsx类型
      let filename = 'excel.xls'
      if ('download' in document.createElement('a')) {
        const downloadElement = document.createElement('a')
        let href = ''
        if (window.URL) {
          href = window.URL.createObjectURL(blob)
        } else {
          href = (window as any).webkitURL.createObjectURL(blob)
        }
        downloadElement.href = href
        downloadElement.download = filename
        document.body.appendChild(downloadElement)
        downloadElement.click()
        if (window.URL) {
          window.URL.revokeObjectURL(href)
        } else {
          (window as any).webkitURL.revokeObjectURL(href)
        }
        document.body.removeChild(downloadElement)
      } else {
        navigator.msSaveBlob(blob, filename)
      }
      return
    }
    if (res.code !== 0) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return response.data
    }
  },
  (error) => {
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

// @ts-ignore
export default service
