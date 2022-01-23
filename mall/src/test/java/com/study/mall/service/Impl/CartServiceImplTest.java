package com.study.mall.service.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.mall.MallApplicationTests;
import com.study.mall.form.CartAddForm;
import com.study.mall.form.CartUpdateForm;
import com.study.mall.service.ICartService;
import com.study.mall.vo.CartVo;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CartServiceImplTest extends MallApplicationTests {

    Gson gson=new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    private ICartService cartService;


    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list={}",gson.toJson(list));
    }

    @Test
    public void add() {
        CartAddForm form=new CartAddForm();
        form.setProductId(27);
        form.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.add(1, form);
        log.info("responseVo={}",gson.toJson(responseVo));
        //Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update(){
        CartUpdateForm cartUpdateForm=new CartUpdateForm();
        cartUpdateForm.setQuantity(7);
        cartUpdateForm.setSelected(false);
        ResponseVo<CartVo> responseVo = cartService.update(1, 26, cartUpdateForm);

        log.info("responseVo={}",gson.toJson(responseVo));
    }


    @Test
    public void delete(){
        ResponseVo<CartVo> responseVo = cartService.delete(1, 29);
        log.info("responseVo={}",gson.toJson(responseVo));
    }
}