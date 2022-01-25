package com.study.mall.dao;

import com.study.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShippingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}