package com.cqut.OnlineMealBooking.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Message;
import com.cqut.OnlineMealBooking.pojo.SalesState;

public interface FoodService {

    List<Food> getFoodList();

    Food findFoodById(String id);

    List<Food> getFoodListByPrice();

    SalesState findSalesStateById(String id);

    void update(Food food);

    Message addFood(String foodName, String price, String merchantId, String foodIntro, MultipartFile filedata,HttpServletRequest request);

    List<Food> serachFood(String keyword);


}
