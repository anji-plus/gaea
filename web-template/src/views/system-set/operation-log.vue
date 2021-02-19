<template>
  <div class="app-container">
    <el-form ref="form" :model="params" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="userName" label="用户名称">
            <el-input v-model.trim="params.userName" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="页面标题" prop="pageTitle">
            <el-input v-model.trim="params.pageTitle" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="请求路径" prop="requestUrl">
            <el-input v-model.trim="params.requestUrl" />
          </el-form-item>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button type="primary" @click="search('form')">{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="reset('form')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column type="expand">
        <template slot-scope="props">
          <p class="table-expand-item">
            <span class="titel">请求路径: </span>
            <span>{{ props.row.requestUrl }}</span>
          </p>
          <p class="table-expand-item">
            <span class="titel">请求参数: </span>
            <span>{{ props.row.requestParam }}</span>
          </p>
          <p class="table-expand-item">
            <span class="titel">响应参数: </span>
            <span>{{ props.row.responseParam }}</span>
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="序号" min-width="40">
        <template slot-scope="scope">
          {{ params.pageSize * (params.pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="用户名称" max-width="120" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column label="页面标题" min-width="110" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.pageTitle }}
        </template>
      </el-table-column>
      <el-table-column label="请求路径" min-width="130" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.requestUrl }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请求参数" min-width="110" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.requestParam }}
        </template>
      </el-table-column>
      <el-table-column label="响应参数" min-width="110" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.responseParam }}
        </template>
      </el-table-column>
      <el-table-column label="来源IP" min-width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.sourceIp }}
        </template>
      </el-table-column>
      <el-table-column label="访问时间" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.requestTime }}
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination :total="totalCount" :page-sizes="[10, 20, 50, 100]" :page-size="params.pageSize" :current-page="params.pageNumber" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
  </div>
</template>

<script>
import { logPageList } from '@/api/system-set'

export default {
  data() {
    return {
      rules: {
        requestUrl: [{ required: false, message: '请输入请求路径', trigger: 'blur' }],
        pageTitle: [{ required: false, message: '请输入页面标题', trigger: 'blur' }],
        userName: [{ required: false, message: '请输入用户名称', trigger: 'blur' }],
      },
      params: {
        pageNumber: 1,
        pageSize: 10,
        pageTitle: '',
        requestUrl: '',
        userName: '',
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
      this.params.userName = ''
      this.params.pageTitle = ''
      this.params.requestUrl = ''
      this.queryByPage()
    },
    async queryByPage() {
      const res = await logPageList(this.params)
      if (res.code != '200') return
      this.listLoading = true
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
      this.params.pageNumber = val
      this.queryByPage()
    },
  },
}
</script>
