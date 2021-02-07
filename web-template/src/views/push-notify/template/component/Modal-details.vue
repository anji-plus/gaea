<template>
  <div class="components-container" style="position: revert">
    <div class="pd-main content-border">
      <el-row type="flex" justify="center" class="pt20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <div class="grid-content">
            <div class="right-box">
              <div class="log-list">
                <div class="l-mailDemo">
                  <ul>
                    <li>
                      <label>模板名称</label>
                      <div>{{ templateDetails.templateName }}</div>
                    </li>
                    <li>
                      <label>更新时间：</label>
                      <div>{{ dateFormat(templateDetails.updatedTime) }}</div>
                    </li>
                    <li>
                      <label>内容：</label>
                      <div v-if="templateDetails.temolateType == 'mail'" v-html="templateDetails.template" />
                      <div v-else>{{ templateDetails.template }}</div>
                    </li>
                    <li>
                      <label>模板参数：</label>
                      <div>{{ templateDetails.templateParam }}</div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
// import {preview} from "@/api/push/notify"
import moment from 'moment'

export default {
  name: 'ModalMailDemoDeploy',
  props: { templateDetails: Object },
  data() {
    return {}
  },
  mounted() {},
  methods: {
    // 时间格式化
    dateFormat: function(row, column) {
      var date = row
      if (date == undefined) {
        return ''
      }
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    // 关闭模态框事件
    close() {
      this.$emit('closeModal')
    },
  },
}
</script>
<style scoped lang="scss">
.l-mailDemo {
  ul li {
    text-align: left;
    line-height: 24px;
    font-size: 14px;
    color: #333;
    width: 100%;
    float: left;
    margin-bottom: 10px;
    word-wrap: break-word;
    label {
      width: 120px;
      text-align: left;
      display: block;
      float: left;
    }
    div {
      text-align: left;
      display: inline-block;
      float: left;
      width: 80%;
    }
  }
}

.labelName {
  margin-bottom: 0.9375rem;
  font-size: 0.875rem;
  color: #606266;
  line-height: 40px;
}

.labelInput {
  margin-bottom: 1.875rem;
  #proIcons {
    border: 1px solid #c0c4cc;
    padding: 0.3125rem;
    li {
      float: left;
      margin-right: 10px;
    }
  }
}

.previewContent {
  line-height: 36px;
}
</style>
