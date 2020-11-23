/*
 * @Author: wlhbdp
 * @Description: 电子钱包----本月各品牌销售占比
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="ffqSevenBrandSalesRatio"></echart>
  </div>
</template>
<script>
import echart from '@/components/echarts.vue';

export default {
  props: {
    ffqSevenBrandSalesRatioData: Array,
  },
  data() {
    return {
      id: 'ffqSevenBrandSalesRatio',
      option: {
        title: {
          text: '本月各品牌销售占比',
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
        color: ['#39D1F6', '#9d7CF2', '#E9c55A', '#4DE2D1', '#E18E39'],
        legend: {
          orient: 'vertical',
          right: '10%',
          top: '30%',
          data: [],
          textStyle: {
            color: '#FFFFFF',
          },
        },
        grid: {
          left: '12%',
          top: 90,
          bottom: 40,
        },
        series: [
          {
            type: 'pie',
            // hoverAnimation: false,
            // avoidLabelOverlap: false,
            radius: ['12%', '60%'],
            center: ['35%', '56%'],
            roseType: 'area',
            labelLine: {
              length: 0,
            },
            label: {
              formatter: '{b}\n{c}%',
            },
            data: [
              {
                value: 0,
                name: '',
                itemStyle: {
                  normal: {
                    color: {
                      colorStops: [
                        {
                          offset: 1,
                          color: '#378ae6', // 0% 处的颜色
                        },
                        {
                          offset: 0,
                          color: '#30c0e3', // 100% 处的颜色
                        },
                      ],
                    },
                  },
                },
              },
              {
                value: 0,
                name: '',
                itemStyle: {
                  normal: {
                    color: {
                      colorStops: [
                        {
                          offset: 1,
                          color: '#9c7af2', // 0% 处的颜色
                        },
                        {
                          offset: 0,
                          color: '#6840d3', // 100% 处的颜色
                        },
                      ],
                    },
                  },
                },
              },
              {
                value: 0,
                name: '',
                itemStyle: {
                  normal: {
                    color: {
                      colorStops: [
                        {
                          offset: 1,
                          color: '#d6b346', // 0% 处的颜色
                        },
                        {
                          offset: 0,
                          color: '#cb8bc3', // 100% 处的颜色
                        },
                      ],
                    },
                  },
                },
              },
              {
                value: 0,
                name: '',
                itemStyle: {
                  normal: {
                    color: {
                      colorStops: [
                        {
                          offset: 1,
                          color: '#de8a38', // 0% 处的颜色
                        },
                        {
                          offset: 0,
                          color: '#ca7031', // 100% 处的颜色
                        },
                      ],
                    },
                  },
                },
              },
              {
                value: 0,
                name: '',
                itemStyle: {
                  normal: {
                    color: {
                      colorStops: [
                        {
                          offset: 1,
                          color: '#378ae6', // 0% 处的颜色
                        },
                        {
                          offset: 0,
                          color: '#30c0e3', // 100% 处的颜色
                        },
                      ],
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
    /**
     * 更新echarts配置数据
     */
    updataOptionData() {
      // 取出排行前6的品牌
      let brandArr = [];
      if (this.data.length > 4) {
        for (let i = 0; i < this.data.length; i++) {
          for (let j = 0; j < this.data.length - 1 - i; j++) {
            if (this.data[j].percentage < this.data[j + 1].percentage) {
              const tem = this.data[j + 1];
              this.data[j + 1] = this.data[j];
              this.data[j] = tem;
            }
          }
        }
        brandArr = this.data.slice(0, 5);
        // // 获取排行前4品牌的总占比
        // let allPer = 0;
        // brandArr.forEach(ele => {
        //   allPer = this._lodash.add(ele.percentage, allPer);
        // });
        // // 添加剩余其他品牌的总占比
        // brandArr.push({
        //   brand: '其他',
        //   percentage: 100 - allPer,
        // });
      } else {
        brandArr = this.data;
      }
      if (brandArr.length === 0) {
        return;
      }
      this.option.legend.data = [];
      // 取出排行前6的品牌
      brandArr.forEach((ele, index) => {
        this.option.legend.data.push(ele.brand); //此行代码在上面注释打开时需注释掉
        this.option.series[0].data[index].value = ele.percentage;
        this.option.series[0].data[index].name = ele.brand;
      });
      if (this.$refs.ffqSevenBrandSalesRatio) {
        this.$refs.ffqSevenBrandSalesRatio.updataEchartsOption(this.option);
      }
    },
  },
  computed: {
    data() {
      return this.ffqSevenBrandSalesRatioData;
    },
  },
  watch: {
    data(value) {
      // debugger;
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
