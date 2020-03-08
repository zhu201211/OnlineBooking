package com.cqut.OnlineMealBooking.service;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.CartFood;
import com.cqut.OnlineMealBooking.pojo.Food;

public interface CartService {

    //创建新的餐车
    void addCart(Cart cart);
    //通过餐车ID查询餐车
    Cart findCartById(String id);
    //创建餐车中菜品
    CartFood addCartFood(String cartId,Food food);
    //通过餐车ID查询菜品列表
    List<CartFood> findCartFoodListByCartId(String id);
    //更新餐车
    void updateCart(Cart cart);
    //根据餐车删除菜品
    void deleteCartFoodByCart(Cart cart);
    //查询餐车菜品
    CartFood findCartFoodById(String cartFoodId);
    
    void deleteCartFoodById(String cartFoodId);
    void deleteCart(Cart cart);
    void updateCartFood(CartFood cartFood);
    Cart clearCart(Cart cart);
}
