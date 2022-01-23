package com.study.mall.controller;

import com.study.mall.consts.MallConst;
import com.study.mall.form.CartAddForm;
import com.study.mall.form.CartUpdateForm;
import com.study.mall.pojo.User;
import com.study.mall.service.ICartService;
import com.study.mall.vo.CartVo;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author yang
 * @date 2022/01/21 09:54
 **/
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;


    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session){
        User user= (User)session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(HttpSession session, @Valid @RequestBody CartAddForm cartAddForm){
        User user= (User)session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(), cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public  ResponseVo<CartVo> update(HttpSession session,
                                      @PathVariable Integer productId,
                                      @Valid @RequestBody CartUpdateForm cartUpdateForm){
        User user= (User)session.getAttribute(MallConst.CURRENT_USER);
        return  cartService.update(user.getId(),productId,cartUpdateForm);
    }

    @DeleteMapping("/carts/{productId}")
    public  ResponseVo<CartVo> delete(HttpSession session,
                                      @PathVariable Integer productId){
        User user= (User)session.getAttribute(MallConst.CURRENT_USER);
        return  cartService.delete(user.getId(),productId);
    }

    @PutMapping("/carts/selectAll")
    public  ResponseVo<CartVo> selectAll(HttpSession session){
        User user= (User)session.getAttribute(MallConst.CURRENT_USER);
        return  cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public  ResponseVo<CartVo> unSelectAll(HttpSession session){
        User user= (User)session.getAttribute(MallConst.CURRENT_USER);
        return  cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public  ResponseVo<Integer> sum(HttpSession session){
        User user= (User)session.getAttribute(MallConst.CURRENT_USER);
        return  cartService.sum(user.getId());
    }

}