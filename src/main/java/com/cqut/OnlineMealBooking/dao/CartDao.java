package com.cqut.OnlineMealBooking.dao;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.CartFood;
import com.cqut.OnlineMealBooking.pojo.Food;

public interface CartDao {

    Cart findCartById(String id);

    void addCart(Cart cart);

    CartFood findCartFoodByCartAndFood(Cart cart,Food food);

    void addCartFood(CartFood cartFood);

    void updateCartFood(CartFood cartFood);

    void updataCart(Cart cart);

    List<CartFood> findCartFoodListByCart(Cart cart);

    void deleteCartFood(CartFood cartFood);

    CartFood findCartFoodById(String id);

    void deleteCart(Cart cart);





}
