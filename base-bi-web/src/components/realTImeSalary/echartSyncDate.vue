/*
 * @Author: wlhbdp
 * @Description: 今日推送用户数据--累计推送用户数据
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="syncData"></echart>
  </div>
</template>
<script>
import echarts from 'echarts';
import echart from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';

export default {
  props: {
    syncData: Array,
  },
  data() {
    return {
      id: 'syncData',
      option: {
        calculable: true,
        textStyle: {
          color: '#FFFFFF',
        },
        grid: {
          left: '8%',
          right: '8%',
          top: 70,
          bottom: 30,
        },
        color: ['#38B7F0', '#9471EC'],
        legend: {
          data: ['今日推送用户数', '累积推送用户数'],
          left: 10,
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
            name: '今日推送用户数',
            axisLine: {
              lineStyle: {
                color: '#35517a',
              },
            },
            axisLabel: {
              formatter: '{value}',
            },
            splitLine: {
              show: false,
            },
          },
          {
            type: 'value',
            name: '累积推送用户数',
            axisLine: {
              lineStyle: {
                color: '#35517a',
              },
            },
            axisLabel: {
              formatter: '{value}',
            },
            splitLine: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: '今日推送用户数',
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
            name: '累积推送用户数',
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
  mounted() {
    this.updataOptionData();
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
        if (this.$refs.syncData) {
          this.$refs.syncData.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      if (this.syncData.length > 0) {
        // 统计业务部门数据
        let groupId = [];
        let daily = [];
        let total = [];
        this.syncData.forEach(item => {
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
        this.updataOptionData();
      }
    },
  },
  components: {
    echart,
  },
};
</script>
