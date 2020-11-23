<!--

 * @Description: 推广引流(渠道)
 -->

<template>
  <div class="app-container">
    <el-card class="header-card" shadow="hover">
      <div class="header-card-content">
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
            <span class="header-card-title">渠道:</span>
            <el-select
              v-model="channel"
              placeholder="请选择渠道"
              class="select-smallwidth"
            >
              <el-option
                v-for="item in channelEmnu"
                :key="item"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
          </div>
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
    <!-- 通过人数和转化率 -->
    <el-card class="main-card" shadow="hover">
      <PDBusRegisterEcharts
        class="echart-block"
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :channel="channel"
        :time="time"
        ref="PDBusRegister"
      ></PDBusRegisterEcharts>
    </el-card>
    <div class="main-card flex-content">
      <el-card class="item-content" shadow="hover">
        <!--用户手机机型Top10-->
        <cusPhoneTypeBar
          :channel="channel"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          class="echart-block"
          ref="cusPhoneTypeBar"
        ></cusPhoneTypeBar>
      </el-card>
      <el-card class="item-content" shadow="hover">
        <!--用户年龄分布-->
        <cusAgeBar
          :channel="channel"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          class="echart-block"
          ref="cusAgeBar"
        ></cusAgeBar>
      </el-card>
      <el-card class="item-content" shadow="hover">
        <cusEducationBar
          :channel="channel"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          class="echart-block"
          ref="cusEducationBar"
        ></cusEducationBar>
      </el-card>
    </div>
    <div class="main-card flex-content">
      <el-card class="item-content" shadow="hover">
        <rateSex
          :channel="channel"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          class="echart-block"
          ref="rateSex"
        ></rateSex>
      </el-card>
      <el-card class="item-content" shadow="hover">
        <cusLevelBar
          :channel="channel"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          class="echart-block"
          ref="cusLevelBar">
        </cusLevelBar>
      </el-card>
      <el-card class="item-content" shadow="hover">
        <cusPeriodsBar
          :channel="channel"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          class="echart-block"
          ref="cusPeriodsBar">
        </cusPeriodsBar>
      </el-card>
    </div>
    <el-card class="main-card map-content" shadow="hover">
      <PayAndWithdrawal
        :channel="channel"
        :endDate="time_series[1]"
        :startDate="time_series[0]"
        class="echart-block"
        ref="PayAndWithdrawal">
      </PayAndWithdrawal>
    </el-card>
    <!-- 渠道注册报表 -->
    <el-card class="main-card table-content" shadow="hover">
      <PDApplyTable
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :channel="channel"
        :time="time"
        ref="PDApplyTable"
      ></PDApplyTable>
    </el-card>
  </div>
</template>

<script>
import { getChanel } from '@/api/business/PDTime';
import { osTypeEunm, timeEunm } from '../../components/enum.js';
import PDApplyTable from './PDApplyTable';
import PDBusRegisterEcharts from './PDBusRegisterEcharts';
import cusPhoneTypeBar from './cusPhoneTypeBar';
import cusAgeBar from './cusAgeBar';
import cusEducationBar from './cusEducationBar';
import rateSex from './rateSex';
import cusLevelBar from './cusLevelBar';
import cusPeriodsBar from './cusPeriodsBar';
import PayAndWithdrawal from './PayAndWithdrawal';

export default {
  components: {
    PDApplyTable,
    PDBusRegisterEcharts,
    cusPhoneTypeBar,
    cusAgeBar,
    cusEducationBar,
    rateSex,
    cusLevelBar,
    cusPeriodsBar,
    PayAndWithdrawal,
  },
  data() {
    return {
      // 时间粒度枚举
      timeEunm,
      time: 'day', //默认按天查询，最小颗粒度
      // 渠道数据
      channelEmnu: [],
      channel: '所有', //默认是所有，不进行过滤
      // 终端枚举
      osTypeEunm,
      osType: '05', //所有终端，不进行过滤
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            },
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            },
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            },
          },
        ],
      },
      // 默认是最近一周的数据
      time_series: [
        new Date().setTime(new Date().getTime()), //startDate
        new Date(), //endDate
      ],
      // busRegistData: [], //渠道注册报表所用数据
    };
  },
  created() {
    this.getChanel();
    this.initTimeSeries();
  },

  methods: {
    // 查询
    healderQuery() {
      if (!this.weekDateStatus()) {
        return;
      }
      this.$refs.PDApplyTable.getPDAppleTbleData(true);
      this.$refs.PDBusRegister.applyApprovedAmount();
      this.$refs.cusPhoneTypeBar.getSummary();
      this.$refs.cusAgeBar.getSummary();
      this.$refs.cusEducationBar.getSummary();
      this.$refs.rateSex.getSummary();
      this.$refs.cusLevelBar.getSummary();
      this.$refs.cusPeriodsBar.getSummary();
      this.$refs.PayAndWithdrawal.getSummary();
    },
    weekDateStatus() {
      const datejs = this.$datejs;
      let lastDate = datejs.DateFormatterYMD(this.time_series[1]);
      let startDate = datejs.DateFormatterYMD(this.time_series[0]);
      if (this.time === 'week') {
        // 根据 开始时间 获取下周一的时间
        startDate = datejs.GetDateStr({
          AddDayCount: 1,
          date: datejs.weekDate(startDate)[1], //当前日期周一日期
        });
        // 根据结束时间获取上周末的时间
        lastDate = datejs.GetDateStr({
          AddDayCount: -1,
          date: datejs.weekDate(lastDate)[0], //当前日期周一日期
        });
        // 如果开始时间在结束时间后面
        if (startDate > lastDate) {
          this.$message({
            type: 'warning',
            message: '请修改时间查询条件，保证其区间内含有整个自然周',
          });
          return false;
        }
        // //this.time_series = [startDate, lastDate];
      } else if (this.time === 'month') {
        // 根据 开始时间 获取下周一的时间
        startDate = datejs.GetDateStr({
          AddDayCount: 1,
          date: datejs.monthDate(startDate)[1], //当前月 月末日期
        });
        // 根据结束时间获取上周末的时间
        lastDate = datejs.GetDateStr({
          AddDayCount: -1,
          date: datejs.monthDate(lastDate)[0], //当前月 月初日期
        });
        // 如果开始时间在结束时间后面
        if (startDate > lastDate) {
          this.$message({
            type: 'warning',
            message: '请修改时间查询条件，保证其区间内含有整个自然月',
          });
          return false;
        }
        //this.time_series = [startDate, lastDate];
      }

      return true;
    },
    // 获取渠道数据
    async getChanel() {
      const res = await getChanel();
      this.channelEmnu = res.data;
    },
    // 初始化时间
    initTimeSeries() {
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.time_series[0] = start;
      this.time_series[1] = new Date();
    },
  },
};
</script>


<style lang="scss" scoped>
// 与父元素宽高同步
.app-container {
  display: flex;
  flex-direction: column;

  .header-card {
    margin-bottom: 20px;
    .header-card-content {
      display: flex;
      justify-content: space-between;

      .header-card-selection {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;

        > div {
          margin-right: 10px;
        }

        .header-card-title {
          font-size: 14px;
        }
      }
    }
  }
  .main-card {
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

  .flex-content {
    display: flex;

    .item-content {
      flex: 1;
      position: relative;
    }
  }

  .map-content {
    height: 700px;
  }
  .table-content {
    min-height: 700px;
  }
}
</style>
