<template>
  <div class="components-container">
    <div class="pd-main content-border">
      <el-row type="flex" justify="center" class="pt20">
        <el-col :xs="23" :sm="23" :md="23" :lg="23" :xl="23">
          <el-form ref="form1" label-position="right" :model="form1" :rules="rules" label-width="130px">
            <el-row :gutter="20" class="labelName">
              <el-col :xs="20" :sm="10" :md="10" :lg="10" :xl="10">
                <el-form-item label="模板类型" prop="newTemplateType">
                  <code-select id="newTemplateType" v-model.trim="form1.newTemplateType" dictname="ALERT_CHANNEL" :disabled="disabled" @changed="getTemplateType" mystyle="width: 100%;" placeholder="请选择"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1">
                &nbsp;
              </el-col>
              <el-col :xs="20" :sm="10" :md="10" :lg="10" :xl="10">
                <el-form-item label="模板名称" prop="templateName">
                  <el-input id="templateName" v-model="form1.templateName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1">
                &nbsp;
              </el-col>
              <el-col :xs="20" :sm="10" :md="10" :lg="10" :xl="10">
                <el-form-item label="模板代码" prop="templateCode">
                  <el-input id="templateCode" v-model="form1.templateCode" :disabled="disabled"></el-input>
                </el-form-item>
              </el-col>
              <!--短信 极光 阿里 安吉 模板--->
              <div v-if="modalTemplateType=='sms'" class="" style="background:#f8f8f8;width:100%;float:left">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="">
                    <el-checkbox-group v-model="form1.signName" @change="signNameData" style="width: 100%;text-align:left">
                      <el-checkbox v-for="(item,index) in signNameList" :label="item.value" :key="index" :disabled="disabled"><b>{{item.label}}</b></el-checkbox>
                    </el-checkbox-group>
                  </el-form-item>
                </el-col>
                <div v-for="(item,i) in form1.signName" :key="i">
                  <el-col :xs="22" :sm="12" :md="12" :lg="12" :xl="12" v-if="item==1">
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="上汽安吉签名" prop="ajSignName">
                        <el-input id="ajSignName" v-model="form1.ajSignName" :disabled="disabled"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover
                        placement="right"
                        width="120"
                        trigger="hover">
                        * 例：上汽安吉 *
                        <i class="el-icon-question" style="color: #8c939d;" slot="reference"></i>
                      </el-popover>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="上汽安吉短信模板" >
                        <el-input type="textarea" :rows="7" v-model="form.templateShow" placeholder="您正在找回密码，验证码：${code}，15分钟内有效，请勿泄露！"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-col>
                  <el-col :xs="22" :sm="12" :md="12" :lg="12" :xl="12" v-if="item==2">
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="阿里签名" prop="aliSignName">
                        <el-input id="aliSignName" v-model="form1.aliSignName" :disabled="disabled"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover
                        placement="right"
                        width="260"
                        trigger="hover">
                        请前往阿里云获取签名<br/> <a href="https://dysms.console.aliyun.com/dysms.htm#/domestic/text/sign" target="_blank"><span style="color: #3E76D4">https://dysms.console.aliyun.com/dysms.htm#/domestic/text/sign</span></a>
                        <i class="el-icon-question" style="color: #8c939d;" slot="reference"></i>
                      </el-popover>
                    </el-col>
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="阿里模板代码" prop="aliTemplateCode">
                        <el-input id="aliTemplateCode" v-model="form1.aliTemplateCode" :disabled="disabled"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover
                        placement="right"
                        width="260"
                        trigger="hover">
                        请前往阿里云获取模板code<br/> <a href="https://dysms.console.aliyun.com/dysms.htm#/domestic/text/template" target="_blank"><span style="color: #3E76D4">https://dysms.console.aliyun.com/dysms.htm#/domestic/text/template</span></a>
                        <i class="el-icon-question" style="color: #8c939d;" slot="reference"></i>
                      </el-popover>
                    </el-col>
                  </el-col>
                  <el-col :xs="22" :sm="12" :md="12" :lg="12" :xl="12" v-if="item==3">
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="极光签名Id" prop="jgSignId">
                        <el-input id="jgSignId" v-model="form1.jgSignId" :disabled="disabled"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover
                        placement="right"
                        width="180"
                        trigger="hover">
                        请前往极光官网获取签名id<br/> <a href="https://www.jiguang.cn" target="_blank"><span style="color: #3E76D4">https://www.jiguang.cn</span></a>
                        <i class="el-icon-question" style="color: #8c939d;" slot="reference"></i>
                      </el-popover>
                    </el-col>
                    <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22">
                      <el-form-item label="极光模板Id" prop="jgTemplateId">
                        <el-input id="jgTemplateId" v-model="form1.jgTemplateId" :disabled="disabled"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="2" :md="2" :lg="2" :xl="2">
                      <el-popover
                        placement="right"
                        width="180"
                        trigger="hover">
                        请前往极光官网获取模板id<br/> <a href="https://www.jiguang.cn" target="_blank"><span style="color: #3E76D4">https://www.jiguang.cn</span></a>
                        <i class="el-icon-question" style="color: #8c939d;" slot="reference"></i>
                      </el-popover>
                    </el-col>
                  </el-col>
                </div>
              </div>
              <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22" v-if="modalTemplateType=='mail'">
                <el-form-item label="模板内容">
                  <htmlEdit v-model="form1.templateShow" ref="code" style="text-align: left"></htmlEdit>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="22" :md="22" :lg="22" :xl="22" v-if="modalTemplateType=='dingtalk'">
                <el-form-item label="模板内容">
                  <htmlEdit v-model="form1.templateShow" ref="code" style="text-align: left"></htmlEdit>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <div class="grid-content">
                  <el-button @click="close" type="info" class="c-button">取消</el-button>
                  <el-button @click="previewTemplate(form1.templateShow)" v-if="modalTemplateType!='sms'" type="success" class="c-button ml20">预览</el-button>
                  <el-button v-if="isFind" @click="saveBtn(proData)" type="primary" class="c-button ml20">保存</el-button>
                </div>
              </el-col>
            </el-row>
          </el-form>
          <el-dialog width="60%" title="模板预览" :visible.sync="innerVisible" append-to-body :close-on-click-modal="false" :lock-scroll="true">
            <el-row :gutter="20" class="test">
              <el-col :xs="20" :sm="14" :md="14" :lg="14" :xl="14">
                <div class="preview"><div v-html="previewContent" class="previewContent"></div></div>
              </el-col> 
              <el-col :xs="20" :sm="10" :md="10" :lg="10" :xl="10" v-if="proData!=null">
                <el-form ref="form" label-position="right" :model="form2" :rules="rules2" label-width="100px">
                  <el-row :gutter="20">
                    <el-col :span="24" v-if="modalTemplateType=='mail'">
                      <el-form-item label="标题" prop="subject">
                        <el-input id="title" v-model="form2.subject" placeholder="邮件测试"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="收件人" prop="email" v-if="modalTemplateType=='mail'">
                        <el-input id="email" v-model="form2.to" placeholder="123@qq.com"></el-input>
                      </el-form-item>
                      <el-form-item label="收件人" prop="email" v-else>
                        <el-input id="phone" v-model="form2.to" placeholder="18588888888"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="自定义参数" prop="paramMap">
                        <el-input id="paramMap" type="textarea" :autosize="{ minRows: 12, maxRows: 14}" v-model="form2.paramMap" placeholder="格式：{'user':'userName'}"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-button @click="testMail(form2)" type="success" class="c-button" :disabled="testMailDisabled">发送测试</el-button>
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
  import {preview,saveTemplate,updateTemplate,showTemplateById,testSendMail} from "@/api/push/notify"
  import htmlEdit from "@/views/push/htmlEdit";
  import CodeSelect from '@/components/codeSelect'
