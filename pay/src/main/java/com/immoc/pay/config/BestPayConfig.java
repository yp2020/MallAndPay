package com.immoc.pay.config;


import com.lly835.bestpay.config.AliPayConfig;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 将 bestPayService 注入进组件 中
 */
@Component
public class BestPayConfig {

    @Autowired
    private WxAccountConfig wxAccountConfig;
    
    @Autowired
    private AlipayAccountConfig alipayAccountConfig;

     @Bean //希望项目启动的时候执行，加注解 @Bean
    public BestPayService bestPayService(WxPayConfig wxPayConfig){

         //支付宝相关的引入，不过没有支付宝商户，这里也就是试一试
         AliPayConfig aliPayConfig=new AliPayConfig();
         aliPayConfig.setAppId(alipayAccountConfig.getAppId());
         aliPayConfig.setPrivateKey(alipayAccountConfig.getPrivateKay());
         aliPayConfig.setAliPayPublicKey(alipayAccountConfig.getPublicKey());
         aliPayConfig.setNotifyUrl(alipayAccountConfig.getNotifyUrl());
         aliPayConfig.setReturnUrl(alipayAccountConfig.getReturnUrl());

//支付类, 所有方法都在这个类里
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        bestPayService.setAliPayConfig(aliPayConfig);

        return bestPayService;
    }


    @Bean
    public WxPayConfig wxPayConfig(){
        //微信支付配置
        WxPayConfig wxPayConfig = new WxPayConfig();

        //公众号Id native支付
        wxPayConfig.setAppId(wxAccountConfig.getAppId());

        //支付商户资料
        // 商户的 id
        wxPayConfig.setMchId(wxAccountConfig.getMchId());
        // 商户的密钥
        wxPayConfig.setMchKey(wxAccountConfig.getMchKey());
//异步回调地址
        wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());
//还要设置一个 returnURL,就是支付以后，跳转的地址
        wxPayConfig.setReturnUrl(wxAccountConfig.getReturnUrl());

        return wxPayConfig;
    }
}
