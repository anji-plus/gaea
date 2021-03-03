<template>
  <div class="app-container">
    <el-form ref="formSearch" :rules="rules" :model="params" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item prop="dictName" label="字典名称">
                <el-input v-model="params.dictName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="dictDesc" label="字典描述">
                <el-input v-model="params.dictDesc" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="itemName" label="字典代码">
                <el-input v-model="params.itemName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="itemDesc" label="代码描述">
                <el-input v-model="params.itemDesc" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="enabled" label="启用状态">
                <el-select v-model="params.enabled" :placeholder="$t('placeholder.select')">
                  <el-option v-for="(item, i) in statusList" :key="i" :label="item.label" :value="item.value" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="locale" label="语言">
                <el-select v-model="params.locale" :placeholder="$t('placeholder.select')">
                  <el-option v-for="(item, i) in languageList" :key="i" :label="item.text" :value="item.id" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button type="primary" @click="search('formSearch')">{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="reset('formSearch')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-button type="primary" icon="el-icon-plus" @click="add">{{ $t('btn.add') }}</el-button>
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" min-width="70">
        <template slot-scope="scope">
          {{ params.pageSize * (params.currentPage - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="字典名称" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.dictName }}
        </template>
      </el-table-column>
      <el-table-column label="字典描述" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.dictDesc }}</span>
        </template>
      </el-table-column>
      <el-table-column label="字典代码" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.itemName }}
        </template>
      </el-table-column>
      <el-table-column label="代码值" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.itemValue }}
        </template>
      </el-table-column>
      <el-table-column label="代码描述" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.itemDesc }}
        </template>
      </el-table-column>
      <el-table-column label="排序" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.sort }}
        </template>
      </el-table-column>
      <el-table-column label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.enabled | filterStatus }}
        </template>
      </el-table-column>
      <el-table-column label="语言" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.locale | filterLanguage }}
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column label="创建人" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.createBy }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="160" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="edit(scope.row, 'see')" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row), 'edit'" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row)" />
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination :total="totalCount" :page-sizes="[10, 20, 50, 100]" :page-size="params.pageSize" :current-page="params.currentPage" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <el-dialog title="表单" center width="40%" :visible.sync="dialogFormVisible">
      <add-edit ref="dlg" :form="formData" :language="languageList" :click-type="clickType" @cancel="cancel" />
    </el-dialog>
  </div>
</template>

<script>
import AddEdit from './component/index'
import { gaeaDictpageList, businessGaeaDictDelect } from '@/api/system-set'
import { dataDictionary } from '@/api/common'
var typeData
export default {
  components: {
    AddEdit,
  },
  filters: {
    filterStatus(val) {
      return val === 1 ? '启用' : '禁用'
    },
    filterLanguage(val) {
      for (var i = 0; i < typeData.languageList.length; i++) {
        if (typeData.languageList[i].id == val) {
          return typeData.languageList[i].text
        }
      }
    },
  },
  data() {
    return {
      // 语言列表
      languageList: [],
      clickType: '',
      formData: {},
      // 启用状态数据
      statusList: [
        {
          label: '启用',
          value: 1,
        },
        {
          label: '禁用',
          value: 0,
        },
      ],
      // 弹框默认隐藏
      dialogFormVisible: false,
      searchForm: {
        dictionaryName: '',
        dictionaryDescription: '',
        dictionaryCode: '',
        codeDescription: '',
        status: '',
      },
      rules: {
        dictName: [
          { required: false, message: '请输入字典名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        dictDesc: [
          { required: false, message: '请输入字典描述', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        itemName: [
          { required: false, message: '请输入字典代码', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        itemDesc: [
          { required: false, message: '请输入代码描述', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        enabled: [{ required: false, message: '请选择启用状态', trigger: 'change' }],
        locale: [{ required: false, message: '请选择语言', trigger: 'change' }],
      },
      params: {
        currentPage: 1,
        pageSize: 10,
        dictName: '',
        dictDesc: '',
        itemName: '',
        itemDesc: '',
        locale: '',
        enabled: null,
      },
      list: null,
      totalCount: 0,
      totalPage: 0,
      listLoading: true,
    }
  },
  watch: {
    dialogFormVisible(val) {
      if (!val) {
        this.formData = {}
        this.$refs.dlg.$refs.form.resetFields()
      }
    },
  },
  // 在生命周期 beforeCreate里面改变this指向
  beforeCreate: function() {
    typeData = this
  },
  created() {
    dataDictionary('LOCALE').then((res) => {
      this.languageList = res.data
    })
    this.queryByPage()
  },
  methods: {
    add() {
      this.clickType = 'add'
      this.dialogFormVisible = true
    },
    edit(data, type) {
      this.clickType = type
      this.dialogFormVisible = true
      this.formData = JSON.parse(JSON.stringify(data))
    },
    cancel() {
      this.dialogFormVisible = false
    },
    // 查询
    search(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('submit!')
          this.queryByPage()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 重置
    reset(formName) {
      this.$refs[formName].resetFields()
      this.params.dictName = ''
      this.params.dictDesc = ''
      this.params.itemName = ''
      this.params.itemDesc = ''
      this.params.locale = ''
      this.params.enabled = null
      this.queryByPage()
    },
    // 新增or编辑
    saveSucess() {
      this.dialogFormVisible = false
      this.queryByPage()
    },
    async queryByPage() {
      this.listLoading = true
      const res = await gaeaDictpageList(this.params)
      if (res.code != '200') return
      this.list = res.data.records
      this.totalCount = res.data.total
      this.totalPage = res.data.pages
      this.listLoading = false
    },
    handleSizeChange(val) {
      this.params.pageSize = val
      this.queryByPage()
    },
    handleCurrentChange(val) {
      this.params.currentPage = val
      this.queryByPage()
    },
    handleClickDelete(row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async() => {
          // 删除操作
          const { code } = await businessGaeaDictDelect(row.id)
          if (code != '200') return
          this.queryByPage()
          // deleteOne({ dictId: row.dictId }).then((response) => {
          //   if (response.repCode == '0000') {
          //     this.$message({ message: '删除成功', type: 'success', duration: 1500 })
          //     this.search()
          //   }
          // })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
  },
}
</script>
