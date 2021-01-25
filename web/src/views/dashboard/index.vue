<!--
 * @Author: zhangyukuan 
 * @Date: 2020-07-13 16:10:40 
 * @Last Modified by:   zhangyukuan 
 * @Last Modified time: 2020-07-13 16:10:40 
 */
!-->
<template>
  <div class="dashboard-container">
    <img :src="bg" alt="背景图" class="dash_bg">
  </div>
</template>

<script>
import bg from '@/assets/images/index.png'
export default {
  name: 'Dashboard',
  data() {
    return {
      bg,
    }
  },
}
</script>
<style scoped lang="scss">
.dashboard-container {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
  // font-size: 30px;
  .dash_bg {
    display: block;
    width: 100%;
    height: 100%;
  }
}
.fixed-header + .app-main > .dashboard-container {
  top: 50px;
}
.hasTagsView .app-main > .dashboard-container {
  top: 84px;
}
</style>
