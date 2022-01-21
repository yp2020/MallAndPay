package com.study.mall.controller;

import com.study.mall.form.CartAddForm;
import com.study.mall.vo.CartVo;
import com.study.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yang
 * @date 2022/01/21 09:54
 **/
@RestController
public class CartController {

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm){
        return null;
    }
}