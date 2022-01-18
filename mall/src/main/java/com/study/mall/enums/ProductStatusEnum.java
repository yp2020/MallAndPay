package com.study.mall.enums;

import lombok.Getter;

/**
 * @author yang
 * @date 2022/01/18 09:49
 **/
@Getter
public enum ProductStatusEnum {
    ON_SALE(1,"在售"),
    OFF_SALE(2,"下架"),
    DELETE(3,"删除"),
    ;
    Integer code;
    String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}