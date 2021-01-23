/* eslint-disable import/prefer-default-export */
import request from '@/utils/request';

/**
 * 推送用户数据
 */
export function syncData() {
  return request({
    url: '/dailySalary/syncData',
    method: 'get',
  });
}

/**
 * 交易金额
 */
export function consumeAmountData() {
  return request({
    url: '/dailySalary/consumeAmountData',
    method: 'get',
  });
}

/**
 * 交易次数
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
 * 完成率
 */
export function completeData() {
  return request({
    url: '/dailySalary/completeData',
    method: 'get',
  });
}
