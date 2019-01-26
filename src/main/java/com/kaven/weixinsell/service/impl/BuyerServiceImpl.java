package com.kaven.weixinsell.service.impl;

import com.kaven.weixinsell.dto.OrderDTO;
import com.kaven.weixinsell.enums.ResultEnum;
import com.kaven.weixinsell.exception.SellException;
import com.kaven.weixinsell.service.BuyerService;
import com.kaven.weixinsell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String orderId, String openid) {
        return checkOrderOwner(orderId , openid ,"查询");
    }

    public OrderDTO calcelOrderOne(String orderId, String openid) {
        OrderDTO orderDTO = checkOrderOwner(orderId , openid ,"取消");
        if(orderDTO == null){
            log.error("【取消订单】订单不存在 orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        return orderService.cancel(orderDTO);
    }

    public OrderDTO checkOrderOwner(String orderId, String openid,String msg){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) return null;
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【{}订单】 该订单不属于当前用户 openid={} ，orderDTO={}",
                    msg,openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
