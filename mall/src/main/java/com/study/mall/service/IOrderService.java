package com.study.mall.service;

import com.github.pagehelper.PageInfo;
import com.study.mall.vo.OrderVo;
import com.study.mall.vo.ResponseVo;

public interface IOrderService {
    /**
     * 谁创建订单，然后就是一个人可以有多个地址，所以这个地址也要传进来
      */
    ResponseVo<OrderVo> add(int shippingId, int  uid);

    /**
     *     订单列表
     */

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);


    /**
     *  uid 的作用是 保证 只能查自己的
     * @param uid
     * @param orderVo
     * @return
     */
    ResponseVo<OrderVo> detail(Integer uid,Long orderVo);

    /**
     *
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo cancel(Integer uid,Long orderNo);



}
