/*
 * @Author: wlhbdp
 * @Description: 框格组件--用来存放具体的图表对象组件
 */
/<template>
  <div id="grid">
    <div class="content">
      <!-- TODO:此处可优化 -->
      <img src="../../public/img/icons/Angle-zs.png" class="left-top">
      <img src="../../public/img/icons/Angle-zx.png" class="left-bottom">
      <img src="../../public/img/icons/Angle-ys.png" class="right-top">
      <img src="../../public/img/icons/Angle-yx.png" class="right-bottom">
      <!-- 此处存放具体的业务组件 -->
      <slot></slot>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  data() {
    return {};
  },
};
</script>


<style lang="less">
#grid {
  width: 100%;
  height: 100%;
  display: flex;
  // justify-content: center;
  // text-align: center;
  // padding: 10px;
  .content {
    flex: 1;
    display: flex;
    box-shadow: 0px 0px 15px #4c67af inset;
    position: relative;
    background-color: #0e3060;
    img {
      width: 48px;
      height: 48px;
      position: absolute;
    }
    .left-top {
      left: 0;
      top: 0;
    }
    .left-bottom {
      left: 0;
      bottom: 0;
    }
    .right-top {
      right: 0;
      top: 0;
    }
    .right-bottom {
      right: 0;
      bottom: 0;
    }
  }
}
</style>
