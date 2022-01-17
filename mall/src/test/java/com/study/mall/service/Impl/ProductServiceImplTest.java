package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.service.IProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        productService.list(100002,1,10);
    }
}