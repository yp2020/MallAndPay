package com.study.mall.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author yang
 * @date 2022/01/08 20:15
 **/

@Data
public class Category {
    private Integer id;
    private  Integer parentId;
    private  String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime ;
    private Date updateTime;
}