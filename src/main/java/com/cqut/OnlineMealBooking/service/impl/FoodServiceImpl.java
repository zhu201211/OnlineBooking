package com.cqut.OnlineMealBooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cqut.OnlineMealBooking.dao.FoodDao;
import com.cqut.OnlineMealBooking.dao.MerchantDao;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.pojo.Message;
import com.cqut.OnlineMealBooking.pojo.SalesState;
import com.cqut.OnlineMealBooking.service.FoodService;
import com.cqut.OnlineMealBooking.utils.UpUtils;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {
    @Resource
    private FoodDao foodDao;
    @Resource
    private MerchantDao merchantDao;

    @Override
    public List<Food> getFoodList() {
	// TODO Auto-generated method stub
	List<Food> foods=foodDao.getFoodList();
	List<Food> fs=new ArrayList<>();
	for (Food food : foods) {
	    if (food.getSalesState().getId().equals("00001")||food.getSalesState().getId()=="00001") {
		fs.add(food);
	    }
	}
	return fs;
    }

    @Override
    public Food findFoodById(String id) {
	// TODO Auto-generated method stub
	return foodDao.findFoodById(id);
    }

    @Override
    public List<Food> getFoodListByPrice() {
	// TODO Auto-generated method stub
	return foodDao.getFoodListByPrice();
    }

    @Override
    public SalesState findSalesStateById(String id) {
	// TODO Auto-generated method stub
	return foodDao.findSalesStateById(id);
    }

    @Override
    public void update(Food food) {
	// TODO Auto-generated method stub
	foodDao.updateFood(food);
    }

    @Override
    public Message addFood(String foodName, String price, String merchantId, String foodIntro, MultipartFile filedata,HttpServletRequest request) {
	// TODO Auto-generated method stub
	Message msg=new Message();
	Merchant merchant=merchantDao.findMerchantById(merchantId);
	if (merchant==null) {
	    msg.setMsg("添加失败，商家不存在");
	    return msg;
	} else {
	    String id=UUID.randomUUID().toString().substring(0, 4);
	    double foodprice=Double.parseDouble(price);
	    String src=UpUtils.getFoodSrc(filedata, request);
	    SalesState s=foodDao.findSalesStateById("00001");
	    Food food=new Food();
	    food.setId(id);
	    food.setPrice(foodprice);
	    food.setFoodName(foodName);
	    food.setFoodIntro(foodIntro);
	    food.setFoodImg(src);
	    food.setSalesVolume(0);
	    food.setMerchant(merchant);
	    food.setSalesState(s);
	    foodDao.addFood(food);
	    msg.setMsg("添加成功！");
	    return msg;
	}
	
    }

    @Override
    public List<Food> serachFood(String keyword) {
	// TODO Auto-generated method stub
	return foodDao.serachFood(keyword);
    }

}
