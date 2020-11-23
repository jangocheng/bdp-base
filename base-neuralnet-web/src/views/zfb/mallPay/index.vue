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
          <span class="header-card-title">推广渠道:</span>
          <el-select
            class="select-smallwidth"
            placeholder="请选择终端"
            v-model="popuChannel"
          >
            <el-option
              :key="item.value"
              :label="item.label"
              :value="item.value"
              v-for="item in popuChannelEunm"
            ></el-option>
          </el-select>
        </div>
        <div>
          <span class="header-card-title">支付类型:</span>
          <el-select
            class="select-smallwidth"
            placeholder="请选择支付类型"
            v-model="payWays"
          >
            <el-option
              :key="item.value"
              :label="item.label"
              :value="item.value"
              v-for="item in payWaysEunm"
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
      <!-- 推广渠道用户支付指标-->
      <bus-user-payment-target
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :popuChannel="popuChannel"
        :payWays="payWays"
        class="echart-block"
        ref="busUserPaymentTarget"
      ></bus-user-payment-target>
    </el-card>
    <!-- 支付流水报表 -->
    <el-card class="main-card table-content" shadow="hover">
      <mall-payment-table
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :popuChannel="popuChannel"
        :payWays="payWays"
        ref="payWaterTable"
      ></mall-payment-table>
    </el-card>
  </div>
</template>

<script>
import { weekDateStatus } from '@/Mixins/weekDateStatus';
import { getChanel } from '@/api/zfb/config';
import busUserPaymentTarget from './busUserPaymentTarget';
import mallPaymentTable from './mallPaymentTable';

export default {
  mixins: [weekDateStatus],
  components: {
    busUserPaymentTarget,
    mallPaymentTable,
  },
  data() {
    return {
      // 时间粒度枚举
      timeEunm: [],
      time: 'day', //默认按天查询，最小颗粒度
      // 渠道数据
      popuChannelEunm: [],
      popuChannel: '', //默认是所有，不进行过滤
      payWaysEunm: [],
      payWays: '', //所有支付类型，不进行过滤
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
      const { dateType, popuChannel, payWays } = res.data;
      this.timeEunm = dateType;
      this.popuChannelEunm = popuChannel;
      this.payWaysEunm = payWays;
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
      this.$refs.busUserPaymentTarget.getUserPayTarget();
      this.$refs.payWaterTable.getMallPayTable(true);
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
