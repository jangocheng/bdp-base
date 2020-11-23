import {Action, getModule, Module, Mutation, VuexModule} from 'vuex-module-decorators'
import store from '@/store'
import {getCodeLibrary} from '@/api/balance'

export interface IDictItemStateItem {
  currkey: string,
  list: {
    key: string,
    value: string
  }[]
}

/**
 *  资金方:fundingparty
 *  资产渠道类型:channeltype
 *  渠道名称:channelname
 *  产品类型:producttype
 *  产品子类型:productchildtype
 *  还款类型:paytype
 *  还款状态:paystatus
 *  十四级分类:overduegrade
 *  减免类型:deductiontype
 */
export interface IDictState {
  fundingparty: IDictItemStateItem,
  channeltype: IDictItemStateItem,
  channelname: IDictItemStateItem,
  producttype: IDictItemStateItem,
  productchildtype: IDictItemStateItem,
  paytype: IDictItemStateItem,
  paystatus: IDictItemStateItem,
  overduegrade: IDictItemStateItem,
  deductiontype: IDictItemStateItem,
}

@Module({ dynamic: true, store, name: 'enmu' })
class Dict extends VuexModule implements IDictState {
  private init: IDictItemStateItem = {
    currkey: '',
    list: [{
      key: '',
      value: ''
    }]
  }
  public fundingparty: IDictItemStateItem = Object.assign({}, this.init)
  public channeltype: IDictItemStateItem = Object.assign({}, this.init)
  public channelname: IDictItemStateItem = Object.assign({}, this.init)
  public producttype: IDictItemStateItem = Object.assign({}, this.init)
  public productchildtype: IDictItemStateItem = Object.assign({}, this.init)
  public paytype: IDictItemStateItem = Object.assign({}, this.init)
  public paystatus: IDictItemStateItem = Object.assign({}, this.init)
  public overduegrade: IDictItemStateItem = Object.assign({}, this.init)
  public deductiontype: IDictItemStateItem = Object.assign({}, this.init)

  @Mutation
  private SET_KEY({ key, value }: { key: string, value: [] }) {
    // @ts-ignore
    this[key].list = value
  }

  @Action
  public async getCodeLibrary(param: string) {
    const res: any = await getCodeLibrary({ codeType: param })
    const { codeType, selectCodeLibrary } = res.data
    this.SET_KEY({ key: codeType, value: selectCodeLibrary })
  }

  @Action
  public async getDict() {
    const dictArr = ['fundingparty', 'channeltype', 'channelname', 'producttype', 'productchildtype', 'paytype', 'paystatus', 'overduegrade', 'deductiontype']
    // 发起循环请求
    dictArr.forEach((item, index) => {
      this.getCodeLibrary(item)
    })
  }
}

export const DictModule = getModule(Dict)
