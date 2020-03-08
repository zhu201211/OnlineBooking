package com.cqut.OnlineMealBooking.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cqut.OnlineMealBooking.dao.MyFoodsDao;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;

@Repository
public class MyFoodsDaoImpl implements MyFoodsDao {
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void addMyFoods(MyFoods myFoods) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().persist(myFoods);
    }

    @Override
    public List<MyFoods> getMyFoodsList(User user) {
	// TODO Auto-generated method stub
	String hql="from MyFoods mf where mf.user=:user";
	return sessionFactory.getCurrentSession().createQuery(hql,MyFoods.class).setParameter("user", user).getResultList();
    }

    @Override
    public void deleteMyFoods(MyFoods m) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(m);
    }
}
