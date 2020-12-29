package com.example.cart.controllers;

import com.example.cart.bean.Cart;
import com.example.cart.dao.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * description: CartController
 * date: 12/30/20 6:17 AM
 * author: fourwood
 */
@RestController
public class CartController {
    @Autowired
    private CartRepository cartRepository;
    public static final int PG_SIZE = 10;

    @RequestMapping(value = "/commodity", method = RequestMethod.GET)
    public ResponseEntity getCartList(@RequestParam int userId , @RequestParam(defaultValue = "1") int pageNumber){
        List<Cart> carts = cartRepository.findByUserId(userId);
        int sz = carts.size();
        int pgStart = (pageNumber - 1) * PG_SIZE;
        int pgEnd = pageNumber * PG_SIZE;
        pgEnd = Math.min(pgEnd, sz);
        try {
            carts = carts.subList(pgStart, pgEnd);
        }catch (IndexOutOfBoundsException | IllegalArgumentException e){
            carts.clear();
        }
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("commodities", carts);
        response.put("count", sz);
        response.put("pgNum", pageNumber);
        response.put("pgSize", PG_SIZE);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/commodity/{commodityId}", method = RequestMethod.GET)
    public ResponseEntity getCartByCommodityId(@RequestParam int userId , @PathVariable int commodityId){
        Cart cart = cartRepository.findDistinctByCommodityIdAndUserId(commodityId, userId);
        if(cart == null) return new ResponseEntity<>("Cart Not Found!", HttpStatus.NOT_FOUND);
        return new ResponseEntity(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/commodity", method = RequestMethod.POST)
    public ResponseEntity createCart(@RequestBody HashMap<String, Object> body){
        Integer userId = (Integer)body.getOrDefault("userId", null);
        Integer commodityId = (Integer)body.getOrDefault("commodityId", null);
        Integer num = (Integer)body.getOrDefault("num", null);
        if(userId == null || commodityId == null || num == null){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        Cart cart = new Cart(userId, commodityId, num);
        cart = cartRepository.save(cart);
        return new ResponseEntity(cart, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/commodity", method = RequestMethod.DELETE)
    public ResponseEntity deleteCart(@RequestParam int userId , @RequestParam int commodityId){
        try{
            cartRepository.deleteByCommodityIdAndUserId(commodityId, userId);
        }catch (RuntimeException e){
            return new ResponseEntity<>("Cart Not Found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/commodity", method = RequestMethod.PUT)
    public ResponseEntity changeCart(@RequestParam int userId , @RequestParam int commodityId, @RequestBody HashMap<String, Integer> body){
        Cart cart = cartRepository.findDistinctByCommodityIdAndUserId(commodityId, userId);
        if(cart == null){
            return new ResponseEntity<>("Cart Not Found!", HttpStatus.NOT_FOUND);
        }
        Integer num = body.getOrDefault("num", null);
        if(num == null) return new ResponseEntity<>("Bad Request!", HttpStatus.BAD_REQUEST);
        cart.setNum(num);
        cartRepository.save(cart);
        return new ResponseEntity(HttpStatus.OK);
    }
}
