<template>
  <div class="app-container">
    <el-card
      class="header-card"
      shadow="hover"
    >
      <div class="header-card-content">
        <div class="header-card-selection">
          <div>
            <span class="header-card-title">资金方:</span>
            <el-select
              v-model="fundingParty.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in fundingParty.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
          <div>
            <span class="header-card-title">资产渠道类型:</span>
            <el-select
              v-model="channelType.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in channelType.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
          <div>
            <span class="header-card-title">渠道名称:</span>
            <el-select
              v-model="channelName.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in channelName.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
          <div>
            <span class="header-card-title">产品类型:</span>
            <el-select
              v-model="productType.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in productType.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
          <div>
            <span class="header-card-title">产品子类型:</span>
            <el-select
              v-model="productChildType.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in productChildType.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
        </div>
      </div>
      <div class="header-card-content">
        <div class="header-card-selection">
          <div>
            <span class="header-card-title">应还年月:</span>
            <el-date-picker
              v-model="payDate"
              :clearable="false"
              :picker-options="pickerOptions"
              align="right"
              end-placeholder="结束月份"
              format="yyyy/MM"
              size="medium"
              start-placeholder="开始月份"
              type="monthrange"
              value-format="yyyy/MM"
            />
          </div>
          <div>
            <span class="header-card-title">实还年月:</span>
            <el-date-picker
              v-model="repayDate"
              :clearable="false"
              :picker-options="pickerOptions"
              align="right"
              end-placeholder="结束月份"
              format="yyyy/MM"
              size="medium"
              start-placeholder="开始月份"
              type="monthrange"
              value-format="yyyy/MM"
            />
          </div>
          <div>
            <span class="header-card-title">还款类型:</span>
            <el-select
              v-model="payType.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in payType.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
          <div>
            <span class="header-card-title">还款状态:</span>
            <el-select
              v-model="payStatus.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in payStatus.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
        </div>
      </div>
      <div class="header-card-content">
        <div class="header-card-selection">
          <div>
            <!--            <span class="header-card-title">买断年月:</span>-->
            <el-radio-group
              v-model="buyout2compensatory"
              size="medium"
            >
              <el-radio-button
                v-for="(item,index) of buyout2compensatoryArr"
                :key="index"
                :label="item"
              />
            </el-radio-group>
            :
            <el-date-picker
              v-model="buyout2compensatoryDate"
              :clearable="false"
              :picker-options="pickerOptions"
              align="right"
              end-placeholder="结束月份"
              format="yyyy/MM"
              size="medium"
              start-placeholder="开始月份"
              type="monthrange"
              value-format="yyyy/MM"
            />
          </div>
          <div>
            <span class="header-card-title">十四级分类:</span>
            <el-select
              v-model="overdueGrade.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in overdueGrade.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
        </div>
        <div class="fr">
          <el-button
            icon="el-icon-search"
            plain
            size="small"
            type="primary"
            @click="getList"
          >
            查询
          </el-button>
        </div>
        <div class="fr">
          <el-button
            icon="el-icon-edit"
            plain
            size="small"
            type="primary"
            @click="reset"
          >
            重置
          </el-button>
        </div>
        <div class="fr">
          <el-button
            icon="el-icon-download"
            plain
            size="small"
            type="primary"
            @click="exportActualRepayment"
          >
            导出
          </el-button>
        </div>
      </div>
    </el-card>
    <el-card
      class="header-card"
      shadow="hover"
    >
      <el-table
        v-loading="listLoading"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          align="center"
          type="index"
        />
        <el-table-column
          label="资金方"
          prop="fundingParty"
        />
        <el-table-column
          label="资产渠道类型"
          prop="channelType"
        />
        <el-table-column
          label="渠道名称"
          prop="channelName"
        />
        <el-table-column
          label="是否引流项目"
          prop="isDrainage"
        />
        <el-table-column
          label="产品类型"
          prop="productType"
        />
        <el-table-column
          label="产品子类型"
          prop="productChildType"
        />
        <el-table-column
          label="还款类型"
          prop="payType"
        />
        <el-table-column
          label="还款状态"
          prop="payStatus"
        />
        <el-table-column
          label="应还年月"
          prop="payDate"
        />
        <el-table-column
          label="实还年月"
          prop="repayDate"
        />
        <el-table-column
          label="代偿年月"
          prop="compensatoryDate"
        >
          <template slot-scope="{row}">
            {{ row.compensatoryDate || '无' }}
          </template>
        </el-table-column>
        <el-table-column
          label="买断年月"
          prop="buyOutDate"
        >
          <template slot-scope="{row}">
            {{ row.buyOutDate || '无' }}
          </template>
        </el-table-column>
        <el-table-column
          label="十四级分类"
          prop="overdueGrade"
        />
        <el-table-column
          align="right"
          label="实还本金"
          prop="actualPrincipalAmount"
        />
        <el-table-column
          align="right"
          label="实还利息"
          prop="actualInterestAmount"
        />
        <el-table-column
          align="right"
          label="实还罚息"
          prop="actualPenaltyAmount"
        />
        <el-table-column
          align="right"
          label="实还服务费"
          prop="actualServiceAmount"
        />
        <el-table-column
          align="right"
          label="提前结清手续费"
          prop="actualPrepareAmount"
        />
        <el-table-column
          align="right"
          label="合计"
          prop="totalAmount"
        />
      </el-table>
    </el-card>
    <pagination
      v-show="listQuery.total>0"
      :limit.sync="listQuery.pageSize"
      :page.sync="listQuery.pageNumber"
      :total="listQuery.total"
      @pagination="getList(false)"
    />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { exportActualRepayment, getActualrepayment } from '@/api/duration'
