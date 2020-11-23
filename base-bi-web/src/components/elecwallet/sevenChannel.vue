/*
 * @Author: wlhbdp
 * @Description: 本月渠道累计进件分布--业务组件，处理与配置柱状图所需要的设计
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="sevenChannel"></echart>
  </div>
</template>
<script>
import echart from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';
import channelObj from './channel.js';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    sevenChannelData: Array,
  },
  data() {
    return {
      id: 'sevenChannel',
      option: {
        title: {
          text: '本月渠道累计进件分布',
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
          top: 150,
          bottom: 40,
          containLabel: true,
        },
        textStyle: {
          color: '#FFFFFF',
        },
        radar: {
          name: {
            // 格式化渠道图表上的标题
            formatter(value, indicator) {
              return `${value}\n${toThousands(indicator.num)}`;
            },
            lineHeight: 12,
          },
          // 此处num属性在文档中没有，在name后生成的代码在上方formatter
          indicator: [],
          nameGap: 6,
          center: ['50%', '60%'],
          radius: '50%',
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
            name: '渠道进件',
            type: 'radar',
            data: [
              {
                value: [],
                name: '渠道进件',
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
    updataOptionData() {
      // 设置雷达图的指示器的最大值
      const temAmout = this.data.map(ele => ele.amount || 0);
      const max = Math.max(...temAmout) * 1.3;
      this.option.radar.indicator = [];
      // 更改option数据
      if (this.data.length > 0) {
        this.data.forEach(ele => {
          if (ele.channel) {
            this.option.radar.indicator.push({
              name: channelObj[ele.channel] || ele.channel,
              num: ele.amount,
              max,
            });
            this.option.series[0].data[0].value.push(ele.amount);
          }
        });
        this.$nextTick(() => {
          if (this.$refs.sevenChannel) {
            this.$refs.sevenChannel.updataEchartsOption(this.option);
          }
        });
      }
    },
  },
  computed: {
    data() {
      return this.sevenChannelData;
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
