<template>
  <div class="app-container">
    <el-card class="header-card" shadow="hover">
      <div class="header-card-selection">
        <div>
          <span class="header-card-title">时间:</span>
          <el-date-picker
            v-model="time_series"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </div>
        <div>
          <span class="header-card-title">时间粒度:</span>
          <el-select
            v-model="time"
            placeholder="请选择时间粒度"
            class="select-smallwidth"
          >
            <el-option
              v-for="item in timeEunm"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
        <div>
          <span class="header-card-title">渠道:</span>
          <el-select
            v-model="channel"
            placeholder="请选择渠道"
            class="select-smallwidth"
          >
            <el-option
              v-for="item in channelEmnu"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
        <div>
          <span class="header-card-title">终端:</span>
          <el-select
            v-model="osType"
            placeholder="请选择终端"
            class="select-smallwidth"
          >
            <el-option
              v-for="item in osTypeEunm"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
        <div class="fr">
          <el-button
            icon="el-icon-search"
            size="medium"
            type="primary"
            plain
            @click="healderQuery"
          >查询
          </el-button>
        </div>
      </div>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <bus-apply-echarts
        class="echart-block"
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :channel="channel"
        :osType="osType"
        ref="BusApplyEcharts"
      ></bus-apply-echarts>
    </el-card>
    <!-- 支付宝进件注册报表 -->
    <el-card class="main-card table-content" shadow="hover">
      <bus-regist
        :channel="channel"
        :endDate="time_series[1]"
        :osType="osType"
        :startDate="time_series[0]"
        :time="time"
        ref="BusRegist"
      ></bus-regist>
    </el-card>
    <!--支付宝进件数量报表-->
    <el-card class="main-card table-content" shadow="hover">
      <bus-apply-table
        :channel="channel"
        :endDate="time_series[1]"
        :osType="osType"
        :startDate="time_series[0]"
        :time="time"
        ref="BusApply"
      ></bus-apply-table>
    </el-card>
  </div>
</template>

<script>
import { weekDateStatus } from '@/Mixins/weekDateStatus';
import { getChanel } from '@/api/zfb/config';
import busApplyEcharts from './busApplyEcharts';
import busRegist from './busRegist.vue';
import busApplyTable from './busApplyTable.vue';

export default {
  mixins: [weekDateStatus],
  components: {
    busApplyEcharts,
    busRegist,
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
      this.$refs.BusApplyEcharts.getConditionQuery();
      this.$refs.BusApply.getAmtQuery(true);
      this.$refs.BusRegist.getRegisterQuery(true);
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
