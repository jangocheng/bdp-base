 <template>
  <div id="PayAndWithdrawal"></div>
</template>

<script>
import chinaJson from 'public/js/china.json'; //此处引入中国地图json

export default {
  props: {
    PayAndWithdrawalData: Array,
  },
  data() {
    return {
      chart: '',
      option: {
        legend: {
          orient: 'vertical',
          top: '50',
          left: '50',
          data: ['电子钱包实时支付', '电子钱包实时提现'],
          textStyle: {
            color: '#ccc',
          },
        },
        title: {
          text: '电子钱包实时支付与提现',
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
          {
            name: '电子钱包实时提现',
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
                color: '#00fcff',
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
        this.chart.setOption(this.option, false, false);
        this.data.forEach(ele => {
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
              case '0202':
                this.option.series[1].data.push({
                  name: '电子钱包实时提现',
                  value: [ele.longitude, ele.latitude, 180],
                });
                break;
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
      this.chart = this.$echarts.init(
        document.getElementById('PayAndWithdrawal'),
      );
      this.chart.setOption(this.option);
      window.addEventListener('resize', this.chart.resize);
    },
  },
  computed: {
    data() {
      return this.PayAndWithdrawalData;
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
#PayAndWithdrawal {
  background-image: url('../../../../public/img/icons/map.png');
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
</style>
