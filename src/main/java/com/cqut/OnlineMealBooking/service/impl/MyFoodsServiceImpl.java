package com.cqut.OnlineMealBooking.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqut.OnlineMealBooking.dao.MyFoodsDao;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.service.MyFoodsService;
import com.cqut.OnlineMealBooking.utils.FoodUtil;
@Service
@Transactional
public class MyFoodsServiceImpl implements MyFoodsService{
    @Resource
    private MyFoodsDao myfoodsDao;

    @Override
    public void addFoodToMyFoodsList(User user,List<Food> foods) {
	// TODO Auto-generated method stub
	List<MyFoods> mFoods=myfoodsDao.getMyFoodsList(user);
	boolean b=false;
	
	for (Food food : foods) {
	    b=FoodUtil.isIncludeFoodFromMyFoods(food,mFoods);
	    if (b) {
		System.out.println("myFoodsList:"+b);
		continue;
	    }
	    MyFoods myFoods =new MyFoods();
	    String id=UUID.randomUUID().toString().substring(0, 6);
	    myFoods.setId(id);
	    myFoods.setUser(user);
	    myFoods.setFood(food);
	    myfoodsDao.addMyFoods(myFoods);
	}
    }

    @Override
    public List<MyFoods> getMyFoodsList(User user1) {
	// TODO Auto-generated method stub
	
	return myfoodsDao.getMyFoodsList(user1);
    }

    @Override
    public void deleteMyFoods(MyFoods m) {
	// TODO Auto-generated method stub
	myfoodsDao.deleteMyFoods(m);
    }
}
