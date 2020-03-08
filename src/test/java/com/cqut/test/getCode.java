package com.cqut.test;

import com.alibaba.fastjson.JSONObject;
import com.cqut.OnlineMealBooking.utils.SendCode;

/**
 * 发送短信测试
 * 
 * @author 祝剑锋
 *
 */
public class getCode {
    public static void main(String[] args) {
	String phone="18323067675";
	JSONObject jsonObject=new JSONObject();
	jsonObject=SendCode.getJson(phone);
	System.out.println("信息："+jsonObject.getString("verifyCode"));
    }
}
