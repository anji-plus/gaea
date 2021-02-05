<template>
  <div class="app-container">
    <el-form ref="formSearch" :rules="rules" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item prop="dictionaryName" label="参数名称">
                <el-input v-model="searchForm.parmasName" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="dictionaryDescription" label="参数描述">
                <el-input v-model="searchForm.parmasDescription" />
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
          {{ params.pageSize * (params.currentPage - 1) + scope.$index + 1 }}
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
          <el-tooltip :content="scope.row.enableFlag == 1 ? '已启用' : '已禁用'" placement="top">
            <el-switch v-model="scope.row.enableFlag" active-color="#13ce66" inactive-color="#ccc" :active-value="1" :inactive-value="0" @change="switchEnableById(scope.row)" />
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
          {{ scope.row.createdBy }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.createdTime }}
        </template>
      </el-table-column>
      <el-table-column label="修改人" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedBy }}
        </template>
      </el-table-column>
      <el-table-column label="修改时间" min-width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.updatedTime }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="100" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button :circle="true" :plain="true" type="success" icon="el-icon-view" size="mini" @click="edit(scope.row.settingId, 'find')" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button :circle="true" :plain="true" type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.settingId)" />
          </el-tooltip>
          <!-- <el-button :circle="true" :plain="true" type="danger" icon="el-icon-delete" size="mini" @click="handleClickDelete(scope.row.parameterId)"/> -->
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
        parmasName: '',
        parmasDescription: '',
      },
      rules: {
        parmasName: [
          { required: false, message: '请输入字典名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
        parmasDescription: [
          { required: false, message: '请输入字典描述', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' },
        ],
      },
      selectInput: {
        keyname: 'settingName',
      },
      params: {
        currentPage: 1,
        pageSize: 10,
        settingName: null,
        settingLabel: null,
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
    cancel() {
      this.dialogFormVisible = false
    },
    add() {
      this.clickType = 'add'
      this.dialogFormVisible = true
    },
    edit(id, val = '') {
      this.clickType = 'edit'
      this.dialogFormVisible = true
      this.$nextTick(function() {
        const data = {
          repCode: '0000',
          repMsg: null,
          repData: {
            settingId: 25,
            settingName: 'app_index_config',
            settingLabel: 'app首页轮播',
            settingType: 'custom-form',
            settingForm:
              '[{\r\n\t"type": "upload",\r\n\t"label": "轮播图片1",\r\n\t"name": "image_file1",\r\n\t"required": true,\r\n\t"placeholder": ""\r\n}, {\r\n\t"type": "upload",\r\n\t"label": "轮播图片2",\r\n\t"name": "image_file2",\r\n\t"required": true,\r\n\t"placeholder": ""\r\n}, {\r\n\t"type": "upload",\r\n\t"label": "轮播图片3",\r\n\t"name": "image_file3",\r\n\t"required": true,\r\n\t"placeholder": ""\r\n},{\r\n\t"type": "input-number",\r\n\t"label": "轮播间隔秒",\r\n\t"name": "durationSecond",\r\n\t"required": true,\r\n\t"placeholder": ""\r\n}]',
            settingValue: '{"image_file1":"http://haitongnla.test.anji-plus.com/auth-service/file/download/a1357152-f47b-4768-a26b-7642ca16bfd5","image_file2":"http://haitongnla.test.anji-plus.com/auth-service/file/download/057d7496-c52d-45fc-a624-6101e389deba","image_file3":"http://haitongnla.test.anji-plus.com/auth-service/file/download/1567be96-f580-47cc-85a0-55159f7331a9","durationSecond":5}',
            enableFlag: 1,
            remark: 'app首页轮播',
            createdBy: 'admin',
            createdTime: '2020-11-25T15:01:57',
            updatedBy: 'admin',
            updatedTime: '2020-12-21T09:33:58',
            settingValueJson: { image_file2: 'http://haitongnla.test.anji-plus.com/auth-service/file/download/057d7496-c52d-45fc-a624-6101e389deba', durationSecond: 5, image_file3: 'http://haitongnla.test.anji-plus.com/auth-service/file/download/1567be96-f580-47cc-85a0-55159f7331a9', image_file1: 'http://haitongnla.test.anji-plus.com/auth-service/file/download/a1357152-f47b-4768-a26b-7642ca16bfd5' },
            itemDesc: null,
            intSettingValue: null,
          },
          success: true,
          error: false,
        }
        this.formData = data.repData
      })
      // this.$router.push({ path: '/system/dict/edit', query: { id: id, val: val }})
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
    // 启用禁用切换
    switchEnableById(val) {
      // reqSwitchEnableById({ settingId: val.settingId }).then((res) => {
      //   if (res.repCode == '0000') {
      //     this.$message.success('状态切换成功')
      //   }
      // })
    },
    queryByPage() {
      this.listLoading = true
      // queryByPage(this.params).then(response => {
      const response = {
        repCode: '0000',
        repMsg: null,
        repData: { totalPage: 1, pageSize: 10, list: [{ settingId: 7, settingName: 'big_screen_chart_refresh_frequency', settingLabel: '大屏图表刷新频率(秒)', settingType: 'input-number', settingForm: '', settingValue: '300', enableFlag: 1, remark: '单位(秒)', createdBy: 'admin', createdTime: '2020-11-25T15:01:57', updatedBy: 'admin', updatedTime: '2021-01-07T12:10:55', settingValueJson: {}, itemDesc: null, intSettingValue: 300 }], currentPage: 1, totalCount: 8 },
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
    handleClickAddOrEdit(id, val = '') {
      this.$router.push({ path: '/system/setting/edit', query: { id: id, val: val }})
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
