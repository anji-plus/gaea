<template>
  <div class="app-container">
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="参数名称" prop="settingName">
            <el-input v-model="form.settingName" :disabled="updateModel" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数描述" prop="settingLabel">
            <el-input v-model="form.settingLabel" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="参数类型" prop="settingType">
            <!--<code-select v-model="form.settingType"  dictname="SETTING_TYPE" mystyle="width: 100%;" placeholder="请选择"/>-->
            <el-select v-model="form.settingType" :disabled="updateModel" :placeholder="$t('placeholder.select')">
              <el-option v-for="(item, i) in settingTYpeList" :key="i" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注">
            <el-input v-model="form.remark" />
          </el-form-item>
        </el-col>
      </el-row>
      <EditForm v-model="updateData" :form-value="form" />
      <!-- 新增 -->
      <div v-if="updateModel == false" class="parameter-add">
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.settingType == 'custom-form'" label="参数表单JSON" :rules="[{ required: true }]">
              <el-input v-model="addForm.settingForm" type="textarea" :placeholder="fromJsonPlacehodler" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.settingType == 'custom-form'" label="参数表单值JSON" :rules="[{ required: true }]">
              <el-input v-model="addForm.settingValue" type="textarea" :placeholder="fromValueJsonPlacehodler" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <EachForm v-model="formItemData" :item="formItem" @eachChange="eachChange" />
          </el-col>
        </el-row>
      </div>
      <div>
        <!-- 编辑 -->
        <template v-if="updateModel == true">
          <el-row>
            <el-col :span="12">
              <el-form-item label="创建人">
                <el-input :disabled="true" :value="form.createBy" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="创建时间">
                <el-input :disabled="true" :value="form.createTime" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="修改人">
                <el-input :disabled="true" :value="form.updateBy" /> </el-form-item></el-col>
            <el-col :span="12">
              <el-form-item label="修改时间">
                <el-input :disabled="true" :value="form.updateTime" /> </el-form-item></el-col>
          </el-row>
        </template>
      </div>
      <el-form-item style="width: 100%">
        <el-button v-if="!isFind" type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button v-else disabled type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button size="medium" @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// import { reqCreate, reqUpdate, queryById } from "@/api/system/setting";
// import CodeSelect from "@/components/codeSelect";
import EditForm from './editForm'
import EachForm from './eachForm'
import { authSetting, authSettingEdit, settingPageList } from '@/api/system-set'
// import {deepClone} from "@/utils"

