<!-- 
  通用性页面组件
  接收参数
  prop-from     --- 查询条件form表单相关
  prop-table    --- 列表相关
  prop-dialog   --- 弹窗form表单相关
  prop-btn      --- 页面是否存在按钮权限 
  prop-api      --- 增删改查接口
  
  增删改查接口部分传输格式如下
  //                            //     参数说明          必须	   	       类型	           可选值	            默认值   
  prop-api: {
    query: getList,             //    查询接口            是           function            -                  -           
    add: add,                   //    新增接口            否           function            -                  -  
    edit: edit,                 //    编辑接口            否           function            -                  -      
    delete: delete,             //    删除接口            否           function            -                  -      
  },
  
  查询条件部分传输格式如下
  prop-from:{
    // 所有查询条件列表               //     参数说明         必须	   	      类型	          可选值	          默认值      
    list:[
      {
        formType: 'input',          //     form表单类型       否            string        select/input        'input'
        label: '字段展示名称',      //   字段名称-支持国际化   是            string             -                 -
        field: 'actionName',       //        字段名           是            string             -                 -
        fieldValue: null,          //      字段初始值         否               -               -                 -
        disabled:false             //     该项表单的禁用       否              boolean           -               false
        clearable:true             //  是否显示清除按钮       否              boolean           -               true
        placeholder: '',          //       placeholder        否               -               -                -
        rules:{ message: '提示' }  //        校验规则         否          object/array         -                 -
        options:[{                //      下拉框的可选值      否              array            -                 []
          label:'启用',
          value:'1'
        }],
        optionsconfig:{           //   下拉框options的配置    否             object            -                 {}
          key: 'label',           //下拉框options的key绑定值  否             string            -    默认用下边label的值若label未传，则默值为'label'
          label: 'label',         //   options的label绑定值   否             string            -               'label'
          value: 'value'          //   options的value绑定值   否             string            -               'value'
        }
      }
    ],
    labelWidth:'100px',          //     表单域标签的宽度       否              string            -               '100px'
    rules:{},                     //     表单验证规则          否              object            -                 -
    disabled:false               //     所有表单的禁用         否              boolean           -               false
    //  待扩展...
  }
  列表部分传输格式如下
  prop-table:{
    // 所有查询条件列表               //     参数说明         必须	   	      类型	          可选值	          默认值      
    list:[
      {
        label: '',                //         列名称           是            string             -                 -
        field: 'actionName',       //        字段名           是            string             -                 -
        minWidth: '100',            //       列最小宽度         否            string             -                110
        operate: true,    // 第一列是否有查看的功能（高亮可操作）  否            boolean         true/false      true
        custom: false, //是否需要自定义展示内容(处理字段后再展示)  否            boolean         true/false      false
        renderer: (row)=>{ //自定义的dom custom 字段为true时必有此值 否            function          -               -
          return 自定义的dom   
        },
      }
    ],
    border: true,                 //     表格是否带边框        否              boolean            -               true
    stripe: false                   //     表格是否斑马纹        否              boolean            -              false
    height: 0                     //     Table 的高度,         否              string/number        -               -
    maxHeight: 0               //     Table 的最大高度,         否              string/number        -               -
    hasSelection:true,            //    是否展示复选框         否              boolean            -               true
    hasIndex:true,                //    是否展示索引列         否              boolean            -               false
    hasCreateAndupdate:true,  // 是否展示创建/更新者和创建时间   否              boolean            -               true
    //  待扩展...
  }
  // 所有form表单新增项             //     参数说明         必须	   	      类型	          可选值	          默认值      
  prop-dialog:{
    // 所有查询条件列表               //     参数说明         必须	   	      类型	          可选值	          默认值      
    list:[
      {
        formType: 'input',          //     form表单类型       否            string        select/input        'input'
        label: '字段展示名称',      //   字段名称-支持国际化   是            string             -                 -
        field: 'actionName',       //        字段名           是            string             -                 -
        notEditable:false          //   该项表单的不可编辑     否              boolean           -               false
        clearable:true             //  是否显示清除按钮       否              boolean           -               true
        placeholder: '',          //       placeholder        否               -               -                -
        rules:{ message: '提示' }  //        校验规则         否          object/array         -                 -
        options:[{                //      下拉框的可选值      否              array            -                 []
          label:'启用',
          value:'1'
        }],
        optionsconfig:{           //   下拉框options的配置    否             object            -                 {}
          key: 'label',           //下拉框options的key绑定值  否             string            -    默认用下边label的值若label未传，则默值为'label'
          label: 'label',         //   options的label绑定值   否             string            -               'label'
          value: 'value'          //   options的value绑定值   否             string            -               'value'
        }
      }
    ],
    labelWidth:'100px',          //     表单域标签的宽度       否              string            -               '100px'
    rules:{},                     //     表单验证规则          否              object            -                 -
    //  待扩展...
  }
 -->
