package com.study.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yang
 * @date 2022/01/25 20:41
 **/
@Data
public class OrderItemVo {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;


    /**
     * 交易完成时的 价格
     */
    private BigDecimal currentUnitPrice;

    /**
     * 交易完成时 数量
     */
    private Integer quantity;

    private BigDecimal totalPrice;

}