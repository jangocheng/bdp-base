
<template>
  <div>
    <echart :id="id" :option="option" ref="amountData"></echart>
  </div>
</template>
<script>
import echarts from 'echarts';
import echart from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    /**
     * 数据集合
     * [{
     *  groupId:'',//集合名称,xAixis轴数据
     *  daily:'',//到今日的数据
     *  total:'',//累计数据
     * }]
     */
    Data: Array,
    //图例的名称
    legendArr: Array,
    //用来生成渲染echarts
    id: '',
    //色盘
    color: Array,
    //渐进柱状图
    // seriesColor: Array,
  },
  data() {
    return {
      // legendArr: ['今日交易金额', '累计交易金额'],
      option: {
        // title: {
        //   text: '分类雷达图',
        // },
        calculable: true,
        textStyle: {
          color: '#FFFFFF',
        },
        grid: {
          // left: '10%',
          // right: '10%',
          top: 60,
          bottom: 30,
        },
        color: this.color,
        legend: {
          data: this.legendArr,
          right: 20,
          textStyle: {
            color: '#FFFFFF',
          },
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
        xAxis: [
          {
            type: 'category',
            data: [],
            axisTick: {
              lineStyle: {
                color: '#35517a',
              },
            },
          },
        ],
        yAxis: [
          {
            type: 'value',
            name: this.legendArr[0],
            axisLine: {
              // lineStyle: {
              //   color: '#35517a',
              // },
            },
            minInterval: 1,
            axisLabel: {
              formatter(params) {
                if (params >= 2000 && params <= 50000) {
                  return `${toThousands(params / 1000)}k`;
                } else if (params > 50000) {
                  return `${toThousands(params / 10000)}w`;
                } else {
                  return `${toThousands(params)}`;
                }
              },
            },
            splitLine: {
              show: false,
            },
          },
          {
            type: 'value',
            name: this.legendArr[1],
            minInterval: 1,
            axisLine: {
              lineStyle: {
                color: '#35517a',
              },
            },
            axisLabel: {
              formatter(params) {
                if (params >= 2000 && params <= 50000) {
                  return `${toThousands(params / 1000)}k`;
                } else if (params > 50000) {
                  return `${toThousands(params / 10000)}w`;
                } else {
                  return `${toThousands(params)}`;
                }
              },
            },
            splitLine: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: this.legendArr[0],
            type: 'bar',
            data: [],
            barWidth: '30%',
            itemStyle: {
              normal: {
                //barBorderRadius: 15,
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: '#39CFF6',
                    },
                    {
                      offset: 1,
                      color: '#2580D2',
                    },
                  ],
                  false,
                ),
              },
            },
          },
          {
            name: this.legendArr[1],
            type: 'bar',
            data: [],
            yAxisIndex: 1,
            barWidth: '30%',
            itemStyle: {
              normal: {
                //barBorderRadius: 15,
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
      this.option.xAxis[0].data = this.data.groupId;
      this.option.series[0].data = this.data.daily;
      this.option.series[1].data = this.data.total;
      this.$nextTick(() => {
        if (this.$refs.amountData) {
          this.$refs.amountData.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      if (this.Data.length > 0) {
        // 统计BU数据
        let groupId = [];
        let daily = [];
        let total = [];
        this.Data.forEach(item => {
          groupId.push(item.groupId);
          daily.push(item.daily);
          total.push(item.total);
        });
        return {
          groupId,
          daily,
          total,
        };
      }
    },
  },
  watch: {
    data(value) {
      if (value) {
        // this.chart.clear();
        // this.chart.dispose();
        // this.init();
        this.updataOptionData();
      }
    },
  },
  components: {
    echart,
  },
};
</script>
