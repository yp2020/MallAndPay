package com.study.mall.service;

import com.github.pagehelper.PageInfo;
import com.study.mall.vo.ResponseVo;

public interface IProductService {

    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

}
