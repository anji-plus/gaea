<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item label="test01" prop="test01">
                <el-input v-model="searchForm.test01" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="test02" prop="test02">
                <el-input v-model="searchForm.test02" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="test03" prop="test03">
                <el-input v-model="searchForm.test03" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="test04" prop="test04">
                <el-input v-model="searchForm.test04" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="test05" prop="test05">
                <el-input v-model="searchForm.test05" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-col :span="24" style="margin-bottom:18px;">
            <el-button
              type="primary"
              @click="
                searchForm.pageNumber = 1
                getData()
              "
            >{{ $t('btn.query') }}</el-button>
            <el-button type="danger" @click="resetForm('formSearch')">{{ $t('btn.reset') }}</el-button>
          </el-col>
          <el-col :span="24">
            <el-dropdown>
              <el-button type="primary" class="buttonItem btnSpan">{{ $t('btn.commonSearch') }}</el-button>
              <el-dropdown-menu slot="dropdown">
                <!-- icon="el-icon-delete" -->
                <el-dropdown-item v-for="(o,index) in commonList" :key="index" :command="o.id">
                  <div class="dropdown-item">
                    <i class="icon el-icon-delete" @click.stop="delItemCommon(o)" />
                    <el-link class="link" type="info" @click.stop="openCommonSearch(o)">{{ o.searchName }}</el-link>
                  </div>
                </el-dropdown-item>
                <el-dropdown-item command="add">
                  <div class="dropdown-item">
                    <i class="icon el-icon-circle-plus-outline" />
                    <el-link class="link" type="primary" @click.stop="openAdvancedSearch('commonSearch')">{{ $t('setSearchConfig.newQuery') }}</el-link>
                  </div>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-button plain @click="openAdvancedSearch">{{ $t('btn.advancedSearch') }}</el-button>
          </el-col>
        </el-col>
      </el-row>
    </el-form>
    <div class="buttonList">
      <div class="list">
        <el-button type="primary" size="small">button</el-button>
      </div>
      <div class="config">
        <el-button plain type="primary" @click="openSetColumn">{{ $t('btn.customColumns') }}</el-button>
      </div>
    </div>
    <el-table v-if="tableConfig.show" v-loading="tableConfig.loading" :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <template v-for="item in columns">
        <el-table-column v-if="item.visible" :key="item.code" :label="item.name" :prop="item.code" :width="item.width||'auto'" align="center" />
      </template>
    </el-table>
    <el-pagination
      v-show="pagination.total > 0"
      background
      :current-page.sync="pagination.pageNumber"
      :page-sizes="$pageSizeAll"
      :page-size="pagination.pageSize"
      layout="total, prev, pager, next, jumper, sizes"
      :total="pagination.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <!-- 自定义列弹窗 -->
    <el-dialog :visible.sync="setTabelDialog.dialog" width="800px" :title="$t('setColumnTable.customColumns')" @closed="closedSetTabel">
      <div v-if="setTabelDialog.show" ref="dragBody" class="dragBody">
        <div ref="dragTable" class="dragTable">
          <div v-for="item in setTabelDialog.list" :key="item.code" class="item" :data-code="item.code">
            <div class="checkbox">
              <el-checkbox v-model="item.visible" :true-label="1" false-label="0" />
            </div>
            <div class="name">
              <span class="label">{{ item.name }}</span>
              <span class="bg" :style="{width: (item.widthStr/setTabelDialog.max*100 +'%')}" />
            </div>
            <div class="width">
              <span>{{ $t('setColumnTable.width') }}</span>
              <div class="input">
                <el-input v-model="item.width" placeholder="auto">
                  <template slot="append">px</template>
                </el-input>
              </div>
            </div>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setTabelDialog.dialog = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="submitTabelConfig">{{ $t('table.ok') }}</el-button>
      </span>
    </el-dialog>
    <!-- 高级搜索弹窗 -->
    <el-dialog :title="advanced.title" :visible.sync="advanced.dialog" width="600px">
      <div class="main">
        <el-form
          v-if="advanced.type==='commonSearch'"
          ref="baseQueryForm"
          :model="advanced"
          :rules="{
            name: [
              { required: true, message: $t('table.pleaseFillIn'), trigger: 'blur' },
              { max: 12, message: $t('table.maxLength12'), trigger: 'blur' }
            ]}"
          label-width="50px"
          class="demo-ruleForm"
        >
          <el-form-item :label="$t('setSearchConfig.label')" prop="name">
            <el-input v-model="advanced.name" maxlength="12" show-word-limit />
          </el-form-item>
        </el-form>
        <div class="list baseQueryItems">
          <el-row v-for="(row,idx) in advanced.addConfig" :key="idx" class="item" type="flex" align="middle">
            <el-col :span="8">
              <el-select v-model="row.prop" value-key="id" :placeholder="$t('table.pleaseSelect')" @change="updateAdvancedOption($event,advanced.addConfig[idx])">
                <el-option v-for="item in advanced.templateOption" :key="item.id" :label="item.nameValue" :value="item" />
              </el-select>
            </el-col>
            <el-col :span="5">
              <el-select v-model="row.operator" :placeholder="$t('table.pleaseSelect')">
                <el-option v-for="item in advanced.options" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-col>
            <el-col :span="8">
              <!-- // type: 1, //  条件类型(1:文本框、2:下拉框、3:日期控件、4:时间控件、5:日期时间控件、6:多记录文本、) -->
              <el-input v-if="!row.prop" v-model="row.value" class="formItem" :placeholder="$t('table.pleaseFillIn')" />
              <el-select v-else-if="row.prop.type===2" v-model="row.value" class="formItem" clearable :placeholder="$t('table.pleaseSelect')" @change="updateValueName($event,row)">
                <el-option v-for="item in row.option" :key="item.id" :label="item.text" :value="item.id">
                  <span style="float: left">{{ item.id }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.text }}</span>
                </el-option>
              </el-select>
              <el-date-picker v-else-if="row.prop.type===3" v-model="row.value" class="formItem" :value-format="row.prop.datePrecision" :format="row.prop.datePrecision" type="date" :placeholder="$t('table.pleaseSelect')" />
              <el-time-select v-else-if="row.prop.type===4" v-model="row.value" class="formItem" :value-format="row.prop.datePrecision" :format="row.prop.datePrecision" :placeholder="$t('table.pleaseSelect')" />
              <el-date-picker v-else-if="row.prop.type===5" v-model="row.value" class="formItem" :value-format="row.prop.datePrecision" :format="row.prop.datePrecision" type="datetime" :placeholder="$t('table.pleaseSelect')" />
              <el-input v-else-if="row.prop.type===6" v-model="row.value" class="formItem" type="textarea" :placeholder="$t('table.pleaseFillIn')" @focus="getHigh($event)" @blur="getNormal($event)" />
              <el-input v-else v-model="row.value" :placeholder="$t('table.pleaseFillIn')" class="formItem" />
            </el-col>
            <el-col v-if="idx!==0" :span="3">
              <i class="el-icon-delete" :title="$t('table.delete')" :style="{color:'red', fontSize: '20px',margin: '0 10px'}" @click="delAdvancedItem(idx)" />
            </el-col>
          </el-row>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addAdvancedQuery">{{ $t('setSearchConfig.addItem') }}</el-button>
        <el-button type="primary" @click="saveOtherSearch">{{ $t('table.ok') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { queryTableColumn, queryconditionList, queryAdvanceExport, saveUserMenuExtensionsBatchSave, queryDictSelect, queryCommonCondition, 
  saveCommonCondition, deleteCommonCondition } from '@/api/advanced'
import Sortable from 'sortablejs'

export default {
  data() {
    return {
      visible: false,
      advancedSearchForm: [],
      commonList: [],
      commonId: null,
      advanced: {
        dialog: false,
        name: '',
        type: '',
        title: '',
        templateOption: [],
        template: {
          prop: null,
          option: [],
          valueName: '',
          queryApiOption: this.queryApiOption,
          operator: 'EQ',
          value: '',
        },
        addConfig: [],
        options: [
          {
            value: 'EQ',
            label: this.$('setSearchConfig.EQ'),
          },
          {
            value: 'NE',
            label: this.$('setSearchConfig.NE'),
          },
          {
            value: 'GT',
            label: this.$('setSearchConfig.GT'),
          },
          {
            value: 'GE',
            label: this.$('setSearchConfig.GE'),
          },
          {
            value: 'LT',
            label: this.$('setSearchConfig.LT'),
          },
          {
            value: 'LE',
            label: this.$('setSearchConfig.ELEQ'),
          },
          {
            value: 'LIKE',
            label: this.$('setSearchConfig.LIKE'),
          },
          {
            value: 'IN',
            label: this.$('setSearchConfig.IN'),
          },
        ],
        list: [],
      },
      pagination: {
        total: 0,
        pageNumber: 1,
        pageSize: 10,
      },
      setTabelDialog: {
        dialog: false,
        show: false,
        max: 0,
        list: [],
      },
      columns: [],
      tableConfig: {
        menuCode: 1118,
        tableCode: 'test01',
        show: true,
        loading: false,
      },
      selectedList: [],
      tableList: [],
      searchForm: {
        test01: null,
        test02: null,
        test03: null,
        test04: null,
        test05: null,
      },
    }
  },
  mounted() {
    this.queryColumns()
    this.queryCommonCondition()
  },
  methods: {
    delItemCommon(o) {
      deleteCommonCondition(o.id).then((res) => {
        this.queryCommonCondition()
      })
    },
    updateValueName(val, row) {
      row.valueName = row.option.find((item) => item.id === val).text
    },
    getHigh(el) {
      el.target.style.height = '100px'
      el.target.style.width = '200%'
      el.target.style.position = 'absolute'
    },
    getNormal(el) {
      // const str = el.target.value
      el.target.style.height = '100%'
      el.target.style.width = '100%'
      el.target.style.position = 'relative'
    },
    updateAdvancedOption(data, item) {
      if (data.type === 2) {
        queryDictSelect({ url: data.dataSourceValue }).then((res) => {
          item.option = res.data || []
        })
      }
      item.value = null
    },
    searchVariousQuery(params) {
      if (!this.advanced.name) {
        this.$message.error(this.$t('setSearchConfig.pleaseFillInName'))
        return false
      }
      const data = {
        commonConditionReqBOList: params,
        menuCode: this.tableConfig.menuCode,
        tableCode: this.tableConfig.tableCode,
        searchName: this.advanced.name,
      }
      saveCommonCondition(data).then((res) => {
        this.queryCommonCondition()
        this.advanced.dialog = false
      })
    },
    saveOtherSearch() {
      const params = this.advanced.addConfig
        .filter((item) => item.prop && item.value)
        .map((item) => {
          return {
            name: item.prop.name,
            value: item.value,
            type: item.prop.type,
            valueName: item.valueName,
            valueType: item.prop.valueType,
            datePrecision: item.prop.datePrecision,
            operator: item.operator,
          }
        })
      if (!params.length) {
        this.$message.error(this.$t('table.selectAtLeastOne'))
        return
      }
      if (this.advanced.type === 'advancedSearch') {
        this.advancedSearchForm = params
        this.getData()
        this.advanced.dialog = false
        // console.log()
      } else {
        this.searchVariousQuery(params)
      }
    },
    addAdvancedQuery() {
      const data = {
        ...this.advanced.template,
      }
      this.advanced.addConfig.push(data)
    },
    delAdvancedItem(index) {
      console.log(index)
      this.advanced.addConfig.splice(index, 1)
    },
    submitTabelConfig() {
      const arr = this.$refs.dragTable.querySelectorAll('.item')
      const list = this.setTabelDialog.list
      const columns = []
      arr.forEach((el) => {
        const code = el.getAttribute('data-code')
        const find = list.find((item) => item.code === code)
        columns.push(find)
      })
      saveUserMenuExtensionsBatchSave(columns).then((res) => {
        this.setTabelDialog.dialog = false
        this.tableConfig.loading = true
        this.tableConfig.show = false
        this.columns = columns
        setTimeout(() => {
          this.tableConfig.show = true
          this.tableConfig.loading = false
        }, 300)
      })
    },
    closedSetTabel() {
      this.sortable = null
      this.setTabelDialog.show = false
      this.setTabelDialog.max = 0
      this.setTabelDialog.list = []
    },
    setSort() {
      console.log(this.$refs.dragTable)
      if (!this.$refs.dragTable || this.sortable) {
        return false
      }
      const el = this.$refs.dragTable
      this.sortable = Sortable.create(el, {
        ghostClass: 'sortable-ghost', //  Class name for the drop placeholder,
        setData: function(dataTransfer) {},
        onEnd: (evt) => {},
      })
    },
    openSetColumn() {
      const list = this.columns
      const max = Math.max(...list.map((item) => item.width || 0))
      const columns = list.map((item) => {
        return {
          ...item,
          widthStr: item.width || 0,
          width: item.width || 0,
        }
      })
      this.setTabelDialog.max = max
      this.setTabelDialog.list = columns
      this.setTabelDialog.show = true
      this.setTabelDialog.dialog = true
      this.$nextTick(() => {
        this.setSort()
      })
    },
    initAdvancedSearch(type = 'advancedSearch') {
      this.advanced.title = this.$t('btn.advancedSearch')
      this.advanced.type = type
      this.advanced.name = ''
      this.advanced.addConfig = [
        {
          ...this.advanced.template,
        },
      ]
      this.advanced.dialog = true
    },
    openAdvancedSearch(type) {
      if (!this.advanced.templateOption.length) {
        queryconditionList({ menuCode: this.tableConfig.menuCode, tableCode: this.tableConfig.tableCode }).then((res) => {
          this.advanced.templateOption = res.data || []
          this.initAdvancedSearch(type)
        })
      } else {
        this.initAdvancedSearch(type)
      }
    },
    queryCommonCondition() {
      queryCommonCondition({ menuCode: this.tableConfig.menuCode, tableCode: this.tableConfig.tableCode }).then((res) => {
        this.commonList = res.data || []
      })
    },
    openCommonSearch(o) {
      this.commonId = o.id
      this.getData()
    },
    queryColumns() {
      queryTableColumn({ menuCode: this.tableConfig.menuCode, tableCode: this.tableConfig.tableCode }).then((res) => {
        this.columns = res.data || []
        this.getData()
      })
    },
    getData() {
      const params = {
        dynamicQueryBos: this.advancedSearchForm,
        pageNumber: this.pagination.pageNumber,
        commonId: this.commonId ? this.commonId : '',
        pageSize: this.pagination.pageSize,
      }
      this.tableConfig.loading = true
      queryAdvanceExport(params).then((res) => {
        this.tableList = res.data.records || []
        this.pagination.total = res.data.total
        this.pagination.pageNumber = res.data.current
        this.tableConfig.loading = false
      })
    },
    resetForm() {},
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
    },
    // 页码改变
    handleCurrentChange(pageNumber) {
      this.pagination.pageNumber = pageNumber
      this.getData()
    },
    // 每页size改变时
    handleSizeChange(val) {
      this.pagination.pageNumber = 1
      this.pagination.pageSize = val
      this.getData()
    },
  },
}
</script>
<style lang="scss" scoped>
.buttonList {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-top: 1px solid #dfe6ec;
}
.dragTable {
  .item:first-child {
    border-top: 1px solid #dddddd;
  }
  .item {
    display: flex;
    align-items: center;
    height: 40px;
    border: 1px solid #dddddd;
    border-top: none;
    padding: 0 10px;
    user-select: none;
  }
  .name {
    margin: 0 20px;
    padding: 0 10px;
    flex: 1;
    display: flex;
    align-items: center;
    height: 100%;
    position: relative;
    .label {
      position: relative;
      z-index: 2;
      color: #333;
    }
    .bg {
      width: 100%;
      height: 100%;
      position: absolute;
      z-index: 1;
      background: #f8f8f8;
      width: 1%;
      left: 0;
      top: 0;
    }
  }
  .width {
    display: flex;
    align-items: center;
    .input {
      width: 120px;
      margin-left: 10px;
    }
  }
}
.formItem {
  display: block;
  width: 100%;
}
.dropdown-item{
  display: flex;
  align-items: center;
  .icon{
    margin-right: 5px;
  }
}
</style>
