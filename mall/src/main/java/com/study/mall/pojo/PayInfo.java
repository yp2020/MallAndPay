package com.study.mall.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yang
 * @date 2022/01/26 12:00
 **/

@Data
public class PayInfo {

    private Integer id;

    private Long orderNo;

    private Integer payPlatform;

    private String platformNumber;

    private String platformStatus;

    private BigDecimal payAmount;

    private Date createTime;

    private Date updateTime;


}