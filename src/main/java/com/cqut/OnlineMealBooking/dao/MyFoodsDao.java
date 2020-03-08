package com.cqut.OnlineMealBooking.dao;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;

public interface MyFoodsDao {

    void addMyFoods(MyFoods myFoods);

    List<MyFoods> getMyFoodsList(User user);

    void deleteMyFoods(MyFoods m);

}
