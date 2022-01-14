package com.study.mall.controller;

import com.study.mall.enums.ResponseEnum;
import com.study.mall.enums.RoleEnum;
import com.study.mall.form.UserForm;
import com.study.mall.pojo.User;
import com.study.mall.service.IUserService;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yang
 * @date 2022/01/14 21:58
 **/
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    public IUserService userService;

    @PostMapping("/register")
    public ResponseVo register(@Valid  @RequestBody UserForm userForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("注册参数有误: {} {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        User user=new User();
        BeanUtils.copyProperties(userForm,user);
        user.setRole(RoleEnum.CUSTOMER.getCode());
         return userService.register(user);
    }
}