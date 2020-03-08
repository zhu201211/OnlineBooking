package com.cqut.OnlineMealBooking.dao;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Favorite;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;

public interface UserDao {

    User loginByPhone(String userPhone, String userPassword);

    User findByPhone(String userPhone);

    void addUser(User user);


    void updateUser(User user);

    List<UserAddress> findAddressList(User user);

    void addUserAddress(UserAddress userAddress);

    UserAddress findAddressById(String addressId);

     List<User> getUserList();

    void createFavorite(Favorite favorite);

    List<Favorite> findAllFavorites(User user);

    void updateAddress(UserAddress address);

    void deleteAddress(UserAddress address);

    User findUserById(String id);

    Favorite findFavoriteByFood(Food food);

    void deleteFavorite(Favorite favorite);

    List<MyFoods> findMyFoodsByUser(User user);



}
