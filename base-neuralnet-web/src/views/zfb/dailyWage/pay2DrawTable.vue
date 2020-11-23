<template>
  <div>
    <div class="export-container">
      <h2>电商收入报表</h2>
      <div>
        <el-button
          class="export"
          icon="el-icon-download"
          size="mini"
          @click="exportExcel"
        >
          导出
        </el-button>
      </div>
    </div>
    <div v-loading="loading">
      <el-table :data="tableData" border style="width: 100%">
      <el-table-column align="center" label="日期" fixed prop="bizDate" :width="tableDateWidth(this.time)"/>
      <el-table-column align="center" label="次部门" fixed prop="sjtType"/>
      <el-table-column align="center" label="电商收入用户数" >
        <el-table-column align="center" label="实名总用户数" prop="cntTotal" />
        <el-table-column align="center" label="未激活" prop="notActiveCnt" />
        <el-table-column align="center" label="激活中" prop="duringActiveCnt" />
        <el-table-column align="center" label="原始已激活" prop="activeCnt" />
        <el-table-column align="center" label="冻结" prop="blockCnt" />
        <el-table-column align="center" label="异动" prop="transactionCnt" />
        <el-table-column align="center" label="有效激活用户数累计" prop="activeCntTotal" />
      </el-table-column>
      <el-table-column align="center" label="电商收入用户申请支付宝额度情况" >
        <el-table-column align="center" label="申请过支付宝额度人数" prop="applyQuotaCnt"/>
        <el-table-column align="center" label="额度通过人数" prop="passQuotaCnt"/>
        <el-table-column align="center" label="新增额度申请人数" prop="newApplyQuotaCnt"/>
        <el-table-column align="center" label="待推送风控系统" prop="topushRiskCnt"/>
        <el-table-column align="center" label="审批拒绝" prop="approvalRefusedCnt"/>
        <el-table-column align="center" label="退回的申请" prop="returnApplyCnt"/>
        <el-table-column align="center" label="已取消的申请" prop="cancelApplyCnt"/>
        <el-table-column align="center" label="审批中" prop="duringApprovalCnt"/>
        <el-table-column align="center" label="新增额度通过人数" prop="newPassQuotaCnt"/>
        <el-table-column align="center" label="新增申请通过率" prop="newPassApplyCntRate"/>
      </el-table-column>
      <el-table-column align="center" label="电商收入额度支付" >
        <el-table-column align="center" label="总支付金额" prop="quotaPaySum"/>
        <el-table-column align="center" label="商城支付金额" prop="quotaShopPaySum"/>
        <el-table-column align="center" label="非商城支付金额" prop="quotaNotShopPaySum"/>
        <el-table-column align="center" label="支付人数" prop="quotaPayCntUv"/>
        <el-table-column align="center" label="支付笔数" prop="quotaPayCntPv"/>
        <el-table-column align="center" label="笔均金额" prop="quotaPaySumAvg"/>
      </el-table-column>
      <el-table-column align="center" label="电商收入提现">
        <el-table-column align="center" label="总提现金额" prop="salaryWithdrawSum"/>
        <el-table-column align="center" label="提现人数" prop="salaryWithdrawCntUv"/>
        <el-table-column align="center" label="提现笔数" prop="salaryWithdrawCntPv"/>
        <el-table-column align="center" label="笔均金额" prop="salaryWithdrawSumAvg"/>
      </el-table-column>
      <el-table-column align="center" label="支付宝" >
        <el-table-column align="center" label="当日总提现金额" prop="zfbWithdrawSum"/>
      </el-table-column>
      <el-table-column align="center" label="电商收入用户支付宝提现" >
        <el-table-column align="center" label="总提现金额" prop="zfbSalaryWithdrawSum"/>
        <el-table-column align="center" label="新增用户提现金额" prop="zfbSalaryNewWithdrawSum"/>
        <el-table-column align="center" label="新增用户提现金额占比" prop="zfbSalaryNewWithdrawSumRatio"/>
        <el-table-column align="center" label="非新增用户提现" prop="zfbSalaryNotNewWithdrawSum"/>
        <el-table-column align="center" label="总提现笔数" prop="zfbSalaryWithdrawCntPv"/>
        <el-table-column align="center" label="新增用户提现笔数" prop="zfbSalaryNewWithdrawCntPv"/>
        <el-table-column align="center" label="新增用户提现笔数占比" prop="zfbSalaryNewWithdrawCntPvRatio"/>
        <el-table-column align="center" label="笔均提现金额" prop="zfbSalaryWithdrawSumAvg"/>
        <el-table-column align="center" label="总提现人数" prop="zfbSalaryWithdrawCntUv"/>
        <el-table-column align="center" label="新增用户提现人数" prop="zfbSalaryNewWithdrawCntUv"/>
        <el-table-column align="center" label="新增用户提现人数占比" prop="zfbSalaryNewWithdrawCntUvRatio"/>
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
  </div>
</template>

<script>
import dayjs from 'dayjs';
import { exportDownload, exportQuery } from '@/api/zfb/getDailyWage.js';
import { tableDateWidthMixins } from '@/Mixins/tableDateWidthMixins';

export default {
  mixins: [tableDateWidthMixins],
  mounted() {
    this.exportQuery(true);
  },
  computed: {
    baseParam() {
      return {
        startDate: this.startDate
          ? dayjs(this.startDate).format('YYYY/MM/DD')
          : '',
        endDate: this.endDate ? dayjs(this.endDate).format('YYYY/MM/DD') : '',
        dateType: this.time,
        quotaStatus: this.quotaStatus,
        sjtType: this.sjtType,
      };
    },
  },
  methods: {
    // 翻页事件
    handleCurrentChange(val) {
      this.pageNum = val;
      this.exportQuery();
    },
    exportExcel() {
      exportDownload(this.baseParam);
    },
    /**
     * 电商收入报表
     * @param status 是否是父组件调用，是传入true
     */
    async exportQuery(status = false) {
      if (status) this.pageNum = 1;
      this.loading = true;
      const res = await exportQuery({
        ...this.baseParam,
        pageNum: Number(this.pageNum),
        pageSize: Number(this.pageSize),
      });
      this.loading = false;
      this.tableData = res.data.list;
      this.total = res.data.total;
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
