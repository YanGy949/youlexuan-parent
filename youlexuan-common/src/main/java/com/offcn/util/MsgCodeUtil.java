package com.offcn.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;
import java.util.Random;

public class MsgCodeUtil {

//    public static void main(String[] args) {
//        System.out.println(createMsgCode());
//    }

    public static String createMsgCode() {
        String msgCode = new Random().nextInt(900000) + 100000 + "";
        return msgCode;
    }


    public static boolean sendMsgCode(String phone, String msgCode) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIYa1d1gS38kDh", "WOhsp7sUlspyLDzTUJDpGGQ3iiTGXd");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);//变量
        request.putQueryParameter("SignName", "优乐选用户注册");//提前申请
        request.putQueryParameter("TemplateCode", "SMS_174989901");//提前申请
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + msgCode + "\"}");//123456 变量
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String data = response.getData();
            Map map = JSON.parseObject(data);
            Object message = map.get("Message");
            Object code = map.get("Code");
            if (message != null && message.equals("OK") && code != null && code.equals("OK")) {
                return true;
            }
            return false;

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
