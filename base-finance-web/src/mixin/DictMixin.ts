import {Component, Vue} from 'vue-property-decorator'
import {DictModule} from '@/store/modules/dict'

@Component({})
export class DictMixin extends Vue {
  // 字典参数
  fundingParty = DictModule.fundingparty
  channelType = DictModule.channeltype
  payType = DictModule.paytype
  channelName = DictModule.channelname
  productType = DictModule.producttype
  productChildType = DictModule.productchildtype
  overdueGrade = DictModule.overduegrade
  payStatus = DictModule.paystatus
  deductionType = DictModule.deductiontype

  pickerOptions = {
    shortcuts: [{
      text: '本月',
      onClick(picker: any) {
        picker.$emit('pick', [new Date(), new Date()])
      }
    }, {
      text: '今年至今',
      onClick(picker: any) {
        const end = new Date()
        const start = new Date(new Date().getFullYear(), 0)
        picker.$emit('pick', [start, end])
      }
    }, {
      text: '最近六个月',
      onClick(picker: any) {
        const end = new Date()
        const start = new Date()
        start.setMonth(start.getMonth() - 6)
        picker.$emit('pick', [start, end])
      }
    }]
  }
}
