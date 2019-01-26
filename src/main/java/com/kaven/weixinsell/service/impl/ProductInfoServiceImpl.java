package com.kaven.weixinsell.service.impl;

import com.kaven.weixinsell.dataobject.ProductInfo;
import com.kaven.weixinsell.dto.CartDTO;
import com.kaven.weixinsell.enums.ProductStatusEnum;
import com.kaven.weixinsell.enums.ResultEnum;
import com.kaven.weixinsell.exception.SellException;
import com.kaven.weixinsell.repository.ProductInfoRepository;
import com.kaven.weixinsell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {


    @Autowired
    ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productInfoId) {
        return repository.findOne(productInfoId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){

            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());

            if(productInfo == null) throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

            Integer newStock = productInfo.getProductStock()+cartDTO.getProductQuantity();

            productInfo.setProductStock(newStock);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){

            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());

            if(productInfo == null) throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

            Integer newStock = productInfo.getProductStock()- cartDTO.getProductQuantity();

            if(newStock < 0 ) throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);

            productInfo.setProductStock(newStock);
            repository.save(productInfo);
        }
    }
}
