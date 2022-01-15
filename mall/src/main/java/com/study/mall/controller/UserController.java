package com.study.mall.controller;

import com.study.mall.consts.MallConst;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.enums.RoleEnum;
import com.study.mall.form.UserLoginForm;
import com.study.mall.form.UserRegisterForm;
import com.study.mall.pojo.User;
import com.study.mall.service.IUserService;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author yang
 * @date 2022/01/14 21:58
 **/
@Slf4j
@RequestMapping()
@RestController
public class UserController {

    @Autowired
    public IUserService userService;

    @PostMapping("/user/register")
    public ResponseVo register(@Valid  @RequestBody UserRegisterForm userRegisterForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("注册参数有误: {} {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        User user=new User();
        BeanUtils.copyProperties(userRegisterForm,user);
        user.setRole(RoleEnum.CUSTOMER.getCode());
         return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User>login(@Valid @RequestBody UserLoginForm userLoginForm,
                                 BindingResult bindingResult,
                                 HttpSession session){
        if(bindingResult.hasErrors()){
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //设置 session
        session.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());
        return userResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo<User> userinfo(HttpSession session){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);

        return ResponseVo.success(user);
    }

    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){

        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }



}