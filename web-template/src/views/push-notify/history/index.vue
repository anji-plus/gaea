<template>
  <div class="app-container">
    <el-form ref="form" :label-position="labelPosition" :model="form" label-width="100px">
      <el-row>
        <el-col :span="19">
          <el-col :span="6">
            <el-form-item label="推送类型">
              <el-select v-model="form.templateType" :placeholder="$t('placeholder.select')">
                <el-option key="1" label="邮件" :value="1" />
                <el-option key="0" label="钉钉" :value="0" />
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
        </el-col>
        <el-col :span="5">
          <el-button type="primary" @click="queryByPage">{{ $t('btn.query') }}</el-button>
          <el-button type="danger" @click="Reset">{{ $t('btn.reset') }}</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-table stripe border style="width: 100%" :data="tableData" element-loading-text="Loading" fit highlight-current-row>
      <el-table-column type="expand">
        <template slot-scope="props">
          <p class="table-expand-item">
            <span>{{ props.row.sendResult }}</span>
          </p>
        </template>
      </el-table-column>
      <el-table-column width="90" label="序号" align="center">
        <template
          slot-scope="scope"
        ><span>{{ scope.$index + (pageNum - 1) * pageSize + 1 }} </span>
        </template>
      </el-table-column>
      <el-table-column prop="pushTo" :label="mailTo" :show-overflow-tooltip="true" />
      <el-table-column label="推送类型" width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.templateType }}
          <!-- {{getDictCode('ALERT_CHANNEL',scope.row.templateType,"labelEng").label}} -->
        </template>
      </el-table-column>
      <el-table-column prop="pushTitle" label="标题" align="center" />
      <el-table-column prop="templateCode" label="模板Code" align="center" />
      <el-table-column prop="sendTime" :formatter="dateFormat" label="发起时间" width="160" align="center" />
      <el-table-column label="发送状态" align="center" width="90">
        <template slot-scope="scope">
          <div v-if="scope.row.sendStatus == 1" class="s-green">成功</div>
          <div v-if="scope.row.sendStatus == 0" class="s-red">失败</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="90">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="查看" placement="top">
            <el-button size="mini" icon="el-icon-search" :plain="true" circle @click="showMailDetails(scope.row)" />
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="fr" :current-page="currentPage4" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <Modal v-show="isModalEmailInfo" ref="Modal" :status-text="isMailTitle" content-text="index.vue" alert="1" @closeModal="closeModal" @button="closeModal">
      <Details :mail-details="mailDetails" :temolate-type="temolateType" @closeModal="closeModal" />
    </Modal>
  </div>
</template>

