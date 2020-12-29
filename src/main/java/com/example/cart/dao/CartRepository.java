package com.example.cart.dao;

import com.example.cart.bean.Cart;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * description: CartRepository
 * date: 12/30/20 6:15 AM
 * author: fourwood
 */
public interface CartRepository extends CrudRepository<Cart, Integer> {
    List<Cart> findByUserId(Integer userId);

    Cart findDistinctByCommodityIdAndUserId(Integer commodityId, Integer userId);

    @Transactional
    void deleteByCommodityIdAndUserId(Integer commodityId, Integer userId);
}
