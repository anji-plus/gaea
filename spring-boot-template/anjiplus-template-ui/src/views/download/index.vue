<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="8">
              <el-form-item label="文件标题" prop="fileTitle">
                <el-input v-model="searchForm.fileTitle" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button
            type="primary"
            @click="
              searchForm.pageNumber = 1
              getData()
            "
          >{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="resetForm('formSearch')">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-table :data="tableList" border>
      <el-table-column label="文件标题" prop="fileTitle" align="center" min-width="180" />
      <el-table-column label="文件描述" align="center" min-width="300">
        <template slot-scope="scope"> 数据范围:{{ scope.row.resultStartTime }} ~ {{ scope.row.resultEndTime }}，累计{{ scope.row.resultSize }}条数据 </template>
      </el-table-column>
      <el-table-column prop="fileStatus" label="文件状态" align="center" width="100" />
      <el-table-column prop="fileCreateTime" label="文件创建时间" align="center" width="160" />
      <el-table-column prop="fileFinishTime" label="文件导出时间" align="center" width="160" />
      <el-table-column prop="createBy" :label="$t('userManage.creator')" align="center" min-width="160" />
      <el-table-column label="操作" align="center" width="80">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="下载" placement="top">
            <el-button :circle="true" :plain="true" type="success" icon="el-icon-download" size="mini" @click="downloadFile(scope.row)" />
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNumber" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
  </div>
</template>
<script>
import { getList, download } from '@/api/download'
import { downloadBlob } from '@/utils'
export default {
  data() {
    return {
      selectedList: [],
      searchForm: {
        fileTitle: null,
        pageNumber: 1,
        pageSize: 10,
      },
      tableList: [],
      total: 0,
    }
  },
  methods: {
    async downloadFile(row) {
      const res = await download(row.fileId)
      if (!res) return
      downloadBlob(res, row.fileTitle)
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.tableList = []
      this.total = 0
    },
    // 查询
    async getData() {
      const { data, code } = await getList(this.searchForm)
      if (code != '200') return
      this.tableList = data.records
      this.total = data.total
    },
    // 页码改变
    handleCurrentChange(pageNumber) {
      this.searchForm.pageNumber = pageNumber
      this.getData()
    },
    // 每页size改变时
    handleSizeChange(val) {
      this.searchForm.pageNumber = 1
      this.searchForm.pageSize = val
      this.getData()
    },
  },
}
</script>
