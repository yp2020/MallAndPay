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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author yang
 * @date 2022/01/21 10:15
 **/
@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY_TEMPLATE="cart_%d";

    private Integer quantity=1;

    private Gson gson = new Gson();

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm form) {

        Product product = productMapper.selectByPrimaryKey(form.getProductId());

        //1. 商品是否存在
        if(product==null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }


        //2. 商品正常在售
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //3. 库存是否充足

        if(product.getStock()<=0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //4. 写入到 redis,也就是加入购物车
        // key:cart_ uid


        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();

        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value=opsForHash.get(redisKey,String.valueOf(product.getId()));

        Cart cart;

        if(StringUtils.isEmpty(value)){
            cart = new Cart(product.getId(), quantity, form.getSelected());
        }else{
             cart = gson.fromJson(value, Cart.class);
             cart.setQuantity(cart.getQuantity()+quantity);
        }

        opsForHash.put(
                redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart));
        
        return null;

    }
}