interface IEnmu {
  currkey: string,
  list: {
    key: string,
    value: string
  }[]
}

// 资金方
const fundingParty: IEnmu = {
  currkey: '',
  list: [{
    key: 'fl',
    value: 'base'
  }, {
    key: 'zgyh',
    value: '中国银行'
  }]
}

// 资产渠道类型
const channelType: IEnmu = {
  currkey: '',
  list: [{
    key: 'zd',
    value: '助贷'
  }, {
    key: 'rz',
    value: '融资'
  }, {
    key: 'zyfyl',
    value: '自营(非引流)'
  }, {
    key: 'zyyl',
    value: '自营(引流)'
  }]
}

// 渠道名称
const channelName: IEnmu = {
  currkey: '',
  list: [{
    key: 'fbd',
    value: '电子钱包'
  }, {
    key: 'fbb',
    value: '电子钱包'
  }, {
    key: 'rrjc',
    value: '人人聚财'
  }, {
    key: 'ykd',
    value: '云科贷'
  }, {
    key: 'xm',
    value: '徙木'
  }, {
    key: 'hqxy',
    value: '横琴星盈'
  }, {
    key: 'wld',
    value: '我来贷'
  }, {
    key: 'jzfq',
    value: '桔子分期'
  }]
}

// 产品类型
const productType: IEnmu = {
  currkey: '',
  list: [{
    key: 'xfd',
    value: '消费贷'
  }, {
    key: 'zd',
    value: '助贷'
  }, {
    key: 'xjd',
    value: '现金贷'
  }, {
    key: 'fqcp',
    value: '分期产品'
  }, {
    key: 'txcp',
    value: '提现产品'
  }]
}

// 产品子类型
const productChildType: IEnmu = {
  currkey: '',
  list: [{
    key: 'fbdzdfq',
    value: '电子钱包-账单分期'
  }, {
    key: 'fbdtx',
    value: '电子钱包-提现'
  }, {
    key: 'jsfq',
    value: '及时分期'
  }, {
    key: 'fbbzf',
    value: '电子钱包支付'
  }, {
    key: 'zdcp',
    value: '助贷产品'
  }, {
    key: 'ysd',
    value: '优速贷'
  }, {
    key: 'zxcp',
    value: '薪资产品'
  }, {
    key: 'yxd',
    value: '优享贷'
  }]
}

// 还款类型
const payType: IEnmu = {
  currkey: '',
  list: [{
    key: 'tqjq',
    value: '提前结清'
  }, {
    key: 'tqhk',
    value: '提前还款'
  }, {
    key: 'qt',
    value: '其他'
  }]
}

// 还款状态
const payStatus: IEnmu = {
  currkey: '',
  list: [{
    key: 'zchk',
    value: '正常还款'
  }, {
    key: 'yqhk',
    value: '逾期还款'
  }, {
    key: 'tqhk',
    value: '提前还款'
  }, {
    key: 'md',
    value: '买断'
  }, {
    key: 'dc',
    value: '代偿'
  }]
}

// 十四级分类
const overdueGrade: IEnmu = {
  currkey: '',
  list: [{
    key: 'C',
    value: 'C'
  }, {
    key: 'M1',
    value: 'M1'
  }, {
    key: 'M2',
    value: 'M2'
  }, {
    key: 'M3',
    value: 'M3'
  }, {
    key: 'M4',
    value: 'M4'
  }, {
    key: 'M5',
    value: 'M5'
  }, {
    key: 'M6',
    value: 'M6'
  }, {
    key: 'M7',
    value: 'M7'
  }, {
    key: 'M8',
    value: 'M8'
  }, {
    key: 'M9',
    value: 'M9'
  }, {
    key: 'M10',
    value: 'M10'
  }, {
    key: 'M11',
    value: 'M11'
  }, {
    key: 'M12',
    value: 'M12'
  }, {
    key: 'M12+',
    value: 'M12+'
  }]
}

const deductionType: IEnmu = {
  currkey: '',
  list: [{
    key: 'sdjm',
    value: '手动减免'
  }, {
    key: 'yhqjm',
    value: '优惠券减免'
  }, {
    key: 'zdjm',
    value: '自动减免'
  }]
}

export {
  IEnmu,
  fundingParty,
  channelType,
  channelName,
  productType,
  productChildType,
  payType,
  payStatus,
  overdueGrade,
  deductionType
}
