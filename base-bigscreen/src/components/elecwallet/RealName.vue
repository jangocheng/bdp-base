/*
 * @Author: wlhbdp
 * @Description: 每日实时注册和实名认证人数--业务组件，处理与配置柱状图所需要的设计
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="realName"></echart>
  </div>
</template>
<script>
import echarts from 'echarts';
import echart from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    RealNameData: Array,
  },
  data() {
    return {
      id: 'realName',
      option: {
        title: {
          text: '每日实时注册和实名认证人数',
          textStyle: {
            fontFamily: 'PingFangSC-Regular',
            fontSize: 14,
            color: '#FFFFFF',
            verticalAlign: 'bottom',
          },
          left: 10,
          top: 10,
        },
        grid: {
          left: '15%',
          right: '12%',
          top: 70,
          bottom: 30,
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
        textStyle: {
          color: '#FFFFFF',
        },
        color: ['#38B7F0', '#9471EC'],
        legend: {
          data: ['注册', '实名'],
          textStyle: {
            color: '#FFFFFF',
          },
          top: '40',
          right: '0',
        },
        xAxis: {
          type: 'category',
          name: '时',
          data: [],
          axisLine: {
            lineStyle: {
              color: '#35517a',
            },
          },
        },
        yAxis: {
          type: 'value',
          name: '人数',
          minInterval: 1,
          axisLabel: {
            formatter(params) {
              if (params >= 1000 && params < 50000) {
                return `${toThousands(params / 1000)}k`;
              } else if (params > 50000) {
                return `${toThousands(params / 10000)}w`;
              } else {
                return `${toThousands(params)}`;
              }
              // return toThousands(params);
            },
          },
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
            name: '注册',
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
          {
            name: '实名',
            type: 'bar',
            data: [],
            barWidth: '30%',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: '#9876EF',
                  },
                  {
                    offset: 1,
                    color: '#754FD9',
                  },
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
        this.option.xAxis.data.push(ele.time); //此行代码在上面注释打开时需注释掉
        this.option.series[0].data.push(ele.register);
        this.option.series[1].data.push(ele.identified);
      });
      this.$nextTick(() => {
        if (this.$refs.realName) {
          this.$refs.realName.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      let temArr = this.RealNameData;
      if (this.RealNameData) {
        // 根据时间进行排序
        for (let i = 0; i < temArr.length; i++) {
          for (let j = 0; j < temArr.length - 1 - i; j++) {
            if (temArr[j].time > temArr[j + 1].time) {
              // console.log('每日实时注册和实名认证人数排序');
              const temp = temArr[j + 1];
              temArr[j + 1] = temArr[j];
              temArr[j] = temp;
            }
          }
        }
        // 取最近5个
        if (temArr.length > 5) {
          temArr = temArr.slice(-5);
        }
      }
      return temArr;
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
