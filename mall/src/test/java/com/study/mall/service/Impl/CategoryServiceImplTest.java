package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.service.ICategoryService;
import com.study.mall.vo.CategoryVo;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CategoryServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICategoryService categoryService;
    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
        Assert.assertEquals(responseVo.getStatus(), ResponseEnum.SUCCESS.getCode());
    }

    @Test
    public void findSubCategoryId(){
        Set<Integer> set=new HashSet<>();
        categoryService.findSubCategoryId(100001,set);
        log.info("set={} "+set);
    }
}