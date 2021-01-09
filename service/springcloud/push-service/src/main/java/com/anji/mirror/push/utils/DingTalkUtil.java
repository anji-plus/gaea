package com.anji.mirror.push.utils;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.taobao.api.FileItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

/**
 * Created by raodeming on 2020/4/1.
 */
@Slf4j
public class DingTalkUtil {


    private int errcode;

    private String accessToken;

    private String errmsg;

    private Long agentId;


    public DingTalkUtil(String appKey, String appSecret, Long agentId) throws Exception {
        String data = getAccessToken(appKey, appSecret);
        JSONObject jsonObject = JSONObject.parseObject(data);
        int errcode = jsonObject.getInteger("errcode");
        String errmsg = jsonObject.getString("errmsg");
        String accessToken = "";
        if (errcode == 0) {
            accessToken = jsonObject.getString("access_token");
        }
        this.errmsg = errmsg;
        this.errcode = errcode;
        this.accessToken = accessToken;
        this.agentId = agentId;
    }

    /**
     * 获取AccessToken凭证
     *
     * @param appKey
     * @param appSecret
     * @return
     * @throws Exception
     */
    public String getAccessToken(String appKey, String appSecret) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest req = new OapiGettokenRequest();
        req.setAppkey(appKey);
        req.setAppsecret(appSecret);
        req.setHttpMethod("GET");
        OapiGettokenResponse rsp = client.execute(req);
        //{"errcode":0,"access_token":"82ad1e1b28b2357dbf090b9a2582319c","errmsg":"ok","expires_in":7200}
        //{"errcode":40096,"errmsg":"不合法的appKey或appSecret"}
        return rsp.getBody();
    }

    /**
     * 获取部门列表
     *
     * @return
     * @throws Exception
     */
    public String getDepartmentList() throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
//            request.setId("123");   //父部门id（如果不传，默认部门为根部门，根部门ID为1）
        request.setHttpMethod("GET");
        OapiDepartmentListResponse rsp = client.execute(request, getAccessToken());
//        System.out.println(rsp.getBody());
        //{"errcode":0,"department":[{"createDeptGroup":true,"name":"rdm团队","id":1,"autoAddUser":true}],"errmsg":"ok"}
        return rsp.getBody();
    }

    /**
     * 获取部门用户
     *
     * @return
     * @throws Exception
     */
    public String getSimplelist(Long departmentId) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(departmentId);
        request.setOffset(0L);
        request.setSize(10L);
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse rsp = client.execute(request, getAccessToken());
//        System.out.println(rsp.getBody());
        //{"errcode":0,"hasMore":false,"errmsg":"ok","userlist":[{"name":"饶德明","userid":"manager9953"},{"name":"钱明","userid":"29073513111206141"},{"name":"李晓燕","userid":"0119371126255792"}]}
        return rsp.getBody();
    }

    /**
     * 根据手机号获取userid
     *
     * @return
     * @throws Exception
     */
    public String getByMobile(String mobile) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
        OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
        request.setMobile(mobile);
        OapiUserGetByMobileResponse execute = client.execute(request, getAccessToken());
