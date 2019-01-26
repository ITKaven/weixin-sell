package com.kaven.weixinsell.repository;

import com.kaven.weixinsell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("100002");
        orderDetail.setOrderId("1000000");
        orderDetail.setProductIcon("www.choudoufu.top");
        orderDetail.setProductId("100012");
        orderDetail.setProductName("臭豆腐");
        orderDetail.setProductPrice(new BigDecimal(10));
        orderDetail.setProductQuantity(7);

        OrderDetail  result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId(){
        List<OrderDetail>  orderDetailList = repository.findByOrderId("1000000");

        Assert.assertNotEquals(0,orderDetailList.size());
    }
}