package com.study.mall.service.Impl;

import com.github.pagehelper.PageInfo;
import com.study.mall.MallApplicationTests;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.service.IProductService;
import com.study.mall.vo.ProductDetailVo;
import com.study.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = productService.list(null, 2, 3);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public  void detail(){
        ResponseVo<ProductDetailVo> detail = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),detail.getStatus());
    }
}