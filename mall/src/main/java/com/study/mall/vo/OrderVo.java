package com.study.mall.vo;

import com.study.mall.pojo.Shipping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yang
 * @date 2022/01/25 20:30
 **/

@Data
public class OrderVo {
    /**
     * 订单编号
     */
    private Long orderNo;

    /**
     * 付款金额
     */
    private BigDecimal payment;

    /**
     * 支付类型
     */
    private Integer paymentType;

    /**
     * 运费
     */
    private Integer postage;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单内商品列表
     */
    private List<OrderItemVo> orderItemVoList;

    /**
     * 收货地址 id
     */
    private  Integer shippingId;

    private Shipping shippingVo;


}