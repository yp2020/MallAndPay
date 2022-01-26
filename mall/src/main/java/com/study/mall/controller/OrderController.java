package com.study.mall.controller;

import com.github.pagehelper.PageInfo;
import com.study.mall.consts.MallConst;
import com.study.mall.form.OrderCreateForm;
import com.study.mall.pojo.User;
import com.study.mall.service.IOrderService;
import com.study.mall.vo.OrderVo;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author yang
 * @date 2022/01/26 10:00
 **/
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(HttpSession session,
                                      @Valid @RequestBody OrderCreateForm orderCreateForm){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.add(orderCreateForm.getShippingId(), user.getId());
    }

    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(HttpSession session,
                                     @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                     @RequestParam(required = false,defaultValue = "1")Integer pageSize){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);

        return orderService.list(user.getId(), pageNum,pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(HttpSession session,
                             @PathVariable long orderNo){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }


    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(HttpSession session,
                             @PathVariable long orderNo){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}