<template>
  <div
    v-if="!item.meta || !item.meta.hidden"
    :class="['menu-wrapper', isCollapse ? 'simple-mode' : 'full-mode', {'first-level': isFirstLevel}]"
  >
    <template v-if="!alwaysShowRootMenu && theOnlyOneChild && !theOnlyOneChild.children">
      <sidebar-item-link
        v-if="theOnlyOneChild.meta"
        :to="resolvePath(theOnlyOneChild.path)"
      >
        <el-menu-item
          :class="{'submenu-title-noDropdown': isFirstLevel}"
          :index="resolvePath(theOnlyOneChild.path)"
        >
          <svg-icon
            v-if="theOnlyOneChild.meta.icon"
            :name="theOnlyOneChild.meta.icon"
          />
          <span
            v-if="theOnlyOneChild.meta.title"
            slot="title"
          >{{ theOnlyOneChild.meta.title }}</span>
        </el-menu-item>
      </sidebar-item-link>
    </template>
    <el-submenu
      v-else
      :index="resolvePath(item.path)"
      popper-append-to-body
    >
      <template slot="title">
        <svg-icon
          v-if="item.meta && item.meta.icon"
          :name="item.meta.icon"
        />
        <span
          v-if="item.meta && item.meta.title"
          slot="title"
        >{{ item.meta.title }}
        </span>
      </template>
      <template v-if="item.children">
        <sidebar-item
          v-for="child in item.children"
          :key="child.path"
          :base-path="resolvePath(child.path)"
          :is-collapse="isCollapse"
          :is-first-level="false"
          :item="child"
          class="nest-menu"
        />
      </template>
    </el-submenu>
  </div>
</template>

<script lang="ts">
import path from 'path'
import { Component, Prop, Vue } from 'vue-property-decorator'
import { RouteConfig } from 'vue-router'
import { isExternal } from '@/utils/validate'
import SidebarItemLink from './SidebarItemLink.vue'

  @Component({
    name: 'SidebarItem',
    components: {
      SidebarItemLink
    }
  })
export default class extends Vue {
    @Prop({ required: true }) private item!: RouteConfig
    @Prop({ default: false }) private isCollapse!: boolean
    @Prop({ default: true }) private isFirstLevel!: boolean
    @Prop({ default: '' }) private basePath!: string

    get alwaysShowRootMenu() {
      return !!(this.item.meta && this.item.meta.alwaysShow)
    }

    get showingChildNumber() {
      if (this.item.children) {
        const showingChildren = this.item.children.filter((item) => {
          return !(item.meta && item.meta.hidden)
        })
        return showingChildren.length
      }
      return 0
    }

    get theOnlyOneChild() {
      if (this.showingChildNumber > 1) return null
      if (this.item.children) {
        for (let child of this.item.children) {
          if (!child.meta || !child.meta.hidden) {
            return child
          }
        }
      }
      return { ...this.item, path: '' }
    }

    private resolvePath(routePath: string) {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(this.basePath)) {
        return this.basePath
      }
      return path.resolve(this.basePath, routePath)
    }
}
</script>

<style lang="scss">
  .el-submenu.is-active > .el-submenu__title {
    color: $subMenuActiveText !important;
  }

  .full-mode {
    .nest-menu .el-submenu > .el-submenu__title,
    .el-submenu .el-menu-item {
      min-width: $sideBarWidth !important;
      background-color: $subMenuBg !important;

      &:hover {
        background-color: $subMenuHover !important;
      }
    }
  }

  .simple-mode {
    &.first-level {
      .submenu-title-noDropdown {
        padding: 0 !important;
        position: relative;

        .el-tooltip {
          padding: 0 !important;
        }
      }

      .el-submenu {
        overflow: hidden;

        & > .el-submenu__title {
          padding: 0 !important;

          .el-submenu__icon-arrow {
            display: none;
          }

          & > span {
            visibility: hidden;
          }
        }
      }
    }
  }
</style>

<style lang="scss" scoped>
  .svg-icon {
    margin-right: 16px;
  }

  .simple-mode {
    .svg-icon {
      margin-left: 20px;
    }
  }
</style>
