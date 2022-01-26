package com.study.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yang
 * @date 2022/01/26 10:10
 **/
@Data
public class OrderCreateForm {
    @NotNull
    private  Integer shippingId;
}