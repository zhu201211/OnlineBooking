package com.cqut.OnlineMealBooking.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cqut.OnlineMealBooking.dao.AdminDao;
import com.cqut.OnlineMealBooking.pojo.Intent;
import com.cqut.OnlineMealBooking.pojo.Merchant;
import com.cqut.OnlineMealBooking.pojo.User;

@Repository
public class AdminDaoImpl implements AdminDao{
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public User login(String userName, String userPassword) {
	// TODO Auto-generated method stub
	
	String hql="from User  u where  u.userPhone=:userPhone and u.userPassword=:userPassword";
	User user = sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("userPhone", userName).setParameter("userPassword", userPassword).uniqueResult();
	return user;
    }

    @Override
    public List<User> findAllUser() {
	// TODO Auto-generated method stub
	String hql="from User u order by u.id";
	return sessionFactory.getCurrentSession().createQuery(hql,User.class).getResultList();

    }

    @Override
    public User findUserById(String id) {
	// TODO Auto-generated method stub
	String hql="from User u where u.id =:id";
	return sessionFactory.getCurrentSession().createQuery(hql,User.class).setParameter("id", id).uniqueResult();
    }

    @Override
    public void updataUser(User user) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public void deleteUser(User user) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public List<User> searchUsersByInfo(String searchInfo) {
	// TODO Auto-generated method stub
	System.out.println("查询用户内容："+searchInfo);
	String hql="from User u where u.userName like :userName or u.userPhone like :userPhone";
	return sessionFactory.getCurrentSession().createQuery(hql,User.class).setParameter("userName", "%"+searchInfo+"%").setParameter("userPhone", "%"+searchInfo+"%").getResultList();
    }

    @Override
    public List<Merchant> findAllMerchant() {
	// TODO Auto-generated method stub
	String hql="from Merchant m order by m.merchantId";
	return sessionFactory.getCurrentSession().createQuery(hql,Merchant.class).getResultList();
    }

    @Override
    public List<Intent> findIntentByState(String state) {
	// TODO Auto-generated method stub
	String hql="from Intent i where i.orderStatus=:orderStatus";
	return sessionFactory.getCurrentSession().createQuery(hql,Intent.class).setParameter("orderStatus", state).getResultList();
    }

    @Override
    public List<User> findUserByInfo(String info) {
	// TODO Auto-generated method stub
	String hql="from User u where u.id like :id or u.userName like :userName";
	return sessionFactory.getCurrentSession().createQuery(hql,User.class).setParameter("id", "%"+info+"%").setParameter("userName", "%"+info+"%").getResultList();
    }


    @Override
    public List<Merchant> findMerchantByInfo(String info) {
	// TODO Auto-generated method stub
	String hql="from Merchant m where m.merchantId like :merchantId or m.merchantName like :merchantName";
	return sessionFactory.getCurrentSession().createQuery(hql,Merchant.class).setParameter("merchantId", "%"+info+"%").setParameter("merchantName", "%"+info+"%").getResultList();
    }
}
