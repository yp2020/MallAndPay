package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.service.ICategoryService;
import com.study.mall.vo.CategoryVo;
import com.study.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CategoryServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICategoryService categoryService;
    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
        Assert.assertEquals(responseVo.getStatus(), ResponseEnum.SUCCESS.getCode());
    }
}