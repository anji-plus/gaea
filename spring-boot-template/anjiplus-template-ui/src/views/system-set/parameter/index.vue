<template>
  <div class="app-container">
    <el-form ref="formSearch" :rules="rules" :model="params" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item prop="dictionaryName" label="参数名称">
                <el-input v-model="params.settingName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="dictionaryDescription" label="参数描述">
                <el-input v-model="params.settingLabel" />
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
    <el-button type="primary" icon="el-icon-plus" @click="handleClickAddOrEdit(null)">{{ $t('btn.add') }}</el-button>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column type="expand">
        <template slot-scope="props">
          <p class="table-expand-item">
            <span class="titel">参数值: </span>
            <span>{{ props.row.settingValue }}</span>
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="序号" min-width="60">
        <template slot-scope="scope">
          {{ params.pageSize * (params.pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="参数名称" min-width="180" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.settingName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="参数描述" min-width="250" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.settingLabel }}
        </template>
      </el-table-column>
      <el-table-column label="启用状态" min-width="120" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.enable }}
          <el-tooltip :content="scope.row.enable == 1 ? '已启用' : '已禁用'" placement="top">
            <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" :active-value="1" :inactive-value="0" @change="switchEnableById(scope.row)" />
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="210" align="center" :show-overflow-tooltip="true">
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
      <el-table-column label="修改人" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.updateBy }}
        </template>
      </el-table-column>
      <el-table-column label="修改时间" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.updateTime }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="100" align="center">
        <template slot-scope="scope">
          <!--<el-tooltip class="item" effect="dark" content="查看" placement="top">-->
          <!--<el-button :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="edit(scope.row.settingId, 'find')" />-->
          <!--</el-tooltip>-->
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.id, null, scope.row.settingName)" />
          </el-tooltip>
          <!-- <el-button :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row.parameterId)"/> -->
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination :total="totalCount" :page-sizes="[10, 20, 50, 100]" :page-size="params.pageSize" :current-page="params.pageNumber" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
  </div>
</template>

<script>
import { settingPageList, authSettingEdit } from '@/api/system-set'
export default {
  data() {
    return {
      clickType: '',
      formData: {},
      // 弹框默认隐藏
      dialogFormVisible: false,
      rules: {
        settingName: [
          { required: false, message: '请输入字典名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        settingLabel: [
          { required: false, message: '请输入字典描述', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
      },
      selectInput: {
        keyname: 'settingName',
      },
      params: {
        pageNumber: 1,
        pageSize: 10,
        settingName: '',
        settingLabel: '',
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
  created() {
    this.queryByPage()
  },
  methods: {
    async queryByPage() {
      const res = await settingPageList(this.params)
      if (res.code != '200') return
      this.list = res.data.records
      this.totalCount = res.data.total
      this.totalPage = res.data.pages
      this.listLoading = false
    },
    cancel() {
      this.dialogFormVisible = false
    },
    addOrEdit(id, val = '') {
      this.$router.push({ path: '/system/setting/edit', query: { id: id, val: val }})
      // this.clickType = 'add'
      // this.dialogFormVisible = true
      // this.formData = {
      //   settingName: '',
      //   settingLabel: '',
      //   settingType: '',
      //   settingValue: '',
      //   settingForm: '',
      //   remark: '',
      // }
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
      this.params.settingName = ''
      this.params.settingLabel = ''
      this.queryByPage()
    },
    // 启用禁用切换
    async switchEnableById(val) {
      const res = await authSettingEdit({
        id: val.id,
        enable: val.enable,
      })
      if (res.code !== '200') {
        this.queryByPage()
      }
    },
    handleSizeChange(val) {
      this.params.pageSize = val
      this.queryByPage()
    },
    handleCurrentChange(val) {
      this.params.pageNumber = val
      this.queryByPage()
    },
    handleClickAddOrEdit(id, val = '', name = '') {
      this.$router.push({ path: '/system-set/parameter/edit', query: { id: id, val: val, name: name }})
    },
    /*
      handleClickDelete(id) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }*/
  },
}
</script>
