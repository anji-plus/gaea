package com.anji.mirror.push.service;

import com.anji.mirror.push.domain.common.MailParam;
import com.anji.mirror.common.model.PushParamVO;
import com.anji.mirror.push.domain.common.SmsParam;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anjiplus.gaea.push.domain.common.DingTalkParam;
import com.anjiplus.gaea.push.domain.po.TemplatePO;
import com.anjiplus.gaea.push.domain.vo.TemplateVO;
import com.anjiplus.gaea.push.domain.vo.TreeParamVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-16
 */
public interface TemplateService extends IService<TemplatePO> {

    /**
     * 创建
     *
     * @param requestModel
     * @return
     */
    ResponseModel create(RequestModel<TemplateVO> requestModel);

    /**
     * 根据id修改
     *
     * @param requestModel
     * @return
     */
    ResponseModel updateById(RequestModel<TemplateVO> requestModel);

    /**
     * 根据id删除
     *
     * @param requestModel
     * @return
     */
    ResponseModel deleteById(RequestModel<TemplateVO> requestModel);

    /**
     * 根据templateCode 查询TemplatePO
     *
     * @param templateCode
     * @return
     */
    TemplatePO queryByTemplateCode(String templateCode);

    /**
     * 根据id查询一条记录
     *
     * @param requestModel
     * @return
     */
    ResponseModel queryById(RequestModel<TemplateVO> requestModel);

    /**
     * 根据参数分页查询列表
     *
     * @param requestModel
     * @return
     */
    ResponseModel queryByPage(RequestModel<TemplateVO> requestModel);


    /**
     * 预览邮件模板
     *
     * @param requestModel
     * @return
     */
    ResponseModel preViewTemplate(RequestModel<TemplateVO> requestModel);

    /**
     * 发送短信
     *
     * @param smsParam
     * @return
     */
    ResponseModel sendSms(SmsParam smsParam);


    /**
     * 发送推送信息
     *
     * @param pushParamVO
     * @return
     */
    ResponseModel sendPush(PushParamVO pushParamVO);

    /**
     * 发送推送信息
     *
     * @param requestModel
     * @return
     */
    ResponseModel sendPush(RequestModel<PushParamVO> requestModel);


    /**
     * 获取模板树
     * @param requestModel
     * @return
     */
    ResponseModel queryTree(RequestModel<TreeParamVO> requestModel);

    /**
     * 发送email
     *
     * @param mailParam
     * @return
     */
    ResponseModel sendMail(MailParam mailParam);


    /**
     * 钉钉消息推送
     *
     * @param dingTalkParam
     * @return
     */
    ResponseModel dingTalkMsg(DingTalkParam dingTalkParam);

}
