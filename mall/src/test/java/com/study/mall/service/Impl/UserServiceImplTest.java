package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.enums.RoleEnum;
import com.study.mall.pojo.User;
import com.study.mall.service.IUserService;
import com.study.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImplTest  extends MallApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        User user = new User();
        user.setUsername("jack");
        user.setPassword("123456");
        user.setEmail("jack@qq.com");
        user.setRole(RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }

    @Test
    public void login() {
        register();
        ResponseVo<User> responseVo = userService.login("jack", "123456");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}