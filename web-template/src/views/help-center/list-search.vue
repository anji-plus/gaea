<template>
  <div class="container">
    <div class="searchNum">
      搜索"<font style="color: #c03">{{ searchForm.helpContent }}</font>",共找到<b>{{ totalCount }}</b>个相关的问题。
    </div>
    <div v-for="item in list" :key="item.id" class="infoBox">
      <div class="infoItem">
        <div class="infoTitle" @click="goDetail(item)">{{ item.helpTitle }}</div>
        <div class="infoCon" v-html="item.helpContent" />
      </div>
    </div>
    <el-pagination v-show="totalCount > 0" background :current-page.sync="searchForm.pageNumber" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="totalCount" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
  </div>
</template>
<script>
import { searchKeyWord } from '@/api/help-center'
export default {
  data() {
    return {
      searchForm: {
        helpContent: '', // 搜索内容
        pageNumber: 1,
        pageSize: 10,
      },
      totalCount: 0,
      list: [],
    }
  },
  watch: {
    $route: {
      handler(obj) {
        const val = obj.query.searchInput
        this.searchForm.helpContent = val
        this.searchKeyWord()
      },
      deep: true,
    },
  },
  mounted() {
    this.searchForm.helpContent = this.$route.query.searchInput
    this.searchKeyWord()
  },
  methods: {
    async searchKeyWord() {
      const { data, code } = await searchKeyWord(this.searchForm)
      if (code != '200') return
      this.list = data.records
      this.totalCount = data.total
    },
    goDetail(item) {
      this.$router.push({
        name: 'helpDetails',
        params: {
          item,
        },
      })
    },
    // 页码改变
    handleCurrentChange(pageNumber) {
      this.searchForm.pageNumber = pageNumber
      this.searchKeyWord()
    },
    // 每页size改变时
    handleSizeChange(val) {
      this.searchForm.pageNumber = 1
      this.searchForm.pageSize = val
      this.searchKeyWord()
    },
  },
}
</script>
<style scoped>
.container .searchNum {
  line-height: 32px;
  padding: 0 15px;
  color: #000;
  font-size: 14px;
  background: #fdffc6;
  margin: 0 0 6px 0;
}
.container .infoBox {
  margin: 0 0 0 15px;
}
.container .infoItem {
  margin: 25px 0;
}
.container .infoItem .infoTitle {
  font-size: 16px;
  color: #1e5494;
  font-weight: bold;
  margin: 0 0 20px;
  cursor: pointer;
}
.container .infoItem .infoTitle:hover {
  text-decoration: underline;
}
.container .infoItem .infoCon {
  color: #666;
}
</style>
