package com.study.mall.enums;

import lombok.Getter;

/**
 * @author yang
 * @date 2022/01/14 21:42
 **/
@Getter
public enum RoleEnum {
    //用户角色类枚举，0- 管理员， 1-普通用户
    ADMIN(0),
    CUSTOMER(1),
            ;
    Integer code;

     RoleEnum(Integer code) {
        this.code = code;
    }
}