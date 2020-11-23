/* eslint-disable import/prefer-default-export */
import request from '@/utils/request';

/**
 * 各业务部门推送用户数据
 */
export function syncData() {
  return request({
    url: '/dailySalary/syncData',
    method: 'get',
  });
}

/**
 * 各业务部门交易金额
 */
export function consumeAmountData() {
  return request({
    url: '/dailySalary/consumeAmountData',
    method: 'get',
  });
}

/**
 * 各业务部门交易次数
 */
export function consumeCountData() {
  return request({
    url: '/dailySalary/consumeCountData',
    method: 'get',
  });
}

/**
 * 整体情况
 */
export function summaryData() {
  return request({
    url: '/dailySalary/summaryData',
    method: 'get',
  });
}

/**
 * 各次业务部门完成率
 */
export function completeData() {
  return request({
    url: '/dailySalary/completeData',
    method: 'get',
  });
}
