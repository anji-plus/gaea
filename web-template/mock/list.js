const total = 100

function createData(pageNum,pageSize) {
  let listData= []
  for (let index = 0; index <= total; index++) {
    if(index > (pageNum-1)*pageSize  && index <= pageNum*pageSize ){
      listData.push({
        fields1: `row${index}Fields1`,
        fields2: `row${index}Fields2`,
        fields3: `row${index}Fields3`,
        fields4: `row${index}Fields4`,
        fields5: `row${index}Fields5`,
        fields6: `一二三四五六七八九十一二三四五`,
        fields7: `row${index}Fields7`,
        fields8: `row${index}Fields8`
      })
    }
  }
  return listData
}


export default [
  // 列表数据接口
  {
    url: '/v1/list',
    type: 'get',
    response: config => {
      try{
        const { pageNum, pageSize } = config.query
        const list = createData(pageNum, pageSize)
        return {
          code:"2000",
          data:{
            list:list,
            total:total
          },
          msg:"success"
        }
      }catch(e){
        return {
          code: "5001",
          msg: e
        }
      }
      
    }
  }
]
