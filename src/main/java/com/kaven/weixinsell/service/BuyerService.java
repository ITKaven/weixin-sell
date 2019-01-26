package com.kaven.weixinsell.service;

import com.kaven.weixinsell.dto.OrderDTO;

public interface BuyerService {

    // 查询订单
    OrderDTO findOrderOne(String orderId , String openid);

    // 取消订单
    OrderDTO calcelOrderOne(String orderId , String openid);
}
