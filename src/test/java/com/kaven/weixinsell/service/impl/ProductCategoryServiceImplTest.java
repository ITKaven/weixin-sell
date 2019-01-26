package com.kaven.weixinsell.service.impl;

import com.kaven.weixinsell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest{

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertEquals( new Integer(1) ,  productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> categoryIdList = Arrays.asList(1,3,4,6,10,11,12);
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryIdList);
        Assert.assertNotEquals(0 , productCategoryList.size());
    }

    @Test
    public void save() {
//        ProductCategory productCategory = new ProductCategory("国庆节优惠",19);
//        ProductCategory result = productCategoryService.save(productCategory);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(7);
        productCategory.setCategoryName("kaven最爱");
        productCategory.setCategoryType(5);
        ProductCategory result = productCategoryService.save(productCategory);
        Assert.assertNotEquals(null, result);
    }
}