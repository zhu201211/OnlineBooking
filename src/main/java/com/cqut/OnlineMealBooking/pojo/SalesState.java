package com.cqut.OnlineMealBooking.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 菜品销售状态实体
 * @author 祝剑锋
 *
 */
@Entity
@Table(name="salesState")
public class SalesState implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length=11)
    private String id;
    
    @Column(length=11)
    private String state;
    
    @OneToMany(mappedBy="salesState")
    private List<Food> foods;

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    

}
