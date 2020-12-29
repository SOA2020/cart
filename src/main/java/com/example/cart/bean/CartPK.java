package com.example.cart.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * description: CartPK
 * date: 12/30/20 6:13 AM
 * author: fourwood
 */
public class CartPK implements Serializable {

    private Integer userId;

    private Integer commodityId;

    public CartPK(Integer userId, Integer commodityId) {
        this.userId = userId;
        this.commodityId = commodityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartPK cartPK = (CartPK) o;
        return userId.equals(cartPK.userId) && commodityId.equals(cartPK.commodityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, commodityId);
    }

    public CartPK() { }

}
