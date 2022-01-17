package com.study.mall.service;

import com.study.mall.vo.ProductVo;
import com.study.mall.vo.ResponseVo;

import java.util.List;

public interface IProductService {

    ResponseVo<List<ProductVo>> list(Integer categoryId,Integer pageNum,Integer pageSize);

}
