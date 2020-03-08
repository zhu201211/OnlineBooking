package com.cqut.OnlineMealBooking.utils;

import java.util.*;

import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Intent_foods;
import com.cqut.OnlineMealBooking.pojo.MyFoods;

public class FoodUtil {
    //��ȡintentfoods���foods
    public static List<Food> getFoodsFormIntentFoods(List<Intent_foods> intent_foodss) {
	List<Food> foods= new ArrayList<>();
	
	for (Intent_foods intent_foods : intent_foodss) {
	    Food food=intent_foods.getFood();
	    foods.add(food);
	}
	
	return foods;
	
    }
    //�ж�MyFoodsList�Ƿ��Ѿ���Food
    public static boolean isIncludeFoodFromMyFoods(Food food, List<MyFoods> mFoods) {
	// TODO Auto-generated method stub
	
	for (MyFoods myFoods : mFoods) {
	    Food f=myFoods.getFood();
	    if (f.getId()==food.getId()||f.getId().equals(food.getId())) {
		return true;
	    }
	}
	
	return false;
    }
}