<script>
import Modal from '../template/component/Modal'
// import {queryPushHistoryByPage, queryPushHistoryById} from '@/api/push/notify';
import Details from './component/Modal-details.vue'
import moment from 'moment'
// import CodeSelect from '@/components/codeSelect'
export default {
  name: 'History',
  components: {
    Modal,
    Details,
  },
  data() {
    return {
      currentPage4: 1,
      total: 0,
      page: 1,
      pageSize: 10,
      pageNum: 1,
      labelPosition: 'right',
      timeSelectedValue: null, // 时间选择器的value
      form: {
        field: '', // 搜索字段
        templateType: '',
      },
      isModalEmailInfo: false,
      tableData: [],
      isMailTitle: '推送内容',
      mailDetails: {},
      beginDate: '',
      endDate: '',

      // 判断 类型
      mailTo: '收件人',
      temolateType: 'mail',
    }
  },
  created() {
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
    // 列表查询
    queryByPage() {
      if (this.timeSelectedValue != null) {
        this.beginDate = moment(this.timeSelectedValue[0]).format('YYYY-MM-DD HH:mm:ss')
        this.endDate = moment(this.timeSelectedValue[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.beginDate = ''
        this.endDate = ''
      }
      this.mailTo = '收件人'
      const param = {
        sentTime: this.timeSelectedValue,
        keyword: this.form.field,
        pageSize: this.pageSize,
        currentPage: this.pageNum,
        startTime: this.beginDate,
        endTime: this.endDate,
        // templateType:this.getDictCode('ALERT_CHANNEL' ,this.form.templateType).labelEng
      }
      const res = {
        repCode: '0000',
        repMsg: null,
        repData: {
          totalPage: 81,
          pageSize: 10,
          list: [
            {
              pushId: 2664,
              templateType: 'mail',
              templateCode: 'power-failed',
              content:
                '<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><meta http-equiv="X-UA-Compatible" content="ie=edge"><title>Document</title></head><body><h3>告警名称：s5560电源状态</h3>告警信息</br>发生时间:2021-01-07 14:04:29.111</br>电源位置:3号VPC 71.33 视频Power 1</br>电源状态:recovered</br>详情:2013 ServerSwitch %%10DEV/5/POWER_RECOVERED: Power 1 recovered.</br><br/>告警条数：1<br/>本邮件由推送系统发送，请勿回复</body></html>',
              pushTitle: 's5560电源状态',
              pushFrom: 'SYSTEM',
              pushTo: 'gongaisheng@anji-plus.com.dis,raodeming@anji-plus.com.dis',
              mobiles: null,
              mailCopy: null,
              mailBcc: null,
              operator: '邮箱',
              sendTime: '2021-01-07T14:05:33',
              sendStatus: 1,
              sendResult: 'success',
              createdBy: null,
              createdTime: '2021-01-07T14:05:33',
              updatedBy: null,
              updatedTime: '2021-01-07T14:05:33',
              endTime: null,
              keyword: null,
              startTime: null,
            },
            {
              pushId: 2663,
              templateType: 'mail',
              templateCode: 'fan-failed',
              content:
                '<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><meta http-equiv="X-UA-Compatible" content="ie=edge"><title>Document</title></head><body><h3>告警名称：s5560风扇状态</h3>告警信息</br>发生时间:2021-01-07 14:04:29.111</br>风扇位置:3号VPC 71.33 视频Fan 1</br>风扇状态:recovered</br>详情:2020-10-18 09:39:41.000 Oct 18 09:39:08 2020 w6sj_2 %%10DEV/4/FAN RECOVERED(l): Fan 1 recovered.</br><br/>告警条数：1<br/>本邮件由推送系统发送，请勿回复</body></html>',
              pushTitle: 's5560风扇状态',
              pushFrom: 'SYSTEM',
              pushTo: 'gongaisheng@anji-plus.com.dis,raodeming@anji-plus.com.dis',
              mobiles: null,
              mailCopy: null,
              mailBcc: null,
              operator: '邮箱',
              sendTime: '2021-01-07T14:05:23',
              sendStatus: 1,
              sendResult: 'success',
              createdBy: null,
              createdTime: '2021-01-07T14:05:23',
              updatedBy: null,
              updatedTime: '2021-01-07T14:05:23',
              endTime: null,
              keyword: null,
              startTime: null,
            },
          ],
          currentPage: 1,
          totalCount: 801,
        },
        success: true,
        error: false,
      }
      // queryPushHistoryByPage(param).then(res=>{
      if (res.repCode == '0000') {
        this.tableData = res.repData.list
        this.total = res.repData.totalCount
        this.currentPage4 = res.repData.currentPage
      }
      // })
    },
    Search() {
      this.queryByPage()
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
      this.isModalEmailInfo = false
    },
    fanhui() {
      this.$router.push('/index')
    },
    // 单封邮件查询
    showMailDetails(val) {
      console.log(val)
      this.temolateType = val.templateType
      this.isModalEmailInfo = true
      const param = {
        pushId: val.pushId,
      }
      const res = {
        repCode: '0000',
        repMsg: null,
        repData: {
          pushId: 2664,
          templateType: 'mail',
          templateCode: 'power-failed',
          content:
            '<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><meta http-equiv="X-UA-Compatible" content="ie=edge"><title>Document</title></head><body><h3>告警名称：s5560电源状态</h3>告警信息</br>发生时间:2021-01-07 14:04:29.111</br>电源位置:3号VPC 71.33 视频Power 1</br>电源状态:recovered</br>详情:2013 ServerSwitch %%10DEV/5/POWER_RECOVERED: Power 1 recovered.</br><br/>告警条数：1<br/>本邮件由推送系统发送，请勿回复</body></html>',
          pushTitle: 's5560电源状态',
          pushFrom: 'SYSTEM',
          pushTo: 'gongaisheng@anji-plus.com.dis,raodeming@anji-plus.com.dis',
          mobiles: null,
          mailCopy: null,
          mailBcc: null,
          operator: '邮箱',
          sendTime: '2021-01-07T14:05:33',
          sendStatus: 1,
          sendResult: 'success',
          createdBy: null,
          createdTime: '2021-01-07T14:05:33',
          updatedBy: null,
          updatedTime: '2021-01-07T14:05:33',
          endTime: null,
          keyword: null,
          startTime: null,
        },
        success: true,
        error: false,
      }
      // queryPushHistoryById(param).then(res => {
      if (res.repCode == '0000') {
        this.mailDetails = res.repData
      }
      // })
    },

    Reset() {
      this.form.field = ''
      this.beginDate = ''
      this.endDate = ''
      this.timeSelectedValue = null
      this.queryByPage()
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

.project-list {
  line-height: 42px;
  font-size: 14px;
  color: #333;
  width: 100%;
  height: calc(100vh - 110px);
  height: -moz-calc(100vh - 110px);
  height: -webkit-calc(100vh - 110px);
  height: calc(100vh - 110px);
  overflow-y: auto;
  overflow-x: hidden;
  .list {
    border-bottom: 1px solid #f4f4f4;
    cursor: pointer;
  }
  span {
    padding: 0 20px;
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
