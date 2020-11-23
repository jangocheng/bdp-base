<template>
  <div>
    <div class="export-container">
      <h2>支付宝支付提现报表</h2>
      <div>
        <el-button
          class="export"
          icon="el-icon-download"
          size="mini"
          @click="exportExcel"
        >导出
        </el-button>
      </div>
    </div>
    <div v-loading="loading">
      <el-table :data="tableData" border style="width: 100%">
      <el-table-column align='center' label="日期" prop="bizDate" fixed :width="tableDateWidth(this.time)"/>
      <el-table-column align='center' label="终端" prop="sourceType" fixed/>
      <el-table-column align='center' label="通过金额(获批额度)" >
        <el-table-column align='center' label="内部员工" prop="staffPassSum"/>
        <el-table-column align='center' label="内部员工占比" prop="staffPassRate"/>
        <el-table-column align='center' label="社会人士" prop="socialPassSum"/>
        <el-table-column align='center' label="社会人士占比" prop="socialPassRate"/>
        <el-table-column align='center' label="小计" prop="passSum"/>
      </el-table-column>
      <el-table-column align='center' label="线上商城支付金额" >
        <el-table-column align='center' label="内部员工" prop="staffOnlineShopPaySum"/>
        <el-table-column align='center' label="内部员工占比" prop="staffOnlineShopPayRate"/>
        <el-table-column align='center' label="社会人士" prop="socialOnlineShopPaySum"/>
        <el-table-column align='center' label="社会人士占比" prop="socialOnlineShopPayRate"/>
        <el-table-column align='center' label="小计" prop="onlineShopPaySum"/>
        <el-table-column align='center' label="折扣金额" prop="onlineShopDiscountSum"/>
      </el-table-column>
      <el-table-column align='center' label="线下门店商品支付金额(有支付商品信息)" >
        <el-table-column align='center' label="内部员工" prop="staffStorePaySum"/>
        <el-table-column align='center' label="内部员工占比" prop="staffStorePayRate"/>
        <el-table-column align='center' label="社会人士" prop="socialStorePaySum"/>
        <el-table-column align='center' label="社会人士占比" prop="socialStorePayRate"/>
        <el-table-column align='center' label="小计" prop="storePaySum"/>
      </el-table-column>
      <el-table-column align='center' label="公租房代扣支付金额" >
        <el-table-column align='center' label="内部员工" prop="staffRentalHouseWithholdSum"/>
        <el-table-column align='center' label="内部员工占比" prop="staffRentalHouseWithholdRate"/>
        <el-table-column align='center' label="社会人士" prop="socialRentalHouseWithholdSum"/>
        <el-table-column align='center' label="社会人士占比" prop="socialRentalHouseWithholdRate"/>
        <el-table-column align='center' label="小计" prop="rentalHouseWithholdSum"/>
      </el-table-column>
      <el-table-column align='center' label="线下门店普通支付金额(无支付商品信息)" >
        <el-table-column align='center' label="内部员工" prop="staffStoreNotPaySum"/>
        <el-table-column align='center' label="内部员工占比" prop="staffStoreNotPayRate"/>
        <el-table-column align='center' label="社会人士" prop="socialStoreNotPaySum"/>
        <el-table-column align='center' label="社会人士占比" prop="socialStoreNotPayRate"/>
        <el-table-column align='center' label="小计" prop="storeNotPaySum"/>
      </el-table-column>
      <el-table-column align='center' label="总支付金额" >
        <el-table-column align='center' label="内部员工" prop="staffPaySum"/>
        <el-table-column align='center' label="内部员工平均支付金额" prop="staffPayAvg"/>
        <el-table-column align='center' label="内部员工占比" prop="staffPayRate"/>
        <el-table-column align='center' label="社会人士" prop="socialPaySum"/>
        <el-table-column align='center' label="社会人士平均支付金额" prop="socialPayAvg"/>
        <el-table-column align='center' label="社会人士占比" prop="socialPayRate"/>
        <el-table-column align='center' label="小计" prop="paySum"/>
        <el-table-column align='center' label="历史最高比率" prop="payMaxHisRate"/>
        <el-table-column align='center' label="历史某天最大值" prop="payMaxHis"/>
        <el-table-column align='center' label="折扣金额" prop="payDiscountSum"/>
        <el-table-column align='center' label="当日平均支付金额" prop="payAvg"/>
        <el-table-column align='center' label="当日最大支付金额" prop="payMax"/>
        <el-table-column align='center' label="支付金额/(支付金额+提现金额)" prop="payRate"/>
      </el-table-column>
      <el-table-column align='center' label="线上商城支付次数" >
        <el-table-column align='center' label="内部员工" prop="staffOnlineShopPayCnt"/>
        <el-table-column align='center' label="内部员工占比" prop="staffOnlineShopPayCntRate"/>
        <el-table-column align='center' label="社会人士" prop="socialOnlineShopPayCnt"/>
        <el-table-column align='center' label="社会人士占比" prop="socialOnlineShopPayCntRate"/>
        <el-table-column align='center' label="小计" prop="onlineShopPayCnt"/>
      </el-table-column>
      <el-table-column align='center' label="线下门店商品支付次数(有支付商品信息)" >
        <el-table-column align='center' label="内部员工" prop="staffStorePayCnt"/>
        <el-table-column align='center' label="内部员工占比" prop="staffStorePayCntRate"/>
        <el-table-column align='center' label="社会人士" prop="socialStorePayCnt"/>
        <el-table-column align='center' label="社会人士占比" prop="socialStorePayCntRate"/>
        <el-table-column align='center' label="小计" prop="storePayCnt"/>
      </el-table-column>
      <el-table-column align='center' label="公租房代扣支付次数" >
        <el-table-column align='center' label="内部员工" prop="staffRentalHouseWithholdCnt"/>
        <el-table-column align='center' label="内部员工占比" prop="staffRentalHouseWithholdCntRate"/>
        <el-table-column align='center' label="社会人士" prop="socialRentalHouseWithholdCnt"/>
        <el-table-column align='center' label="社会人士占比" prop="socialRentalHouseWithholdCntRate"/>
        <el-table-column align='center' label="小计" prop="rentalHouseWithholdCnt"/>
      </el-table-column>
      <el-table-column align='center' label="线下门店普通支付次数(无支付商品信息)" >
        <el-table-column align='center' label="内部员工" prop="staffStoreNotPayCnt"/>
        <el-table-column align='center' label="内部员工占比" prop="staffStoreNotPayCntRate"/>
        <el-table-column align='center' label="社会人士" prop="socialStoreNotPayCnt"/>
        <el-table-column align='center' label="社会人士占比" prop="socialStoreNotPayCntRate"/>
        <el-table-column align='center' label="小计" prop="storeNotPayCnt"/>
      </el-table-column>
      <el-table-column align='center' label="总支付次数" >
        <el-table-column align='center' label="内部员工" prop="staffPayCnt" />
        <el-table-column align='center' label="内部员工占比" prop="staffPayCntRate"/>
        <el-table-column align='center' label="社会人士" prop="socialPayCnt"/>
        <el-table-column align='center' label="社会人士占比" prop="socialPayCntRate"/>
        <el-table-column align='center' label="折扣次数" prop="payDiscountCnt"/>
        <el-table-column align='center' label="小计" prop="payCnt"/>
      </el-table-column>
      <el-table-column align='center' label="提现次数" >
        <el-table-column align='center' label="内部员工" prop="staffWithdrawCnt"/>
        <el-table-column align='center' label="内部员工首次提现/内部员工当日提现" prop="staffFirstWithdrawCntRate"/>
        <el-table-column align='center' label="内部员工首次提现" prop="staffFirstWithdrawCnt"/>
        <el-table-column align='center' label="内部员工占比" prop="staffWithdrawCntRatio"/>
        <el-table-column align='center' label="社会人士" prop="socialWithdrawCnt"/>
        <el-table-column align='center' label="社会人士首次提现/社会人士当日提现" prop="socialFirstWithdrawCntRate"/>
        <el-table-column align='center' label="社会人士首次提现" prop="socialFirstWithdrawCnt"/>
        <el-table-column align='center' label="社会人士占比" prop="socialWithdrawCntRatio"/>
        <el-table-column align='center' label="首次提现小计" prop="firstWithdrawCnt"/>
        <el-table-column align='center' label="首次提现/当日提现" prop="firstWithdrawCntRate"/>
        <el-table-column align='center' label="小计" prop="withdrawCnt"/>
      </el-table-column>
      <el-table-column align='center' label="提现金额" >
        <el-table-column align='center' label="内部员工" prop="staffWithdrawSum"/>
        <el-table-column align='center' label="内部员工平均提现" prop="staffWithdrawSumAvg"/>
        <el-table-column align='center' label="内部员工首次提现/内部员工提现" prop="staffFirstWithdrawSumRate"/>
        <el-table-column align='center' label="内部员工首次提现" prop="staffFirstWithdrawSum"/>
        <el-table-column align='center' label="内部员工提现/当日提现" prop="staffWithdrawSumRate"/>
        <el-table-column align='center' label="社会人士" prop="socialWithdrawSum"/>
        <el-table-column align='center' label="社会人士平均提现" prop="socialWithdrawSumAvg"/>
        <el-table-column align='center' label="社会人士首次提现/社会人士提现" prop="socialFirstWithdrawSumRate"/>
        <el-table-column align='center' label="社会人士首次提现" prop="socialFirstWithdrawSum"/>
        <el-table-column align='center' label="社会人士提现/当日提现" prop="socialWithdrawSumRate"/>
        <el-table-column align='center' label="首次提现小计" prop="firstWithdrawSum"/>
        <el-table-column align='center' label="首次提现/当日提现" prop="firstWithdrawSumRate"/>
        <el-table-column align='center' label="小计" prop="withdrawSum"/>
        <el-table-column align='center' label="历史最高比率" prop="withdrawSumMaxHisRate"/>
        <el-table-column align='center' label="历史某天最大值" prop="withdrawSumMaxHisLag"/>
        <el-table-column align='center' label="历史平均值" prop="withdrawSumAvgHisLag"/>
        <el-table-column align='center' label="(当日平均-历史平均)/历史平均的比率" prop="withdrawSumAvgHisRate"/>
        <el-table-column align='center' label="当日平均提现金额" prop="withdrawSumAvg"/>
        <el-table-column align='center' label="当日最大提现金额" prop="withdrawSumMax"/>
        <el-table-column align='center' label="提现金额/(提现金额+提现金额)" prop="withdrawSumRate"/>
      </el-table-column>
    </el-table>
      <div class="footer-content" v-if="tableData.length > 0">
      <el-pagination
        layout="prev, pager, next"
        :total="total"
        :current-page="pageNum"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </div>
    </div>
  </div>
</template>

<script>
import dayjs from 'dayjs';
import {
  exportQuery,
  getExportDownload,
} from '@/api/zfb/getPay2WithdrawalsData';
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
        sourceType: this.osType,
      };
    },
  },
  methods: {
    exportExcel() {
      getExportDownload(this.baseParam);
    },
    // 翻页事件
    handleCurrentChange(val) {
      this.pageNum = val;
      this.exportQuery();
    },
    /**
     * 获取-支付宝支付提现报表
     * @param status 是否是父组件调用，是传入true
     */
    async exportQuery(status = false) {
      this.loading = true;
      if (status) this.pageNum = 1;
      // 格式化对象
      const res = await exportQuery({
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
