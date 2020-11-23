<template>
  <div :id="id"></div>
</template>

<script>
// import { mapGetters } from 'vuex';

export default {
  props: {
    id: {
      type: String,
      default: 'echarts',
    },
    option: {
      type: Object,
      default() {
        return null;
      },
    },
  },
  mounted() {
    this.init();
  },
  watch: {
    option() {
      this.updateEchartsOption(this.option);
    },
    sidebarStatus() {
      setTimeout(this.resize, 650);
    },
  },
  computed: {
    sidebarStatus() {
      return this.$store.state.app.sidebar.opened;
    },
  },
  data() {
    return {
      // echarts对象
      chart: '',
    };
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
      this.chart.on('click', params => {
        // 抛出node节点点击事件
        if (params.dataType === 'node') {
          this.$emit('nodeClick', params.data);
        }
        // 抛出edge节点点击事件
        if (params.dataType === 'edge') {
          this.$emit('edgeClick', params.data);
        }
      });
    },
  },
};
</script>
