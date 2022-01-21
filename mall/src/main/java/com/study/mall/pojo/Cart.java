package com.study.mall.pojo;

import lombok.Data;

/**
 * @author yang
 * @date 2022/01/21 10:29
 **/
@Data
public class Cart {

    /**
     * 装进 redis 里的商品对象
     */

    private Integer productId;

    /**
     * 购物车中商品的数量
     */
    private  Integer quantity;

    private Boolean productSelected;

    public Cart() {

    }

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}