package com.cqut.OnlineMealBooking.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cqut.OnlineMealBooking.dao.CartDao;
import com.cqut.OnlineMealBooking.pojo.Cart;
import com.cqut.OnlineMealBooking.pojo.CartFood;
import com.cqut.OnlineMealBooking.pojo.Food;

@Repository
public class CartDaoImpl implements CartDao{
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Cart findCartById(String id) {
	// TODO Auto-generated method stub
	System.out.println("findCartById+"+id);
	String hql="from Cart c where c.id=:id";
	Cart cart=sessionFactory.getCurrentSession().createQuery(hql,Cart.class).setParameter("id", id).uniqueResult();
	return cart;
    }

    @Override
    public void addCart(Cart cart) {
	// TODO Auto-generated method stub
	System.out.println("addCart");
	sessionFactory.getCurrentSession().persist(cart);
    }

    @Override
    public CartFood findCartFoodByCartAndFood(Cart cart,Food food) {
	// TODO Auto-generated method stub
	String hql="from CartFood cf where cf.food=:food and cf.cart=:cart";
	return sessionFactory.getCurrentSession().createQuery(hql,CartFood.class).setParameter("food", food).setParameter("cart", cart).uniqueResult();
    }

    @Override
    public void addCartFood(CartFood cartFood) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().persist(cartFood);
    }

    @Override
    public void updateCartFood(CartFood cartFood) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(cartFood);
    }

    @Override
    public void updataCart(Cart cart) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(cart);
    }

    @Override
    public List<CartFood> findCartFoodListByCart(Cart cart) {
	// TODO Auto-generated method stub
	String hql="from CartFood cf where cf.cart=:cart";
	return sessionFactory.getCurrentSession().createQuery(hql,CartFood.class).setParameter("cart", cart).getResultList();
    }

    @Override
    public void deleteCartFood(CartFood cartFood) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(cartFood);
	
    }

    @Override
    public CartFood findCartFoodById(String id) {
	// TODO Auto-generated method stub
	String hql="from CartFood cf where cf.id=:id";
	return sessionFactory.getCurrentSession().createQuery(hql,CartFood.class).setParameter("id", id).uniqueResult();
    }

    @Override
    public void deleteCart(Cart cart) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(cart);
    }


}