//        System.out.println(execute.getBody());
        //{"errcode":0,"errmsg":"ok","userid":"29073513111206141"}
        String body = execute.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.getInteger("errcode").equals(0)) {
            return jsonObject.getString("userid");
        } else {
            log.warn("dingTalk根据手机号:{},获取userId异常：{}", mobile, execute.getBody());
            return "";
        }
    }

    /**
     * 根据手机号获取userid
     *
     * @return
     * @throws Exception
     */
    public String getByMobiles(String mobiles) throws Exception {
        StringBuffer sb = new StringBuffer();
        String[] split = mobiles.split(",");
        for (String s : split) {
            String byMobile = getByMobile(s);
            if (StringUtils.isNotEmpty(byMobile)) {
                sb.append(byMobile).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 发送工作消息通知
     *
     * @return
     * @throws Exception
     */
    public String sendJobNews(String userIds, String jobNews) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userIds);
        request.setAgentId(getAgentId());
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        JSONObject jsonObject = JSONObject.parseObject(jobNews);
        //消息类型
        String msgType = jsonObject.getString("msgType");
        switch (msgType) {
            case "text":
                msg.setMsgtype(msgType);
                msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
                msg.getText().setContent(jsonObject.getString("content"));
                break;
            case "image":
                msg.setMsgtype(msgType);
                msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
                msg.getImage().setMediaId(jsonObject.getString("mediaId"));
                request.setMsg(msg);
                break;
            case "file":
                System.out.println("file");
                msg.setMsgtype(msgType);
                msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
                msg.getFile().setMediaId(jsonObject.getString("mediaId"));
                request.setMsg(msg);
                break;
            case "link":
                msg.setMsgtype(msgType);
                msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
                msg.getLink().setTitle(jsonObject.getString("title"));
                msg.getLink().setText(jsonObject.getString("text"));
                msg.getLink().setMessageUrl(jsonObject.getString("messageUrl"));
                msg.getLink().setPicUrl(jsonObject.getString("picUrl"));
                request.setMsg(msg);
                break;
            case "markdown":
                System.out.println("markdown");
                msg.setMsgtype(msgType);
                msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
                msg.getMarkdown().setText(jsonObject.getString("text"));
                msg.getMarkdown().setTitle(jsonObject.getString("title"));
                request.setMsg(msg);
                break;
            case "oa":
                msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
                msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
                msg.getOa().getHead().setText(jsonObject.getString("text"));
                msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
                msg.getOa().getBody().setContent(jsonObject.getString("content"));
                msg.setMsgtype(msgType);
                request.setMsg(msg);
                break;
            case "action_card":
                msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
                msg.getActionCard().setTitle(jsonObject.getString("title"));
                msg.getActionCard().setMarkdown(jsonObject.getString("markDown"));
                msg.getActionCard().setSingleTitle("singleTitle");
                msg.getActionCard().setSingleUrl(jsonObject.getString("singleUrl"));
                msg.setMsgtype(msgType);
                break;
            default:
                msg.setMsgtype("text");
                msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
                msg.getText().setContent(jsonObject.getString("工作消息类型设置不正确，请忽略本条消息！祝工作愉快"));
                break;
        }
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
//        System.out.println(response.getBody());
        //{"errcode":0,"task_id":166242765538,"request_id":"7kcjpj1pgpgy"}
        return response.getBody();
    }


    /**
     * 发送工作消息通知，卡片形式
     * 相同信息只能发三次
     *
     * @return
     * @throws Exception
     */
    public String sendJobNews(String userIds, String title, String markDown, String singleTiltle, String singleUrl) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userIds);
        request.setAgentId(getAgentId());
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(title);
        msg.getActionCard().setMarkdown(markDown);
        msg.getActionCard().setSingleTitle(singleTiltle);
        msg.getActionCard().setSingleUrl(singleUrl);
        msg.setMsgtype("action_card");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
//        System.out.println(response.getBody());
        //{"errcode":0,"task_id":166242765538,"request_id":"7kcjpj1pgpgy"}
        return response.getBody();
    }

    /**
     * 发送工作消息通知，文本形式
     *
     * @return
     * @throws Exception
     */
    public String sendJobNews1(String userIds, String content) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userIds);
        request.setAgentId(getAgentId());
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(content);
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
//        System.out.println(response.getBody());
        //{"errcode":0,"task_id":166242765538,"request_id":"7kcjpj1pgpgy"}
        return response.getBody();
    }


    /**
     * 上传媒体文件
     *
     * @return
     * @throws Exception
     */
    public String uploadImage(String fileName, InputStream inputStream) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest request = new OapiMediaUploadRequest();
        request.setType("image");
        request.setMedia(new FileItem(fileName, inputStream));
        OapiMediaUploadResponse response = client.execute(request, getAccessToken());
        //{"errcode":0,"errmsg":"ok","media_id":"@lALPDefRvw2qevDMyMzI","created_at":1585793471586,"type":"image"}
        return response.getBody();
    }


    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
}
