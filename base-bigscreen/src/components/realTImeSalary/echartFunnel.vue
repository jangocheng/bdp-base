

 <template>
  <div>
    <echart :id="id" :option="option" ref="amountData"></echart>
  </div>
</template>
 
 <script>
import echarts from 'echarts';
import echart from '@/components/echarts.vue';
import getUrl from '@/utils/getUrl';
import {toThousands} from '@/utils/tool';

export default {
  props: {
    /**
     * 数据集合
     * [{
     *  groupId:'',//集合名称,xAixis轴数据
     *  daily:'',//到今日的数据
     *  total:'',//累计数据
     * }]
     */
    Data: Array,
    //用来生成渲染echarts
    id: '',
  },
  data() {
    return {
      id: this.id,
      option: {
        title: {
          text: '推送用户数据概况',
          textStyle: {
            fontFamily: 'PingFangSC-Regular',
            fontSize: 14,
            color: '#FFFFFF',
            verticalAlign: 'bottom',
          },
          left: 10,
          top: 10,
        },
        color: ['#efbb1a', '#d77169', '#c14f60', '#953f61', '#72355f'],
        series: {
          name: '漏斗图',
          type: 'funnel',
          x: '10%',
          y: 60,
          //x2: 80,
          y2: 60,
          width: '80%',
          // height: {totalHeight} - y - y2,
          min: 0,
          minSize: '0%',
          maxSize: '100%',
          sort: 'descending', // 'ascending', 'descending'
          gap: 0,
          data: [
            { value: 0, name: '申请通过数' },
            { value: 0, name: '电子钱包申请数' },
            { value: 0, name: '累计激活用户数' },
            { value: 0, name: '累计注册用户数' },
            { value: 0, name: '累计推送用户数' },
          ].sort(function(a, b) {
            return a.value - b.value;
          }),
          roseType: true,
          label: {
            normal: {
              formatter(params) {
                return params.name + ' ' + toThousands(params.value);
              },
              position: 'center',
            },
          },
          itemStyle: {
            normal: {
              borderWidth: 0,
              shadowBlur: 30,
              shadowOffsetX: 0,
              shadowOffsetY: 10,
              shadowColor: 'rgba(0, 0, 0, 0.5)',
            },
          },
        },
      },
    };
  },
  methods: {
    /**
     * 更新echarts配置数据
     */
    updataOptionData() {
      //   this.option.series[0].data = this.data.daily;
      this.option.series.map = this.temData['累计推送用户数'];
      this.option.series.data.forEach(ele => {
        ele.value = this.temData[ele.name];
      });
      // this.option.series.data = [
      //   { value: this.temData['申请通过数'], name: '申请通过数' },
      //   { value: this.temData['电子钱包申请数'], name: '电子钱包申请数' },
      //   { value: this.temData['累计激活用户数'], name: '累计激活用户数' },
      //   { value: this.temData['累计注册用户数'], name: '累计注册用户数' },
      //   { value: this.temData['累计推送用户数'], name: '累计推送用户数' },
      // ].sort(function(a, b) {
      //   return a.value - b.value;
      // });
      this.$nextTick(() => {
        console.log('开始渲染');
        if (this.$refs.amountData) {
          this.$refs.amountData.updataEchartsOption(this.option);
        }
      });
    },
  },
  computed: {
    temData() {
      const temObj = Object.assign({}, this.Data);
      return temObj;
    },
  },
  watch: {
    temData(value) {
      console.log('运行');
      this.updataOptionData();
    },
  },
  components: {
    echart,
  },
};
</script>
 
<style lang="less" scoped>
</style>

 
