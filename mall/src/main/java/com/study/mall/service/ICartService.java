package com.study.mall.service;

import com.study.mall.form.CartAddForm;
import com.study.mall.form.CartUpdateForm;
import com.study.mall.vo.CartVo;
import com.study.mall.vo.ResponseVo;

public interface ICartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm form);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);


}
