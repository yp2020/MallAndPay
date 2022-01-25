package com.study.mall.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 收货地址对应的 pojo 对象
 */
@Data
public class Shipping {


    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     *  收件人姓名
      */
    private String receiverName;

    /**
     *    收货固定电话
     */
    private String receiverPhone;

    /**
     * 收货移动电话
     */
    private String receiverMobile;

    /**
     * 省份
     */
    private String receiverProvince;

    /**
     * 城市
     */
    private String receiverCity;

    /**
     * 区/县
     */
    private String receiverDistrict;

    /**
     * 详细地址
     */
    private String receiverAddress;

    /**
     *邮编
     */
    private String receiverZip;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    private Date updateTime;

}