<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu">
      <template v-if="device !== 'mobile'">
        <el-link :underline="false" target="_blank" href="https://github.com/anji-plus/gaea" class="doc">{{ $t('navbar.github') }}</el-link>
        <!-- <div href="https://github.com/anji-plus/gaea" class="right-menu-item doc">盖亚</div> -->
        <!-- 搜索功能按钮 -->
        <!-- <search id="header-search" class="right-menu-item" /> -->
        <!-- 国际化 -->
        <lang-select class="right-menu-item hover-effect" />
        <!-- 全屏展示功能按钮 -->
        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip :content="$t('navbar.size')" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>
      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" placement="bottom-end" :hide-on-click="true" trigger="click">
        <div class="avatar-wrapper">
          <span class="user-avatar">{{ displayName }}</span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <div class="drop_up">
            <p class="drop_up_tit">{{ $t('navbar.personal') }}</p>
            <div class="info">
              <div>
                <p><i class="el-icon-user" /> {{ $t('navbar.account') }}</p>
                <p>{{ displayName }}</p>
              </div>
              <div>
                <p><i class="el-icon-help" /> {{ $t('navbar.organisation') }}</p>
                <!-- <p> {{'组织名称'}} </p> -->
                <el-select v-model="myOrgani" class="organisation" size="mini" :placeholder="$t('placeholder.select')" @change="changeOrg(val)">
                  <el-option v-for="item in orgList" :key="item.id" :label="item.orgName" :value="item.orgCode" />
                </el-select>
              </div>
            </div>
            <div class="btn_group">
              <router-link to="/change-password">
                <el-dropdown-item>
                  <el-button type="primary" plain> {{ $t('navbar.changePassword') }} </el-button>
                </el-dropdown-item>
              </router-link>
              <el-dropdown-item>
                <el-button type="danger" plain @click.native="logout"> {{ $t('navbar.logOut') }} </el-button>
              </el-dropdown-item>
            </div>
            <!-- <p class="help_center"> -->
            <el-link type="info" class="help_center" @click="$router.push('/helpCenList')">帮助中心</el-link>
            <!-- <router-link to="/helpCenList">
                帮助中心
              </router-link> -->
            <!-- </p> -->
          </div>
          <!-- <router-link to="/change-password">
            <el-dropdown-item>
              {{ $t('navbar.changePassword') }}
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item> -->
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
// import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import LangSelect from '@/components/LangSelect'
// import Search from '@/components/HeaderSearch'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    // ErrorLog,
    Screenfull,
    SizeSelect,
    LangSelect,
    // Search
  },
  data() {
    return {
      displayName: Cookies.get('displayName'),
      infoShow: false,
      myOrgani: Cookies.get('orgCode'),
    }
  },
  computed: {
    ...mapGetters(['sidebar', 'avatar', 'device', 'orgList']),
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    displayInfo() {
      this.infoShow = !this.infoShow
    },
    changeOrg(val) {
      Cookies.set('orgCode', val)
      location.reload()
      // this.$store.dispatch('')
    },
  },
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.1);
        }
      }
    }
    .doc {
      display: inline-block;
      padding: 0 8px;
      vertical-align: text-bottom;
      font-size: 12px;
    }
    .user_info {
      position: relative;
      .user_avatar {
        // display: block;
        font-size: 14px;
        line-height: 45px;
        cursor: pointer;
        height: 40px;
        border-radius: 10px;
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          display: block;
          font-size: 14px;
          line-height: 45px;
          cursor: pointer;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 15px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
<style lang="scss">
.drop_up {
  position: absolute;
  width: 300px;
  // height: 230px;
  background: #fff;
  padding-bottom: 10px;
  top: 6%;
  right: 15px;
  border-radius: 4px;
  box-shadow: 0px 4px 8px 0 rgba(0, 0, 0, 0.2);
  transform: translate(20px, 0);
  color: #333;
  font-size: 16px;
  text-align: center;
  .drop_up_tit {
    position: relative;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    background-color: #1989fa;
    width: 100%;
    height: 40px;
    line-height: 40px;
    margin: 0;
    color: #ffffff;
    &::after {
      content: '';
      width: 0;
      height: 0;
      position: absolute;
      top: -10px;
      right: 40px;
      border-bottom: solid 10px #1989fa;
      border-left: solid 10px transparent;
      border-right: solid 10px transparent;
    }
  }
  .info {
    width: 70%;
    margin: 0 auto;
    font-size: 14px;
    p {
      margin: 0;
    }
    div {
      width: 100%;
      height: 40px;
      line-height: 40px;
      display: flex;
      align-items: center;
      justify-content: space-around;
    }
    .organisation {
      width: 110px;
    }
  }
  .btn_group {
    display: flex;
    justify-content: space-around;
    margin: 10px 0;
    li:hover {
      background-color: transparent;
    }
  }
  .help_center {
    display: inline-block;
    // width: 90px;
    height: 12px;
    margin: 0 auto;
    text-align: center;
    // a{
    //   text-decoration: underline;
    font-size: 12px;
    // }
  }
}
.popper__arrow {
  display: none !important;
  &::after {
    display: none !important;
  }
}
</style>
