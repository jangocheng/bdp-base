<!--
 * @Description: 渠道注册报表
 -->

<template>
  <div>
    <div class="export-container">
      <h2>支付宝进件数量报表</h2>
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
      <el-table-column align='center' label="终端"  fixed prop="sourceType"/>
      <el-table-column align='center' label="进件" >
        <el-table-column align='center' label="内部员工" prop="applyStaff"/>
        <el-table-column align='center' prop="applyStaffRatio">
          <template slot="header" slot-scope="scope">
            <el-tooltip
              placement="top"
              content="在所在行时间区间内完成注册的用户中,内部员工所在比重"
            >
              <span>内部员工占比 * </span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column align='center' label="社会人士" prop="applySocial"/>
        <el-table-column align='center' label="社会人士占比" prop="applySocialRatio"/>
        <el-table-column align='center' label="小计" prop="applyTotal"/>
      </el-table-column>
      <el-table-column align='center' label="通过" >
        <el-table-column align='center' label="内部员工" prop="passStaff"> </el-table-column>
        <el-table-column align='center' label="内部员工占比" prop="passStaffRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士" prop="passSocial"> </el-table-column>
        <el-table-column align='center' label="社会人士占比" prop="passSocialRatio">
        </el-table-column>
        <el-table-column align='center' label="内部员工通过率" prop="passStaffPassRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士通过率" prop="passSocialPassRatio">
        </el-table-column>
        <el-table-column align='center' label="总体通过率" prop="passPassRatio">
        </el-table-column>
        <el-table-column align='center' label="小计" prop="passTotal"> </el-table-column>
      </el-table-column>
      <el-table-column align='center' label="拒绝" >
        <el-table-column align='center' label="内部员工" prop="refuseStaff"> </el-table-column>
        <el-table-column align='center' label="内部员工占比" prop="refuseStaffRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士" prop="refuseSocial">
        </el-table-column>
        <el-table-column align='center' label="社会人士占比" prop="refuseSocialRatio">
        </el-table-column>
        <el-table-column align='center' label="内部员工拒绝率" prop="refuseStaffPassRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士拒绝率" prop="refuseSocialPassRatio">
        </el-table-column>
        <el-table-column align='center' label="总体拒绝率" prop="refusePassRatio">
        </el-table-column>
        <el-table-column align='center' label="小计" prop="refuseTotal"> </el-table-column>
      </el-table-column>
      <el-table-column align='center' label="取消" >
        <el-table-column align='center' label="内部员工" prop="cancelStaff"> </el-table-column>
        <el-table-column align='center' label="内部员工占比" prop="cancelStaffRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士" prop="cancelSocial">
        </el-table-column>
        <el-table-column align='center' label="社会人士占比" prop="cancelSocialRatio">
        </el-table-column>
        <el-table-column align='center' label="内部员工取消率" prop="cancelStaffPassRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士取消率" prop="cancelSocialPassRatio">
        </el-table-column>
        <el-table-column align='center' label="总体取消率" prop="cancelPassRatio">
        </el-table-column>
        <el-table-column align='center' label="小计" prop="cancelTotal"> </el-table-column>
      </el-table-column>
      <el-table-column align='center' label="审批中" >
        <el-table-column align='center' label="内部员工" prop="approvalStaff">
        </el-table-column>
        <el-table-column align='center' label="内部员工占比" prop="approvalStaffRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士" prop="approvalSocial">
        </el-table-column>
        <el-table-column align='center' label="社会人士占比" prop="approvalSocialRatio">
        </el-table-column>
        <el-table-column align='center' label="内部员工待审率" prop="approvalStaffPassRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士待审率" prop="approvalSocialPassRatio">
        </el-table-column>
        <el-table-column align='center' label="总体待审率" prop="approvalPassRatio">
        </el-table-column>
        <el-table-column align='center' label="小计" prop="approvalTotal"> </el-table-column>
      </el-table-column>
      <el-table-column align='center' label="挂起" >
        <el-table-column align='center' label="内部员工" prop="suspendStaff">
        </el-table-column>
        <el-table-column align='center' label="内部员工占比" prop="suspendStaffRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士" prop="suspendSocial">
        </el-table-column>
        <el-table-column align='center' label="社会人士占比" prop="suspendSocialRatio">
        </el-table-column>
        <el-table-column align='center' label="内部员工挂起率" prop="suspendStaffPassRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士挂起率" prop="suspendSocialPassRatio">
        </el-table-column>
        <el-table-column align='center' label="总体挂起率" prop="suspendPassRatio">
        </el-table-column>
        <el-table-column align='center' label="小计" prop="suspendTotal"> </el-table-column>
      </el-table-column>
      <el-table-column align='center' label="退回补件" >
        <el-table-column align='center' label="内部员工" prop="returnPatchStaff">
        </el-table-column>
        <el-table-column align='center' label="内部员工占比" prop="returnPatchStaffRatio">
        </el-table-column>
        <el-table-column align='center' label="社会人士" prop="returnPatchSocial">
        </el-table-column>
        <el-table-column align='center' label="社会人士占比" prop="returnPatchSocialRatio">
        </el-table-column>
        <el-table-column align='center' label="内部员工退回补件率" prop="returnPatchStaffPassRatio"/>
        <el-table-column align='center' label="社会人士退回补件率" prop="returnPatchSocialPassRatio"/>
        <el-table-column align='center' label="总体退回补件率" prop="returnPatchPassRatio"/>
        <el-table-column align='center' label="小计" prop="returnPatchTotal">
        </el-table-column>
      </el-table-column>
      <el-table-column align='center' label="客服校验--审批中(8月29日新信审系统上线前状态)" >
        <el-table-column align='center' label="内部员工" prop="serviceApprovalStaff"/>
        <el-table-column align='center' label="内部员工占比" prop="serviceApprovalStaffRatio"/>
        <el-table-column align='center' label="社会人士" prop="serviceApprovalSocial"/>
        <el-table-column align='center' label="社会人士占比" prop="serviceApprovalSocialRatio"/>
        <el-table-column align='center' label="内部员工客服校验待审率" prop="serviceApprovalStaffPassRatio"/>
        <el-table-column align='center' label="社会人士客服校验待审率" prop="serviceApprovalSocialPassRatio"/>
        <el-table-column align='center' label="总体客服校验待审率" prop="serviceApprovalPassRatio"/>
        <el-table-column align='center' label="小计" prop="serviceApprovalTotal"/>
      </el-table-column>
      <el-table-column align='center' label="客服校验--退回申请(8月29日新信审系统上线前状态)" >
        <el-table-column align='center' label="内部员工" prop="serviceReturnPatchStaff"/>
        <el-table-column align='center' label="内部员工占比" prop="serviceReturnPatchStaffRatio"/>
        <el-table-column align='center' label="社会人士" prop="serviceReturnPatchSocial"/>
        <el-table-column align='center' label="社会人士占比" prop="serviceReturnPatchSocialRatio"/>
        <el-table-column align='center' label="内部员工客服校验退回补件率" prop="serviceReturnPatchStaffPassRatio"/>
        <el-table-column align='center' label="社会人士客服校验退回补件率" prop="serviceReturnPatchSocialPassRatio"/>
        <el-table-column align='center' label="总体客服校验退回补件率" prop="serviceReturnPatchPassRatio"/>
        <el-table-column align='center' label="小计" prop="serviceReturnPatchTotal">
        </el-table-column>
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
import { getAmtDownload, getAmtQuery } from '@/api/zfb/getApplyData';
import { tableDateWidthMixins } from '@/Mixins/tableDateWidthMixins';

export default {
  mixins: [tableDateWidthMixins],
  computed: {
    baseParam() {
      return {
        startDate: this.startDate
          ? dayjs(this.startDate).format('YYYY/MM/DD')
          : '',
        endDate: this.endDate ? dayjs(this.endDate).format('YYYY/MM/DD') : '',
        dateType: this.time,
        targetType: this.channel,
        sourceType: this.osType,
      };
    },
  },
  methods: {
    // 翻页事件
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getAmtQuery();
    },
    /**
     * @param status 是否是父组件调用，是传入true
     */
    async getAmtQuery(status = false) {
      this.loading = true;
      if (status) this.pageNum = 1;
      const res = await getAmtQuery({
        ...this.baseParam,
        pageNum: Number(this.pageNum),
        pageSize: Number(this.pageSize),
      });
      this.loading = false;
      this.tableData = res.data.list;
      this.total = res.data.total;
    },
    exportExcel() {
      getAmtDownload(this.baseParam);
    },
  },
  mounted() {
    this.getAmtQuery(true);
  },
};
</script>

<style lang="scss" scoped>
.footer-content {
  margin: 20px;
  text-align: right;
}
</style>
