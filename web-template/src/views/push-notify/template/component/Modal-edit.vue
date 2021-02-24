<template>
  <div class="components-container">
    <div class="pd-main content-border">
      <el-row type="flex" justify="center" class="pt20">
        <el-col :xs="23" :sm="23" :md="23" :lg="23" :xl="23">
          <el-form ref="form1" label-position="right" :model="form1" :rules="rules" label-width="130px">
            <el-row :gutter="20" class="labelName">
              {{ form1.newTemplateType }}
              <el-col :xs="20" :sm="10" :md="10" :lg="10" :xl="10">
                <el-form-item label="模板类型" prop="newTemplateType">
                  <!--<code-select id="newTemplateType" v-model.trim="form1.newTemplateType" dictname="ALERT_CHANNEL" :disabled="disabled" @changed="getTemplateType" mystyle="width: 100%;" placeholder="请选择"/>-->
                  <el-select v-model="form1.newTemplateType" :placeholder="$t('placeholder.select')" @change="getTemplateType">
                    <el-option label="邮件" value="mail" />
                    <el-option label="钉钉" value="dingtalk" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1"> &nbsp;</el-col>
              <el-col :xs="20" :sm="10" :md="10" :lg="10" :xl="10">
                <el-form-item label="模板名称" prop="templateName">
                  <el-input id="templateName" v-model="form1.templateName" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1"> &nbsp;</el-col>
              <el-col :xs="20" :sm="10" :md="10" :lg="10" :xl="10">
                <el-form-item label="模板代码" prop="templateCode">
                  <el-input id="templateCode" v-model="form1.templateCode" :disabled="disabled" />
                </el-form-item>
              </el-col>
              <!--短信 极光 阿里 安吉 模板--->
              <div v-if="modalTemplateType == 'sms'" class="" style="background: #f8f8f8; width: 100%; float: left">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="">
                    <el-checkbox-group v-model="form1.signName" style="width: 100%; text-align: left" @change="signNameData">
                      <el-checkbox
                        v-for="(item, index) in signNameList"
                        :key="index"
                        :label="item.value"
                        :disabled="disabled"
                      ><b>{{ item.label }}</b></el-checkbox>
                    </el-checkbox-group>
                  </el-form-item>
                </el-col>
                <div v-for="(item, i) in form1.signName" :key="i">
                  <el-col v-if="item == 1" :xs="22" :sm="12" :md="12" :lg="12" :xl="12">
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="上汽安吉签名" prop="ajSignName">
                        <el-input id="ajSignName" v-model="form1.ajSignName" :disabled="disabled" />
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover placement="right" width="120" trigger="hover">
                        * 例：上汽安吉 *
                        <i slot="reference" class="el-icon-question" style="color: #8c939d" />
                      </el-popover>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="上汽安吉短信模板">
                        <el-input v-model="form.templateShow" type="textarea" :rows="7" placeholder="您正在找回密码，验证码：${code}，15分钟内有效，请勿泄露！" />
                      </el-form-item>
                    </el-col>
                  </el-col>
                  <el-col v-if="item == 2" :xs="22" :sm="12" :md="12" :lg="12" :xl="12">
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="阿里签名" prop="aliSignName">
                        <el-input id="aliSignName" v-model="form1.aliSignName" :disabled="disabled" />
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover placement="right" width="260" trigger="hover">
                        请前往阿里云获取签名<br>
                        <a href="https://dysms.console.aliyun.com/dysms.htm#/domestic/text/sign" target="_blank"><span style="color: #3e76d4">https://dysms.console.aliyun.com/dysms.htm#/domestic/text/sign</span></a>
                        <i slot="reference" class="el-icon-question" style="color: #8c939d" />
                      </el-popover>
                    </el-col>
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="阿里模板代码" prop="aliTemplateCode">
                        <el-input id="aliTemplateCode" v-model="form1.aliTemplateCode" :disabled="disabled" />
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover placement="right" width="260" trigger="hover">
                        请前往阿里云获取模板code<br>
                        <a href="https://dysms.console.aliyun.com/dysms.htm#/domestic/text/template" target="_blank"><span style="color: #3e76d4">https://dysms.console.aliyun.com/dysms.htm#/domestic/text/template</span></a>
                        <i slot="reference" class="el-icon-question" style="color: #8c939d" />
                      </el-popover>
                    </el-col>
                  </el-col>
                  <el-col v-if="item == 3" :xs="22" :sm="12" :md="12" :lg="12" :xl="12">
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="极光签名Id" prop="jgSignId">
                        <el-input id="jgSignId" v-model="form1.jgSignId" :disabled="disabled" />
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover placement="right" width="180" trigger="hover">
                        请前往极光官网获取签名id<br>
                        <a href="https://www.jiguang.cn" target="_blank"><span style="color: #3e76d4">https://www.jiguang.cn</span></a>
                        <i slot="reference" class="el-icon-question" style="color: #8c939d" />
                      </el-popover>
                    </el-col>
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="极光模板Id" prop="jgTemplateId">
                        <el-input id="jgTemplateId" v-model="form1.jgTemplateId" :disabled="disabled" />
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover placement="right" width="180" trigger="hover">
                        请前往极光官网获取模板id<br>
                        <a href="https://www.jiguang.cn" target="_blank"><span style="color: #3e76d4">https://www.jiguang.cn</span></a>
                        <i slot="reference" class="el-icon-question" style="color: #8c939d" />
                      </el-popover>
                    </el-col>
                  </el-col>
                </div>
              </div>
              <el-col v-if="modalTemplateType == 'mail'" :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                <el-form-item label="模板内容">
                  <htmlEdit ref="code" v-model="form1.templateShow" style="text-align: left" />
                </el-form-item>
              </el-col>
              <el-col v-if="modalTemplateType == 'dingtalk'" :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                <el-form-item label="模板内容">
                  <htmlEdit ref="code" v-model="form1.templateShow" style="text-align: left" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <div class="grid-content">
                  <el-button type="info" class="c-button" @click="close">取消</el-button>
                  <el-button v-if="modalTemplateType != 'sms'" type="success" class="c-button ml20" @click="previewTemplate(form1.templateShow)">预览 </el-button>
                  <el-button v-if="isFind" type="primary" class="c-button ml20" @click="saveBtn(proData)">保存</el-button>
                </div>
              </el-col>
            </el-row>
          </el-form>
          <el-dialog width="60%" title="模板预览" :visible.sync="innerVisible" append-to-body :close-on-click-modal="false" :lock-scroll="true">
            <el-row :gutter="20" class="test">
              <el-col :xs="20" :sm="14" :md="14" :lg="14" :xl="14">
                <div class="preview">
                  <div class="previewContent" v-html="previewContent" />
                </div>
              </el-col>
              <el-col v-if="proData != null" :xs="20" :sm="10" :md="10" :lg="10" :xl="10">
                <el-form ref="form" label-position="right" :model="form2" :rules="rules2" label-width="100px">
                  <el-row :gutter="20">
                    {{ form2 }}
                    <el-col v-if="modalTemplateType == 'mail'" :span="24">
                      <el-form-item label="标题" prop="subject">
                        <el-input id="title" v-model="form2.subject" placeholder="邮件测试" />
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item v-if="modalTemplateType == 'mail'" label="收件人" prop="email">
                        <el-input id="email" v-model="form2.to" placeholder="123@qq.com" />
                      </el-form-item>
                      <el-form-item v-else label="收件人" prop="email">
                        <el-input id="phone" v-model="form2.to" placeholder="18588888888" />
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="自定义参数" prop="paramMap">
                        <el-input id="paramMap" v-model="form2.paramMap" type="textarea" :autosize="{ minRows: 12, maxRows: 14 }" placeholder="格式：{'user':'userName'}" />
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-button type="success" class="c-button" :disabled="testMailDisabled" @click="testMail(form2)"> 发送测试 </el-button>
                    </el-col>
                  </el-row>
                </el-form>
              </el-col>
            </el-row>
          </el-dialog>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import { gaeaPushTemplateAdd, gaeaPushTemplateEdit, gaeaPushTemplatePreview, gaeaPushTemplateTestSendPush } from '@/api/push-notify'
