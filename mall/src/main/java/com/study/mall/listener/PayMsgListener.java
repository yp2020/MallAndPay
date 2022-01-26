package com.study.mall.listener;

import com.google.gson.Gson;
import com.study.mall.pojo.PayInfo;
import com.study.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yang
 * @date 2022/01/26 11:59
 **/
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void process(String msg){
        log.info("********* 收到的消息为 ******");
        log.info(msg);
        log.info("********* 收到的消息为 ******");

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        log.info(payInfo.toString());
        if("SUCCESS".equals(payInfo.getPlatformStatus())){

            //支付状态为 成功，修改订单的状态为已经付款
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
