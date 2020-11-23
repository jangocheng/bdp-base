<template>
  <div class="app-container">
    <el-card class="header-card" shadow="hover">
      <div class="header-card-selection">
        <div>
          <span class="header-card-title">时间:</span>
          <el-date-picker
            :picker-options="pickerOptions"
            align="right"
            end-placeholder="结束日期"
            range-separator="至"
            start-placeholder="开始日期"
            type="daterange"
            unlink-panels
            v-model="time_series"
          ></el-date-picker>
        </div>
        <div>
          <span class="header-card-title">时间粒度:</span>
          <el-select
            class="select-smallwidth"
            placeholder="请选择时间粒度"
            v-model="time"
          >
            <el-option
              :key="item.value"
              :label="item.label"
              :value="item.value"
              v-for="item in timeEunm"
            ></el-option>
          </el-select>
        </div>
        <div>
          <span class="header-card-title">终端:</span>
          <el-select
            class="select-smallwidth"
            placeholder="请选择终端"
            v-model="osType"
          >
            <el-option
              :key="item.value"
              :label="item.label"
              :value="item.value"
              v-for="item in osTypeEunm"
            ></el-option>
          </el-select>
        </div>
        <div class="fr">
          <el-button
            @click="healderQuery"
            icon="el-icon-search"
            plain
            size="medium"
            type="primary"
            >查询
          </el-button>
        </div>
      </div>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <!-- 授信金额占比-->
      <credit-amount
        :channel="channel"
        :endDate="time_series[1]"
        :osType="osType"
        :startDate="time_series[0]"
        :time="time"
        class="echart-block"
        ref="creditAmount"
      ></credit-amount>
    </el-card>
    <!-- 提现次数、金额及占比 -->
    <el-card class="main-card" shadow="hover">
      <withdrawal-echart
        :channel="channel"
        :endDate="time_series[1]"
        :osType="osType"
        :startDate="time_series[0]"
        :time="time"
        class="echart-block"
        ref="withdrawalEchart"
      ></withdrawal-echart>
    </el-card>
    <!-- 首次提现次数、金额及占比 -->
    <el-card class="main-card" shadow="hover">
      <first-withdrawal-echart
        :channel="channel"
        :endDate="time_series[1]"
        :osType="osType"
        :startDate="time_series[0]"
        :time="time"
        class="echart-block"
        ref="firstWithdrawalEchart"
      ></first-withdrawal-echart>
    </el-card>
    <!--支付宝进件数量报表-->
    <el-card class="main-card table-content" shadow="hover">
      <bus-apply-table
        :channel="channel"
        :endDate="time_series[1]"
        :osType="osType"
        :startDate="time_series[0]"
        :time="time"
        ref="busApplyTable"
      ></bus-apply-table>
    </el-card>
  </div>
</template>

<script>
import { weekDateStatus } from '@/Mixins/weekDateStatus';
import { getChanel } from '@/api/zfb/config';
import CreditAmount from './CreditAmount';
import withdrawalEchart from './withdrawalEchart';
import firstWithdrawalEchart from './firstWithdrawalEchart';
import busApplyTable from './busApplyTable.vue';

export default {
  mixins: [weekDateStatus],
  components: {
    CreditAmount,
    withdrawalEchart,
    firstWithdrawalEchart,
    busApplyTable,
  },
  data() {
    return {
      // 时间粒度枚举
      timeEunm: [],
      time: 'day', //默认按天查询，最小颗粒度
      // 渠道数据
      channelEmnu: [],
      channel: '', //默认是所有，不进行过滤
      // 终端枚举
      osTypeEunm: [],
      osType: '', //所有终端，不进行过滤
      // 默认是最近一周的数据
      time_series: [
        new Date().setTime(new Date().getTime()), //startDate
        new Date(), //endDate
      ],
    };
  },
  created() {
    this.getChanel();
    this.initTimeSeries();
  },
  methods: {
    async getChanel() {
      const res = await getChanel();
      const { dateType, sourceType, targetType } = res.data;
      this.timeEunm = dateType;
      this.channelEmnu = targetType;
      this.osTypeEunm = sourceType;
    },
    // 初始化时间
    initTimeSeries() {
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.time_series[0] = start;
      this.time_series[1] = new Date();
    },
    // 查询
    healderQuery() {
      if (!this.weekDateStatus()) return;
      this.$refs.creditAmount.getCreditAmountQuery();
      this.$refs.withdrawalEchart.withdrawal();
      this.$refs.firstWithdrawalEchart.firstWithdrawal();
      this.$refs.busApplyTable.exportQuery(true);
    },
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  display: flex;
  flex-direction: column;

  .header-card {
    margin-bottom: 20px;

    .header-card-selection {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;

      .header-card-title {
        font-size: 14px;
      }
    }
  }

  .main-card {
    // flex: 1;
    min-height: 500px;
    position: relative;
    margin-bottom: 20px;
    // 为容器组件赋予宽高
    .echart-block {
      position: absolute;
      top: 20px;
      left: 20px;
      right: 20px;
      bottom: 20px;
    }
  }

  .table-content {
    min-height: 700px;
  }

  .ostype-smallwidth {
    width: 100px;
  }
}
</style>
