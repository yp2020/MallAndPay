package com.study.mall.service;

import com.study.mall.vo.OrderVo;
import com.study.mall.vo.ResponseVo;

public interface IOrderService {
    public ResponseVo<OrderVo> add(int shippingId, int  uid);
}
