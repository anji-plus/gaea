package com.anjiplus.gaea.push.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by raodeming on 2019/11/25.
 */
@Slf4j
//@Component
//@RefreshScope
public class IpInterceptor extends HandlerInterceptorAdapter {


    public IpInterceptor(String ipFrequency) {
        this.ipFrequency = ipFrequency;
    }

    //    @Value("${captcha.limit.ip.frequency:10000}")
    private String ipFrequency;

    /**
     * ip校验
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
//        String body = requestWrapper.getBody();
//        JSONObject jsonObject = JSONObject.parseObject(body);
//        //ip限制请求：五分钟10000次
//        log.info(body);
//        String sourceIP = jsonObject.getString("sourceIP");
//        if (StringUtils.isNotBlank(sourceIP)) {
//            BeanFactory factory = WebApplicationContextUtils
//                    .getRequiredWebApplicationContext(httpServletRequest.getServletContext());
//            RedisService redisService = (RedisService) factory.getBean("redisServiceImpl");
//            String redisKey = String.format("RUNNING:LIMIT:CAPTCHA:%s", sourceIP);
//            redisKey = redisKey.replace(".", "_");
//            long count = redisService.incr(redisKey, 1L, 300);
//            log.info("redisKey={} ip={}获取行为验证码次数达{}, 最多{}次", redisKey, sourceIP, count, ipFrequency);
//            //同一个ip五分钟内限制10000次
//            if (count > Integer.parseInt(ipFrequency)) {
//                httpServletResponse.setCharacterEncoding("UTF-8");
//                httpServletResponse.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
//                httpServletResponse.getWriter().print(JSONObject.toJSONString(ResponseModel.fail(RepCodeEnum.USER_FREQUENT_OPERATION_ERROR)));
//                return false;
//            }
//        }
        return true;
    }
}
