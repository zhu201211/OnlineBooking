package com.cqut.OnlineMealBooking.pojo;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ËÍ²ÍµØÖ·ÊµÌå
 * @author ×£½£·æ
 *
 */
@Entity
@Table(name="userAddress")
public class UserAddress implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length=11)
    private String id;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Column(length=120)
    private String frontAddress;
    
    @Column(length=120)
    private String detailAddress;
    
    @Column(length=12)
    private String userPhone;
    
    @Column(length=12)
    private String userName;
    
    @OneToMany(mappedBy="userAddress")
    private List<Intent> intents;

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

    public String getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(String frontAddress) {
        this.frontAddress = frontAddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Intent> getIntents() {
        return intents;
    }

    public void setIntents(List<Intent> intents) {
        this.intents = intents;
    }

   
}
