package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ¶©µ¥ÊµÌå
 * @author ×£½£·æ
 *
 */
@Entity
@Table(name="cartFood")
public class CartFood implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length=11)
    private String id;
    
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name="food_id")
    private Food food;
    
    @Column
    private int foodNum;
    
    @Column
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }


    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
	return "CartFood [id=" + id + ", cart=" + cart + ", food=" + food + ", foodNum=" + foodNum + ", price=" + price
		+ "]";
    }
    
}
