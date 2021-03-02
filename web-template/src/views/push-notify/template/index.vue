<template>
  <div class="app-container">
    <el-form ref="form" :label-position="labelPosition" :model="form" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col :span="6">
              <el-form-item label="推送类型">
                <el-select v-model="form.templateType" :placeholder="$t('placeholder.select')">
                  <el-option v-for="item in pushTypeData" :key="item.id" :label="item.text" :value="item.id" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="搜索关键字">
                <el-input v-model="form.field" placeholder="请输入搜索字段" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="创建日期">
                <el-date-picker v-model="timeSelectedValue" type="daterange" align="left" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" style="text-align: center">
          <el-button type="primary" @click="queryByPage">{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="Reset">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-button type="primary" icon="el-icon-plus" @click="addOrEditMailDemo(undefined)">{{ $t('btn.add') }}</el-button>
    <el-table stripe border style="width: 100%" :data="tableData">
      <el-table-column width="90" label="序号" align="center">
        <template
          slot-scope="scope"
        ><span>{{ scope.$index + (pageNum - 1) * pageSize + 1 }} </span></template>
      </el-table-column>
      <el-table-column prop="templateName" label="模板名称" align="left" header-align="center" :show-overflow-tooltip="true" />
      <el-table-column label="推送类型" width="90">
        <template slot-scope="scope">
          {{ scope.row.templateType | filterPushType }}
          <!-- {{getDictCode('ALERT_CHANNEL',scope.row.templateType,"labelEng").label}} -->
        </template>
      </el-table-column>
      <el-table-column prop="templateCode" label="模板代码" align="center" width="160" />
      <el-table-column prop="templateParam" label="模板参数" align="center" :show-overflow-tooltip="true" />
      <el-table-column prop="createBy" label="创建者" align="center" width="120" />
      <el-table-column prop="createTime" :formatter="dateFormat" align="center" label="创建时间" width="160" />
      <el-table-column prop="updateTime" :formatter="dateFormat" align="center" label="修改时间" width="160" />

      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-tooltip v-if="scope.row.templateType == 'sms'" class="item" effect="dark" content="短信测试" placement="top">
            <el-button size="mini" type="warning" icon="el-icon-chat-dot-square" :plain="true" circle @click="testSms(scope.row)" />
          </el-tooltip>
          <el-tooltip v-else class="item" effect="dark" content="查看" placement="top">
            <el-button size="mini" icon="el-icon-search" :plain="true" circle @click="Preview(scope.row)" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button size="mini" type="success" icon="el-icon-view" :plain="true" circle @click="queryOrEditMailDemo(scope.$index, scope.row)" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button type="primary" size="mini" icon="el-icon-edit" :plain="true" circle @click="addOrEditMailDemo(scope.$index, scope.row)" />
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button type="danger" size="mini" icon="el-icon-delete" :plain="true" circle @click="DemoDel(scope.row.id)" />
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="fr" :current-page="currentPage4" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <div v-if="isModalTrueVisible">
      <Modal v-show="isModalVisible" ref="Modal" :status-text="statusText" content-text="index.vue" sure="确定" alert="1" @closeModal="closeModal">
        <Edit :pro-data="proData" :template-deploy-type="templateDeployType" :is-find="isFind" @closeModal="closeModal" />
      </Modal>
    </div>
    <div v-if="isModalTrueMailDemo">
      <Modal v-show="isModalMailDemo" ref="Modal" status-text="模板详情" content-text="index.vue" sure="确定" alert="1" @closeModal="closeModal">
        <Details :template-details="templateDetails" @closeModal="closeModal" />
      </Modal>
    </div>
    <div v-if="isTsetSms">
      <Modal v-show="isModalTsetSms" ref="Modal" status-text="模板查看" content-text="index.vue" sure="确定" alert="1" @closeModal="closeModal">
        <SMSTest :pro-test-sms-data="proTestSmsData" :template-deploy-type="templateDeployType" @closeModal="closeModal" />
      </Modal>
    </div>
  </div>
</template>

<script>
import { dataDictionary } from '@/api/common'
import { gaeaPushTemplateDelect, gaeaPushTemplateSearch } from '@/api/push-notify'
import Modal from './component/Modal.vue'
import Edit from './component/Modal-edit'
import Details from './component/Modal-details'
import SMSTest from './component/Modal-SMSTest'
// import {queryTemplateList,deleteTemplate,showTemplateById} from '@/api/push/notify';
// import parseTime from '@/utils/index'
import moment from 'moment'

var typeData
// import miment from 'miment'
// import CodeSelect from '@/components/codeSelect'
export default {
  name: 'History',
  components: {
    Modal,
    Edit,
    Details,
    SMSTest,
  },
  filters: {
    filterPushType(val) {
      for (var i = 0; i < typeData.pushTypeData.length; i++) {
        if (typeData.pushTypeData[i].id == val) {
          return typeData.pushTypeData[i].text
        }
      }
    },
  },
  data() {
    return {
      // 推送类型
      pushTypeData: [],
      currentPage4: 1,
      total: 0,
      page: 1,
      pageSize: 10,
      pageNum: 1,
      labelPosition: 'right',
      statusText: '添加模板',
      timeSelectedValue: null, // 时间选择器的value
      form: {
        field: '', // 搜索字段
        templateType: '',
      },
      proData: null,
      isModalVisible: false, // 显示隐藏新增编辑弹出框
      editFlag: true, // 新增编辑判断
      tableData: [], // 模板列表
      isModalMailDemo: false, // 显示隐藏模板弹出框
      templateDetails: {}, // 模板查看的详情
      isModalTrueVisible: false,
      isModalTrueMailDemo: false,
      beginDate: '',
      endDate: '',

      // 判断 类型
      mailTo: '收件人',
      isTsetSms: false,
      isModalTsetSms: false,
      proTestSmsData: null,
      templateType: '',
      templateDeployType: '',
      isFind: true,
    }
  },
  // 在生命周期 beforeCreate里面改变this指向
  beforeCreate: function() {
    typeData = this
  },
  created() {
    dataDictionary('ALERT_CHANNEL').then((res) => {
      this.pushTypeData = res.data
    })
    document.onkeypress = function(e) {
      var keycode = document.all ? event.keyCode : e.which
      if (keycode == 13) {
        return false
      }
    }
  },
  mounted() {
    this.queryByPage()
  },
  methods: {
    // 时间格式化
    dateFormat: function(row, column) {
      var date = row[column.property]
      if (date == undefined) {
        return ''
      }
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    Reset() {
      this.form.field = ''
      this.form.templateType = ''
      this.beginDate = ''
      this.endDate = ''
      this.timeSelectedValue = null
      this.queryByPage()
    },
    // 列表查询
    queryByPage() {
      if (this.timeSelectedValue != null) {
        this.beginDate = moment(this.timeSelectedValue[0]).format('YYYY-MM-DD HH:mm:ss')
        this.endDate = moment(this.timeSelectedValue[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.beginDate = ''
        this.endDate = ''
      }
      const param = {
        createTime: this.beginDate ? `${this.beginDate},${this.endDate}` : '',
        templateName: this.form.field,
        pageSize: this.pageSize,
        currentPage: this.pageNum,
        templateType: this.form.templateType,
      }
      gaeaPushTemplateSearch(param).then((res) => {
        if (res.code != '200') return
        this.tableData = res.data.records
        this.total = res.data.total
        this.currentPage4 = res.data.pages
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.queryByPage()
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.queryByPage()
    },
    closeModal() {
      this.isModalTrueVisible = false
      this.isModalVisible = false
      this.isModalMailDemo = false
      this.isTsetSms = false
      this.isModalTsetSms = false
      this.$refs.form1 && this.$refs.form1.resetFields()
      this.queryByPage()
    },
    fanhui() {
      this.$router.push('/index')
    },
    // 获取单封模板信息
    Preview(val) {
      this.isModalMailDemo = true
      this.isModalTrueMailDemo = true
      this.templateDetails = val
      // showTemplateById({templateId:val.templateId}).then(res => {
      //   if (res.repCode == "0000") {
      //     this.templateDetails=res.repData;
      //   }
      // }).catch(error => { });
    },
    queryOrEditMailDemo(index, val) {
      this.statusText = '查看模板'
      this.proData = val
      this.templateDeployType = val.templateType
      this.isModalVisible = true
      this.isModalTrueVisible = true
      this.isFind = false
    },
    addOrEditMailDemo(index, val) {
      this.isFind = true
      if (val === undefined) {
        this.statusText = '新增模板'
        this.proData = null
        this.templateDeployType = null
      } else {
        this.statusText = '编辑模板'
        this.proData = val
        this.templateDeployType = val.templateType
      }
      this.isModalVisible = true
      this.isModalTrueVisible = true
    },
    // 删除模板
    DemoDel(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          // const params = {
          //   templateType: this.templateDeployType,
          //   templateId: val,
          // }
          gaeaPushTemplateDelect(id).then((res) => {
            if (res.code != '200') return
            this.queryByPage()
          })
          // deleteTemplate(params).then((response) => {
          //   if (response.repCode == '0000') {
          //     this.$message({ message: '删除成功', type: 'success', duration: 1500 })
          //     this.queryByPage()
          //   }
          // })
        })
        .catch(() => {})
    },
    Tutorial() {
      this.$router.push({ path: '/push/push/tutorial' })
    },
    testSms(row) {
      this.isTsetSms = true
      this.isModalTsetSms = true
      this.proTestSmsData = row
    },
  },
}
</script>

<style scoped lang="scss">
.s-green {
  color: #55a532;
}

.s-red {
  color: red;
}

.box {
  .top {
    text-align: center;
  }
}

.nav-menu {
  width: 100%;
  background: #203160;
  position: fixed;
  z-index: 10000;
  top: 0;
  .logo {
    margin-top: 14px;
    width: 85px;
  }
}

.userBox {
  line-height: 64px;
  color: rgba(255, 255, 255, 0.4);
  font-size: 12px;
  cursor: pointer;
  .goOut {
    padding: 10px 18px;

    &:hover {
      color: #03afff;
    }
  }
}

.el-collapse {
  position: relative;
}

.el-button {
  font-size: 12px;
}

.left-box {
  margin-right: 15px;
  overflow: hidden;
}

.right-box {
  padding: 20px;
  overflow-x: hidden;
}

.left-box,
.right-box {
  background: #ffffff;
}

.project-lid {
  background: #9199b1;
  height: 60px;
  padding: 0 20px;
  color: #fff;
  line-height: 60px;
  font-size: 16px;
  overflow: hidden;
  label {
    color: #fff;
  }
}

.log-list {
  overflow-y: auto;
  overflow-x: hidden;
}

.log-repeat {
  .log-info {
    div {
      line-height: 20px;
      font-size: 13px;
      word-wrap: break-word;
      white-space: pre-wrap;
    }
  }
}

.el-collapse {
  border-width: 0;
}

.advanced-search {
  background: #fff;
  border-radius: 100px;
  font-size: 13px;
  color: #fff;
  height: 30px;
  line-height: 30px;
  padding: 0 20px;
}

.icon-zuidahua {
  color: #fff;
  font-size: 24px;
  &:hover {
    font-size: 29px;
    color: #fff;
  }
}

.zhiding {
  margin-top: 14px;
  font-size: 14px;
  color: #999;
  position: absolute;
  z-index: 1;
  right: 37px;
  .icon {
    color: #333;
  }
  .del-log {
    position: absolute;
    display: block;
    top: 0;
    right: -25px;
    color: #d8d8d8;
  }
  .del-log:hover {
    color: red;
  }
}
</style>
