package com.study.mall.dao;

import com.study.mall.MallApplicationTests;
import com.study.mall.pojo.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CategoryMapperTest  extends MallApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;
    @Test
    public void findById() {
        Category byId = categoryMapper.findById(100001);
        System.out.println(byId.toString());

    }

    @Test
    public void queryById() {
        Category byId = categoryMapper.queryById(100001);
        System.out.println(byId.toString());
    }
}