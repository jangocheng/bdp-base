/*
 * @Author: wlhbdp
 * @Description: 电子钱包----实时成交单数
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="ffqClinchNum"></echart>
  </div>
</template>
<script>
import echarts from 'echarts';
import echart from '@/components/echarts.vue';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    ffqClinchNumData: Array,
  },
  data() {
    return {
      id: 'ffqClinchNum',
      option: {
        title: {
          text: '实时成交单数',
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
          //   return `${prams[0].name}点<br />单数:${toThousands(prams[0].data)}`;
          // },
        },
        grid: {
          left: '12%',
          right: '20%',
          top: 70,
          bottom: 40,
        },
        textStyle: {
          color: '#FFFFFF',
        },
        color: ['#38B7F0', '#9471EC'],
        yAxis: {
          type: 'value',
          name: '单数',
          axisLabel: {
            formatter(params) {
              if (params >= 1000 && params <= 50000) {
                return `${toThousands(params / 1000)}k`;
              } else if (params > 50000) {
                return `${toThousands(params / 10000)}w`;
              } else {
                return `${toThousands(params)}`;
              }
            },
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
        xAxis: {
          name: '时间',
          type: 'category',
          data: [],
          axisLine: {
            lineStyle: {
              color: '#35517a',
            },
          },
        },
        series: [
          {
            type: 'bar',
            data: [],
            barWidth: '30%',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    { offset: 0, color: '#39CFF6' },
                    { offset: 1, color: '#2580D2' },
                  ],
                  false,
                ),
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
        this.option.xAxis.data.push(ele.time);
        this.option.series[0].data.push(ele.deals);
      });
      this.$nextTick(() => {
        if (this.$refs.ffqClinchNum) {
          this.$refs.ffqClinchNum.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      // console.log(this.ffqClinchNumData);
      if (this.ffqClinchNumData && this.ffqClinchNumData instanceof Array) {
        let temArr = this.ffqClinchNumData;
        for (let i = 0; i < temArr.length; i++) {
          for (let j = 0; j < temArr.length - 1 - i; j++) {
            if (temArr[j].time > temArr[j + 1].time) {
              // console.log('实时成交单数排序');
              let temp = temArr[j + 1];
              temArr[j + 1] = temArr[j];
              temArr[j] = temp;
            }
          }
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
