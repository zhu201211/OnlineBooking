package com.cqut.test;

import com.alibaba.fastjson.JSONObject;
import com.cqut.OnlineMealBooking.utils.SendCode;

/**
 * ���Ͷ��Ų���
 * 
 * @author ף����
 *
 */
public class getCode {
    public static void main(String[] args) {
	String phone="18323067675";
	JSONObject jsonObject=new JSONObject();
	jsonObject=SendCode.getJson(phone);
	System.out.println("��Ϣ��"+jsonObject.getString("verifyCode"));
    }
}
