package com.study.mall.dao;

import com.study.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShippingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    List<Shipping> selectByUid(Integer uid);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);



    int deleteByPrimaryKeyAndUid(@Param("shippingId") Integer shippingId,
                                 @Param("uid") Integer uid);
}