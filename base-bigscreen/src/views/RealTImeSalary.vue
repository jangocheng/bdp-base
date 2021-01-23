/*
 * 实时薪资着陆页
 */

 <template>
  <div id="realtimesalary">
    <div class="echarts-content">
      <div class="content">
        <div>
          <grid>
            <!-- 各BU交易次数 -->
            <echartData
              id="countData"
              :legendArr="echartCountDataLegendArr"
              :Data="countData"
              class="echartsContent"
            ></echartData>
          </grid>
        </div>
        <div>
          <grid>
            <!-- 各BU交易金额 -->
            <echartData
              id="amountData"
              :legendArr="echartAmountDataLegendArr"
              :Data="amountData"
              class="echartsContent"
            ></echartData>
          </grid>
        </div>
        <div>
          <grid>
            <!-- 各BU推送用户数据 -->
            <testData
              id="syncData"
              :legendArr="echartSyncDataLegendArr"
              :Data="syncData"
              class="echartsContent"
            >
            </testData>
          </grid>
        </div>
      </div>
      <div class="map-content">
        <div>
          <grid>
            <!-- 各BU激活完成率 -->
            <echartGauge
              class="gauge-content"
              v-for="(item, index) in completeDataArr"
              :id="item.groupId"
              :Data="item"
              :key="index"
            ></echartGauge>
          </grid>
        </div>
      </div>
    </div>
    <div class="footer-text">
      <div v-for="(value, key) in hint" :key="key">
        <p class="title">{{ key }}</p>
        <!-- <NumberGrow :value="value"></NumberGrow> -->
        <span class="number">{{ value | toThousands }}</span>
        <!-- <animated-number
          class="number"
          :value="value"
          :formatToPrice="formatToPrice"
          :duration="800"
        />-->
      </div>
    </div>
  </div>
</template>
 
 <script>
import getUrl from '@/utils/getUrl';
import AnimatedNumber from 'animated-number-vue';
import grid from '@/components/grid.vue';
import realTimeRrade from '@/components/realTImeSalary/realTimeRrade.vue';
import echartFunnel from '@/components/realTImeSalary/echartFunnel.vue';
import NumberGrow from '@/components/NumberGrow.vue';
// import echartSyncDate from '../components/realTImeSalary/echartSyncDate'; //各BU推送数据
import { toThousands } from '@/utils/tool';
import echartData from '../components/realTImeSalary/echartData'; //各BU交易金额
import echartGauge from '../components/realTImeSalary/echartGauge'; //各BU交易金额
import testData from '../components/realTImeSalary/testData'; //各BU交易金额
import {
  syncData,
  consumeAmountData,
  consumeCountData,
  summaryData,
  completeData,
} from '../api/RealTimeAPI.js';

