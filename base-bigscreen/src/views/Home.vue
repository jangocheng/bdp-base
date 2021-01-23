<template>
  <div id="home">
    <!-- 头部 -->
    <div class="header">
      <Header
        @listenFooActive="listenFooActive"
        :active="active"
        ref="header"
      ></Header>
    </div>
    <div class="main">
      <component :is="currentTabComponent"></component>
      <!-- <Elecwallet v-if="active===0"></Elecwallet>
      <Staging v-else-if="active===1"></Staging>
      <RealTImeSalary v-else></RealTImeSalary>-->
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import Elecwallet from './Elecwallet.vue';
import Staging from './Staging.vue';
import RealTImeSalary from './RealTImeSalary.vue';

export default {
  name: 'home',
  data() {
    return {
      active: 2,
      componentsArr: ['Elecwallet', 'Staging', 'RealTImeSalary'],
      intervalObj: null, //计时器对象
    };
  } /*  */,
  computed: {
    currentTabComponent() {
      const temCom = this.componentsArr[this.active];
      return temCom;
    },
  },
  methods: {
    listenFooActive(index) {
      this.active = index;
    },
    initIntervalObj() {
      this.intervalObj = setInterval(() => {
        if (this.active === this.componentsArr.length - 1) {
          this.active = 0;
        } else {
          this.active++;
        }
      }, 1000 * 20);
    },
  },
  watch: {
    active(value) {
      if (this.intervalObj) {
        clearInterval(this.intervalObj);
        this.initIntervalObj();
      }
    },
  },
  beforeDestroy() {
    if (this.intervalObj) {
      clearInterval(this.intervalObj);
      this.initIntervalObj();
    }
  },
  created() {
    this.initIntervalObj();
    setInterval(() => {
      window.location.reload('true');
    }, 1000 * 60 * 10);
  },
  components: {
    Header,
    Elecwallet,
    Staging,
    RealTImeSalary,
  },
};
</script>

<style lang="less">
@import '../assets/less/baseVar.less';

#home {
  width: 100%;
  height: 100%;
  background-color: @baseBackground;
  .header {
    width: 100%;
    height: 15%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .main {
    width: 98%;
    height: calc(100% - 15% - 20px);
    padding: 10px;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
    > div {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
