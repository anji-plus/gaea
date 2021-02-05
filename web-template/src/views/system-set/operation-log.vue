<template>
  <div class="app-container">
    <el-form ref="form" :model="params" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="loginName" label="用户名称">
            <el-select v-model="params.loginName" :placeholder="$t('placeholder.select')">
              <el-option key="1" label="名称1" :value="1" />
              <el-option key="0" label="名称2" :value="0" />
            </el-select>
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
          <!--
          <p class="table-expand-item">
            <span class="titel">请求类型: </span>
            <span>{{props.row.requestMethod}}</span>
          </p>
          -->
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
          {{ params.pageSize * (params.currentPage - 1) + scope.$index + 1 }}
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
      <!--
      <el-table-column label="请求类型" min-width="80" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.requestMethod }}
        </template>
      </el-table-column>
      -->
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
      <el-pagination :total="totalCount" :page-sizes="[10, 20, 50, 100]" :page-size="params.pageSize" :current-page="params.currentPage" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
  </div>
</template>

<script>
export default {
  components: {
    // AddEdit,
  },
  data() {
    return {
      searchForm: {
        parmasName: '',
        parmasDescription: '',
      },
      rules: {
        requestUrl: [{ required: false, message: '请输入请求路径', trigger: 'blur' }],
        pageTitle: [{ required: false, message: '请输入页面标题', trigger: 'blur' }],
        loginName: [{ required: false, message: '请选择用户名称', trigger: 'change' }],
      },
      selectInput: {
        keyname: 'settingName',
      },
      params: {
        currentPage: 1,
        pageSize: 10,
        pageTitle: null,
        requestUrl: null,
        userName: null,
        loginName: null,
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
      const response = {
        repCode: '0000',
        repMsg: null,
        repData: {
          totalPage: 188,
          pageSize: 10,
          list: [
            {
              logId: 171279,
              userId: 1,
              userName: 'admin',
              requestUrl: '/auth-service/menu/queryActionTreeForMenu',
              pageTitle: '查询菜单的关联按钮树',
              requestParam: '{"menuId":52}',
              responseParam:
                '{"repCode":"0000","repMsg":null,"repData":{"checkedIds":[1,2,3,4,22],"treeData":[{"id":1,"label":"新增","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":2,"label":"修改","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":3,"label":"删除","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":4,"label":"查询","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":11,"label":"分配权限","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":12,"label":"分配角色","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":13,"label":"重置密码","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":21,"label":"导入","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false},{"id":22,"label":"导出","disabled":false,"children":[],"extend":null,"parentId":null,"name":null,"value":null,"type":null,"checked":false,"expend":false}]},"success":true,"error":false}',
              sourceIp: '10.108.12.20',
              requestMethod: null,
              requestTime: '2021-02-05T12:47:17',
            },
          ],
          currentPage: 1,
          totalCount: 1872,
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
  },
}
</script>
