package com.study.mall.service.Impl;

import com.google.gson.Gson;
import com.study.mall.dao.ProductMapper;
import com.study.mall.enums.ProductStatusEnum;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.form.CartAddForm;
import com.study.mall.form.CartUpdateForm;
import com.study.mall.pojo.Cart;
import com.study.mall.pojo.Product;
import com.study.mall.service.ICartService;
import com.study.mall.vo.CartProductVo;
import com.study.mall.vo.CartVo;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public ResponseVo<CartVo> list(Integer uid) {

        //返回 对象的属性设置
        Boolean selectAll=true;
        Integer cartTotalQuantity=0;
        BigDecimal cartTotalPrice=BigDecimal.ZERO;

        CartVo cartVo=new CartVo();
        List<CartProductVo> cartProductVoList=new ArrayList<>();


        // 存入 redis 设置
        HashOperations<String, String,String > opsForHash = stringRedisTemplate.opsForHash();

        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //todo  不要在 For 循环中去查数据库，想想怎么去优化
            Product product = productMapper.selectByPrimaryKey(productId);
            if(product!=null){
                CartProductVo cartProductVo=new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                cartProductVoList.add(cartProductVo);
                if(!cart.getProductSelected()){
                    selectAll=false;
                }
                //总价的计算 只会计算选中的
                if(cart.getProductSelected()){
                    cartTotalPrice= cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }

            cartTotalQuantity+=cart.getQuantity();
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {

        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();

        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value=opsForHash.get(redisKey,String.valueOf(productId));

        Cart cart;

        if(StringUtils.isEmpty(value)){
            //购物车里不存在这个商品，报错
            return  ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }


        //购物车中存在该商品，那么就要进行状态的修改
        cart = gson.fromJson(value, Cart.class);
        //修改具体内容
        if(cartUpdateForm.getQuantity()!=null&& cartUpdateForm.getQuantity()>=0){
            cart.setQuantity(cartUpdateForm.getQuantity());
        }

        if(cartUpdateForm.getSelected()!=null){
            cart.setProductSelected(cartUpdateForm.getSelected());
        }

        opsForHash.put(redisKey,String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {

        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();

        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value=opsForHash.get(redisKey,String.valueOf(productId));

        Cart cart;

        if(StringUtils.isEmpty(value)){
            //购物车里不存在这个商品，报错
            return  ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        opsForHash.delete(redisKey,String.valueOf(productId));
        return list(uid);
    }


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

        return list(uid);

    }
}