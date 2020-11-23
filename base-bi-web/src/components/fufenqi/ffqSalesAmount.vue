/*
 * @Author: wlhbdp
 * @Description:
 */
 <template>
  <div>
    <echarts :id="id" :option="option" ref="ffqSalesAmount"></echarts>
  </div>
</template>

<script>
import echarts from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    ffqSalesAmountData: Array,
  },
  data() {
    return {
      id: 'ffqSalesAmount',
      option: {
        title: {
          text: '实时消费金额',
          textStyle: {
            // fontFamily: 'PingFangSC-Regular',
            fontSize: 14,
            color: '#FFFFFF',
            verticalAlign: 'bottom',
          },
          left: 10,
          top: 10,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            crossStyle: {
              color: '#384757',
            },
          },
          // formatter(prams) {
          //   return `${prams[0].name}点<br />消费金额:${prams[0].data}元`;
          // },
        },
        textStyle: {
          color: '#FFFFFF',
        },
        color: ['#E6CB4B'],
        grid: {
          left: '12%',
          top: 70,
          bottom: 40,
        },
        xAxis: {
          type: 'category',
          name: '时',
          // boundaryGap: false,
          axisLine: {
            //坐标轴轴线相关设置。数学上的x轴
            show: true,
            lineStyle: {
              color: '#233e64',
            },
          },
          data: [],
        },
        yAxis: {
          name: '金额',
          minInterval: 1,
          type: 'value',
          splitLine: {
            show: false,
          },
          axisLabel: {
            formatter(params) {
              if (params >= 1000 && params < 50000) {
                return `${toThousands(params / 1000)}k`;
              } else if (params >= 50000) {
                return `${toThousands(params / 10000)}w`;
              } else {
                return `${toThousands(params)}`;
              }
            },
          },
          minInterval: 1000,
          axisLine: {
            //坐标轴轴线相关设置。数学上的x轴
            show: true,
            lineStyle: {
              color: '#233e64',
            },
          },
        },
        series: [
          {
            type: 'line',
            smooth: true, //是否平滑曲线显示
            lineStyle: {
              normal: {
                color: '#E6CB4B', // 线条颜色
              },
            },
            areaStyle: {
              //区域填充样式
              normal: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0,
                      color: 'rgba(230,203,75,0.2)',
                    },
                    {
                      offset: 1,
                      color: 'rgba(230,203,75,0)',
                    },
                  ],
                },
              },
            },
            data: [],
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
      /**
       * TODO:下列代码是实现高频数据情况下的X从右往左的推移效果，勿删可留
       */
      // const xData = [];
      // if (xData.length > 0) {
      //   xData.shift();
      //   xData.push(this.data[this.data.length - 1].time);
      // } else {
      //   this.data.forEach(ele => {
      //     xData.push(ele.time);
      //   });
      // }
      // this.option.series.forEach(ele => {
      //   ele.data = [];
      // });
      this.option.series.map(ele => {
        const temObj = ele;
        temObj.data = [];
        return temObj;
      });
      this.option.xAxis.data = [];
      this.data.forEach(ele => {
        // xData.push(ele.time); //此行代码在上面注释打开时需注释掉
        this.option.xAxis.data.push(ele.time); //此行代码在上面注释打开时需注释掉
        this.option.series[0].data.push(ele.amount.toFixed(2));
      });
      this.$nextTick(() => {
        if (this.$refs.ffqSalesAmount) {
          this.$refs.ffqSalesAmount.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      if (this.ffqSalesAmountData && this.ffqSalesAmountData instanceof Array) {
        let temArr = this.ffqSalesAmountData;
        for (let i = 0; i < temArr.length; i++) {
          for (let j = 0; j < temArr.length - 1 - i; j++) {
            if (temArr[j].time > temArr[j + 1].time) {
              // console.log('实时消费金额排序');
              const temp = temArr[j + 1];
              temArr[j + 1] = temArr[j];
              temArr[j] = temp;
            }
          }
        }
        return temArr;
      }
      // return this.ffqSalesAmountData;
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
    echarts,
  },
};
</script>

