package com.study.mall.service;

import com.study.mall.vo.CategoryVo;
import com.study.mall.vo.ResponseVo;

import java.util.List;

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();
}
