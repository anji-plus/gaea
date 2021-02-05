<template>
  <div class="app-container">
    <el-form ref="formSearch" :rules="rules" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item prop="dictionaryName" label="字典名称">
                <el-input v-model="searchForm.dictionaryName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="dictionaryDescription" label="字典描述">
                <el-input v-model="searchForm.dictionaryDescription" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="dictionaryCode" label="字典代码">
                <el-input v-model="searchForm.dictionaryCode" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="codeDescription" label="代码描述">
                <el-input v-model="searchForm.codeDescription" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="status" label="启用状态">
                <el-select v-model="searchForm.status" :placeholder="$t('placeholder.select')">
                  <el-option key="1" label="启用" :value="1" />
                  <el-option key="0" label="禁用" :value="0" />
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
          {{ scope.row.enableFlag }}
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column label="创建人" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.createdBy }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.createdTime }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="115" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="edit(scope.row.dictId, 'find')" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.dictId)" />
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
      <add-edit ref="dlg" :form="formData" :click-type="clickType" @cancel="cancel" />
    </el-dialog>
  </div>
</template>

<script>
import AddEdit from './component/index'
// import { queryByPage, deleteOne } from '@/api/system/dict'
export default {
  components: {
    AddEdit,
  },
  data() {
    return {
      clickType: '',
      formData: {},
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
        dictionaryName: [
          { required: false, message: '请输入字典名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        dictionaryDescription: [
          { required: false, message: '请输入字典描述', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        dictionaryCode: [
          { required: false, message: '请输入字典代码', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        codeDescription: [
          { required: false, message: '请输入代码描述', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        status: [{ required: false, message: '请选择启用状态', trigger: 'change' }],
      },
      params: {
        currentPage: 1,
        pageSize: 10,
        dictName: null,
        dictDesc: null,
        itemName: null,
        itemDesc: null,
        enableFlag: null,
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
    add() {
      this.clickType = 'add'
      this.dialogFormVisible = true
    },
    edit(id, val = '') {
      this.clickType = 'edit'
      this.dialogFormVisible = true
      this.$nextTick(function() {
        const data = { repData: { dictId: 317, dictName: 'HELP_CATEGORY', dictDesc: '帮忙分类', itemName: 'trigger_alert', itemValue: 'trigger_alert', itemDesc: '触发告警', itemExtend: '', enableFlag: 0, deleteFlag: 0, sort: 3, remark: '321423235325', createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'aimee', updatedTime: '2020-12-21T16:10:53' }}
        this.formData = data.repData
      })
      // this.$router.push({ path: '/system/dict/edit', query: { id: id, val: val }})
    },
    cancel() {
      this.dialogFormVisible = false
    },
    // 查询
    search(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 重置
    reset(formName) {
      this.$refs[formName].resetFields()
      for (var key in this.searchForm) {
        this.searchForm[key] = ''
      }
    },
    queryByPage() {
      this.listLoading = true
      // queryByPage(this.params).then(response => {
      //   if (response.repCode == '0000') {
      //     this.list = response.repData.list
      //     this.totalCount = response.repData.totalCount
      //     this.totalPage = response.repData.totalPage
      //   }
      //   this.listLoading = false
      // })
      const response = {
        repCode: '0000',
        repMsg: null,
        repData: {
          totalPage: 9,
          pageSize: 10,
          list: [
            { dictId: 317, dictName: 'HELP_CATEGORY', dictDesc: '帮忙分类', itemName: 'trigger_alert', itemValue: 'trigger_alert', itemDesc: '触发告警', itemExtend: '', enableFlag: 0, deleteFlag: 0, sort: 3, remark: '321423235325', createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'aimee', updatedTime: '2020-12-21T16:10:53' },
            { dictId: 353, dictName: '243', dictDesc: '342', itemName: '2332', itemValue: '32523', itemDesc: '23523', itemExtend: '', enableFlag: 1, deleteFlag: 0, sort: 1, remark: '', createdBy: 'aimee', createdTime: '2020-12-16T13:26:01', updatedBy: 'aimee', updatedTime: '2020-12-16T13:26:01' },
            { dictId: 1, dictName: 'AUDIT_FLAG', dictDesc: '审核标志', itemName: 'waiting', itemValue: 'waiting', itemDesc: '待审核', itemExtend: null, enableFlag: 1, deleteFlag: 0, sort: 1, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
            { dictId: 2, dictName: 'AUDIT_FLAG', dictDesc: '审核标志', itemName: 'ongoing', itemValue: 'ongoing', itemDesc: '审核中', itemExtend: null, enableFlag: 1, deleteFlag: 0, sort: 2, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
            { dictId: 3, dictName: 'AUDIT_FLAG', dictDesc: '审核标志', itemName: 'approved', itemValue: 'approved', itemDesc: '通过', itemExtend: '', enableFlag: 1, deleteFlag: 0, sort: 3, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
            { dictId: 4, dictName: 'AUDIT_FLAG', dictDesc: '审核标志', itemName: 'rejected', itemValue: 'rejected', itemDesc: '拒绝', itemExtend: null, enableFlag: 1, deleteFlag: 0, sort: 4, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
            { dictId: 5, dictName: 'DELETE_FLAG', dictDesc: '删除状态', itemName: 'deleted', itemValue: '1', itemDesc: '已删除', itemExtend: null, enableFlag: 1, deleteFlag: 0, sort: 5, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
            { dictId: 6, dictName: 'DELETE_FLAG', dictDesc: '删除状态', itemName: 'undeleted', itemValue: '0', itemDesc: '未删除', itemExtend: null, enableFlag: 1, deleteFlag: 0, sort: 6, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
            { dictId: 7, dictName: 'ENABLE_FLAG', dictDesc: '是否启用', itemName: 'disable', itemValue: '0', itemDesc: '已禁用', itemExtend: null, enableFlag: 1, deleteFlag: 0, sort: 7, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
            { dictId: 8, dictName: 'ENABLE_FLAG', dictDesc: '是否启用', itemName: 'enable', itemValue: '1', itemDesc: '已启用', itemExtend: null, enableFlag: 1, deleteFlag: 0, sort: 8, remark: null, createdBy: 'admin', createdTime: '2020-11-25T15:01:36', updatedBy: 'admin', updatedTime: '2020-11-25T15:01:36' },
          ],
          currentPage: 1,
          totalCount: 87,
        },
        success: true,
        error: false,
      }
      if (response.repCode == '0000') {
        this.list = response.repData.list
        this.totalCount = response.repData.totalCount
        this.totalPage = response.repData.totalPage
      }
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
        .then(() => {
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
