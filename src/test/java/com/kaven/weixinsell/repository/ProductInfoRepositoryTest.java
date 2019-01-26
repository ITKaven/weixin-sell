package com.kaven.weixinsell.repository;

import com.kaven.weixinsell.dataobject.ProductInfo;
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
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1000090");
        productInfo.setProductName("臭豆腐");
        productInfo.setProductPrice(new BigDecimal(10));
        productInfo.setProductDescription("非常好吃！");
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("www.kaven.top");
        productInfo.setProductStock(10050);
        productInfo.setCategoryType(10);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null , result);
    }

    @Test
    public void findByProductStatusTest(){
        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0 , productInfoList.size());
    }
}