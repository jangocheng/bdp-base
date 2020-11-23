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
      </div>
    </el-card>
    <el-card
      class="header-card"
      shadow="hover"
    >
      <el-table
        v-loading="listLoading"
        :data="tableData"
        :span-method="objectSpanMethod"
        border
        style="width: 100%"
      >
        <el-table-column
          align="center"
          label="等级标准分类"
        >
          <el-table-column
            label="五级分类"
            align="center"
            prop="fivetype"
            width="120"
          />
          <el-table-column
            label="十四级分类"
            align="center"
            prop="fourteentype"
            width="120"
          />
          <el-table-column
            label="逾期天数"
            align="center"
            prop="overduedays"
            width="140"
          />
        </el-table-column>
        <el-table-column
          align="center"
          label="剩余本金"
        >
          <template v-if="dateList.length>0">
            <el-table-column
              v-for="(item,index) of dateList"
              :key="index"
              align="center"
              :label="item"
            >
              <template slot-scope="{row}">
                {{ row.distribution[index] }}
              </template>
            </el-table-column>
          </template>
          <template v-else>
            <el-table-column
              v-if="dateList.length===0"
              align="center"
              label="暂无数据"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getFivelevel } from '@/api/balance'
import { IbalanceFivelevel } from '@/api/types'
import { getCurTime } from '@/utils'
import Pagination from '@/components/Pagination/index.vue'
import DatePicker from '@/components/DatePicker/index.vue'

  @Component({
    name: 'Fivelevel',
    components: { Pagination, DatePicker }
  })
export default class extends Vue {
    dataDate: [string, string] = ['', ''] // 应还年月

    listLoading = false
    tableData: {
      fivetype: string
      fourteentype: string
      overduedays: string
      distribution: any[]
    }[] = [
      {
        fivetype: '正常类',
        fourteentype: 'C',
        overduedays: '0天',
        distribution: []
      }, {
        fivetype: '关注类',
        fourteentype: 'M1',
        overduedays: '1-30天(含30天)',
        distribution: []
      }, {
        fivetype: '关注类',
        fourteentype: 'M2',
        overduedays: '31-60天(含60天)',
        distribution: []
      }, {
        fivetype: '关注类',
        fourteentype: 'M3',
        overduedays: '61-90天(含90天)',
        distribution: []
      }, {
        fivetype: '次级类',
        fourteentype: 'M4',
        overduedays: '91-120天(含120天)',
        distribution: []
      }, {
        fivetype: '次级类',
        fourteentype: 'M5',
        overduedays: '121-150天(含150天)',
        distribution: []
      }, {
        fivetype: '次级类',
        fourteentype: 'M6',
        overduedays: '151-180天(含180天)',
        distribution: []
      }, {
        fivetype: '可疑',
        fourteentype: 'M7',
        overduedays: '181-210天(含210天)',
        distribution: []
      }, {
        fivetype: '可疑',
        fourteentype: 'M8',
        overduedays: '211-240天(含240天)',
        distribution: []
      }, {
        fivetype: '可疑',
        fourteentype: 'M9',
        overduedays: '241-270天(含270天)',
        distribution: []
      }, {
        fivetype: '可疑',
        fourteentype: 'M10',
        overduedays: '271-300天(含300天)',
        distribution: []
      }, {
        fivetype: '可疑',
        fourteentype: 'M11',
        overduedays: '301-330天(含330天)',
        distribution: []
      }, {
        fivetype: '可疑',
        fourteentype: 'M12',
        overduedays: '331-360天(含360天)',
        distribution: []
      }, {
        fivetype: '损失',
        fourteentype: 'M12+',
        overduedays: '360天以上',
        distribution: []
      }, {
        fivetype: '合计',
        fourteentype: '',
        overduedays: '',
        distribution: []
      }]
    dateList: string[] = [''] // 时间title
    arr1: number[] = [] // 合并单元格用

    get ListParam(): IbalanceFivelevel {
      return {
        dataDateStart: this.dataDate[0],
        dataDateEnd: this.dataDate[1]
      }
    }

    created() {
      this.initDate()
      this.setdates(this.tableData)
      this.getList()
    }

    private async getList() {
      this.listLoading = true
      let res = await getFivelevel(this.ListParam)
      let List = res.data.fiveLevelReportBeanList
      this.dateList = []
      this.tableData.forEach(item => {
        item.distribution = []
      })
      List.forEach((item: { dataDate: string; levelAmountList: any[]; }) => {
        this.dateList.push(item.dataDate)
        this.tableData.forEach((itemB, indexB) => {
          itemB.distribution.push(item.levelAmountList[indexB])
        })
      })

      setTimeout(() => {
        this.listLoading = false
      }, 0.5 * 1000)
    }

    setdates(arr: any) {
      let obj: any = {}
      let k = []
      for (var i = 0, len = arr.length; i < len; i++) {
        k = arr[i].fivetype
        if (obj[k]) {
          obj[k]++
        } else {
          obj[k] = 1
        }
      }
      // 保存结果{el-'元素'，count-出现次数}
      for (let o in obj) {
        for (let i = 0; i < obj[o]; i++) {
          if (i === 0) {
            this.arr1.push(obj[o])
          } else {
            this.arr1.push(0)
          }
        }
      }
    }

    private objectSpanMethod({ row, column, rowIndex, columnIndex }: any) {
      if (columnIndex === 0 && this.tableData[rowIndex]) {
        let _row = this.arr1[rowIndex]
        let _col = this.arr1[rowIndex] > 0 ? 1 : 0
        return {
          rowspan: _row,
          colspan: _col
        }
      }
    }

    private initDate() {
      this.dataDate = [getCurTime(false), getCurTime(false)]
    }

    private reset() {
      this.initDate()
    }
}
</script>

<style lang="scss" scoped>

</style>
