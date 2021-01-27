<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="8">
              <el-form-item :label="$t('userManage.loginId')" prop="userId">
                <el-input v-model="searchForm.userId" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item :label="$t('userManage.loginResult')" prop="loginResult">
                <el-input v-model="searchForm.loginResult" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item :label="$t('userManage.loginStartTime')" prop="loginStartTime">
                <el-date-picker v-model="searchForm.loginStartTime" type="datetime" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" :placeholder="$t('placeholder.select')" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item :label="$t('userManage.loginEndTime')" prop="loginEndTime">
                <el-date-picker v-model="searchForm.loginEndTime" type="datetime" style="width: 100%" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" :placeholder="$t('placeholder.select')" />
              </el-form-item>
            </el-col>
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
    <el-table :data="tableList" border>
      <el-table-column prop="userId" :label="$t('userManage.loginId')" align="center" min-width="160" />
      <el-table-column prop="userAgent" :label="$t('userManage.userAgent')" align="center" min-width="160" />
      <el-table-column prop="loginTime" :label="$t('userManage.loginTime')" align="center" min-width="160" />
      <el-table-column prop="loginResultText" :label="$t('userManage.loginResult')" align="center" min-width="180" />
      <el-table-column prop="clientType" :label="$t('userManage.clientType')" align="center" min-width="160" />
      <el-table-column prop="clientIp" :label="$t('userManage.clientIp')" align="center" min-width="150" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNum" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
  </div>
</template>
<script>
import { log } from '@/api/system-set'
export default {
  data() {
    return {
      searchForm: {
        userId: '', // 用户账号
        loginResult: '',
        loginStartTime: '',
        loginEndTime: '',
        pageNum: 1,
        pageSize: 10,
      },
      tableList: [],
      total: 0,
    }
  },
  methods: {
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.tableList = []
      this.total = 0
    },
    // 查询
    getData() {
      log(this.searchForm).then((res) => {
        if (res.code == '2000') {
          this.tableList = res.data.list
          this.total = res.data.total
          return
        }
      })
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
