package com.cqut.OnlineMealBooking.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.tools.ant.taskdefs.optional.depend.constantpool.StringCPInfo;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cqut.OnlineMealBooking.dao.UserDao;
import com.cqut.OnlineMealBooking.pojo.Favorite;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.MyFoods;
import com.cqut.OnlineMealBooking.pojo.User;
import com.cqut.OnlineMealBooking.pojo.UserAddress;

@Repository
public class UserDaoImpl implements UserDao {
    
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public User loginByPhone(String userPhone, String userPassword) {
	// TODO Auto-generated method stub
	System.out.println("UserDaoImpl:"+userPhone+"+"+userPassword);
	
	String hql="from User u where  u.userPhone=:userPhone and u.userPassword=:userPassword";
	User user = sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("userPhone", userPhone).setParameter("userPassword", userPassword).uniqueResult();
	return user;
    }

    //通过电话号查找用户
    @Override
    public User findByPhone(String userPhone) {
	// TODO Auto-generated method stub
	System.out.println("findByPhone"+userPhone);
	String hql="from User u where u.userPhone=:userPhone";
	User user=sessionFactory.getCurrentSession().createQuery(hql,User.class).setParameter("userPhone", userPhone).uniqueResult();
	return user;
    }

    @Override
    public void addUser(User user) {
	// TODO Auto-generated method stub
	System.out.println("dao层添加用户");
	sessionFactory.getCurrentSession().persist(user);
    }

    

    @Override
    public void updateUser(User user) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public List<UserAddress> findAddressList(User user) {
	// TODO Auto-generated method stub
	System.out.println("UserDaoImpl:findAddressList");
	String hql="from UserAddress ua where ua.user=:user";
	
	return sessionFactory.getCurrentSession().createQuery(hql,UserAddress.class).setParameter("user",user).getResultList();
    }

    @Override
    public void addUserAddress(UserAddress userAddress) {
	// TODO Auto-generated method stub
	System.out.println("dao层添加新地址");
	sessionFactory.getCurrentSession().persist(userAddress);
    }

    @Override
    public UserAddress findAddressById(String id) {
	// TODO Auto-generated method stub
	System.out.println("UserDaoImpl:findAddressById");
	String hql="from UserAddress ua where ua.id=:id";
	return sessionFactory.getCurrentSession().createQuery(hql,UserAddress.class).setParameter("id",id).uniqueResult();
    }

    @Override
    public List<User> getUserList() {
	// TODO Auto-generated method stub
	String hql="from User u order by id";
	return sessionFactory.getCurrentSession().createQuery(hql,User.class).getResultList();
    }

    @Override
    public void createFavorite(Favorite favorite) {
	// TODO Auto-generated method stub
	System.out.println("收藏dao");
	sessionFactory.getCurrentSession().persist(favorite);
    }

    @Override
    public List<Favorite> findAllFavorites(User user) {
	// TODO Auto-generated method stub
	String hql="from Favorite fa where fa.user=:user";
	return sessionFactory.getCurrentSession().createQuery(hql,Favorite.class).setParameter("user",user).getResultList();
    }

    @Override
    public void updateAddress(UserAddress address) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(address);
    }

    @Override
    public void deleteAddress(UserAddress address) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(address);
    }

    @Override
    public User findUserById(String id) {
	// TODO Auto-generated method stub
	String hql="from User u where u.id=:id";
	return sessionFactory.getCurrentSession().createQuery(hql,User.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public Favorite findFavoriteByFood(Food food) {
	// TODO Auto-generated method stub
	String hql="from Favorite f where f.food=:food";
	return sessionFactory.getCurrentSession().createQuery(hql, Favorite.class).setParameter("food", food).getSingleResult();
    }

    @Override
    public void deleteFavorite(Favorite favorite) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().delete(favorite);
    }

    @Override
    public List<MyFoods> findMyFoodsByUser(User user) {
	// TODO Auto-generated method stub
	String hql="from MyFoods m where m.user=:user";
	return sessionFactory.getCurrentSession().createQuery(hql, MyFoods.class).setParameter("user", user).getResultList();
    }

   

}
