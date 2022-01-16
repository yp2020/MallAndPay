package com.study.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yang
 * @date 2022/01/16 19:37
 **/
@Data
public class CategoryVo {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;
    private List<CategoryVo> subCategories;

}