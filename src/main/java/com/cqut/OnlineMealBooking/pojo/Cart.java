package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * ²Í³µÊµÌå
 * @author ×£½£·æ
 *
 */
@Entity
@Table(name="cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length=11)
    private String id;
    
    @OneToOne(mappedBy="cart")
    private User user;
    
    @OneToMany(mappedBy="cart")
    private List<CartFood> cartFoods;
    
    @Column
    private int totalNum;
    @Column
    private double totalPrice;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<CartFood> getCartFoods() {
        return cartFoods;
    }
    public void setCartFoods(List<CartFood> cartFoods) {
        this.cartFoods = cartFoods;
    }
    public int getTotalNum() {
        return totalNum;
    }
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    @Override
    public String toString() {
	return "Cart [id=" + id + ", user=" + user + ", cartFoods=" + cartFoods + ", totalNum=" + totalNum
		+ ", totalPrice=" + totalPrice + "]";
    }
    
    
}
