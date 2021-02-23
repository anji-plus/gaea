<template>
  <div class="app-container">
    <el-form ref="formSearch" :rules="rules" :model="params" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item prop="helpCategory" label="所属分类">
                <el-select v-model="params.helpCategory" :placeholder="$t('placeholder.select')">
                  <el-option v-for="(item, i) in classificationList" :key="i" :label="item.text" :value="item.id" />
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
                <el-select v-model="params.enabled" :placeholder="$t('placeholder.select')">
                  <el-option v-for="(item, i) in statusList" :key="i" :label="item.label" :value="item.value" />
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
    <el-button type="primary" icon="el-icon-plus" @click="addOrEdit(null, '新增')">{{ $t('btn.add') }}</el-button>
    <!-- 查询结果列表 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" style="width: 100%" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="70">
        <template slot-scope="scope">
          {{ params.pageSize * (params.currentPage - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="所属分类" min-width="60" align="center">
        <template slot-scope="scope">
          {{ scope.row.helpCategory | filterClassification }}
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
          {{ scope.row.enabled | filterStatus }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="140" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="addOrEdit(scope.row, '查看')" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="addOrEdit(scope.row, '编辑')" />
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
    <el-dialog v-if="dialogFormVisible" :title="clickType" center width="80%" :visible.sync="dialogFormVisible">
      <add-edit ref="dlg" :help-form="formData" :click-type="clickType" :classification-list="classificationList" />
    </el-dialog>
  </div>
</template>

<script>
import { gaeaHelpPageList, gaeaHelpDelect, dataDictionary } from '@/api/system-set'
import AddEdit from './component/index'
var typeData
export default {
  components: {
    AddEdit,
  },
  filters: {
    filterStatus(val) {
      return val === 1 ? '启用' : '禁用'
    },
    filterClassification(val) {
      for (var i = 0; i < typeData.classificationList.length; i++) {
        if (typeData.classificationList[i].id == val) {
          return typeData.classificationList[i].text
        }
      }
    },
  },
  data() {
    return {
      clickType: '',
      formData: {},
      // 弹框默认隐藏
      dialogFormVisible: false,
      params: {
        currentPage: 1,
        pageSize: 10,
        helpTitle: '',
        enabled: null,
        helpCategory: '',
      },
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
      // 所属分类数据
      classificationList: [
        // {extend: "", label: "登录注册", labelEng: "login_register", value: "login_register"},
        // {extend: "", label: "权限角色", labelEng: "auth_role", value: "auth_role"},
        // {extend: "", label: "字典管理", labelEng: "dict_manager", value: "dict_manager"},
        // {extend: "", label: "系统设置", labelEng: "system_setting", value: "system_setting"},
        // {extend: "", label: "消息推送", labelEng: "message_push", value: "message_push"},
        // {extend: "", label: "设备管理", labelEng: "device_manager", value: "device_manager"},
        // {extend: "", label: "监控计算", labelEng: "item_calculate", value: "item_calculate"}
      ],
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
        enabled: [{ required: false, message: '请选择启用状态', trigger: 'change' }],
      },
    }
  },
  watch: {
    dialogFormVisible(val) {
      if (!val) {
        this.formData = {}
        this.$refs.dlg.$refs.helpForm.resetFields()
      }
    },
  },
  // 在生命周期 beforeCreate里面改变this指向
  beforeCreate: function() {
    typeData = this
  },
  mounted() {
    this.queryByPage()
  },
  created() {
    this.getDictionary()
  },
  methods: {
    colseDialog(type) {
      this.dialogFormVisible = false
      this.clickType = ''
      if (type != 'no') {
        this.queryByPage()
      }
    },
    addOrEdit(data, type) {
      this.dialogFormVisible = true
      this.clickType = type
      if (type == '查看' || type == '编辑') {
        this.formData = JSON.parse(JSON.stringify(data))
      }
    },
    // 查询
    search(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.queryByPage()
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
      this.params.helpCategory = ''
      this.params.helpTitle = ''
      this.params.enabled = null
      this.queryByPage()
    },
    async queryByPage() {
      this.listLoading = true
      const res = await gaeaHelpPageList(this.params)
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
    // 删除
    handleClickDelete(row) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async() => {
          const { code } = await gaeaHelpDelect(row.id)
          if (code != '200') return
          this.queryByPage()
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
    async getDictionary() {
      const res = await dataDictionary('FILE_STATUS')
      if (res.code != '200') return
      this.classificationList = res.data
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
