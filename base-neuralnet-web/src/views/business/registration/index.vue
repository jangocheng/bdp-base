<!--
 * @Description: 实时注册数据vue文件
 -->
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
              :key="item"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </div>
        <div>
          <span class="header-card-title">终端:</span>
          <el-select
            v-model="osType"
            placeholder="请选择终端"
            class="select-smallwidth ostype-smallwidth"
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
    <!-- 注册到进件各环节数量及环比 -->
    <el-card class="main-card" shadow="hover">
      <bus-apply-echarts
        class="echart-block"
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :channel="channel"
        :osType="osType"
        ref="BusApply"
      ></bus-apply-echarts>
    </el-card>
    <!-- 渠道注册量占比及数量环比 -->
    <el-card class="main-card" shadow="hover">
      <busChanelEcharts
        class="echart-block"
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :channel="channel"
        :osType="osType"
        ref="BusApplyChanel"
      ></busChanelEcharts>
    </el-card>
    <!-- 渠道注册报表 -->
    <el-card class="main-card table-content" shadow="hover">
      <bus-regist
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :channel="channel"
        :osType="osType"
        ref="BusRegist"
      ></bus-regist>
    </el-card>
  </div>
</template>


<script>
import { getChanel } from '@/api/business/getBusRegist.js';
import { osTypeEunm, timeEunm } from '../../../components/enum.js';
import busApplyEcharts from './busApplyEcharts.vue';
import busChanelEcharts from './busChanelEcharts.vue';
import busRegist from './busRegist.vue';

export default {
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
  components: {
    busRegist,
    busApplyEcharts,
    busChanelEcharts,
  },
  methods: {
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
    // 查询
    healderQuery() {
      if (!this.weekDateStatus()) {
        return;
      }
      // 更新渠道报表
      this.$refs.BusRegist.getBusTableData(true);
      //更新“注册到进件”echart数据
      this.$refs.BusApply.getRegisterData();
      this.$refs.BusApplyChanel.getChannelData();
    },
    // 获取渠道数据
    async getChanel() {
      // 发起渠道请求
      const res = await getChanel();
      this.channelEmnu = res.data;
      this.healderQuery();
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
