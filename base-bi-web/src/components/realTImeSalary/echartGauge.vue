/*
 * @Author: wlhbdp
 * @Description: 今日推送用户数据--累计推送用户数据
 */

<template>
  <div>
    <echart :id="id" :option="option" ref="gaugeData"></echart>
  </div>
</template>
<script>
import echarts from 'echarts';
import echart from '@/components/echarts.vue';
import { toThousands } from '@/utils/tool';
import { consumeCountData } from '../../api/RealTimeAPI';

export default {
  props: {
    /**
     * {
     *  groupId:'',//集合名称,xAixis轴数据
     *  activeAmt:'',//有效激活人数
     *  syncAmt:'',//有效推送数
     * }
     */
    Data: Object,
    //用来生成渲染echarts
    id: '',
    //色盘
    color: Array,
  },
  methods: {
    /**
     * 更新echarts配置数据
     */
    updataOptionData() {
      console.log('开始渲染');
      this.$nextTick(() => {
        if (this.$refs.gaugeData) {
          this.$refs.gaugeData.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    groupId() {
      const tem = this.Data.groupId;
      return tem;
    },
    tem() {
      let str =
        Number((this.Data.activeAmt / this.Data.syncAmt) * 100).toFixed(2) * 1;
      return str;
    },
    option() {
      return {
        title: {
          text: `${this.groupId}次业务部门激活完成率`,
          textStyle: {
            color: '#FFF',
            fontSize: '14',
          },
          left: 'center',
          bottom: '-5',
        },
        series: [
          {
            type: 'gauge',
            axisLine: {
              show: true,
              lineStyle: {
                width: 14,
                shadowBlur: 0,
                // 属性lineStyle控制线条样式
                color: [[0.2, '#d85848'], [0.8, '#63869e'], [1, '#6bb351']],
              },
            },
            center: ['50%', '50%'], // 默认全局居中
            radius: '70%',
            axisLabel: {
              distance: -12,
              textStyle: {
                color: '#03b7c9',
                fontSize: '10',
                fontWeight: 'bold',
              },
            },
            splitLine: {
              show: true,
              length: -8,
              lineStyle: {
                color: '#03b7c9',
              },
            },
            itemStyle: {
              normal: {
                color: '#03b7c9',
              },
            },
            detail: {
              show: true,
              offsetCenter: [0, '60%'],
              textStyle: {
                fontSize: 15,
                color: '#FFFFCC',
                fontWeight: '700'
              },
              formatter: '{value}%',
            },
            data: [
              {
                value: this.tem,
              },
            ],
          },
        ],
      };
    },
  },
  watch: {
    groupId: {
      handler(newName, oldName) {
        if (newName !== oldName) {
          this.updataOptionData();
        }
      },
      immediate: true,
    },
  },
  components: {
    echart,
  },
};
</script>
