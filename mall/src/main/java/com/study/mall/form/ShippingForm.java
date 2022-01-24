package com.study.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 地址对应的表单对象
 */
@Data
public class ShippingForm {


    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;
    //收货人邮编

}
