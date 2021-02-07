<template>
  <div class="app-container">
    <el-form ref="formSearch" :rules="rules" :model="params" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item prop="helpCategory" label="所属分类">
                <el-select v-model="params.helpCategory" :placeholder="$t('placeholder.select')">
                  <el-option key="1" label="分类1" :value="1" />
                  <el-option key="0" label="分类2" :value="0" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="helpTitle" label="标题">
                <el-input v-model="params.helpTitle" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="helpCategory" label="启用状态">
                <el-select v-model="params.enableFlag" :placeholder="$t('placeholder.select')">
                  <el-option key="1" label="状态1" :value="1" />
                  <el-option key="0" label="状态2" :value="0" />
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
    <el-button type="primary" icon="el-icon-plus" @click="addOrEdit('add')">{{ $t('btn.add') }}</el-button>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="70">
        <template slot-scope="scope">
          {{ params.pageSize * (params.currentPage - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="所属分类" min-width="60" align="center">
        <template slot-scope="scope">
          {{ scope.row.helpCategory }}
        </template>
      </el-table-column>
      <el-table-column label="标题" min-width="140" align="center">
        <template slot-scope="scope">
          <span class="title">{{ scope.row.helpTitle }}</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" min-width="40" align="center">
        <template slot-scope="scope">
          {{ scope.row.sort }}
        </template>
      </el-table-column>
      <el-table-column label="启用状态" min-width="40" align="center">
        <template slot-scope="scope">
          {{ scope.row.status }}
          {{ scope.row.enableFlag }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="140" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="handleClickAddOrEdit(scope.row.helpId, 'find')" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="handleClickAddOrEdit(scope.row.helpId)" />
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
    <el-dialog title="表单" center width="80%" :visible.sync="dialogFormVisible">
      <!--<add-edit-->
      <!--:form="formData"-->
      <!--:clickType='clickType'-->
      <!--@cancel='cancel'-->
      <!--ref="dlg"/>-->
      <add-edit :form="formData" />
    </el-dialog>
  </div>
</template>

<script>
// import { queryByPage, deleteOne } from '@/api/system/helpCenter'
// import CodeSelect from '@/components/codeSelect'
// import {getStorageItem,setStorageItem} from '@/utils/storage.js'
import AddEdit from './component/index'
export default {
  components: {
    AddEdit,
  },
  data() {
    return {
      formData: {},
      // 弹框默认隐藏
      dialogFormVisible: false,
      params: {
        currentPage: 1,
        pageSize: 10,
        helpTitle: '',
        enableFlag: '',
        helpCategory: '',
      },
      list: [],
      totalCount: 0,
      totalPage: 0,
      listLoading: false,
      rules: {
        helpCategory: [{ required: false, message: '请选择所属分类', trigger: 'change' }],
        helpTitle: [
          { required: false, message: '请输入标题', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        enableFlag: [{ required: false, message: '请选择启用状态', trigger: 'change' }],
      },
    }
  },
  watch: {
    dialogFormVisible(val) {
      if (!val) {
        // this.formData={}
        // this.$refs.dlg.$refs.form.resetFields()
      }
    },
  },
  // created() {
  //   this.queryByPage()
  // },
  mounted() {
    // if(getStorageItem('helpCenterDetail')) {
    //   this.params = getStorageItem('helpCenterDetail')
    // }
    this.queryByPage()
  },
  methods: {
    addOrEdit() {
      this.dialogFormVisible = true
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
      this.helpCategory = ''
      this.helpTitle = ''
      this.enableFlag = ''
      // for(var key in this.params){
      //   this.params[key]=""
      // }
    },
    queryByPage() {
      this.listLoading = true
      // queryByPage(this.params).then(response => {
      const response = {
        repCode: '0000',
        repMsg: null,
        repData: {
          totalPage: 2,
          pageSize: 10,
          list: [
            {
              helpId: 25,
              helpCategory: 'login_register',
              helpTitle: '1222',
              helpContent: '<figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/b9b01955-d20e-434c-ac90-2c25597217a2"></figure><figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/f8f4a4e4-c156-45c2-b350-2e0c1afe87d4"></figure><p>&nbsp;</p><figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/3bdda098-8ba6-4a0b-bf2f-9e56550a872e"></figure><p>&nbsp;</p>',
              enableFlag: 1,
              sort: 1,
              remark: null,
              createdBy: 'aimee',
              createdTime: '2020-12-22T11:02:40',
              updatedBy: 'aimee',
              updatedTime: '2020-12-22T11:05:53',
            },
          ],
          currentPage: 1,
          totalCount: 11,
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
      // })
    },
    handleSizeChange(val) {
      this.params.pageSize = val
      this.queryByPage()
    },
    handleCurrentChange(val) {
      this.params.currentPage = val
      this.queryByPage()
    },
    // 新增 or 编辑
    handleClickAddOrEdit(id, val = '') {
      if (id) {
        // setStorageItem('helpCenterDetail', this.params)
      }
      this.$router.push({ path: '/system/helpCenter/edit', query: { id: id, val: val }})
    },
    // 删除
    handleClickDelete(row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          // deleteOne({ helpId: row.helpId }).then((response) => {
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
<style lang="scss" scoped>
.title {
  width: 100%;
  display: inline-block;
  text-align: left;
}
</style>
