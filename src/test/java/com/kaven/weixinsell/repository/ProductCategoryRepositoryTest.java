package com.kaven.weixinsell.repository;

import com.kaven.weixinsell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    @Transactional
    /**
     * 和事务注解 @Transactional 有不同
     * 事务注解 @Transactional ： 执行方法出现异常，执行回滚
     * 这个 @Transactional ：执行回滚
     * */
    public void saveTest(){
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryName("打折");
//        productCategory.setCategoryType(6);
        ProductCategory productCategory = new ProductCategory("双11打折",10);
        ProductCategory result = repository.save(productCategory);
//        Assert.assertNotEquals(null,result);
        Assert.assertNotNull(result);
    }

    @Test
    @Transactional
    public void updateTest(){
        ProductCategory productCategory = repository.findOne(10);
        productCategory.setCategoryName("一折");
        productCategory.setCategoryType(12);
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}