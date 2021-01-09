<template>
  <div class="app-container">
    <el-form
      ref="form"
      :rules="rules"
      :model="form"
      :inline="true"
      label-width="120px"
      label-position="top"
      size="small"
    >
      <el-form-item label="参数名称" prop="settingName">
        <el-input v-model="form.settingName" :disabled="updateModel" />
      </el-form-item>
      <el-form-item label="参数描述" prop="settingLabel">
        <el-input v-model="form.settingLabel" />
      </el-form-item>
      <el-form-item label="参数类型" prop="settingType">
        <code-select v-model="form.settingType" :disabled="updateModel" dictname="SETTING_TYPE" mystyle="width: 100%;" placeholder="请选择"/>
        <!-- <el-input v-model="form.settingType" /> -->
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.remark" />
      </el-form-item>
      <EditForm
        :formValue="form"
        v-model="updateData"
      ></EditForm>
      <!-- 新增 -->
      <div v-if="updateModel == false" class="parameter-add">
        <el-form-item label="参数表单JSON" v-if="form.settingType == 'custom-form'" :rules="[{ required: true }]">
          <el-input
            type="textarea"
            :placeholder="fromJsonPlacehodler"
            v-model="addForm.settingForm"
          ></el-input>
        </el-form-item>

        <el-form-item label="参数表单值JSON" :rules="[{ required: true }]" v-if="form.settingType == 'custom-form'">
          <el-input
            type="textarea"
            :placeholder="fromValueJsonPlacehodler"
            v-model="addForm.settingValue"
          ></el-input>
        </el-form-item>

        <EachForm :item="formItem" v-model="formItemData" @eachChange='eachChange'></EachForm>
      </div>
      <div>
        <!-- 编辑 -->
        <template v-if="updateModel == true">
          <el-form-item label="创建人">
            <el-input :disabled="true" :value="form.createdBy" />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input
              :disabled="true"
              :value="form.createdTime | formatTimestamp"
            />
          </el-form-item>
          <el-form-item label="修改人">
            <el-input :disabled="true" :value="form.updatedBy" />
          </el-form-item>
          <el-form-item label="修改时间">
            <el-input
              :disabled="true"
              :value="form.updatedTime | formatTimestamp"
            />
          </el-form-item>
        </template>
      </div>
      <el-form-item style="width: 100%;">
        <el-button v-if="!isFind" type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button v-else disabled type="primary" size="medium" @click="onSubmit">保存</el-button>
        <el-button size="medium" @click="goBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { reqCreate, reqUpdate, queryById } from "@/api/system/setting";
import CodeSelect from "@/components/codeSelect";
import EditForm from "./editForm";
import EachForm from "@/components/eachForm"
import {deepClone} from "@/utils"

export default {
  components: {
    CodeSelect,
    EditForm,
    EachForm
  },
  data() {
    return {
      form: {
        settingId: null,
        settingName: "",
        settingLabel: "",
        settingType: "",
        settingValue: "",
        settingForm: '',
        remark: "",
        createdBy: "",
        createdTime: null,
        updatedBy: "",
        updatedTime: null
      },
      addForm:{
        settingValue: "",
        settingForm: ""
      },
      rules: {
        settingName: [
          { required: true, message: "请输入参数名称", trigger: "blur" }
        ],
        settingLabel: [
          { required: true, message: "请输入参数描述", trigger: "blur" }
        ],
        settingType: [
          { required: true, message: "请输入参数类型", trigger: "change" }
        ],
        settingValue: [
          { required: true, message: "请输入参数值", trigger: "blur" }
        ]
      },
      updateData: {},
      fromJsonPlacehodler: JSON.stringify([
        {
          type: "input",
          label: "IP地址",
          name: "ip",
          required: true,
          placeholder: ""
        },
        {
          type: "input-number",
          label: "登录失败几次后锁定用户",
          name: "lockAfterLoginFail",
          required: true,
          placeholder: ""
        },
        {
          type: "textarea",
          label: "执行脚本",
          name: "command",
          required: true,
          placeholder: ""
        },
        {
          type: "code-select",
          dictname: "ENABLE_FLAG",
          label: "是否启用",
          name: "enableFlag",
          required: true,
          placeholder: ""
        },
        {
          type: "select",
          label: "支付方式",
          name: "payChannel",
          options: [
            {
              value: "alipay",
              label: "支付宝"
            },
            {
              value: "weixin",
              label: "微信支付"
            },
            {
              value: "jdpay",
              label: "京东支付"
            },
            {
              value: "qqpay",
              label: "QQ钱包"
            },
            {
              value: "unipay ",
              label: "银联支付"
            }
          ],
          required: true,
          placeholder: ""
        },
        {
          type: "radio-group",
          options: ["上海", "北京", "广州", "深圳"],
          label: "活动地点",
          name: "position",
          required: true,
          placeholder: ""
        },
        {
          type: "checkbox-group",
          options: ["现金", "网银", "代付"],
          label: "支付方式",
          name: "payType",
          required: true,
          placeholder: ""
        }
      ],null,'\t'),
      fromValueJsonPlacehodler: JSON.stringify({
          "ip": "129.168.1.1",
          "lockAfterLoginFail": 1,
          "command": "sh /app/startall.sh",
          "dataCenter": "shangqiyun",
          "payChannel": "weixin",
          "position": "上海",
          "payType": ["现金", "网银"]
        },null, '\t'),
      isFind: true  
    };
  },
  computed: {
    updateModel() {
      return this.$route.query.id != null;
    },
    formItem(){
      return {
        "type": this.form.settingType,
        "label": this.form.settingLabel,
        "name": this.form.settingName,
        "required": true,
        "placeholder": ""
      }
    },
    formItemData(){
      return {[this.form.settingName]: this.form.settingValue}
    }
  },
  created() {
    var id = this.$route.query.id;
    this.isFind = this.$route.query.val
    if (id != null) {
      this.form.settingId = id;
      this.queryDetail(id);
    }
  },
  methods: {
    eachChange(val){
      console.log(val, 'gagda');

    },
    queryDetail(settingId) {
      queryById({ settingId }).then(response => {
        if (response.repCode == "0000") {
          this.form = response.repData;
          this.updateData = this.deepClone(this.form)
        }
      });
    },
    onSubmit() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        if (this.$route.query.id) {
          // 为更新除表单之外的值

          this.updateData.settingLabel = this.form.settingLabel
          this.updateData.remark = this.form.remark

          reqUpdate(this.updateData).then(response => {
            if (response.repCode == "0000") {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 2 * 1000
              });
              this.goBack();
            }
          });
        } else {
          if (this.form.settingType != 'custom-form') {
            this.form.settingValue = this.formItemData[this.form.settingName]
          }else{
            this.form.settingValue = this.addForm.settingValue
            this.form.settingForm = this.addForm.settingForm
          }
          let data = deepClone(this.form)
          reqCreate(data).then(response => {
            if (response.repCode == "0000") {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 2 * 1000
              });
              this.goBack();
            }
          });
        }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.line {
  text-align: center;
}
.parameter-add{
  /deep/.el-textarea .el-textarea__inner{
    min-height: 400px!important;
    max-height: 600px!important;
  }
}
</style>

