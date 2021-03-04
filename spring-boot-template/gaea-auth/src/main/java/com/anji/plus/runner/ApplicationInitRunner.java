package com.anji.plus.runner;

import com.anji.plus.gaea.GaeaProperties;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.init.InitRequestUrlMappings;
import com.anji.plus.modules.authority.dao.entity.GaeaAuthority;
import com.anji.plus.modules.authority.service.GaeaAuthorityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用启动后执行，获取所有mvc请求信息
 * @author lr
 * @since 2021-03-01
 */
public class ApplicationInitRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(ApplicationInitRunner.class);

    @Autowired
    private InitRequestUrlMappings initRequestUrlMappings;

    @Autowired
    private GaeaAuthorityService gaeaAuthorityService;

    @Autowired
    private GaeaProperties gaeaProperties;

    /**
     * 请求地址
     */
    private final static String REQUEST_URL = "http://%s/gaea/boot/requestInfos";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name:}")
    private String applicationName;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //第一步：处理当前服务，获取当前服务中的请求信息

        //获取当前应用中所有的请求信息
        List<InitRequestUrlMappings.RequestInfo> requestInfos = initRequestUrlMappings.getRequestInfos();

        //处理，从数据库中删除不存在的，同时保存新增的请求信息
        handlerDbRequestInfo(requestInfos, applicationName);


        //第二步：跨服务，跨服务请求其他服务的请求信息

        //微服务时，需要设置获取请求信息的服务spring.application.name
        List<String> requestInfoServiceIds = gaeaProperties.getRequestInfoServiceIds();

        //当spring.gaea.requestInfoServiceIds配置时，需要请求相应服务的请求信息
        if (!CollectionUtils.isEmpty(requestInfoServiceIds)) {
            //获取所有的服务ID
            List<String> serviceIds = discoveryClient.getServices();

            serviceIds.stream().filter(serviceId -> !StringUtils.equals(applicationName, serviceId) && requestInfoServiceIds.contains(serviceId))
                    .forEach(serviceId -> {
                try {
                    //格式请求URL
                    String url = String.format(REQUEST_URL, serviceId);
                    ResponseEntity<ResponseBean> responseEntity = restTemplate.getForEntity(url, ResponseBean.class, new Object() {});
                    System.out.println(responseEntity);
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        ResponseBean body = responseEntity.getBody();
                        List<LinkedHashMap> data = (List<LinkedHashMap>) body.getData();
                        List<InitRequestUrlMappings.RequestInfo> toRequestInfos = toRequestInfos(data);
                        handlerDbRequestInfo(toRequestInfos, serviceId);
                    }
                } catch (Exception e) {
                    return;
                }

            });
        }
    }

    /**
     * 处理请求信息，从数据库中删除不存在的，同时保存新增的请求信息
     * @param requestInfos
     * @param applicationName
     */
    public void handlerDbRequestInfo(List<InitRequestUrlMappings.RequestInfo> requestInfos, String applicationName) {
        List<String> requestPathList = requestInfos.stream().map(InitRequestUrlMappings.RequestInfo::getPath).collect(Collectors.toList());

        LambdaQueryWrapper<GaeaAuthority> authorityWrapper = Wrappers.lambdaQuery();
        authorityWrapper.eq(GaeaAuthority::getApplicationName, applicationName);

        //数据库中的权限信息
        List<GaeaAuthority> dbAuthorities = gaeaAuthorityService.list(authorityWrapper);
        List<String> dbAuthorityPaths = dbAuthorities.stream().map(GaeaAuthority::getPath).collect(Collectors.toList());


        //判断数据库有，新的请求信息没有的，要删除
        List<String> gaeaAuthorities = dbAuthorities.stream()
                .filter(authority -> !requestPathList.contains(authority.getPath()))
                .map(GaeaAuthority::getPath)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(gaeaAuthorities)) {
            logger.info("删除的就请求信息：{}", gaeaAuthorities);
            authorityWrapper.in(GaeaAuthority::getPath, gaeaAuthorities);
            //删除
            gaeaAuthorityService.delete(authorityWrapper);
        }

        //保存时，新的请求信息有，数据库里没有的
        List<InitRequestUrlMappings.RequestInfo> saveRequestInfos = requestInfos.stream().filter(requestInfo -> !dbAuthorityPaths.contains(requestInfo.getPath())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(saveRequestInfos)) {
            //批量保存
            batchSaveRequestInfos(saveRequestInfos);
        }
    }

    /**
     * 处理请求信息并批量保存
     * @param requestInfos
     */
    private List<InitRequestUrlMappings.RequestInfo> toRequestInfos(List<LinkedHashMap> requestInfos) {
        List<InitRequestUrlMappings.RequestInfo> result = requestInfos.stream().map(requestInfo -> {
            InitRequestUrlMappings.RequestInfo info = new InitRequestUrlMappings.RequestInfo();
            info.setApplicationName(requestInfo.get("applicationName").toString());
            info.setAuthority(requestInfo.get("authority").toString());
            info.setBeanName(requestInfo.get("beanName").toString());
            info.setPath(requestInfo.get("path").toString());

            return info;
        }).collect(Collectors.toList());

        return result;
    }


    /**
     * 处理请求信息并批量保存
     * @param requestInfos
     */
    private void batchSaveRequestInfos(List<InitRequestUrlMappings.RequestInfo> requestInfos) {
        List<GaeaAuthority> gaeaAuthorities = requestInfos.stream().map(requestInfo -> {
            GaeaAuthority gaeaAuthority = new GaeaAuthority();
            gaeaAuthority.setApplicationName(requestInfo.getApplicationName());
            gaeaAuthority.setAuthCode(requestInfo.getAuthority());
            gaeaAuthority.setParentCode(requestInfo.getBeanName());
            gaeaAuthority.setPath(requestInfo.getPath());

            return gaeaAuthority;
        }).collect(Collectors.toList());

        //批量保存
        gaeaAuthorityService.insertBatch(gaeaAuthorities);
    }
}
