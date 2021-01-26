<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchData" class="formStyle" label-width="110px" :rules="searchRules">
      <el-row>
        <el-col :span="19">
          <el-row class="form-table">
            <el-col :span="10">
              <el-form-item :label="$t('systemConf.bizType')" prop="bizType">
                <el-select v-model="searchData.bizType" filterable :placeholder="$t('placeholder.select')" clearable style="width: 100%">
                  <el-option v-for="item in dictionaryTypeList" :key="item" :label="item" :value="item" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" @click="queryData('formSearch')">{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="resetForm('formSearch')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-button type="primary" icon="el-icon-plus" @click="addOrEditDic('view', null)">{{ $t('btn.add') }}</el-button>
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="addOrEditDic('edit', selectedList[0])">{{ $t('btn.edit') }}</el-button>
    <el-button type="danger" icon="el-icon-delete" :disabled="selectedList.length != 1" @click="handleDel(selectedList[0])">{{ $t('btn.delete') }}</el-button>
    <el-table :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column prop="bizType" :label="$t('systemConf.bizType')" align="center" />
      <el-table-column prop="bizCode" :label="$t('systemConf.bizCode')" align="center" />
      <el-table-column prop="bizText" :label="$t('systemConf.bizText')" align="center" />
      <el-table-column prop="description" :label="$t('systemConf.description')" align="center" />
      <el-table-column prop="seq" :label="$t('systemConf.seq')" align="center" />
    </el-table>
    <!-- 新增和编辑的弹窗 -->
    <el-dialog :title="$t(`btn.${minDialogTitle}`)" :visible.sync="minDialogVisible" width="40%" center @close="miniClose">
      <el-form ref="dictionaryRules" :model="addEditData" :rules="dictionaryRules" label-width="150px" label-position="right" class="demo-ruleForm">
        <el-row>
          <el-col :lg="20" :xl="20" :md="20">
            <el-form-item v-if="minDialogTitle === 'view'" :label="$t('systemConf.bizType')" prop="bizType">
              <el-select v-model="addEditData.bizType" filterable clearable style="width: 100%">
                <el-option v-for="item in dictionaryTypeList" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
            <el-form-item v-else :label="$t('systemConf.bizType')">
              <el-input v-model="addEditData.bizType" disabled type="text" />
            </el-form-item>
            <el-form-item :label="$t('systemConf.bizCode')" prop="bizCode">
              <el-input v-model="addEditData.bizCode" type="text" />
            </el-form-item>
            <el-form-item :label="$t('systemConf.bizText')" prop="bizText">
              <el-input v-model="addEditData.bizText" type="text" />
            </el-form-item>
            <el-form-item :label="$t('systemConf.description')" prop="description">
              <el-input v-model="addEditData.description" type="text" />
            </el-form-item>
            <el-form-item :label="$t('systemConf.seq')" prop="seq">
              <el-input v-model="addEditData.seq" type="text" @change.native="changeValue" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="submitForm('dictionaryRules')">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="minDialogVisible = false">{{ $t('btn.cancel') }}</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="$t('promptMessage.deleteTipTitle')" :visible.sync="firstDialogVisible" width="30%">
      <span>{{ $t('systemConf.strongHints') }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="firstDialogVisible = false">{{ $t('btn.cancel') }}</el-button>
        <el-button type="primary" @click="handleClose()">{{ $t('btn.confirm') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { dictionaryType, dictionaryCodesQuery, dictionaryDelete, dictionaryEdit, dictionaryAdd } from '@/api/system-conf'
export default {
  name: 'DataDictionary',
  data() {
    return {
      // 查询参数
      searchData: {
        bizType: '',
      },
      dictionaryTypeList: [],
      searchRules: {
        bizType: [{ required: true, message: this.$t('placeholder.select'), trigger: 'change' }],
      },
      // 表格数据
      tableData: [],
      selectedList: [],
      // 新增编辑弹框
      minDialogVisible: false,
      firstDialogVisible: false,
      // 弹窗的标题
      minDialogTitle: 'view',
      isRemove: true,
      // 车场新建数据规则
      dictionaryRules: {
        bizCode: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        bizText: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        bizType: [{ required: true, message: this.$t('placeholder.input'), trigger: 'change' }],
        // description: [{ required: true, message: '请输入字典描述', trigger: 'blur' }]
      },
      // 新增和编辑的数据
      addEditData: {
        bizCode: '',
        bizText: '',
        bizType: '',
        description: '',
        seq: null,
      },
      deleteId: '',
    }
  },
  created() {
    this.getDictType()
  },
  methods: {
    async getDictType() {
      // 获取字典类型
      const { code, data } = await dictionaryType()
      if (code != '2000') return
      this.dictionaryTypeList = data
    },
    // 查询过程 提交表单加验证
    queryData(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          const { code, data } = await dictionaryCodesQuery(this.searchData)
          if (code != '2000') return
          this.tableData = data
        } else {
          return false
        }
      })
    },
    // 重置
    resetForm(formName) {
      this.tableData = []
      this.$refs[formName].resetFields()
    },
    // 新增或编辑数据字典
    addOrEditDic(title, data) {
      switch (title) {
        case 'edit':
          // statements_1
          this.addEditData = JSON.parse(JSON.stringify(data))
          break
        default:
          // statements_def
          break
      }
      this.minDialogTitle = title
      this.minDialogVisible = true
    },
    // 删除
    handleDel(current) {
      this.firstDialogVisible = true
      this.deleteId = current.pkId
    },
    handleClose() {
      this.firstDialogVisible = false
      this.$confirm(`${this.$t('promptMessage.handleTip')}`, {
        dangerouslyUseHTMLString: true,
        type: 'warning',
      })
        .then(async(_) => {
          // 此处判断是删除操作还是编辑操作
          if (this.isRemove) {
            const { code } = await dictionaryDelete(this.deleteId)
            if (code != '2000') return
            this.queryData('formSearch')
          } else {
            const { code } = await dictionaryEdit(this.addEditData)
            if (code != '2000') return
            this.queryData('formSearch')
            this.minDialogVisible = false
          }
        })
        .catch((_) => {})
    },
    // 提交表单加验证
    submitForm(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          if (this.minDialogTitle === 'view') {
            // 新建提交操作
            const { code } = await dictionaryAdd(this.addEditData)
            if (code != '2000') return
            if (this.addEditData.bizType === this.searchData.bizType) {
              this.queryData('formSearch')
            }
            this.minDialogVisible = false
          } else {
            this.isRemove = false // 标识删除操作
            this.firstDialogVisible = true
          }
        } else {
          return false
        }
      })
    },
    miniClose() {
      this.addEditData = {
        bizCode: '',
        bizText: '',
        bizType: '',
        description: '',
        seq: '',
      }
      this.$refs['dictionaryRules'].resetFields()
    },
    changeValue() {
      this.addEditData.seq = this.addEditData.seq.replace(/[^\d，,]]*/g, '')
      this.addEditData.seq = this.addEditData.seq.replace(/,/g, '')
    },
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
    },
  },
}
</script>
