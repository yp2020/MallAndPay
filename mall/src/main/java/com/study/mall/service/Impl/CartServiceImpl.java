package com.study.mall.service.Impl;

import com.google.gson.Gson;
import com.study.mall.dao.ProductMapper;
import com.study.mall.enums.ProductStatusEnum;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.form.CartAddForm;
import com.study.mall.pojo.Cart;
import com.study.mall.pojo.Product;
import com.study.mall.service.ICartService;
import com.study.mall.vo.CartVo;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yang
 * @date 2022/01/21 10:15
 **/
@Service
public class CartServiceImpl implements ICartService {


    private final static String CART_REDIS_KEY_TEMPLATE="cart_%d";
    private Integer quantity=1;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;


    private Gson gson = new Gson();
    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm form) {
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        //1. 商品是否存在
        if(product==null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //2. 商品正常在售
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE)){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //3. 库存是否充足
        if(product.getStock()<=0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }


        //4. 写入到 redis
        // key:cart_ uid

        Cart cart = new Cart(product.getId(),
                quantity,
                form.getSelected());

        redisTemplate.opsForValue()
                .set(String.format(CART_REDIS_KEY_TEMPLATE,uid),
                gson.toJson(cart));

        return null;
    }
}