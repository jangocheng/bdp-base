/*
 * @Author: wlhbdp
 * @Description: 电子钱包大屏监控页面窗体组件
 */

<template>
  <div id="elecwallet">
    <div class="left-block">
      <div>
        <grid>
          <RealName
            id="realName"
            class="echartsContent"
            :RealNameData="registerAndIdentifyData"
          ></RealName>
          <div class="sum-content">
            <div>
              <p>注册总额</p>
              <span>{{ registerSum }}</span>
            </div>
            <div>
              <p>实名总额</p>
              <span>{{ identifiedSum }}</span>
            </div>
          </div>
        </grid>
      </div>
      <div>
        <grid>
          <userClassify
            id="userClassify"
            class="echartsContent"
            :userClassifyData="statisticOfYestodayAppliedUserData"
          ></userClassify>
        </grid>
      </div>
      <div>
        <grid>
          <RealTimeConsumption
            id="RealTimeConsumption"
            class="echartsContent"
            :RealTimeConsumptionData="dailyRealTimeConsumptionData"
          ></RealTimeConsumption>
          <div class="sum-content">
            <div>
              <p>信用支付总额</p>
              <span>{{ creditPaymentSum }}</span>
            </div>
            <div>
              <p>薪资支付总额</p>
              <span>{{ salaryPaymentSum }}</span>
            </div>
            <div>
              <p>提现总额</p>
              <span>{{ withdrawSum }}</span>
            </div>
          </div>
        </grid>
      </div>
    </div>
    <div class="center-block">
      <PayAndWithdrawal
        class="echartsContent"
        :PayAndWithdrawalData="userUsageData"
      ></PayAndWithdrawal>
    </div>
    <div class="right-block">
      <div>
        <grid>
          <sevenChannel
            id="sevenChannel"
            class="echartsContent"
            :sevenChannelData="distOfChannelApplyData"
          ></sevenChannel>
        </grid>
      </div>
      <div>
        <grid>
          <sevenPassRate
            id="sevenPassRate"
            class="echartsContent"
            :sevenPassRateData="passingRateOfNewUserData"
          ></sevenPassRate>
        </grid>
      </div>
      <div>
        <grid>
          <sevenExpense
            id="sevenExpense"
            class="echartsContent"
            :sevenExpenseData="distOfConsumptionData"
          ></sevenExpense>
        </grid>
      </div>
    </div>
  </div>
</template>

<script>
import RealName from '@/components/elecwallet/RealName.vue';
import userClassify from '@/components/elecwallet/userClassify.vue';
import RealTimeConsumption from '@/components/elecwallet/RealTimeConsumption.vue';
import PayAndWithdrawal from '@/components/elecwallet/PayAndWithdrawal/PayAndWithdrawal.vue';
import sevenChannel from '@/components/elecwallet/sevenChannel.vue';
import sevenPassRate from '@/components/elecwallet/sevenPassRate.vue';
import sevenExpense from '@/components/elecwallet/sevenExpense.vue';
import grid from '@/components/grid.vue';
import getUrl from '@/utils/getUrl';
import { toThousands } from '@/utils/tool';

import {
  dailyRealTimeConsumption
} from '../api/elecwallet.js';
import { syncData } from '../api/RealTimeAPI';

