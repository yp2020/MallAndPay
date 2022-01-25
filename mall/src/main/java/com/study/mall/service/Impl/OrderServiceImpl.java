package com.study.mall.service.Impl;

import com.study.mall.dao.OrderItemMapper;
import com.study.mall.dao.OrderMapper;
import com.study.mall.dao.ProductMapper;
import com.study.mall.dao.ShippingMapper;
import com.study.mall.enums.OrderStatusEnum;
import com.study.mall.enums.PaymentTypeEnum;
import com.study.mall.enums.ProductStatusEnum;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.pojo.*;
import com.study.mall.service.IOrderService;
import com.study.mall.vo.OrderItemVo;
import com.study.mall.vo.OrderVo;
import com.study.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2022/01/25 20:49
 **/

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private CartServiceImpl cartServiceImpl;


    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public ResponseVo<OrderVo> add(int shippingId,int uid) {

        /**
         * 1. 查出收货地址并校验
         */
        Shipping shipping = shippingMapper.selectByPrimaryKeyAndUid(shippingId,uid);

        //校验地址是否存在
        if(shipping==null){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        /**
         * 2. 通过 uid 获取购物车
         *  校验是否有商品被选中，选中的商品有无库存
         */
        List<Cart> cartList = cartServiceImpl.listForCart(uid)
                .stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());

        /**
         * 校验购物车是否为空，也就是是否有商品被选中
         */
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }

        /**
         * 一次性提取出所有的 被选中的商品的 productId
         */
        Set<Integer> productIdSet = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toSet());

        /**
         * 根据所有的 productId 查出所有的商品，
         * 然后转换成一个 id:product 的 key-value 结构
         */
        Map<Integer, Product> idToProductMap = productMapper
                .selectByProductIdSet(productIdSet)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));


        long orderNo=generateOrderNo();
        List<OrderItem> orderItemList=new ArrayList<>();


        /**
         * 商品校验
         */
        for (Cart cart : cartList) {
            Product product = idToProductMap.get(cart.getProductId());

            /**
             * 校验商品是否存在
             */
            if(product==null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "该商品不存在,productId= "+cart.getProductId());
            }

            /**
             * 校验商品状态
             */
            if(!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
                        product.getName()+" 不是在售状态");
            }

            /**
             * 校验库存
             */
            if(product.getStock()<cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,
                        product.getName()+" 库存不正确");
            }

            OrderItem orderItem=buildOrderItem(uid,orderNo,cart.getQuantity(),product);

            orderItemList.add(orderItem);
            /**
             * 减去库存并更新到数据库
             */
            product.setStock(product.getStock()-cart.getQuantity());

            int updateByPrimaryKeySelective = productMapper.updateByPrimaryKeySelective(product);

            if(updateByPrimaryKeySelective<=0){
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }





        /**
         *  4. 生成订单 入库 Order 表 和 OrderItem 表，
         *  用事务来控制 两张表的数据都被插入或者都不插入
         */
                       //计算价格的任务在这里
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        int rowForOrder = orderMapper.insertSelective(order);
        if(rowForOrder<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        /**
         * 批量插入，避免循环内部连接数据库
         */
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);



        /**
         *
         * 6. 更新购物车，删除选中的商品
         */
        for (Cart cart : cartList) {
            cartServiceImpl.delete(uid,cart.getProductId());
        }

        OrderVo orderVo =buildOrderVo(order,orderItemList,shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 用时间戳解决这个 订单号
     * 企业级的话: 分布式唯一 id
     * @return
     */
    private Long generateOrderNo(){
        return System.currentTimeMillis()+new Random().nextInt(999);
    }


    private OrderItem buildOrderItem(Integer uid,
                                     Long orderNo,
                                     Integer quantity,
                                     Product product){
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item ;
    }
    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList){

        BigDecimal payment = orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order=new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }


    public OrderVo buildOrderVo(Order order,List<OrderItem>orderItemList,Shipping shipping){
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order,orderVo);

        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());

        orderVo.setOrderItemVoList(orderItemVoList);
        if(shipping!=null){
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }

        return orderVo;
    }
}