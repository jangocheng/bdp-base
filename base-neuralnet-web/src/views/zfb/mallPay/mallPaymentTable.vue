<template>
  <div>
    <div class="export-container">
      <h2>商城支付报表</h2>
      <div>
        <el-button
          class="export"
          icon="el-icon-download"
          size="mini"
          @click="exportDowload"
        >
          导出
        </el-button>
      </div>
    </div>
    <div v-loading="loading">
      <el-table :data="tableData" border style="width: 100%">
      <el-table-column align='center' label="日期" fixed prop="bizDate" :width="tableDateWidth(this.time)"/>
      <el-table-column align='center'>
        <template slot="header" slot-scope="scope">
          <el-tooltip
            placement="top"
            content="mall首家体验店开业活动活动开始时间2018/06/21"
          >
            <span>mall首家体验店 * </span>
          </el-tooltip>
        </template>
         <el-table-column align='center' label="支付宝支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntCdZfb"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntCdZfb"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntCdZfb"/>
           <el-table-column align='center' label="支付金额" prop="paySumCdZfb"/>
        </el-table-column>
      </el-table-column>
       <el-table-column align='center' label="销售人员/推广分享">
         <el-table-column align='center' label="支付宝支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntShareZfb"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntShareZfb"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntShareZfb"/>
           <el-table-column align='center' label="支付金额" prop="paySumShareZfb"/>
        </el-table-column>
         <el-table-column align='center' label="微信支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntShareWx"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntShareWx"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntShareWx"/>
           <el-table-column align='center' label="支付金额" prop="paySumShareWx"/>
        </el-table-column>
         <el-table-column align='center' label="银行卡支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntShareBank"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntShareBank"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntShareBank"/>
           <el-table-column align='center' label="支付金额" prop="paySumShareBank"/>
        </el-table-column>
         <el-table-column align='center' label="薪资额度支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntShareSalary"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntShareSalary"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntShareSalary"/>
           <el-table-column align='center' label="支付金额" prop="paySumShareSalary"/>
        </el-table-column>
         <el-table-column align='center' label="组合支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntShareGroup"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntShareGroup"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntShareGroup"/>
           <el-table-column align='center' label="支付金额" prop="paySumShareGroup"/>
        </el-table-column>
      </el-table-column>
       <el-table-column align='center' label="历史推广渠道">
         <el-table-column align='center' label="支付宝支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntChannelZfb"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntChannelZfb"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntChannelZfb"/>
           <el-table-column align='center' label="支付金额" prop="paySumChannelZfb"/>
        </el-table-column>
         <el-table-column align='center' label="微信支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntChannelWx"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntChannelWx"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntChannelWx"/>
           <el-table-column align='center' label="支付金额" prop="paySumChannelWx"/>
        </el-table-column>
      </el-table-column>
       <el-table-column align='center' label="自然量">
         <el-table-column align='center' label="支付宝支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntNatureZfb"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntNatureZfb"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntNatureZfb"/>
           <el-table-column align='center' label="支付金额" prop="paySumNatureZfb"/>
        </el-table-column>
         <el-table-column align='center' label="微信支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntNatureWx"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntNatureWx"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntNatureWx"/>
           <el-table-column align='center' label="支付金额" prop="paySumNatureWx"/>
        </el-table-column>
         <el-table-column align='center' label="银行卡支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntNatureBank"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntNatureBank"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntNatureBank"/>
           <el-table-column align='center' label="支付金额" prop="paySumNatureBank"/>
        </el-table-column>
         <el-table-column align='center' label="薪资额度支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntNatureSalary"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntNatureSalary"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntNatureSalary"/>
           <el-table-column align='center' label="支付金额" prop="paySumNatureSalary"/>
        </el-table-column>
         <el-table-column align='center' label="组合支付">
           <el-table-column align='center' label="新用户数" prop="newPayUserCntNatureGroup"/>
           <el-table-column align='center' label="支付用户数" prop="payUserCntNatureGroup"/>
           <el-table-column align='center' label="支付件数" prop="payOrderCntNatureGroup"/>
           <el-table-column align='center' label="支付金额" prop="paySumNatureGroup"/>
        </el-table-column>
      </el-table-column>
       <el-table-column align='center' label="汇总">
         <el-table-column align='center' label="新用户数" prop="newPayUserCntTotal"/>
         <el-table-column align='center' label="支付用户数" prop="payUserCntTotal"/>
         <el-table-column align='center' label="支付件数" prop="payOrderCntTotal"/>
         <el-table-column align='center' label="支付金额" prop="paySumTotal"/>
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
import { exportDownload, getMallPayTable } from '@/api/zfb/mallPay.js';
import { tableDateWidthMixins } from '@/Mixins/tableDateWidthMixins';

export default {
  mixins: [tableDateWidthMixins],
  mounted() {
    this.getMallPayTable(true);
  },
  computed: {
    baseParam() {
      return {
        startDate: this.startDate
          ? dayjs(this.startDate).format('YYYY/MM/DD')
          : '',
        endDate: this.endDate ? dayjs(this.endDate).format('YYYY/MM/DD') : '',
        dateType: this.time,
        popuChannel: this.popuChannel,
        payWays: this.payWays,
      };
    },
  },
  methods: {
    // 翻页事件
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getMallPayTable();
    },
    exportDowload() {
      exportDownload(this.baseParam);
    },
    /**
     * 获取-商城支付报表
     * @param status 是否是父组件调用，是传入true
     */
    async getMallPayTable(status = false) {
      this.loading = true;
      if (status) this.pageNum = 1;
      const res = await getMallPayTable({
        ...this.baseParam,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      });
      this.loading = false;
      this.total = res.data.total;
      this.tableData = res.data.list;
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
