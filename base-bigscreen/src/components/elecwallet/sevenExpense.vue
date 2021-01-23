/*
 * @Author: wlhbdp
 * @Description: 本月累计消费情况--业务组件
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="sevenExpense"></echart>
  </div>
</template>
<script>
import echart from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    sevenExpenseData: Array,
  },
  data() {
    return {
      id: 'sevenExpense',
      option: {
        title: {
          text: '本月累计消费情况',
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
        tooltip: {
          trigger: 'item',
          axisPointer: {
            type: 'shadow',
            crossStyle: {
              color: '#384757',
            },
          },
        },
        series: {
          name: '本月累计消费情况',
          type: 'pie',
          hoverAnimation: false,
          avoidLabelOverlap: false,
          radius: [0, '50%'],
          // color: ['#F1A91D', '#38BBF1'],
          labelLine: {
            length: 0,
          },
          label: {
            // formatter: '{b}\n{toThousandsc}',
            formatter(params) {
              return `${params.name}\n${toThousands(params.value)}`;
            },
          },
          data: [
            {
              value: 0,
              name: '电子钱包支付金额(元)',
              selected: true,
              itemStyle: {
                normal: {
                  color: {
                    colorStops: [
                      {
                        offset: 1,
                        color: '#e78e02', // 0% 处的颜色
                      },
                      {
                        offset: 0,
                        color: '#ffd144', // 100% 处的颜色
                      },
                    ],
                  },
                },
              },
            },
            {
              value: 0,
              name: '电子钱包提现金额(元)',
              itemStyle: {
                normal: {
                  color: {
                    colorStops: [
                      {
                        offset: 0,
                        color: '#378ae6', // 0% 处的颜色
                      },
                      {
                        offset: 1,
                        color: '#39ddf9', // 100% 处的颜色
                      },
                    ],
                  },
                },
              },
            },
          ],
        },
      },
    };
  },
  methods: {
    updataOptionData() {
      if (this.data && this.data.length > 0) {
        this.data.forEach(ele => {
          switch (ele.type) {
            // 电子钱包支付金额
            case 'payment':
              this.option.series.data[0].value = ele.amount
                .toFixed(2)
                .toLocaleString();
              break;
            // 电子钱包提现金额
            case 'withdraw':
              this.option.series.data[1].value = ele.amount
                .toFixed(2)
                .toLocaleString();
              break;
            default:
              break;
          }
        });
        // 组装echarts配置对象
        this.$nextTick(() => {
          this.$refs.sevenExpense.updataEchartsOption(this.option);
        });
      }
    },
  },
  computed: {
    data() {
      return this.sevenExpenseData;
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
