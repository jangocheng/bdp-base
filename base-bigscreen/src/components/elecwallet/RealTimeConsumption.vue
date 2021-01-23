/*
 * @Author: wlhbdp
 * @Description: 每日实时使用金额--业务组件，处理与配置柱状图所需要的设计
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="RealTimeConsumption"></echart>
  </div>
</template>
<script>
import echart from '@/components/echarts.vue';
import echarts from 'echarts';
import { toThousands } from '@/utils/tool';
export default {
  props: {
    RealTimeConsumptionData: Array,
  },
  data() {
    return {
      id: 'RealTimeConsumption',
      option: {
        title: {
          text: '每日实时使用金额',
          textStyle: {
            fontFamily: 'PingFangSC-Regular',
            fontSize: 14,
            color: '#FFFFFF',
            verticalAlign: 'bottom',
          },
          left: 10,
          top: 10,
        },
        textStyle: {
          color: '#FFFFFF',
        },
        color: ['#DD5605', '#9471EC'],
        legend: {
          data: ['信用支付', '薪资支付', '提现'],
          textStyle: {
            color: '#FFFFFF',
          },
          top: '40',
          right: '0',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            crossStyle: {
              color: '#384757',
            },
          },
        },
        grid: {
          left: '15%',
          right: '8%',
          top: 70,
          bottom: 30,
        },
        xAxis: {
          name: '时',
          type: 'category',
          data: [],
          nameGap: '5',
          axisLine: {
            lineStyle: {
              color: '#35517a',
            },
          },
        },
        yAxis: {
          type: 'value',
          name: '金额',
          // nameGap: '5',
          axisLabel: {
            formatter(params) {
              if (params >= 1000 && params < 10000) {
                return `${toThousands(params / 1000)}k`;
              } else if (params >= 10000) {
                return `${toThousands(params / 10000)}w`;
              } else {
                return `${toThousands(params)}`;
              }
              // return toThousands(params);

              // return toThousands(params / 10000);
            },
          },
          minInterval: 10000,
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
            name: '信用支付',
            type: 'bar',
            data: [],
            barWidth: '20%',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: 'rgba(250,160,38,1)' },
                  { offset: 1, color: 'rgba(250,160,38,0.3)' },
                ]),
              },
            },
          },
          {
            name: '薪资支付',
            type: 'bar',
            data: [],
            barWidth: '20%',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: 'rgba(0,102,153,1)' },
                  { offset: 1, color: 'rgba(0,102,153,0.3)' },
                ]),
              },
            },
          },
          {
            name: '提现',
            type: 'bar',
            data: [],
            barWidth: '20%',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: 'rgba(56,183,240,1)' },
                  { offset: 1, color: 'rgba(56,183,240,0.3)' },
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
        this.option.xAxis.data.push(ele.bizHour); //此行代码在上面注释打开时需注释掉
        this.option.series[0].data.push(this._lodash.round(ele.creditPayment));
        this.option.series[1].data.push(this._lodash.round(ele.salaryPayment));
        this.option.series[2].data.push(this._lodash.round(ele.withdraw));
      });
      this.$nextTick(() => {
        if (this.$refs.RealTimeConsumption) {
          this.$refs.RealTimeConsumption.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      if (
        this.RealTimeConsumptionData &&
        this.RealTimeConsumptionData instanceof Array
      ) {
        let temArr = this.RealTimeConsumptionData;
        for (let i = 0; i < temArr.length; i++) {
          for (let j = 0; j < temArr.length - 1 - i; j++) {
            if (temArr[j].bizHour > temArr[j + 1].bizHour) {
              // console.log('每日实时使用金额排序');
              let temp = temArr[j + 1];
              temArr[j + 1] = temArr[j];
              temArr[j] = temp;
            }
          }
        }
        // 取最近5个
        if (temArr.length > 5) {
          temArr = temArr.slice(-5);
        }
        return temArr;
      }
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
