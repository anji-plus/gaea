<!--
 * @Author: zyk 
 * @Date: 2020-07-13 16:10:40 
 * @Last Modified by:   zyk 
 * @Last Modified time: 2020-07-13 16:10:40 
 */
!-->
<template>
  <el-dropdown trigger="click" class="international" @command="handleSetLanguage">
    <div>
      <svg-icon class-name="international-icon" :icon-class="`lang-${language}`" />
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item :disabled="language === 'zh'" command="zh">
        <div class="lang_style">
          <svg-icon class-name="international-icon" :icon-class="`lang-zh`" />
          <span>中文</span>
        </div>
      </el-dropdown-item>
      <el-dropdown-item :disabled="language === 'en'" command="en">
        <div class="lang_style">
          <svg-icon class-name="international-icon" :icon-class="`lang-en`" />
          <span>English</span>
        </div>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  computed: {
    language() {
      return this.$store.getters.language
    },
  },
  methods: {
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('app/setLanguage', lang)
      document.title = this.$t('system.name')
      this.$message({
        message: this.$t('promptMessage.success'),
        type: 'success',
      })
    },
  },
}
</script>
<style lang="scss" scoped>
.lang_style {
  display: flex;
  width: 60px;
  align-items: center;
  justify-content: space-between;
}
</style>
