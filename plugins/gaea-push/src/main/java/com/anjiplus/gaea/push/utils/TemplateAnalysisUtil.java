package com.anjiplus.gaea.push.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anjiplus.gaea.push.domain.common.SmsTemplateAccount;
import com.anjiplus.gaea.push.domain.common.TParam;
import com.anjiplus.gaea.push.domain.po.TemplatePO;
import com.anjiplus.gaea.push.domain.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 工具类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-16
 */
@Slf4j
public class TemplateAnalysisUtil {
    private static String htmlHead = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\"><title>Document</title></head><body>";
    private static String htmlEnd = "</body></html>";
    private static String[] keyWord = new String[]{"javascript", "vbscript", "expression", "applet", "meta", "xml", "blink", "link", "script", "embed", "object", "iframe", "frame", "frameset", "ilayer", "layer", "bgsound", "title", "base", "onabort", "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate", "onbeforecopy", "onbeforecut", "onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste", "onbeforeprint", "onbeforeunload", "onbeforeupdate", "onblur", "onbounce", "oncellchange", "onchange", "onclick", "oncontextmenu", "oncontrolselect", "oncopy", "oncut", "ondataavailable", "ondatasetchanged", "ondatasetcomplete", "ondblclick", "ondeactivate", "ondrag", "ondragend", "ondragenter", "ondragleave", "ondragover", "ondragstart", "ondrop", "onerror", "onerrorupdate", "onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout", "onhelp", "onkeydown", "onkeypress", "onkeyup", "onlayoutcomplete", "onload", "onlosecapture", "onmousedown", "onmouseenter", "onmouseleave", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onmousewheel", "onmove", "onmoveend", "onmovestart", "onpaste", "onpropertychange", "onreadystatechange", "onreset", "onresize", "onresizeend", "onresizestart", "onrowenter", "onrowexit", "onrowsdelete", "onrowsinserted", "onscroll", "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", "onunload"};

    static Pattern param = Pattern.compile("\\{\\s*[a-zA-Z0-9]+\\s*((\\.|\\:)\\s*[a-zA-Z0-9]+)?\\s*\\}");
    static Pattern list = Pattern.compile("\\{\\s*((listbegin\\s*\\:\\s*[a-zA-Z0-9]+\\s*\\:\\s*[a-zA-Z0-9]+)|(listend))+\\s*\\}");

    private final static String cid = "cid:";

    private final static String text_type = "text";
    private final static String pic_type = "pic";

    private final static String paramSplit = "#%#";


    /**
     * 分析模板
     * // .$|()[{^?*+\\  特殊字符作为分隔符时需要使用\\进行转义
     * 表示空格  " \\s"， "[ ]"， "[\\s]"
     * 1、 表示空格  " \\s"， "[ ]"， "[\\s]"
     * <p>
     * 表示多个空格 "\\s+"， "[ ]+"， "[\\s]+"
     * <p>
     * 2、 表示数字   "\\d"， "[\\d]"， "[0-9]"
     * <p>
     * 表示多个数字，同理，在后面加上"+"
     *
     * @param userTemplate
     * @param isPreView
     * @return
     */
    public static TemplatePO analysisTemplate(String userTemplate, boolean isPreView, boolean isMail) {

        TemplatePO templatePO = new TemplatePO();
        templatePO.setTemplateShow(userTemplate);
        String templete = new String(userTemplate);
        templete = templete.toLowerCase();
        if (isMail) {
            templete = templete.replaceAll("\\s+", "");
            templete = templete.replaceAll("\n+", "<br/>");
            templete = templete.replaceAll("<\\s*((a))", "");
            templete = templete.replaceAll("(javascript)|(vbscript)|(expression)|(applet)|(meta)|(xml)|(blink)|(link)|(script)|(embed)|(object)|(iframe)|(frame)|(frameset)|(ilayer)|(layer)|(bgsound)|(title)|(base)|(onabort)|(onactivate)|(onafterprint)|(onafterupdate)|(onbeforeactivate)|(onbeforecopy)|(onbeforecut)|(onbeforedeactivate)|(onbeforeeditfocus)|(onbeforepaste)|(onbeforeprint)|(onbeforeunload)|(onbeforeupdate)|(onblur)|(onbounce)|(oncellchange)|(onchange)|(onclick)|(oncontextmenu)|(oncontrolselect)|(oncopy)|(oncut)|(ondataavailable)|(ondatasetchanged)|(ondatasetcomplete)|(ondblclick)|(ondeactivate)|(ondrag)|(ondragend)|(ondragenter)|(ondragleave)|(ondragover)|(ondragstart)|(ondrop)|(onerror)|(onerrorupdate)|(onfilterchange)|(onfinish)|(onfocus)|(onfocusin)|(onfocusout)|(onhelp)|(onkeydown)|(onkeypress)|(onkeyup)|(onlayoutcomplete)|(onload)|(onlosecapture)|(onmousedown)|(onmouseenter)|(onmouseleave)|(onmousemove)|(onmouseout)|(onmouseover)|(onmouseup)|(onmousewheel)|(onmove)|(onmoveend)|(onmovestart)|(onpaste)|(onpropertychange)|(onreadystatechange)|(onreset)|(onresize)|(onresizeend)|(onresizestart)|(onrowenter)|(onrowexit)|(onrowsdelete)|(onrowsinserted)|(onscroll)|(onselect)|(onselectionchange)|(onselectstart)|(onstart)|(onstop)|(onsubmit)|(onunload)", "");
            templete = templete.replaceAll("<\\s*((a|A)|([S|s][C|c][R|r][I|i][P|p][T|t]))", "");
        }
        templete = templete.replaceAll("", "");
        templete = templete.replaceAll("", "");
        templete = templete.replaceAll("\\\\\\s*\\{", "##{##");
        templete = templete.replaceAll("\\\\\\s*\\}", "##}##");
        templatePO.setTemplate(templete);

        List<TParam> listParamsList = new ArrayList<TParam>();
        Map paramMap = new HashMap();
        templete = analysisList(templete, listParamsList, paramMap);

        List<String> paramList = new ArrayList<String>();
        templete = analysisParam(templete, paramList);

        for (String param : paramList) {
            if (param.indexOf(cid) != -1) {
                param = param.replace(cid, "");
                paramMap.put(param, pic_type);
            } else
                paramMap.put(param, text_type);
        }

        templete = templete.replaceAll("##\\{##", "{");
        templete = templete.replaceAll("##\\}##", "}");
        System.out.println(templete);
        templatePO.setParamMap(paramMap);
        templatePO.setTemplate(templete);
        return templatePO;
    }

    /**
     * 转换 paramMap
     *
     * @param map
     * @return
     */
    public static Map conversionParaMap(Map map) {
        Map paramMap = new HashMap();
        if (Objects.nonNull(map)) {
            for (Object key : map.keySet()) {
                //value 是 TParam类 代表是list
                if (map.get(key) instanceof TParam) {
                    //初始化子map
                    TParam subMap = (TParam) map.get(key);
                    //itemVar 的value 为 map的key
                    String listKey = subMap.getListParam();
                    //list 不为空 且 listKey不为空
                    if (!subMap.getItemParamList().isEmpty() && StringUtils.isNoneBlank(listKey)) {
                        Map listMap = new HashMap();
                        for (Object itemKey : subMap.getItemParamList()) {
                            listMap.put(itemKey, text_type);
                        }
                        //遍历结束 赋值
                        List listMapList = new ArrayList();
                        listMapList.add(listMap);
                        //将遍历的listMapList放入内部
                        paramMap.put(listKey, listMapList);
                    }
                } else {
                    paramMap.put(key, map.get(key));
                }
            }
        } else {
            return null;
        }

        return paramMap;
    }


    /**
     * 分析数组
     *
     * @param template
     * @param listParamList
     * @param paramMap
     * @return
     */
    private static String analysisList(String template, List<TParam> listParamList, Map paramMap) {

        //正则匹配
        Matcher listMatch = list.matcher(template);
        String temp;
        String matcherStr;
        int index;


        String[] paramArr;

        String listTemplate;
        while (listMatch.find()) {
            TParam listParam = new TParam();
            matcherStr = temp = listMatch.group();
            if ((index = temp.indexOf("{")) != 0)
                temp = temp.substring(index);

            temp = temp.replaceAll("\\s+", "");
            temp = temp.substring(1, temp.length() - 1);

            System.out.println(temp);
            if ("listend".equals(temp)) { //是否结束
                TParam listParamf = listParamList.get(listParamList.size() - 1);
                int listEndIndex = template.indexOf(matcherStr) + matcherStr.length();
                int contentListEnd = template.indexOf(matcherStr);
                listTemplate = template.substring(listParamf.getBegin(), listEndIndex);

                List<String> paramList = new ArrayList<String>();
                String listTemplateBuild = analysisParam(template.substring(listParamf.getContentBegin(), contentListEnd), paramList);

                String listItemVar = listParamf.getItemVar() + ".";
                for (String param : paramList) {
                    if (param.indexOf(listItemVar) != -1) {
                        param = param.replace(listItemVar, "");
                        listParamf.getItemParamList().add(param);
                    } else if (param.indexOf(cid) != -1) {
                        param = param.replace(cid, "");
                        listParamf.getParamMap().put(param, pic_type);
                    } else {
                        listParamf.getParamMap().put(param, text_type);
                    }
                }
                paramMap.put(listParamf.getListParam(), listParamf);
                listParamf.setListTemplate(listTemplateBuild);
                template = template.replace(listTemplate, paramSplit + listParamf.getListParam() + paramSplit);
//                System.out.println("template:"+template);
//                System.out.println("templateList:"+listTemplate);
//                System.out.println("templateListbuild:"+listTemplateBuild);
            } else {
                paramArr = temp.split(":");
                int listBeginIndex = template.indexOf(matcherStr);
                listParam.setBegin(listBeginIndex);
                listParam.setContentBegin(listBeginIndex + matcherStr.length());
                listParam.setListParam(paramArr[1]);
                listParam.setItemVar(paramArr[2]);
                listParam.setItemParamList(new ArrayList<String>());
                listParam.setParamMap(new HashMap<String, String>());
                listParamList.add(listParam);

            }
            System.out.println("list: " + temp);
        }
        //{listbegin:listName:itemName}
        //{listend}

        return template;
    }

    /**
     * 分析list遍历
     *
     * @param template
     * @param paramList
     * @return
     */
    private static String analysisParam(String template, List<String> paramList) {

        String temp;
        String matcherStr;
        int index;

        Matcher matcher = param.matcher(template);
        while (matcher.find()) {
            matcherStr = temp = matcher.group();
            if ((index = temp.indexOf("{")) != 0)
                temp = temp.substring(index);
            matcherStr = temp;
            temp = temp.replaceAll("\\s+", "");
            temp = temp.substring(1, temp.length() - 1);
            if ("listend".equals(temp))
                continue;
            paramList.add(temp);
//            paramMap.put(temp,temp);
            template = template.replace(matcherStr, paramSplit + temp + paramSplit);
        }
        return template;
    }

    /**
     * 短信模板信息Param
     *
     * @param template
     * @return
     */
    public static String getTemplateParam(String template) {
        Map map = new HashMap();
        String[] split = template.split("\\$\\{");
        Arrays.stream(split).forEach(item -> {
            if (item.contains("}")) {
                String substring = item.substring(0, item.indexOf("}"));
                map.put(substring, "text");
            }
        });
        return JSONObject.toJSONString(map);
    }


    private static String getJSONObject() {

        JSONObject message = new JSONObject();

        message.put("msgType", "text");
        message.put("title", "alert告警信息");
        //消息点击链接地址，当发送消息为小程序时支持小程序跳转链接
        message.put("messageUrl", "http://www.messageUrl.com");
        message.put("picUrl", "http://www.picUrl.png");
        message.put("singleUrl", "http://www.singleUrl.com");
        message.put("singleTitle", "alert singleTitle 告警信息");


        StringBuffer dingTalkMEssage = new StringBuffer();
        dingTalkMEssage.append(" \n### 告警时间:");
        dingTalkMEssage.append("2020-10-19 14:24:18");

        dingTalkMEssage.append(" \n#### 告警名称:");
        dingTalkMEssage.append("睛灵调外接口Stable失败");

        dingTalkMEssage.append(" \n#### 告警等级:");
        dingTalkMEssage.append("ERROR告警");

        dingTalkMEssage.append(" \n#### 告警项目:");
        dingTalkMEssage.append("睛灵监控");

        dingTalkMEssage.append(" \n#### 告警信息");
        dingTalkMEssage.append("\n#### ");
        dingTalkMEssage.append("时间");
        dingTalkMEssage.append("\n#### ");
        dingTalkMEssage.append(" \n#### 维度:");
        dingTalkMEssage.append("serviceName=api-service funcCode=dingTalk bizld=error status=0 desc=java.net.ConnectException: Connection refused (Connection refused) addition=null");
        dingTalkMEssage.append(" \n#### 维度:");
        dingTalkMEssage.append("status=0");

        message.put("text", dingTalkMEssage);

        message.put("content", dingTalkMEssage);
        System.out.println(message);


        return message.toJSONString();
    }

    /**
     * 将paramMap转换成map
     *
     * @param paraMap
     * @return
     */
    public static Map<String, Object> getPreParam(Map<String, Object> paraMap) {
        Map<String, Object> param = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
            if (entry.getValue() instanceof TParam) {
                TParam tparam = (TParam) entry.getValue();
                List<Map> listParam = new ArrayList<>();
                Map itemParam = new HashMap();

                for (String itemp : tparam.getItemParamList()) {
                    itemParam.put(itemp, itemp);
                }
                listParam.add(itemParam);
                param.put(entry.getKey(), listParam);
            } else
                param.put(entry.getKey(), entry.getKey());
        }
        return param;
    }


