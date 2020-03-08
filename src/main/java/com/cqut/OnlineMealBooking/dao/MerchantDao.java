package com.cqut.OnlineMealBooking.dao;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Merchant;

public interface MerchantDao {

    List<Merchant> getMerchantLists();

    Merchant findMerchantById(String merchantId);

    List<Food> findFoodsByMerchant(Merchant merchant);

    void updateMerchant(Merchant merchant);

    void addMerchant(Merchant merchant);

    List<Merchant> findMerchantByInfo(String info);

    List<Merchant> serachMerchant(String keyword);


}
