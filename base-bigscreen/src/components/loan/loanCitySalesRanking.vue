/*
 * @Author: wlhbdp
 * @Description: 实际城市销售排行
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="loanCitySalesRanking"></echart>
  </div>
</template>
<script>
import echarts from 'echarts';
import echart from '@/components/echarts.vue';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    loanCitySalesRankingData: Array,
  },
  data() {
    return {
      id: 'loanCitySalesRanking',
      option: {
        title: {
          text: '实际城市销售排行',
          textStyle: {
            fontFamily: 'PingFangSC-Regular',
            fontSize: 14,
            color: '#FFFFFF',
            verticalAlign: 'bottom',
          },
          left: 10,
          top: 10,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            crossStyle: {
              color: '#384757',
            },
          },
          // formatter(prams) {
          //   return `${prams[0].name}<br />单数:${toThousands(prams[0].data)}笔`;
          // },
        },
        grid: {
          left: '12%',
          top: 70,
          bottom: 40,
        },
        textStyle: {
          color: '#FFFFFF',
        },
        xAxis: {
          type: 'category',
          data: [],
          axisLine: {
            lineStyle: {
              color: '#35517a',
            },
          },
        },
        yAxis: {
          type: 'value',
          name: '单数',
          axisLabel: {
            formatter(params) {
              if (params >= 1000 && params < 50000) {
                return `${toThousands(params / 1000)}k`;
              } else if (params > 50000) {
                return `${toThousands(params / 10000)}w`;
              } else {
                return `${toThousands(params)}`;
              }
            },
            // return toThousands(params);
          },
          minInterval: 1,
          axisLine: {
            lineStyle: {
              color: '#35517a',
            },
          },
          splitLine: {
            show: false,
          },
        },
        series: [
          {
            type: 'bar',
            data: [],
            barWidth: '30%',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: 'rgba(250,160,38,1)' },
                  { offset: 1, color: 'rgba(250,160,38,0.3)' },
                ]),
              },
            },
          },
        ],
      },
    };
  },
  methods: {
    /**
     * 更新echarts配置数据
     */
    updataOptionData() {
      /**
       * TODO:下列代码是实现高频数据情况下的X从右往左的推移效果，勿删可留
       */
      // const xData = [];
      // if (xData.length > 0) {
      //   xData.shift();
      //   xData.push(this.data[this.data.length - 1].time);
      // } else {
      //   this.data.forEach(ele => {
      //     xData.push(ele.time);
      //   });
      // }
      // this.option.series.forEach(ele => {
      //   ele.data = [];
      // });
      this.option.series.map(ele => {
        const temObj = ele;
        temObj.data = [];
        return temObj;
      });
      this.option.xAxis.data = [];
      this.data.forEach(ele => {
        // xData.push(ele.time); //此行代码在上面注释打开时需注释掉
        this.option.xAxis.data.push(ele.cityName); //此行代码在上面注释打开时需注释掉
        this.option.series[0].data.push(ele.sales);
      });
      this.$nextTick(() => {
        if (this.$refs.loanCitySalesRanking) {
          this.$refs.loanCitySalesRanking.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      return this.loanCitySalesRankingData;
    },
  },
  watch: {
    data(value) {
      if (value) {
        this.updataOptionData();
      }
    },
  },
  components: {
    echart,
  },
};
</script>