    /**
     * 构建成HTML
     *
     * @param templatePO
     * @param userParam
     * @param paraMap
     * @return
     */
    public static String buildHTML(TemplatePO templatePO, Map userParam, Map paraMap, boolean isMail) {
        String htmlTemplate = templatePO.getTemplate();
//        StringBuffer test=new StringBuffer(templatePO.getTemplate());
        Map param = new HashMap();
        Map valueMap = new HashMap();
        String lowerKey = null;
        if (userParam != null) {
            for (Object key : userParam.keySet()) {
                lowerKey = String.valueOf(key) ;
                param.put(lowerKey.toLowerCase(), userParam.get(key));
            }
        }

        for (Object key : paraMap.keySet()) {
            if (paraMap.get(key) instanceof String && pic_type.equalsIgnoreCase(String.valueOf(paraMap.get(key)))) {
                htmlTemplate = htmlTemplate.replaceAll(paramSplit + cid + key + paramSplit, cid + key);
            }
            if (param.containsKey(key)) {
                if (paraMap.get(key) instanceof String) {
                    if (text_type.equalsIgnoreCase(String.valueOf(paraMap.get(key)) )) {
                        htmlTemplate = htmlTemplate.replaceAll(paramSplit + key + paramSplit, String.valueOf(param.get(key)) );
                    }
                } else {

//                    TParam tParam = JSONObject.parseObject(paraMap.get(key).toString(), TParam.class);
                    TParam tParam = JSONObject.parseObject(JSONObject.toJSONString(paraMap.get(key)), TParam.class);
                    List list = (List) param.get(key);
                    String listTemplateAll = "";
                    for (Object userValueParam : list) {
                        String listTemplate = tParam.getListTemplate();
                        valueMap = (Map) userValueParam;
                        Map valueParamMap = new HashMap();
                        for (Object valueMapKey : valueMap.keySet()) {
                            lowerKey = String.valueOf(valueMapKey) ;
                            valueParamMap.put(lowerKey.toLowerCase(), valueMap.get(valueMapKey));
                        }

                        for (String itemParam : tParam.getItemParamList()) {
                            if (valueParamMap.containsKey(itemParam)) {
                                String replacement = Matcher.quoteReplacement(String.valueOf(valueParamMap.get(itemParam)) );
                                listTemplate = listTemplate.replaceAll(paramSplit + tParam.getItemVar() + "." + itemParam + paramSplit, replacement);
                            } else {
                                listTemplate = listTemplate.replaceAll(paramSplit + tParam.getItemVar() + "." + itemParam + paramSplit, "");
                            }
                        }
                        for (Object listKey : tParam.getParamMap().keySet()) {
                            if (pic_type.equalsIgnoreCase(String.valueOf( paraMap.get(listKey)))) {
                                listTemplateAll = listTemplateAll.replaceAll(paramSplit + listKey + paramSplit, cid + param.get(listKey));
                            }
                            if (param.containsKey(listKey)) {
                                if (text_type.equalsIgnoreCase(String.valueOf(paraMap.get(listKey)))) {
                                    String replacement = Matcher.quoteReplacement(String.valueOf(param.get(listKey)));
                                    listTemplateAll = listTemplateAll.replaceAll(paramSplit + listKey + paramSplit, replacement);
                                }
                            } else
                                listTemplateAll = listTemplateAll.replaceAll(paramSplit + listKey + paramSplit, "");

                        }
                        if (isMail) {
                            listTemplateAll += listTemplate + "<br/>";

                        } else {
                            listTemplateAll += listTemplate;
                        }
                    }
                    listTemplateAll = Matcher.quoteReplacement(listTemplateAll);
                    htmlTemplate = htmlTemplate.replaceAll(paramSplit + key + paramSplit, listTemplateAll);
                }
            } else {
                htmlTemplate = htmlTemplate.replaceAll(paramSplit + key + paramSplit, "");
            }
        }
        if (isMail) {
            htmlTemplate = htmlHead + htmlTemplate + htmlEnd;
        } else {
            htmlTemplate = htmlTemplate;
        }
        return htmlTemplate;
    }

