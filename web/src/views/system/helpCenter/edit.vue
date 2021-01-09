<template>
  <div class="app-container">
    <el-form :model="helpForm" :rules="rules" ref="helpForm" label-width="100px" class="demo-ruleForm">
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="所属分类" prop="helpCategory">
            <code-select v-model="helpForm.helpCategory" dictname="HELP_CATEGORY" mystyle="width: 200px;" placeholder="请选择所属分类"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="标题" prop="helpTitle">
              <el-input v-model="helpForm.helpTitle" placeholder="请填写标题" @blur="blurInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="排序" prop="sort">
              <el-input v-model="helpForm.sort" placeholder="请填写排序"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="启用状态" prop="enableFlag">
            <code-select v-model="helpForm.enableFlag" dictname="ENABLE_FLAG" mystyle="width: 200px;" placeholder="启用状态"/>
          </el-form-item>
        </el-col>
      </el-row>
      <div class="uedit-warp">
        <div class="goods-editor">
          <!-- 工具栏容器 -->
          <div id="toolbar-container"></div>
          <!-- 编辑器容器 -->
          <div id="editor">
            <!-- <p>This is the initial editor content.</p> -->
          </div>
        </div>
      </div>
      <div class="confirm-btn">
        <el-button  @click="goBack" class="btn">取消</el-button>
        <el-button v-if="!isFind" type="primary" size="medium" class="btn" @click="confirmBtn('helpForm')">保存</el-button>
        <el-button v-else disabled type="primary" size="medium" class="btn" @click="confirmBtn('helpForm')">保存</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import {  reqCreate, queryById,reqUpdate,upload,titleCheck } from '@/api/system/helpCenter'
import CKEditor from '@ckeditor/ckeditor5-build-decoupled-document'
import CodeSelect from '@/components/codeSelect'
import '@ckeditor/ckeditor5-build-decoupled-document/build/translations/zh-cn'
import { parse } from 'path-to-regexp'

export default {
  components: {
    CodeSelect
  },
  data() {
    return {
      helpForm: {
        helpCategory: '', // 分类
        helpTitle: '', // 标题
        sort: '', // 排序
        enableFlag: '', // 启用状态
      },
      rules: {
        helpCategory: [
          { required: true, message: '请选择所属分类', trigger: 'change' }
        ],
        helpTitle: [
          { required: true, message: '请输入标题', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入排序', trigger: 'blur' }
        ],
        enableFlag: [
          { required: true, message: '请选择启用状态', trigger: 'change' }          
        ]
      },
      id: null,
      editor:null,
      isFind: true
    }
  },
  mounted() {
    this.id = this.$route.query.id
    this.isFind = this.$route.query.val
    if(this.id != null) { // 编辑
      this.queryDetail(this.id)
    }
    this.initCKEditor()
  },
  methods: {
    initCKEditor() {
      class UploadAdapter {
        constructor(loader) {
          this.loader = loader
        }
        async upload() {  //重置上传路径
          const data = new FormData();
          data.append('file', await this.loader.file);
          return new Promise((resolve, reject) => {
            upload(data).then(res => {
              resolve({
                default: res.repData
              })
            })
          })
        }
      }
      CKEditor.create(document.querySelector('#editor'), {
        language: 'zh-cn',
        ckfinder: {
          'uploaded': 1, 'url': '/'
        },
        link: {
          addTargetToExternalLinks: true,
        },
        mediaEmbed: {
          providers: [
            {
    					name: 'myprovider',
    					url: [
    						/^lizzy.*\.com.*\/media\/(\w+)/,
    						/^www\.lizzy.*/,
    						/(http|https):\/\/([\w.]+\/?)\S*/
    					],
    					html: match => {
    						//获取媒体url
                const input = match['input'];
    						return (
    							'<div style="position: relative; padding-bottom: 100%; height: 0; padding-bottom: 70%;">' +
    								`<iframe src="${input}" ` +
    									'style="position: absolute; width: 100%; height: 100%; top: 0; left: 0;" ' +
    									'frameborder="0" allowtransparency="true" allow="encrypted-media">' +
    								'</iframe>' +
    							'</div>'
    						);
    					}
    				}
          ]
        }
      }).then(editor => {
        editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
          return new UploadAdapter(loader);
        };
        const toolbarContainer = document.querySelector('#toolbar-container');
        toolbarContainer.appendChild(editor.ui.view.toolbar.element);        
        this.editor = editor
      })
    },
    blurInput() {
      if(!this.helpForm.helpCategory) {
        this.$message.error('请先选择所属分类')
        this.helpForm.helpTitle = ""
        return
      }
      let params = {}
      params.helpCategory = this.helpForm.helpCategory
      params.helpTitle = this.helpForm.helpTitle
      titleCheck(params).then(res => {
        if(res.repCode !== '0000') {
          this.helpForm.helpTitle = ''
        }
      })
    },
    queryDetail(id) {
      queryById({helpId: id}).then(response => {
        if (response.repCode == '0000') {
          this.helpForm = response.repData
          this.editor.setData(response.repData.helpContent)
        }
      })
    },
    // 保存
    confirmBtn(formName) {
       this.$refs[formName].validate((valid) => {
        if(valid) {
          if(!this.editor.getData()) {
            this.$message.error('请输入富文本框内容')
            return
          }
          let helpForm = this.helpForm
          helpForm.helpContent = this.editor.getData()
          if(this.id === null) { // 新增
            reqCreate(helpForm).then(res => {
              if(res.repCode === '0000') {
                this.$message({ message: '新增成功', type: 'success', duration: 2 * 1000 })
                this.goBack()
              }
            })
          } else { // 编辑
            reqUpdate(helpForm).then(res => {
              if(res.repCode === '0000') {
                this.$message({ message: '编辑成功', type: 'success', duration: 2 * 1000 })
                this.goBack()
              }
            })
          }
        }
      })
    }
  }
}
</script>
<style scoped>
.uedit-warp{
  margin-top: 20px;
}
.confirm-btn{
  width: 100%;
  text-align: center;
  margin-top: 40px;
}
.confirm-btn .btn{
  width: 200px;
}

#editor {
  min-height: calc(100vh - 302px);
  max-height: calc(100vh - 302px);
  border: 1px solid #c4c4c4;
}
</style>