import { IDurationActualrepayment } from '@/api/types'
import { getdatatime } from '@/utils'
import Pagination from '@/components/Pagination/index.vue'
import DatePicker from '@/components/DatePicker/index.vue'

  @Component({
    name: 'Actualrepayment',
    components: { Pagination, DatePicker }
  })
export default class extends Vue {
    [x: string]: any;

    payDate: [string, string] = ['', ''] // 应还年月
    repayDate: [string, string] = ['', ''] // 实还年月
    buyout2compensatory: string = '买断年月'
    buyout2compensatoryArr: string[] = ['买断年月', '代偿年月']
    buyout2compensatoryDate: [string, string] = ['', ''] // 代偿年月
    listLoading = true
    listQuery = {
      pageNumber: 1,
      pageSize: 10, // 每页大小
      total: 0
    }
    tableData = []

    get ListParam(): IDurationActualrepayment {
      return {
        ...this.exportParam,
        pageNumber: this.listQuery.pageNumber - 1,
        pageSize: this.listQuery.pageSize
      }
    }

    get exportParam(): IDurationActualrepayment {
      return {
        fundingParty: this.fundingParty.currkey,
        channelType: this.channelType.currkey,
        channelName: this.channelName.currkey,
        productType: this.productType.currkey,
        productChildType: this.productChildType.currkey,
        payType: this.payType.currkey,
        payDateStart: this.payDate[0],
        payDateEnd: this.payDate[1],
        repayDateStart: this.repayDate[0],
        repayDateEnd: this.repayDate[1],
        compensatoryDateStart: this.buyout2compensatory === this.buyout2compensatoryArr[1] ? this.buyout2compensatoryDate[0] : '',
        compensatoryDateEnd: this.buyout2compensatory === this.buyout2compensatoryArr[1] ? this.buyout2compensatoryDate[1] : '',
        buyoutDateStart: this.buyout2compensatory === this.buyout2compensatoryArr[0] ? this.buyout2compensatoryDate[0] : '',
        buyoutDateEnd: this.buyout2compensatory === this.buyout2compensatoryArr[0] ? this.buyout2compensatoryDate[1] : '',
        payStatus: this.payStatus.currkey,
        overdueGrade: this.overdueGrade.currkey
      }
    }

    created() {
      this.initDate()
      this.getList()
    }

    private async getList(btnClick: boolean = true) {
      if (btnClick) {
        this.listQuery = {
          pageNumber: 1,
          pageSize: 10, // 每页大小
          total: 0
        }
      }
      this.listLoading = true
      let res = await getActualrepayment(this.ListParam)
      this.tableData = res.data.financeCumulativeActualRepaymentReportListROList
      this.listQuery.pageNumber = res.data.pageNumber + 1
      this.listQuery.pageSize = res.data.pageSize
      this.listQuery.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 0.5 * 1000)
    }

    private initDate() {
      this.payDate = [getdatatime(), getdatatime()]
      this.repayDate = [getdatatime(), getdatatime()]
      this.buyout2compensatoryDate = [getdatatime(), getdatatime()]
    }

    private reset() {
      this.listQuery = {
        pageNumber: 1,
        pageSize: 10, // 每页大小
        total: 0
      }
      this.fundingParty.currkey = ''
      this.channelType.currkey = ''
      this.channelName.currkey = ''
      this.productType.currkey = ''
      this.productChildType.currkey = ''
      this.payType.currkey = ''
      this.initDate()
    }

    private async exportActualRepayment() {
      let res = await exportActualRepayment(this.exportParam)
    }
}
</script>

<style lang="scss" scoped>

</style>
