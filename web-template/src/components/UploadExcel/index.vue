<template>
  <div style="display: inline-block; margin-left: 10px">
    <el-button type="primary" icon="el-icon-upload2" @click="uploadFile">导入</el-button>
    <el-dialog class="back_top" :append-to-body="true" title="导入" :visible.sync="dialogVisible" :close-on-click-modal="false" :close-on-press-escape="false" width="40%" :before-close="handleClose" center>
      <div class="app-container info-submit-container">
        <el-row>
          <el-col :span="24">
            <el-upload
              ref="upload"
              class="upload-infoSubmit"
              :action="baseUrl + fileApi[this.importApi]"
              :headers="authHeader"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :on-success="success"
              :on-error="error"
              :on-change="change"
:limit="1"
              accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
              name="file"
              :auto-upload="false"
            >
              <el-row type="flex" justify="space-between">
                <el-col :span="18">
                  <div class="text">选择信息文件</div>
                </el-col>
                <el-col :span="5">
                  <el-button type="primary">浏览</el-button>
                </el-col>
              </el-row>
            </el-upload>
          </el-col>
          <el-col :span="24">
            <el-button type="text" @click="download">文件模板下载</el-button>
          </el-col>
        </el-row>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'
// import * as fileApi from '@/api/file-api'
export default {
  props: {
    importApi: {
      type: String,
      required: true,
      default: '',
    },
    exportApi: {
      type: String,
      required: false,
      default: '',
    },
    exportName: {
      type: String,
      required: false,
      default: '',
    },
  },
  data() {
    return {
      fileApi: {},
      dialogVisible: false,
      authHeader: {
        Authorization: 'Bearer ' + getToken(),
      },
      fileList: [],
      Loading: false,
      baseUrl: process.env.VUE_APP_BASE_API,
    }
  },
  created() {},
  methods: {
    // 导入按钮
    uploadFile() {
      this.dialogVisible = true
    },
    handleClose() {
      this.$refs.upload.clearFiles() // 重置按钮
      this.dialogVisible = false
      // this.$emit('colseDialog')
      return
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    change(file, fileList) {
      console.log(fileList)
      this.fileList = fileList
    },
    handlePreview(file, fileList) {
      console.log(file, fileList)
    },
    submitUpload() {
      if (this.fileList == '') {
        this.Loading = false
      } else {
        this.Loading = true
      }
      this.$refs.upload.submit() // 提交按钮
    },
    download() {
      // window.open(this.fileApi[this.exportApi])
    },
    success(response) {
      if (response.code != '2000') {
        this.$message({
          showClose: true,
          duration: 0,
          message: response.data,
          type: 'error',
          center: true,
        })
      } else {
        this.$message({
          showClose: true,
          duration: 0,
          message: response.data,
          type: 'success',
          center: true,
        })
        this.handleClose()
      }
      this.$refs.upload.clearFiles()
      this.Loading = false
    },
    error(err) {
      this.$message.error('导入失败')
      this.Loading = false
    },
  },
}
</script>
<style>
.upload-infoSubmit .el-upload {
  display: block;
  text-align: left;
}
</style>
<style scoped lang="scss">
.text {
  height: 28px;
  line-height: 28px;
  background: #eee;
  color: #333; // padding-left: 15px;
}

.select {
  height: 28px;
}
</style>
<style lang="scss">
.back_top .el-dialog__header {
  background: #1890ff;
  span {
    color: #fff;
  }
}
</style>
