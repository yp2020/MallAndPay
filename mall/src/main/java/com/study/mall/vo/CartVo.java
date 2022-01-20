package com.study.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yang
 * @date 2022/01/20 22:33
 **/
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selectedAll;

    // 购物车商品总价格

    private BigDecimal cartTotalPrice;

    //购物车商品总数量
    private Integer cartTotalQuantity;


}