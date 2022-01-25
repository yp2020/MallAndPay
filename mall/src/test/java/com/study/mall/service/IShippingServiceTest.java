package com.study.mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.mall.MallApplicationTests;
import com.study.mall.form.ShippingForm;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
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
    }

    @Test
    public void update() {
    }

    @Test
    public void list() {
    }
}