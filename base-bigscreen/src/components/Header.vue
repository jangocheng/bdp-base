<template>
  <div id="header">
    <div
      v-for="(item,index) in titleTextArr"
      :key="index"
      @click="fooActive(index)"
      :class="{active:active==index}"
      class="title"
    >{{item}}</div>
  </div>
</template>

<script type="es6">
export default {
  props: ['active'],
  data() {
    return {
      titleTextArr: ['电子钱包看板', '电商看板', '收入看板'],
    };
  },
  // mounted() {
  //   console.log(this.active);
  //   if (window.localStorage.active) {
  //     this.$emit('listenFooActive', window.localStorage.active);
  //   } else {
  //     window.localStorage.active = 0;
  //   }
  // },
  methods: {
    // 修改active
    fooActive(index) {
      // window.localStorage.active = index;
      this.$emit('listenFooActive', index);
    },
  },
};
</script>

<style  lang="less">
#header {
  display: flex;
  justify-content: space-around;
  .title {
    color: #e5e5e5;
    font-weight: 900;
    font-size: 24px;
    margin: 0 10px;
    text-align: center;
    height: 79px;
    width: 194px;
    line-height: 84px;
    padding: 0 44px;
    background-image: url('../../public/img/icons/header.png');
    background-size: 100%;
    background-repeat: no-repeat;
    cursor: pointer;
    &:hover {
      background-image: url('../../public/img/icons/header-active.png');
      background-size: 100%;
      background-repeat: no-repeat;
      color: #ffa800;
    }
  }
  .active {
    color: #ffa800;
    background-image: url('../../public/img/icons/header-active.png');
    background-size: 100%;
    background-repeat: no-repeat;
  }
}
</style>
