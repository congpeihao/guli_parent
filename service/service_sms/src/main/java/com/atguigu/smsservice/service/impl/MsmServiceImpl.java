package com.atguigu.smsservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.smsservice.service.MsmService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {


    @Override
    public boolean send(Map<String, Object> param, String phone) {


        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI5tH1fBELKfYgYs54286r", "4qqOS3tGd2Vn8G2sI4jIZ9jKUpUv5j");

        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("阿里云短信测试");
        request.setTemplateCode("SMS_154950909");
        request.setPhoneNumbers(phone);
        request.setTemplateParam(JSONObject.toJSONString(param));

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            if (response.getCode().equals("OK")){
                return  true;
            }else{
                return  false;
            }

        } catch (ServerException e) {
            e.printStackTrace();
            return  false;
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            return  false;
        }


    }
}
