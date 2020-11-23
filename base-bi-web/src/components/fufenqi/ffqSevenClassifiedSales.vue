/*
 * @Author: wlhbdp
 * @Description: 电子钱包----本月商品分类销售雷达图
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="ffqSevenClassifiedSales"></echart>
  </div>
</template>
<script>
import echart from '@/components/echarts.vue';
import {toThousands} from '@/utils/tool';

export default {
  props: {
    ffqSevenClassifiedSalesData: Array,
  },
  data() {
    return {
      id: 'ffqSevenClassifiedSales',
      option: {
        title: {
          text: '本月商品分类销售雷达图',
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
          // left: '15%',
          top: 150,
          bottom: 40,
          containLabel: true,
        },
        textStyle: {
          color: '#FFFFFF',
        },
        radar: {
          name: {
            formatter(value, indicator) {
              return `${value}\n${toThousands(indicator.num)}件`;
            },
            lineHeight: 15,
          },
          // 此处num属性在文档中没有，在name后生成的代码在上方formatter
          indicator: [],
          nameGap: 6,
          center: ['50%', '60%'],
          radius: '60%',
          axisLine: {
            lineStyle: {
              color: '#35517a',
            },
          },
          splitLine: {
            lineStyle: {
              color: '#35517a',
            },
          },
          // 雷达图背景的颜色，在这儿随便设置了一个颜色，完全不透明度为0，就实现了透明背景
          splitArea: {
            show: false,
          },
        },
        series: [
          {
            name: '分类销售',
            type: 'radar',
            data: [
              {
                value: [],
                name: '分类销售',
                areaStyle: {
                  normal: {
                    color: 'rgba(56,182,240, 0.5)',
                  },
                },
                lineStyle: {
                  normal: {
                    type: 'solid',
                    color: 'rgb(56,182,240)',
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
      // 取出排行前5的品牌
      let categoryArr = [];
      if (this.data.length > 5) {
        for (let i = 0; i < this.data.length; i++) {
          for (let j = 0; j < this.data.length - 1 - i; j++) {
            if (this.data[j].amount < this.data[j + 1].amount) {
              const tem = this.data[j + 1];
              this.data[j + 1] = this.data[j];
              this.data[j] = tem;
            }
          }
        }
        categoryArr = this.data.slice(0, 5);
      } else {
        categoryArr = this.data;
      }
      if (categoryArr.length === 0) {
        return;
      }

      // 设置雷达图的指示器的最大值
      const temAmout = categoryArr.map(ele => ele.amount || 0);
      const max = Math.max(...temAmout) * 1.3;
      this.option.radar.indicator = [];

      // 更改option数据
      categoryArr.forEach(ele => {
        if (ele.category) {
          this.option.radar.indicator.push({
            name: ele.category,
            num: ele.amount,
            max,
          });
          this.option.series[0].data[0].value.push(ele.amount);
        }
      });
      if (this.$refs.ffqSevenClassifiedSales) {
        this.$refs.ffqSevenClassifiedSales.updataEchartsOption(this.option);
      }
    },
  },
  computed: {
    data() {
      return this.ffqSevenClassifiedSalesData;
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
