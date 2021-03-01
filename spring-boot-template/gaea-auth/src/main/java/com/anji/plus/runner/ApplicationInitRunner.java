package com.anji.plus.runner;

import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.init.InitRequestUrlMappings;
import com.anji.plus.modules.authority.dao.entity.GaeaAuthority;
import com.anji.plus.modules.authority.service.GaeaAuthorityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用启动后执行
 * @author lr
 * @since 2021-03-01
 */
public class ApplicationInitRunner implements ApplicationRunner {

    @Autowired
    private InitRequestUrlMappings initRequestUrlMappings;

    @Autowired
    private GaeaAuthorityService gaeaAuthorityService;

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

        //获取当前应用中所有的请求信息
        List<InitRequestUrlMappings.RequestInfo> requestInfos = initRequestUrlMappings.getRequestInfos();

        //删除
        gaeaAuthorityService.delete(new LambdaQueryWrapper<>());

        //批量保存
        batchSaveRequestInfos(requestInfos);

        //TODO 判断是否是微服务

        //获取所有的服务ID
        List<String> services = discoveryClient.getServices();

        services.stream().filter(s -> !StringUtils.equals(applicationName, s)).forEach(s -> {
            try {
                if (StringUtils.equals("gaea-gateway", s)) {
                    return;
                }

                String url = String.format(REQUEST_URL, s);
                ResponseEntity<ResponseBean> responseEntity = restTemplate.getForEntity(url, ResponseBean.class, new Object() {});
                System.out.println(responseEntity);
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    ResponseBean body = responseEntity.getBody();
                    List<LinkedHashMap> data = (List<LinkedHashMap>) body.getData();
                    batchSaveMapRequestInfos(data);
                }
            } catch (Exception e) {
                return;
            }

        });


    }


    private void batchSaveMapRequestInfos(List<LinkedHashMap> requestInfos) {
        List<GaeaAuthority> gaeaAuthorities = requestInfos.stream().map(requestInfo -> {
            GaeaAuthority gaeaAuthority = new GaeaAuthority();
            gaeaAuthority.setApplicationName(requestInfo.get("applicationName").toString());
            gaeaAuthority.setAuthCode(requestInfo.get("authority").toString());
            gaeaAuthority.setParentCode(requestInfo.get("beanName").toString());
            gaeaAuthority.setPath(requestInfo.get("path").toString());

            return gaeaAuthority;
        }).collect(Collectors.toList());

        //批量保存
        gaeaAuthorityService.insertBatch(gaeaAuthorities);
    }

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

    public static void main(String[] args) {
        String url = String.format(REQUEST_URL, "localhost:9091");
        ResponseEntity<ResponseBean> stringResponseEntity = new RestTemplate().getForEntity(url, ResponseBean.class, new Object() {});

        System.out.println(stringResponseEntity);
    }
}
