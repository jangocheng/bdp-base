/*
 * @Author: wlhbdp
 * @Description: 电子钱包数据看板
 */

<template>
  <div id="loan">
    <div>
      <grid>
        <loanClinchNum
          id="loanClinchNum"
          class="echartsContent"
          :loanClinchNumData="hourlyDealStatisticData"
        ></loanClinchNum>
      </grid>
    </div>
    <div>
      <grid>
        <loanSalesAmount
          id="loanSalesAmount"
          class="echartsContent"
          :loanSalesAmountData="hourlySumStatisticData"
        ></loanSalesAmount>
      </grid>
    </div>
    <div>
      <grid>
        <loanCitySalesRanking
          id="loanCitySalesRanking"
          class="echartsContent"
          :loanCitySalesRankingData="realTimeCityRankData"
        ></loanCitySalesRanking>
      </grid>
    </div>
    <div class="w50">
      <grid>
        <loanSevenBrandSalesRatio
          id="loanSevenBrandSalesRatio"
          class="echartsContent"
          :loanSevenBrandSalesRatioData="recentSevenDayBrandData"
        ></loanSevenBrandSalesRatio>
      </grid>
    </div>
    <div class="w50">
      <grid>
        <loanSevenClassifiedSales
          id="loanSevenClassifiedSales"
          class="echartsContent"
          :loanSevenClassifiedSalesData="recentSevenDayChannelData"
        ></loanSevenClassifiedSales>
      </grid>
    </div>
    <div></div>
  </div>
</template>

<script>
import grid from '@/components/grid.vue';
import loanClinchNum from '@/components/loan/loanClinchNum.vue';
import loanSalesAmount from '@/components/loan/loanSalesAmount.vue';
import loanCitySalesRanking from '@/components/loan/loanCitySalesRanking.vue';
import loanSevenBrandSalesRatio from '@/components/loan/loanSevenBrandSalesRatio.vue';
import loanSevenClassifiedSales from '@/components/loan/loanSevenClassifiedSales.vue';
import getUrl from '@/utils/getUrl';

export default {
  data() {
    return {
      stompClientChanel: [
        'hourlyDealStatistic', //实时成交单数
        'hourlySumStatistic', //实时销售金额
        'realTimeCityRank', //实际城市销售排行数据通道
        'recentSevenDayBrand', //本月各品牌销售占比
        'recentSevenDayChannel', //本月商品分类销售雷达图
      ],
      hourlyDealStatisticData: null,
      hourlySumStatisticData: null,
      realTimeCityRankData: null,
      recentSevenDayBrandData: null,
      recentSevenDayChannelData: null,
    };
  },
  created() {
    this.initWebSocket();
  },
  methods: {
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
            this.updataEchartData(ele, temArr);
            // this.log(ele, temArr);
          });
          // 往通道发送请求数据
          this.stompClient.send(`/app/fbdShop/${ele}`, {}, 'first send');
        });
      });
    },
    log(params, data) {
      switch (params) {
        case 'hourlyDealStatistic':
          console.log('实时成交单数', data);
          break;
        case 'hourlySumStatistic':
          console.log('实时销售金额', data);
          break;
        case 'realTimeCityRank':
          console.log('实际城市销售排行数据通道', data);
          break;
        case 'recentSevenDayBrand':
          console.log('本月各品牌销售占比', data);
          break;
        case 'recentSevenDayChannel':
          console.log('本月商品分类销售雷达图', data);
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
  beforeDestroy() {
    console.log('断开连接');
    // 页面离开时断开连接,清除定时器
    this.disconnect();
  },
  components: {
    grid,
    loanClinchNum,
    loanSalesAmount,
    loanCitySalesRanking,
    loanSevenBrandSalesRatio,
    loanSevenClassifiedSales,
  },
};
</script>

<style lang="less">
.echartsContent {
  width: 100%;
  height: 100%;
}
#loan {
  width: 100%;
  height: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  //   align-content: center;
  > div {
    width: 32%;
    height: 45%;
  }
  // .w50 {
  //   width: 45%;
  //   height: 45%;
  // }
}
</style>
