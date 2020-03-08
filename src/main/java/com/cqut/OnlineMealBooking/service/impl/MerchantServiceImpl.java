package com.cqut.OnlineMealBooking.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqut.OnlineMealBooking.dao.MerchantDao;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.service.MerchantService;

@Service
@Transactional
public class MerchantServiceImpl implements MerchantService{
    @Resource
    private MerchantDao merchantDao;

    @Override
    public List<Merchant> getMerchantLists() {
	// TODO Auto-generated method stub
	List<Merchant> ms=merchantDao.getMerchantLists();
	List<Merchant> merchants=new ArrayList<>();
	for (Merchant merchant : ms) {
	    if (merchant.getState().equals("营业中")||merchant.getState().equals("新入驻")) {
		merchants.add(merchant);
	    }
	}
	return merchants;
	
    }

    @Override
    public Merchant findMerchantById(String merchantId) {
	// TODO Auto-generated method stub
	Merchant merchant=merchantDao.findMerchantById(merchantId);
	return merchant;
    }

    @Override
    public List<Food> findFoodsByMerchant(Merchant merchant) {
	// TODO Auto-generated method stub
	List<Food> foods=merchantDao.findFoodsByMerchant(merchant);
	return foods;
    }

    @Override
    public void updateMerchant(Merchant merchant) {
	// TODO Auto-generated method stub
	merchantDao.updateMerchant(merchant);
    }

    @Override
    public List<Merchant> serachMerchant(String keyword) {
	// TODO Auto-generated method stub
	return merchantDao.serachMerchant(keyword);
    }

  
}
