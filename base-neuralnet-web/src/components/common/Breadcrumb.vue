<template>
  <el-breadcrumb>
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item) in $route.matched" :key="item.path" class="my-breadcrumb">
        {{ item.meta.title }}
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
export default {
  name: 'Breadcrumb',
};
</script>

<style lang="less" scoped>
.el-breadcrumb {
  display: flex;
  align-items: center;
  margin-left: 10px;
}

.breadcrumb-enter-active,
.breadcrumb-leave-active {
  transition: all 0.5s;
}

.breadcrumb-enter,
.breadcrumb-leave-active {
  opacity: 0;
  transform: translateX(20px);
}

.breadcrumb-move {
  transition: all 0.5s;
}

.breadcrumb-leave-active {
  position: absolute;
}
</style>

<style lang="less">
.my-breadcrumb {
  .el-breadcrumb__inner {
    color: #dfe3ea !important;
  }
}
</style>
