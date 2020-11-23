<template>
  <div class="main" v-loading="loading">
    <div :id="id" class="echart-block"></div>
  </div>
</template>

<script>
import dayjs from 'dayjs';
import { numFormatter, objClone, toThousands } from '@/utils/index';
import { withdrawal } from '@/api/zfb/getPay2WithdrawalsData';
import { tableDateWidthMixins } from '@/Mixins/tableDateWidthMixins';

export default {
  mixins: [tableDateWidthMixins],
  data() {
    return {
      id: 'withdrawalEchart',
      chart: '',
      isChain: false,
      // 数据
      xData: [],
      withdrawCnt: [], //提现总次数
      staffWithdrawCnt: [], //内部员工提现次数
      socialWithdrawCnt: [], //社会人士提现次数
      staffWithdrawCntRatio: [], //内部员工提现次数占比
      socialWithdrawCntRatio: [], //社会人士提现次数占比
      withdrawSum: [], //提现总金额
      staffWithdrawSum: [], //内部员工提现金额
      socialWithdrawSum: [], //社会人士提现金额
      staffWithdrawSumRate: [], //内部员工提现金额占比
      socialWithdrawSumRate: [], //社会人士提现金额占比
    };
  },
  computed: {
    legendArr() {
      return [
        {
          name: '提现总次数',
          type: this.isChain ? 'line' : 'bar',
          data: this.withdrawCnt,
        },
        {
          name: '内部员工提现次数',
          type: this.isChain ? 'line' : 'bar',
          data: this.staffWithdrawCnt,
        },
        {
          name: '社会人士提现次数',
          type: this.isChain ? 'line' : 'bar',
          data: this.socialWithdrawCnt,
        },
        {
          name: '内部员工提现次数占比',
          type: 'line',
          data: this.staffWithdrawCntRatio,
        },
        {
          name: '社会人士提现次数占比',
          type: 'line',
          data: this.socialWithdrawCntRatio,
        },
      ];
    },
    legendArr2() {
      return [
        {
          name: '提现总金额',
          type: this.isChain ? 'line' : 'bar',
          data: this.withdrawSum,
        },
        {
          name: '内部员工提现金额',
          type: this.isChain ? 'line' : 'bar',
          data: this.staffWithdrawSum,
        },
        {
          name: '社会人士提现金额',
          type: this.isChain ? 'line' : 'bar',
          data: this.socialWithdrawSum,
        },
        {
          name: '内部员工提现金额占比',
          type: 'line',
          data: this.staffWithdrawSumRate,
        },
        {
          name: '社会人士提现金额占比',
          type: 'line',
          data: this.socialWithdrawSumRate,
        },
      ];
    },
    sidebarStatus() {
      return this.$store.state.app.sidebar.opened;
    },
    option() {
      // 柱状图对象
      const barObj = {
        name: '',
        type: 'bar',
        barMaxWidth: 35,
        barGap: '10%',
        itemStyle: {
          normal: {
            label: {
              show: true,
              position: 'top',
              formatter(p) {
                return toThousands(p.value);
              },
            },
          },
        },
        data: [],
      };
      // 折线图对象
      const lineObj = {
        name: '',
        type: 'line',
        symbolSize: 10,
        symbol: 'circle',
        yAxisIndex: 1,
        itemStyle: {
          normal: {
            barBorderRadius: 0,
            label: {
              show: true,
              position: 'top',
              formatter(p) {
                return `${p.value > 0 ? p.value : ''}%`;
              },
            },
          },
        },
        data: [],
      };
      const seriesArr = [];
      this.legendArr2.forEach(ele => {
        const temBarObj = objClone(barObj);
        const temLineObj = objClone(lineObj);
        // 为true 表示 当前渲染环比数据
        if (this.isChain) {
          temLineObj.yAxisIndex = 0;
          temLineObj.name = ele.name;
          temLineObj.data = ele.data;
          seriesArr.push(temLineObj);
        } else if (ele.type === temBarObj.type) {
          temBarObj.name = ele.name;
          temBarObj.data = ele.data;
          seriesArr.push(temBarObj);
        } else if (temLineObj.type === ele.type) {
          temLineObj.name = ele.name;
          temLineObj.data = ele.data;
          seriesArr.push(temLineObj);
        }
      });
      this.legendArr.forEach(ele => {
        const temBarObj = objClone(barObj);
        const temLineObj = objClone(lineObj);
        // 为true 表示 当前渲染环比数据
        if (this.isChain) {
          temLineObj.yAxisIndex = 0;
          temLineObj.name = ele.name;
          temLineObj.data = ele.data;
          seriesArr.push(temLineObj);
        } else if (ele.type === temBarObj.type) {
          temBarObj.name = ele.name;
          temBarObj.data = ele.data;
          seriesArr.push(temBarObj);
        } else if (temLineObj.type === ele.type) {
          temLineObj.name = ele.name;
          temLineObj.data = ele.data;
          seriesArr.push(temLineObj);
        }
      });
      return {
        title: [
          {
            text: this.text,
            textStyle: {
              fontSize: '22',
            },
          },
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            textStyle: {
              color: '#fff',
            },
          },
          formatter(params) {
            let resStr = '';
            params.forEach(ele => {
              let res = ele.value;
              if (
                ele.seriesName.endsWith('率') ||
                ele.seriesName.endsWith('占比')
              ) {
                res = `${res}%`;
              } else {
                res = toThousands(res);
              }
              resStr += `${ele.marker}${ele.seriesName}:${res}<br/>`;
            });
            return resStr;
          },
        },
        grid: {
          top: 75,
          left: 60,
          right: 60,
          bottom: 65,
        },
        legend: [
          {
            textStyle: {
              color: '#90979c',
            },
            right: '10%',
            data: this.legendArr,
          },
          {
            textStyle: {
              color: '#90979c',
            },
            right: '10%',
            top: '6%',
            data: this.legendArr2,
          },
        ],
        calculable: true,
        xAxis: [
          {
            type: 'category',
            axisLine: {
              lineStyle: {
                color: '#90979c',
              },
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            splitArea: {
              show: false,
            },
            data: this.xData,
          },
        ],
        yAxis: [
          {
            name: this.isChain ? '环比(%)' : '数额',
            type: 'value',
            splitLine: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: '#90979c',
              },
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              formatter(value) {
                return numFormatter(value, 2);
              },
            },
          },
          // "即时状态"下"通过率"所用
          {
            show: !this.isChain,
            name: '占比',
            type: 'value',
            splitLine: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: '#90979c',
              },
            },
            axisTick: {
              show: false,
            },
            position: 'right',
          },
        ],
        dataZoom: [
          {
            show: true,
            height: 30,
            handleIcon:
              'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
            handleSize: '110%',
            handleStyle: {
              color: '#d3dee5',
            },
            borderColor: '#90979c',
          },
          {
            type: 'inside',
            show: true,
          },
        ],
        toolbox: {
          show: false,
          itemSize: 24,
          right: 20,
          itemGap: 15,
          feature: {
            myTool2: {
              show: true,
              title: '即时',
              icon:
                'path://M512 0a512 512 0 1 0 512 512 512 512 0 0 0-512-512z m192 638.656H490.304a42.944 42.944 0 0 1-42.944-43.008V296.704a43.008 43.008 0 1 1 85.952 0v256H704a43.776 43.776 0 0 1 42.944 43.008 42.944 42.944 0 0 1-42.944 42.944z',
              iconStyle: {
                borderColor: this.instantBtnColor,
              },
              onclick: () => {
                // this.getCurrData();
                this.isChain = false;
                this.withdrawal();
              },
            },
            myTool1: {
              show: true,
              title: '环比',
              icon:
                'path://M512 45.176471C769.415532 45.176471 978.823532 254.584471 978.823532 512 978.823532 769.415532 769.415532 978.823532 512 978.823532 254.584471 978.823532 45.176471 769.415532 45.176471 512 45.176471 254.584471 254.584471 45.176471 512 45.176471L512 45.176471ZM512 0C229.225412 0 0 229.225412 0 512 0 794.774589 229.225412 1024 512 1024 794.774589 1024 1024 794.774589 1024 512 1024 229.225412 794.774589 0 512 0L512 0 512 0ZM753.037809 595.574523C740.887122 571.288218 726.400824 543.778632 709.573427 513.067674 692.737807 482.356712 673.736438 450.170411 652.573425 416.506028 641.463834 400.548493 630.010409 394.301918 618.217262 397.766301L618.217262 364.971781C619.600824 360.466301 620.991232 356.122466 622.383012 351.958082 623.765207 347.793699 625.325481 343.288219 627.067945 338.423836L725.450138 338.423836C746.963835 338.423836 757.724109 328.192329 757.724109 307.711507 757.724109 287.238904 746.965207 276.999178 725.450138 276.999178L484.956986 276.999178C463.784383 276.999178 453.203561 287.238904 453.203561 307.711507 453.203561 316.740274 455.976164 324.108767 461.532329 329.834794 467.078904 335.560822 474.887123 338.423836 484.956986 338.423836L563.039176 338.423836C555.400822 362.027945 547.162465 383.451233 538.311782 402.711507 529.463834 421.971781 519.222738 440.451233 507.599452 458.149863 495.969315 475.848493 482.613151 493.206027 467.51726 510.204658 452.42137 527.212877 434.983014 545.082742 415.202192 563.822464 407.21452 571.808768 402.359726 578.315617 400.626849 583.343012 398.885753 588.377262 399.92685 594.192328 403.750137 600.780001 401.31863 602.521098 398.804932 604.423834 396.202192 606.507397 393.599452 608.589588 390.906301 610.49918 388.133699 612.233426L388.133699 513.849861 416.243288 513.849861C427.344657 513.849861 434.983014 511.07726 439.147397 505.521096 443.311781 499.97452 445.393973 492.507397 445.393973 483.137534 445.393973 472.726575 443.483014 465.007397 439.667945 459.973151 435.844658 454.947123 428.036439 452.425205 416.243288 452.425205L388.133699 452.425205 388.133699 338.425205 419.366575 338.425205C429.777534 338.425205 437.325479 335.562192 442.010411 329.836165 446.695343 324.110137 449.037808 316.741644 449.037808 307.712877 449.037808 297.301918 446.955616 289.493699 442.791233 284.288219 438.626849 279.08274 430.81863 276.48 419.366575 276.48L290.270685 276.48C278.11863 276.48 270.229589 279.08274 266.585754 284.288219 262.941918 289.493699 261.12 297.301918 261.12 307.712877 261.12 316.741644 263.202192 324.110137 267.366575 329.836165 271.530959 335.562192 279.159726 338.425205 290.270685 338.425205L327.229589 338.425205 327.229589 452.425205 296.51726 452.425205C284.365206 452.425205 276.476165 454.945754 272.832329 459.973151 269.188493 465.007397 267.366575 472.726575 267.366575 483.137534 267.366575 492.507397 269.448767 499.97452 273.613151 505.521096 277.777534 511.07726 285.406301 513.849861 296.51726 513.849861L327.229589 513.849861 327.229589 649.193697C319.241918 653.007396 311.873425 656.562191 305.106301 659.863562 298.339178 663.15671 292.173425 665.671782 286.626849 667.411507 276.556986 670.875889 269.270685 676.60329 264.763835 684.590961 260.250137 692.569042 260.250137 701.418358 264.763835 711.137536L263.72274 709.055345C267.187123 719.466301 271.962466 726.233426 278.037808 729.356713 284.104931 732.478628 293.914521 731.089587 307.448767 725.19233L306.928219 725.712876C315.599452 722.589588 325.667945 718.245755 337.12 712.699177 348.572055 707.144381 360.455617 700.985477 372.777534 694.219725 385.091233 687.453972 397.243288 680.075889 409.21589 672.09644 421.188493 664.110136 432.200822 655.960822 442.270685 647.632056L441.750137 648.152602C448.337808 643.297807 452.762466 637.66082 455.02411 631.233423 457.277534 624.816988 457.01726 618.138906 454.243288 611.193697 455.284384 610.500547 457.01726 608.940273 459.448767 606.508769 479.570685 587.426575 497.530959 568.947123 513.325481 551.069041 529.111782 533.199176 543.428219 514.370412 556.270684 494.589589L556.270684 713.740273C556.270684 724.843013 558.784384 732.822467 563.820001 737.685478 568.846029 742.541645 576.573425 744.973148 586.984381 744.973148 596.354248 744.973148 603.902193 742.541645 609.626849 737.685478 615.352878 732.821094 618.21589 724.843013 618.21589 713.740273L618.21589 480.534795C634.174797 506.562192 648.750136 531.637535 661.941919 555.753974 675.126851 579.87863 686.229591 602.173148 695.256986 622.644383L694.73644 621.603287C698.200822 630.630687 703.235067 637.659453 709.833697 642.686848 716.421371 647.721098 725.619999 647.981368 737.42274 643.466301L736.90219 643.986852C747.655619 640.171781 754.421371 633.755341 757.203564 624.725207 759.976166 615.706025 758.415892 605.814246 752.51863 595.055345L753.037809 595.574523 753.037809 595.574523Z',
              iconStyle: {
                borderColor: this.chainBtnColor,
              },
              onclick: () => {
                this.isChain = true;
                this.withdrawal();
              },
            },
          },
        },
        series: seriesArr,
      };
    },
    chainBtnColor() {
      return this.isChain ? '#1296db' : '#cccccc';
    },
    instantBtnColor() {
      return this.isChain ? '#cccccc' : '#1296db';
    },
    text() {
      const INSTANT_STR = '提现次数、金额及占比';
      const CHAIN_STR = '注册到进件各环节"环比"';
      return this.isChain ? CHAIN_STR : INSTANT_STR;
    },
    sunText() {
      const SUB_INSTANT_STR = '点击右侧按钮可切换到"环比"展示';
      const SUB_CHAIN_STR = '点击右侧按钮可切换到"数量"展示';
      return this.isChain ? SUB_CHAIN_STR : SUB_INSTANT_STR;
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
    async withdrawal() {
      const _init = () => {
        this.xData = [];
        this.withdrawCnt = [];
        this.staffWithdrawCnt = [];
        this.socialWithdrawCnt = [];
        this.staffWithdrawCntRatio = [];
        this.socialWithdrawCntRatio = [];
        this.withdrawSum = [];
        this.staffWithdrawSum = [];
        this.socialWithdrawSum = [];
        this.staffWithdrawSumRate = [];
        this.socialWithdrawSumRate = [];
      };
      try {
        this.loading = true;
        const params = {
          startDate: this.startDate
            ? dayjs(this.startDate).format('YYYYMMDD')
            : '',
          endDate: this.endDate ? dayjs(this.endDate).format('YYYYMMDD') : '',
          dateType: this.time,
          sourceType: this.osType,
          isChain: this.isChain,
        };
        const res = await withdrawal(params);
        _init();
        if (res.data && res.data.length > 0) {
          res.data.forEach(ele => {
            this.xData.push(ele.bizDate);
            this.withdrawCnt.push(ele.withdrawCnt);
            this.staffWithdrawCnt.push(ele.staffWithdrawCnt);
            this.socialWithdrawCnt.push(ele.socialWithdrawCnt);
            this.staffWithdrawCntRatio.push(ele.staffWithdrawCntRatio);
            this.socialWithdrawCntRatio.push(ele.socialWithdrawCntRatio);
            this.withdrawSum.push(ele.withdrawSum);
            this.staffWithdrawSum.push(ele.staffWithdrawSum);
            this.socialWithdrawSum.push(ele.socialWithdrawSum);
            this.staffWithdrawSumRate.push(ele.staffWithdrawSumRate);
            this.socialWithdrawSumRate.push(ele.socialWithdrawSumRate);
          });
        }
        this.loading = false;
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
    this.withdrawal();
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