    /**
     * 获取templateInfo
     * @param templateVO
     * @return
     */
    public static String getTemplateInfo(TemplateVO templateVO) {
        if (Objects.nonNull(templateVO.getSmsTemplateAccount())){
            Map<String, String> map = new HashMap<>();
            //上汽安吉签名
            map.put("aliSignName", templateVO.getSmsTemplateAccount().getAliSignName());
            //阿里模板Code
            map.put("aliTemplateCode", templateVO.getSmsTemplateAccount().getAliTemplateCode());
            //极光签名Id
            map.put("jgSignId", String.valueOf(templateVO.getSmsTemplateAccount().getJgSignId()));
            //极光模板Id
            map.put("jgTemplateId", String.valueOf(templateVO.getSmsTemplateAccount().getJgTemplateId()));
            //上汽安吉签名
            map.put("sqajSignName", templateVO.getSmsTemplateAccount().getAjSignName());
            //发送顺序
            map.put("sendOrder", templateVO.getSmsTemplateAccount().getSendOrder());
            //短信平台已选择项
            map.put("signNameSelected", templateVO.getSmsTemplateAccount().getSignNameSelected());

            return JSONObject.toJSONString(map);
        }
        return "";
    }

    /**
     * 根据templateInfo 设置smsTemplateAccount
     * @param template
     * @return
     */
    public static  TemplateVO getTemplateVOByInfo(TemplateVO template) {
        String templateInfo = template.getTemplateInfo();
        SmsTemplateAccount account = new SmsTemplateAccount();
        if (StringUtils.isNoneBlank(templateInfo)){
            try {
                JSONObject json = JSONObject.parseObject(templateInfo);
                account.setAliSignName(json.getString("aliSignName"));
                account.setAliTemplateCode(json.getString("aliTemplateCode"));
                String jgSignId = json.getString("jgSignId");
                if (null != jgSignId) {
                    account.setJgSignId(Integer.parseInt(jgSignId));
                }
                String jgTemplateId = json.getString("jgTemplateId");
                if (null != jgTemplateId) {
                    account.setJgTemplateId(Integer.parseInt(jgTemplateId));
                }
                account.setAjSignName(json.getString("sqajSignName"));
                account.setSendOrder(json.getString("sendOrder"));
                account.setSignNameSelected(json.getString("signNameSelected"));
            } catch (Exception e) {
                log.error("解析模板info报错 无需处理", e);
            }
        }

        template.setSmsTemplateAccount(account);
        return template;
    }


