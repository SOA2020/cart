package com.example.cart.bean;

import javax.persistence.*;

/**
 * description: Cart
 * date: 12/30/20 6:10 AM
 * author: fourwood
 */
@Entity
@Table(name = "cart")
@IdClass(CartPK.class)
public class Cart {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "commodity_id")
    private Integer commodityId;

    private Integer num;

    public Cart(Integer userId, Integer commodityId, Integer num) {
        this.userId = userId;
        this.commodityId = commodityId;
        this.num = num;
    }

    public Cart() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", commodityId=" + commodityId +
                ", num=" + num +
                '}';
    }

}
