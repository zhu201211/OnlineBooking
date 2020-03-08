package com.cqut.OnlineMealBooking.service;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Merchant;

public interface MerchantService {

    List<Merchant> getMerchantLists();

    Merchant findMerchantById(String merchantId);

    List<Food> findFoodsByMerchant(Merchant merchant);

    void updateMerchant(Merchant merchant);

    List<Merchant> serachMerchant(String keyword);


}
