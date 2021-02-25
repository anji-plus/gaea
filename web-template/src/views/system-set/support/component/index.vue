<template>
  <div class="app-container">
    <el-form ref="helpForm" :model="helpForm" :rules="rules" label-width="100px" class="demo-ruleForm">
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item prop="helpCategory" label="所属分类">
            <el-select v-model="helpForm.helpCategory" :placeholder="$t('placeholder.select')">
              <el-option v-for="(item, i) in classificationList" :key="i" :label="item.text" :value="item.id" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="标题" prop="helpTitle">
            <el-input v-model="helpForm.helpTitle" placeholder="请填写标题" @blur="blurInput" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="排序" prop="sort">
            <el-input v-model="helpForm.sort" type="number" placeholder="请填写排序" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="enabled" label="启用状态">
            <el-select v-model="helpForm.enabled" :placeholder="$t('placeholder.select')">
              <el-option v-for="(item, i) in statusList" :key="i" :label="item.label" :value="item.value" />
            </el-select>
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

    <div v-if="clickType != '查看'" slot="footer" style="text-align: center">
      <el-button type="primary" plain @click="confirmBtn('helpForm')">{{ $t('btn.confirm') }}</el-button>
      <el-button type="danger" plain @click="cancel()">{{ $t('btn.close') }}</el-button>
    </div>
  </div>
</template>

<script>
import CKEditor from '@ckeditor/ckeditor5-build-decoupled-document'
import '@ckeditor/ckeditor5-build-decoupled-document/build/translations/zh-cn'
import { gaeaHelpAdd, gaeaHelpEdit, uploadImg } from '@/api/system-set'
export default {
  components: {},
  props: {
    helpForm: {
      type: Object,
      default: function() {
        return {
          helpCategory: '', // 分类
          helpTitle: '', // 标题
          sort: null, // 排序
          enabled: '', // 启用状态
        }
      },
    },
    clickType: {
      type: String,
      default: function() {
        return ''
      },
    },
    classificationList: {
      type: Array,
      default: function() {
        return []
      },
    },
  },
  data() {
    return {
      // 所属分类数据
      // classificationList: [
      //   // {extend: "", label: "登录注册", labelEng: "login_register", value: "login_register"},
      //   // {extend: "", label: "权限角色", labelEng: "auth_role", value: "auth_role"},
      //   // {extend: "", label: "字典管理", labelEng: "dict_manager", value: "dict_manager"},
      //   // {extend: "", label: "系统设置", labelEng: "system_setting", value: "system_setting"},
      //   // {extend: "", label: "消息推送", labelEng: "message_push", value: "message_push"},
      //   // {extend: "", label: "设备管理", labelEng: "device_manager", value: "device_manager"},
      //   // {extend: "", label: "监控计算", labelEng: "item_calculate", value: "item_calculate"}
      // ],
      // 启用状态数据
      statusList: [
        {
          label: '启用',
          value: 1,
        },
        {
          label: '禁用',
          value: 0,
        },
      ],
      rules: {
        helpCategory: [{ required: true, message: '请选择所属分类', trigger: 'change' }],
        helpTitle: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
        enabled: [{ required: true, message: '请选择启用状态', trigger: 'change' }],
      },
      id: null,
      editor: null,
    }
  },
  watch: {
    'helpForm.sort': function(val) {
      this.helpForm.sort = val != null ? Number(val) : null
    },
  },
  mounted() {
    const that = this
    this.initCKEditor()
    setTimeout(function() {
      if (that.clickType == '查看' || that.clickType == '编辑') {
        // 编辑
        that.queryDetail()
      }
    }, 100)
  },
  methods: {
    initCKEditor() {
      class UploadAdapter {
        constructor(loader) {
          this.loader = loader
        }
        async upload() {
          // 重置上传路径
          const data = new FormData()
          data.append('file', await this.loader.file)
          return new Promise((resolve, reject) => {
            uploadImg(data)
              .then((res) => {
                if (res.code == '200') {
                  resolve({
                    default: res.data,
                  })
                }
              })
              .catch((err) => {
                reject(err)
              })
          })
        }
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
        this.editor = editor // 将编辑器保存起来，用来随时获取编辑器中的内容等，执行一些操作
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
    queryDetail() {
      console.log(1, this.helpForm.helpContent)
      this.editor.setData(this.helpForm.helpContent)
      // this.editor.setData("\"<figure class=\"image\"><img src=\"https://gaea.anji-plus.com/auth-service/file/download/80a46139-7e4a-4cbb-9cff-e2485e038d45\"></figure>\"")
    },
    cancel() {
      this.$parent.$parent.colseDialog('no')
    },
    // 清空内容
    clearEdit() {
      this.editor.setData('')
    },
    // 保存
    confirmBtn(formName) {
      this.$refs[formName].validate(async(valid) => {
        if (valid) {
          if (!this.editor.getData()) {
            this.$message.warning('请输入富文本框内容')
            return
          }
          const helpForm = this.helpForm
          helpForm.helpContent = this.editor.getData()
          if (this.clickType == '新增') {
            // 新增
            const res = await gaeaHelpAdd(helpForm)
            if (res.code != '200') return
            this.$parent.$parent.colseDialog('yes')
            this.clearEdit()
          } else {
            // 编辑
            const res = await gaeaHelpEdit(helpForm)
            if (res.code != '200') return
            this.$parent.$parent.colseDialog('yes')
            this.clearEdit()
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
