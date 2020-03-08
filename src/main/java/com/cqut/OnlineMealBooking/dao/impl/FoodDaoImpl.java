package com.cqut.OnlineMealBooking.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cqut.OnlineMealBooking.dao.FoodDao;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.SalesState;
@Repository
public class FoodDaoImpl implements FoodDao{
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<Food> getFoodList() {
	// TODO Auto-generated method stub
	String hql="from Food f order by salesVolume desc";
	return sessionFactory.getCurrentSession().createQuery(hql,Food.class).getResultList();
    }

    @Override
    public Food findFoodById(String id) {
	// TODO Auto-generated method stub
	String hql="from Food f where f.id=:id";
	return sessionFactory.getCurrentSession().createQuery(hql,Food.class).setParameter("id", id).uniqueResult();
    }

    @Override
    public void updateFood(Food food) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(food);
    }

    @Override
    public List<Food> getFoodListByPrice() {
	// TODO Auto-generated method stub
	String hql="from Food f order by price";
	return sessionFactory.getCurrentSession().createQuery(hql,Food.class).getResultList();
    }

    @Override
    public List<Food> findAllFoods() {
	// TODO Auto-generated method stub
	String hql="from Food f order by f.id";
	return sessionFactory.getCurrentSession().createQuery(hql,Food.class).getResultList();
    }

    @Override
    public List<Food> findFoodsByInfo(String info) {
	// TODO Auto-generated method stub
	String hql="from Food f where f.foodName like :foodName or f.id like :id";
	return sessionFactory.getCurrentSession().createQuery(hql,Food.class).setParameter("foodName", "%"+info+"%").setParameter("id", "%"+info+"%").getResultList();
    }

    @Override
    public SalesState findSalesStateById(String id) {
	// TODO Auto-generated method stub
	String hql="from SalesState s where s.id=:id";
	return sessionFactory.getCurrentSession().createQuery(hql,SalesState.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public void addFood(Food food) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().persist(food);
    }

    @Override
    public List<Food> serachFood(String keyword) {
	// TODO Auto-generated method stub
	String hql="from Food f where f.foodName like :foodName or f.foodIntro like :foodIntro";
	return sessionFactory.getCurrentSession().createQuery(hql,Food.class).setParameter("foodName", "%"+keyword+"%").setParameter("foodIntro", "%"+keyword+"%").getResultList();
    }

   
}
