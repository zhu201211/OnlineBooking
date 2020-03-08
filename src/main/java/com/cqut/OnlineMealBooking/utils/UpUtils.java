package com.cqut.OnlineMealBooking.utils;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传工具类
 * 
 * @author 祝剑锋
 *
 */
public class UpUtils {
    
  //获取我们本地工作区间的项目跟路径
    private static String relPath="F:\\WorkSpace\\OnlineMealBooking\\src\\main\\webapp\\";

    // 商家文件上传处理
    public static String getMerchantSrc(MultipartFile filedate, HttpServletRequest request) {
	// 获取我们的当前路径（项目部署在tomcat中的根路径）
	String proPath = request.getSession().getServletContext().getRealPath("/");
	// 设置我们文件上传后的 保存路径
	String savePath = "images/merchant";
	// 声明文件路径并且创建该文件目录
	File file = new File(proPath + savePath);
	if (!file.exists()) {
	    file.mkdirs();
	}
	//保证根路径下的图片位置存在
	File file1=new File(relPath+savePath);
	if (!file1.exists()) {
	    file1.mkdirs();
	}
	//创建文件输出流
	try {
         	//获取上传文件的名称 
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
    
    //用户文件上传处理
    public static String getUserSrc(MultipartFile filedate, HttpServletRequest request) {
  	// 获取我们的当前路径（项目部署在tomcat中的根路径）
  	String proPath = request.getSession().getServletContext().getRealPath("/");
  	// 设置我们文件上传后的 保存路径
  	String savePath = "images/user";
  	// 声明文件路径并且创建该文件目录
  	File file = new File(proPath + savePath);
  	if (!file.exists()) {
  	    file.mkdirs();
  	}
  	//保证根路径下的图片位置存在
  	File file1=new File(relPath+savePath);
  	if (!file1.exists()) {
  	    file1.mkdirs();
  	}
  	//创建文件输出流
  	try {
           	//获取上传文件的名称 
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
    
    //菜品文件上传处理
    public static String getFoodSrc(MultipartFile filedate, HttpServletRequest request) {
  	// 获取我们的当前路径（项目部署在tomcat中的根路径）
  	String proPath = request.getSession().getServletContext().getRealPath("/");
  	// 设置我们文件上传后的 保存路径
  	String savePath = "images/food";
  	// 声明文件路径并且创建该文件目录
  	File file = new File(proPath + savePath);
  	if (!file.exists()) {
  	    file.mkdirs();
  	}
  	//保证根路径下的图片位置存在
  	File file1=new File(relPath+savePath);
  	if (!file1.exists()) {
  	    file1.mkdirs();
  	}
  	//创建文件输出流
  	try {
           	//获取上传文件的名称 
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
