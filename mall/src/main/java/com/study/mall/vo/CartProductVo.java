package com.study.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yang
 * @date 2022/01/20 22:33
 **/
@Data
public class CartProductVo {

    private Integer productId;

    /**
     * 购物车中商品的数量
     */
    private  Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;
    /**
     * 商品是否选中
     */
    private Boolean productSelected;
}