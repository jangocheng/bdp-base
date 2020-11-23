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
        </div>
      </div>
      <div class="header-card-content">
        <div class="header-card-selection">
          <div>
            <span class="header-card-title">代偿年月:</span>
            <el-date-picker
              v-model="compensatoryDate"
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
            @click="exportCompensatory"
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
          label="产品类型"
          prop="productType"
        />
        <el-table-column
          label="代偿年月"
          prop="compensatoryDate"
        />

        <el-table-column
          align="right"
          label="本金"
          prop="principalAmount"
        />
        <el-table-column
          align="right"
          label="利息"
          prop="interestAmount"
        />
        <el-table-column
          align="right"
          label="罚息"
          prop="penaltyAmount"
        />
        <el-table-column
          align="right"
          label="服务费"
          prop="serviceAmount"
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
import { exportCompensatory, getCompensatory } from '@/api/duration'
import { IDurationCompensatory } from '@/api/types'
import { getdatatime } from '@/utils'
import Pagination from '@/components/Pagination/index.vue'
import DatePicker from '@/components/DatePicker/index.vue'

  @Component({
    name: 'Compensatory',
    components: { Pagination, DatePicker }
  })
export default class extends Vue {
    [x: string]: any;

    compensatoryDate: [string, string] = ['', '']
    listLoading = true
    listQuery = {
      pageNumber: 1,
      pageSize: 10,
      total: 0
    }
    tableData = []

    get ListParam(): IDurationCompensatory {
      return {
        ...this.exportParam,
        pageNumber: this.listQuery.pageNumber - 1,
        pageSize: this.listQuery.pageSize
      }
    }

    get exportParam(): IDurationCompensatory {
      return {
        fundingParty: this.fundingParty.currkey,
        channelType: this.channelType.currkey,
        channelName: this.channelName.currkey,
        productType: this.productType.currkey,
        compensatoryDateStart: this.compensatoryDate[0],
        compensatoryDateEnd: this.compensatoryDate[1]
      }
    }

    created() {
      this.compensatoryDate = [getdatatime(), getdatatime()]
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
      let res = await getCompensatory(this.ListParam)
      this.tableData = res.data.financeCompensatoryCumulativePartnersReportListROList
      this.listQuery.pageNumber = res.data.pageNumber + 1
      this.listQuery.pageSize = res.data.pageSize
      this.listQuery.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 0.5 * 1000)
    }

    private async exportCompensatory() {
      let res = await exportCompensatory(this.exportParam)
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
      this.compensatoryDate = [getdatatime(), getdatatime()]
    }
}
</script>

<style lang="scss" scoped>
</style>
