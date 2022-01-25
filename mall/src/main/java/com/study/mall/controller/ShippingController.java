package com.study.mall.controller;

import com.github.pagehelper.PageInfo;
import com.study.mall.consts.MallConst;
import com.study.mall.form.ShippingForm;
import com.study.mall.pojo.User;
import com.study.mall.service.IShippingService;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author yang
 * @date 2022/01/25 10:43
 **/

@RestController
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @PostMapping("/shippings")
    ResponseVo<Map<String, Integer>> add(HttpSession session,
                                         @Valid @RequestBody ShippingForm shippingForm) {

        User user =(User) session.getAttribute(MallConst.CURRENT_USER);

        return shippingService.add(user.getId(),shippingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    ResponseVo delete(HttpSession session,
                      @PathVariable Integer shippingId){

        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    ResponseVo update(HttpSession session,
                      @PathVariable  Integer shippingId,
                      @Valid @RequestBody ShippingForm shippingForm){

        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(),shippingId,shippingForm);
    }

    @GetMapping("/shippings")
    ResponseVo<PageInfo> list(HttpSession session,
                              @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                              @RequestParam(required = false,defaultValue = "1")Integer pageSize){

        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}