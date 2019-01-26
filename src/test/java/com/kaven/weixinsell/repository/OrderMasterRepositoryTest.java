package com.kaven.weixinsell.repository;

import com.kaven.weixinsell.dataobject.OrderMaster;
import com.kaven.weixinsell.enums.OrderStatusEnum;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void findByBuyerOpenid(){
        PageRequest pageRequest = new PageRequest(0,3);
        Page<OrderMaster> result = repository.findByBuyerOpenid("160930010",pageRequest);
        System.out.println(result.getTotalElements());
        Assert.assertNotEquals(0 , result.getTotalElements());
    }

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("湖南商学院萃雅607");
        orderMaster.setBuyerName("Kaven");
        orderMaster.setBuyerOpenid("160930010");
        orderMaster.setBuyerPhone("15576725340");
        orderMaster.setOrderAmount(new BigDecimal(108));
        orderMaster.setOrderId("1000010");

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

}