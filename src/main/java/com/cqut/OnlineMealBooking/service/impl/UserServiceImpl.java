package com.cqut.OnlineMealBooking.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cqut.OnlineMealBooking.service.UserService;
import com.cqut.OnlineMealBooking.utils.UpUtils;
import com.cqut.OnlineMealBooking.dao.MyFoodsDao;
import com.cqut.OnlineMealBooking.dao.UserDao;
import com.cqut.OnlineMealBooking.pojo.Favorite;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Message;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;
    @Resource
    private MyFoodsDao myFoodsDao;

    @Override
    public User loginByPhone(String userPhone, String userPassword) {
	// TODO Auto-generated method stub
	
	User user=userDao.loginByPhone(userPhone,userPassword);
	
	return user;
    }

    //通过电话号查找用户
    @Override
    public User findByPhone(String userPhone) {
	// TODO Auto-generated method stub
	User user=userDao.findByPhone(userPhone);
	
	return user;
    }

    @Override
    public Message addUser(User user) {
	// TODO Auto-generated method stub
	Message msg = new Message();
	if(user.getUserPhone()!=null&&user.getUserPhone()!="") {
	    //注册时生成默认id，默认头像，默认昵称 
	    String id = UUID.randomUUID().toString().substring(0, 4);
	    user.setId(id);
	    user.setUserImg("images/user-photo.png");
	    user.setUserName(user.getUserPhone());
	    userDao.addUser(user);
	    msg.setMsg("注册成功！请登录！");
	    return msg;
	}else {
		msg.setMsg("用户已存在");
		return msg;
	    }
    }


    @Override
    public void updateUser(User user) {
	// TODO Auto-generated method stub
	userDao.updateUser(user);
    }

    @Override
    public List<UserAddress> findAddressList(User user) {
	// TODO Auto-generated method stub
	
	List<UserAddress> uAddresses=userDao.findAddressList(user);
	
	return uAddresses;
    }

    @Override
    public void addUserAddress(UserAddress userAddress, User user) {
	// TODO Auto-generated method stub
	String id=UUID.randomUUID().toString().substring(0, 4);
	userAddress.setId(id);
	userAddress.setUser(user);
	
	userDao.addUserAddress(userAddress);
	
    }

    @Override
    public UserAddress findAddressById(String addressId) {
	// TODO Auto-generated method stub
	return userDao.findAddressById(addressId);
    }

    @Override
    public List<User> getUserList() {
	// TODO Auto-generated method stub
	List<User> users=userDao.getUserList();
	User u=userDao.findByPhone("admin");
	users.remove(u);
	for (User user : users) {
	    List<MyFoods> myFoods=myFoodsDao.getMyFoodsList(user);
	    List<Favorite> favorites=userDao.findAllFavorites(user);
	    user.setMyFoodList(myFoods);
	    user.setFavorites(favorites);
	}
	return users;
    }

    @Override
    public void createFavorite(Favorite favorite) {
	// TODO Auto-generated method stub
	String id=UUID.randomUUID().toString().substring(0, 4);
	favorite.setId(id);
	userDao.createFavorite(favorite);
    }

    @Override
    public List<Favorite> findAllFavorites(User user) {
	// TODO Auto-generated method stub
	return userDao.findAllFavorites(user);
    }

    @Override
    public void updateAddress(UserAddress address) {
	// TODO Auto-generated method stub
	userDao.updateAddress(address);
    }

    @Override
    public void deleteAddress(UserAddress address) {
	// TODO Auto-generated method stub
	userDao.deleteAddress(address);
    }

    @Override
    public User findUserById(String id) {
	// TODO Auto-generated method stub
	return userDao.findUserById(id);
    }

    @Override
    public Favorite findFavoriteByFood(Food food) {
	// TODO Auto-generated method stub
	
	return userDao.findFavoriteByFood(food);
    }

    @Override
    public void deleteFavorite(Favorite favorite) {
	// TODO Auto-generated method stub
	userDao.deleteFavorite(favorite);
    }

    @Override
    public void updateInfor(MultipartFile filedata, String userName,HttpServletRequest request) {
	// TODO Auto-generated method stub
	User user=(User)request.getSession().getAttribute("u");
	User user2=userDao.findUserById(user.getId());
	if (filedata.getSize()>0) {
	    String src=UpUtils.getUserSrc(filedata, request);
	    user2.setUserImg(src);
	}
	user2.setUserName(userName);
	userDao.updateUser(user2);
    }

    @Override
    public List<MyFoods> findMyFoodsByUser(User user) {
	// TODO Auto-generated method stub
	
	return userDao.findMyFoodsByUser(user);
    }
}
