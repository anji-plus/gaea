<template>
  <div class="container">
    <el-button type="primary" size="mini" plain @click="goBack">返回</el-button>
    <h2 class="helpTitle">{{ details.helpTitle }}</h2>
    <div class="answer" v-html="helpContent" />
  </div>
</template>
<script>
import { queryById } from '@/api/help-center'
export default {
  data() {
    return {
      details: {
        helpTitle: '',
      },
      helpContent: '',
    }
  },
  mounted() {
    this.details = this.$route.params.item
    this.queryById()
  },
  methods: {
    async queryById() {
      const { code, data } = await queryById({ id: this.details.id, accessKey: this.details.accessKey })
      if (code != '200') return
      const helpContent = data.helpContent
      const helpContentVideo = helpContent.replace(/<oembed url/gi, "<video controls='controls' src").replace(/oembed>/gi, 'video>')
      this.helpContent = helpContentVideo
    },
    goBack() {
      this.$router.back()
    },
  },
}
</script>
<style scope>
.container .helpTitle {
  font-size: 16px;
  color: #000;
  font-weight: bold;
  margin: 20px 0 5px;
}
.container .answer {
  margin: 20px 0 0 0;
}
.answer .table table {
  border-collapse: collapse;
  border-spacing: 0;
  border: 1px double #b3b3b3;
}
.answer .table table td,
.answer .table table th {
  min-width: 2em;
  padding: 0.4em;
  border: 1px solid #bfbfbf;
}
video {
  width: 100%;
}
</style>
