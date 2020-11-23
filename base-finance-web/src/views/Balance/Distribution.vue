<template>
  <div class="app-container">
    <el-card
      class="header-card"
      shadow="hover"
    >
      <div class="header-card-content">
        <div class="header-card-selection">
          <div>
            <span class="header-card-title">截止日期:</span>
            <el-date-picker
              v-model="dataDate"
              value-format="yyyy/MM/dd"
              size="medium"
              type="date"
              placeholder="选择日期时间"
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
          label="产品子类型"
          prop="productChildType"
        />
        <el-table-column
          align="right"
          label="未到期本金"
          prop="principalAmount"
        />
        <el-table-column
          align="center"
          label="逾期本金"
        >
          <el-table-column
            prop="normalPrincipalAmount"
            align="right"
            label="正常类(M0)"
          />
          <el-table-column
            prop="followPrincipalAmount"
            align="right"
            label="关注类(M1-M3)"
          />
          <el-table-column
            prop="secondaryPrincipalAmount"
            align="right"
            label="次级类(M4-M6)"
          />
          <el-table-column
            prop="suspiciousPrincipalAmount"
            align="right"
            label="可疑(M7-M12)"
          />
          <el-table-column
            prop="lossPrincipalAmount"
            align="right"
            label="损失(M12+)"
          />
          <el-table-column
            prop="subTotalAmount"
            align="right"
            label="小计"
          />
        </el-table-column>
        <el-table-column
          align="center"
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
import { getDistribution } from '@/api/balance'
import { IbalanceDistribution } from '@/api/types'
import { getCurTime } from '@/utils'
import Pagination from '@/components/Pagination/index.vue'
import DatePicker from '@/components/DatePicker/index.vue'

  @Component({
    name: 'Distribution',
    components: { Pagination, DatePicker }
  })
export default class extends Vue {
    dataDate: string = '' // 截止日期
    listLoading = true
    listQuery = {
      pageNumber: 1,
      pageSize: 10, // 每页大小
      total: 0
    }
    tableData = []

    get ListParam(): IbalanceDistribution {
      return {
        dataDate: this.dataDate,
        pageNumber: this.listQuery.pageNumber - 1,
        pageSize: this.listQuery.pageSize
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
      let res = await getDistribution(this.ListParam)
      this.tableData = res.data.financeResidualPrincipalDistributionReportListROList
      this.listQuery.pageNumber = res.data.pageNumber + 1
      this.listQuery.pageSize = res.data.pageSize
      this.listQuery.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 0.5 * 1000)
    }

    private initDate() {
      this.dataDate = getCurTime()
    }

    private reset() {
      this.listQuery = {
        pageNumber: 1,
        pageSize: 10, // 每页大小
        total: 0
      }
      this.initDate()
    }
}
</script>

<style lang="scss" scoped>

</style>