// import {preview,saveTemplate,updateTemplate,showTemplateById,testSendMail} from "@/api/push/notify"
import htmlEdit from './htmlEdit'
// import CodeSelect from '@/components/codeSelect'
// import { parse } from 'path-to-regexp';
export default {
  name: 'ModalMailDemoDeploy',
  components: {
    htmlEdit,
    // CodeSelect
  },
  props: {
    proData: Object,
    templateDeployType: String,
    isFind: Boolean,
  },
  data() {
    return {
      form1: {
        signName: [],
        templateShow: '',
        template: '',
        newTemplateType: '',
        templateCode: '',
        id: '',
        aliSignName: '',
        aliTemplateCode: '',
        jgSignId: '',
        jgTemplateId: '',
        ajSignName: '',
      },
      signNameList: [
        { value: 1, label: '上汽安吉签名' },
        { value: 2, label: '阿里签名' },
        { value: 3, label: '极光签名Id' },
      ],
      signNameSelected: null,
      form2: {
        to: '',
        paramMap: "{'user':'userName'}",
      },
      modalTemplateType: this.templateDeployType,
      innerVisible: false, // 邮件模板内层预览弹框
      previewContent: {}, // 预览返回的信息
      rules: {
        newTemplateType: [
          {
            required: true,
            message: '模板名称不能为空',
            trigger: 'change',
          },
        ],
        templateName: [
          {
            required: true,
            message: '模板名称不能为空',
            trigger: 'blur',
          },
        ],
        templateCode: [
          {
            required: true,
            message: '模板Code不能为空',
            trigger: 'blur',
          },
        ],
        template: [
          {
            required: true,
            message: '短信内容不能为空',
            trigger: 'blur',
          },
        ],
      },
      rules2: {
        to: [{ required: true, message: '收件人不能为空', trigger: 'blur' }],
        paramMap: [
          {
            required: true,
            message: '格式为对象形式',
            trigger: 'blur',
          },
        ],
      },
      testMailDisabled: false,
      pushType: 1,
      timer: '', // 定时loading
      disabled: false,
      checkedCities: ['上海', '北京'],
    }
  },
  watch: {
    proData(val) {
      this.modalTemplateType = null
      this.mailDemoInformation(val)
    },
  },
  mounted() {
    this.mailDemoInformation(this.proData)
    this.$nextTick(() => {
      this.$refs['form1'].clearValidate()
    })
  },
  methods: {
    getTemplateType(val) {
      console.log(111, val)
      // const value = parseFloat(val.value)
      // this.modalTemplateType=this.getDictCode('ALERT_CHANNEL' ,value,"value").labelEng;
      // this.form1.newTemplateType=this.getDictCode('ALERT_CHANNEL' ,value).value;
      this.modalTemplateType = val
      // this.form1.newTemplateType=val;
      this.mailDemoInformation2()
    },
    mailDemoInformation2() {
      if (this.modalTemplateType == 'sms') {
        this.form1.templateShow = '您${name}正在找回密码，验证码：${code}，15分钟内有效，请勿泄露！'
      } else if (this.modalTemplateType == 'dingtalk') {
        this.form1.templateShow = '### 告警时间：{sendTime}\n### 告警名称：{alertName}\n### 告警等级：{alertlevel}\n### 告警项目：{projectName}\n### 告警信息\n{listbegin : alertInfoList : alertInfo }\n#### 时间: {alertInfo.alertTime}\n#### 维度: {alertInfo.alertInfoDmensions}\n#### 指标: {alertInfo.alertInfo}\n{ listend }\n\n本邮件由推送系统发送，请勿回复'
      } else {
        this.form1.templateShow = '<h4>亲爱的{user}，你好！</h4></br>\n</br>\n</br>\n \{使用关键字大括号\}</br> \n {test} </br>\n\n </br> \n\n\n 云邮件系统</br>'
      }
      this.$nextTick(() => {
        this.$refs.code && this.$refs.code.setContent(this.form1.templateShow)
      })
    },
    mailDemoInformation(val) {
      console.log(2222, val)
      if (val != null) {
        this.disabled = true
        this.form1.newTemplateType = val.templateType
        this.modalTemplateType = val.templateType
        this.form1.id = val.id
        if (val !== undefined) {
          this.proData = val
        }
        var res = val
        // let params={
        //   templateType:this.form1.newTemplateType,
        //   templateId:this.proData.templateId
        // };
        // gaeaPushTemplateDetail(this.proData.id).then(res=>{
        // if (res.code == '0000') {
        this.form1 = val
        // this.form1.newTemplateType=this.getDictCode('ALERT_CHANNEL' ,res.repData.templateType,"labelEng").value;
        this.modalTemplateType = res.templateType
        this.form1.newTemplateType = res.templateType
        if (this.modalTemplateType == 'sms') {
          this.form1.templateShow = res.template
          const smsTemplateAccount = res.smsTemplateAccount
          this.form1.aliTemplateCode = smsTemplateAccount.aliTemplateCode
          this.form1.jgSignId = smsTemplateAccount.jgSignId
          this.form1.jgTemplateId = smsTemplateAccount.jgTemplateId
          this.form1.aliSignName = smsTemplateAccount.aliSignName
          this.form1.ajSignName = smsTemplateAccount.ajSignName
          if (smsTemplateAccount.signNameSelected == null) {
            this.form1.signName = []
          } else {
            this.form1.signName = JSON.parse(smsTemplateAccount.signNameSelected)
          }
        } else {
          this.form1.templateShow = res.templateShow
          this.$nextTick(() => {
            this.$refs.code.setContent(this.form1.templateShow)
          })
        }
        // }
        // }).catch(error => {})
      } else {
        this.disabled = false
      }
    },
    signNameData(val) {
      this.signNameSelected = val
    },
    saveBtn(val) {
      this.$refs['form1'].validate((valid) => {
        if (valid) {
          const params = {
            id: this.form1.id,
            templateShow: this.form1.templateShow,
            templateCode: this.form1.templateCode,
            templateName: this.form1.templateName,
            templateType: this.modalTemplateType,
            smsTemplateAccount: {
              sendOrder: null,
              jgSignId: Number(this.form1.jgSignId),
              jgTemplateId: Number(this.form1.jgTemplateId),
              aliTemplateCode: this.form1.aliTemplateCode,
              aliSignName: this.form1.aliSignName,
              ajSignName: this.form1.ajSignName,
              signNameSelected: JSON.stringify(this.signNameSelected),
            },
          }
          if (this.proData != null) {
            gaeaPushTemplateEdit(params).then((res) => {
              if (res.code == '200') {
                this.close()
              }
            })
          } else {
            gaeaPushTemplateAdd(params).then((res) => {
              if (res.code == '200') {
                this.close()
              }
            })
          }
        } else {
          return false
        }
      })
    },
    // 关闭模态框事件
    close() {
      console.log(11234)
      this.$emit('closeModal')
    },
    destroyTimer() {
      if (this.timer) {
        clearInterval(this.timer)
      }
    },
    // 预览模板
    previewTemplate(val) {
      this.innerVisible = true
      let params = {}
      if (this.proData == null) {
        this.form2 = {
          to: '',
          paramMap: "{'user':'test'}",
        }
        params = {
          templateShow: val,
          templateType: this.modalTemplateType,
        }
      } else {
        this.form2 = {
          to: '',
          paramMap: this.proData.templateParam,
        }
        params = {
          templateShow: val,
          templateType: this.modalTemplateType,
          id: this.proData.id,
        }
      }

      gaeaPushTemplatePreview(params).then((res) => {
        // const res = {
        //   repCode: '0000',
        //   repMsg: null,
        //   repData: {
        //     id: null,
        //     templateName: null,
        //     templateCode: null,
        //     templateType: null,
        //     template: '<h4>亲爱的#%#user#%#，你好！</h4></br></br></br>{使用关键字大括号}</br>#%#test#%#</br></br>云邮件系统</br>',
        //     templateShow: '<h4>亲爱的{user}，你好！</h4></br>\n</br>\n</br>\n {使用关键字大括号}</br> \n {test} </br>\n\n </br> \n\n\n 云邮件系统</br>',
        //     templateParam: null,
        //     templateInfo: null,
        //     enableFlag: null,
        //     deleteFlag: null,
        //     createdBy: null,
        //     createdTime: null,
        //     updatedBy: null,
        //     updatedTime: null,
        //     paramMap: {test: 'text', user: 'text'},
        //     html: '<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><meta http-equiv="X-UA-Compatible" content="ie=edge"><title>Document</title></head><body><h4>亲爱的user，你好！</h4></br></br></br>{使用关键字大括号}</br>test</br></br>云邮件系统</br></body></html>',
        //   },
        //   success: true,
        //   error: false,
        // }
        if (res.code == '200') {
          this.form2.paramMap = JSON.stringify(res.data.paramMap)
          this.previewContent = res.data.html
        }
      })
    },
    // 发送测试
    testMail(val) {
      if (val.to === '') {
        this.$message({
          message: '收件人不能为空',
          type: 'error',
        })
      } else {
        if (this.modalTemplateType == 'mail') {
          this.pushType = 1
        } else if (this.modalTemplateType == 'dingtalk') {
          this.pushType = 2
        } else {
          this.pushType = 3
        }
        const params = {
          pushType: this.pushType,
          templateCode: this.form1.templateCode,
          to: this.form2.to,
          subject: this.form2.subject,
          from: 'test',
          copy: '',
          paramMap: JSON.parse(this.form2.paramMap),
        }
        this.testMailDisabled = true
        gaeaPushTemplateTestSendPush(params).then((res) => {
          if (res.code == '200') {
            this.innerVisible = false
            this.testMailDisabled = false
            this.innerVisible = false
          }
        })
      }
    },
  },
}
</script>
<style scoped lang="scss">
.preview {
  background: #f8f8f8;
  padding: 10px;
}

.test {
  margin-top: 10px;
  border-top: 1px solid #eeeeee;
  padding-top: 10px;
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
