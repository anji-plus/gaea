## 简介
此模板初衷是统一公司内前端基础架构、开发规范和UI风格。避免重复性工作，降本增效。

此模板基于 vue 和 element-ui实现。它使用了公司规定的前端技术栈，内置了 i18 国际化解决方案，动态路由，权限验证，提炼了典型的业务模型，提供了常用的功能组件，它可以帮助你快速搭建企业级中后台前端架构。
目前项目主要包含以下功能

- 登录 / 注销 / 首页

- 用户管理
  - 角色管理（动态菜单配置）
  - 用户管理
  - 权限管理
  - 登录日志

- 系统配置
  - 数据字典管理

- 错误页面
  - 401
  - 404

- 全局功能
  - Screenfull全屏 
  - 动态侧边栏（支持多级路由嵌套）
  - 国际化多语言
  - 自适应收缩侧边栏
  - 本地/后端 mock 数据
  - Svg Sprite 图标
  - 富文本编辑器（Tinymce）
  - 快捷导航标签页
  - 动态面包屑
  - 多种动态换肤

- 其他功能 后期完善
## 安装

```bash
# 克隆项目
git clone http://gitlab.anji-allways.com/scaff-work/demo-front-end.git
# 或者
git clone git@gitlab.anji-allways.com:scaff-work/demo-front-end.git

# 进入项目目录
cd demo-front-end

# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 启动服务
npm run dev
```

浏览器访问 http://localhost:8080

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```

## 其它

```bash
# 预览发布环境效果
npm run preview

# 预览发布环境效果 + 静态资源分析
npm run preview -- --report

# 代码格式检查
npm run lint

# 代码格式检查并自动修复
npm run lint -- --fix
```


## 开发规范
请查看[前端开发手册](http://gitlab.anji-allways.com/scaff-work/demo-front-end/blob/master/前端开发手册.docx)