<template>
  <div>
    <el-form ref="form" :rules="rules" :model="form" label-width="100px">
      <el-row class="form_table">
        <el-col :span="12">
          <el-form-item label="字典名称" prop="dictName">
            <el-input v-model.trim="form.dictName" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="字典描述" prop="dictDesc">
            <el-input v-model.trim="form.dictDesc" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="字典代码" prop="itemName">
            <el-input v-model.trim="form.itemName" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="代码值" prop="itemValue">
            <el-input v-model.trim="form.itemValue" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="代码描述" prop="itemDesc">
            <el-input v-model.trim="form.itemDesc" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="启用状态" prop="enabled">
            <el-select v-model="form.enabled" :placeholder="$t('placeholder.select')">
              <el-option key="1" label="启用" :value="1" />
              <el-option key="0" label="禁用" :value="0" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="语言" prop="locale">
            <el-select v-model="form.locale" :placeholder="$t('placeholder.select')">
              <el-option v-for="(item, i) in language" :key="i" :label="item.text" :value="item.id" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序" prop="sort" class="dataDictionary">
            <el-input-number v-model="form.sort" :min="1" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注">
            <el-input v-model="form.remark" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="扩展属性" prop="itemExtend">
            <el-input v-model="form.itemExtend" />
          </el-form-item>
        </el-col>
      </el-row>
      <!--编辑的时候才会显示-->
      <el-row v-if="clickType != 'add'">
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
        <el-col :span="12">
          <el-form-item label="修改人">
            <el-input :disabled="true" :value="form.updateBy" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改时间">
            <el-input :disabled="true" :value="form.updateTime" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div v-if="clickType != 'see'" slot="footer" style="text-align: center">
      <el-button type="primary" plain @click="onSubmit">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="cancel">{{ $t('btn.close') }}</el-button>
    </div>
  </div>
</template>

<script>
import { gaeaDictAdd, gaeaDictEdit } from '@/api/system-set'
export default {
  props: {
    clickType: {
      type: String,
      default: function() {
        return ''
      },
    },
    language: {
      type: Array,
      default: function() {
        return []
      },
    },
    form: {
      type: Object,
      default: function() {
        return {
          id: null,
          dictName: '',
          dictDesc: '',
          itemName: '',
          itemValue: '',
          itemDesc: '',
          itemExtend: '',
          enabled: null,
          locale: '',
          deleteFlag: null,
          sort: null,
          remark: '',
          createBy: '',
          createTime: null,
          updateBy: '',
          updateTime: null,
        }
      },
    },
  },
  data() {
    return {
      // form: {
      //   id: null,
      //   dictName: '',
      //   dictDesc: '',
      //   itemName: '',
      //   itemValue: '',
      //   itemDesc: '',
      //   itemExtend: '',
      //   enableFlag: null,
      //   deleteFlag: null,
      //   sort: null,
      //   remark: '',
      //   createdBy: '',
      //   createdTime: null,
      //   updatedBy: '',
      //   updatedTime: null
      // },
      rules: {
        dictName: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
        dictDesc: [{ required: true, message: '请输入字典描述', trigger: 'blur' }],
        itemName: [{ required: true, message: '请输入字典代码', trigger: 'blur' }],
        itemValue: [{ required: true, message: '请输入代码值', trigger: 'blur' }],
        itemDesc: [{ required: true, message: '请输入代码描述', trigger: 'blur' }],
        enabled: [{ required: true, message: '请选择启用状态', trigger: 'change' }],
        locale: [{ required: true, message: '请选择语言', trigger: 'change' }],
        sort: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { type: 'number', message: '排序必须为数字值' },
        ],
      },
      isFind: true,
    }
  },
  computed: {},
  created() {
    var id = this.$route.query.id
    this.isFind = this.$route.query.val
    if (id != null) {
      this.form.dictId = id
      this.queryDetail(id)
    }
  },
  methods: {
    cancel(formName) {
      this.$emit('cancel')
      // this.$refs[formName].resetFields();
    },
    queryDetail(id) {
      // queryById({ dictId: id }).then((response) => {
      //   if (response.repCode == '0000') {
      //     this.form = response.repData
      //   }
      // })
    },
    onSubmit() {
      this.$refs['form'].validate(async(valid) => {
        if (valid) {
          console.log('submit!')
          let res
          if (this.clickType == 'add') {
            res = await gaeaDictAdd(this.form)
          } else {
            res = await gaeaDictEdit(this.form)
          }
          if (res.code != '200') return
          this.$parent.$parent.saveSucess()
        } else {
          console.log('error submit!!')
          return false
        }
        // if (!valid) {
        //   return
        // }
        // if (this.updateModel) {
        //   reqUpdate(this.form).then(response => {
        //     if (response.repCode == '0000') {
        //       this.$message({ message: '更新成功', type: 'success', duration: 2 * 1000 })
        //       this.goBack()
        //     }
        //   })
        // }else {
        //   reqCreate(this.form).then(response => {
        //     if (response.repCode == '0000') {
        //       this.$message({ message: '新增成功', type: 'success', duration: 2 * 1000 })
        //       this.goBack()
        //     }
        //   })
        // }
      })
    },
  },
}
</script>

<style>
.dataDictionary .el-input-number--mini {
  width: 100%;
}
</style>
