package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.form.CartAddForm;
import com.study.mall.service.ICartService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CartServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void add() {
        CartAddForm form=new CartAddForm();
        form.setProductId(26);
        form.setSelected(true);
        cartService.add(1,form);
    }
}