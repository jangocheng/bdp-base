import request from '@/utils/request'
import {IbalanceDistribution, IbalanceFivelevel, IbalanceOverdue, IbalanceUndue} from './types'

/**
 * 查询码表
 * @param params
 */
export const getCodeLibrary = (params: { codeType: string }) =>
  request({
    url: '/finance/library/selectCodeLibrary',
    method: 'post',
    data: params
  })

// 导出逾期表
export const exportOverdue = (params: IbalanceOverdue) =>
  request({
    url: '/finance/balance/exportOverdue',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 查询逾期表
export const getOverdue = (params: IbalanceOverdue) =>
  request({
    url: '/finance/balance/overdue',
    method: 'post',
    data: params
  })

// 导出未到期表
export const exportUndue = (params: IbalanceUndue) =>
  request({
    url: '/finance/balance/exportUndue',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 查询未到期表
export const getUndue = (params: IbalanceUndue) =>
  request({
    url: '/finance/balance/undue',
    method: 'post',
    data: params
  })

// 查询剩余本金分布表
export const getDistribution = (params: IbalanceDistribution) =>
  request({
    url: '/finance/balance/distribution',
    method: 'post',
    data: params
  })

// 查询剩余本金五级分布表
export const getFivelevel = (params: IbalanceFivelevel) =>
  request({
    url: '/finance/balance/fivelevel',
    method: 'post',
    data: params
  })
