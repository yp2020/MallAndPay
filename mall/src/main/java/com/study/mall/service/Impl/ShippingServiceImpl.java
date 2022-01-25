package com.study.mall.service.Impl;

import com.github.pagehelper.PageInfo;
import com.study.mall.dao.ShippingMapper;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.form.ShippingForm;
import com.study.mall.pojo.Shipping;
import com.study.mall.service.IShippingService;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yang
 * @date 2022/01/25 09:16
 **/
@Service
public class ShippingServiceImpl implements IShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm shippingForm) {

        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);

        int insertSelective = shippingMapper.insertSelective(shipping);

        if(insertSelective<=0){
            //  增加地址失败
          return ResponseVo.error(ResponseEnum.ERROR);
        }

        Map<String, Integer> response=new HashMap<>();
        // 这里要配置主键回填
        response.put("shippingId",shipping.getId());
        return ResponseVo.success(response);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        int deleteByPrimaryKeyAndUid = shippingMapper.deleteByPrimaryKeyAndUid(shippingId, uid);
        if(deleteByPrimaryKeyAndUid<=0){
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL,"删除地址失败");
        }

        return ResponseVo.successByMsg("删除地址成功");
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm shippingForm) {
        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);

        int updateByPrimaryKeySelective = shippingMapper.updateByPrimaryKeySelective(shipping);

        if(updateByPrimaryKeySelective<=0){
            return ResponseVo.error(ResponseEnum.ERROR,"更新地址失败");
        }
        return ResponseVo.successByMsg("更新地址成功");
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        return null;
    }
}