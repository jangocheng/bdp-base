<!--
 * @Description: 渠道注册报表
 -->

<template>
  <div>
    <h2>渠道申请报表</h2>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column label="日期" prop="date" width="200"></el-table-column>
      <el-table-column label="当日">
        <el-table-column
          label="申请数"
          prop="applyAmount"
          width="120">
        </el-table-column>
        <el-table-column
          label="流程状态"
          width="120">
          <el-table-column
            label="审批通过"
            prop="applyApprovedAmount"
          ></el-table-column>
          <el-table-column
            label="准入审核-初审"
            prop="accessFirstTrialAmount"
          ></el-table-column>
          <el-table-column
            label="退回补充资料"
            prop="returnSupplementaryAmount"
          ></el-table-column>
          <el-table-column
            label="准入申请"
            prop="accessApplyAmount"
          ></el-table-column>
          <el-table-column
            label="审批拒绝"
            prop="approvalRefuseAmount"
          ></el-table-column>
        </el-table-column>
        <el-table-column
          label="激活状态"
          width="120">
          <el-table-column
            label="初审"
            prop="firstTrialAmount"
          ></el-table-column>
          <el-table-column
            label="关闭"
            prop="closeAmount"
          ></el-table-column>
          <el-table-column
            label="激活"
            prop="activationAmount"
          ></el-table-column>
          <el-table-column
            label="拒绝"
            prop="refuseAmount"
          ></el-table-column>
          <el-table-column
            label="新增"
            prop="newAmount"
          ></el-table-column>
          <el-table-column
            label="准入"
            prop="accessAmount"
          ></el-table-column>
        </el-table-column>
      </el-table-column>
      <el-table-column label="累积">
        <el-table-column
          label="申请数"
          prop="applyTotalAmount"
          width="120">
        </el-table-column>
        <el-table-column
          label="流程状态"
          width="120">
          <el-table-column
            label="审批通过"
            prop="applyApprovedTotalAmount"
          ></el-table-column>
          <el-table-column
            label="准入审核-初审"
            prop="accessFirstTrialTotalAmount"
          ></el-table-column>
          <el-table-column
            label="退回补充资料"
            prop="returnSupplementaryTotalAmount"
          ></el-table-column>
          <el-table-column
            label="准入申请"
            prop="accessApplyTotalAmount"
          ></el-table-column>
          <el-table-column
            label="审批拒绝"
            prop="approvalRefuseTotalAmount"
          ></el-table-column>
        </el-table-column>
        <el-table-column
          label="激活状态"
          width="120">
          <el-table-column
            label="初审"
            prop="firstTrialTotalAmount"
          ></el-table-column>
          <el-table-column
            label="关闭"
            prop="closeTotalAmount"
          ></el-table-column>
          <el-table-column
            label="激活"
            prop="activationTotalAmount"
          ></el-table-column>
          <el-table-column
            label="拒绝"
            prop="refuseTotalAmount"
          ></el-table-column>
          <el-table-column
            label="新增"
            prop="newTotalAmount"
          ></el-table-column>
          <el-table-column
            label="准入"
            prop="accessTotalAmount"
          ></el-table-column>
        </el-table-column>
      </el-table-column>
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
import { getMechantAuditApplyTableData } from '@/api/business/merchantAudit.js';

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
      this.getMechantAuditApplyTableData();
    },
    /**
     * 获取渠道申请报表
     * @param status 是否是父组件调用，是传入true
     */
    async getMechantAuditApplyTableData(status = false) {
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
      };
      // 格式化对象
      const res = await getMechantAuditApplyTableData(param);
      if (!res || !res.data) {
        return;
      }
      this.total = res.data.total;
      this.tableData = res.data.list;
      // this.pageNumber = res.result.pageNumber;
      // this.pageSize = res.result.pageSize;
    },
  },
};
</script>

<style lang="scss" scoped>
.footer-content {
  margin: 20px;
  text-align: right;
}
</style>
