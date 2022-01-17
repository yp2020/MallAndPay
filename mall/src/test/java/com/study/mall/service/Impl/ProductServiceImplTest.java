package com.study.mall.service.Impl;

import com.study.mall.MallApplicationTests;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.service.IProductService;
import com.study.mall.vo.ProductVo;
import com.study.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVo<List<ProductVo>> responseVo = productService.list(null, 1, 10);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}