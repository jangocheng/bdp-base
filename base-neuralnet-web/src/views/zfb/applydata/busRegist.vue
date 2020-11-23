<!--
 * @Description: 渠道注册报表
 -->

<template>
  <div>
    <div class="export-container">
      <h2>支付宝进件注册报表</h2>
      <div>
        <el-button
          class="export"
          icon="el-icon-download"
          size="mini"
          @click="exportDow"
          >导出
        </el-button>
      </div>
    </div>
    <div v-loading="loading">
      <el-table :data="tableData" border style="width: 100%;">
      <el-table-column fixed label="日期" prop="bizDate" align="center" :width="tableDateWidth(this.time)"/>
      <el-table-column label="注册渠道" prop="targetType" align="center"/>
      <el-table-column label="注册人数" prop="regSum" align="center"/>
      <el-table-column label="累积注册人数" prop="regSumHis" align="center"/>
      <el-table-column label="实名认证人数" prop="realnameSum" align="center"/>
      <el-table-column label="实名认证率" prop="realnameRate" align="center"/>
      <el-table-column label="额度申请件数" prop="applySum" align="center"/>
      <el-table-column label="累计额度申请件数" prop="applySumHis" align="center"/>
      <el-table-column label="通过件数" prop="passSum" align="center"/>
      <el-table-column label="拒绝件数" prop="refuseSum" align="center"/>
      <el-table-column label="取消件数" prop="cancelSum" align="center"/>
      <el-table-column label="通过率" prop="passRate" align="center"/>
      <el-table-column label="拒绝率" prop="refuseRate" align="center"/>
      <el-table-column label="取消率" prop="cancelRate" align="center"/>
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
import { tableDateWidthMixins } from '@/Mixins/tableDateWidthMixins';
import { getRegisterDownload, getRegisterQuery } from '@/api/zfb/getApplyData';

export default {
  mixins: [tableDateWidthMixins],
  mounted() {
    this.getRegisterQuery(true);
  },
  methods: {
    // 翻页事件
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getRegisterQuery();
    },
    exportDow() {
      const param = {
        startDate: this.startDate
          ? dayjs(this.startDate).format('YYYY/MM/DD')
          : '',
        endDate: this.endDate ? dayjs(this.endDate).format('YYYY/MM/DD') : '',
        dateType: this.time,
        targetType: this.channel,
        sourceType: this.osType,
      };
      getRegisterDownload(param);
    },
    /**
     * 获取渠道注册报表
     * @param status 是否是父组件调用，是传入true
     */
    async getRegisterQuery(status = false) {
      this.loading = true;
      if (status) this.pageNum = 1;
      // 生成参数对象
      const param = {
        startDate: this.startDate
          ? dayjs(this.startDate).format('YYYYMMDD')
          : '',
        endDate: this.endDate ? dayjs(this.endDate).format('YYYYMMDD') : '',
        dateType: this.time,
        targetType: this.channel,
        sourceType: this.osType,
        pageNum: Number(this.pageNum),
        pageSize: Number(this.pageSize),
      };
      // 格式化对象
      const res = await getRegisterQuery(param);
      this.loading = false;
      if (!res || !res.data) return;
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
