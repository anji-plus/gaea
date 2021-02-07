<template>
  <div class="app-container">
    <el-form ref="searchForm" :model="searchForm" label-width="150px" :rules="rules">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('userManage.oldPsw')" prop="oldPassword">
            <el-input v-model="searchForm.oldPassword" :placeholder="$t('userManage.oldPlace')" show-password />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('userManage.newPsw')" prop="newPassword1">
            <el-input v-model="searchForm.newPassword1" :placeholder="$t('userManage.newPlace')" show-password />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('userManage.confirmPsw')" prop="newPassword2">
            <el-input v-model="searchForm.newPassword2" :placeholder="$t('userManage.confirmPlace')" show-password />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row style="margin: 30px 0 0 120px">
        <el-button type="primary" @click="submitPassword('searchForm')">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="clear()">{{ $t('btn.cancel') }}</el-button>
      </el-row>
    </el-form>
  </div>
</template>
<script>
// import Cookies from 'js-cookie'
import { transPsw } from '@/utils/encrypted'
import { changePassword } from '@/api/system-set'
export default {
  data() {
    return {
      searchForm: {
        newPassword1: '',
        oldPassword: '',
        newPassword2: '',
        // pkId: Cookies.get('pkId'),
      },
      rules: {
        oldPassword: [{ required: true, message: this.$t('userManage.oldPlace'), trigger: 'blur' }],
        newPassword1: [
          { required: true, message: this.$t('placeholder.input'), trigger: 'blur' },
          // { pattern: /^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{8,30}$/, message: this.$t('userManage.newPlace') },
        ],
        newPassword2: [{ required: true, message: this.$t('userManage.confirmPlace'), trigger: 'blur' }],
      },
      firstLogin: this.$route.params.firstLogin,
    }
  },
  methods: {
    submitPassword(dialogForm) {
      this.$refs[dialogForm].validate((valid) => {
        if (valid) {
          // 密码输入不一致
          if (this.searchForm.newPassword1 !== this.searchForm.newPassword2) {
            this.$message.error(this.$t('userManage.passwordNotEq'))
            return
          }
          var passwordObj = {
            password: transPsw(this.searchForm.newPassword1),
            oldPassword: transPsw(this.searchForm.oldPassword),
            confirmPassword: transPsw(this.searchForm.newPassword2),
            // pkId: Cookies.get('pkId'),
          }
          // 发送请求
          changePassword(passwordObj).then((res) => {
            if (res.code != '2000') return
            // 成功后跳出提示，重新登录
            this.$alert(this.$t('userManage.changeSuccess'), this.$t('promptMessage.deleteTipTitle'), {
              confirmButtonText: this.$t('btn.confirm'),
              callback: (action) => {
                this.logout()
              },
            })
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    clear() {
      if (this.firstLogin) {
        this.$router.push({ path: '/' })
      } else {
        this.$router.back()
      }
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/`)
    },
  },
}
</script>
<style lang="scss" scoped>
.el-col {
  height: 60px;
}
</style>
