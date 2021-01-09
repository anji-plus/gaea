<template>
  <div class="">
    <div class="search-pop clearfix">
      <div class="grid-content">
        <el-row type="flex" justify="center">
          <el-col :xs="22" :sm="22" :md="22" :lg="22" :xl="24">
            <div class="grid-content pd-main pt20 pb20">
              <el-row class="row-bg" style="margin-top:50px">
                <el-col :offset="2" :xs="12" :sm="6" :md="6" :lg="6" :xl="6">
                  <label class="fl" style="line-height:40px">教程类型：</label><code-select v-model.trim="templateType" dictname="ALERT_CHANNEL" mystyle="width:60%;" placeholder="请选择"/>
                </el-col>
                <el-col :offset="2" :xs="20" :sm="20" :md="20" :lg="20" :xl="20">
                  <div class="grid-content">
                    <div class="right-box" v-if="templateType=='1'">
                      <div class="log-list word">
                        <header>云邮件使用教程</header>
                        <div class="mt30" style="width: 100%;float: left">
                          <div class="log-repeat">
                            <h2>1. 引用工具类 </h2>
                            <h4>MirrorMailUtil</h4>
                            <div class="js">
                              <p>
                                <a href="/static/MirrorMailUtil.java" download="MirrorMailUtil.java" class="download">下载mail工具类</a>
                              </p>
                            </div>
                            <h2>2. 配置模板 </h2>
                            <h4>模板Code保证唯一性</h4>
                            <div class="img"><img src="../../../assets/images/20190904132756.png" width="90%"></div>
                            <h2>3. 发送邮件实例代码 </h2>
                            <div class="js">
                            <p>
                              Map&lt;String, String&gt; mailParam=new HashMap&lt;String, String&gt;();<br>
                              mailParam.put("templateCode","testTemplateCode");  //模板Code<br>
                              mailParam.put("subject","test");  // 标题<br>
                              mailParam.put("to","156110452@qq.com");  //收件人，多用户用;或者,隔开<br>
                              mailParam.put("from","test");  //发件人别名<br>
                              mailParam.put("copy","156110452@qq.com"); //抄送，多用户用;或者,隔开<br>
                              <br>
                              <br>
                              //组装模板参数<br>
                              Map templateParam=new HashMap();  //模板参数<br>
                              templateParam.put("user","test1");<br>
                              templateParam.put("test","test2");<br>

                              //组装附件参数<br>
                              Map&lt;String, File&gt;  attachFiles=new HashMap&lt;String, File&gt;();<br>
                              attachFiles.put("git.txt",new File("d:\\git.txt"));<br>
                              try {<br>
                              String result = MailUtil.sendMail(mailParam, templateParam, attachFiles);<br>
                              System.out.println(result);<br>
                              } catch (Exception e){<br>
                              e.printStackTrace();<br>
                              }<br>

                            </p>
                            </div>
                            <h2>4. 收件效果 </h2>
                            <div class="img"><img src="../../../assets/images/20190904132242.png" width="547"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="right-box" v-if="templateType=='3'">
                      <div class="log-list word">
                        <header>短信转发使用教程</header>
                        <div class="mt30" style="width: 100%;float: left">
                          <div class="log-repeat">
                            <h2>1. 引用工具类 </h2>
                            <h4>MirrorSmsUtil</h4>
                            <div class="js">
                              <p>
                                <a href="/static/MirrorSmsUtil.java" download="MirrorSmsUtil.java" class="download">下载sms工具类</a>
                              </p>
                            </div>
                            <h2>2. 发送短信实例代码 </h2>
                            <div class="js">
                            <p>
                              Map&lt;String, String&gt; smsParam = new HashMap&lt;String, String&gt;();<br>
                              smsParam.put("mobiles", "17788889999");//手机号码，多号用,隔开<br>
                              smsParam.put("context", "你的验证码为:123456");//发送内容<br>
                              try {
                              String result = MirrorSmsUtil.sendSms(smsParam);<br>
                              System.out.println(result);<br>
                              } catch (Exception e) {<br>
                              e.printStackTrace();<br>
                              }<br>

                            </p>
                            </div>
                            <h2>3. 手机接收成功 </h2>
                            <div class="img" style="width: 40%"><img src="../../../assets/images/短信模板展示.jpg"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="right-box" v-if="templateType=='2'">
                      <div class="log-list word">
                        <header>钉钉使用教程</header>
                        <div class="mt30" style="width: 100%;float: left">
                          <div class="log-repeat">
                            <h2>暂无 </h2>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
  import htmlEdit from "@/views/push/htmlEdit";
  import CodeSelect from '@/components/codeSelect'
export default {
  name: "Tutorial",
  data () {
    return {
      closemodal: "close",
      template1Show:'',
      template2Show:'',
      template3Show:'',
      templateType:''
    };
  },
  components: {
    htmlEdit,
    CodeSelect
  },
  mounted() {
  },
  methods: {
    fanhui(){
      this.$router.push("/MailDeploy");
    },
    // 关闭模态框事件
    close () {
      this.$emit("closeModal");
    }
  }
};
</script>
<style scoped lang="scss">
  .download{
    color: dodgerblue;
    font-size: 14px;
  }
  .nav-menu {
    width: 100%;
    background: #203160;
    position: fixed;
    z-index: 10000;
    top:0;
    .logo {
      margin-top: 14px;
      width: 85px;
    }
  }
  .userBox {
    line-height: 64px;
    color: rgba(255, 255, 255, 0.4);
    font-size: 12px;
    cursor: pointer;
    .goOut {
      padding: 10px 18px;

      &:hover {
        color: #03afff;
      }
    }
  }
  .el-collapse{
    position: relative;
  }
  .el-button {
    font-size: 12px;
  }
  .project-lid {
    background: #9199b1;
    height: 60px;
    padding: 0 20px;
    color: #fff;
    line-height: 60px;
    font-size: 16px;
    overflow: hidden;
    label {
      color: #fff;
    }
  }
  .project-list {
    line-height: 42px;
    font-size: 14px;
    color: #333;
    width: 100%;
    height: calc(100vh - 110px);
    height: -moz-calc(100vh - 110px);
    height: -webkit-calc(100vh - 110px);
    height: calc(100vh - 110px);
    overflow-y: auto;
    overflow-x: hidden;
  }
  .log-list {
    min-height: calc(100vh - 150px);
    min-height: -moz-calc(100vh - 150px);
    min-height: -webkit-calc(100vh - 150px);
    min-height: calc(100vh - 150px);
    max-height: calc(100vh - 150px);
    max-height: -moz-calc(100vh - 150px);
    max-height: -webkit-calc(100vh - 150px);
    max-height: calc(100vh - 150px);
    overflow-y: auto;
    overflow-x: hidden;
    &.word{
      header{
        font-weight: bold;
        line-height: 48px;
        
      }
       h2{
         background: #eee;
         line-height: 36px;
         text-indent: 1rem;
         margin-top: 20px;
       }
      h4{
        line-height: 36px;
        text-indent: 1rem;
        font-size: 12px;
        font-weight: bold;
      }
      .js{
        background: #f9f9f9;
        padding: 20px;
        p{
          font-size: 14px;
          line-height: 24px;
          word-break: break-word;
        }
      }
      .img{
        padding: 20px;
      }
    }
  }
  .log-repeat {
    .log-info {
      div {
        line-height: 20px;
        font-size: 13px;
        word-wrap:break-word;
        white-space:pre-wrap;
      }
    }
  }
  .el-collapse {
    border-width: 0;
  }
</style>
