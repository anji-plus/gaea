<template>
  <div class="components-container">
    <div class="pd-main content-border">
      <el-row type="flex" justify="center" class="pt20">
        <el-col :xs="24" :sm="23" :md="23" :lg="23" :xl="23">
          <el-form ref="form" label-position="right" :model="form" :rules="rules" label-width="120px">
            <el-row :gutter="20" class="labelName">
              <el-col :xs="20" :sm="20" :md="20" :lg="20" :xl="20">
                <el-form-item label="手机号码" prop="TestNumber">
                  <el-input id="TestNumber" v-model="form.TestNumber" placeholder="请输入接收短信的测试号码"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="20" :sm="20" :md="20" :lg="20" :xl="20">
                <el-form-item label="模板代码" prop="TestTemplate">
                    <el-input :disabled="true" id="TestTemplate" v-model="form.templateCode"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="20" :sm="20" :md="20" :lg="20" :xl="20">
                <el-form-item label="模板内容">
                  <el-form-item v-model="form.template">
                    <el-input :disabled="true" v-model="form.template"></el-input>
                  </el-form-item>
                </el-form-item>
              </el-col>
              <el-col :xs="20" :sm="20" :md="20" :lg="20" :xl="20">
                <el-form-item label="模板参数" prop="templateParam">
                  <el-input id="templateParam" placeholder="请输入内容" v-model="form.templateParam">
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <div class="grid-content">
                  <el-button @click="close" type="info" class="c-button">取消</el-button>
                  <el-button @click="testMail" type="primary" class="c-button ml20">发送</el-button>
                </div>
              </el-col>
            </el-row>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
  import {testSendMail} from "@/api/push/notify"
  export default {
    name: "ModalSMSDemoDeploy",
    props: {
      proTestSmsData: Object,
      templateDeployType:String
    },
    data () {
      return {
        form: {
          TestNumber:"",
          templateCode:"",
          templateParam:'',
        },
        rules: {
          TestNumber:[ {
            required: true,
            message: "电话号码不能为空",
            trigger: "blur"
          }, { validator: this.validatePhone, trigger: "blur" }
            ],
          templateParam:[{
            required: true,
            message: "测试内容不能为空",
            trigger: "blur"
          }]
        },
        templateSelect:[]
      };
    },
    components: {
    },
    watch:{
      proTestSmsData(val){
          this.form=val;
      }
    },
    mounted() {
      this.form=this.proTestSmsData
    },
    methods: {
      // 关闭模态框事件
      close () {
        this.$emit("closeModal");
      },
      selectTemplateId(val){
      },
      //发送测试
      testMail(){
        let params = {
          paramMap:JSON.parse(this.form.templateParam),
          to:this.form.TestNumber,
          templateCode:this.form.templateCode,
          templateId:this.form.templateId,
          from:"test",
          copy:"",
          pushType:3,
        };
        testSendMail(params).then(res=>{
          if(res.repCode=="0000"){
            this.$message({
              message:  '短信发送成功，请注意查收',
              type:     'success'
            });
            this.close();
          }
        })
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
