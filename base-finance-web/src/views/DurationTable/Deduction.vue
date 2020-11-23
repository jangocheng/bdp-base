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
            <span class="header-card-title">减免类型:</span>
            <el-select
              v-model="deductionType.currkey"
              clearable
              placeholder="请选择"
              size="medium"
            >
              <el-option
                v-for="item in deductionType.list"
                :key="item.value"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </div>
          <div>
            <span class="header-card-title">减免年月:</span>
            <el-date-picker
              v-model="deductionDate"
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
            @click="exportDeduction"
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
        <!--        <el-table-column-->
        <!--          label="是否引流项目"-->
        <!--          prop="isDrainage"-->
        <!--        />-->
        <el-table-column
          label="产品类型"
          prop="productType"
        />
        <el-table-column
          label="产品子类型"
          prop="productChildType"
        />
        <el-table-column
          label="应还年月"
          prop="payDate"
        />
        <el-table-column
          label="减免类型"
          prop="deductionType"
        />
        <el-table-column
          label="减免年月"
          prop="deductionDate"
        />
        <el-table-column
          align="right"
          label="减免利息"
          prop="deductionInterest"
        />
        <el-table-column
          align="right"
          label="减免服务费"
          prop="deductionService"
        />
        <el-table-column
          align="right"
          label="减免扣款失败费"
          prop="deductionFailAmount"
        />
        <el-table-column
          align="right"
          label="减免罚息"
          prop="deductionPenalty"
        />
        <el-table-column
          align="right"
          label="减免总金额"
          prop="deductionTotalAmount"
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
import { channelName, channelType, deductionType, fundingParty, productChildType, productType } from '@/enmu.ts'
import { exportDeduction, getDeduction } from '@/api/duration'
import { IDurationDeduction } from '@/api/types'
import { getdatatime } from '@/utils'
import Pagination from '@/components/Pagination/index.vue'
import DatePicker from '@/components/DatePicker/index.vue'

  @Component({
    name: 'Deduction',
    components: { Pagination, DatePicker }
  })
export default class extends Vue {
    fundingParty = fundingParty
    channelType = channelType
    channelName = channelName
    productType = productType
    productChildType = productChildType
    deductionType = deductionType
    payDate: [string, string] = ['', '']
    deductionDate: [string, string] = ['', '']
    listLoading = true
    listQuery = {
      pageNumber: 1,
      pageSize: 10, // 每页大小
      total: 0
    }
    tableData = []

    get ListParam(): IDurationDeduction {
      return {
        ...this.exportParam,
        pageNumber: this.listQuery.pageNumber - 1,
        pageSize: this.listQuery.pageSize
      }
    }

    get exportParam(): IDurationDeduction {
      return {
        fundingParty: this.fundingParty.currkey,
        channelType: this.channelType.currkey,
        channelName: this.channelName.currkey,
        productType: this.productType.currkey,
        productChildType: this.productChildType.currkey,
        payDateStart: this.payDate[0],
        payDateEnd: this.payDate[1],
        deductionType: this.deductionType.currkey,
        deductionDateStart: this.deductionDate[0],
        deductionDateEnd: this.deductionDate[1]
      }
    }

    created() {
      this.initDate()
      this.getList()
    }

    private initDate() {
      this.payDate = [getdatatime(), getdatatime()]
      this.deductionDate = [getdatatime(), getdatatime()]
    }

    private async getList(btnClick: boolean = true) {
      if (btnClick) {
        this.listQuery = {
          pageNumber: 1,
          pageSize: 10, // 每页大小
          total: 0
        }
      }
      let res = await getDeduction(this.ListParam)
      this.tableData = res.data.financeCumulativeDeductionReportListROList
      this.listQuery.pageNumber = res.data.pageNumber + 1
      this.listQuery.pageSize = res.data.pageSize
      this.listQuery.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 0.5 * 1000)
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
      this.deductionType.currkey = ''
      this.initDate()
    }

    private async exportDeduction() {
      let res = await exportDeduction(this.exportParam)
    }
}
</script>

<style lang="scss" scoped>

</style>
