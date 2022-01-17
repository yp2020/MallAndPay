package com.study.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yang
 * @date 2022/01/17 23:02
 **/
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private BigDecimal price;



}