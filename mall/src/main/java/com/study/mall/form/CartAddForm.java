package com.study.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yang
 * @date 2022/01/21 09:52
 **/
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private  Integer quantity;

    private Boolean selected =true;
}