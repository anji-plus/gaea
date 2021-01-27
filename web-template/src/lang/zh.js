/*
 * @Author: zyk
 * @Date: 2020-07-22 16:59:48
 * @Last Modified by: zyk
 * @Last Modified time: 2021-01-27 10:14:02
 */
export default {
  system: {
    name: '盖亚',
  },
  // 按钮
  btn: {
    view: '查看',
    add: '新增',
    edit: '修改',
    delete: '删除',
    export: '导出',
    import: '导入',
    query: '查询',
    reset: '重置',
    save: '保存',
    confirm: '确定',
    cancel: '取消',
    close: '关闭',
    back: '返回',
    next: '下一步',
    transfer: '穿梭框',
    choose: '选择文件',
    browse: '浏览',
    download: '文件模板下载',
  },
  // form表单占位信息
  placeholder: {
    select: '请选择',
    all: '全部',
    input: '请输入',
  },
  tagsView: {
    refresh: '刷新',
    close: '关闭',
    closeOthers: '关闭其它',
    closeAll: '关闭所有',
  },
  // 系统内弹出提示信息
  promptMessage: {
    success: '操作成功',
    failed: '操作失败',
    netErr: '网络错误',
    reqFailed: '请求失败',
    deleteTip: '确定删除选中数据',
    handleTip: '确定继续当前操作',
    deleteTipTitle: '提示',
    reload: '登录信息失效,请重新登录',
    OOPS: '哎呦！',
    headline: '您无法访问这个页面...',
    info: '请点击下面的按钮返回首页。',
    goHome: '返回首页',
    back: '返回',
    orCan: '或者你可以去:',
    noPer: '你没有权限去该页面',
    unhappy: '如有不满请联系你领导',
  },
  route: {
    dashboard: '首页',
    organization: '组织机构',
    authority: '权限管理',
    btnConfig: '按钮配置',
    menuConfig: '菜单配置',
    role: '角色管理',
    user: '用户管理',
    permission: '接口权限',
    systemSet: '系统设置',
    dataDictionary: '字典管理',
    parameter: '参数管理',
    support: '帮助中心',
    operationLog: '操作日志',
    changePassword: '修改密码',
    pushNotify: '推送管理',
    situation: '收发概况',
    history: '推送历史',
    template: '推送模板',
    download: '导出中心',
    list: '列表页',
    normal: '普通列表页',
  },
  navbar: {
    dashboard: '首页',
    github: '项目地址',
    logOut: '退出登录',
    theme: '换肤',
    size: '布局大小',
    changePassword: '修改密码',
    personal: '个人中心',
    account: '账号',
    organisation: '组织',
  },
  settings: {
    title: '系统布局配置',
    theme: '主题色',
    tagsView: '开启 Tags-View',
    fixedHeader: '固定 Header',
    sidebarLogo: '侧边栏 Logo',
  },
  login: {
    title: '欢迎来到盖亚',
    logIn: '登录',
    username: '用户名/手机号',
    password: '密码',
    nameMsg: '请输入用户名',
    pswMsg: '请输入密码',
    firstLogin: '首次登录请修改密码',
    remPsw: '记住密码',
    forgetPsw: '忘记密码',
  },

  userManage: {
    oldPsw: '旧密码',
    newPsw: '新密码',
    confirmPsw: '确认密码',
    oldPlace: '请输入旧密码',
    newPlace: '格式为8-16位字符，必须包含大小字母和数字',
    confirmPlace: '请再次输入新密码',
    passwordNotEq: '新密码输入不一致',
    changeSuccess: '密码修改成功,请重新登录',
    userId: '用户账号',
    userName: '用户姓名',
    organizeName: '组织名称',
    organizeCode: '组织代码ID',
    role: '角色',
    status: '状态',
    // 角色管理
    roleId: '角色编号',
    roleName: '角色名称',
    described: '描述',
    creator: '创建人',
    creationTime: '创建时间',
    modifyTime: '更改时间',
    modifyUser: '更改人',
    roleManage: '权限管理',
    menuManage: '菜单管理',
    roleRule: '必须以ROLE_开头25位之内的大写字母或数字',
    mobileVilid: '请输入正确的手机号',
    type: '权限类型',
    name: '权限名称',
    attribute: '权限属性',
    value: '权限值',
    isValid: '状态',
    // add
    normal: '已激活',
    invalid: '未激活',
    isDelete: '删除',
    noDelete: '未删除',
    operate: '操作',
    addPer: '添加权限',
    addRole: '添加角色',
    typeName: '类型名称',
    email: '用户邮箱',
    idCard: '身份证',
    mobile: '手机号',
    userRoles: '所属角色',
    tel: '电话',
    lastLoginTime: '最后登录时间',
    frozenTime: '冻结时间',
    password: '用户密码',
    remark: '备注',
    baseInfo: '基础信息',
    roleInfo: '角色信息',
    vilad: '只能输入数字、字母、下划线',
    viladEmail: '邮箱格式错误',
    resetpsw: '确定重置选中用户的密码',
    // 登录日志
    loginId: '用户ID',
    loginResult: '登录结果',
    loginStartTime: '开始时间',
    loginEndTime: '结束时间',
    userAgent: '用户代理信息',
    loginTime: '登录时间',
    clientType: '客户端类型',
    clientIp: '客户端IP',
  },

  // 系统设置页面
  systemConf: {
    bizType: '字典类型',
    bizCode: '字典编码',
    bizText: '字典内容',
    description: '字典描述',
    seq: '排序(SEQ)',
    strongHints: '该操作可能会引起系统异常，请与系统管理员确认后操作',
  },
  // 业务相关
  businessGlossary: {
    isValid: '是否有效',
    yes: '是',
    no: '否',
    fields1: '字段1',
    fields2: '字段2',
    fields3: '字段3',
    fields4: '字段4',
    fields5: '字段5',
    fields6: '字段6',
    fields7: '字段7',
    fields8: '字段8',
    plinput: '请输入城市拼音',
  },
}
