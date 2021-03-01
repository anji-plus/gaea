<template>
  <div class="container">
    <h2>{{ title }}</h2>
    <div class="title">
      <div v-for="(item, index) in list" :key="index" class="item">
        • <span @click="itemClick(item)">{{ item.helpTitle }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import { querytitleByCategory } from '@/api/help-center'
export default {
  data() {
    return {
      title: '',
      list: [],
    }
  },
  watch: {
    $route: {
      handler(obj) {
        this.title = obj.query.title
        this.querytitleByCategory(obj.query.val)
      },
      deep: true,
    },
  },
  mounted() {
    this.title = this.$route.query.title
    this.querytitleByCategory(this.$route.query.val)
  },
  methods: {
    async querytitleByCategory(helpCategory) {
      const { code, data } = await querytitleByCategory({ helpCategory })
      if (code != '200') return
      this.list = data
    },
    // 详情
    itemClick(item) {
      this.$router.push({
        // path: `/helpCenList/detail`,
        name: 'helpDetails',
        params: {
          item,
        },
      })
    },
  },
}
</script>
<style scoped>
.container h2 {
  font-size: 16px;
  color: #000;
  font-weight: bold;
  margin: 0 0 5px;
}
.container .title {
  margin: 0 0 0 5px;
}
.container .title .item {
  line-height: 30px;
  padding: 0 20px 0 0;
}
.container .title .item span {
  color: #1e5494;
  cursor: pointer;
}
.container .title .item span:hover {
  text-decoration: underline;
}
</style>
