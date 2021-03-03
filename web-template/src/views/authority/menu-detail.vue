<template>
  <div class="app-container">
    <el-card shadow="always" class="card">
      <div slot="header" class="clearfix">
        <span>{{ $t('setColumnTable.customColumns') }}}</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="openAddColumn">{{ $t('btn.add') }}</el-button>
      </div>
      <el-table :data="columnTableData" border style="width: 100%">
        <el-table-column prop="tableCode" :label="$t('setColumnTable.tableCode')" />
        <el-table-column prop="name" :label="$t('setColumnTable.name')" />
        <el-table-column prop="code" :label="$t('setColumnTable.code')" />
        <el-table-column prop="width" :label="$t('setColumnTable.width')">
          <template slot-scope="scope">{{ scope.row.width?scope.row.width:$t('table.auto') }}</template>
        </el-table-column>
        <el-table-column prop="sortable" :label="$t('setColumnTable.sortable')">
          <template slot-scope="scope">{{ scope.row.sortable?$t('table.yes'):$t('table.no') }}</template>
        </el-table-column>
        <el-table-column prop="visible" :label="$t('setColumnTable.visible')">
          <template slot-scope="scope">{{ scope.row.visible?$t('table.yes'):$t('table.no') }}</template>
        </el-table-column>
        <el-table-column fixed="right" :label="$t('table.operation')" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="editColumn(scope.row)">{{ $t('table.edit') }}</el-button>
            <el-button type="danger" size="small" @click="delColumn(scope.row)">{{ $t('table.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-card shadow="always" class="card">
      <div slot="header" class="clearfix">
        <span>{{ $t('setSearchConfig.advancedSearch') }}</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="openAddSearch">{{ $t('btn.add') }}</el-button>
      </div>
      <el-table :data="searchTableData" border style="width: 100%">
        <el-table-column prop="tableCode" :label="$t('setSearchConfig.tableCode')" />
        <el-table-column prop="nameValue" :label="$t('setSearchConfig.nameValue')" />
        <el-table-column prop="name" :label="$t('setSearchConfig.name')" />
        <el-table-column prop="type" :label="$t('setSearchConfig.type')">
          <template slot-scope="scope">
            <span>{{ types.find(item=>item.value===scope.row.type)?types.find(item=>item.value===scope.row.type).label:'' }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="dataSource" label="数据源">
          <template slot-scope="scope">
            <span>{{ dataSource.find(item=>item.value===scope.row.dataSource)?dataSource.find(item=>item.value===scope.row.dataSource).label:'空' }}</span>
          </template>
        </el-table-column> -->
        <el-table-column prop="dataSourceValue" :label="$t('setSearchConfig.dataSourceValue')" />
        <el-table-column prop="valueType" :label="$t('setSearchConfig.valueType')">
          <template slot-scope="scope">
            <span>{{ valueType.find(item=>item.value===scope.row.valueType)?valueType.find(item=>item.value===scope.row.valueType).label:'空' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="datePrecision" :label="$t('setSearchConfig.datePrecision')" />
        <el-table-column fixed="right" :label="$t('table.operation')" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="openEditSearch(scope.row)">{{ $t('table.edit') }}</el-button>
            <el-button type="danger" size="small" @click="delSearch(scope.row)">{{ $t('table.delete') }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 设置列表单 -->
    <el-dialog :visible.sync="setColumnDialog.dialog" :title="setColumnDialog.title" width="600px">
      <el-form ref="setColumnForm" :model="setColumnDialog.form" :rules="setColumnDialog.rules" label-width="100px" label-suffix=":">
        <el-form-item :label="$t('setColumnTable.tableCode')" prop="tableCode">
          <el-input v-model.trim="setColumnDialog.form.tableCode" :maxlength="100" />
        </el-form-item>
        <!-- <el-form-item label="组名" prop="groupName">
          <el-input v-model.trim="setColumnDialog.form.groupName" :maxlength="100" />
        </el-form-item> -->
        <el-form-item :label="$t('setColumnTable.name')" prop="name">
          <el-input v-model.trim="setColumnDialog.form.name" :maxlength="100" />
        </el-form-item>
        <el-form-item :label="$t('setColumnTable.code')" prop="code">
          <el-input v-model.trim="setColumnDialog.form.code" :maxlength="100" />
        </el-form-item>
        <el-form-item :label="$t('setColumnTable.sortNo')" prop="sortNo">
          <el-input-number v-model.number="setColumnDialog.form.sortNo" :min="0" />
        </el-form-item>
        <el-form-item :label="$t('setColumnTable.sortable')" prop="sortable">
          <el-switch v-model="setColumnDialog.form.sortable" :active-value="1" :inactive-value="0" active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item v-if="setColumnDialog.form.sortable===1" :label="$t('setColumnTable.sortCode')" prop="sortCode">
          <el-input v-model.trim="setColumnDialog.form.sortCode" :maxlength="100" />
        </el-form-item>
        <el-form-item :label="$t('setColumnTable.visible')" prop="visible">
          <el-switch v-model="setColumnDialog.form.visible" :active-value="1" :inactive-value="0" active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item :label="$t('setColumnTable.width')" prop="width">
          <el-input-number v-model.number="setColumnDialog.form.width" :min="0" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="setColumnDialog.dialog = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="validateForm('setColumnForm',submitSetColumn)">{{ $t('table.ok') }}</el-button>
      </div>
    </el-dialog>
    <!-- 高级搜索 -->
    <el-dialog :visible.sync="setSearchConfig.dialog" :title="setSearchConfig.title" width="600px">
      <el-form ref="setSearchConfigForm" :model="setSearchConfig.form" :rules="setSearchConfig.rules" label-width="100px" label-suffix=":">
        <el-form-item :label="$t('setSearchConfig.tableCode')" prop="tableCode">
          <el-input v-model.trim="setSearchConfig.form.tableCode" :maxlength="100" />
        </el-form-item>
        <el-form-item :label="$t('setSearchConfig.nameValue')" prop="nameValue">
          <el-input v-model.trim="setSearchConfig.form.nameValue" :maxlength="100" />
        </el-form-item>
        <el-form-item :label="$t('setSearchConfig.name')" prop="name">
          <el-input v-model.trim="setSearchConfig.form.name" :maxlength="100" />
        </el-form-item>
        <el-form-item :label="$t('setSearchConfig.type')" prop="type">
          <el-select v-model="setSearchConfig.form.type" :placeholder="$t('table.pleaseSelect')" @change="updateSearchType">
            <el-option v-for="item in types" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <!-- <el-form-item v-if="setSearchConfig.form.type===2" label="数据源" prop="dataSource">
          <el-select v-model="setSearchConfig.form.dataSource" placeholder="请选择">
            <el-option v-for="item in dataSource" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item> -->
        <el-form-item v-if="setSearchConfig.form.dataSource===1" :label="$t('setSearchConfig.dataSourceValue')" prop="dataSourceValue">
          <el-input v-model.trim="setSearchConfig.form.dataSourceValue" :maxlength="100" />
        </el-form-item>
        <el-form-item v-if="setSearchConfig.form.type===3||setSearchConfig.form.type===4||setSearchConfig.form.type===5" :label="$t('setSearchConfig.datePrecision')" prop="datePrecision">
          <el-select v-model="setSearchConfig.form.datePrecision" :placeholder="$t('table.pleaseSelect')">
            <el-option v-for="item in datePrecision" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="setSearchConfig.form.type===1" :label="$t('setSearchConfig.valueType')" prop="valueType">
          <el-select v-model="setSearchConfig.form.valueType" :placeholder="$t('table.pleaseSelect')">
            <el-option v-for="item in valueType" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="setSearchConfig.dialog = false">{{ $t('table.cancel') }}</el-button>
          <el-button type="primary" @click="validateForm('setSearchConfigForm',submitSetSearchConfig)">{{ $t('table.ok') }}</el-button>
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
        title: '',
        id: null,
        form: {},
        rules: {
          sortNo: [{ required: true, message: this.$t('table.pleaseFillIn'), trigger: 'blur' }],
          tableCode: [{ required: true, message: this.$t('table.pleaseFillIn'), trigger: 'blur' }],
          name: [{ required: true, message: this.$t('table.pleaseFillIn'), trigger: 'blur' }],
          code: [{ required: true, message: this.$t('table.pleaseFillIn'), trigger: 'blur' }],
        },
      },
      setSearchConfig: {
        dialog: false,
        title: '',
        id: null,
        form: {},
        rules: {
          tableCode: [{ required: true, message: this.$t('table.pleaseSelect'), trigger: 'blur' }],
          name: [{ required: true, message: this.$t('table.pleaseFillIn'), trigger: 'blur' }],
          nameValue: [{ required: true, message: this.$t('table.pleaseFillIn'), trigger: 'blur' }],
          dataSource: [{ required: true, message: this.$t('table.pleaseSelect'), trigger: 'change' }],
          datePrecision: [{ required: true, message: this.$t('table.pleaseSelect'), trigger: 'change' }],
          valueType: [{ required: true, message: this.$t('table.pleaseSelect'), trigger: 'change' }],
          dataSourceValue: [{ required: true, message: this.$t('table.pleaseFillIn'), trigger: 'blur' }],
        },
      },
      types: [
        {
          label: this.$t('setSearchConfig.input'),
          value: 1,
        },
        {
          label: this.$t('setSearchConfig.sclect'),
          value: 2,
        },
        {
          label: this.$t('setSearchConfig.dateInput'),
          value: 3,
        },
        {
          label: this.$t('setSearchConfig.timeInput'),
          value: 4,
        },
        {
          label: this.$t('setSearchConfig.dateTimeInput'),
          value: 5,
        },
        {
          label: this.$t('setSearchConfig.textarea'),
          value: 6,
        },
      ],
      valueType: [
        {
          label: this.$t('setSearchConfig.number'),
          value: 2,
        },
        {
          label: this.$t('setSearchConfig.string'),
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
          label: this.$t('setSearchConfig.y'),
          value: 'yyyy',
        },
        {
          label: this.$t('setSearchConfig.yM'),
          value: 'yyyy-MM',
        },
        {
          label: this.$t('setSearchConfig.yMd'),
          value: 'yyyy-MM-dd',
        },
        {
          label: this.$t('setSearchConfig.H'),
          value: 'HH',
        },
        {
          label: this.$t('setSearchConfig.Hm'),
          value: 'HH:mm',
        },
        {
          label: this.$t('setSearchConfig.Hms'),
          value: 'HH:mm:ss',
        },
        {
          label: this.$t('setSearchConfig.yMdH'),
          value: 'yyyy-MM-dd HH',
        },
        {
          label: this.$t('setSearchConfig.yMdHm'),
          value: 'yyyy-MM-dd HH:mm',
        },
        {
          label: this.$t('setSearchConfig.yMdHms'),
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
      if (value === 2) {
        this.setSearchConfig.form.dataSource = 1
      } else {
        this.setSearchConfig.form.dataSource = null
      }
      this.setSearchConfig.form.datePrecision = null
      this.setSearchConfig.form.valueType = null
      this.setSearchConfig.form.dataSourceValue = ''
    },
    openAddSearch() {
      this.setSearchConfig.id = null
      this.setSearchConfig.form = { ...setSearchConfigForm }
      this.setSearchConfig.title = this.$t('table.add')
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
      this.setSearchConfig.title = this.$t('table.edit')
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
      this.setColumnDialog.title = this.$t('table.edit')
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
      this.setColumnDialog.title = this.$t('table.edit')
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
      
