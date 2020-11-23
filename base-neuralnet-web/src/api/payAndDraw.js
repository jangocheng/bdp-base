import request from '@/utils/request';

/**
 * 获取消费类型
 * @returns {AxiosPromise}
 */
export function getPaytypes() {
  return request({
    url: '/payAndDraw/paytypes',
    method: 'get',
  });
}

/**
 * 查询交易笔数
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param time  时间粒度
 * @param customerType  客户类型：01、内部员工| 02、社会人士| 所有
 * @param consumptionType 消费类型
 * @returns {AxiosPromise}
 */
export function getPayAmount({
  startDate,
  endDate,
  time,
  customerType,
  consumptionType,
}) {
  return request({
    url: '/payAndDraw/PayAmount',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      customerType,
      consumptionType,
    },
  });
}

/**
 * 查询交易金额
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param time  时间粒度
 * @param customerType  客户类型：01、内部员工| 02、社会人士| 所有
 * @param consumptionType 消费类型
 * @returns {AxiosPromise}
 */
export function getPaySum({
  startDate,
  endDate,
  time,
  customerType,
  consumptionType,
}) {
  return request({
    url: '/payAndDraw/PaySum',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      customerType,
      consumptionType,
    },
  });
}

/**
 * 每天最大单笔金额
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param time  时间粒度
 * @param customerType  客户类型：01、内部员工| 02、社会人士| 所有
 * @param consumptionType 消费类型
 * @returns {AxiosPromise}
 */
export function getPayMaximum({
  startDate,
  endDate,
  time,
  customerType,
  consumptionType,
}) {
  return request({
    url: '/payAndDraw/maximum',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      customerType,
      consumptionType,
    },
  });
}

/**
 * 支付类型占比
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param time  时间粒度
 * @param customerType  客户类型：01、内部员工| 02、社会人士| 所有
 * @param consumptionType 消费类型
 * @returns {AxiosPromise}
 */
export function getPayPaytype({
  startDate,
  endDate,
  time,
  customerType,
  consumptionType,
}) {
  return request({
    url: '/payAndDraw/paytype',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      customerType,
      consumptionType,
    },
  });
}

/**
 * 商品种类分布
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param time  时间粒度
 * @param customerType  客户类型：01、内部员工| 02、社会人士| 所有
 * @param consumptionType 消费类型
 * @returns {AxiosPromise}
 */
export function getPayProduct({
  startDate,
  endDate,
  time,
  customerType,
  consumptionType,
}) {
  return request({
    url: '/payAndDraw/product',
    method: 'get',
    params: {
      startDate,
      endDate,
      time,
      customerType,
      consumptionType,
    },
  });
}
