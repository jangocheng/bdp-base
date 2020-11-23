<!--
 * @Description: 渠道申请报表
 -->

 <template>
  <div>
    <h2>门店金额消费情况</h2>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="rname" label="商户名称"></el-table-column>
      <el-table-column prop="storeName" label="门店名称"></el-table-column>
      <el-table-column prop="consumptionAmount" label="次数"></el-table-column>
      <el-table-column prop="consumptionSum" label="消额(元)"></el-table-column>
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
</template>

<script>
import dayjs from 'dayjs';
import { getPopularizeStore } from '@/api/business/PDChannel.js';

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
    channel: {
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
      this.getPopularizeStore();
    },
    /**
     * 获取渠道申请报表
     * @param status 是否是父组件调用，是传入true
     */
    async getPopularizeStore(status = false) {
      if (status) {
        this.pageNum = 1;
      }
      // 生成参数对象
      const param = {
        startDate: this.startDate
          ? dayjs(this.startDate).format('YYYYMMDD')
          : '',
        endDate: this.endDate ? dayjs(this.endDate).format('YYYYMMDD') : '',
        channel: this.channel,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      };
      // 格式化对象
      const res = await getPopularizeStore(param);
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
