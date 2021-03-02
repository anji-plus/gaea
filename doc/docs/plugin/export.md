# 盖亚-导出中心组件

## 一.组件介绍
导出中心组件**gaea-export**需要结合盖亚底盘一起使用，主要提供导出excel和pdf相关文件的导出功能，导出分为excel的简单导出，模板导出，pdf的模板导出三种模式。简单导出采用的*EasyExcel*，模板导出采用的是*Jasperreports*。

导出采用工具类的方式，总共提供了四个导出方法：
方法一（Jasper方式，导出格式为文件，可异步）：
*void exportByFilePathJasper(ExportOperation exportOperation)*
方法二（Jasper方式，导出格式为文件流）：
*byte[] exportByteInfoJasper(ExportOperation exportOperation)*
方法三（EasyExcel方式，导出格式为文件，可异步）：
*void exportByFilePathSimple(ExportOperation exportOperation, Class clazz)*
方法四（EasyExcel方式，导出格式为文件流）：
*byte[] exportByteInfoSimple(ExportOperation exportOperation, Class clazz)*


## 二.组件配置
#### 1.pom.xml中加入依赖如下
```
 <dependency>
        <groupId>com.anji-plus</groupId>
        <artifactId>spring-boot-gaea</artifactId>
 </dependency>

<dependency>
        <groupId>com.anji-plus</groupId>
        <artifactId>spring-boot-starter-gaea-export</artifactId>
 </dependency>
```

#### 2.添加配置如下
```
spring:
  gaea:
    subscribes:
      export:
        enabled: true
```

*注：export 是导出组件的名称，enabled：true表示启用导出组件*

#### 3.启动类添加配置(@EnabledGaeaConfiguration)
```
@SpringBootApplication
@EnableFeignClients
@EnabledGaeaConfiguration
public class AuthApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AuthApplication.class);
    }

}
```

*注：@EnabledGaeaConfiguration表示启用盖亚*
## 三.组件使用示例
组装好需要导出的数据，调用组件工具类ExportUtil方法实现导出，

如果需要直接导出到文件目录，则ExportOperation参数中需要指明文件保存目录
#### 1.简单导出
下面是采用EasyExcel方式导出excel，并保存到设置的文件目录的代码：
```
ExportOperation exportOperation = new ExportOperation();
        //指明导出数据查询到结果开始时间
        exportOperation.setResultStartTime(LocalDateTime.now());
        List<GaeaLog> list=gaeaLogMapper.queryLogInfo(page,gaeaLogParam);
        if(CollectionUtils.isEmpty(list)){
            throw BusinessExceptionBuilder.build(RespCommonCode.LIST_IS_EMPTY);
        }
        page.setRecords(list);
        //指明导出数据查询到结果结束时间
        exportOperation.setResultEndTime(LocalDateTime.now());
        //指明导出数据查询到结果条数
        exportOperation.setResultSize(Long.parseLong(list.size()+""));
        //指明采用什么模式导出
        exportOperation.setExportType(ExportTypeEnum.SIMPLE_EXCEL.getCodeValue());
        //设置导出的文件名
        exportOperation.setFileTitle("Export Log Info");
        //设置导出的文件存放目录
        exportOperation.setFilePath(dictPath);
        //设置导出的数据集合
        exportOperation.setList(list);
        //保存当前操作人
        exportOperation.setCreaterUsername(UserContentHolder.getContext().getUsername());
        //调用盖亚组件实现导出文件
        ExportUtil.getInstance().exportByFilePathSimple(exportOperation,GaeaLog.class);
```

*注：异步可以采用监听事件来获取ExportOperation对象，并获取导出的结果，并保存导出操作记录*

如下，采用事件监听来获取导出结果：
```
@Component
@Slf4j
public class GaeaExportEventListener {
    @Autowired
    private BusinessServiceClient businessServiceClient;

    @EventListener
    @Async
    public void getExportInfo(GaeaExportApplicationEvent event) {
        ExportOperation exportOperation = event.getExportOperation();
        log.info("--gaea-export:requestData:{}", JSON.toJSONString(exportOperation));
        Boolean isSuccess = businessServiceClient.export(exportOperation);
        log.info("--gaea-export:result---{}",isSuccess);
    }
}
```

#### 2.模板导出
- 创建模板，并保存到根目录下，如下:
![1bd16b87956edad0a2e79175241e074dbaf69f97](1bd16b87956edad0a2e79175241e074dbaf69f97.png)

- 模板导出代码示例如下:
```
ExportOperation exportOperation = new ExportOperation();
        exportOperation.setResultSize(100L);
        exportOperation.setFileTitle("字典信息列表");
		//*指定模板ID（对应模板文件名）
        exportOperation.setReportTemplateId("helloword");
        exportOperation.setFilePath("D:\\data\\applogs\\");
        exportOperation.setResultStartTime(LocalDateTime.now());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "hellword");
        List<HashMap> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("Field_1", "Field1-" + i);
            item.put("Field_2", "Field2-" + i);
            list.add(item);
        }
        exportOperation.setResultStartTime(LocalDateTime.now());
        //*设置模板中参数
        exportOperation.setParameters(parameters);
        //*设置记录
        exportOperation.setList(list);
		//保存当前操作人
exportOperation.setCreaterUsername(UserContentHolder.getContext().getUsername());
        //设置导出模式
exportOperation.setExportType(ExportTypeEnum.JASPER_TEMPLATE_EXCEL.getCodeValue());
        ExportUtil.getInstance().exportByFilePathJasper(exportOperation);
```

*注：也可以采用异步导出方式，通过事务监听获取导出结果，并保存对应导出操作记录*


#### 3.直接导出文件流
直接导出文件流，访问方法不一样，就用简单模式举个列子：

```
ExportOperation exportOperation = new ExportOperation();
        //指明导出数据查询到结果开始时间
        exportOperation.setResultStartTime(LocalDateTime.now());
        List<GaeaLog> list=gaeaLogMapper.queryLogInfo(page,gaeaLogParam);
        if(CollectionUtils.isEmpty(list)){
            throw BusinessExceptionBuilder.build(RespCommonCode.LIST_IS_EMPTY);
        }
        page.setRecords(list);
        //指明导出数据查询到结果结束时间
        exportOperation.setResultEndTime(LocalDateTime.now());
        //指明导出数据查询到结果条数
        exportOperation.setResultSize(Long.parseLong(list.size()+""));
        //指明采用什么模式导出
        exportOperation.setExportType(ExportTypeEnum.SIMPLE_EXCEL.getCodeValue());
        //设置导出的文件名
        exportOperation.setFileTitle("Export Log Info");
        //设置导出的数据集合
        exportOperation.setList(list);
        //保存当前操作人
        exportOperation.setCreaterUsername(UserContentHolder.getContext().getUsername());
        //调用盖亚组件实现导出文件
        byte[] bytes=ExportUtil.getInstance().exportByteInfoSimple(exportOperation,GaeaLog.class);
        FileOutputStream fileOutputStream=null;
        try {
            File file =new File(dictPath+exportOperation.getFileId()+".xlsx");
            fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(bytes,0,bytes.length);
            fileOutputStream.flush();
        }catch (Exception e){
            log.error("---error--{}",e.getMessage());
        }finally {
            if(null!=fileOutputStream){
                fileOutputStream.close();
            }
        }
```
