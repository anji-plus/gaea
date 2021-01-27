<template>
  <div>
    <el-card style="margin-top: 10px">
      <div slot="header" class="flex_class">
        <span>{{ $t('userManage.roleInfo') }}</span>
      </div>
      <el-table ref="multipleTable" :row-key="getRowKeys" :header-cell-class-name="cellClass" :data="tableList" @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" :selectable="selectable" width="50" center :reserve-selection="true" />
        <el-table-column prop="roleCode" :label="$t('userManage.roleId')" align="center" />
        <el-table-column prop="roleName" :label="$t('userManage.roleName')" align="center" />
        <el-table-column prop="statusText" :label="$t('userManage.status')" align="center" />
        <el-table-column prop="descript" :label="$t('userManage.described')" align="center" />
      </el-table>
    </el-card>
  </div>
</template>
<script>
import { roleList } from '@/api/authority'
export default {
  props: {
    roles: {
      require: true,
      default: () => {
        return []
      },
      type: Array,
    },
    disabled: {
      require: true,
      default: 'view',
      type: String,
    },
  },
  data() {
    return {
      selectedList: [],
      searchForm: {
        isValid: 1,
        type: '',
        pageSize: 1000,
        pageNum: 1,
      },
      tableList: [],
      getRowKeys(row) {
        return row.pkId
      },
      selectable: (row, index) => {
        return this.disabled != 'view'
      },
      cellClass: (row) => {
        if (row.columnIndex === 0 && this.disabled == 'view') {
          return 'disabledCheck'
        }
      },
    }
  },
  created() {
    this.getData()
  },
  updated() {
    var roleList = []
    this.tableList.forEach((item) => {
      if (this.roles.includes(item.pkId)) {
        roleList.push(item)
        this.$refs.multipleTable.toggleRowSelection(item, true)
      }
    })
    this.selectedList = roleList
  },
  methods: {
    // 查询
    getData() {
      roleList(this.searchForm).then((res) => {
        if (res.code == '2000') {
          this.tableList = res.data.list
          return
        }
      })
    },
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
    },
  },
}
</script>
<style lang="scss">
.el-table .disabledCheck .cell .el-checkbox__inner {
  display: none !important;
}
</style>
