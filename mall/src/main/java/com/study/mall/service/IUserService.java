package com.study.mall.service;

import com.study.mall.pojo.User;
import com.study.mall.vo.ResponseVo;

public interface IUserService {

    /* 注册*/
    ResponseVo register(User user );


    ResponseVo<User>login(String username,String password);
}
