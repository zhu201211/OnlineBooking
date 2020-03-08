package com.cqut.OnlineMealBooking.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqut.OnlineMealBooking.dao.CartDao;
import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.CartFood;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService{
    
    @Resource
    private CartDao cartDao;

    @Override
    public void addCart(Cart cart) {
	// TODO Auto-generated method stub
	Cart cart2=cartDao.findCartById(cart.getId());
	if (cart2==null) {
	    cartDao.addCart(cart);
	} else {
	    String id= UUID.randomUUID().toString().substring(0, 4);
	    cart.setId(id);
	    cartDao.addCart(cart);
	}
    }

    @Override
    public Cart findCartById(String id) {
	// TODO Auto-generated method stub
	Cart cart=cartDao.findCartById(id);
	return cart;
    }

    @Override
    public CartFood addCartFood(String cartId,Food food) {
	// TODO Auto-generated method stub
	Cart cart=cartDao.findCartById(cartId);
	cart.setTotalNum(cart.getTotalNum()+1);
	cart.setTotalPrice(cart.getTotalPrice()+food.getPrice());
	CartFood c=cartDao.findCartFoodByCartAndFood(cart,food);
	cartDao.updataCart(cart);
	if (c==null) {
	    CartFood cartFood=new CartFood();
	    String id = UUID.randomUUID().toString().substring(0, 4);
	    cartFood.setId(id);
	    cartFood.setFoodNum(1);
	    cartFood.setFood(food);
	    cartFood.setPrice(food.getPrice());
	    cartFood.setCart(cart);
	    cartDao.addCartFood(cartFood);
	    return cartFood;
	} else {
	    c.setFoodNum(c.getFoodNum()+1);
	    c.setPrice(c.getPrice()+food.getPrice());
	    c.setCart(cart);
	    cartDao.updateCartFood(c);
	    return c;
	}
    }

    @Override
    public List<CartFood> findCartFoodListByCartId(String id) {
	// TODO Auto-generated method stub
	Cart cart=cartDao.findCartById(id);
	return cartDao.findCartFoodListByCart(cart);
    }

    @Override
    public void updateCart(Cart cart) {
	// TODO Auto-generated method stub
	cartDao.updataCart(cart);
    }

    @Override
    public void deleteCartFoodByCart(Cart cart) {
	// TODO Auto-generated method stub
	List<CartFood> cartFoods=cartDao.findCartFoodListByCart(cart);
	if (cartFoods==null) {
	    return ;
	} else {
	    for (CartFood cartFood : cartFoods) {
		cartDao.deleteCartFood(cartFood);
	    }
	}
    }

    @Override
    public CartFood findCartFoodById(String cartFoodId) {
	// TODO Auto-generated method stub
	return cartDao.findCartFoodById(cartFoodId);
    }

    @Override
    public void deleteCartFoodById(String cartFoodId) {
	// TODO Auto-generated method stub
	CartFood cartFood=cartDao.findCartFoodById(cartFoodId);
	cartDao.deleteCartFood(cartFood);
    }

    @Override
    public void deleteCart(Cart cart) {
	// TODO Auto-generated method stub
	cartDao.deleteCart(cart);
    }

    @Override
    public void updateCartFood(CartFood cartFood) {
	// TODO Auto-generated method stub
	cartDao.updateCartFood(cartFood);
    }

    @Override
    public Cart clearCart(Cart cart) {
	// TODO Auto-generated method stub
	List<CartFood> cartFoods=cartDao.findCartFoodListByCart(cart);
	cart.setCartFoods(null);
	cart.setTotalNum(0);
	cart.setTotalPrice(0);
	cartDao.updataCart(cart);
	
	for (CartFood cartFood : cartFoods) {
	    cartDao.deleteCartFood(cartFood);
	}
	
	Cart cart1=cartDao.findCartById(cart.getId());
	return cart1;
    }

 
}
