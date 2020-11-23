export interface IArticleData {
  id: number
  status: string
  title: string
  abstractContent: string
  fullContent: string
  sourceURL: string
  imageURL: string
  timestamp: string | number
  platforms: string[]
  disableComment: boolean
  importance: number
  author: string
  reviewer: string
  type: string
  pageviews: number
}

export interface IRoleData {
  key: string
  name: string
  description: string
  routes: any
}

export interface ITransactionData {
  orderId: string
  timestamp: string | number
  username: string
  price: number
  status: string
}

export interface IUserData {
  id: number
  username: string
  password: string
  name: string
  email: string
  phone: string
  avatar: string
  introduction: string
  roles: string[]
}

export interface IDurationLoan {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  productChildType: string,
  effectiveDateStart: string,
  effectiveDateEnd: string,
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

export interface IDurationMaturity {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  productChildType: string,
  payDateStart: string,
  payDateEnd: string,
  payType: string,
  repayDateStart: string,
  repayDateEnd: string,
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 实还表查询字段
export interface IDurationActualrepayment {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  productChildType: string,
  payDateStart: string,
  payDateEnd: string,
  payType: string,
  repayDateStart: string,
  repayDateEnd: string,
  compensatoryDateStart:string,
  compensatoryDateEnd:string,
  buyoutDateStart:string,
  buyoutDateEnd:string,
  payStatus:string,
  overdueGrade:string
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 减免表查询字段
export interface IDurationDeduction {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  productChildType: string,
  payDateStart: string,
  payDateEnd: string,
  deductionType: string,
  deductionDateStart : string,
  deductionDateEnd : string,
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 代偿表查询字段
export interface IDurationCompensatory {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  compensatoryDateStart : string,
  compensatoryDateEnd : string,
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 买断查询字段
export interface IDurationBuyout {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  buyoutDateStart : string,
  buyoutDateEnd : string,
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 逾期表 查询字段
export interface IbalanceOverdue {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  productChildType:string,
  payDateStart : string,
  payDateEnd : string,
  overdueGrade:string
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 未到期表 查询字段
export interface IbalanceUndue {
  fundingParty: string,
  channelType: string,
  channelName: string,
  productType: string,
  productChildType:string,
  payDateStart : string,
  payDateEnd : string,
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 剩余本金分布表 查询字段
export interface IbalanceDistribution {
  dataDate: string,
  pageNumber?: number, // 页数
  pageSize?: number // 每页多少
}

// 剩余本金无级分布表 查询字段
export interface IbalanceFivelevel {
  dataDateStart: string,
  dataDateEnd: string,
}
