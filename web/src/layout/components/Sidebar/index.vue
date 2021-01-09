<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <div class="admin-title" @click="goBigScreen" v-if="hasPermission('bigScreenManage:find')">
      AJ-Gaea(盖亚)
    </div>
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#304DA7"
      text-color="#fefefe"
      active-text-color="#409EFF"
    >
      <sidebar-item v-for="route in routes" :key="route.path" :item="route" :base-path="route.path"/>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    routes() {
      return this.$router.options.routes
    },
    isCollapse() {
      return !this.sidebar.opened
    },
    
  },
  methods:{
    goBigScreen(){
      let routeUrl = this.$router.resolve({
        path: "/bigScreen"
      });
      window.open(routeUrl.href, '_blank');
    }
  }
}
</script>

<style lang="scss" scoped>
  .admin-title{
    height: 60px;
    line-height: 60px;
    text-align: center;
    width: 100%;
    background: linear-gradient(180deg, #386ACB 0%, #304DA7 100%);
    font-weight: 500;
    color: #FFFFFF;
    font-size: 14px;
  }
  .admin-title:hover{
    cursor: pointer;
  }
</style>
