<template>
<div>
  <el-menu class="navbar" mode="horizontal">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <breadcrumb />
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper">
        <!-- <img src="@/assets/images/user/appro2.png" class="user-avatar"> -->
        <i class="icon iconfont iconyonghu user"/>
        <span class="user-name">{{userName}}</span>
        <i class="el-icon-caret-bottom"/>
      </div>
      <el-dropdown-menu slot="dropdown" class="user-dropdown">
        <!-- <router-link class="inlineBlock" to="/">
          <el-dropdown-item>
            首页
          </el-dropdown-item>
        </router-link> -->
        <el-dropdown-item divided v-if="hasPermission('helpManage:find')">
          <span style="display:block;" @click="helpCenter">帮助中心</span>
        </el-dropdown-item>
        <el-dropdown-item divided  v-if="hasPermission('userManage:reset')">
          <span style="display:block;" @click="updatePassword">修改密码</span>
        </el-dropdown-item>
        <el-dropdown-item divided>
          <span style="display:block;" @click="logout">注销登录</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-menu>
  <!-- 修改密码弹框 -->
  <el-dialog
    title="修改密码"
    :visible.sync="wordVisible"
    width="40%"
    :close-on-click-modal='false'
    top="20vh"
    class="password-box"
    >
    <el-form ref="form" :model="form" label-width="100px" :rules="rules" :close-on-click-modal="false">
      <el-form-item label="原密码" prop="oldPassword">
        <el-input v-model.trim="form.oldPassword" type="password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model.trim="form.password" type="password" autocomplete="off"></el-input>
        <!-- <span class="password-tips"><i class="el-icon-warning-outline"> 密码至少8位,切包含大写、小写字母、数字、特殊字符中的3种</i></span> -->
      </el-form-item>
      <el-form-item label="确认新密码" prop="confirmPassword">
        <el-input v-model.trim="form.confirmPassword" type="password" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>  
    <span slot="footer" class="dialog-footer">
      <el-button @click="wordVisible = false">取 消</el-button>
      <el-button type="primary" @click="confrimUpdate">确 定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import {getItem,delItem, getStorageItem} from "@/utils/storage"
import { aesEncrypt } from '@/utils/aes'
import {reqUpdatePassword} from "@/api/login"

export default {
  data() {
    // 确认密码
		var validatePass3 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    // const validatePass = (rule, value, callback) => {
		// 	if (!/^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{6,}$/.test(value)) {
		// 		callback(new Error('请按要求输入密码'))
		// 	} else {
		// 		callback()
		// 	}
    // };
    const validateOldPass = (rule, value, callback) => {
			if (value.length < 6 || value.length > 30) {
				callback(new Error('请输入原密码'))
			} else {
				callback()
			}
		};
    return {
      userName: '',
      wordVisible: false, //修改密码弹框
      form :{
        oldPassword:'',
        password:'',
        confirmPassword:''
      },
      rules:{
        oldPassword: [
          {required:true, validator: validateOldPass, trigger: 'blur' }
        ],
        password: [
          {required:true, message: '请选择新密码', trigger: 'blur' }
        ],
        confirmPassword: [
          {required:true, validator: validatePass3 , trigger: 'blur' }
        ]
      }
    }
  },
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ])
  },
  created() {
    this.userName = getStorageItem('user').realName
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      this.$confirm('确定要退出吗', '温馨提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // logout().then((res) => {
        //   delItem('userName')
        //   delItem('token')
        //   localStorage.clear()
        //   sessionStorage.clear()
        //   location.reload() // 为了重新实例化vue-router对象 避免bug
        //   this.$router.push('/login')
        // })
        delItem('userName')
        delItem('token')
        sessionStorage.clear()
        localStorage.clear()
        this.$router.push('/login')
      })
    },
    // 修改密码
    updatePassword(){
      this.wordVisible = true
      this.$nextTick(()=>{
        this.$refs.form && this.$refs.form.resetFields();
      })
    },
    // 发送请求 确认修改
    confrimUpdate(){  
      this.$refs.form.validate((valid) => {
        if (valid) {
          const {oldPassword,password,confirmPassword} = this.form
          let data = {
            oldPassword: aesEncrypt(oldPassword),
            password: aesEncrypt(password),
            confirmPassword: aesEncrypt(confirmPassword),
          }
          reqUpdatePassword(data).then(res=>{
            if (res.repCode == "0000") {
              this.wordVisible = false
              this.$message.success("修改密码成功,请重新登录")
              sessionStorage.clear()
              localStorage.clear()
              delItem('userName')
              delItem('token')
              this.$router.push('/login')
            }
          })
        } else {
          return false;
        }
      });
    },
    helpCenter() {
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

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  background: #3768C8 !important;
  .hamburger-container {
    line-height: 57px;
    height: 49px;
    float: left;
    padding: 0 10px;
    background: rgb(64, 158, 255);
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      line-height: 50px;
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
        vertical-align: text-bottom;
      }
      .user-name{
        color: #fff;
      }
      .el-icon-caret-bottom {
        color: #fff;
        position: absolute;
        right: -20px;
        top: 21px;
        font-size: 12px;
      }
      .user{
        color: #fff;
        font-size: 16px;
      }
    }
  }
}
.password-box{
  .password-tips{
    position: absolute;
    right: 0px;
    top: 100%;
    line-height: 1;
    font-size: 13px;
    padding-top: 4px;
  }
}
.el-popper{
  padding: 0;
}
.el-dropdown-menu__item--divided{
  margin-top: 0;
}
</style>

