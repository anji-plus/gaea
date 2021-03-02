# 盖亚-审计日志组件
### 一.组件介绍
审计日志组件**gaea-log**需要结合盖亚底盘一起使用，通过AOP方式获取每个请求的相关数据（请求和返回数据）。后端直接通过注解方式可以获取对应方法的请求数据。注解中可以指明请求title，是否保存请求参数（默认保存），是否保存返回参数（默认不保存）。

单体架构，可以采用事件监听获取请求相关数据。
微服务架构，可以配置回调路径来获取请求相关数据。

### 二.组件配置
#### 1.pom.xml添加依赖
```
<dependency>
     <groupId>com.anji-plus</groupId>
     <artifactId>spring-boot-gaea</artifactId>
</dependency>

<dependency>
     <groupId>com.anji-plus</groupId>
     <artifactId>spring-boot-starter-gaea-log</artifactId>
     <version>1.0-SNAPSHOT</version>
</dependency>
```

#### 2.添加配置
```
spring:
  gaea:
    subscribes:
      audit-log:
        enabled: true
        callback-url: http://*****
        publish-event: true
```

*注：当微服务架构下，配置callback-url来获取请求日志信息；当单体架构下，只需要配置publish-event:true，需要监听事件来获取日志信息*

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
### 三.组件使用示例
- 直接在controller层对应方法上引用注解@GaeaAuditLog
```
	/**
     * 用户修改密码
     * @param reqParam
     * @return
     */
    @PostMapping("/updatePassword")
    @GaeaAuditLog(pageTitle = "修改密码",isSaveResponseData = true)
    public ResponseBean updatePassword(@RequestBody GaeaUserPasswordParam reqParam){
        return responseSuccessWithData(gaeaUserService.updatePassword(reqParam));
    }
```

- 如果是微服务架构，需要写一个回调的方法，用来获取捕捉到的日志信息,示例代码如下：
```
	@RequestMapping("/callback")
    public ResponseBean callback(@RequestBody LogOperation logOperation){
        gaeaLogService.saveCallbackInfo(logOperation);
        return responseSuccess();
    }

```

- 如果是单体架构，需要监听一个事件，用来获取捕捉到的日志信息，示例代码如下：
```
@Component
@Slf4j
public class GaeaAuditLogEventListener {

    @EventListener
    @Async
    public void getExportInfo(AuditLogApplicationEvent event){
        LogOperation logOperation= event.getLogOperation();
        log.info("----收到的日志信息--{}", JSON.toJSONString(logOperation));
    }
}
```
