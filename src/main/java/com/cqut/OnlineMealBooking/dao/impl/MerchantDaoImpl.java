package com.cqut.OnlineMealBooking.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cqut.OnlineMealBooking.dao.MerchantDao;
import com.cqut.OnlineMealBooking.pojo.Food;
import com.cqut.OnlineMealBooking.pojo.Merchant;

@Repository
public class MerchantDaoImpl implements MerchantDao{
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<Merchant> getMerchantLists() {
	// TODO Auto-generated method stub
	String hql="from Merchant m order by merchantPop desc";
	return sessionFactory.getCurrentSession().createQuery(hql,Merchant.class).getResultList();
    }

    @Override
    public Merchant findMerchantById(String merchantId) {
	// TODO Auto-generated method stub
	System.out.println("find merchant by id="+merchantId);
	String hql="from Merchant m where m.merchantId=:merchantId";
	return sessionFactory.getCurrentSession().createQuery(hql,Merchant.class).setParameter("merchantId", merchantId).uniqueResult();
    }

    @Override
    public List<Food> findFoodsByMerchant(Merchant merchant) {
	// TODO Auto-generated method stub
	String hql="from Food f where f.merchant=:merchant";
	return sessionFactory.getCurrentSession().createQuery(hql,Food.class).setParameter("merchant",merchant).getResultList();
    }

    @Override
    public void updateMerchant(Merchant merchant) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().merge(merchant);
    }

    @Override
    public void addMerchant(Merchant merchant) {
	// TODO Auto-generated method stub
	sessionFactory.getCurrentSession().persist(merchant);
    }

    @Override
    public List<Merchant> findMerchantByInfo(String info) {
	// TODO Auto-generated method stub
	String hql="from Merchant m where m.merchantName like :merchantName or m.merchantId like :merchantId";
	return sessionFactory.getCurrentSession().createQuery(hql,Merchant.class).setParameter("merchantName", "%"+info+"%").setParameter("merchantId", "%"+info+"%").getResultList();
    }

    @Override
    public List<Merchant> serachMerchant(String keyword) {
	// TODO Auto-generated method stub
	String hql="from Merchant m where m.merchantName like :merchantName or m.merchantIntro like :merchantIntro";
	return sessionFactory.getCurrentSession().createQuery(hql,Merchant.class).setParameter("merchantName", "%"+keyword+"%").setParameter("merchantIntro", "%"+keyword+"%").getResultList();
    }

  
}
