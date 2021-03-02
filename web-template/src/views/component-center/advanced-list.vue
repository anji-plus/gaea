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
            <el-button plain @click="openAdvancedSearch">{{ $t('btn.advancedSearch') }}</el-button>
            <el-button plain type="primary" @click="openCommonSearch">{{ $t('btn.commonSearch') }}</el-button>
          </el-col>
        </el-col>
      </el-row>
    </el-form>
    <div class="buttonList">
      <div class="list">
        <el-button type="primary" size="small">按钮一</el-button>
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
    <el-dialog :visible.sync="setTabelDialog.dialog" width="800px" title="自定义列" @closed="closedSetTabel">
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
              <span>宽度</span>
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
        <el-button @click="setTabelDialog.dialog = false">取 消</el-button>
        <el-button type="primary" @click="submitTabelConfig">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { queryTableColumn, queryconditionList, queryAdvanceExport, saveUserMenuExtensionsBatchSave } from '@/api/advanced'
import Sortable from 'sortablejs'

export default {
  data() {
    return {
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
  },
  methods: {
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
    openCustomColumns() {},
    openAdvancedSearch() {},
    openCommonSearch() {},
    queryColumns() {
      queryTableColumn({ menuCode: this.tableConfig.menuCode, tableCode: this.tableConfig.tableCode }).then((res) => {
        console.log(res.data)
        this.columns = res.data || []
        this.getData()
      })
    },
    getData() {
      const params = {
        pageNumber: this.pagination.pageNumber,
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
</style>
