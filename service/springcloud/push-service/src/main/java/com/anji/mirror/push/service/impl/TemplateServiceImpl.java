package com.anji.mirror.push.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.push.domain.common.SmsTemplateAccount;
import com.anji.mirror.push.domain.po.TemplatePO;
import com.anji.mirror.push.enums.DeleteFlagEnum;
import com.anji.mirror.push.enums.EnableFlagEnum;
import com.anji.mirror.push.utils.DingTalkUtil;
import com.anji.mirror.push.utils.MailMessageUtil;
import com.anji.mirror.push.utils.SendSmsUtil;
import com.anji.mirror.push.utils.TemplateAnalysisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.anji.mirror.common.enums.AlertChannelEnum;
import com.anji.mirror.common.service.RedisService;
import com.anji.mirror.common.utils.ValidateUtil;
import com.anji.mirror.push.domain.common.DingTalkParam;
import com.anji.mirror.push.domain.common.MailParam;
import com.anji.mirror.common.model.PushParamVO;
import com.anji.mirror.push.domain.common.SmsParam;
import com.anji.mirror.push.domain.vo.PushHistoryVO;
import com.anji.mirror.push.domain.vo.TemplateTreeVO;
import com.anji.mirror.push.domain.vo.TemplateVO;
import com.anji.mirror.push.domain.vo.TreeParamVO;
import com.anji.mirror.push.mapper.TemplateMapper;
import com.anji.mirror.push.service.PushHistoryService;
import com.anji.mirror.push.service.TemplateService;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.BeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-16
 */
