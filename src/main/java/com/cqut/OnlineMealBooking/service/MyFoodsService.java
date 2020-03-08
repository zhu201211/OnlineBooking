package com.cqut.OnlineMealBooking.service;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;

public interface MyFoodsService {

    void addFoodToMyFoodsList(User user,List<Food> foods);

    List<MyFoods> getMyFoodsList(User user1);

    void deleteMyFoods(MyFoods m);

}
