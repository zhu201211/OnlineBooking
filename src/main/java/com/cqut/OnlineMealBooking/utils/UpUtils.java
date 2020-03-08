package com.cqut.OnlineMealBooking.utils;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * �ļ��ϴ�������
 * 
 * @author ף����
 *
 */
public class UpUtils {
    
  //��ȡ���Ǳ��ع����������Ŀ��·��
    private static String relPath="F:\\WorkSpace\\OnlineMealBooking\\src\\main\\webapp\\";

    // �̼��ļ��ϴ�����
    public static String getMerchantSrc(MultipartFile filedate, HttpServletRequest request) {
	// ��ȡ���ǵĵ�ǰ·������Ŀ������tomcat�еĸ�·����
	String proPath = request.getSession().getServletContext().getRealPath("/");
	// ���������ļ��ϴ���� ����·��
	String savePath = "images/merchant";
	// �����ļ�·�����Ҵ������ļ�Ŀ¼
	File file = new File(proPath + savePath);
	if (!file.exists()) {
	    file.mkdirs();
	}
	//��֤��·���µ�ͼƬλ�ô���
	File file1=new File(relPath+savePath);
	if (!file1.exists()) {
	    file1.mkdirs();
	}
	//�����ļ������
	try {
         	//��ȡ�ϴ��ļ������� 
            	String orgName=filedate.getOriginalFilename();
            	
            	String end=orgName.substring(orgName.lastIndexOf("."));
            	String start=String.valueOf(System.currentTimeMillis());
            	
            	String imgPath=savePath+"/"+start+end;
        	FileOutputStream  fos=new FileOutputStream(proPath+imgPath, true);
        	FileOutputStream  fos1=new FileOutputStream(relPath+imgPath, true);
        	fos.write(filedate.getBytes());
        	fos1.write(filedate.getBytes());
        	fos1.flush();
        	fos.flush();
        	fos1.close();
        	fos.close();
        	return imgPath;
	} catch (Exception e) {
	    e.printStackTrace();
	    return "";
	}
    }
    
    //�û��ļ��ϴ�����
    public static String getUserSrc(MultipartFile filedate, HttpServletRequest request) {
  	// ��ȡ���ǵĵ�ǰ·������Ŀ������tomcat�еĸ�·����
  	String proPath = request.getSession().getServletContext().getRealPath("/");
  	// ���������ļ��ϴ���� ����·��
  	String savePath = "images/user";
  	// �����ļ�·�����Ҵ������ļ�Ŀ¼
  	File file = new File(proPath + savePath);
  	if (!file.exists()) {
  	    file.mkdirs();
  	}
  	//��֤��·���µ�ͼƬλ�ô���
  	File file1=new File(relPath+savePath);
  	if (!file1.exists()) {
  	    file1.mkdirs();
  	}
  	//�����ļ������
  	try {
           	//��ȡ�ϴ��ļ������� 
              	String orgName=filedate.getOriginalFilename();
              	
              	String end=orgName.substring(orgName.lastIndexOf("."));
              	String start=String.valueOf(System.currentTimeMillis());
              	
              	String imgPath=savePath+"/"+start+end;
          	FileOutputStream  fos=new FileOutputStream(proPath+imgPath, true);
          	FileOutputStream  fos1=new FileOutputStream(relPath+imgPath, true);
          	fos.write(filedate.getBytes());
          	fos1.write(filedate.getBytes());
          	fos1.flush();
          	fos.flush();
          	fos1.close();
          	fos.close();
          	return imgPath;
  	} catch (Exception e) {
  	    e.printStackTrace();
  	    return "";
  	}
      }
    
    //��Ʒ�ļ��ϴ�����
    public static String getFoodSrc(MultipartFile filedate, HttpServletRequest request) {
  	// ��ȡ���ǵĵ�ǰ·������Ŀ������tomcat�еĸ�·����
  	String proPath = request.getSession().getServletContext().getRealPath("/");
  	// ���������ļ��ϴ���� ����·��
  	String savePath = "images/food";
  	// �����ļ�·�����Ҵ������ļ�Ŀ¼
  	File file = new File(proPath + savePath);
  	if (!file.exists()) {
  	    file.mkdirs();
  	}
  	//��֤��·���µ�ͼƬλ�ô���
  	File file1=new File(relPath+savePath);
  	if (!file1.exists()) {
  	    file1.mkdirs();
  	}
  	//�����ļ������
  	try {
           	//��ȡ�ϴ��ļ������� 
              	String orgName=filedate.getOriginalFilename();
              	
              	String end=orgName.substring(orgName.lastIndexOf("."));
              	String start=String.valueOf(System.currentTimeMillis());
              	
              	String imgPath=savePath+"/"+start+end;
          	FileOutputStream  fos=new FileOutputStream(proPath+imgPath, true);
          	FileOutputStream  fos1=new FileOutputStream(relPath+imgPath, true);
          	fos.write(filedate.getBytes());
          	fos1.write(filedate.getBytes());
          	fos1.flush();
          	fos.flush();
          	fos1.close();
          	fos.close();
          	return imgPath;
  	} catch (Exception e) {
  	    e.printStackTrace();
  	    return "";
  	}
      }
}
