import request from '@/utils/request'
import {
  IDurationActualrepayment,
  IDurationBuyout,
  IDurationCompensatory,
  IDurationDeduction,
  IDurationLoan,
  IDurationMaturity
} from './types'

// 累计放款表
export const getDurationLoan = (params: IDurationLoan) =>
  request({
    url: '/finance/duration/loan',
    method: 'post',
    data: params
  })

// 导出放款表
export const exportLoan = (params: IDurationLoan) =>
  request({
    url: '/finance/duration/exportLoan',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 累计到期表
export const getDurationMaturity = (params: IDurationMaturity) =>
  request({
    url: '/finance/duration/maturity',
    method: 'post',
    data: params
  })

// 导出到期表
export const exportMaturity = (params: IDurationMaturity) =>
  request({
    url: '/finance/duration/exportMaturity',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 导出实还表
export const exportActualRepayment = (params: IDurationActualrepayment) =>
  request({
    url: '/finance/duration/exportActualRepayment',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 查询实还表
export const getActualrepayment = (params: IDurationActualrepayment) =>
  request({
    url: '/finance/duration/actualrepayment',
    method: 'post',
    data: params
  })

// 导出减免表
export const exportDeduction = (params: IDurationDeduction) =>
  request({
    url: '/finance/duration/exportDeduction',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 查询减免表
export const getDeduction = (params: IDurationDeduction) =>
  request({
    url: '/finance/duration/deduction',
    method: 'post',
    data: params
  })

// 导出代偿表
export const exportCompensatory = (params: IDurationCompensatory) =>
  request({
    url: '/finance/duration/exportCompensatory',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 查询代偿表
export const getCompensatory = (params: IDurationCompensatory) =>
  request({
    url: '/finance/duration/compensatory',
    method: 'post',
    data: params
  })

// 导出买断表
export const exportBuyout = (params: IDurationBuyout) =>
  request({
    url: '/finance/duration/exportBuyout',
    method: 'post',
    responseType: 'blob',
    data: params
  })

// 查询买断表
export const getbuyout = (params: IDurationBuyout) =>
  request({
    url: '/finance/duration/buyout',
    method: 'post',
    data: params
  })
