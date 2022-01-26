package com.immoc.pay.service.impl;

import com.google.gson.Gson;
import com.immoc.pay.PayApplicationTests;
import com.immoc.pay.pojo.PayInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Slf4j
public class PayServiceImplTest extends PayApplicationTests {

    @Autowired
    private PayServiceImpl payServiceImpl;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void create() {
//        payService.create("1234444444", BigDecimal.valueOf(0.01));

    }


    @Test
    public void sendMQMsg(){

        PayInfo payInfo=new PayInfo(10010l, 1,  "PAID", BigDecimal.valueOf(0.01));
        String msg=new Gson().toJson(payInfo);
        log.info("*********");
        log.info(msg);
        log.info("*********");

//        第一个参数为 队列的名字， 第二个是 具体的内容
        amqpTemplate.convertAndSend("payNotify",msg);
    }

}