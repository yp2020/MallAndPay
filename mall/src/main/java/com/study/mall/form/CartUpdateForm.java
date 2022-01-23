package com.study.mall.form;

import lombok.Data;

/**
 * @author yang
 * @date 2022/01/23 11:09
 **/
@Data
public class CartUpdateForm {
    private  Integer quantity;
    //更改其选中的状态
    private Boolean selected;
}