package com.cqut.OnlineMealBooking.service;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.CartFood;
import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Intent_foods;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;

public interface IntentService {

    Intent createIntent(Cart cart,List<CartFood> cartFoods, UserAddress uAddress, User user);

    Intent findIntentById(String intentId);

    List<Intent_foods> findIntentFoodsListByIntent(Intent intent);

    Intent updateIntent(Intent intent);

    List<Intent> findIntent1ByUser(User user);

    List<Intent> findIntent2ByUser(User user);

    List<Intent> findIntentByUser(User user);

    void deleteIntentFoods(Intent_foods i);

    void deleteIntent(Intent intent);

    List<Intent> findIntentByAddress(UserAddress address);

    List<Intent> findAllIntentByUser(User u);

}
