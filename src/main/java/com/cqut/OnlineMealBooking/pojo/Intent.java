package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * ¶©µ¥ÊµÌå
 * @author ×£½£·æ
 *
 */
@Entity
@Table(name="intent")
public class Intent implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length=15)
    private String id;
    
    @Column(length=11)
    private String userId;
    
    @OneToMany(mappedBy="intent")
    private List<Intent_foods> intent_foodss;
    
    @ManyToOne
    @JoinColumn(name="userAddress_id")
    private UserAddress userAddress;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;
   
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;
    
    @Column
    private int totalNum;

    @Column
    private double totalPrice;
    
    @Column(length=12)
    private String orderStatus;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Intent_foods> getIntent_foodss() {
        return intent_foodss;
    }

    public void setIntent_foodss(List<Intent_foods> intent_foodss) {
        this.intent_foodss = intent_foodss;
    }

    

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    

}
