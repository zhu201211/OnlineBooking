package com.cqut.OnlineMealBooking.dao;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Intent_foods;
import com.cqut.OnlineMealBooking.pojo.UserAddress;

public interface IntentDao {

    void addIntent(Intent intent);

    void addIntent_foods(Intent_foods intent_foods);

    Intent findIntentById(String intentId);

    List<Intent_foods> findIntentFoodsListByIntent(Intent intent);

    void updateIntent(Intent intent);

    List<Intent> findIntentByUserId(String id);

    void deleteIntentFoods(Intent_foods i);

    void deleteIntent(Intent intent);

    List<Intent> findIntentByAddress(UserAddress userAddress);

}