    public static void main(String[] args) {
        TemplateVO templateVO = new TemplateVO();
        String s = "{\"aliTemplateCode\":\"\",\"aliSignName\":\"\",\"jgSignId\":\"7762\",\"jgTemplateId\":\"161682\",\"sqajSignName\":\"上汽安吉\",\"sendOrder\":[1,2,3],\"signNameSelected\":[1,2,3]}\n";
        templateVO.setTemplateInfo(s);
        TemplateVO templateVOByInfo = getTemplateVOByInfo(templateVO);
        System.out.println(JSONObject.toJSON(templateVOByInfo.getSmsTemplateAccount()));
        List<Integer> lists1 = JSONArray.parseArray(templateVOByInfo.getSmsTemplateAccount().getSignNameSelected(), Integer.class);
        System.out.println(lists1);

        List<Integer> lists = JSONArray.parseArray(templateVOByInfo.getSmsTemplateAccount().getSendOrder(), Integer.class);
        System.out.println(lists);

//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(" \n### 告警时间:");
//        stringBuffer.append("2020-10-19 14:24:21");
//
//        stringBuffer.append(" \n#### 告警名称:");
//        stringBuffer.append("睛灵调外接口Stable失败");
//
//        stringBuffer.append(" \n#### 告警等级:");
//        stringBuffer.append("ERROR告警");
//
//        stringBuffer.append(" \n#### 告警项目:");
//        stringBuffer.append("睛灵监控");
//
//        stringBuffer.append(" \n#### 告警信息");
//        stringBuffer.append("\n#### 时间");
//        stringBuffer.append("2020-10-19 14:24:23");
//        stringBuffer.append("\n#### 指标");
//        stringBuffer.append("serviceName=api-service funcCode=dingTalk bizld=error");
//        stringBuffer.append("\n#### 维度");
//        stringBuffer.append("status=0");
//        stringBuffer.append("\n#### 时间");
//        stringBuffer.append("2020-10-19 14:24:48");
//        stringBuffer.append("\n#### 指标");
//        stringBuffer.append("serviceName=api-service funcCode=dingTalk bizld=error");
//        stringBuffer.append("\n#### 维度");
//        stringBuffer.append("status=0");
//
//        System.out.println(stringBuffer);

//        TemplatePO templatePO = analysisTemplate(
//                "\"###告警时间：{sendTime}↵###告警名称：{alertName}↵###告警等级：{alertlevel}↵###告警项目：{projectName}↵###告警信息↵{listbegin : alertInfoList : alertInfo }↵####时间: {alertInfo.alertTime}↵####维度: {alertInfo.alertInfoDmensions}↵####指标: {alertInfo.alertInfo}↵{ listend }↵↵本邮件由推送系统发送，请勿回复\"",
//                false);
//        System.out.println(ToStringBuilder.
//                reflectionToString(templatePO, ToStringStyle.MULTI_LINE_STYLE));
//        System.out.println(ToStringBuilder.
//                reflectionToString(templatePO.getParaMap().get("alertinfolist"), ToStringStyle.MULTI_LINE_STYLE));
//
//        Map<String, Object> param = new HashMap<>();
//        System.out.println(param);
//        param.put("sendtime","2020-10-19 14:24:21");
//        param.put("alertname","睛灵调外接口Stable失败");
//        param.put("alertlevel","ERROR告警");
//        param.put("projectname","睛灵监控");
//
//        List alist = new ArrayList();
//        Map<String, Object> map = new HashMap<>();
//        map.put("alerttime","2020-10-19 14:24:21");
//        map.put("alertinfoDmensions","serviceName=api-service funcCode=dingTalk bizld=error status=0 desc=java.net.ConnectException: Connection refused (Connection refused) addition=null");
//        map.put("alertinfo","status=0");
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("alerttime","2020-10-19 14:25:21");
//        map1.put("alertInfodmensions","serviceName=api-service funcCode=dingTalk bizld=error status=0 desc=java.net.ConnectException: Connection refused (Connection refused) addition=null");
//        map1.put("alertinfo","status=0");
//        alist.add(map);
//        alist.add(map1);
//
//        param.put("alertinfolist", alist);
//
//        String html = TemplateAnalysisUtil.buildDingTalkMarkDown(templatePO, param, templatePO.getParaMap());
//
//        html.replace("\\<br/>","");
//        html.replace("\\↵","\\n");
//        System.out.println(html);

//        System.out.println(ToStringBuilder.
//                reflectionToString(templatePO, ToStringStyle.MULTI_LINE_STYLE));
//        System.out.println(ToStringBuilder.
//                reflectionToString(templatePO.getParaMap().get("alertinfolist"), ToStringStyle.MULTI_LINE_STYLE));
//
//        String js = "{\"projectname\":\"text\",\"alertinfolist\":{\"begin\":90,\"contentBegin\":130,\"contentEnd\":0,\"end\":0,\"itemParamList\":[\"alerttime\",\"alertinfodmensions\",\"alertinfo\"],\"itemVar\":\"alertinfo\",\"listParam\":\"alertinfolist\",\"listTemplate\":\"↵####时间: #%#alertinfo.alerttime#%#↵####维度: #%#alertinfo.alertinfodmensions#%#↵####指标: #%#alertinfo.alertinfo#%#↵\",\"paramMap\":{}},\"alertname\":\"text\",\"alertlevel\":\"text\",\"sendtime\":\"text\"}";
//
//        Object parse = JSONObject.parse(js);
//        System.out.println(ToStringBuilder.
//                reflectionToString(parse, ToStringStyle.JSON_STYLE));


//        System.out.println(getJSONObject());
//
//        System.out.println(getTemplateParam("您正在找回密码，验证码：${code}，15分钟内有效，请勿泄露！"));
    }
}
