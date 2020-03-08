package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * 用户实体
 * @author 祝剑锋
 *
 */
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length=11)
    private String id;
    
    @Column(length=12)
    private String userPhone;

    @Column(length=20)
    private String userPassword;
    
    @Column(length=20)
    private String userName;

    @Column(length=100)
    private String userImg;
    
    @OneToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
    
    @OneToMany(mappedBy="user")
    private List<UserAddress> addresses;
    
    @OneToMany(mappedBy="user")
    private List<Favorite> favorites;
    
    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @OneToMany(mappedBy="user")
    private List<MyFoods> MyFoodList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddress> addresses) {
        this.addresses = addresses;
    }

    public List<MyFoods> getMyFoodList() {
        return MyFoodList;
    }

    public void setMyFoodList(List<MyFoods> myFoodList) {
        MyFoodList = myFoodList;
    }
    

}
