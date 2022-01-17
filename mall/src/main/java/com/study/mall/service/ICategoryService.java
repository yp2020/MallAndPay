package com.study.mall.service;

import com.study.mall.vo.CategoryVo;
import com.study.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id , Set<Integer> result);
}
