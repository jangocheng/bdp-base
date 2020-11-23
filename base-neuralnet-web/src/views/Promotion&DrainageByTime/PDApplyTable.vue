<!--
 * @Description: 渠道申请报表
 -->

<template>
  <div>
    <h2>单一渠道各项指标</h2>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column label="时间" prop="date" width="240"></el-table-column>
      <el-table-column label="渠道" prop="channel"></el-table-column>
      <!-- <el-table-column prop="osType" label="终端"></el-table-column>-->
      <!-- <el-table-column prop="customerName" label="UV"></el-table-column> -->
      <el-table-column label="注册人数" prop="registerAmount"></el-table-column>
      <el-table-column
        label="实名人数"
        prop="identifiedAmount"
      ></el-table-column>
      <el-table-column
        label="实名认证率"
        prop="registerIdentifiedRate"
      ></el-table-column>
      <el-table-column label="申请件数" prop="applyAmount"></el-table-column>
      <el-table-column
        label="申请通过数"
        prop="applyApprovedAmount"
      ></el-table-column>
      <el-table-column
        label="申请通过率"
        prop="applyApprovedRate"
      ></el-table-column>
      <el-table-column
        label="注册转化率"
        prop="registerToApplyRate"
      ></el-table-column>
      <el-table-column
        label="机审通过数"
        prop="machinePassAmount"
      ></el-table-column>
      <el-table-column
        label="机审通过率"
        prop="machinePassRate"
      ></el-table-column>
      <el-table-column label="拒绝件数" prop="applyRefusedAmount"></el-table-column>
      <el-table-column label="申请拒绝率" prop="applyRefuseRate"></el-table-column>
      <el-table-column
        label="机审拒绝件数"
        prop="machineRefuseAmount"
      ></el-table-column>
      <el-table-column
        label="机审拒绝率"
        prop="machineRefuseRate"
      ></el-table-column>
      <el-table-column
        label="提现人数"
        prop="withdrawAmount"
      ></el-table-column>
      <el-table-column label="提现成功率" prop="withdrawPassRage"></el-table-column>
      <el-table-column label="复贷提现用户数" prop="repaymentWithdrawAmount"></el-table-column>
      <el-table-column label="复贷提现用户数占比" prop="repaymentWithdrawRate"></el-table-column>
      <el-table-column label="当天提现金额(元)" prop="withdrawSum"></el-table-column>
      <el-table-column label="额度提现均值(元)" prop="approvedWithdrawMean"></el-table-column>
      <el-table-column label="累积提现次数" prop="totalWithdrawTimes"></el-table-column>
      <el-table-column label="累积提现金额(元)" prop="totalWithdrawSum"></el-table-column>
      <el-table-column label="首逾率" prop="firstOverdueRate"></el-table-column>
      <el-table-column label="M1逾期率" prop="m1OverdueRate"></el-table-column>
      <el-table-column label="M2逾期率" prop="m2OverdueRate"></el-table-column>
      <el-table-column label="M3逾期率" prop="m3OverdueRate"></el-table-column>
      <el-table-column label="坏账率" prop="badLogRate"></el-table-column>
    </el-table>
    <div class="footer-content" v-if="tableData.length > 0">
      <el-pagination
        :current-page="pageNum"
        :total="total"
        @current-change="handleCurrentChange"
        layout="prev, pager, next"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import dayjs from 'dayjs';
import { getPDAppleTbleData } from '@/api/business/PDTime.js';

export default {
  props: {
    /**
     * 两个参数时间格式20180911
     */
    startDate: {
      // type: Date,
      // required: true,
    },
    endDate: {
      // type: Date,
      // required: true,
    },
    time: {
      type: String,
      required: true,
    },
    channel: {
      type: String,
      required: true,
    },
    // osType: {
    //   type: String,
    //   required: true,
    // },
  },
  data() {
    return {
      tableData: [], //
      pageNum: 1, //当前页数
      total: 0, //总条数
      pageSize: 10, //每页条数,默认
    };
  },
  methods: {
    // 翻页事件
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getPDAppleTbleData();
    },
    /**
     * 获取渠道申请报表
     * @param status 是否是父组件调用，是传入true
     */
    async getPDAppleTbleData(status = false) {
      if (status) {
        this.pageNum = 1;
      }
      // 生成参数对象
      const param = {
        startDate: this.startDate
          ? dayjs(this.startDate).format('YYYYMMDD')
          : '',
        endDate: this.endDate ? dayjs(this.endDate).format('YYYYMMDD') : '',
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        time: this.time,
        channel: this.channel,
        osType: this.osType,
      };
      this.tableData = [];
      // 格式化对象
      const res = await getPDAppleTbleData(param);
      this.total = res.data.total;
      this.tableData = res.data.list;
      // this.pageNumber = res.result.pageNumber;
      // this.pageSize = res.result.pageSize;
    },
  },
  mounted() {
    this.getPDAppleTbleData(true);
  },
};
</script>

<style lang="scss" scoped>
.footer-content {
  margin: 20px;
  text-align: right;
}
</style>
