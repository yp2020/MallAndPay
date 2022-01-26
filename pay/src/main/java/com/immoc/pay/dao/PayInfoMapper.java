package com.immoc.pay.dao;

import com.immoc.pay.pojo.PayInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author asus
 */
@Mapper
public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);


    PayInfo selectByOrderNo(Long orderNo);    //通过订单号来查询订单

}
