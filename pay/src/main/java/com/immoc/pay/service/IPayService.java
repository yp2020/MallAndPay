package com.immoc.pay.service;

import com.immoc.pay.pojo.PayInfo;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @author asus
 */
public interface IPayService {

    /**
     * 创建支付
     */
    PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum bestPayTypeEnum);


    /**
     * 异步通知处理
     * @param notifyData
     */
    String asyncNotify(String notifyData);

    /**
     * 通过订单号来 查询支付记录
     * @param orderId
     * @return
     */
    PayInfo queryByOrderId(String orderId);
}
