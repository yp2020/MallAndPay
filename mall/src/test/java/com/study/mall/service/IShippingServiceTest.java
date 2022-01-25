package com.study.mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.mall.MallApplicationTests;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.form.ShippingForm;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class IShippingServiceTest extends MallApplicationTests {
    @Autowired
    private IShippingService shippingService;

    Gson gson=new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        ShippingForm form=new ShippingForm(
                "路人甲",
                "1123344",
                "17623456673",
                "胡建省",
                "某某市",
                "某某县",
                "某某镇某某村某某组某某号",
                "326678");
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(1, form);
        log.info("responseVo={}",gson.toJson(responseVo));



    }

    @Test
    public void delete() {
        Integer uid=1;
        Integer shippingId=14;
        ResponseVo responseVo = shippingService.delete(uid, shippingId);
        log.info("responseVo={}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update() {
        Integer uid=1;
        Integer shippingId=15;
        ShippingForm form=new ShippingForm(
                "路人乙",
                "1123344",
                "17623456673",
                "福建省",
                "不知道是什么市",
                "某某县",
                "某某镇某某村某某组某某号",
                "326678");
        ResponseVo<Map<String, Integer>> responseVo = shippingService.update(uid,shippingId,form);

        log.info("responseVo={}",gson.toJson(responseVo));
        Assert.assertEquals(responseVo.getStatus(),ResponseEnum.SUCCESS.getCode());
    }

    @Test
    public void list() {
    }
}