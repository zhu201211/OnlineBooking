package com.cqut.OnlineMealBooking.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.pojo.Message;
import com.cqut.OnlineMealBooking.pojo.User;

public interface AdminService {

    	User login(String userName, String userPassword) ;

	List<User> findAllUser();

	Message updateUser(User user, MultipartFile filedata, HttpServletRequest request);

	User findUserById(String id);

	void deleteUser(User user);

	List<User> searchUsersByInfo(String searchInfo);

	User findByPhone(String userPhone);

	Message addUser(MultipartFile filedata, String userPhone, String userName, String userPassword,Cart cart,
		HttpServletRequest request);

	Message updatePassword(String pw1, String pw2, HttpServletRequest request);

	List<Merchant> findAllMerchant();

	Message addMerchant(String name, String phone, String address, MultipartFile filedata, String intro,
		HttpServletRequest request);

	List<Food> findAllFoods();

	List<Food> findFoodsByInfo(String info);

	List<Food> findFoodsByMerchantInfo(String info);

	List<Intent> findIntentByState(String state);

	List<User> findUserByInfo(String info);

	List<Food> findMerchantFoodById(String merchantId);

	Message updateMerchant(String merchantId, String name, String phone, String address, String intro, String state,
		MultipartFile filedata, HttpServletRequest request);

	List<Merchant> findMerchantByInfo(String info);

	Message updateFood(String foodId,String name, String price, String intro, MultipartFile filedata, HttpServletRequest request);


}
