package com.study.mall.controller;

import com.study.mall.pojo.User;
import com.study.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yang
 * @date 2022/01/14 21:58
 **/
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @PostMapping("/register")
    public ResponseVo register(@RequestBody User user){
        log.info("username={}",user.getUsername());
        return ResponseVo.success("注册成功");
    }
}