<!--
 * @Description: 渠道注册报表
 -->

 <template>
  <div>
    <h2>渠道注册报表</h2>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="date" label="时间" width="280"></el-table-column>
      <el-table-column prop="channel" label="渠道"></el-table-column>
      <el-table-column prop="osType" label="终端"></el-table-column>
      <el-table-column prop="registerAmount" label="注册数"></el-table-column>
      <el-table-column
        prop="identifiedAmount"
        label="实名认证数"
      ></el-table-column>
      <el-table-column
        label="实名认证率"
        prop="identifiedRate"
      ></el-table-column>
      <el-table-column prop="applyAmount" label="申请件数"></el-table-column>
      <el-table-column
        prop="applyApprovedAmount"
        label="申请通过数"
      ></el-table-column>
      <el-table-column prop="pass" label="申请通过率"></el-table-column>
      <el-table-column
        prop="applyRefusedAmount"
        label="拒绝件数"
      ></el-table-column>
      <el-table-column
        prop="applyCancelledAmount"
        label="取消件数"
      ></el-table-column>
      <el-table-column
        prop="applyApprovingAmount"
        label="审批中件数"
      ></el-table-column>
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
import { getBusTableData } from '@/api/business/getBusRegist.js';

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
    osType: {
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
      this.getBusTableData();
    },
    /**
     * 获取渠道注册报表
     * @param status 是否是父组件调用，是传入true
     */
    async getBusTableData(status = false) {
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
      // 格式化对象
      const res = await getBusTableData(param);
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