import { parse } from 'path-to-regexp';
  export default {
    name: "ModalMailDemoDeploy",
    props: {
      proData: Object,
      templateDeployType:String,
      isFind: Boolean
    },
    data () {
      return {
        form1: {
          signName:[],
          templateShow:"",
          template:"",
          newTemplateType:"",
          templateCode:"",
          templateId:"",
          aliSignName:"",
          aliTemplateCode:"",
          jgSignId:"",
          jgTemplateId:"",
          ajSignName:"",
        },
        signNameList:[
          {value: 1, label: "上汽安吉签名"},
          {value: 2, label: "阿里签名"},
          {value: 3, label: "极光签名Id"},
        ],
        signNameSelected:null,
        form2: {
          to:"",
          paramMap:"{'user':'userName'}"
        },
        modalTemplateType:this.templateDeployType,
        innerVisible:false,   //邮件模板内层预览弹框
        previewContent:{},          //预览返回的信息
        rules: {
          newTemplateType:[{
            required: true,
            message: "模板名称不能为空",
            trigger: "change"
          }],
          templateName:[{
            required: true,
            message: "模板名称不能为空",
            trigger: "blur"
          }],
          templateCode:[{
            required: true,
            message: "模板Code不能为空",
            trigger: "blur"
          }],
          template:[{
            required: true,
            message: "短信内容不能为空",
            trigger: "blur"
          }],
        },
        rules2: {
          to:[
            { required: true, message: '收件人不能为空', trigger: 'blur' },
          ],
          paramMap:[{
            required: true,
            message: "格式为对象形式",
            trigger: "blur"
          }]
        },
        testMailDisabled:false,
        pushType:1,
        timer: '',//定时loading
        disabled:false,
        checkedCities: ['上海', '北京'],
      };
    },
    components: {
      htmlEdit,
      CodeSelect
    },
    watch:{
      proData(val){
        this.modalTemplateType=null;
        this.mailDemoInformation(val)
      }
    },
    mounted() {
      this.mailDemoInformation(this.proData);
      this.$nextTick(() =>{
        this.$refs['form1'].clearValidate()
      })
    },
    methods: {
      getTemplateType(val){
        let value=parseFloat(val.value)
        this.modalTemplateType=this.getDictCode('ALERT_CHANNEL' ,value,"value").labelEng;
        this.form1.newTemplateType=this.getDictCode('ALERT_CHANNEL' ,value).value;
        this.mailDemoInformation2()
      },
      mailDemoInformation2(){
        if(this.modalTemplateType=='sms'){
          this.form1.templateShow = '您${name}正在找回密码，验证码：${code}，15分钟内有效，请勿泄露！'
        }else if(this.modalTemplateType=='dingtalk'){
          this.form1.templateShow = '### 告警时间：{sendTime}\n### 告警名称：{alertName}\n### 告警等级：{alertlevel}\n### 告警项目：{projectName}\n### 告警信息\n{listbegin : alertInfoList : alertInfo }\n#### 时间: {alertInfo.alertTime}\n#### 维度: {alertInfo.alertInfoDmensions}\n#### 指标: {alertInfo.alertInfo}\n{ listend }\n\n本邮件由推送系统发送，请勿回复'
        }else{
          this.form1.templateShow = '<h4>亲爱的{user}，你好！</h4></br>\n</br>\n</br>\n \{使用关键字大括号\}</br> \n {test} </br>\n\n </br> \n\n\n 云邮件系统</br>'
        }
        this.$nextTick(()=>{
          this.$refs.code && this.$refs.code.setContent(this.form1.templateShow);
        })
      },
      mailDemoInformation(val){
        console.log(this.proData)
        if(val!=null){
          this.disabled=true;
          this.form1.newTemplateType=val.templateType;
          this.modalTemplateType=val.templateType;
          this.form1.templateId=val.templateId;
          if(val!==undefined){
            this.proData=val;
          };
          let params={
            templateType:this.form1.newTemplateType,
            templateId:this.proData.templateId
          };
          showTemplateById(params).then(res=>{
            if (res.repCode == "0000") {
              this.form1 = res.repData;
              this.form1.newTemplateType=this.getDictCode('ALERT_CHANNEL' ,res.repData.templateType,"labelEng").value;
              this.modalTemplateType=res.repData.templateType;
              if(this.modalTemplateType=='sms'){
                this.form1.templateShow= res.repData.template;
                let smsTemplateAccount= res.repData.smsTemplateAccount
                this.form1.aliTemplateCode= smsTemplateAccount.aliTemplateCode;
                this.form1.jgSignId= smsTemplateAccount.jgSignId;
                this.form1.jgTemplateId= smsTemplateAccount.jgTemplateId;
                this.form1.aliSignName= smsTemplateAccount.aliSignName;
                this.form1.ajSignName= smsTemplateAccount.ajSignName;
                if(smsTemplateAccount.signNameSelected==null){
                  this.form1.signName=[]
                }else{
                  this.form1.signName =JSON.parse(smsTemplateAccount.signNameSelected)
                }
              }else{
                this.form1.templateShow= res.repData.templateShow;
                this.$nextTick(()=>{
                this.$refs.code.setContent(this.form1.templateShow);
              })
              }
            }
          }).catch(error => {})
        }else {
          this.disabled=false;
        }
      },
      signNameData(val){
        this.signNameSelected=val;
      },
      saveBtn (val) {
        this.$refs['form1'].validate((valid) => {
          if (valid) {
            let params = {
              templateId:this.form1.templateId,
              templateShow:this.form1.templateShow,
              templateCode:this.form1.templateCode,
              templateName:this.form1.templateName,
              templateType:this.modalTemplateType,
              smsTemplateAccount:{
                sendOrder:null,
                jgSignId:Number(this.form1.jgSignId),
                jgTemplateId:Number(this.form1.jgTemplateId),
                aliTemplateCode:this.form1.aliTemplateCode,
                aliSignName:this.form1.aliSignName,
                ajSignName:this.form1.ajSignName,
                signNameSelected:JSON.stringify(this.signNameSelected)
              }
            };
            if (this.proData!=null) {
              updateTemplate(params).then(res => {
                if (res.repCode == "0000") {
                  this.$message({
                    message:  '编辑模板成功',
                    type:     'success'
                  });
                  this.close()
                }
              });
            } else {
              saveTemplate(params).then(res=>{
                if(res.repCode=="0000"){
                  this.$message({
                    message:  '新增模板成功',
                    type:     'success'
                  });
                  this.close()
                }
              });
            }
          } else {
            return false;
          }
        });
      },
      // 关闭模态框事件
      close () {
        this.$emit("closeModal");
      },
      destroyTimer () {
        if (this.timer) {
          clearInterval(this.timer);
        }
      },
      //预览模板
      previewTemplate(val){
        this.innerVisible = true;
        let params={};
        if(this.proData==null){
          this.form2={
            to:'',
            paramMap:"{'user':'test'}"
          };
          params ={
            "templateShow":val,
            "templateType":this.modalTemplateType
          }
        }else {
          this.form2={
            to:'',
            paramMap:this.proData.templateParam
          }
          params ={
            "templateShow":val,
            "templateType":this.modalTemplateType,
            "templateId":this.proData.templateId
          }
        }
        preview(params).then(res=>{
          if(res.repCode=="0000"){
            this.form2.paramMap=JSON.stringify(res.repData.paramMap)
            this.previewContent = res.repData.html
          }
        })
      },
      //发送测试
      testMail(val){
        if(val.to===''){
          this.$message({
            message:  '收件人不能为空',
            type:     'error'
          });
        }else {
          if(this.modalTemplateType=='mail'){
            this.pushType=1
          }else if(this.modalTemplateType=='dingtalk'){
            this.pushType=2
          }else{
            this.pushType=3
          }
          let params = {
            pushType:this.pushType,
            templateCode:this.form1.templateCode,
            to:this.form2.to,
            subject:this.form2.subject,
            from:"test",
            copy:"",
            paramMap:JSON.parse(this.form2.paramMap),
          };
          this.testMailDisabled=true;
          testSendMail(params).then(res=>{
            if(res.repCode=="0000"){
              this.$message({
                message:  '发送成功，请注意查收',
                type:     'success'
              });
              this.innerVisible=false;
            }else {
              this.testMailDisabled=false
              this.innerVisible=false;
            }
          })
        }
      }
    }
  };
</script>
<style scoped lang="scss">
  .preview{
    background: #f8f8f8;
    padding: 10px;
  }
  .test{
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
  .previewContent{
    line-height: 36px;
  }
</style>
