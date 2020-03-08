package com.cqut.OnlineMealBooking.dao;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.pojo.User;

public interface AdminDao {

    User login(String userName, String userPassword);

    List<User> findAllUser();

    User findUserById(String id);

    void updataUser(User user);

    void deleteUser(User user);

    List<User> searchUsersByInfo(String searchInfo);

    List<Merchant> findAllMerchant();

    List<Intent> findIntentByState(String state);

    List<User> findUserByInfo(String info);


    List<Merchant> findMerchantByInfo(String info);


}
