package com.anji.mirror.push;

/**
 * Created by raodeming on 2019/9/29.
 */
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
public class AliSmsTest {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-test", "testaccesskey243313", "Kiwes882wfasdfjasdlfkjasdl");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-test");
        request.putQueryParameter("PhoneNumbers", "111111111");
        request.putQueryParameter("SignName", "七哥专卖店");
        request.putQueryParameter("TemplateCode", "SMS_11111111");
        request.putQueryParameter("TemplateParam", "{\"name\":\"饶德明\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
