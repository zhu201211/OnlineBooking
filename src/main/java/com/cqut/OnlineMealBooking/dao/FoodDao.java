package com.cqut.OnlineMealBooking.dao;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.SalesState;

public interface FoodDao {

    List<Food> getFoodList();

    Food findFoodById(String id);

    void updateFood(Food food);

    List<Food> getFoodListByPrice();

    List<Food> findAllFoods();

    List<Food> findFoodsByInfo(String info);

    SalesState findSalesStateById(String id);

    void addFood(Food food);

    List<Food> serachFood(String keyword);



}
