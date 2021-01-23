/*
 * @Author: wlhbdp
 * @Description: 昨日申请通过用户分类统计
 */
 <template>
  <div>
    <echarts :id="id" :option="option" ref="userClassify"></echarts>
  </div>
</template>

<script>
import echarts from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';
import { toThousands } from '@/utils/tool';

export default {
  props: {
    userClassifyData: Array,
  },
  data() {
    return {
      id: 'userClassify',
      option: {
        title: {
          text: '昨日申请通过用户分类统计',
          textStyle: {
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
        color: ['#E6CB4B', '#68ED90'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line',
            crossStyle: {
              color: '#384757',
            },
          },
        },
        legend: {
          data: [
            {
              name: '社会人士',
              icon: 'roundRect',
            },
            {
              name: '内部员工',
              icon: 'roundRect',
            },
          ],
          textStyle: {
            color: '#FFFFFF',
          },
          top: '40',
        },
        grid: {
          left: '15%',
          right: '17%',
          top: 80,
          bottom: 30,
        },
        xAxis: {
          type: 'category',
          name: '类别',
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
          type: 'value',
          name: '人数',
          minInterval: 1,
          splitLine: {
            show: false,
          },
          axisLine: {
            //坐标轴轴线相关设置。数学上的x轴
            show: true,
            lineStyle: {
              color: '#233e64',
            },
          },
          axisLabel: {
            formatter(params) {
              if (params >= 1000 && params <= 50000) {
                return `${toThousands(params / 1000)}k`;
              } else if (params > 50000) {
                return `${toThousands(params / 10000)}w`;
              } else {
                return `${toThousands(params)}`;
              }
              // return toThousands(params);
            },
          },
        },
        series: [
          {
            name: '社会人士',
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
          {
            name: '内部员工',
            type: 'line',
            smooth: true, //是否平滑曲线显示
            lineStyle: {
              normal: {
                color: '#68ED90', // 线条颜色
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
                      color: 'rgba(104,237,144,0.2)',
                    },
                    {
                      offset: 1,
                      color: 'rgba(104,237,144,0)',
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
    // 更新折线图配置文件数据
    updataOptionData() {
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
        this.option.xAxis.data.push(ele.level);
        this.option.series[0].data.push(ele.outer);
        this.option.series[1].data.push(ele.inner);
      });
      this.$nextTick(() => {
        if (this.$refs.userClassify) {
          this.$refs.userClassify.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    data() {
      return this.userClassifyData;
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
    echarts,
  },
};
</script>

