<!--

 * @Description: 实时注册数据vue文件
 -->

<template>
  <div class="app-container">
    <el-card class="header-card" shadow="hover">
      <div class="header-card-content">
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
          <!--          <div>-->
          <!--            <span class="header-card-title">时间粒度:</span>-->
          <!--            <el-select-->
          <!--              class="select-smallwidth"-->
          <!--              placeholder="请选择时间粒度"-->
          <!--              v-model="time"-->
          <!--            >-->
          <!--              <el-option-->
          <!--                :key="item.value"-->
          <!--                :label="item.label"-->
          <!--                :value="item.value"-->
          <!--                v-for="item in timeEunm"-->
          <!--              ></el-option>-->
          <!--            </el-select>-->
          <!--          </div>-->
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
    <!-- 申请量趋势图 -->
    <el-card class="main-card" shadow="hover">
      <register-echarts
        :endDate="time_series[1]"
        :startDate="time_series[0]"
        :time="time"
        class="echart-block"
        ref="MechantRegister"
      >
      </register-echarts>
    </el-card>
    <!-- 通过与激活门店趋势图 -->
    <el-card class="main-card" shadow="hover">
      <active-merchant
        :endDate="time_series[1]"
        :startDate="time_series[0]"
        :time="time"
        class="echart-block"
        ref="activeMechant"
      ></active-merchant>
    </el-card>
    <!-- 渠道申请报表 -->
    <el-card class="main-card table-content" shadow="hover">
      <bus-apply-table
        :endDate="time_series[1]"
        :startDate="time_series[0]"
        :time="time"
        ref="MechantAudit"
      ></bus-apply-table>
    </el-card>
  </div>
</template>

<script>
import { timeEunm } from '../../components/enum.js';
import busApplyTable from './busApplyTable';
import registerEcharts from './registerEcharts';
import activeMerchant from './activeMerchant.vue';

export default {
  data() {
    return {
      // 时间粒度枚举
      timeEunm,
      time: 'day', //默认按天查询，最小颗粒度
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
    };
  },
  created() {
    this.initTimeSeries();
  },
  mounted() {
    this.healderQuery();
  },
  components: {
    busApplyTable,
    registerEcharts,
    activeMerchant,
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
      this.$refs.MechantAudit.getMechantAuditApplyTableData(true);
      this.$refs.MechantRegister.getRegisterData();
      this.$refs.activeMechant.getActiveMechantData();
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
  // top: 0;
  // bottom: 0;
  // right: 0;
  // left: 0;
  // position: absolute;
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
