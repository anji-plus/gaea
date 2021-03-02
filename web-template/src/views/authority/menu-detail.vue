<template>
  <div class="app-container">
    <el-card shadow="always" class="card">
      <div slot="header" class="clearfix">
        <span>自定义列</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="openAddColumn">{{ $t('btn.add') }}</el-button>
      </div>
      <el-table :data="columnTableData" border style="width: 100%">
        <el-table-column prop="tableCode" label="表编码" />
        <el-table-column prop="name" label="列名" />
        <el-table-column prop="code" label="列编码" />
        <el-table-column prop="width" label="宽度（px）">
          <template slot-scope="scope">{{ scope.row.width?scope.row.width:'自适应' }}</template>
        </el-table-column>
        <el-table-column prop="sortable" label="是否排序">
          <template slot-scope="scope">{{ scope.row.sortable?'是':'否' }}</template>
        </el-table-column>
        <el-table-column prop="visible" label="是否可见">
          <template slot-scope="scope">{{ scope.row.visible?'是':'否' }}</template>
        </el-table-column>
        <el-table-column prop="tableCode" label="表编码" />
        <el-table-column fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="editColumn(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="delColumn(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-card shadow="always" class="card">
      <div slot="header" class="clearfix">
        <span>高级搜索</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="openAddSearch">{{ $t('btn.add') }}</el-button>
      </div>
      <el-table :data="searchTableData" border style="width: 100%">
        <el-table-column prop="tableCode" label="表编码" />
        <el-table-column prop="nameValue" label="名称" />
        <el-table-column prop="name" label="编码" />
        <el-table-column prop="type" label="条件类型">
          <template slot-scope="scope">
            <span>{{ types.find(item=>item.value===scope.row.type)?types.find(item=>item.value===scope.row.type).label:'空' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataSource" label="数据源">
          <template slot-scope="scope">
            <span>{{ dataSource.find(item=>item.value===scope.row.dataSource)?dataSource.find(item=>item.value===scope.row.dataSource).label:'空' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="dataSourceValue" label="接口地址" />
        <el-table-column prop="valueType" label="条件值类型">
          <template slot-scope="scope">
            <span>{{ valueType.find(item=>item.value===scope.row.valueType)?valueType.find(item=>item.value===scope.row.valueType).label:'空' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="datePrecision" label="日期精度" />
        <el-table-column fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="openEditSearch(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="delSearch(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 设置列表单 -->
    <el-dialog :visible.sync="setColumnDialog.dialog" :title="setColumnDialog.title" width="600px">
      <el-form ref="setColumnForm" :model="setColumnDialog.form" :rules="setColumnDialog.rules" label-width="100px" label-suffix=":">
        <el-form-item label="表编码" prop="tableCode">
          <el-input v-model.trim="setColumnDialog.form.tableCode" :disabled="!!setColumnDialog.id" :maxlength="100" />
        </el-form-item>
        <el-form-item label="组名" prop="groupName">
          <el-input v-model.trim="setColumnDialog.form.groupName" :maxlength="100" />
        </el-form-item>
        <el-form-item label="列名" prop="name">
          <el-input v-model.trim="setColumnDialog.form.name" :maxlength="100" />
        </el-form-item>
        <el-form-item label="列编码" prop="code">
          <el-input v-model.trim="setColumnDialog.form.code" :maxlength="100" :disabled="!!setColumnDialog.id" />
        </el-form-item>
        <el-form-item label="排序号" prop="sortNo">
          <el-input-number v-model.number="setColumnDialog.form.sortNo" :min="0" />
        </el-form-item>
        <el-form-item label="是否排序" prop="sortable">
          <el-switch v-model="setColumnDialog.form.sortable" :active-value="1" :inactive-value="0" active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item v-if="setColumnDialog.form.sortable===1" label="排序字段" prop="sortCode">
          <el-input v-model.trim="setColumnDialog.form.sortCode" :maxlength="100" />
        </el-form-item>
        <el-form-item label="是否可见" prop="visible">
          <el-switch v-model="setColumnDialog.form.visible" :active-value="1" :inactive-value="0" active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item label="宽度(0为自适应宽度)" prop="width">
          <el-input-number v-model.number="setColumnDialog.form.width" :min="0" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="setColumnDialog.dialog = false">取 消</el-button>
        <el-button type="primary" @click="validateForm('setColumnForm',submitSetColumn)">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 高级搜索 -->
    <el-dialog :visible.sync="setSearchConfig.dialog" :title="setSearchConfig.title" width="600px">
      <el-form ref="setSearchConfigForm" :model="setSearchConfig.form" :rules="setSearchConfig.rules" label-width="100px" label-suffix=":">
        <el-form-item label="表编码" prop="tableCode">
          <el-input v-model.trim="setSearchConfig.form.tableCode" :maxlength="100" />
        </el-form-item>
        <el-form-item label="名称" prop="nameValue">
          <el-input v-model.trim="setSearchConfig.form.nameValue" :maxlength="100" />
        </el-form-item>
        <el-form-item label="编码" prop="name">
          <el-input v-model.trim="setSearchConfig.form.name" :maxlength="100" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="setSearchConfig.form.type" placeholder="请选择" @change="updateSearchType">
            <el-option v-for="item in types" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="setSearchConfig.form.type===2" label="数据源" prop="dataSource">
          <el-select v-model="setSearchConfig.form.dataSource" placeholder="请选择">
            <el-option v-for="item in dataSource" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="setSearchConfig.form.dataSource===1" label="接口地址" prop="dataSourceValue">
          <el-input v-model.trim="setSearchConfig.form.dataSourceValue" :maxlength="100" />
        </el-form-item>
        <el-form-item v-if="setSearchConfig.form.type===3||setSearchConfig.form.type===4||setSearchConfig.form.type===5" label="日期精度" prop="datePrecision">
          <el-select v-model="setSearchConfig.form.datePrecision" placeholder="请选择">
            <el-option v-for="item in datePrecision" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="setSearchConfig.form.type===1" label="值类型" prop="valueType">
          <el-select v-model="setSearchConfig.form.valueType" placeholder="请选择">
            <el-option v-for="item in valueType" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="setSearchConfig.dialog = false">取 消</el-button>
          <el-button type="primary" @click="validateForm('setSearchConfigForm',submitSetSearchConfig)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
      
<script>
import { queryconditionList, queryMenuExtension, addMenuextension, editMenuextension, addCondition, eidtCondition, delCondition, delMenuextension } from '@/api/authority'
const setColumnForm = {
  tableCode: '',
  groupName: '',
  code: '',
  sortCode: '',
  sortNo: '',
  name: '',
  sortable: 0,
  visible: 1,
  width: 0,
}
const setSearchConfigForm = {
  tableCode: '', // 表code
  name: '', // 条件名
  nameValue: '', // 条件值
  type: 1, //  条件类型(1:文本框、2:下拉框、3:日期控件、4:时间控件、5:日期时间控件、6:多记录文本、)
  dataSource: '', // 数据源(1:接口、2:固定内容)(联想控件接口统一格式,code,name)
  dataSourceValue: '', // 联想控件接口地址
  datePrecision: '', // 日期精度
  valueType: '', // 条件值类型(1:字符串,2:数字,3:日期)
}
export default {
  data() {
    return {
      id: this.$route.query.id,
      columnTableData: [],
      searchTableData: [],
      setColumnDialog: {
        dialog: false,
        title: '自定义列',
        id: null,
        form: {},
        rules: {
          sortNo: [{ required: true, message: '请输入排序号', trigger: 'blur' }],
          // sortCode: [{ required: true, message: '请输入排序字段', trigger: 'blur' }],
          tableCode: [{ required: true, message: '请输入表编码', trigger: 'blur' }],
          name: [{ required: true, message: '请输入列名', trigger: 'blur' }],
          code: [{ required: true, message: '请输入列编码', trigger: 'blur' }],
        },
      },
      setSearchConfig: {
        dialog: false,
        title: '高级搜索',
        id: null,
        form: {},
        rules: {
          tableCode: [{ required: true, message: '请输入表编码', trigger: 'blur' }],
          name: [{ required: true, message: '编码', trigger: 'blur' }],
          nameValue: [{ required: true, message: '名称', trigger: 'blur' }],
          dataSource: [{ required: true, message: '请选择数据源', trigger: 'change' }],
          datePrecision: [{ required: true, message: '请选择日期精度', trigger: 'change' }],
          valueType: [{ required: true, message: '请选择条件值类型', trigger: 'change' }],
          dataSourceValue: [{ required: true, message: '联想控件接口地址', trigger: 'blur' }],
        },
      },
      types: [
        {
          label: '文本框',
          value: 1,
        },
        {
          label: '下拉框',
          value: 2,
        },
        {
          label: '日期控件',
          value: 3,
        },
        {
          label: '时间控件',
          value: 4,
        },
        {
          label: '日期时间控件',
          value: 5,
        },
        {
          label: '多记录文本',
          value: 6,
        },
      ],
      valueType: [
        {
          label: '数字',
          value: 2,
        },
        {
          label: '字符串',
          value: 1,
        },
      ],
      dataSource: [
        {
          label: '固定内容',
          value: 2,
        },
        {
          label: '接口',
          value: 1,
        },
      ],
      datePrecision: [
        {
          label: '年',
          value: 'yyyy',
        },
        {
          label: '年-月',
          value: 'yyyy-MM',
        },
        {
          label: '年-月-日',
          value: 'yyyy-MM-dd',
        },
        {
          label: '时',
          value: 'HH',
        },
        {
          label: '时:分',
          value: 'HH:mm',
        },
        {
          label: '时:分:秒',
          value: 'HH:mm:ss',
        },

        {
          label: '年-月-日 时',
          value: 'yyyy-MM-dd HH',
        },
        {
          label: '年-月-日 时:分',
          value: 'yyyy-MM-dd HH:mm',
        },
        {
          label: '年-月-日 时:分:秒',
          value: 'yyyy-MM-dd HH:mm:ss',
        },
      ],
    }
  },
  mounted() {
    this.getColumnData()
    this.getSearchData()
  },
  methods: {
    async getColumnData() {
      const { data, code } = await queryMenuExtension(this.id)
      this.columnTableData = data
    },
    async getSearchData() {
      const { data, code } = await queryconditionList({ menuCode: this.id })
      this.searchTableData = data
    },
    updateSearchType(value) {
      // (1:文本框、2:下拉框、3:日期控件、4:时间控件、5:日期时间控件、6:多记录文本、)
      this.setSearchConfig.form.dataSource = null
      this.setSearchConfig.form.datePrecision = null
      this.setSearchConfig.form.valueType = null
      this.setSearchConfig.form.dataSourceValue = ''
    },
    openAddSearch() {
      this.setSearchConfig.id = null
      this.setSearchConfig.form = { ...setSearchConfigForm }
      this.setSearchConfig.title = '新增高级搜索'
      this.setSearchConfig.dialog = true
    },
    openEditSearch(row) {
      this.setColumnDialog.id = row.id
      const keys = Object.keys(setSearchConfigForm)
      const opt = {}
      for (let i = 0; i < keys.length; i++) {
        const key = keys[i]
        opt[key] = row[key]
      }
      this.setSearchConfig.form = opt
      this.setSearchConfig.id = row.id
      this.setSearchConfig.title = '编辑高级搜索'
      this.setSearchConfig.dialog = true
    },
    editColumn(row) {
      this.setColumnDialog.id = row.id
      const keys = Object.keys(setColumnForm)
      const opt = {}
      for (let i = 0; i < keys.length; i++) {
        const key = keys[i]
        opt[key] = row[key]
      }
      this.setColumnDialog.form = opt
      this.setColumnDialog.title = '编辑自定义列'
      this.setColumnDialog.dialog = true
    },
    async delSearch(row) {
      const { code } = await delCondition(row.id)
      if (code != '200') return
      this.getSearchData()
    },
    async delColumn(row) {
      const { code } = await delMenuextension(row.id)
      if (code != '200') return
      this.getColumnData()
    },
    openAddColumn() {
      this.setColumnDialog.id = null
      this.setColumnDialog.form = { ...setColumnForm }
      this.setColumnDialog.title = '新增自定义列'
      this.setColumnDialog.dialog = true
    },
    validateForm(name, callback) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          if (callback) {
            callback()
          }
        }
      })
    },
    async submitSetColumn() {
      if (this.setColumnDialog.id) {
        const params = {
          ...this.setColumnDialog.form,
          id: this.setColumnDialog.id,
          menuCode: this.id,
        }
        const { data, code } = await editMenuextension(params)
      } else {
        const params = {
          ...this.setColumnDialog.form,
          menuCode: this.id,
        }
        const { data, code } = await addMenuextension(params)
      }
      this.setColumnDialog.dialog = false
      this.getColumnData()
    },
    async submitSetSearchConfig() {
      const setSearchConfig = this.setSearchConfig
      if (setSearchConfig.id) {
        const params = {
          ...setSearchConfig.form,
          id: setSearchConfig.id,
          menuCode: this.id,
        }
        const { data, code } = await eidtCondition(params)
      } else {
        const params = {
          ...setSearchConfig.form,
          menuCode: this.id,
        }
        const { data, code } = await addCondition(params)
      }
      this.setSearchConfig.dialog = false
      this.getSearchData()
    },
  },
}
</script>
<style lang="scss" scoped>
.card {
  margin-bottom: 20px;
}
</style>
      
