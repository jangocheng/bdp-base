 <template>
  <div id="realTimeRrade"></div>
</template>

<script>
import chinaJson from 'public/js/china.json'; //此处引入中国地图json

export default {
  props: {
    realTimeRradeData: Array,
  },
  data() {
    return {
      chart: '',
      option: {
        title: {
          text: '实时交易地图',
          textStyle: {
            fontFamily: 'PingFangSC-Regular',
            fontSize: 14,
            color: '#FFFFFF',
            verticalAlign: 'bottom',
          },
          left: 30,
          top: 20,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line',
            lineStyle: {
              opacity: 0,
            },
          },
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
              areaColor: '#0e7bb0',
              borderColor: '#1a1b1c',
            },
          },
        },
        series: [
          {
            name: '电子钱包实时支付',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            data: '',
            symbolSize: val => val[2] / 20,
            rippleEffect: {
              brushType: 'stroke',
            },
            showEffectOn: 'render',
            zlevel: 1,
            hoverAnimation: true,
            label: {
              normal: {
                formatter: '{b}',
                position: 'right',
                show: false,
              },
              emphasis: {
                show: true,
              },
            },
            itemStyle: {
              normal: {
                color: '#e9fd03',
                shadowBlur: 10,
                shadowColor: '#333',
              },
            },
          },
        ],
      },
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    updataOptionData() {
      if (this.data && this.data.length > 0) {
        this.option.series.map(ele => {
          const temObj = ele;
          temObj.data = [];
          return temObj;
        });
        this.chart.setOption(this.option);
        this.data.forEach(ele => {
          console.log('电商收入更新数据');
          if (ele) {
            switch (ele.usageType) {
              // 支付
              case '0201':
                this.option.series[0].data.push({
                  name: '电子钱包实时支付',
                  value: [ele.longitude, ele.latitude, 180],
                });
                break;
              // 提现
              // case '0202':
              //   this.option.series[1].data.push({
              //     name: '电子钱包实时提现',
              //     value: [ele.longitude, ele.latitude, 180],
              //   });
              //   break;
              default:
                break;
            }
          }
        });
        this.chart.setOption(this.option);
      }
    },
    init() {
      this.$echarts.registerMap('china', chinaJson);
      this.chart = this.$echarts.init(document.getElementById('realTimeRrade'));
      this.chart.setOption(this.option);
      window.addEventListener('resize', this.chart.resize);
    },
  },
  computed: {
    data() {
      return this.realTimeRradeData;
    },
  },
  watch: {
    data(value) {
      if (value) {
        this.chart.clear();
        this.chart.dispose();
        this.init();
        this.updataOptionData();
      }
    },
  },
};
</script>

<style lang="less">
</style>
