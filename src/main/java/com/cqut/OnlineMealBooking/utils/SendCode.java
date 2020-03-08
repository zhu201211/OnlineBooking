package com.cqut.OnlineMealBooking.utils;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;

/**
 * 短信发送工具类
 * 
 * @author 祝剑锋
 *
 */
public class SendCode {
    public static JSONObject getJson(String Phone) {
	
	//apiUrl为请求地址，个人开发者使用https://sms_developer.zhenzikj.com
	String apiUrl="https://sms_developer.zhenzikj.com";
	
	//appId为申请的榛子云短信平台的应用ID
	String appId="100638";
	
	//appSecret申请的榛子云短信平台的应用密钥
	String appSecret="YWQ5MzAzNmMtMzZjMy00MDBlLTg1ZjAtZjI1Yjk0ZWI2YTQx";
	
	JSONObject json = null;
	try {
	    //生成4位验证码
	    String verifyCode = String.valueOf(new Random().nextInt(8999) + 1000);
	    ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);//通过榛子云短信平台发送短信
	    String result=client.send(Phone, "您的验证码为："+verifyCode+"，该验证码有效期为2分钟。如非本人操作，请忽视本信息。");
	    json = JSONObject.parseObject(result);
	    if(json.getIntValue("code") != 0){
		//发送短信失败
		System.out.println("发送短信失败！请重试！失败原因 : "+json.getIntValue("code"));
		 json.put("msg", "发送短信失败！请重试！失败原因 : "+json.getIntValue("code"));
		return json;
	    }
	    //以json存放数据
	    json = new JSONObject();
	    json.put("userPhone", Phone);
	    json.put("verifyCode", verifyCode);
	    json.put("createTime", System.currentTimeMillis());
		
	    return json;
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	    System.out.println("网络异常！发送短信失败！ ");
	    json.put("msg", "网络异常！发送短信失败！");
	    return json;
	}
	
    }
}
