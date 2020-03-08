package com.cqut.OnlineMealBooking.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cqut.OnlineMealBooking.dao.IntentDao;
import com.cqut.OnlineMealBooking.pojo.*;
@Repository
public class IntentDaoImpl implements IntentDao{
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void addIntent(Intent intent) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().persist(intent);
    }


    @Override
    public void addIntent_foods(Intent_foods intent_foods) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().persist(intent_foods);
    }

    @Override
    public Intent findIntentById(String id) {
	// TODO Auto-generated method stub
	String hql="from Intent i where i.id=:id";
	return sessionFactory.getCurrentSession().createQuery(hql,Intent.class).setParameter("id",id).uniqueResult();
    }

    @Override
    public List<Intent_foods> findIntentFoodsListByIntent(Intent intent) {
	// TODO Auto-generated method stub
	String hql="from Intent_foods inf where inf.intent=:intent";
	return sessionFactory.getCurrentSession().createQuery(hql,Intent_foods.class).setParameter("intent", intent).getResultList();
    }


    @Override
    public void updateIntent(Intent intent) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(intent);
    }


    @Override
    public List<Intent> findIntentByUserId(String userId) {
	// TODO Auto-generated method stub
	String hql="from Intent i where i.userId=:userId";
	return sessionFactory.getCurrentSession().createQuery(hql,Intent.class).setParameter("userId", userId).getResultList();
    }


    @Override
    public void deleteIntentFoods(Intent_foods i) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(i);
    }


    @Override
    public void deleteIntent(Intent intent) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(intent);
    }


    @Override
    public List<Intent> findIntentByAddress(UserAddress userAddress) {
	// TODO Auto-generated method stub
	String hql="from Intent i where i.userAddress=:userAddress";
	return sessionFactory.getCurrentSession().createQuery(hql,Intent.class).setParameter("userAddress", userAddress).getResultList();
    }
}
