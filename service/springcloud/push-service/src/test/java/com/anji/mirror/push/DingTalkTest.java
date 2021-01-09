package com.anji.mirror.push;

import com.anji.mirror.push.utils.DingTalkUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiChatSendResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import org.junit.Test;

/**
 * Created by raodeming on 2020/4/1.
 */
public class DingTalkTest {

    private static String accessToken = "13241234asrfqewrqwerwqefsdafdsfasd";

    /**
     * 获取凭证
     * @param
     */
    @Test
    public void test1() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey("asdfasdfqw3423");
            req.setAppsecret("asdfasdfasdfqww45233423423asdf-FU-asdfasdf3243423412");
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());
            //{"errcode":0,"access_token":"82ad1e1b28b2357dbf090b9a2582319c","errmsg":"ok","expires_in":7200}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取jsapi ticket
     */
    @Test
    public void test2(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
            OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
            req.setHttpMethod("GET");
            OapiGetJsapiTicketResponse rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());
            //{"errcode":0,"errmsg":"ok","ticket":"3QNaIAj95t4XaaBrjv2H6ujsCPid0MbNqaEJgPLGXs4hkkUoey3JHheqntRxeckCCvSvquagyOUWNhkGFrAApv","expires_in":7200}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取部门列表
     */
    @Test
    public void test3(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest request = new OapiDepartmentListRequest();
//            request.setId("123");
            request.setHttpMethod("GET");
            OapiDepartmentListResponse rsp = client.execute(request, accessToken);
            System.out.println(rsp.getBody());

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取部门用户userid列表
     */
    @Test
    public void test4(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
            OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
            req.setDeptId("45140491");
            req.setHttpMethod("GET");
            OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());
            //{"errcode":0,"errmsg":"ok","userIds":["29073513111206141","0119371126255792","manager9953"]}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取部门用户
     */
    @Test
    public void test5(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
            OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
            request.setDepartmentId(45140491L);
            request.setOffset(0L);
            request.setSize(10L);
            request.setHttpMethod("GET");
            OapiUserSimplelistResponse rsp = client.execute(request, accessToken);
            System.out.println(rsp.getBody());

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机号获取userid
     */
    @Test
    public void test6(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
            OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
            request.setMobile("13122222222");

            OapiUserGetByMobileResponse execute = client.execute(request, accessToken);
            System.out.println(execute.getBody());
            //{"errcode":60121,"errmsg":"找不到该用户"}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户详情
     */
    @Test
    public void test7(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid("032915350626168420");
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
            System.out.println(response.getBody());
            //{"errcode":0,"unionid":"L9jdFNXDOop5n4wmX3WlyQiEiE","userid":"29073513111206141","isLeaderInDepts":"{1:false}","isBoss":false,"isSenior":false,"department":[1],"orderInDepts":"{1:176332680479820512}","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"tags":{},"isHide":false,"name":"钱明"}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送工作通知消息
     */
    @Test
    public void test8(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setUseridList("423412341234123");
            request.setAgentId(222222226L);
            request.setToAllUser(false);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();


            msg.setMsgtype("markdown");
            msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
//            msg.getMarkdown().setText("### 口岸后台服务心跳告警 \n" +
//                    "#### 告警时间: 2020-04-13 10:42:42 \n" +
//                    "#### 告警信息： \n  " +
//                    "时间: 2020-04-13 10:42:29   \n  " +
//                    "维度: node=business_89 status=0  \n  " +
//                    "指标: status=0 ");
            msg.getMarkdown().setText("### 口岸后台服务心跳告警 \n" +
                    "#### 告警时间: 2020-04-13 10:42:42 \n" +
                    "#### 告警信息： \n  " +
                    "时间: 2020-04-13 10:42:29   \n  " +
                    "维度: node=business_89 status=0  \n  " +
                    "指标: status=0 ");
            msg.getMarkdown().setTitle("口岸后台服务心跳告警");
            request.setMsg(msg);
//
//            msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
//            msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
//            msg.getOa().getHead().setText("head");
//            msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
//            msg.getOa().getBody().setContent("xxx");
//            msg.setMsgtype("oa");
//            request.setMsg(msg);
//
//            msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
//            msg.getActionCard().setTitle("大通VDC心跳告警");
//            msg.getActionCard().setMarkdown("# 睛灵监控 \n## 大通VDC心跳告警  \n* 2020-2-2 10:10:10心跳超时 \n![alt 啊](https://img.alicdn.com/tps/TB1XLjqNVXXXXc4XVXXXXXXXXXX-170-64.png)");
//            msg.getActionCard().setSingleTitle("查看详情");
//            msg.getActionCard().setSingleUrl("https://mirror.anji-plus.com/");
//            msg.setMsgtype("action_card");
//            request.setMsg(msg);


            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,accessToken);
            System.out.println(response.getBody());
            //{"errcode":0,"task_id":166242765538,"request_id":"7kcjpj1pgpgy"}
            //{"errcode":0,"task_id":167507839628,"request_id":"6ezzf5jtw43t"}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送群消息
     */
    @Test
    public void test9(){
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/chat/send");
            OapiChatSendRequest request = new OapiChatSendRequest();
            request.setChatid("1");

            OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
            msg.setMsgtype("text");
            OapiChatSendRequest.Text text = new OapiChatSendRequest.Text();
            text.setContent("测试文本信息");
            msg.setText(text);

            request.setMsg(msg);
            OapiChatSendResponse response = client.execute(request, accessToken);
            System.out.println(response.getBody());
            //{"errcode":0,"unionid":"L9jdFNXDOop5n4wmX3WlyQiEiE","userid":"29073513111206141","isLeaderInDepts":"{1:false}","isBoss":false,"isSenior":false,"department":[1],"orderInDepts":"{1:176332680479820512}","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"tags":{},"isHide":false,"name":"钱明"}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传媒体文件
     */
    @Test
    public void test10(){
        try {
            DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
            OapiMediaUploadRequest request = new OapiMediaUploadRequest();
            request.setType("image");
            request.setMedia(new FileItem("C:\\Users\\raodeming\\Desktop\\logo.png"));
            OapiMediaUploadResponse response = client.execute(request,accessToken);
            System.out.println(response.getBody());
//            {"errcode":0,"errmsg":"ok","media_id":"@lALPDfYHulTbaZbMyMzI","created_at":1586313431963,"type":"image"}
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            DingTalkUtil dingtalkUtil = new DingTalkUtil("13241234fasfaeswf", "i4aS6-234123421341234asdf-APiFp", 656418016L);
            String s = dingtalkUtil.getSimplelist(1L);
            System.out.println("------------");
            System.out.println(s);
            String byMobile = dingtalkUtil.getByMobiles("17754223939,15317177413");
            System.out.println(byMobile);
//            String test = dingRalkUtil.sendJobNews(byMobile, "测试消息");
//            System.out.println(test);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
