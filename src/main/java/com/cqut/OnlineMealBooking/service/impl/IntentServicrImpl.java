package com.cqut.OnlineMealBooking.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqut.OnlineMealBooking.dao.FoodDao;
import com.cqut.OnlineMealBooking.dao.IntentDao;
import com.cqut.OnlineMealBooking.dao.MerchantDao;
import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.CartFood;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Intent_foods;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;
import com.cqut.OnlineMealBooking.service.IntentService;
@Service
@Transactional
public class IntentServicrImpl implements IntentService{
    @Resource
    private IntentDao intentDao;
    @Resource
    private FoodDao foodDao;
    @Resource
    private MerchantDao merchantDao;
    @Override
    public Intent createIntent(Cart cart,List<CartFood> cartFoods, UserAddress uAddress, User user) {
	// TODO Auto-generated method stub
	String intentId=UUID.randomUUID().toString().substring(0, 8);
	Intent intent = new Intent();
	intent.setId(intentId);
	intent.setUserId(user.getId());
	intent.setUserAddress(uAddress);
	Date date =new Date();
	intent.setOrderTime(date);
	intent.setTotalNum(cart.getTotalNum());
	intent.setTotalPrice(cart.getTotalPrice());
	intent.setOrderStatus("未付款");//未付款
	intentDao.addIntent(intent);
	Intent intent2=intentDao.findIntentById(intentId);
	for (CartFood cartFood : cartFoods) {
	    Intent_foods intent_foods=new Intent_foods();
	    intent_foods.setId(cartFood.getId());
	    intent_foods.setNum(cartFood.getFoodNum());
	    intent_foods.setPrice(cartFood.getPrice());
	    intent_foods.setFood(cartFood.getFood());
	    intent_foods.setIntent(intent2);
	    intentDao.addIntent_foods(intent_foods);
	    Food food=cartFood.getFood();
	    food.setSalesVolume(food.getSalesVolume()+1);
	    foodDao.updateFood(food);
	    Merchant merchant=food.getMerchant();
	    merchant.setMerchantPop(merchant.getMerchantPop()+1);
	    merchantDao.updateMerchant(merchant);
	}
	return intent2;
    }
    @Override
    public Intent findIntentById(String intentId) {
	// TODO Auto-generated method stub
	
	return intentDao.findIntentById(intentId);
    }
    @Override
    public List<Intent_foods> findIntentFoodsListByIntent(Intent intent) {
	// TODO Auto-generated method stub
	
	
	return intentDao.findIntentFoodsListByIntent(intent);
    }
    @Override
    public Intent updateIntent(Intent intent) {
	// TODO Auto-generated method stub
	intentDao.updateIntent(intent);
	return intentDao.findIntentById(intent.getId());
    }
    @Override
    public List<Intent> findIntent1ByUser(User user) {
	// TODO Auto-generated method stub
	List<Intent> intents=intentDao.findIntentByUserId(user.getId());
	List<Intent> iList=new ArrayList<>();
	for (Intent intent : intents) {
	    if (intent.getOrderStatus().equals("未付款")) {
		iList.add(intent);
	    }
	}
	return iList;
    }
    @Override
    public List<Intent> findIntent2ByUser(User user) {
	// TODO Auto-generated method stub
	List<Intent> intents=intentDao.findIntentByUserId(user.getId());
	List<Intent> iList=new ArrayList<>();
	for (Intent intent : intents) {
	    if (intent.getOrderStatus().equals("待收货")) {
		iList.add(intent);
	    }
	}
	return iList;
    }
    @Override
    public List<Intent> findIntentByUser(User user) {
	// TODO Auto-generated method stub
	List<Intent> intents=intentDao.findIntentByUserId(user.getId());
	List<Intent> iList=new ArrayList<>();
	for (Intent i : intents) {
	    if (i.getOrderStatus().equals("已收货")) {
		iList.add(i);
	    }
	}
	return iList;
    }
    @Override
    public void deleteIntentFoods(Intent_foods i) {
	// TODO Auto-generated method stub
	intentDao.deleteIntentFoods(i);
    }
    @Override
    public void deleteIntent(Intent intent) {
	// TODO Auto-generated method stub
	intentDao.deleteIntent(intent);
    }
    @Override
    public List<Intent> findIntentByAddress(UserAddress address) {
	// TODO Auto-generated method stub
	return intentDao.findIntentByAddress(address);
    }
    @Override
    public List<Intent> findAllIntentByUser(User u) {
	// TODO Auto-generated method stub
	return intentDao.findIntentByUserId(u.getId());
    }

}
