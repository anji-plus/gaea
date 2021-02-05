<template>
  <div class="app-container">
    <el-form ref="helpForm" :model="helpForm" :rules="rules" label-width="100px" class="demo-ruleForm">
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="标题" prop="helpTitle">
            <el-input v-model="helpForm.helpTitle" placeholder="请填写标题" @blur="blurInput" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="排序" prop="sort">
            <el-input v-model="helpForm.sort" placeholder="请填写排序" />
          </el-form-item>
        </el-col>
      </el-row>
      <div class="uedit-warp">
        <div class="goods-editor">
          <!-- 工具栏容器 -->
          <div id="toolbar-container" />
          <!-- 编辑器容器 -->
          <div id="editor">
            <!-- <p>This is the initial editor content.</p> -->
          </div>
        </div>
      </div>
    </el-form>

    <div slot="footer" style="text-align: center">
      <el-button type="primary" plain>{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain>{{ $t('btn.close') }}</el-button>
    </div>
  </div>
</template>

<script>
import CKEditor from '@ckeditor/ckeditor5-build-decoupled-document'
import '@ckeditor/ckeditor5-build-decoupled-document/build/translations/zh-cn'

export default {
  components: {},
  data() {
    return {
      helpForm: {
        helpCategory: '', // 分类
        helpTitle: '', // 标题
        sort: '', // 排序
        enableFlag: '', // 启用状态
      },
      rules: {
        helpCategory: [{ required: true, message: '请选择所属分类', trigger: 'change' }],
        helpTitle: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
        enableFlag: [{ required: true, message: '请选择启用状态', trigger: 'change' }],
      },
      id: null,
      editor: null,
      isFind: true,
    }
  },
  mounted() {
    this.id = this.$route.query.id
    this.isFind = this.$route.query.val
    // if(this.id != null) { // 编辑
    //   this.queryDetail()
    // }
    var self = this
    setTimeout(function() {
      self.queryDetail()
    }, 1000)

    this.initCKEditor()
  },
  methods: {
    initCKEditor() {
      console.log(11111)
      class UploadAdapter {
        constructor(loader) {
          this.loader = loader
        }
        // async upload() {
        //   // 重置上传路径
        //   const data = new FormData()
        //   data.append('file', await this.loader.file)
        //   return new Promise((resolve, reject) => {
        //     upload(data).then((res) => {
        //       resolve({
        //         default: res.repData,
        //       })
        //     })
        //   })
        // }
      }
      CKEditor.create(document.querySelector('#editor'), {
        language: 'zh-cn',
        ckfinder: {
          uploaded: 1,
          url: '/',
        },
        link: {
          addTargetToExternalLinks: true,
        },
        mediaEmbed: {
          providers: [
            {
              name: 'myprovider',
              url: [/^lizzy.*\.com.*\/media\/(\w+)/, /^www\.lizzy.*/, /(http|https):\/\/([\w.]+\/?)\S*/],
              html: (match) => {
                // 获取媒体url
                const input = match['input']
                return '<div style="position: relative; padding-bottom: 100%; height: 0; padding-bottom: 70%;">' + `<iframe src="${input}" ` + 'style="position: absolute; width: 100%; height: 100%; top: 0; left: 0;" ' + 'frameborder="0" allowtransparency="true" allow="encrypted-media">' + '</iframe>' + '</div>'
              },
            },
          ],
        },
      }).then((editor) => {
        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
          return new UploadAdapter(loader)
        }
        const toolbarContainer = document.querySelector('#toolbar-container')
        toolbarContainer.appendChild(editor.ui.view.toolbar.element)
        this.editor = editor
      })
    },
    blurInput() {
      if (!this.helpForm.helpCategory) {
        this.$message.error('请先选择所属分类')
        this.helpForm.helpTitle = ''
        return
      }
      const params = {}
      params.helpCategory = this.helpForm.helpCategory
      params.helpTitle = this.helpForm.helpTitle
      // titleCheck(params).then((res) => {
      //   if (res.repCode !== '0000') {
      //     this.helpForm.helpTitle = ''
      //   }
      // })
    },
    queryDetail(id) {
      // queryById({helpId: id}).then(response => {
      const response = {
        repCode: '0000',
        repMsg: null,
        repData: {
          helpId: 25,
          helpCategory: 'login_register',
          helpTitle: '1222',
          helpContent: '<figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/b9b01955-d20e-434c-ac90-2c25597217a2"></figure><figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/f8f4a4e4-c156-45c2-b350-2e0c1afe87d4"></figure><p>&nbsp;</p><figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/3bdda098-8ba6-4a0b-bf2f-9e56550a872e"></figure><p>&nbsp;</p>',
          enableFlag: 1,
          sort: 1,
          remark: null,
          createdBy: 'aimee',
          createdTime: '2020-12-22T11:02:40',
          updatedBy: 'aimee',
          updatedTime: '2020-12-22T11:05:53',
        },
        success: true,
        error: false,
      }
      if (response.repCode == '0000') {
        this.helpForm = response.repData
        this.editor.setData('<figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/b9b01955-d20e-434c-ac90-2c25597217a2"></figure><figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/f8f4a4e4-c156-45c2-b350-2e0c1afe87d4"></figure><p>&nbsp;</p><figure class="image"><img src="http://haitongnla.test.anji-plus.com/auth-service/file/download/3bdda098-8ba6-4a0b-bf2f-9e56550a872e"></figure><p>&nbsp;</p>')
      }
      // })
    },
    // 保存
    confirmBtn(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.editor.getData()) {
            this.$message.error('请输入富文本框内容')
            return
          }
          const helpForm = this.helpForm
          helpForm.helpContent = this.editor.getData()
          if (this.id === null) {
            // 新增
            // reqCreate(helpForm).then((res) => {
            //   if (res.repCode === '0000') {
            //     this.$message({ message: '新增成功', type: 'success', duration: 2 * 1000 })
            //     // this.goBack()
            //   }
            // })
          } else {
            // 编辑
            // reqUpdate(helpForm).then((res) => {
            //   if (res.repCode === '0000') {
            //     this.$message({ message: '编辑成功', type: 'success', duration: 2 * 1000 })
            //     // this.goBack()
            //   }
            // })
          }
        }
      })
    },
  },
}
</script>
<style scoped>
.uedit-warp {
  margin-top: 20px;
}
.confirm-btn {
  width: 100%;
  text-align: center;
  margin-top: 40px;
}
.confirm-btn .btn {
  width: 200px;
}

#editor {
  min-height: calc(100vh - 302px);
  max-height: calc(100vh - 302px);
  border: 1px solid #c4c4c4;
}
</style>
