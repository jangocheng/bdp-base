/*
 * @Author: wlhbdp
 * @Description: echarts容器组件
 */

<template>
  <div :id="id"></div>
</template>
<script>
export default {
  name: 'Chart',
  data() {
    return {
      //echarts实例
      chart: '',
    };
  },
  props: {
    //父组件需要传递的参数：id，width，height，option
    id: {
      type: String,
      default: 'init',
    },
    option: {
      type: Object,
      //Object类型的prop值一定要用函数return出来，不然会报错。原理和data是一样的，
      //使用闭包保证一个vue实例拥有自己的一份props
      default() {},
    },
  },
  mounted() {
    this.init();
  },
  methods: {
    updataEchartsOption(params) {
      if (this.chart) {
        this.chart.setOption(params);
        window.addEventListener('resize', this.chart.resize);
      }
    },
    init() {
      if (this.id !== 'init') {
        this.chart = this.$echarts.init(document.getElementById(this.id));
      }
    },
  },
};
</script>
