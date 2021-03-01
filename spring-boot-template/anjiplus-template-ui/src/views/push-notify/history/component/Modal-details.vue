<template>
  <div class="components-container" style="position: revert">
    <div class="pd-main content-border">
      <el-row type="flex" justify="center" class="pt20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <div class="grid-content">
            <div class="right-box">
              <div v-if="mailDetails !== undefined" class="log-list">
                <div class="l-mailDemo">
                  <ul v-if="temolateType == 'mail'">
                    <li>
                      <label>收件人：</label>
                      <div>{{ mailDetails.pushTo }}</div>
                    </li>
                    <li>
                      <label>发送时间：</label>
                      <div>{{ dateFormat(mailDetails.createTime) }}</div>
                    </li>
                    <li>
                      <label>内容：</label>
                      <div class="mail-detail" v-html="mailDetails.content" />
                    </li>
                    <li>
                      <label>发送状态：</label>
                      <div v-if="mailDetails.sendStatus == 1" style="color: #55a532">成功</div>
                      <div v-if="mailDetails.sendStatus == 0" style="color: red">失败</div>
                    </li>
                    <li>
                      <label>返回参数：</label>
                      <div>{{ mailDetails.sendResult }}</div>
                    </li>
                  </ul>
                  <ul v-else>
                    <li>
                      <label>收件人：</label>
                      <div>{{ mailDetails.pushTo }}</div>
                    </li>
                    <li>
                      <label>发送时间：</label>
                      <div>{{ dateFormat(mailDetails.createdTime) }}</div>
                    </li>
                    <li>
                      <label>内容：</label>
                      <div>{{ mailDetails.content }}</div>
                    </li>
                    <li>
                      <label>发送状态：</label>
                      <div v-if="mailDetails.sendStatus == 1" style="color: #55a532">成功</div>
                      <div v-if="mailDetails.sendStatus == 0" style="color: red">失败</div>
                    </li>
                    <li>
                      <label>返回参数：</label>
                      <div>{{ mailDetails.sendResult }}</div>
                    </li>
                  </ul>
                </div>
              </div>
              <div v-if="templateDetails !== undefined" class="log-list">
                <div class="l-mailDemo">
                  <ul>
                    <li><h4>亲爱的{user}，你好！</h4></li>
                    <li>告警名称：{{ templateDetails.templateName }}</li>
                    <li>告警Code：{{ templateDetails.templateCode }}</li>
                    <li><div v-html="templateDetails.templateShow" /></li>
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
  props: { mailDetails: Object, templateDetails: Object, temolateType: String },
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
.mail-detail {
  background: #002240;
  padding: 10px;
  color: #fff;
}
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