export default {
  components: {
    RealName,
    userClassify,
    RealTimeConsumption,
    PayAndWithdrawal,
    sevenChannel,
    sevenPassRate,
    sevenExpense,
    grid,
  },
  data() {
    return {
      stompClientChanel: [
        'registerAndIdentify', //每日实时注册和实名认证人数
        'statisticOfYestodayAppliedUser', //昨日申请通过用户分类统计
        // 'dailyRealTimeConsumption', //每日实时使用金额
        'distOfChannelApply', //本月渠道累计进件分布
        'passingRateOfNewUser', //本月新用户通过率
        'distOfConsumption', //本月累计消费情况
        'userUsage', //电子钱包实时支付和实时提现地理位置分布
      ],
      registerAndIdentifyData: null,
      statisticOfYestodayAppliedUserData: null,
      dailyRealTimeConsumptionData: null,
      distOfChannelApplyData: null,
      passingRateOfNewUserData: null,
      distOfConsumptionData: null,
      userUsageData: null,

      // 每日实时注册和实名人数，注册总额、实名总额
      registerSum: 0,
      identifiedSum: 0,
      //每日实时使用金额总额数据
      creditPaymentSum: 0,
      salaryPaymentSum: 0,
      withdrawSum: 0,
    };
  },
  created() {
    this.initWebSocket();
    this.getDailyRealTimeConsumption();
  },
  methods: {
    async getDailyRealTimeConsumption() {
      const res = await dailyRealTimeConsumption();
      this.dailyRealTimeConsumptionData = res.rows
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
        case 'registerAndIdentify':
          console.log('每日实时注册和实名认证人数', data);
          break;
        case 'statisticOfYestodayAppliedUser':
          console.log('昨日申请通过用户分类统计', data);
          break;
          // case 'dailyRealTimeConsumption':
          //   console.log('每日实时使用金额', data);
          break;
        case 'distOfChannelApply':
          console.log('本月渠道累计进件分布', data);
          break;
        case 'passingRateOfNewUser':
          console.log('本月新用户通过率', data);
        case 'distOfConsumption':
          console.log('本月累计消费情况', data);
        case 'userUsage':
          console.log('电子钱包实时支付和实时提现地理位置分布', data);
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
        this.stompClient.disconnect();
      }
    },
  },
  watch: {
    registerAndIdentifyData(value) {
      let registerSum, identifiedSum;
      this.registerAndIdentifyData.forEach(ele => {
        registerSum = (registerSum || 0) + ele.register;
        identifiedSum = (identifiedSum || 0) + ele.identified;
      });
      this.registerSum = toThousands(registerSum);
      this.identifiedSum = toThousands(identifiedSum);
    },
    dailyRealTimeConsumptionData() {
      // 处理总额数据;
      let creditPaymentSum, salaryPaymentSum, withdrawSum;
      const temLength = this.dailyRealTimeConsumptionData.length

      this.creditPaymentSum = this.dailyRealTimeConsumptionData[0].creditPaymentSum
      if (parseInt(this.creditPaymentSum) !== this.creditPaymentSum) {
        this.creditPaymentSum = parseInt(this.creditPaymentSum) + 1
      }
      this.salaryPaymentSum = this.dailyRealTimeConsumptionData[0].salaryPaymentSum
      if (parseInt(this.salaryPaymentSum) !== this.salaryPaymentSum) {
        this.salaryPaymentSum = parseInt(this.salaryPaymentSum) + 1
      }
      this.withdrawSum = this.dailyRealTimeConsumptionData[0].withdrawSum
      if (parseInt(this.withdrawSum) !== this.withdrawSum) {
        this.withdrawSum = parseInt(this.withdrawSum) + 1
      }
      // this.salaryPaymentSum = toThousands(salaryPaymentSum);
      // this.withdrawSum = toThousands(withdrawSum);
    },
  },
  beforeDestroy() {
    console.log('断开连接');
    // 页面离开时断开连接,清除定时器
    this.disconnect();
  },
};
</script>

<style lang="less">
.echartsContent {
  width: 100%;
  height: 100%;
}
#elecwallet {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  .left-block,
  .right-block {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    > div {
      // flex: 1;
      height: 33%;
    }
  }
  #realName,
  #RealTimeConsumption {
    width: 80%;
  }
  .sum-content {
    flex: 1;
    padding-top: 10%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    > div {
      flex: 1;
      color: aliceblue;
      > p {
        font-size: 8px;
        transform: scale(0.8);
      }
      > span {
        font-size: 10px;
        transform: scale(0.8);
      }
    }
  }
  .center-block {
    width: 44%;
    margin: 0 10px;
  }
  > div {
    height: 100%;
  }
  // div {
  //   border: 1px red solid;
  // }
}
</style>
