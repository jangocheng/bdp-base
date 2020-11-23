<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger
      :toggle-click="toggleSideBar"
      :is-active="sidebar.opened"
      class="hamburger-container"
    />
    <breadcrumb/>
    <!-- 下列注释模块打开即是登录功能 -->
    <!--    <el-dropdown class="avatar-container" trigger="click">-->
      <div class="avatar-container name">
        {{realName}}
        <!-- <i class="el-icon-caret-bottom icon"/> -->
      </div>
<!--      <el-dropdown-menu slot="dropdown" class="user-dropdown">-->
<!--        <el-dropdown-item>-->
<!--          <span style="display:block;" @click="logout">登出</span>-->
<!--        </el-dropdown-item>-->
<!--      </el-dropdown-menu>-->
    <!--    </el-dropdown>-->
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex';
import Breadcrumb from '@/components/Breadcrumb';
import Hamburger from '@/components/Hamburger';
import { getToken, removeToken } from '@/utils/auth.js';

export default {
  components: {
    Breadcrumb,
    Hamburger,
  },
  computed: {
    ...mapGetters(['sidebar', 'avatar']),
    realName() {
      return decodeURI(getToken('realName'));
    },
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar');
    },
    // 没有向后台验证，暂且清除Cookie
    logout() {
      this.$confirm('此操作将登出，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          removeToken('realName');
          removeToken('token');
          this.$router.go(0);
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消登出',
          });
        });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0 !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    /*cursor: pointer;*/

    .icon {
      position: absolute;
      right: -20px;
      top: calc(50% - 7px);
    }
  }

  .name {
    right: 14px;
    white-space: nowrap;
  }
}
</style>
