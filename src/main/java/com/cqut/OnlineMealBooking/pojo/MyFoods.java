package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ÊµÌå
 * @author ×£½£·æ
 *
 */
@Entity
@Table(name="myFoods")
public class MyFoods implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length=11)
    private String id;
    
    @ManyToOne
    @JoinColumn(name="food_id")
    private Food food;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
