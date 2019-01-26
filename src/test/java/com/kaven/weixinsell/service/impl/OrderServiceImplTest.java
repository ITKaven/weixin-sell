package com.kaven.weixinsell.service.impl;

import com.kaven.weixinsell.dataobject.OrderDetail;
import com.kaven.weixinsell.dto.OrderDTO;
import com.kaven.weixinsell.enums.OrderStatusEnum;
import com.kaven.weixinsell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String ORDER_ID = "1545964408121360701";
    private final String ORDER_OPENID = "160930010";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("湖南商学院");
        orderDTO.setBuyerName("Kaven");
        orderDTO.setBuyerOpenid(ORDER_OPENID);
        orderDTO.setBuyerPhone("15576725340");

        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail  o1 = new OrderDetail();
        o1.setProductQuantity(2);
        o1.setProductId("100000");

        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】 orderDTO={}",orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,10);
        Page<OrderDTO> result = orderService.findList(ORDER_OPENID , request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finished() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finished(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode() , result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}