export default {
  components: {
    // CodeSelect,
    EditForm,
    EachForm,
  },
  data() {
    return {
      settingTYpeList: [
        { extend: '', label: '字符串', labelEng: 'input', value: 'input' },
        { extend: '', label: '数字', labelEng: 'input-number', value: 'input-number' },
        { extend: '', label: '文本区域', labelEng: 'textarea', value: 'textarea' },
        { extend: '', label: '数据字典', labelEng: 'code-select', value: 'code-select' },
        { extend: '', label: '下拉列表', labelEng: 'select', value: 'select' },
        { extend: '', label: '单选按钮', labelEng: 'radio-group', value: 'radio-group' },
        { extend: '', label: '多选按钮', labelEng: 'checkbox-group', value: 'checkbox-group' },
        { extend: '', label: '自定义表单', labelEng: 'custom-form', value: 'custom-form' },
      ],
      form: {
        settingId: null,
        settingName: '',
        settingLabel: '',
        settingType: '',
        settingValue: '',
        settingForm: '',
        remark: '',
        createdBy: '',
        createdTime: null,
        updatedBy: '',
        updatedTime: null,
      },
      addForm: {
        settingValue: '',
        settingForm: '',
      },
      rules: {
        settingName: [{ required: true, message: '请输入参数名称', trigger: 'blur' }],
        settingLabel: [{ required: true, message: '请输入参数描述', trigger: 'blur' }],
        settingType: [{ required: true, message: '请输入参数类型', trigger: 'change' }],
        settingValue: [{ required: true, message: '请输入参数值', trigger: 'blur' }],
      },
      updateData: {},
      fromJsonPlacehodler: JSON.stringify(
        [
          {
            type: 'input',
            label: 'IP地址',
            name: 'ip',
            required: true,
            placeholder: '',
          },
          {
            type: 'input-number',
            label: '登录失败几次后锁定用户',
            name: 'lockAfterLoginFail',
            required: true,
            placeholder: '',
          },
          {
            type: 'textarea',
            label: '执行脚本',
            name: 'command',
            required: true,
            placeholder: '',
          },
          {
            type: 'code-select',
            dictname: 'ENABLE_FLAG',
            label: '是否启用',
            name: 'enableFlag',
            required: true,
            placeholder: '',
          },
          {
            type: 'select',
            label: '支付方式',
            name: 'payChannel',
            options: [
              {
                value: 'alipay',
                label: '支付宝',
              },
              {
                value: 'weixin',
                label: '微信支付',
              },
              {
                value: 'jdpay',
                label: '京东支付',
              },
              {
                value: 'qqpay',
                label: 'QQ钱包',
              },
              {
                value: 'unipay ',
                label: '银联支付',
              },
            ],
            required: true,
            placeholder: '',
          },
          {
            type: 'radio-group',
            options: ['上海', '北京', '广州', '深圳'],
            label: '活动地点',
            name: 'position',
            required: true,
            placeholder: '',
          },
          {
            type: 'checkbox-group',
            options: ['现金', '网银', '代付'],
            label: '支付方式',
            name: 'payType',
            required: true,
            placeholder: '',
          },
        ],
        null,
        '\t'
      ),
      fromValueJsonPlacehodler: JSON.stringify(
        {
          ip: '129.168.1.1',
          lockAfterLoginFail: 1,
          command: 'sh /app/startall.sh',
          dataCenter: 'shangqiyun',
          payChannel: 'weixin',
          position: '上海',
          payType: ['现金', '网银'],
        },
        null,
        '\t'
      ),
      isFind: true,
    }
  },
  computed: {
    updateModel() {
      return this.$route.query.id != null
    },
    formItem() {
      return {
        type: this.form.settingType,
        label: this.form.settingLabel,
        name: this.form.settingName,
        required: true,
        placeholder: '',
      }
    },
    formItemData() {
      return { [this.form.settingName]: this.form.settingValue }
    },
  },
  created() {
    var id = this.$route.query.id
    this.isFind = this.$route.query.val
    this.name = this.$route.query.name
    if (id != null) {
      this.form.settingId = id
      this.queryDetail()
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    eachChange(val) {
      console.log(val, 'gagda')
    },
    async queryByPage() {
      const that = this
      const res = await settingPageList({ pageNumber: 1, pageSize: 10, settingName: this.name })
      if (res.code != '200') return
      this.form = res.data.records[0]
      this.updateData = that.deepClone(this.form)

      console.log(111, this.form)
    },
    queryDetail() {
      this.queryByPage()
    },
    deepClone(source) {
      if (!source && typeof source !== 'object') {
        throw new Error('error arguments', 'shallowClone')
      }
      const targetObj = source.constructor === Array ? [] : {}
      Object.keys(source).forEach((keys) => {
        if (source[keys] && typeof source[keys] === 'object') {
          targetObj[keys] = this.deepClone(source[keys])
        } else {
          targetObj[keys] = source[keys]
        }
      })
      return targetObj
    },
    onSubmit() {
      const that = this
      this.$refs['form'].validate(async(valid) => {
        if (!valid) {
          return
        }
        if (this.$route.query.id) {
          // 为更新除表单之外的值
          this.updateData.settingLabel = this.form.settingLabel
          this.updateData.remark = this.form.remark
          const { code } = await authSettingEdit(this.updateData)
          if (code != '200') return
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 2 * 1000,
          })
          this.goBack()
          // reqUpdate(this.updateData).then(response => {
          //   if (response.repCode == "0000") {
          //     this.$message({
          //       message: "操作成功",
          //       type: "success",
          //       duration: 2 * 1000
          //     });
          //     this.goBack();
          //   }
          // });
        } else {
          if (this.form.settingType != 'custom-form') {
            console.log(1, this.formItemData)
            this.form.settingValue = this.formItemData[this.form.settingName]
          } else {
            this.form.settingValue = this.addForm.settingValue
            this.form.settingForm = this.addForm.settingForm
          }
          const data = that.deepClone(this.form)
          const { code } = await authSetting(data)
          if (code != '200') return
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 2 * 1000,
          })
          this.goBack()
          // reqCreate(data).then(response => {
          //   if (response.repCode == "0000") {
          //     this.$message({
          //       message: "操作成功",
          //       type: "success",
          //       duration: 2 * 1000
          //     });
          //     this.goBack();
          //   }
          // });
        }
      })
    },
  },
}
</script>

<style scoped lang="scss">
.line {
  text-align: center;
}
.parameter-add {
  /deep/.el-textarea .el-textarea__inner {
    min-height: 400px !important;
    max-height: 600px !important;
  }
}
</style>
