<template>
  <div>
    <div class="export-container">
      <h2>支付流水报表</h2>
      <div>
        <el-button
          class="export"
          icon="el-icon-download"
          size="mini"
          @click="exportDowload"
          >导出
        </el-button>
      </div>
    </div>
    <div v-loading="loading">
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column
          align="center"
          label="日期"
          fixed
          prop="bizDate"
          :width="tableDateWidth(this.time)"
        />
        <el-table-column align="center" label="终端" fixed prop="sourceType" />
        <el-table-column align="center" label="线上商城支付金额">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffOnlineShopPaySum"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffOnlineShopPayRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialOnlineShopPaySum"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialOnlineShopPayRate"
          />
          <el-table-column
            align="center"
            label="折扣金额"
            prop="onlineShopPayDiscountSum"
          />
          <el-table-column
            align="center"
            label="小计"
            prop="onlineShopPaySum"
          />
        </el-table-column>
        <el-table-column align="center" label="线下门店商品支付金额(有支付商品信息)">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffStorePaySum"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffStorePayRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialStorePaySum"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialStorePayRate"
          />
          <el-table-column align="center" label="小计" prop="storePaySum" />
        </el-table-column>
        <el-table-column align="center" label="公租房代扣支付金额">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffRentalHouseWithholdSum"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffRentalHouseWithholdRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialRentalHouseWithholdSum"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialRentalHouseWithholdRate"
          />
          <el-table-column
            align="center"
            label="小计"
            prop="rentalHouseWithholdSum"
          />
        </el-table-column>
        <el-table-column align="center" label="线下门店普通支付金额(无支付商品信息)">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffStoreNotPaySum"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffStoreNotPayRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialStoreNotPaySum"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialStoreNotPayRate"
          />
          <el-table-column align="center" label="小计" prop="storeNotPaySum" />
        </el-table-column>
        <el-table-column align="center" label="总支付金额">
          <el-table-column align="center" label="内部员工" prop="staffPaySum" />
          <el-table-column
            align="center"
            label="内部员工平均支付金额"
            prop="staffPayAvg"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffPayRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialPaySum"
          />
          <el-table-column
            align="center"
            label="社会平均支付金额"
            prop="socialPayAvg"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialPayRate"
          />
          <el-table-column align="center" prop="payMaxHisRate">
            <template slot="header" slot-scope="scope">
              <el-tooltip
                placement="top"
                content="当前特定终端下和日期下总支付金额的(小计-历史某天最大值)/历史某天最大值"
              >
                <span>历史最高比率 * </span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="历史某天最大值"
            prop="payMaxHis"
          />
          <el-table-column
            align="center"
            label="折扣金额"
            prop="payDiscountSum"
          />
          <el-table-column
            align="center"
            label="当日平均支付金额"
            prop="payAvg"
          />
          <el-table-column
            align="center"
            label="当日最大支付金额"
            prop="payMax"
          />
          <el-table-column align="center" label="小计" prop="paySum" />
        </el-table-column>
        <el-table-column align="center" label="线上商城支付次数">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffOnlineShopPayCnt"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffOnlineShopPayCntRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialOnlineShopPayCnt"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialOnlineShopPayCntRate"
          />
          <el-table-column
            align="center"
            label="小计"
            prop="onlineShopPayCnt"
          />
        </el-table-column>
        <el-table-column align="center" label="线下门店商品支付次数(有支付商品信息)">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffStorePayCnt"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffStorePayCntRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialStorePayCnt"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialStorePayCntRate"
          />
          <el-table-column align="center" label="小计" prop="storePayCnt" />
        </el-table-column>
        <el-table-column align="center" label="公租房代扣支付次数">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffRentalHouseWithholdCnt"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffRentalHouseWithholdCntRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialRentalHouseWithholdCnt"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialRentalHouseWithholdCntRate"
          />
          <el-table-column
            align="center"
            label="小计"
            prop="rentalHouseWithholdCnt"
          />
        </el-table-column>
        <el-table-column align="center" label="线下门店商品支付次数(无支付商品信息)">
          <el-table-column
            align="center"
            label="内部员工"
            prop="staffStoreNotPayCnt"
          />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffStoreNotPayCntRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialStoreNotPayCnt"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialStoreNotPayCntRate"
          />
          <el-table-column align="center" label="小计" prop="storeNotPayCnt" />
        </el-table-column>
        <el-table-column align="center" label="总支付次数">
          <el-table-column align="center" label="内部员工" prop="staffPayCnt" />
          <el-table-column
            align="center"
            label="内部员工占比"
            prop="staffPayCntRate"
          />
          <el-table-column
            align="center"
            label="社会人士"
            prop="socialPayCnt"
          />
          <el-table-column
            align="center"
            label="社会人士占比"
            prop="socialPayCntRate"
          />
          <el-table-column
            align="center"
            label="折扣次数"
            prop="payDiscountCnt"
          />
          <el-table-column align="center" label="小计" prop="payCnt" />
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
import { exportDownload, getPay2waterTable } from '@/api/zfb/getPay2Water.js';
import { tableDateWidthMixins } from '@/Mixins/tableDateWidthMixins';

export default {
  mixins: [tableDateWidthMixins],
  mounted() {
    this.getPay2waterTable(true);
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
        payType: this.payType,
      };
    },
  },
  methods: {
    exportDowload() {
      exportDownload(this.baseParam);
    },
    // 翻页事件
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getPay2waterTable();
    },
    /**
     * 获取-支付流水报表
     * @param status 是否是父组件调用，是传入true
     */
    async getPay2waterTable(status = false) {
      this.loading = true;
      if (status) this.pageNum = 1;
      // 格式化对象
      const res = await getPay2waterTable({
        ...this.baseParam,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      });
      if (!res || !res.data) return;
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
