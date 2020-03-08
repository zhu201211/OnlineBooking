package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * ²ËÆ·ÊµÌå
 * @author ×£½£·æ
 *
 */
@Entity
@Table(name="food")
public class Food implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length=11)
    private String id;
    
    @Column(length=12)
    private String foodName;

    @ManyToOne
    @JoinColumn(name="salesState_id")
    private SalesState salesState;
    
    @Column(length=150)
    private String foodIntro;
    
    
    @ManyToOne
    @JoinColumn(name="merchant_id")
    private Merchant merchant;
    
    @OneToMany(mappedBy="food")
    private List<Favorite> favorites;
    
    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Column
    private double price;
    
    @Column
    private int  salesVolume;
    
    @OneToMany(mappedBy="food")
    private List<CartFood> cartFoods;
    
    @OneToMany(mappedBy="food")
    private List<MyFoods> myFoodss;

    public List<MyFoods> getMyFoodss() {
        return myFoodss;
    }

    public void setMyFoodss(List<MyFoods> myFoodss) {
        this.myFoodss = myFoodss;
    }

    public List<CartFood> getCartFoods() {
        return cartFoods;
    }


    @Override
    public String toString() {
	return "Food [id=" + id + ", foodName=" + foodName + ", salesState=" + salesState + ", foodIntro=" + foodIntro
		+ ", merchant=" + merchant + ", favorites=" + favorites + ", price=" + price + ", salesVolume="
		+ salesVolume + ", cartFoods=" + cartFoods + ", myFoodss=" + myFoodss + ", foodImg=" + foodImg + "]";
    }

    public void setCartFoods(List<CartFood> cartFoods) {
        this.cartFoods = cartFoods;
    }

    @Column(length=100)
    private String foodImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodIntro() {
        return foodIntro;
    }

    public void setFoodIntro(String foodIntro) {
        this.foodIntro = foodIntro;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public SalesState getSalesState() {
        return salesState;
    }

    public void setSalesState(SalesState salesState) {
        this.salesState = salesState;
    }

    
}
