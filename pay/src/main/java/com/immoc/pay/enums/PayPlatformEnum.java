package com.immoc.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import javafx.application.Platform;
import lombok.Getter;

@Getter
public enum PayPlatformEnum {
    //1-支付宝，2 微信
    ALIPAY(1),

    WX(2),
    ;
    Integer code;

    PayPlatformEnum(Integer code) {
        this.code = code;
    }

    public static  PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum){

        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            //传过来的支付平台 与我这里设置的支付平台相等，那么就是可以返回。
            if(bestPayTypeEnum.getPlatform().name().equals(payPlatformEnum.name())){
                return payPlatformEnum;
            }
        }

        throw new RuntimeException("支付平台错误: "+ bestPayTypeEnum.name());
    }
}
