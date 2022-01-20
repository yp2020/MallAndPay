package com.study.mall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.mall.dao.ProductMapper;
import com.study.mall.enums.ProductStatusEnum;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.pojo.Product;
import com.study.mall.service.ICategoryService;
import com.study.mall.service.IProductService;
import com.study.mall.vo.ProductDetailVo;
import com.study.mall.vo.ProductVo;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet=new HashSet<>();
        categoryService.findSubCategoryId(categoryId,categoryIdSet);
        //还要加上自身的 id
        if(categoryId!=null){
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);


        List<ProductVo> productVoList = products.stream().map(e -> {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(e, productVo);
            return productVo;
        }).collect(Collectors.toList());

        PageInfo pageInfo=new PageInfo(products);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        if(product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())||product.getStatus().equals(ProductStatusEnum.DELETE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVo productDetailVo=new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        // 敏感数据处理
        productDetailVo.setStock(product.getStock()>100? 100:product.getStock());

        return ResponseVo.success(productDetailVo);
    }
}