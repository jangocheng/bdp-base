<template>
  <div class="main">
    <div :id="id" class="echart-block"></div>
  </div>
</template>

<script>
import dayjs from 'dayjs';
import { getSummary } from '@/api/business/PDTime.js';

export default {
  props: {
    /**
     * 两个参数时间格式20180911
     */
    startDate: {},
    endDate: {},
    channel: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      id: 'rateSex',
      chart: '',
      // false即时状态 | true环比状态
      isChain: false,
      // 数据
      echartsData: {},
    };
  },
  computed: {
    sidebarStatus() {
      return this.$store.state.app.sidebar.opened;
    },
    option() {
      return {
        title: [
          {
            text: '用户性别分布',
            textStyle: {
              fontSize: '22',
            },
          },
        ],
        tooltip: {
          trigger: 'item',
          formatter(parms) {
            return `${parms.seriesName}</br>${parms.marker}${
              parms.data.name
            }</br>数量：${parms.data.value}</br>占比：${parms.percent}%`;
          },
        },
        series: {
          name: '标题',
          type: 'pie',
          center: ['50%', '50%'],
          radius: ['30%', '45%'],
          clockwise: false, //饼图的扇区是否是顺时针排布
          avoidLabelOverlap: false,
          label: {
            normal: {
              show: true,
              position: 'outter',
              formatter(parms) {
                return `${parms.data.name},占比:${parms.percent}%`;
              },
            },
          },
          labelLine: {
            normal: {
              length: 20,
              length2: 3,
              smooth: true,
            },
          },
          data: this.echartsData,
        },
      };
    },
  },
  watch: {
    sidebarStatus() {
      setTimeout(this.resize, 650);
    },
  },
  methods: {
    resize() {
      this.chart.resize();
    },
    updateEchartsOption(params) {
      if (this.chart) {
        this.chart.clear();
        this.chart.setOption(params);
        window.addEventListener('resize', this._.debounce(this.resize, 150));
      }
    },
    init() {
      this.chart = this.$echarts.init(document.querySelector(`#${this.id}`));
      if (this.option) {
        this.updateEchartsOption(this.option);
      }
    },
    async getSummary() {
      const _init = () => {
        this.echartsData = [];
      };
      try {
        const params = {
          startDate: this.startDate
            ? dayjs(this.startDate).format('YYYYMMDD')
            : '',
          endDate: this.endDate ? dayjs(this.endDate).format('YYYYMMDD') : '',
          channel: this.channel,
          isChain: this.isChain,
          summaryField: 'gender',
        };
        _init();
        const res = await getSummary(params);
        if (!res || !res.data || res.data.length === 0) {
          return;
        }
        this.echartsData = res.data.map(item => ({
          value: item.consumptionSum,
          legendname: item.consumptionType,
          name: item.consumptionType,
        }));
        this.updateEchartsOption(this.option);
      } catch (error) {
        console.log('error', error);
        _init();
        this.updateEchartsOption(this.option);
      }
    },
  },
  mounted() {
    this.init();
    this.getSummary();
  },
};
</script>
<style lang="scss" scoped>
.main {
  .echart-block {
    height: 100%;
    width: 100%;
  }
}
</style>
