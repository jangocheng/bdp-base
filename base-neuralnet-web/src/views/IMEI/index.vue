<!--
 * @Description: 同一设备登录用户查询
 -->

<template>
  <div class="app-container">
    <el-card class="header-card" shadow="hover">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            v-model="IMEI_id"
            placeholder="请输入要查询的设备ID"
            clearable
            prefix-icon="el-icon-search"
          ></el-input>
        </el-col>
        <el-col :span="18">
          <div class="fr">
            <el-button
              icon="el-icon-search"
              size="medium"
              type="primary"
              plain
              @click="() => this.queryIMEIList()"
              >查询</el-button
            >
          </div>
        </el-col>
      </el-row>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <el-card class="message" v-if="messageStatus">
        <div v-if="messageData.label ">
          用户ID:
          <span>{{ messageData.label }}</span>
        </div>
        <div v-if="messageData.group">
          角色:
          <span>{{ messageData.group }}</span>
        </div>
        <div v-if="messageData.staffStatus">
          工作状态:
          <span>{{ messageData.staffStatus|staffStatus }}</span>
        </div>
        <div v-if="messageData.overdueStatus">
          逾期状态:
          <span>{{ messageData.overdueStatus |overdueStatus}}</span>
        </div>
        <!--        关系节点显示-->
        <div v-if="messageData.id">
          ID:
          <span>{{ messageData.id }}</span>
        </div>
        <div v-if="messageData.name">
          name:
          <span>{{ messageData.name }}</span>
        </div>
        <div v-if="messageData.contactType">
          联系人类型:
          <span>{{ messageData.contactType|contactType }}</span>
        </div>
      </el-card>
      <e-graph
        id="IMEIEcharts"
        @nodeClick="nodeClick"
        @edgeClick="edgeClick"
        :option="option"
        class="echart-block"
      ></e-graph>
    </el-card>
  </div>
</template>

<script>
import eGraph from '@/components/E-Graph';
import { getLoadCustomerUsedSameDevice } from '@/api/IMEI.js';

export default {
  data() {
    return {
      // IMEI_id: '00A3A8D7-59D3-4074-82CA-0EE6FE4A3FCE', //输入查询的设备号ID,
      IMEI_id: '', //输入查询的设备号ID,
      data: [],
      links: [],
      /**
       * 提示框所用数据
       */
      messageData: null,
    };
  },
  computed: {
    messageStatus() {
      // return !!this.messageData;
      return !!this.messageData;
    },
    option() {
      return {
        animationDuration: 0,
        animationEasingUpdate: 'quinticInOut',
        series: {
          type: 'graph',
          roam: true, //缩放移动
          symbolSize: 20,
          draggable: true,
          layout: 'force',
          force: {
            repulsion: 500,
            gravity: 0.1,
            edgeLength: 15,
            layoutAnimation: true,
          },
          legend: {
            x: 'center',
          },
          focusNodeAdjacency: true,
          edgeSymbol: ['circle', 'arrow'],
          edgeSymbolSize: [3, 7],
          edgeLabel: {
            normal: {
              show: true,
              textStyle: {
                fontSize: 10,
              },
              formatter: '{c}',
            },
          },
          // 对节点上的文本进行格式化
          label: {
            normal: {
              show: true,
              position: 'bottom',
              formatter: '{b}',
              fontSize: 10,
              fontStyle: '600',
            },
          },
          data: this.data,
          links: this.links,
          lineStyle: {
            normal: {
              opacity: 0.9,
              width: 1.5,
              curveness: 0,
            },
          },
        },
      };
    },
  },
  // mounted() {
  //   this.queryIMEIList();
  // },
  methods: {
    async queryIMEIList() {
      if (!this.IMEI_id) {
        this.$message({
          message: '请输入设备号',
          type: 'warning',
        });
        return;
      }
      // 验证设备号的合理性
      // if (Check.isIMEI(this.IMEI_id)) {
      this.data = [];
      this.links = [];
      const res = await getLoadCustomerUsedSameDevice(this.IMEI_id);
      if (res.data.data.length > 0) {
        res.data.data.forEach(ele => {
          if (ele.category !== '0') {
            ele.itemStyle = {
              color: '#334553',
            };
          }
        });
        this.data = res.data.data;
      }
      if (res.data.links.length > 0) this.links = res.data.links;
    },
    nodeClick(params) {
      if (!params.data) return;
      const { label, group, staffStatus, overdueStatus } = params.data;
      this.messageData = {
        label,
        group,
        staffStatus,
        overdueStatus,
      };
    },
    edgeClick(params) {
      if (!params.data) return;
      const { id, name, contactType } = params.data;
      this.messageData = {
        id,
        name,
        contactType,
      };
    },
  },
  components: {
    eGraph,
  },
};
</script>


<style lang="scss" scoped>
// 与父元素宽高同步
.app-container {
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  position: absolute;
  display: flex;
  flex-direction: column;

  .header-card {
    margin-bottom: 20px;
  }
  .main-card {
    flex: 1;
    position: relative;
    // 为容器组件赋予宽高
    .echart-block {
      position: absolute;
      top: 20px;
      left: 20px;
      right: 20px;
      bottom: 20px;
    }
    // 提示框组件
    .message {
      position: absolute;
      top: 20px;
      left: 20px;
      /*width: 200px;*/
      z-index: 999;
      font-size: 14px;
      line-height: 24px;
    }
  }
}
</style>