@Service
@Slf4j
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, TemplatePO> implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private PushHistoryService pushHistoryService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private MailMessageUtil mailMessageUtil;

    @Autowired
    SendSmsUtil sendSmsUtil;

    //排序,优先级越高，优先选择<br/>例：[1,2,3]<br/>1:代表上汽安吉<br/>2.极光<br/>3.阿里
    @Value("${customer.sms.sendOrder}")
    private String sendOrder;

    @Value("${customer.dingTalk.appKey}")
    private String dingTalkAppKey;
    @Value("${customer.dingTalk.appSecret}")
    private String dingTalkAppSecret;
    //应用id
    @Value("${customer.dingTalk.agentId}")
    private Long dingTalkAgentId;

    /**
     * 新增数据
     *
     * @param requestModel
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<TemplateVO> requestModel) {
        //参数校验
        TemplateVO templateVO = requestModel.getData();
        log.error("templateVO---- : {}", templateVO);

        ResponseModel valid = validateCreateFieldNotNull(templateVO);
        if (valid.isError()) {
            return valid;
        }
        //业务校验
        if (null != queryByTemplateCode(templateVO.getTemplateCode())) {//存在
            return RepCodeEnum.EXIST_ERROR.parseError("模板code");
        }
        //业务处理
        String operator = requestModel.getOpUserName();
        if (templateVO.getEnableFlag() == null) {
            templateVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        templateVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        templateVO.setCreatedBy(operator);
        templateVO.setTemplateInfo(TemplateAnalysisUtil.getTemplateInfo(templateVO));
        templateVO.setCreatedTime(LocalDateTime.now());
        templateVO.setUpdatedBy(operator);
        templateVO.setUpdatedTime(LocalDateTime.now());
        TemplatePO templatePO = new TemplatePO();
        BeanUtils.copyProperties(templateVO, templatePO);
        valid = processingDataResults(templateVO, templatePO);
        if (valid.isError()) {
            return valid;
        }
        templatePO = (TemplatePO) valid.getRepData();
        //创建
        boolean flag = save(templatePO);
        //返回结果
        if (flag) {
            return ResponseModel.success(templatePO);
        } else {
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    /**
     * 内部处理新增和更新数据
     *
     * @param templateVO
     * @param templatePO
     * @return
     */
    private ResponseModel processingDataResults(TemplateVO templateVO, TemplatePO templatePO) {
        if (StringUtils.isBlank(templateVO.getTemplateShow())) {
            return RepCodeEnum.BLANK_ERROR.parseError("templateShow");
        }
        if (templateVO.getTemplateType().toLowerCase().equals("mail") || templateVO.getTemplateType().toLowerCase().equals("dingtalk")) { //处理数据
            //templateShow  template paramMap
            TemplatePO templateAnalysis = TemplateAnalysisUtil.analysisTemplate(templateVO.getTemplateShow(), false, templateVO.getTemplateType().toLowerCase().equals("mail"));
            templatePO.setTemplateShow(templateAnalysis.getTemplateShow());
            templatePO.setTemplate(templateAnalysis.getTemplate());
            templatePO.setTemplateParam(JSON.toJSONString(templateAnalysis.getParamMap()));
        } else if (templateVO.getTemplateType().equals("sms")) {
            //转换
            templatePO.setTemplate(templateVO.getTemplateShow());
            templatePO.setTemplateParam(TemplateAnalysisUtil.getTemplateParam(templatePO.getTemplate()));
        }
        return ResponseModel.success(templatePO);
    }

    /**
     * 根据id更新
     *
     * @param requestModel
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<TemplateVO> requestModel) {

        ResponseModel valid = validateUpdateAndDelete(requestModel);
        if (valid.isError()) {
            return valid;
        }
        TemplatePO templatePO = (TemplatePO) valid.getRepData();
        //vo参数
        TemplateVO templateVO = requestModel.getData();
        //更新时间和操作人
        String operator = requestModel.getOpUserName();
        templateVO.setUpdatedBy(operator);
        templateVO.setUpdatedTime(LocalDateTime.now());
        templateVO.setTemplateInfo(TemplateAnalysisUtil.getTemplateInfo(templateVO));
        BeanUtils.copyProperties(templateVO, templatePO, true);
        ResponseModel valid1 = processingDataResults(templateVO, templatePO);
        if (valid1.isError()) {
            return valid1;
        }
        templatePO = (TemplatePO) valid1.getRepData();
        boolean flag = updateById(templatePO);


        //返回结果
        if (flag) {
            return ResponseModel.success(templatePO);
        } else {
            return ResponseModel.fail("修改失败");
        }
    }

    /**
     * 根据id删除 逻辑删除
     *
     * @param requestModel
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<TemplateVO> requestModel) {
        ResponseModel valid = validateUpdateAndDelete(requestModel);
        if (valid.isError()) {
            return valid;
        }
        TemplatePO templatePO = (TemplatePO) valid.getRepData();

        //逻辑删除
        //更新时间和操作人
        String operator = requestModel.getOpUserName();
        templatePO.setUpdatedBy(operator);
        templatePO.setUpdatedTime(LocalDateTime.now());
        templatePO.setDeleteFlag(DeleteFlagEnum.DELETED.getCodeValue());
        boolean flag = updateById(templatePO);

//        boolean flag = removeById(templateId);

        //返回结果
        if (flag) {
            return ResponseModel.success("删除成功");
        } else {
            return ResponseModel.fail("删除失败");
        }
    }

    /**
     * 根据code查询
     *
     * @param templateCode
     * @return
     */
    @Override
    public TemplatePO queryByTemplateCode(String templateCode) {
        if (templateCode == null || "".equals(templateCode)) {
            return null;
        }
        TemplateVO param = new TemplateVO();
        param.setTemplateCode(templateCode);
        QueryWrapper<TemplatePO> wrapper = new QueryWrapper<>();
        wrapper.eq("template_code", templateCode);
        wrapper.eq("delete_flag", DeleteFlagEnum.UNDELETED.getCodeValue());
        TemplatePO template = getOne(wrapper);
        if (null == template) {
            return null;
        }
        return template;
    }

    /**
     * 根据i的查询
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel queryById(RequestModel<TemplateVO> requestModel) {
        //参数校验
        TemplateVO templateVO = requestModel.getData();
        if (templateVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long templateId = templateVO.getTemplateId();
        if (templateId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("模板id");
        }
        //业务处理
        TemplatePO templatePO = getById(templateId);
        if (templatePO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("模板");
        }
        //返回结果
        BeanUtils.copyProperties(templatePO, templateVO);
        TemplateVO templateVOByInfo = TemplateAnalysisUtil.getTemplateVOByInfo(templateVO);
        templateVO.setSmsTemplateAccount(templateVOByInfo.getSmsTemplateAccount());
        return ResponseModel.success(templateVO);
    }

    /**
     * 分页查询
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel queryByPage(RequestModel<TemplateVO> requestModel) {
        //参数校验
        TemplateVO templateVO = requestModel.getData();
        if (templateVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
//        if (StringUtils.isBlank(templateVO.getTemplateType())) {
//            return RepCodeEnum.BLANK_ERROR.parseError("模板类型");
//        }
        //created_time  startTime endTime
        //keyword template_name or template_code
        //分页参数
        Page<TemplateVO> page = new Page<TemplateVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        templateVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        log.info("templateVO: {}", templateVO);
        //业务处理
        IPage<TemplateVO> pageList = templateMapper.queryByPage(page, templateVO);
        pageList.getRecords().stream().forEach(vo -> {
            TemplateVO templateVOByInfo = TemplateAnalysisUtil.getTemplateVOByInfo(vo);
            vo.setSmsTemplateAccount(templateVOByInfo.getSmsTemplateAccount());
        });
        //返回结果
        return ResponseModel.success(pageList);
    }


    /**
     * 预览邮件模板
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel preViewTemplate(RequestModel<TemplateVO> requestModel) {
        //参数校验
        TemplateVO templateVO = requestModel.getData();
        if (templateVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if (StringUtils.isBlank(templateVO.getTemplateType())) {
            return RepCodeEnum.BLANK_ERROR.parseError("模板类型");
        }
        if (StringUtils.isBlank(templateVO.getTemplateShow())) {
            return RepCodeEnum.BLANK_ERROR.parseError("templateShow");
        }
        TemplatePO templatePO = TemplateAnalysisUtil.analysisTemplate(templateVO.getTemplateShow(), true, templateVO.getTemplateType().toLowerCase().equals("mail"));
        Map<String, Object> param = TemplateAnalysisUtil.getPreParam(templatePO.getParamMap());
        String html = TemplateAnalysisUtil.buildHTML(templatePO, param, templatePO.getParamMap(), templateVO.getTemplateType().toLowerCase().equals("mail"));
        if (templateVO.getTemplateType().toLowerCase().equals("dingtalk")) {
            templatePO.setHtml(html.replace("\n", "</br>"));
        } else {
            templatePO.setHtml(html);
        }

        templatePO.setParamMap(TemplateAnalysisUtil.conversionParaMap(templatePO.getParamMap()));
        return ResponseModel.success(templatePO);
    }

    /**
     * 发送短信
     *
     * @param smsParam
     * @return
     */
    @Override
    public ResponseModel sendSms(SmsParam smsParam) {
        ResponseModel valid = validateSmsSendFieldNotNull(smsParam);
        if (valid.isError()) {
            return valid;
        }
        smsParam.setMobiles((String) valid.getRepData());
        if (!StringUtils.isBlank(smsParam.getParam())) {
            smsParam.setParamMap(JSONObject.parseObject(smsParam.getParam()));
        }
        //获取 三方信息
        if (null == smsParam.getContext()) {
            smsParam.setContext("");
        }
        //模板
        TemplateVO templateVO = new TemplateVO();
        templateVO.setTemplateCode(smsParam.getTemplateCode());
        TemplatePO smsTemplatePO = queryByTemplateCode(templateVO.getTemplateCode());
        log.info("smsTemplatePO: {}", smsTemplatePO);
        if (Objects.isNull(smsTemplatePO)) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("模板");
        }
        TemplateVO smsTemplate = new TemplateVO();
        BeanUtils.copyProperties(smsTemplatePO, smsTemplate);
        if (null == smsTemplate || StringUtils.isBlank(smsTemplate.getTemplate())) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("模板");
        }
        String result = "";
        //发送短信历史
        PushHistoryVO pushHistory = new PushHistoryVO();
        pushHistory.setTemplateType("sms");
        pushHistory.setMobiles(smsParam.getMobiles());
        pushHistory.setPushTo(smsParam.getMobiles());
        pushHistory.setTemplateCode(smsTemplatePO.getTemplateCode());
        //默认0
        pushHistory.setSendStatus(0);
        //将smsTemplate的模板转换成可以原子读写的对象引用变量
        AtomicReference<String> template = new AtomicReference<>(smsTemplate.getTemplate());
        Map paramMap = smsParam.getParamMap();
        if (null == paramMap) {
            return RepCodeEnum.NULL_ERROR.parseError("模板参数");
        }
        //构建模板
        paramMap.forEach((key, value) -> {
            //"${code}"
            String concat = "${".concat(key.toString()).concat("}");
            if (template.get().contains(concat)) {
                //将 "${code}" 替换为 对应值
                template.set(template.get().replace(concat, value.toString()));
                log.info("替换模板参数：{}", template);
            }
        });
        //将构建的模板重新赋值
        smsTemplate.setTemplate(template.get());
        pushHistory.setContent(template.get());
        //同一手机统一模板发送限制
        String redisKey = String.format("RUNNING:LIMIT:API:%s-%s", smsParam.getMobiles(), smsParam.getTemplateCode());
        long count = redisService.incr(redisKey, 1L, 3600);
        log.info("redisKey={} phone={}发送短信次数达{}, 一小时最多6次", redisKey, smsParam.getMobiles(), count);
        //同一个ip一小时内只能6次
        if (count > 6) {
            return ResponseModel.fail(RepCodeEnum.USER_FREQUENT_OPERATION_ERROR);
        }
        TemplateVO templateVOByInfo = TemplateAnalysisUtil.getTemplateVOByInfo(smsTemplate);
        SmsTemplateAccount smsTemplateAccount = templateVOByInfo.getSmsTemplateAccount();
        //选择运营商 sendOrder
        //运营商列表
        String sendOrders = sendOrder;
        if (StringUtils.isNoneBlank(smsTemplateAccount.getSendOrder())) {
            sendOrders = smsTemplateAccount.getSendOrder();
        }
        List<Integer> lists = JSONArray.parseArray(sendOrders, Integer.class);
        if (Objects.isNull(lists)) {
            pushHistory.setSendStatus(0);
            pushHistory.setSendResult("短信排序为空");
            savePushHistory(pushHistory);
            return ResponseModel.fail("短信排序为空");
        }
        for (Integer item : lists) {
            if (item == 1) {
                //上汽安吉
                try {
                    pushHistory.setOperator("短信-上汽");
                    result = sendSmsUtil.sendSqajSendSms(smsParam, smsTemplate,
                            smsTemplateAccount.getAjSignName());
                    log.info("上汽发送短信结果：{}", result);
                    if (Objects.nonNull(result)) {
                        if (JSONObject.parseObject(result).getOrDefault("code", "").equals("0000")) {
                            pushHistory.setSendStatus(1);
                            break;
                        } else {
                            pushHistory.setSendStatus(0);
                            break;
                        }
                    }
                } catch (Exception e) {
                    log.error("上汽发送短信失败", e);
                    //失败写入
                    pushHistory.setSendStatus(0);
                    pushHistory.setSendResult(e.getMessage());
                    savePushHistory(pushHistory);
                    return ResponseModel.fail(RepCodeEnum.API_SEND_SMS_ERROR);
                }
            } else if (item == 2) {
                try {
                    //极光
                    result = sendSmsUtil.sendJGSendSms(smsParam.getMobiles(),
                            smsParam.getParamMap(),
                            smsTemplateAccount.getJgSignId(),
                            smsTemplateAccount.getJgTemplateId());
                    pushHistory.setOperator("短信-极光");
                    log.info("极光发送短信结果：{}", result);
                    if (Objects.nonNull(result)) {
                        if (result.contains("msg_id")) {
                            pushHistory.setSendStatus(1);
                            break;
                        } else {
                            pushHistory.setSendStatus(0);
                            break;
                        }
                    }

                } catch (Exception e) {
                    log.error("极光发送短信失败", e);
                    pushHistory.setSendStatus(0);
                    pushHistory.setSendResult(e.getMessage());
                    savePushHistory(pushHistory);
                    return ResponseModel.fail(RepCodeEnum.API_SEND_SMS_ERROR);
                }
            } else if (item == 3) {
                try {
                    //阿里
                    pushHistory.setOperator("短信-阿里");
                    result = sendSmsUtil.sendAliSendSms(smsParam.getMobiles(),
                            smsParam.getParamMap(),
                            smsTemplateAccount.getAliSignName(),
                            smsTemplateAccount.getAliTemplateCode());
                    log.info("阿里发送短信结果：{}", result);
                    if (Objects.nonNull(result)) {
                        if (JSONObject.parseObject(result).getOrDefault("Message", "").equals("OK")) {
                            pushHistory.setSendStatus(1);
                            break;
                        } else {
                            pushHistory.setSendStatus(0);
                            break;
                        }
                    }

                } catch (Exception e) {
                    log.error("阿里发送短信失败", e);
                    pushHistory.setSendStatus(0);
                    pushHistory.setSendResult(e.getMessage());
                    savePushHistory(pushHistory);
                    return ResponseModel.fail(RepCodeEnum.API_SEND_SMS_ERROR);
                }
            }
        }
        pushHistory.setSendResult(result);
        savePushHistory(pushHistory);
        if (pushHistory.getSendStatus() == 1) {
            return ResponseModel.success(result);
        }
        return ResponseModel.fail(result);
    }

    /**
     * 保存历史数据
     *
     * @param pushHistoryVO
     */
    private void savePushHistory(PushHistoryVO pushHistoryVO) {
        if (pushHistoryVO.getTemplateType() == "sms") {
            pushHistoryVO.setPushTitle("短信消息");
        } else if (pushHistoryVO.getTemplateType() == "dingtalk") {
            pushHistoryVO.setPushTitle("钉钉消息");
        }
        if (StringUtils.isBlank(pushHistoryVO.getPushFrom())) {
            pushHistoryVO.setPushFrom("SYSTEM");
        }
        if (StringUtils.isBlank(pushHistoryVO.getCreatedBy())) {
            pushHistoryVO.setCreatedBy("admin");
        }
        pushHistoryVO.setSendTime(LocalDateTime.now());
        pushHistoryVO.setCreatedTime(LocalDateTime.now());
        RequestModel<PushHistoryVO> requestModel = new RequestModel<PushHistoryVO>();
        requestModel.setData(pushHistoryVO);
        pushHistoryService.create(requestModel);
    }

    /**
     * 发送邮件
     *
     * @param mailParam
     * @return
     */
    @Override
    public ResponseModel sendMail(MailParam mailParam) {
        if (mailParam == null) {
            return RepCodeEnum.NULL_ERROR.parseError("邮件对象");
        }
        if (!StringUtils.isBlank(mailParam.getParam())) {
            JSONObject json = JSON.parseObject(mailParam.getParam());
            mailParam.setParamMap(json);
        }
        if (StringUtils.isBlank(mailParam.getTo())) {
            return RepCodeEnum.BLANK_ERROR.parseError("收件人");
        }
        if (StringUtils.isBlank(mailParam.getTemplateCode())) {
            return RepCodeEnum.BLANK_ERROR.parseError("模板编码");
        }
        String result = "";
        log.info("mailParam : {}", mailParam);
        TemplatePO templatePO = queryByTemplateCode(mailParam.getTemplateCode());
        if (Objects.isNull(templatePO)) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("模板");
        }
        templatePO.setTemplateType("mail");
        //保存邮件
        PushHistoryVO pushHistory = new PushHistoryVO();
        pushHistory.setTemplateType("mail");
        pushHistory.setSendStatus(1);
        pushHistory.setSendResult("success");
        pushHistory.setOperator("邮箱");
        pushHistory.setPushTitle(mailParam.getSubject());
        pushHistory.setPushFrom(mailParam.getFrom());
        pushHistory.setPushTo(mailParam.getTo());
        pushHistory.setMailCopy(mailParam.getCopy());
        pushHistory.setContent(this.buildMail(mailParam.getParamMap(), templatePO));
        pushHistory.setTemplateCode(mailParam.getTemplateCode());
        pushHistory.setCreatedBy(mailParam.getFrom());
        try {
            mailMessageUtil.sedMailByMimeMessage(mailParam, pushHistory.getContent());
            //系统发送
            savePushHistory(pushHistory);
            result = "成功";
        } catch (Exception e) {
            pushHistory.setSendStatus(0);
            pushHistory.setSendResult(e.getMessage());
            savePushHistory(pushHistory);
            log.error("邮件发送异常", e);
            return ResponseModel.fail(e.getMessage());
        }
        if (pushHistory.getSendStatus() == 1) {
            return ResponseModel.success(result);
        }
        return ResponseModel.fail(result);
    }

    /**
     * 发送钉钉消息
     *
     * @param dingTalkParam
     * @return
     */
    @Override
    public ResponseModel dingTalkMsg(DingTalkParam dingTalkParam) {
        ResponseModel valid = validateDingTalkMsgFieldNotNull(dingTalkParam);
        if (valid.isError()) {
            return valid;
        }
        dingTalkParam.setMobiles((String) valid.getRepData());
        TemplatePO templatePO = queryByTemplateCode(dingTalkParam.getTemplateCode());
        if (Objects.isNull(templatePO)) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("模板");
        }
        //处理jobNews
        JSONObject message = new JSONObject();
        message.put("msgType", "markdown");
        if (StringUtils.isBlank(dingTalkParam.getSubject())) {
            message.put("title", "钉钉消息");
        } else {
            message.put("title", dingTalkParam.getSubject());
        }
        StringBuffer dingTalkMEssage = new StringBuffer();
        //拿到map 转content
        templatePO.setTemplateType("dingTalk");
        dingTalkMEssage.append(buildMail(dingTalkParam.getParamMap(), templatePO));
        message.put("text", dingTalkMEssage);
        log.info("message   {}", message);
        dingTalkParam.setJobNews(message.toJSONString());
        PushHistoryVO pushHistory = new PushHistoryVO();
        pushHistory.setTemplateType("dingtalk");
        pushHistory.setOperator("钉钉");
        pushHistory.setTemplateCode(templatePO.getTemplateCode());
        pushHistory.setMobiles(dingTalkParam.getMobiles());
        pushHistory.setPushTo(dingTalkParam.getMobiles());
        pushHistory.setContent(dingTalkParam.getJobNews());
        try {
            DingTalkUtil dingTalkUtil = new DingTalkUtil(dingTalkAppKey, dingTalkAppSecret, dingTalkAgentId);
            String byMobiles = dingTalkUtil.getByMobiles(dingTalkParam.getMobiles());
            if (StringUtils.isBlank(byMobiles)) {
                pushHistory.setSendStatus(0);
                pushHistory.setSendResult("群组中找不到该用户");
                savePushHistory(pushHistory);
                return ResponseModel.error("群组中找不到该用户");
            }
            String s = dingTalkUtil.sendJobNews(byMobiles, dingTalkParam.getJobNews());
            Integer errcode = JSONObject.parseObject(s).getInteger("errcode");
            if (errcode.equals(0)) {
                pushHistory.setSendStatus(1);
                pushHistory.setSendResult(s);
                savePushHistory(pushHistory);
                return ResponseModel.success(s);
            }
            pushHistory.setSendStatus(0);
            pushHistory.setSendResult(s);
            savePushHistory(pushHistory);
            return ResponseModel.error(s);
        } catch (Exception e) {
            pushHistory.setSendStatus(0);
            pushHistory.setSendResult(e.getMessage());
            savePushHistory(pushHistory);
            return ResponseModel.error(e.getMessage());
        }
    }

    /**
     * 发送推送信息
     *
     * @param pushParamVO
     * @return
     */
    @Override
    public ResponseModel sendPush(PushParamVO pushParamVO) {
        ResponseModel valid = validateSendPushFieldNotNull(pushParamVO);
        if (valid.isError()) {
            return valid;
        }
        if (pushParamVO.getPushType() == AlertChannelEnum.MAIL.getCodeValue()) {//1
            MailParam model = new MailParam();
            model.setParamMap(pushParamVO.getParamMap());
            model.setTemplateCode(pushParamVO.getTemplateCode());
            model.setSubject(pushParamVO.getSubject());
            model.setBcc(pushParamVO.getBcc());
            model.setTo(pushParamVO.getTo());
            model.setFrom(pushParamVO.getFrom());
            model.setCopy(pushParamVO.getCopy());
            model.setFileMap(pushParamVO.getFileMap());
            return sendMail(model);

        } else if (pushParamVO.getPushType() == AlertChannelEnum.DINGTALK.getCodeValue()) { //2
            DingTalkParam model = new DingTalkParam();
            model.setParamMap(pushParamVO.getParamMap());
            model.setTemplateCode(pushParamVO.getTemplateCode());
            model.setMobiles(pushParamVO.getTo());
            model.setSubject(pushParamVO.getSubject());
            return dingTalkMsg(model);
        } else if (pushParamVO.getPushType() == AlertChannelEnum.SMS.getCodeValue()) {//3
            SmsParam model = new SmsParam();
            model.setParamMap(pushParamVO.getParamMap());
            model.setTemplateCode(pushParamVO.getTemplateCode());
            model.setMobiles(pushParamVO.getTo());
            return sendSms(model);
        }
        return ResponseModel.fail("模板类型有误");
    }


    /**
     * 获取模板树
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel queryTree(RequestModel<TreeParamVO> requestModel) {
        TreeParamVO c = requestModel.getData();
        if (CollectionUtils.isEmpty(c.getTreeList())) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        List<TemplateTreeVO> reqList = c.getTreeList();
        List<TemplateTreeVO> resList = new ArrayList<>();
        try {
            for (TemplateTreeVO vo : reqList) {
                TemplateTreeVO patentVO = new TemplateTreeVO();
                patentVO.setValue(vo.getValue());
                if (vo.getValue() == AlertChannelEnum.MAIL.getCodeValue()) {
                    patentVO.setLabel("邮件");
                    patentVO.setCode("mail");
                } else if (vo.getValue() == AlertChannelEnum.DINGTALK.getCodeValue()) {
                    patentVO.setLabel("钉钉");
                    patentVO.setCode("dingtalk");
                } else if (vo.getValue() == AlertChannelEnum.SMS.getCodeValue()) {
                    patentVO.setLabel("短信");
                    patentVO.setCode("sms");
                } else {
                    //其他 不传
                    continue;
                }
                QueryWrapper<TemplatePO> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("enable_flag", 1);
                queryWrapper.eq("delete_flag", 0);
                queryWrapper.eq("template_type", patentVO.getCode());
                List<TemplatePO> templatePOS = templateMapper.selectList(queryWrapper);
                List<TemplateTreeVO> children = templatePOS.stream().map(s -> {
                    TemplateTreeVO child = new TemplateTreeVO();
                    child.setCode(s.getTemplateCode());
                    child.setValue(s.getTemplateId());
                    child.setLabel(s.getTemplateName());
                    child.setShow(s.getTemplateShow());
                    TemplateVO v = new TemplateVO();
                    v.setTemplateType(s.getTemplateType());
                    v.setTemplateShow(s.getTemplateShow());
                    RequestModel r = new RequestModel();
                    r.setData(v);
                    ResponseModel resp = preViewTemplate(r);
                    if (resp.isSuccess()) {
                        TemplatePO p = (TemplatePO) resp.getRepData();
                        child.setParam(p.getParamMap());
                    }

                    return child;
                }).collect(Collectors.toList());
                patentVO.setChildren(children);
                resList.add(patentVO);
            }
        } catch (Exception e) {
            return ResponseModel.fail(e.getMessage());
        }
        return ResponseModel.success(resList);
    }


    //---------------------发送模板---------------------

    /**
     * 发送推送模板
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel sendPush(RequestModel<PushParamVO> requestModel) {
        //参数校验
        PushParamVO pushParamVO = requestModel.getData();
        return sendPush(pushParamVO);
    }

    /**
     * 构建html
     *
     * @return
     */
    private String buildMail(Map paramMap, TemplatePO templatePO) {
        if (templatePO.getTemplateParam() != null) {
            JSONObject json = JSON.parseObject(templatePO.getTemplateParam());
            templatePO.setParamMap(json);
        }
        String html = TemplateAnalysisUtil.buildHTML(templatePO, paramMap, templatePO.getParamMap(), templatePO.getTemplateType().toLowerCase().equals("mail"));
        return html;
    }

    //---------------------校验---------------------

    /**
     * 检验发送推送信息
     *
     * @param pushParamVO
     * @return
     */
    private ResponseModel validateSendPushFieldNotNull(PushParamVO pushParamVO) {
        if (pushParamVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        if (Objects.isNull(pushParamVO.getPushType())) {
            return RepCodeEnum.NULL_ERROR.parseError("推送类型");
        }
        if (StringUtils.isBlank(pushParamVO.getTemplateCode())) {
            return RepCodeEnum.NULL_ERROR.parseError("模板code");
        }
        return ResponseModel.success();
    }

    /**
     * 校验钉钉消息推送
     *
     * @param dingTalkParam
     * @return
     */
    private ResponseModel validateDingTalkMsgFieldNotNull(DingTalkParam dingTalkParam) {
        if (dingTalkParam == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("请求数据");
        }
        if (StringUtils.isBlank(dingTalkParam.getTemplateCode())) {
            return RepCodeEnum.BLANK_ERROR.parseError("模板code");
        }
        if (Objects.isNull(dingTalkParam.getParamMap())) {
            return RepCodeEnum.BLANK_ERROR.parseError("消息内容");
        }
        ResponseModel responseModel = validateMobiles(dingTalkParam.getMobiles());
        return responseModel;
    }

    /**
     * 检验发送验证码
     *
     * @param smsParam
     * @return
     */
    private ResponseModel validateSmsSendFieldNotNull(SmsParam smsParam) {
        if (smsParam == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        if (StringUtils.isBlank(smsParam.getTemplateCode())) {
            return RepCodeEnum.NULL_ERROR.parseError("模板code");
        }
        ResponseModel responseModel = validateMobiles(smsParam.getMobiles());
        return responseModel;
    }

    /**
     * 根据数据库必填项，校验是否为空，不校验主键
     *
     * @param templateVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(TemplateVO templateVO) {
        if (templateVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        if (StringUtils.isBlank(templateVO.getTemplateType())) {
            return RepCodeEnum.NULL_ERROR.parseError("模板类型");
        }
        if (StringUtils.isBlank(templateVO.getTemplateCode())) {
            return RepCodeEnum.NULL_ERROR.parseError("模板Code");
        }

        if (StringUtils.isBlank(templateVO.getTemplateName())) {
            return RepCodeEnum.NULL_ERROR.parseError("模板名称");
        }
        return ResponseModel.success();
    }


    /**
     * 模板更新和模板删除（逻辑删除）校验
     *
     * @param requestModel
     * @return
     */
    private ResponseModel validateUpdateAndDelete(RequestModel<TemplateVO> requestModel) {
        //参数校验
        TemplateVO templateVO = requestModel.getData();
        if (templateVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        Long templateId = templateVO.getTemplateId();
        if (templateId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("模板id");
        }
        //业务处理
        TemplatePO templatePO = getById(templateId);
        if (templatePO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("模板");
        }
        return ResponseModel.success(templatePO);
    }

    /**
     * 校验多个手机号
     * @param mobiles
     * @return
     */
    private ResponseModel validateMobiles(String mobiles){
        //先校验手机号是否存在
        if (StringUtils.isBlank(mobiles)) {
            return RepCodeEnum.NULL_ERROR.parseError("手机号");
        }
        mobiles = mobiles.replace("，", ",");
        String[] split = mobiles.split(",");
        Set<String> collect = Arrays.stream(split).collect(Collectors.toSet());
        for (String phone : collect) {
            if (!ValidateUtil.isMobile(phone)) {
                return RepCodeEnum.PARAM_FORMAT_ERROR.parseError("手机号:".concat(phone));
            }
        }
        //校验成功再重新赋值
        mobiles = collect.stream().collect(Collectors.joining(","));
        ResponseModel r = new ResponseModel();
        r.setRepCodeEnum(RepCodeEnum.SUCCESS);
        r.setRepData(mobiles);
        return r;
    }
}
