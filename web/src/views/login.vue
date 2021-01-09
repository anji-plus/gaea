<template>
  <div class="login-container">
    <div class="login-bg">
      <div class="login-ocean"></div>
      <div class="login-crane"></div>
      <div class="login-ship"></div>
    </div>
    <div class="login-box">
      <img src="../../static/htlogo.png" width="60"><img src="../../static/htname.png" width="400">
      <div class="login-list">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
          <h3 class="title"><img src="../../static/logo.png" width="200"></h3>
          <el-form-item prop="loginName" class="margin-top">
            <el-input v-model="loginForm.loginName" name="loginName" type="text" prefix-icon="el-icon-user" auto-complete="on" placeholder="用户名">
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input :type="pwdType" v-model="loginForm.password" name="password" prefix-icon="el-icon-lock" auto-complete="on" placeholder="密码" @keyup.enter.native="checkParam">
            </el-input>
          </el-form-item>
          <el-form-item label="" prop="Remember" class="Remember">
            <el-checkbox v-model="loginForm.Remember" label="记住密码"></el-checkbox>
            <!-- <a @click="goForget" class="float-r">找回密码</a> -->
            <a class="float-r" @click="goHelpCenter">帮助中心</a>
          </el-form-item>
          <el-form-item class="btn_login">
            <el-button :loading="loading" type="primary" style="width:100%;" @click.native.prevent="checkParam">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <Verify
      @success="handleLogin"
      :captchaType="'blockPuzzle'"
      :imgSize="{width:'400px',height:'200px'}"
      ref="verify"></Verify>
  </div>
</template>

<script>
import { aesEncrypt } from '@/utils/aes'
import { login } from "@/api/login";
import { setItem, getItem } from '@/utils/storage';
import { queryForCodeSelect } from "@/api/system/dict";
import Verify from './../components/verifition/Verify'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!/^[A-Za-z0-9]+$/.test(value)) {
        callback(new Error('用户名只能是字母数字'))
      } else {
        callback()
      }
    }
    const validatePass = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('密码不能小于5位'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        loginName: '',
        password: '',
        Remember:false
      },
      loginRules: {
        loginName: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined
    }
  },
  components:{
    Verify
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      queryForCodeSelect().then(res => {
        if(res.repCode === '0000') {
          localStorage.setItem('helpCategory',JSON.stringify(res.repData.HELP_CATEGORY));
        }
      })
    },
    checkParam() {
      // 如果参数合格就调用验证码
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.$refs.verify.show();
          // this.handleLogin()
        } else {
          return false
        }
      })
    },
    goForget(){
      this.$router.push("/forget");
    },
    handleLogin(params) {
      let data = {
        loginName: this.loginForm.loginName,
        password: aesEncrypt(this.loginForm.password)
      }
      login(Object.assign(data,params)).then(res => {
        // 缓存设置
        if (res.repCode == "0000") {
          var repData = res.repData;
          setItem("token", repData.token)
          localStorage.setItem("user", JSON.stringify(repData))
          setItem("userName", repData.loginName)
          queryForCodeSelect().then(res1 => {
            if (res1.repCode == '0000') {
              localStorage.setItem('queryForCodeSelect',JSON.stringify(res1.repData));
              // this.$router.push("/index");
              if(this.hasPermission('overviewManage:find')) {
                this.$router.push("/log/logview/summary");
              } else {
                this.$router.push("/index");
              }
            }
          }).catch(error => {
            console.log(error, 'error');      
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },
    goHelpCenter() {
      let helpCategory = JSON.parse(localStorage.getItem('helpCategory'))
      this.$router.push({
        path: '/helpCenList/list',
        query: {
          id: 0,
          val: helpCategory[0].value,
          title: helpCategory[0].label
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
.margin-top{
  margin-top: 50px;
}
.login-bg{
  position:absolute;
  z-index: 1;
  background: url("../../static/bg_01.jpg") center top no-repeat;
  background-size: 100% 100%;
  width: 100vw;
  height: 100vh;
}
.login-crane{
  position:absolute;
  z-index: 3;
  background: url("../../static/Crane.png") center top no-repeat;
  background-size: 100% 100%;
  width: 40vw;
  min-width: 550px;
  max-width: 770px;
  height: 100%;
  bottom: 0;
}
.login-ship{
  position:absolute;
  z-index: 4;
  background: url("../../static/ship.png") center top no-repeat;
  background-size: 100% 100%;
  width: 250px;
  height: 190px;
  bottom:10vh;
  left: 33.6666vw;
  animation: myMove 3s infinite;
  -webkit-animation: myMove 3s infinite;
}
.login-ocean{
  position:absolute;
  z-index: 2;
  background: url("../../static/ocean.png") center top no-repeat;
  background-size: 100% 100%;
  width: 100vw;
  height: 80vh;
  bottom: 0;
  animation: water-right 5s infinite linear;
}
.login-box{
  position:absolute;
  z-index: 100;
  width: 500px;
  height: 500px;
  top: 17%;
  right: 9%;
}
.login-list{
  width: 100%;
  height: 100%;
  background: url("../../static/login_box.png") center top no-repeat;
  background-size: 100% 100%;
}
@keyframes water-right{
  0% {
    transform: translateX(0) translateZ(0) scaleY(1.15)
  }
  25% {
    transform: translateX(-1) translateZ(0) scaleY(1)
  }
  50% {
    transform: translateX(0) translateZ(0) scaleY(1.1)
  }
  75% {
    transform: translateX(-1) translateZ(0) scaleY(1)
  }
  100% {
    transform: translateX(0) translateZ(0) scaleY(1.15)
  }
}
@keyframes myMove {
    0% {
        bottom:10vh;
    }

    50% {
        bottom:9.5vh;
    }

    100% {
        bottom:10vh;
    }
}

@-webkit-keyframes myMove {
    0% {
        bottom:10vh;
    }

    50% {
        bottom:9.5vh;
    }

    100% {
        bottom:10vh;
    }
}
/* reset element-ui css */
.login-container {
  .Remember{
    color: #fff;
    a {
      line-height: 16px;
      }
    .el-checkbox{
      color: #fff;
    }
  }
  .el-input__inner{
    color: #fff;
  }
  .el-input__inner[DangerColor="danger"] {
    background-color: #F56C6C;    //红色
  }
 
  .el-input__inner[WarningColor="warning"] {
    background-color: #E6A23C;    //橙色
  }
 
  .el-input__inner[SuccessColor="success"] {
    background-color: #67C23A;    //绿色
  }
  .el-input {
    display: inline-block;
    height: 40px;
    color: #fff;
    input:-internal-autofill-previewed,
      input:-internal-autofill-selected {
      -webkit-text-fill-color: #FFF !important;
       transition: background-color 5000s ease-in-out 0s !important;
    }
    input {
      background:#0e316e;
      border: 1px solid #6C839C;
      padding-left: 40px;
    }
  }
  .btn_login{
    .el-button--primary{
      background: linear-gradient(180deg, #0BA6FA 0%, #1D73BF 100%);
      border:none;
      margin-top: 40px;
    }
  }
} 

.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    max-width: 100%;
    padding: 30px 16% 15px 16%;
    margin: 45px auto;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .el-form-item__content{
    line-height: 0;
  }
  .svg-container {
    color: $dark_gray;
    vertical-align: middle;
    width: 0;
    display: inline-block;
  }
  .title {
    font-size: 26px;
    font-weight: 400;
    color: $light_gray;
    margin: 0px auto 40px auto;
    text-align: center;
    font-weight: bold;
  } 
}
</style>
