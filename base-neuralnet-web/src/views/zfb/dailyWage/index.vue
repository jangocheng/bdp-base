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
            value-format="yyyy/MM/dd"
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
          <span class="header-card-title">额度状态:</span>
          <el-select
            class="select-smallwidth"
            placeholder="请选择"
            v-model="quotaStatus"
          >
            <el-option
              :key="item.value"
              :label="item.label"
              :value="item.value"
              v-for="item in quotaStatusEunm"
            ></el-option>
          </el-select>
        </div>
        <div>
          <span class="header-card-title">次部门:</span>
          <el-select
            class="select-smallwidth"
            placeholder="请选择"
            v-model="sjtType"
          >
            <el-option
              :key="item.value"
              :label="item.label"
              :value="item.value"
              v-for="item in sjtTypeEunm"
            ></el-option>
          </el-select>
        </div>
        <div>
          <span class="header-card-title">审批状态:</span>
          <el-select
            class="select-smallwidth"
            placeholder="请选择"
            v-model="approveStatus"
          >
            <el-option
              :key="item.value"
              :label="item.label"
              :value="item.value"
              v-for="item in approveStatusEnum"
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
      <daily-salaried-users-apply
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :quotaStatus="quotaStatus"
        :sjtType="sjtType"
        :approveStatus="approveStatus"
        class="echart-block"
        ref="dailySalariedUsersApply"
      ></daily-salaried-users-apply>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <!--薪资额度使用情况-->
      <daily-salaried-users-pay
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :quotaStatus="quotaStatus"
        :sjtType="sjtType"
        :approveStatus="approveStatus"
        class="echart-block"
        ref="dailySalariedUsersPay"
      ></daily-salaried-users-pay>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <daily-salaried-users-draw
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :quotaStatus="quotaStatus"
        :sjtType="sjtType"
        class="echart-block"
        ref="dailySalariedUsersDraw"
      ></daily-salaried-users-draw>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <daily-salaried-users-add-draw
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :quotaStatus="quotaStatus"
        :sjtType="sjtType"
        class="echart-block"
        ref="dailySalariedUsersAddDraw"
      ></daily-salaried-users-add-draw>
    </el-card>
    <el-card class="main-card table-content" shadow="hover">
      <pay-draw-table
        :startDate="time_series[0]"
        :endDate="time_series[1]"
        :time="time"
        :quotaStatus="quotaStatus"
        :sjtType="sjtType"
        ref="payDrawTable"
      ></pay-draw-table>
    </el-card>
  </div>
</template>

<script>
import { weekDateStatus } from '@/Mixins/weekDateStatus';
import { getChanel } from '@/api/zfb/config';
import dailySalariedUsersApply from './dailySalariedUsersApply.vue';
import dailySalariedUsersPay from './dailySalariedUsersPay.vue';
import dailySalariedUsersDraw from './dailySalariedUsersDraw.vue';
import dailySalariedUsersAddDraw from './dailySalariedUsersAddDraw.vue';
import payDrawTable from './pay2DrawTable.vue';

export default {
  mixins: [weekDateStatus],
  components: {
    dailySalariedUsersApply,
    dailySalariedUsersPay,
    dailySalariedUsersDraw,
    dailySalariedUsersAddDraw,
    payDrawTable,
  },
  data() {
    return {
      // 时间粒度枚举
      timeEunm: [],
      time: 'day', //默认按天查询，最小颗粒度
      // 渠道数据
      quotaStatusEunm: [],
      quotaStatus: '', //默认是所有，不进行过滤
      sjtTypeEunm: [],
      sjtType: '', //所有支付类型，不进行过滤
      approveStatusEnum: [],
      approveStatus: '', //所以审批状态
      // 默认是最近一周的数据
      time_series: [new Date().setTime(new Date().getTime()), new Date()],
    };
  },
  created() {
    this.getChanel();
    this.initTimeSeries();
  },
  methods: {
    async getChanel() {
      const res = await getChanel();
      const { dateType, quotaStatus, sjtType, approveStatus } = res.data;
      this.timeEunm = dateType;
      this.quotaStatusEunm = quotaStatus;
      this.sjtTypeEunm = sjtType;
      this.approveStatusEnum = approveStatus;
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
      this.$refs.dailySalariedUsersApply.creditQuotaApplyQuery();
      this.$refs.dailySalariedUsersPay.quotaPayQuery();
      this.$refs.dailySalariedUsersDraw.quotaWithdrawQuery();
      this.$refs.dailySalariedUsersAddDraw.quotaNewWithdrawQuery();
      this.$refs.payDrawTable.exportQuery(true);
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
