package com.cqut.OnlineMealBooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cqut.OnlineMealBooking.dao.AdminDao;
import com.cqut.OnlineMealBooking.dao.CartDao;
import com.cqut.OnlineMealBooking.dao.FoodDao;
import com.cqut.OnlineMealBooking.dao.IntentDao;
import com.cqut.OnlineMealBooking.dao.MerchantDao;
import com.cqut.OnlineMealBooking.dao.UserDao;
import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.pojo.Message;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;
import com.cqut.OnlineMealBooking.service.AdminService;
import com.cqut.OnlineMealBooking.utils.UpUtils;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    @Resource
    private AdminDao adminDao;
    @Resource
    private UserDao userDao;
    @Resource
    private CartDao cartDao;
    @Resource
    private MerchantDao merchantDao;
    @Resource
    private FoodDao foodDao;
    @Resource
    private IntentDao intentDao;
    
    @Override
    public User login(String userName, String userPassword) {
	return adminDao.login(userName,userPassword);
    }

    @Override
    public List<User> findAllUser() {
	List<User> users=adminDao.findAllUser();
	for (User user : users) {
	   List<UserAddress> addresses=userDao.findAddressList(user);
	   user.setAddresses(addresses);
	}
	return users;
    }

    @Override
    public Message updateUser(User user, MultipartFile filedata, HttpServletRequest request) {
	Message msg=new Message();
	User user2=adminDao.findUserById(user.getId());
	//System.out.println(user2.getCart().getId());
	System.out.println("�ļ�����"+filedata.getOriginalFilename().toString());
	if (user2!=null) {
	    Cart cart=cartDao.findCartById(user2.getCart().getId());
	    if (cart!=null) {
		//���û����ﳵ����
		user.setCart(cart);
	    } else {
		//���û����ﳵ������
		Cart cart2=new Cart();
		String id= UUID.randomUUID().toString().substring(0, 4);
		cart2.setId(id);
		cartDao.addCart(cart2);
		user.setCart(cart2);
	    }
	    if (filedata!=null&&filedata.getSize()>0) {
		String src = UpUtils.getUserSrc(filedata, request);
		user.setUserImg(src);
	    } else {
		user.setUserImg(user2.getUserImg());
	    }
	    adminDao.updataUser(user);
	    msg.setMsg("�޸ĳɹ�");
	} else {
	    msg.setMsg("�û�������");
	}
	return msg;
    }

    @Override
    public User findUserById(String id) {
	// TODO Auto-generated method stub
	User user = adminDao.findUserById(id);
	return user;
    }

    @Override
    public void deleteUser(User  user) {
	// TODO Auto-generated method stub
	
	adminDao.deleteUser(user);
    }

    @Override
    public List<User> searchUsersByInfo(String searchInfo) {
	// TODO Auto-generated method stub
	List<User> users=adminDao.searchUsersByInfo(searchInfo);
	return users;
    }

    @Override
    public User findByPhone(String userPhone) {
	// TODO Auto-generated method stub
	User user=userDao.findByPhone(userPhone);
	
	return user;
    }

    @Override
    public Message addUser(MultipartFile filedata, String userPhone, String userName, String userPassword,Cart cart,HttpServletRequest request) {
	// TODO Auto-generated method stub
	Message msg = new Message();
	User user=new User();
	String uId = UUID.randomUUID().toString().substring(0, 4);
	user.setId(uId);
	user.setUserPhone(userPhone);
	user.setUserName(userName);
	user.setUserPassword(userPassword);
	user.setCart(cart);
	if (filedata.getSize()>0) {
	    String src=UpUtils.getUserSrc(filedata, request);
	    user.setUserImg(src);
	} else {
	    user.setUserImg("images/user/user-photo.png");
	}
	userDao.addUser(user);
	msg.setMsg("����û��ɹ�");
	return msg;
    }

    @Override
    public Message updatePassword(String pw1, String pw2, HttpServletRequest request) {
	// TODO Auto-generated method stub
	Message msg=new Message();
	User user=((User) request.getSession().getAttribute("u"));
	if (user.getUserPassword().equals(pw1)) {
	    user.setUserPassword(pw2);
	    userDao.updateUser(user);
	    msg.setMsg("�޸ĳɹ���");
	} else {
	    msg.setMsg("�������");
	}
	
	return msg;
    }

    @Override
    public List<Merchant> findAllMerchant() {
	// TODO Auto-generated method stub
	List<Merchant> merchants=adminDao.findAllMerchant();
	return merchants;
    }

    @Override
    public Message addMerchant(String name, String phone, String address, MultipartFile filedata, String intro,
	    HttpServletRequest request) {
	// TODO Auto-generated method stub
	Message msg=new Message();
	Merchant merchant=new Merchant();
	String src=UpUtils.getMerchantSrc(filedata, request);
	String id=UUID.randomUUID().toString().substring(0, 4);
	merchant.setMerchantId(id);
	merchant.setMerchantName(name);
	merchant.setMerchantPhone(phone);
	merchant.setMerchantAddress(address);
	merchant.setMerchantIntro(intro);
	merchant.setMerchantImg(src);
	merchant.setMerchantPop(0);
	merchant.setState("����פ");
	merchantDao.addMerchant(merchant);
	msg.setMsg("�ɹ�����̼ң�������Ʒ����Ϊ���̼Ҳ�����Ӳ�Ʒ��");
	return msg;
    }

    @Override
    public List<Food> findAllFoods() {
	// TODO Auto-generated method stub
	
	return foodDao.findAllFoods();
    }

    @Override
    public List<Food> findFoodsByInfo(String info) {
	// TODO Auto-generated method stub
	
	return foodDao.findFoodsByInfo(info);
    }

    @Override
    public List<Food> findFoodsByMerchantInfo(String info) {
	// TODO Auto-generated method stub
	List<Merchant> ms=merchantDao.findMerchantByInfo(info);
	List<Food> foods=new ArrayList<>();
	for (Merchant m : ms) {
	    List<Food> fs=merchantDao.findFoodsByMerchant(m);
	    for (Food food : fs) {
		foods.add(food);
	    }
	}
	return foods;
    }

    @Override
    public List<Intent> findIntentByState(String state) {
	// TODO Auto-generated method stub
	return adminDao.findIntentByState(state);
    }

    @Override
    public List<User> findUserByInfo(String info) {
	// TODO Auto-generated method stub
	
	return adminDao.findUserByInfo(info);
    }

    @Override
    public List<Food> findMerchantFoodById(String merchantId) {
	// TODO Auto-generated method stub
	Merchant merchant=merchantDao.findMerchantById(merchantId);
	if (merchant!=null) {
	    List<Food> foods=merchantDao.findFoodsByMerchant(merchant);
	    return foods;
	} else {
	    return null;
	}
	
    }

    @Override
    public Message updateMerchant(String merchantId, String name, String phone, String address, String intro,
	    String state, MultipartFile filedata, HttpServletRequest request) {
	// TODO Auto-generated method stub
	Message msg=new Message();
	if (name==null||name=="") {
	    msg.setMsg("�̼�������Ϊ��");
	    return msg;
	} else if(phone==null||phone==""){
	    msg.setMsg("��ϵ�绰����Ϊ��");
	    return msg;
	}else if(address==null||address==""){
	    msg.setMsg("�̼ҵ�ַ����Ϊ��");
	    return msg;
	}else {
    	    Merchant merchant=merchantDao.findMerchantById(merchantId);
    	    if (merchant==null) {
    		msg.setMsg("�̼Ҳ�����");
    		return msg;
    	    } else {
    		merchant.setMerchantName(name);
    		merchant.setMerchantPhone(phone);
    		merchant.setMerchantIntro(intro);
    		merchant.setMerchantAddress(address);
    		merchant.setState(state);
    		if (filedata.getSize()>0) {
    		    String src=UpUtils.getMerchantSrc(filedata, request);
    		    merchant.setMerchantImg(src);
		}
    		merchantDao.updateMerchant(merchant);
    		msg.setMsg("������Ϣ�ɹ�");
    		return msg;
    	    }
	}
    }

    @Override
    public List<Merchant> findMerchantByInfo(String info) {
	// TODO Auto-generated method stub
	return adminDao.findMerchantByInfo(info);
    }

    @Override
    public Message updateFood(String foodId,String name, String price, String intro, MultipartFile filedata,
	    HttpServletRequest request) {
	// TODO Auto-generated method stub
	Message msg=new Message();
	if (name==null||name=="") {
	    msg.setMsg("��Ʒ���ֲ���Ϊ��");
	    return msg;
	} else if (price==null||price==""){
	    msg.setMsg("��Ʒ�۸���Ϊ��");
	    return msg;
	}else {
	    Food food=foodDao.findFoodById(foodId);
	    if (food==null) {
		msg.setMsg("�ò�Ʒ������");
		return msg;
	    } else {
		food.setFoodName(name);
		food.setFoodIntro(intro);
		double p=Double.parseDouble(price);
		food.setPrice(p);
		if (filedata.getSize()>0) {
		    String src=UpUtils.getFoodSrc(filedata, request);
		    food.setFoodImg(src);
		}
		foodDao.updateFood(food);
		msg.setMsg("�޸ĳɹ�");
		return msg;
	    }
	}
	
    }

 
}
