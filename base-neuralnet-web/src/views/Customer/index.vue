<!--

 * @Description: 客户一度人脉逾期查询
 -->

<template>
  <div class="app-container">
    <el-card class="header-card" shadow="hover">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            v-model="phoneNumber"
            placeholder="请输入要查询的手机号"
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
              @click="() => this.queryCustomerList()"
              >查询</el-button
            >
          </div>
        </el-col>
      </el-row>
    </el-card>
    <el-card class="main-card" shadow="hover">
      <el-card class="message" v-if="message1Status">
        <div v-if="messageData.label ">
          名称:
          <span>{{ messageData.label }}</span>
        </div>
        <div v-if="messageData.group">
          角色:
          <span>{{ messageData.group }}</span>
        </div>
        <div v-if="messageData.staffStatus">
          工作状态:
          <span>{{ messageData.staffStatus |staffStatus }}</span>
        </div>
        <div v-if="messageData.overdueStatus">
          逾期状态:
          <span>{{ messageData.overdueStatus|overdueStatus }}</span>
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
          <span>{{ messageData.contactType |contactType }}</span>
        </div>
      </el-card>
      <e-graph
        id="Curstomercharts"
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
import { loadSuspiciousCustomersByPhoneNumber } from '@/api/customer.js';
import Check from '@/utils/check.js';

export default {
  data() {
    return {
      phoneNumber: '', //输入查询的手机号,
      data: [],
      links: [],
      /**
       * 提示框所用数据
       */
      messageData: null,
    };
  },
  computed: {
    message1Status() {
      return !!this.messageData;
    },
    option() {
      return {
        animationDuration: 0,
        animationEasingUpdate: 'quinticInOut',
        series: [
          {
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
            tooltip: {},
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
        ],
      };
    },
  },
  methods: {
    async queryCustomerList() {
      console.log(this.phoneNumber);
      if (!this.phoneNumber) {
        this.$message({
          message: '请填写手机号',
          type: 'warning',
        });
        return;
      }
      if (Check.isPhone(this.phoneNumber)) {
        this.data = [];
        this.links = [];
        const res = await loadSuspiciousCustomersByPhoneNumber(
          this.phoneNumber,
        );
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
      } else {
        this.$message({
          message: '手机号有误，请核实',
          type: 'error',
        });
        this.data = [];
        this.links = [];
        this.messageData = null;
      }
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
      console.log(params);
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
      z-index: 999;
      font-size: 14px;
      line-height: 24px;
    }
  }
}
</style>
