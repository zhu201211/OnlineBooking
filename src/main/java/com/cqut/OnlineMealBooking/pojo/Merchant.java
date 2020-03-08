package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * µêÆÌÊµÌå
 * @author ×£½£·æ
 *
 */
@Entity
@Table(name="merchant")
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length=11)
    private String merchantId;
    
    @Column(length=12)
    private String merchantName;
    
    @Column(length=12)
    private String merchantPhone;
    
    @Column(length=120)
    private String merchantAddress;
    
    @Column(length=120)
    private String merchantIntro;
    
    @OneToMany(mappedBy="merchant")
    private List<Food> foods;
    
    @Column(length=100)
    private String merchantImg;
    
    @Column
    private long merchantPop; 
    
    @Column(length=12)
    private String state;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantIntro() {
        return merchantIntro;
    }

    public void setMerchantIntro(String merchantIntro) {
        this.merchantIntro = merchantIntro;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getMerchantImg() {
        return merchantImg;
    }

    public void setMerchantImg(String merchantImg) {
        this.merchantImg = merchantImg;
    }

    public long getMerchantPop() {
        return merchantPop;
    }

    public void setMerchantPop(long merchantPop) {
        this.merchantPop = merchantPop;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



   
}
