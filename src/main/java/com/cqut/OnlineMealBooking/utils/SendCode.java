package com.cqut.OnlineMealBooking.utils;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;

/**
 * ���ŷ��͹�����
 * 
 * @author ף����
 *
 */
public class SendCode {
    public static JSONObject getJson(String Phone) {
	
	//apiUrlΪ�����ַ�����˿�����ʹ��https://sms_developer.zhenzikj.com
	String apiUrl="https://sms_developer.zhenzikj.com";
	
	//appIdΪ���������ƶ���ƽ̨��Ӧ��ID
	String appId="100638";
	
	//appSecret���������ƶ���ƽ̨��Ӧ����Կ
	String appSecret="YWQ5MzAzNmMtMzZjMy00MDBlLTg1ZjAtZjI1Yjk0ZWI2YTQx";
	
	JSONObject json = null;
	try {
	    //����4λ��֤��
	    String verifyCode = String.valueOf(new Random().nextInt(8999) + 1000);
	    ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);//ͨ������ƶ���ƽ̨���Ͷ���
	    String result=client.send(Phone, "������֤��Ϊ��"+verifyCode+"������֤����Ч��Ϊ2���ӡ���Ǳ��˲���������ӱ���Ϣ��");
	    json = JSONObject.parseObject(result);
	    if(json.getIntValue("code") != 0){
		//���Ͷ���ʧ��
		System.out.println("���Ͷ���ʧ�ܣ������ԣ�ʧ��ԭ�� : "+json.getIntValue("code"));
		 json.put("msg", "���Ͷ���ʧ�ܣ������ԣ�ʧ��ԭ�� : "+json.getIntValue("code"));
		return json;
	    }
	    //��json�������
	    json = new JSONObject();
	    json.put("userPhone", Phone);
	    json.put("verifyCode", verifyCode);
	    json.put("createTime", System.currentTimeMillis());
		
	    return json;
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	    System.out.println("�����쳣�����Ͷ���ʧ�ܣ� ");
	    json.put("msg", "�����쳣�����Ͷ���ʧ�ܣ�");
	    return json;
	}
	
    }
}
