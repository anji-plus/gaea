<template>
  <div class="app-container">
    <el-form ref="form" :rules="rules" :model="form" :inline="true" label-width="120px" label-position="top" size="small">
      <el-form-item label="登录名" prop="loginName">
        <el-input :disabled="disabled" v-model="form.loginName" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="真实姓名" prop="realName">
        <el-input v-model="form.realName" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="form.phone" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="用户邮箱">
        <el-input v-model="form.email" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="启用状态" prop="enableFlag">
        <code-select v-model="form.enableFlag" mystyle="width: 100%" dictname="ENABLE_FLAG" prefixicon="el-icon-d-caret"/>
      </el-form-item>
      <el-form-item/>
      <el-form-item label="密码" prop="password1" v-if="form.loginName != 'admin'">
        <el-input type="password" show-password v-model="form.password1" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="password2" v-if="form.loginName != 'admin'">
        <el-input type="password" show-password v-model="form.password2" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" prefix-icon="el-icon-info"/>
      </el-form-item>
      <el-form-item label="推荐人" prop="recommender">
        <el-input v-model="form.recommender" prefix-icon="el-icon-info"/>
      </el-form-item>
      <template v-if="updateModel==true">
        <el-form-item label="最后一次登陆时间">
          <el-input :disabled="true" v-model="form.lastLoginTime" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="最后一次登录IP">
          <el-input :disabled="true" v-model="form.lastLoginIp" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="创建人">
          <el-input :disabled="true" :value="form.createdBy" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input :disabled="true" :value="form.createdTime | formatTimestamp" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="修改人">
          <el-input :disabled="true" :value="form.updatedBy" prefix-icon="el-icon-info"/>
        </el-form-item>
        <el-form-item label="修改时间">
          <el-input :disabled="true" :value="form.updatedTime | formatTimestamp" prefix-icon="el-icon-info"/>
        </el-form-item>
      </template>
      <el-form-item style="width: 100%;">
        <el-button v-if="!isFind" type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button v-else disabled type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button size="medium" @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { create, queryById ,updateById} from '@/api/authority/user'
import CodeSelect from '@/components/codeSelect'
import { aesEncrypt } from '@/utils/aes'

export default {
  components: {
    CodeSelect
  },
  data() {var password1Validator = (rule, value, callback) => {
      if (this.updateModel) {
        callback()
      } else {
        if (this.isBlank(value)) {
          callback(new Error('请输入密码'))
        } else {
          if (value.length < 6 || value.length > 20) {
            callback(new Error('密码长度在 6 到 20 个字符'))
          } else {
            callback()
          }
        }
      }
    }
    var password2Validator = (rule, value, callback) => {
      if (this.updateModel) {
        if (!this.isBlank(this.form.password1)) {
          if (value == null || value === '') {
            callback(new Error('请输入确认密码'))
          } else if (value !== this.form.password1) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        }
        callback()
      } else {
        if (value == null || value === '') {
          callback(new Error('请输入确认密码'))
        } else {
          if (value.length < 6 || value.length > 20) {
            callback(new Error('长度在 6 到 20 个字符'))
          } else {
            if (value !== this.form.password1) {
              callback(new Error('两次输入密码不一致'))
            } else {
              callback()
            }
          }
        }
      }
    }
    return {
      form: {
        userId: null,
        loginName: null,
        password1: null,
        password2: null,
        password: null,
        confirmPassword: null,
        realName: null,
        phone: null,
        email: null,
        enableFlag: null,
        deleteFlag: null,
        remark: null,
        recommender: null,
        lastLoginTime: null,
        lastLoginIp: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
      },
      rules: {
        loginName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 4, max: 16, message: '长度在 4 到 16 个字符', trigger: 'blur' }
        ],
        realName:[
          { required: true, message: '请输入真实姓名', trigger: 'blur' },
        ],
        password1: [{ required: true, validator: password1Validator, trigger: 'blur' }],
        password2: [{ required: true, validator: password2Validator, trigger: 'blur' }],
        nikeName: [{ required: true, message: '请输入用户昵称', trigger: 'blur' }],
        enableFlag: [{ required: true, message: '请选择启用状态', trigger: 'change' }]
      },
      disabled:false,
      isFind: true
    }
  },
  computed: {
    updateModel() {
      return this.$route.query.id != null
    }
  },
  created() {
    var userId = this.$route.query.id
    this.isFind = this.$route.query.val
    if (userId != null) {
      this.disabled=true;
      this.form.userId = userId
      this.queryById(userId)
    }
  },
  methods: {
    queryById(userId) {
      queryById(userId).then(response => {
        if (response.repCode == '0000') {
          this.form = response.repData
        }
      })
    },
    onSubmit() {
      this.$refs['form'].validate((valid) => {
        if (!valid) {
          return
        }
        this.form.password = this.form.password1?aesEncrypt(this.form.password1) : null
        this.form.confirmPassword = this.form.password2?aesEncrypt(this.form.password2): null
        let form = {
          userId: this.form.userId,
          loginName: this.form.loginName,
          password: this.form.password,
          confirmPassword: this.form.confirmPassword,
          realName: this.form.realName,
          phone: this.form.phone,
          email: this.form.email,
          enableFlag: this.form.enableFlag,
          deleteFlag: this.form.deleteFlag,
          remark: this.form.remark,
          recommender: this.form.recommender,
          lastLoginTime: this.form.lastLoginTime,
          lastLoginIp: this.form.lastLoginIp,
          createdBy: this.form.createdBy,
          createdTime: this.form.createdTime,
          updatedBy: this.form.updatedBy,
          updatedTime: this.form.updatedTime
        }
        if(this.$route.query.id !==null){
          form.userId = this.$route.query.id
          updateById(form).then(response => {
            if (response.repCode == '0000') {
              this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
              this.goBack()
            }
          })
        }else{ // 编辑
          
          create(form).then(response => {
            if (response.repCode == '0000') {
              this.$message({ message: '操作成功', type: 'success', duration: 2 * 1000 })
              this.goBack()
            }
          })
        }
        
      })
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