<template>
  <div class="app-container">
    <el-form ref="formSearch" :model="searchForm" :label-width="propForm.labelWidth || '100px'" :rules="propForm.rules" :disabled="propForm.disabled">
      <el-row>
        <el-col :span="19">
          <el-row class="form_table">
            <el-col v-for="item in formList" :key="item.field" :span="item.span || 6">
              <el-form-item :label="item.label" :rules="item.rules" :prop="item.field" :disabled="item.disabled">
                <!-- 输入框 -->
                <el-input v-if="!item.formType || item.formType == 'input'" v-model.trim="searchForm[item.field]" :placeholder="item.placeholder || $t('placeholder.input')" :clearable="item.clearable !== false" />
                <!-- 下拉框 -->
                <el-select v-else-if="item.formType == 'select'" v-model="searchForm[item.field]" :placeholder="item.placeholder || $t('placeholder.select')" :clearable="item.clearable !== false">
                  <el-option v-for="ele in item.options || []" :key="ele[item.optionsconfig.key || item.optionsconfig.label || 'label']" :label="ele[item.optionsconfig.label || 'label']" :value="ele[item.optionsconfig.value || 'value']" />
                </el-select>
                <!-- 日期时间框  -->
                <el-date-picker v-else-if="item.formType.indexOf('date') >= 0" v-model="searchForm[item.field]" style="width: 100%" :placeholder="item.placeholder || $t('placeholder.select')" :type="item.formType" :clearable="item.clearable !== false" />
                <!-- 待扩展的表单类型，请自行扩展 -->
                <el-input v-else placeholder="组件不支持此类型表单请至组件内部自行扩展" disabled />
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
    <div v-if="propBtn">
      <permission-btn label="add" icon="el-icon-plus" type="primary" @click.native="openCreateUser" />
      <permission-btn label="edit" icon="el-icon-edit" type="primary" :disabled="selectedList.length != 1" @click="editDetail('edit', null)">{{ $t('btn.edit') }}</permission-btn>
      <delete-btn :disabled="selectedList.length != 1" :has-permission="true" @handleDelete="handleDelete" />
    </div>
    <div v-else>
      <el-button type="primary" icon="el-icon-plus" @click="openCreateUser">{{ $t('btn.add') }}</el-button>
      <el-button type="primary" icon="el-icon-edit" :disabled="selectedList.length != 1" @click="editDetail('edit', null)">{{ $t('btn.edit') }}</el-button>
      <delete-btn :disabled="selectedList.length != 1" @handleDelete="handleDelete" />
    </div>
    <el-table :data="tableList" :border="table.border !== false" :height="table.height || null" :max-height="table.maxHeight || null" :stripe="table.stripe" @selection-change="handleSelectionChange">
      <el-table-column v-if="table.hasSelection !== false" fixed type="selection" width="40" align="center" />
      <el-table-column v-if="table.hasIndex" label="序号" type="index" width="50" align="center" />
      <el-table-column v-for="(item, index) in propTable.list" :key="item.field" :label="item.label" :min-width="item.minWidth || 110" align="center">
        <template slot-scope="scope">
          <!-- 正常展示模式 -->
          <div v-if="!item.custom">
            <!-- 是第一列数据 && 需要高亮字段不为false 高亮并且可以点击 -->
            <span v-if="!index && item.operate !== false" class="view" @click="editDetail('view', scope.row)">{{ scope.row[item.field] }}</span>
            <span v-else>{{ scope.row[item.field] }}</span>
          </div>
          <!-- 自定义展示数据 -->
          <div v-else v-html="item.renderer(scope.row)" />
        </template>
      </el-table-column>
      <!-- <el-table-column prop="enabled" label="启用状态" min-width="90" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.enabled ? '启用' : '禁用' }}</span>
        </template>
      </el-table-column> -->
      <el-table-column v-if="table.hasCreateAndupdate !== false" prop="createTime" :label="$t('userManage.creationTime')" align="center" min-width="160" />
      <el-table-column v-if="table.hasCreateAndupdate !== false" prop="createBy" :label="$t('userManage.creator')" align="center" min-width="160" />
      <el-table-column v-if="table.hasCreateAndupdate !== false" prop="updateTime" :label="$t('userManage.modifyTime')" align="center" min-width="180" />
      <el-table-column v-if="table.hasCreateAndupdate !== false" prop="updateBy" :label="$t('userManage.modifyUser')" align="center" min-width="140" />
    </el-table>
    <el-pagination v-show="total > 0" background :current-page.sync="searchForm.pageNumber" :page-sizes="$pageSizeAll" :page-size="searchForm.pageSize" layout="total, prev, pager, next, jumper, sizes" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog :title="$t(`btn.${dialogTittle}`)" width="50%" :close-on-click-modal="false" center :visible.sync="basicDialog" @close="closeDialog">
      <el-form ref="formDialog" :model="dialogForm" :label-width="propDialog.labelWidth || '100px'" :rules="propDialog.rules" :disabled="dialogTittle == 'view'">
        <el-row class="form_table">
          <el-col v-for="item in dialogList" :key="item.field" :span="item.span || 8">
            <el-form-item :label="item.label" :rules="item.rules" :prop="item.field" :disabled="item.disabled">
              <!-- 输入框 -->
              <el-input v-if="!item.formType || item.formType == 'input'" v-model.trim="dialogForm[item.field]" :placeholder="item.placeholder || $t('placeholder.input')" :clearable="item.clearable !== false" :disabled="item.notEditable && dialogTittle != 'add'" />
              <!-- 下拉框 -->
              <el-select v-else-if="item.formType == 'select'" v-model="dialogForm[item.field]" :placeholder="item.placeholder || $t('placeholder.select')" :clearable="item.clearable !== false" :disabled="item.notEditable && dialogTittle != 'add'">
                <el-option v-for="ele in item.options || []" :key="ele[item.optionsconfig.key || item.optionsconfig.label || 'label']" :label="ele[item.optionsconfig.label || 'label']" :value="ele[item.optionsconfig.value || 'value']" />
              </el-select>
              <!-- 日期时间框  -->
              <el-date-picker v-else-if="item.formType.indexOf('date') >= 0" v-model="dialogForm[item.field]" style="width: 100%" :placeholder="item.placeholder || $t('placeholder.select')" :type="item.formType" :clearable="item.clearable !== false" :disabled="item.notEditable && dialogTittle != 'add'" />
              <!-- 输入框 -->
              <el-input v-else placeholder="组件不支持此类型表单请至组件内部自行扩展" disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" style="text-align: center">
        <el-button v-if="dialogTittle != 'view'" type="primary" plain @click="confirm">{{ $t('btn.confirm') }}</el-button>
        <el-button type="danger" plain @click="basicDialog = false">{{ $t('btn.close') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { cloneDeep } from 'loadsh'

export default {
  props: {
    propForm: {
      require: true,
      type: Object,
      default: () => {
        return {}
      },
    },
    propBtn: {
      require: true,
      type: Boolean,
      default: () => {
        return false
      },
    },
    propTable: {
      require: true,
      type: Object,
      default: () => {
        return {}
      },
    },
    propDialog: {
      require: false,
      type: Object,
      default: () => {
        return {}
      },
    },
    propApi: {
      require: true,
      type: Object,
      default: () => {
        return {}
      },
    },
  },
  data() {
    return {
      formList: [], // 查询条件prop相关的过度变量
      table: {}, // 列表prop相关的过度变量
      dialogList: [],
      selectedList: [],
      searchForm: {
        pageNumber: 1,
        pageSize: 10,
      },
      tableList: [],
      total: 0,
      dialogTittle: 'view',
      basicDialog: false,
      dialogForm: {},
    }
  },
  created() {
    // 查询条件处理
    this.formList = cloneDeep(this.propForm.list)
    this.formList.forEach((item) => {
      // 动态添加属性
      this.$set(this.searchForm, item.field, item.fieldValue || null)
      // 如果是select框，并且未传入options配置项时，给optionsconfig弄个默认值
      item.formType == 'select' && !item.optionsconfig && (item.optionsconfig = {})
    })

    // 表格处理
    this.table = cloneDeep(this.propTable)
    // 没传border属性时，border属性默认为 true
    !this.table.hasOwnProperty('border') && (this.table.border = true)

    // 弹窗处理
    this.dialogList = cloneDeep(this.propDialog.list || [])
    this.dialogList.forEach((item) => {
      // 动态添加属性
      this.$set(this.dialogForm, item.field, item.fieldValue || null)
      // 如果是select框，并且未传入options配置项时，给optionsconfig弄个默认值
      item.formType == 'select' && !item.optionsconfig && (item.optionsconfig = {})
    })
  },
  methods: {
    // 提交按钮
    confirm() {
      this.$refs.formDialog.validate(async(valid, obj) => {
        if (valid) {
          if (this.dialogTittle == 'add') {
            const { code } = await this.propApi.add(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          } else {
            const { code } = await this.propApi.edit(this.dialogForm)
            if (code != '200') return
            this.closeDialog(true)
          }
        } else {
          return
        }
      })
    },
    // 关闭弹窗
    closeDialog(bool) {
      bool && this.getData() // 点确定关闭弹窗的时候才会刷新列表
      this.$refs['formDialog'].resetFields()
      this.basicDialog = false
    },
    // 删除操作
    async handleDelete() {
      const { code } = await this.propApi.delete(this.selectedList[0].id)
      if (code != '200') return
      this.getData()
    },
    // 新建操作
    openCreateUser() {
      this.dialogTittle = 'add' // 新建
      this.basicDialog = true
    },
    // 编辑和查看操作
    editDetail(title, row) {
      this.dialogTittle = title
      this.basicDialog = true
      this.$nextTick(() => {
        this.dialogForm = JSON.parse(JSON.stringify(row || this.selectedList[0]))
      })
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.tableList = []
      this.total = 0
    },
    // 查询
    async getData() {
      const { data, code } = await this.propApi.query(this.searchForm)
      if (code != '200') return
      this.tableList = data.records
      this.total = data.total
    },
    // 选择项改变时
    handleSelectionChange(val) {
      this.selectedList = val
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