export default {
  data() {
    return {
      stompClientChanel: ['userUsage'],
      userUsageData: null,
      // 整体情况数据
      hint: {
        累计推送用户数: 0,
        // 累计激活用户数: 0,
        累计激活用户数: 0,
        电子钱包申请数: 0,
        申请通过数: 0,
        累计消费人数: 0,
        '累计消费金额(元)': 0,
      },
      // 各BU交易金额
      amountData: [],
      echartAmountDataLegendArr: ['今日交易金额', '累计交易金额'],
      echartAmountDataColorArr: ['#E6CB4B', '#68ED90'],
      echartAmountDataSeriesColor: ['#009900', '#009933', '#00CC00', '#00CC33'],
      // 各BU交易次数
      countData: [],
      echartCountDataLegendArr: ['今日交易次数', '累计交易次数'],
      echartCountDataColorArr: ['#38B7F0', '#9471EC'],
      echartCountDataSeriesColor: ['#39CFF6', '#2580D2', '#9876EF', '#754FD9'],
      // 各BU推送用户数据`
      syncData: [],
      echartSyncDataLegendArr: ['累计推送用户数'],
      // 各BU完成率
      completeDataArr: [],
      // 计时器对象
      // intervalObj: 0,
    };
  },
  created() {
    // this.initWebSocket();
    this.getSyncData();
    this.getConsumeAmountData();
    this.getConsumeCountData();
    this.getSummaryData();
    this.getCompleteData();
  },
  watch: {
    hint(value) {
      console.log(value);
    },
  },
  filters: {
    toThousands(value) {
      return toThousands(value);
    },
  },
  methods: {
    // 请求BU推送用户数据
    async getSyncData() {
      const res = await syncData();
      this.syncData = res.rows;
    },
    // 请求各BU交易金额
    async getConsumeAmountData() {
      const res = await consumeAmountData();
      this.amountData = res.rows;
    },
    // 请求各BU交易次数
    async getConsumeCountData() {
      const res = await consumeCountData();
      this.countData = res.rows;
    },
    // 请求各BU完成率
    async getCompleteData() {
      const res = await completeData();
      let temArr = res.rows;
      this.completeDataArr = [];
      temArr.forEach(ele => {
        if (ele.activeAmt !== null && ele.syncAmt !== null && ele.groupId) {
          this.completeDataArr.push(ele);
        }
      });
    },
    // 整体情况
    async getSummaryData() {
      const res = await summaryData();
      this.hint.累计推送用户数 = res.obj.totalCount || 0;
      // this.hint.累计激活用户数 = res.obj.totalRegister || 0;
      this.hint.累计激活用户数 = res.obj.totalActive || 0;
      this.hint.电子钱包申请数 = res.obj.totalCreditRegister || 0;
      this.hint.申请通过数 = res.obj.totalApproved || 0;
      this.hint.累计消费人数 = res.obj.totalConsumeCount || 0;
      this.hint['累计消费金额(元)'] =
        res.obj.totalConsumeAmount.toFixed(2) || 0;
    },
    formatToPrice(value) {
      return `R$ ${value.toFixed(2)}`;
    },
    initWebSocket() {
      this.connection();
    },
    connection() {
      // 建立连接对象
      //连接服务端提供的通信接口，连接以后才可以订阅广播消息和个人消息
      this.objSocket = this.Socket(getUrl('VUE_APP_LIANGXIAO'));
      // 获取STOMP子协议的客户端对象
      this.stompClient = this.Stomp.over(this.objSocket);
      this.stompClient.debug = null;
      this.stompClient.connect({}, () => {
        this.stompClientChanel.forEach(ele => {
          // 订阅消息通道
          this.stompClient.subscribe(`/rtbi/elecwallet/${ele}`, msg => {
            const temArr = JSON.parse(msg.body).data;
            if (!temArr) {
              return;
            }
            this.updataEchartData(ele, temArr);
            // this.log(ele, temArr);
          });
          // 往通道发送请求数据
          this.stompClient.send(`/app/fbd/${ele}`, {}, 'first send');
        });
      });
    },
    log(params, data) {
      switch (params) {
        case 'userUsage':
          data.forEach(ele => {
            if (ele.usageType === '0201') {
              console.log('电商收入实时支付地理位置分布', data);
            }
          });
          break;
      }
    },
    /**
     * 更新echarts数据
     */
    updataEchartData(url, param) {
      this[`${url}Data`] = param;
    },
    /**
     * 断开连接
     */
    disconnect() {
      if (this.stompClient != null) {
        console.log('触发断开');
        this.stompClient.disconnect();
      }
    },
  },
  beforeDestroy() {
    // 页面离开时断开连接,断开连接
    this.disconnect();
  },
  computed: {},
  components: {
    grid,
    realTimeRrade,
    AnimatedNumber,
    echartData,
    echartFunnel,
    echartGauge,
    testData
  },
};
</script> 
<style lang="less">
#realtimesalary {
  width: 100%;
  height: calc(100% - 40px);
  display: flex;
  flex-direction: column-reverse;
  .echarts-content {
    flex: 1;
    display: flex;
    justify-content: center;
    flex-direction: column-reverse;
    .map-content {
      // width: 38%;
      height: 60%;
      > div {
        width: 100%;
        height: 100%;
        > #grid {
          display: flex;
          justify-content: space-between;
          .gauge-content {
            // flex: 1;
            // height: 100%;
            padding: 5px 10px;
            display: flex;
            justify-content: center;
            align-items: center;
            > div {
              width: calc(100% - 10px);
              height: calc(100% - 20px);
            }
          }
        }
      }
    }
    .map-content {
      .content {
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
        .gauge-content {
          width: 30% !important;
          height: 48% !important;
        }
      }
    }
    > .content {
      height: 40%;
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      > div {
        flex: 1;
        // width: 30%;
        height: 100%;
      }
    }
  }

  .footer-text {
    width: 100%;
    height: 8%;
    color: aliceblue;
    display: flex;
    justify-content: space-between;
    > div {
      flex: 1;
      text-align: center;
      .title {
        font-size: 14px;
      }
      .number {
        font-size: 22px;
        font-weight: bold;
      }
    }
  }
  .echartsContent {
    // width: 100%;
    height: 100%;
    padding: 5px 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    > div {
      width: calc(100% - 10px);
      height: calc(100% - 20px);
      // width: 100%;
      // height: 100%;
    }
  }
}
</style>