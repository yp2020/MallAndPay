package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.form.CartAddForm;
import com.study.mall.service.ICartService;
import com.study.mall.vo.CartVo;
import com.study.mall.vo.ResponseVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void add() {
        CartAddForm form=new CartAddForm();
        form.setProductId(27);
        form.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.add(1, form);
        //Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}