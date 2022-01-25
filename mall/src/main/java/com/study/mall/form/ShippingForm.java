package com.study.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 地址对应的表单对象 入参
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


    public ShippingForm(@NotBlank String receiverName, @NotBlank String receiverPhone, @NotBlank String receiverMobile, @NotBlank String receiverProvince, @NotBlank String receiverCity, @NotBlank String receiverDistrict, @NotBlank String receiverAddress, @NotBlank String receiverZip) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverMobile = receiverMobile;
        this.receiverProvince = receiverProvince;
        this.receiverCity = receiverCity;
        this.receiverDistrict = receiverDistrict;
        this.receiverAddress = receiverAddress;
        this.receiverZip = receiverZip;
    }
}
