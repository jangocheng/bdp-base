const { request } = require('../utils/request');

/**
 * 查询交易笔数
 * @param customerType  // 02-社会人士，01-内部员工 注意：不传查所有渠道的数据
 * @param consumptionType //线下门店商品分期支付 注意：必填字段
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getConsumptionAmount(
  customerType,
  consumptionType,
  startDate,
  endDate,
) {
  const param = {
    path: `/business/pw/consumption/amount`,
    method: 'post',
    data: {
      customerType,
      consumptionType,
      startDate,
      endDate,
    },
  };
  return request(param);
}

/**
 * 交易金额
 * @param customerType
 * @param consumptionType
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getConsumptionSum(customerType, consumptionType, startDate, endDate) {
  const param = {
    path: `/business/pw/consumption/sum`,
    method: 'post',
    data: {
      customerType,
      consumptionType,
      startDate,
      endDate,
    },
  };
  return request(param);
}

/**
 * 最大单笔交易金额
 * @param customerType
 * @param consumptionType
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getConsumptionMaximum(
  customerType,
  consumptionType,
  startDate,
  endDate,
) {
  const param = {
    path: `/business/pw/consumption/maximum`,
    method: 'post',
    data: {
      customerType,
      consumptionType,
      startDate,
      endDate,
    },
  };
  return request(param);
}

/**
 * 笔平均交易金额
 * @param customerType
 * @param consumptionType
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getConsumptionAverage(
  customerType,
  consumptionType,
  startDate,
  endDate,
) {
  const param = {
    path: `/business/pw/consumption/average`,
    method: 'post',
    data: {
      customerType,
      consumptionType,
      startDate,
      endDate,
    },
  };
  return request(param);
}

/**
 * 支付类型占比
 * @param customerType
 * @param consumptionType
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getRatePaytype(customerType, consumptionType, startDate, endDate) {
  const param = {
    path: `/business/pw/rate/paytype`,
    method: 'post',
    data: {
      customerType,
      consumptionType,
      startDate,
      endDate,
    },
  };
  return request(param);
}

/**
 * 商品种类分布
 * @param customerType
 * @param consumptionType
 * @param startDate
 * @param endDate
 * @returns {Promise<*>|*}
 */
function getRateProduct(customerType, consumptionType, startDate, endDate) {
  const param = {
    path: `/business/pw/rate/product`,
    method: 'post',
    data: {
      customerType,
      consumptionType,
      startDate,
      endDate,
    },
  };
  return request(param);
}

/**
 * 获取消费类型
 * @returns {Promise<*>|*}
 */
function getRatePaytypes() {
  const param = {
    path: `/business/pw/paytypes`,
  };
  return request(param);
}

module.exports = {
  getConsumptionAmount,
  getConsumptionSum,
  getConsumptionMaximum,
  getConsumptionAverage,
  getRatePaytype,
  getRateProduct,
  getRatePaytypes,
};
