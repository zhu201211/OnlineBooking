package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 订单菜品实体
 * @author 祝剑锋
 *
 */
@Entity
@Table(name="intent_foods")
public class Intent_foods  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length=11)
    private String id;
    
    @ManyToOne
    @JoinColumn(name="intent_id")
    private Intent intent;
    
    @OneToOne
    @JoinColumn(name = "food_id")
    private Food food;
    
    @Column
    private int num;
    
    @Column
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
