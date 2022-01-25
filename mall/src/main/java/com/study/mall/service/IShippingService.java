package com.study.mall.service;

import com.github.pagehelper.PageInfo;
import com.study.mall.form.ShippingForm;
import com.study.mall.vo.ResponseVo;

import java.util.Map;

/**
 * 收货地址相关接口
 *
 */
public interface IShippingService {
    /**
     *   入参需要加一个 用户id 也就是 uid
     * @param uid
     * @param shippingForm
     * @return
     */

    ResponseVo<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm);

    ResponseVo delete(Integer uid, Integer shippingId);

    ResponseVo update(Integer uid,Integer shippingId,ShippingForm shippingForm);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
