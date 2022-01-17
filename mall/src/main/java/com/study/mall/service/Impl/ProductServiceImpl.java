package com.study.mall.service.Impl;

import com.study.mall.dao.ProductMapper;
import com.study.mall.pojo.Product;
import com.study.mall.service.ICategoryService;
import com.study.mall.service.IProductService;
import com.study.mall.vo.ProductVo;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yang
 * @date 2022/01/17 23:06
 **/
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<List<ProductVo>> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet=new HashSet<>();
        categoryService.findSubCategoryId(categoryId,categoryIdSet);
        //还要加上自身的 id
        categoryIdSet.add(categoryId);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        log.info("product={}",products);
        return null ;
    }
}