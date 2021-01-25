<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item :label="$t('businessGlossary.fields1')" prop="fields1">
                <el-input v-model="searchForm.fields1" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('businessGlossary.fields2')" prop="fields2">
                <el-input v-model="searchForm.fields2" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('businessGlossary.fields3')" prop="fields3">
                <el-input v-model="searchForm.fields3" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('businessGlossary.fields4')" prop="fields4">
                <el-input v-model="searchForm.fields4" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('businessGlossary.fields5')" prop="fields5">
                <el-input v-model="searchForm.fields5" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('businessGlossary.fields6')" prop="fields6">
                <el-date-picker v-model="searchForm.fields6" type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" :placeholder="$t('placeholder.select')" style="width: 100%" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button
            type="primary"
            @click="
              searchForm.pageNum = 1
              getData()
            "
          >{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="resetForm('formSearch')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-button type="primary" icon="el-icon-plus" @click="openCreateUser">{{ $t('btn.add') }}</el-button>
    <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="editDetail('edit', null)">{{ $t('btn.edit') }}</el-button>
    <delete-btn :disabled="selectedList.length != 1" @handleDelete="handleDelete" />
    <el-button type="primary" icon="el-icon-download" @click.native="download">{{ $t('btn.export') }}</el-button>
    <!-- 
      importApi -- fileApi中导入地址的键        必填
      exportApi -- exportApi中模板导出地址的键  必填
      exportName  -- 导出模板文件的名称         非必填
    -->
    <upload-excel import-api="exportApi" export-api="tempExportApi" export-name="模板名称" />
    <!-- <el-button type="primary" icon="el-icon-film" @click.native="dialogVisible = true">{{ $t('btn.transfer') }}</el-button>
    <el-button type="primary" @click.native="openToast" icon="el-icon-thumb">Toast</el-button>
    <el-button type="primary" icon="el-icon-loading" @click.native="openFullScreen">Loading</el-button> -->
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column :label="$t('businessGlossary.fields1')" align="center">
        <template slot-scope="scope">
          <span class="view" @click="editDetail('view', scope.row)">{{ scope.row.fields1 }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="fields2" :label="$t('businessGlossary.fields2')" align="center" />
      <el-table-column prop="fields3" :label="$t('businessGlossary.fields3')" align="center" />
      <el-table-column prop="fields4" :label="$t('businessGlossary.fields4')" align="center" />
      <el-table-column prop="fields5" :label="$t('businessGlossary.fields5')" align="center" />
      <el-table-column prop="fields6" :label="$t('businessGlossary.fields6')" align="center" width="200" :show-overflow-tooltip="true" />
      <el-table-column prop="fields7" :label="$t('businessGlossary.fields7')" align="center" />
      <el-table-column prop="fields8" :label="$t('businessGlossary.fields8')" align="center" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNum" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="60%" :close-on-click-modal="false" center :visible.sync="basicDialog" @close="closeDialog">
      <el-form ref="userForm" :model="dialogForm" :rules="formRules" label-width="100px" style="padding-right: 15px" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col :span="6">
            <el-form-item :label="$t('businessGlossary.fields1')" prop="fields1">
              <el-input v-model="dialogForm.fields1" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('businessGlossary.fields2')" prop="fields2">
              <el-input v-model="dialogForm.fields2" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('businessGlossary.fields3')" prop="fields3">
              <el-input v-model="dialogForm.fields3" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('businessGlossary.fields4')" prop="fields4">
              <el-input v-model="dialogForm.fields4" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item :label="$t('businessGlossary.fields5')" prop="fields5">
              <el-input v-model="dialogForm.fields5" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button v-if="dialogTittle != 'view'" type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="basicDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="$t('btn.transfer')" :visible.sync="dialogVisible" center width="60%">
      <el-transfer v-model="value" filterable :filter-method="filterMethod" :filter-placeholder="$t('businessGlossary.plinput')" :data="data" />
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="dialogVisible = false">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="dialogVisible = false">{{ $t('btn.close') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { listData } from '@/api/list'
import fileApi from '@/api/file-api'
export default {
  data() {
    const generateData = (_) => {
      const data = []
      const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都']
      const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu']
      cities.forEach((city, index) => {
        data.push({
          label: city,
          key: index,
          pinyin: pinyin[index],
        })
      })
      return data
    }
    return {
      selectedList: [],
      searchForm: {
        fields1: '',
        fields2: '',
        fields3: '',
        fields4: '',
        fields5: '',
        fields6: '',
        pageNum: 1,
        pageSize: 10,
      },
      tableList: [],
      total: 0,
      dialogTittle: 'view',
      basicDialog: false,
      dialogForm: {
        fields1: '',
        fields2: '',
        fields3: '',
        fields4: '',
        fields5: '',
        fields6: '',
      },
      formRules: {
        fields1: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
        fields2: [{ required: true, message: this.$t('placeholder.input'), trigger: 'blur' }],
      },
      dialogVisible: false,
      data: generateData(),
      value: [],
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1
      },
    }
  },
  methods: {
    download() {
      fileApi.exportFile(this.searchForm, 'exportApi', '导出地址')
    },
    // 提交按钮
    confirm() {
      this.$refs.userForm.validate((valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            // userAdd(this.dialogForm).then(res => {
            //   if (res.code === '2000') {
            this.closeDialog(true)
            //     return
            //   }
            // })
          } else {
            // userEdit(this.dialogForm).then(res => {
            //   if (res.code === '2000') {
            this.closeDialog(true)
            //     return
            //   }
            // })
          }
        } else {
          return
        }
      })
    },
    // 关闭弹窗
    closeDialog(bool) {
      bool && this.getData() // 点确定关闭弹窗的时候才会刷新列表
      this.$refs['userForm'].resetFields()
      this.basicDialog = false
    },
    // 删除操作
    handleDelete() {
      // userDelete(this.selectedList[0].pkId).then(res => {
      //   if (res.code === '2000') {
      this.getData()
      return
      //   }
      // })
    },
    // 新建操作
    openCreateUser() {
      this.dialogTittle = 'add' // 新建
      this.basicDialog = true
    },
    // 编辑和查看操作
    editDetail(title, row) {
      this.dialogTittle = title
      this.basicDialog = true
      this.$nextTick(() => {
        this.dialogForm = JSON.parse(JSON.stringify(row || this.selectedList[0]))
      })
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.tableList = []
      this.total = 0
    },
    // 查询
    getData() {
      listData(this.searchForm).then((res) => {
        if (res.code == '2000') {
          this.tableList = res.data.list
          this.total = res.data.total
          return
        }
      })
    },
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
    },
    // 页码改变
    handleCurrentChange(pageNum) {
      this.searchForm.pageNum = pageNum
      this.getData()
    },
    // 每页size改变时
    handleSizeChange(val) {
      this.searchForm.pageNum = 1
      this.searchForm.pageSize = val
      this.getData()
    },
    openFullScreen() {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      setTimeout(() => {
        loading.close()
      }, 2000)
    },
    openToast() {
      this.$message('消息提示')
      this.$message({
        message: '成功消息',
        type: 'success',
      })
      this.$message({
        message: '警告消息',
        type: 'warning',
      })
      this.$message({
        message: '错误消息',
        type: 'error',
      })
    },
  },
}
</script>
<style lang="scss">
.el-transfer {
  text-align: center;
  .el-transfer-panel {
    text-align: left;
  }
}
</style>
