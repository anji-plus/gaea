<template>
  <div class="in-coder-panel">
    <textarea ref="textarea" />
  </div>
</template>

<script type="text/ecmascript-6">
// 引入全局实例
import _CodeMirror from 'codemirror'
import 'codemirror/addon/display/autorefresh'
// 核心样式
import 'codemirror/lib/codemirror.css'
// 引入主题后还需要在 options 中指定主题才会生效
import 'codemirror/theme/cobalt.css'
// 需要引入具体的语法高亮库才会有对应的语法高亮效果
// codemirror 官方其实支持通过 /addon/mode/loadmode.js 和 /mode/meta.js 来实现动态加载对应语法高亮库
// 但 vue 貌似没有无法在实例初始化后再动态加载对应 JS ，所以此处才把对应的 JS 提前引入
import 'codemirror/mode/xml/xml.js'
// 尝试获取全局实例
const CodeMirror = window.CodeMirror || _CodeMirror

export default {
  name: 'InCoder',
  props: {
    // 外部传入的内容，用于实现双向绑定
    // value: String,
    value: {
      type: String,
      default: ''
    },
    // 外部传入的语法类型
    language: {
      type: String,
      default: null
    },
  },
  data() {
    return {
      // 内部真实的内容
      code: '',
      // 默认的语法类型
      mode: 'html',
      // 编辑器实例
      coder: null,
      // 默认配置
      options: {
        // 缩进格式
        tabSize: 2,
        // 主题，对应主题库 JS 需要提前引入
        theme: 'cobalt',
        // 显示行号
        lineNumbers: true,
        line: true,
        autoRefresh: true
      },
      // 支持切换的语法高亮类型，对应 JS 已经提前引入
      // 使用的是 MIME-TYPE ，不过作为前缀的 text/ 在后面指定时写死了
    }
  },
  mounted() {
    // 初始化
    this._initialize()
    this.coder.setOption('mode', `text/html`)
  },
  methods: {
    // 初始化
    _initialize() {
      // 初始化编辑器实例，传入需要被实例化的文本域对象和默认配置
      this.coder = CodeMirror.fromTextArea(this.$refs.textarea, this.options)
      // 编辑器赋值
      this.coder.setValue(this.value || this.code)

      // 支持双向绑定
      this.coder.on('change', (coder) => {
        this.code = coder.getValue()
        if (this.$emit) {
          this.$emit('input', this.code)
        }
      })
    },
    setContent(content) {
      this.coder.setValue(content)
    }
  }
}
</script>

<style lang="scss">
.in-coder-panel {
  flex-grow: 1;
  display: flex;
  position: relative;
  .CodeMirror {
    flex-grow: 1;
    z-index: 1;
    .CodeMirror-code {
      line-height: 19px;
    }
  }
  .code-mode-select {
    position: absolute;
    z-index: 2;
    right: 30px;
    top: 10px;
    max-width: 130px;
  }
}
</style>
