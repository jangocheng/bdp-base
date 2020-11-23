<template>
  <div id="PayAndWithdrawal"></div>
</template>

<script>
import chinaJson from '@/assets/china.json'; //此处引入中国地图json
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
      chart: '',
      maxSize4Pin: 100,
      minSize4Pin: 20,
      max: 480,
      min: 9,
      PayAndWithdrawalData: [],
      geoCoordMap: [], //地区经纬度
    };
  },
  mounted() {
    this.init();
    this.getSummary();
  },
  methods: {
    async getSummary() {
      const _init = () => {
        this.PayAndWithdrawalData = [];
      };
      try {
        const params = {
          startDate: this.startDate
            ? dayjs(this.startDate).format('YYYYMMDD')
            : '',
          endDate: this.endDate ? dayjs(this.endDate).format('YYYYMMDD') : '',
          channel: this.channel,
          isChain: this.isChain,
          summaryField: 'province',
        };
        _init();
        const res = await getSummary(params);
        if (!res || !res.data || res.data.length === 0) {
          return;
        }
        this.PayAndWithdrawalData = res.data.arr;
        this.updateOptionData();
      } catch (e) {
        console.log(e);
      }
    },
    updateOptionData() {
      if (this.PayAndWithdrawalData && this.PayAndWithdrawalData.length > 0) {
        this.option.series.forEach(ele => {
          ele.data = [];
        });
        this.chart.setOption(this.option, false, false);
        this.PayAndWithdrawalData.forEach(ele => {
          const geoCoord = this.geoCoordMap[ele.type];
          if (geoCoord) {
            this.option.series.forEach(e => {
              e.data.push({
                name: ele.type,
                value: geoCoord.concat(ele.amount),
              });
            });
          }
        });
        this.chart.setOption(this.option);
      }
    },
    init() {
      this.$echarts.registerMap('china', chinaJson);
      this.chart = this.$echarts.init(
        document.getElementById('PayAndWithdrawal'),
      );
      this.chart.setOption(this.option);
      window.addEventListener('resize', this.chart.resize);
      const mapFeatures = this.$echarts.getMap('china').geoJson.features;
      mapFeatures.forEach(e => {
        const name = e.properties.name;
        this.geoCoordMap[name] = e.properties.cp;
      });
    },
  },
  computed: {
    sidebarStatus() {
      return this.$store.state.app.sidebar.opened;
    },
    option() {
      return {
        title: {
          text: '支付宝用户地域分布图',
          textStyle: {
            fontSize: 24,
          },
          left: 30,
          top: 20,
        },
        geo: {
          map: 'china',
          show: true,
          roam: false,
          label: {
            emphasis: {
              show: false,
            },
          },
          itemStyle: {
            normal: {
              // 地图区域的颜色
              areaColor: '#334552',
              borderColor: '#1a1b1c',
            },
          },
        },
        tooltip: {
          trigger: 'item',
          formatter(params) {
            return `${params.name}
            ${params.value[2]}`;
          },
        },
        series: [
          {
            name: '散点',
            type: 'scatter',
            coordinateSystem: 'geo',
            label: {
              normal: {
                formatter: '{b}',
                position: 'right',
                show: true,
              },
              emphasis: {
                show: true,
              },
            },
            itemStyle: {
              normal: {
                color: '#05C3F9',
              },
            },
            data: '',
          },
          {
            name: '点',
            type: 'scatter',
            coordinateSystem: 'geo',
            symbol: 'pin', //气泡
            // symbolSize(val) {
            //   return 30;
            //   // return 0.05 * val[2] + 18;
            // },
            symbolSize: 30,
            label: {
              normal: {
                formatter(params) {
                  return `${params.value[2]}`;
                },
                show: true,
                textStyle: {
                  color: '#fff',
                  fontSize: 9,
                },
              },
            },
            itemStyle: {
              normal: {
                color: '#F62157', //标志颜色
              },
            },
            zlevel: 6,
            data: [],
          },
        ],
      };
    },
  },
  watch: {
    sidebarStatus() {
      setTimeout(this.resize, 650);
    },
  },
};
</script>

<style lang="scss">
#PayAndWithdrawal {
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
</style>
