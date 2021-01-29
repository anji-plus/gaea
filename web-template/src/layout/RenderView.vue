<!-- 
 * @Author: zyk
 * @Date: 2021-01-28 15:34:51
 * @Last Modified by: zyk
 * @Last Modified time: 2021-01-25 16:54:51
 -->
<template>
  <keep-alive :include="cachedViews">
    <router-view :key="key" />
  </keep-alive>
</template>

<script>
export default {
  name: 'RenderView',
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    key() {
      return this.$route.path
    },
  },
}
</script>
