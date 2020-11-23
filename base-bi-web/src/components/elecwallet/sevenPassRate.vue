/*
 * @Author: wlhbdp
 * @Description: 本月新用户通过率--业务组件，处理与配置柱状图所需要的设计
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="sevenPassRate"></echart>
  </div>
</template>
<script>
import echart from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';

export default {
  props: {
    sevenPassRateData: Array,
  },
  data() {
    return {
      id: 'sevenPassRate',
      option: {
        title: {
          text: '本月新用户通过率',
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
          left: '12%',
          top: 90,
          bottom: 40,
        },
        textStyle: {
          color: '#FFFFFF',
        },
        series: [
          {
            type: 'pie',
            clockWise: true,
            radius: ['50%', '56%'],
            itemStyle: {
              normal: {
                label: {
                  show: false,
                },
                labelLine: {
                  show: false,
                },
              },
            },
            hoverAnimation: false,
            data: [
              {
                value: 75,
                name: '01',
                itemStyle: {
                  normal: {
                    color: {
                      // 完成的圆环的颜色
                      colorStops: [
                        {
                          offset: 1,
                          color: '#13a5b4', // 0% 处的颜色
                        },
                        {
                          offset: 0,
                          color: '#46edd4', // 100% 处的颜色
                        },
                      ],
                    },

                    labelLine: {
                      show: false,
                    },
                  },
                  emphasis: {
                    color: '#00cefc', // 鼠标悬浮上去完成的圆环的颜色
                  },
                },
                label: {
                  normal: {
                    formatter: '{c}%',
                    position: 'center',
                    show: true,
                    textStyle: {
                      fontSize: '28',
                      fontWeight: 'normal',
                      color: '#fff',
                    },
                  },
                },
              },
              {
                value: 25,
                name: 'invisible',
                itemStyle: {
                  normal: {
                    color: '#0b4770',
                    label: {
                      show: false,
                    },
                    labelLine: {
                      show: false,
                    },
                  },
                },
              },
            ],
          },
          {
            name: 'line2',
            type: 'pie',
            clockWise: true,
            radius: ['0', '45%'],
            itemStyle: {
              normal: {
                label: {
                  show: false,
                },
                labelLine: {
                  show: false,
                },
              },
            },
            hoverAnimation: false,
            data: [
              {
                value: 100,
                name: '01',
                itemStyle: {
                  normal: {
                    color: '#0b4770', // 100% 处的颜色,
                    label: {
                      show: false,
                    },
                    labelLine: {
                      show: false,
                    },
                  },
                },
              },
            ],
          },
        ],
      },
    };
  },
  methods: {
    updataOptionData() {
      if (this.data && this.data.length > 0) {
        const passed = (this.data[0].passed / this.data[0].total).toFixed(2);
        this.option.series[0].data[0].value = this._lodash.ceil(
          passed * 100,
          2,
        );
        this.option.series[0].data[1].value =
          this._lodash.subtract(1, passed) * 100;
        this.$nextTick(() => {
          if (this.$refs.sevenPassRate) {
            this.$refs.sevenPassRate.updataEchartsOption(this.option);
          }
        });
      }
    },
  },
  computed: {
    data() {
      return this.sevenPassRateData;
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
