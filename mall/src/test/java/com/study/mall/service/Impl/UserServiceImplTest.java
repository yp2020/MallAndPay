package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.enums.RoleEnum;
import com.study.mall.pojo.User;
import com.study.mall.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
}