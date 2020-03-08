package com.cqut.OnlineMealBooking.service;

import java.util.List;

import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.CartFood;
import com.cqut.OnlineMealBooking.pojo.Food;

public interface CartService {

    //�����µĲͳ�
    void addCart(Cart cart);
    //ͨ���ͳ�ID��ѯ�ͳ�
    Cart findCartById(String id);
    //�����ͳ��в�Ʒ
    CartFood addCartFood(String cartId,Food food);
    //ͨ���ͳ�ID��ѯ��Ʒ�б�
    List<CartFood> findCartFoodListByCartId(String id);
    //���²ͳ�
    void updateCart(Cart cart);
    //���ݲͳ�ɾ����Ʒ
    void deleteCartFoodByCart(Cart cart);
    //��ѯ�ͳ���Ʒ
    CartFood findCartFoodById(String cartFoodId);
    
    void deleteCartFoodById(String cartFoodId);
    void deleteCart(Cart cart);
    void updateCartFood(CartFood cartFood);
    Cart clearCart(Cart cart);
}
