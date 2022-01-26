package com.immoc.pay.service.impl;
import com.google.gson.Gson;
import com.immoc.pay.dao.PayInfoMapper;
import com.immoc.pay.enums.PayPlatformEnum;
import com.immoc.pay.pojo.PayInfo;
import com.immoc.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author asus
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    private final static String QUEUE_PAY_NOTIFY="payNotify";

    @Autowired
    private BestPayService bestPayService;
    
    @Autowired
    private PayInfoMapper payInfoMapper ;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 支付发起
     * @param orderId
     * @param amount
     */
    @Override
    public PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum bestPayTypeEnum) {

        //1.写入数据库
        PayInfo payInfo=new PayInfo(Long.parseLong(orderId),
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);

        payInfoMapper.insertSelective(payInfo);

        PayRequest request = new PayRequest();
        request.setOrderName("一个订单");
//        可以随便写
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(bestPayTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("发起支付的 response={}",response);
        return response;
    }

    /**
     * 异步通知处理
     * @param notifyData
     * 1. 签名校验 是否一致
     * 2，查询数据库中的数据 通过 orderNo 查询数据库中的记录是否存在 如果不存在，就说明发生了比较严重的问题，发异常，然后需要告警
     * 3. 判断 支付状态 已支付，那直接过去，
     * 4. 未支付， 判断 金额是否相等 ，用的是 BigDecimal 类型的 compareTo 方法
     * 5. 修改订单的 支付状态为成功支付
     * 6.告知支付平台已经支付成功 ，不需要再通知了。
     */
    @Override
    public String asyncNotify(String notifyData) {
        //1. 签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("异步通知 Response={}",payResponse);

        //2.金额校验  从数据库中查订单，进行校验
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(payResponse.getOrderId()));
        if(payInfo==null){
            throw new RuntimeException("通过 orderNo 查询的结果是 null ");// 正常情况下不会发生的严重情况，需要告警

        }
        //判断支付状态
        if(!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())){
            //订单状态不是 已支付 那么就要比较数据库中的金额 和异步回调中的金额
            if(payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount()))!=0){
                //==0 表示相等
                throw new RuntimeException("异步通知中的金额和数据库中的不一致，orderNo="+payResponse.getOrderId());
            }
            // 3. 修改订单的支付状态
            //将订单支付状态改为成功，然后写入数据库
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());

            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }


        // pay项目发送 MQ 消息， mall 接收 MQ 消息
        String msg=new Gson().toJson(payInfo);

        log.info("*********");
        log.info(msg);
        log.info("*********");

        amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY,msg);

        if(payResponse.getPayPlatformEnum()== BestPayPlatformEnum.WX){
            //4.收到微信支付结果通知后， 返回参数给微信支付,作用是使得微信不再发通知了.
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }else if(payResponse.getPayPlatformEnum()==BestPayPlatformEnum.ALIPAY){
            return "success";
        }
        throw new RuntimeException("异步通知中，支付平台错误");
    }

    @Override
    public PayInfo queryByOrderId(String orderId) {
        PayInfo res= payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
//        log.info("查询结果: "+res.toString());
        return res;
    }
}
