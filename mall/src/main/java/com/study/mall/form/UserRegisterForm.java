package com.study.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yang
 * @date 2022/01/14 22:29
 **/
@Data
public class UserRegisterForm {

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

}