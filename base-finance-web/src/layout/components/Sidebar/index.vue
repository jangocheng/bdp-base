<template>
  <div :class="{'has-logo': showLogo}">
    <sidebar-logo
      v-if="showLogo"
      :collapse="isCollapse"
    />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :active-text-color="menuActiveTextColor"
        :background-color="variables.menuBg"
        :collapse="isCollapse"
        :collapse-transition="false"
        :default-active="activeMenu"
        :text-color="variables.menuText"
        :unique-opened="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="route in routes"
          :key="route.path"
          :base-path="route.path"
          :is-collapse="isCollapse"
          :item="route"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { AppModule } from '@/store/modules/app'
import { PermissionModule } from '@/store/modules/permission'
import SidebarItem from './SidebarItem.vue'
import SidebarLogo from './SidebarLogo.vue'
import variables from '@/styles/_variables.scss'

  @Component({
    name: 'SideBar',
    components: {
      SidebarItem,
      SidebarLogo
    }
  })
export default class extends Vue {
  get sidebar() {
    return AppModule.sidebar
  }

    showLogo: boolean = false;

    get routes() {
      console.log(PermissionModule.routes)
      return PermissionModule.routes
    }

    get menuActiveTextColor() {
      return variables.menuActiveText
    }

    get variables() {
      return variables
    }

    get activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // 如果设置路径，侧栏将突出显示您设置的路径
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    }

    get isCollapse() {
      return !this.sidebar.opened
    }
}
</script>

<style lang="scss">
  .sidebar-container {
    .horizontal-collapse-transition {
      transition: 0s width ease-in-out, 0s padding-left ease-in-out, 0s padding-right ease-in-out;
    }

    .scrollbar-wrapper {
      overflow-x: hidden !important;
    }

    .el-scrollbar__view {
      height: 100%
    }

    .el-scrollbar__bar {
      &.is-vertical {
        right: 0;
      }

      &.is-horizontal {
        display: none;
      }
    }
  }
</style>

<style lang="scss" scoped>
  .el-scrollbar {
    height: 100%
  }

  /*是否显示logo块*/
  .has-logo {
    .el-scrollbar {
      height: calc(100% - 50px);
    }
  }

  .el-menu {
    border: none;
    height: 100%;
    width: 100% !important;
  }
</style>
