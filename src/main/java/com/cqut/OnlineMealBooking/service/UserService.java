package com.cqut.OnlineMealBooking.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.cqut.OnlineMealBooking.pojo.Favorite;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Message;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;

public interface UserService {

    User loginByPhone(String userPhone, String userPassword);
    
    User findByPhone(String userPhone);

    Message addUser(User user);

    void updateUser(User user);

    List<UserAddress> findAddressList(User user);

    void addUserAddress(UserAddress userAddress, User user);

    UserAddress findAddressById(String addressId);

    List<User> getUserList();

    void createFavorite(Favorite favorite);

    List<Favorite> findAllFavorites(User user);

    void updateAddress(UserAddress address);

    void deleteAddress(UserAddress address);

    User findUserById(String id);

    Favorite findFavoriteByFood(Food food);

    void deleteFavorite(Favorite favorite);


    void updateInfor(MultipartFile filedata, String userName, HttpServletRequest request);

    List<MyFoods> findMyFoodsByUser(User user);


}
