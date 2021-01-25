<template>
  <el-dialog :title="$t('userManage.roleManage')" width="80%" :close-on-click-modal="false" center :visible.sync="visib" :before-close="closeDialog">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="8">
              <el-form-item :label="$t('userManage.type')" prop="type">
                <el-select v-model="searchForm.type" :placeholder="$t('placeholder.select')">
                  <el-option v-for="item in permissionTypes" :key="item.bizCode" :label="item.bizCode" :value="item.bizCode" />
                </el-select>
              </el-form-item>
            </el-col>
            <!-- <el-col :span="8">
              <el-form-item :label="$t('businessGlossary.isValid')" prop="isValid">
                <el-select v-model="searchForm.isValid" :placeholder="$t('placeholder.select')">
                  <el-option :key="0" :label="$t('businessGlossary.no')" :value="0" />
                  <el-option :key="1" :label="$t('businessGlossary.yes')" :value="1" />
                </el-select>
              </el-form-item>
            </el-col> -->
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
    <el-table :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column fixed type="selection" width="40" center />
      <el-table-column prop="type" :label="$t('userManage.type')" align="center" min-width="180" />
      <el-table-column prop="typeName" :label="$t('userManage.typeName')" align="center" min-width="180" />
      <el-table-column prop="name" :label="$t('userManage.name')" align="center" min-width="180" />
      <el-table-column prop="value" :label="$t('userManage.value')" align="center" min-width="180" />
      <el-table-column prop="attribute" :label="$t('userManage.attribute')" align="center" min-width="180" />
      <el-table-column prop="isValid" :label="$t('businessGlossary.isValid')" align="center" min-width="160">
        <template slot-scope="scope">
          {{ $t(`businessGlossary.${scope.row.isValid ? 'yes' : 'no'}`) }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" :label="$t('userManage.creationTime')" align="center" min-width="180" />
      <el-table-column prop="createUser" :label="$t('userManage.creator')" align="center" min-width="150" />
      <el-table-column prop="modifyTime" :label="$t('userManage.modifyTime')" align="center" min-width="140" />
      <el-table-column prop="modifyUser" :label="$t('userManage.modifyUser')" align="center" min-width="180" />
    </el-table>
    <el-pagination v-show="total > 0" small background :current-page.sync="searchForm.pageNum" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <div slot="footer" style="text-align: center">
      <el-button type="primary" :disabled="selectedList.length == 0" plain @click="UserConfirm">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="closeDialog">{{ $t('btn.close') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getDictList } from '@/api/dict-data'
import { getPermissionList } from '@/api/user-management'
export default {
  props: {
    visib: {
      required: true,
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      permissionTypes: [],
      selectedList: [],
      searchForm: {
        isValid: 1,
        type: '',
        pageSize: 10,
        pageNum: 1,
      },
      tableList: [],
      total: 0,
    }
  },
  created() {
    this.getTypes()
  },

  methods: {
    getTypes() {
      getDictList('permission_type').then((res) => {
        if (res.code == '2000') {
          this.permissionTypes = res.data
          return
        }
      })
    },
    UserConfirm() {
      this.resetForm('formSearch')
      this.$emit('handleClose', this.selectedList)
    },
    closeDialog() {
      this.resetForm('formSearch')
      this.$emit('handleClose', [])
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.tableList = []
      this.total = 0
    },
    // 查询
    getData() {
      getPermissionList(this.searchForm).then((res) => {
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
  },
}
</script>
