
export default [
  // 登录接口
  {
    url: '/v1/login',
    type: 'post',
    response: config => {
      try{
        const { username } = config.body
        return {
          code:"2000",
          data:{
            jwt:"token",
            userdetail:{
              displayName:"develop",
              firstLogin:false,
              userId:'develop',
              username:username
            },
          },
          msg:"成功"
        }
      }catch(e){
        return {
          code: "5001",
          msg: e
        }
      }
      
    }
  },

  // 获取菜单接口
  {
    url: '/v1/menu/token\.*',
    type: 'get',
    response: config => {
      const token = config.url.split('/v1/menu/token/')[1]
      try{
        return {
          code: '2000',
          data: [],
          msg: ''
        }
      }catch(e){
        return {
          code: "5001",
          msg: e
        }
      }
      
        

    }
  },

  // user logout
  {
    url: '/v1/logout',
    type: 'post',
    response: _ => {
      return {
        code: 2000,
        data: 'success'
      }
    }
  }
]
