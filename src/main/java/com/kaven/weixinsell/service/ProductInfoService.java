package com.kaven.weixinsell.service;

import com.kaven.weixinsell.dataobject.ProductInfo;
import com.kaven.weixinsell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    // 搜索指定 id 的商品信息
    ProductInfo findOne(String productInfoId);

    // 用户搜索所有正常出售的商品信息
    List<ProductInfo> findUpAll();

    // 卖家搜索所有商品信息，包括在架或者不在架的商品信息
    Page<ProductInfo> findAll(Pageable pageable);

    // 添加商品信息、更新商品信息
    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
