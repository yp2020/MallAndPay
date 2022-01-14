package com.study.mall.enums;

import lombok.Getter;

/**
 * @author yang
 * @date 2022/01/14 22:21
 **/
@Getter
public enum ResponseEnum {

    ERROR(-1,"服务端错误"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2,"用户名字已存在"),

    PARAM_ERROR(3,"参数错误"),
    EMAIL_EXIST(4,"email 已存在"),
    NEED_LOGIN(10,"用户未登录,请先登录"),
                    ;
    Integer code;
    String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}