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
          <div>
            <span class="header-card-title">消费类型:</span>
            <el-select
              class="select-smallwidth"
              placeholder="请选择渠道"
              v-model="consumptionType"
            >
              <el-option
                :key="item"
                :label="item"
                :value="item"
                v-for="item in consumptionTypeEunm"
              ></el-option>
            </el-select>
          </div>
          <div>
            <span class="header-card-title">客户类型:</span>
            <el-select
              class="select-smallwidth"
              placeholder="请选择终端"
              v-model="customer"
            >
              <el-option
                :key="item.value"
                :label="item.label"
                :value="item.value"
                v-for="item in customerEunm"
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
    <el-card class="main-card" shadow="hover">
      <consumption-amount
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :consumptionType="consumptionType"
        :customerType="customer"
        ref="consumptionAmount"
        class="echart-block"
      ></consumption-amount>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <consumption-sum
        :consumptionType="consumptionType"
        :customerType="customer"
        :endDate="time_series[1]"
        :startDate="time_series[0]"
        :time="time"
        class="echart-block"
        ref="consumptionSum"
      ></consumption-sum>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <consumption-maximum
        :consumptionType="consumptionType"
        :customerType="customer"
        :endDate="time_series[1]"
        :startDate="time_series[0]"
        :time="time"
        class="echart-block"
        ref="consumptionMaximum"
      ></consumption-maximum>
    </el-card>
    <div class="main-card flex-content">
      <el-card class="pie-content" shadow="hover">
        <ratePaytype
          :consumptionType="consumptionType"
          :customerType="customer"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          :time="time"
          class="echart-block"
          ref="ratePaytype">
        </ratePaytype>
      </el-card>
      <el-card class="pie-content" shadow="hover">
        <rateProduct
          :consumptionType="consumptionType"
          :customerType="customer"
          :endDate="time_series[1]"
          :startDate="time_series[0]"
          :time="time"
          class="echart-block"
          ref="rateProduct">
        </rateProduct>
      </el-card>
    </div>
  </div>
</template>


<script>
import { getPaytypes } from '@/api/payAndDraw.js';
import { customerEunm, timeEunm } from '../../components/enum.js';
import consumptionAmount from './consumptionAmount.vue';
import consumptionSum from './consumptionSum.vue';
import consumptionMaximum from './consumptionMaximum.vue';
import ratePaytype from './ratePaytype.vue';
import rateProduct from './rateProduct.vue';

export default {
  data() {
    return {
      // 时间粒度枚举
      timeEunm,
      time: 'day', //默认按天查询，最小颗粒度
      // 渠道数据
      consumptionTypeEunm: [],
      consumptionType: '所有', //默认是所有，不进行过滤
      // 客户类型
      customerEunm,
      customer: '03', //所有客户类型
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
    this.getPaytypes();
    this.initTimeSeries();
  },
  components: {
    consumptionAmount,
    consumptionSum,
    consumptionMaximum,
    ratePaytype,
    rateProduct,
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
      this.$refs.consumptionAmount.getPayAmount();
      this.$refs.consumptionSum.getPaySum();
      this.$refs.consumptionMaximum.getPayMaximum();
      this.$refs.ratePaytype.getPayPaytype();
      this.$refs.rateProduct.getPayProduct();
    },
    // 获取渠道数据
    async getPaytypes() {
      // 发起渠道请求
      const res = await getPaytypes();
      this.consumptionTypeEunm = ['所有', ...res.data];
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

  .flex-content {
    display: flex;

    .pie-content {
      flex: 1;
      position: relative;
    }
  }

  .customer-smallwidth {
    width: 100px;
  }
}
</style>
