package com.kaven.weixinsell.service.impl;

import com.kaven.weixinsell.dataobject.ProductInfo;
import com.kaven.weixinsell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() {
        ProductInfo result = productInfoService.findOne("123456");
        Assert.assertEquals("123456",result.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0 ,3);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("100012");
        productInfo.setProductName("臭豆腐");
        productInfo.setProductPrice(new BigDecimal(10));
        productInfo.setProductDescription("味道很好，我很喜欢吃！");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductIcon("www.choudoufu.top");
        productInfo.setProductStock(1000);
        productInfo.setCategoryType(5);

        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }
}