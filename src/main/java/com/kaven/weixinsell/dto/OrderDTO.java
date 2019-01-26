package com.kaven.weixinsell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kaven.weixinsell.dataobject.OrderDetail;
import com.kaven.weixinsell.enums.OrderStatusEnum;
import com.kaven.weixinsell.enums.PayStatusEnum;
import com.kaven.weixinsell.utils.serializer.DateToLongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    // 订单 id
    private String orderId;

    // 买家名字
    private String buyerName;

    // 买家电话
    private String buyerPhone;

    // 买家地址
    private String buyerAddress;

    // 买家微信openid
    private String buyerOpenid;

    // 订单总金额
    private BigDecimal orderAmount;

    // 订单状态
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    // 支付状态
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    // 创建订单时间
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    // 更新订单时间
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;

    // 订单详情列表
    List<OrderDetail> orderDetailList;
}
