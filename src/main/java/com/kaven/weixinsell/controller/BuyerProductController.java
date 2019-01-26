package com.kaven.weixinsell.controller;

import com.kaven.weixinsell.dataobject.ProductCategory;
import com.kaven.weixinsell.dataobject.ProductInfo;
import com.kaven.weixinsell.service.ProductCategoryService;
import com.kaven.weixinsell.service.ProductInfoService;
import com.kaven.weixinsell.utils.ResultVueObjectUtil;
import com.kaven.weixinsell.vueObject.ProductInfoVueObject;
import com.kaven.weixinsell.vueObject.ProductVueObject;
import com.kaven.weixinsell.vueObject.ResultVueObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVueObject list(){

        // 获得所有上架商品信息
         List<ProductInfo> productInfoList = productInfoService.findUpAll();

        // 进一步获得所有上架商品的类目编号集合
        List<Integer> categoryTypeList = productInfoList.stream().
                map(e->e.getCategoryType()).
                collect(Collectors.toList());

        // 获得所有商品的类目信息
        List<ProductCategory> productCategoryList = productCategoryService.
                findByCategoryTypeIn(categoryTypeList);

        List<ProductVueObject> productVueObjectList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductVueObject productVueObject = new ProductVueObject();
            BeanUtils.copyProperties(productCategory,productVueObject);

            List<ProductInfoVueObject> productInfoVueObjectList= new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productCategory.getCategoryType() != productInfo.getCategoryType()) continue;
                ProductInfoVueObject productInfoVueObject = new ProductInfoVueObject();
                BeanUtils.copyProperties(productInfo,productInfoVueObject);

                productInfoVueObjectList.add(productInfoVueObject);
            }

            productVueObject.setProductInfoVueObjectList(productInfoVueObjectList);
            productVueObjectList.add(productVueObject);
        }

        return ResultVueObjectUtil.success(productVueObjectList);
    }
}
