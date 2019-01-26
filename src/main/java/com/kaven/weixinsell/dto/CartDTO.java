package com.kaven.weixinsell.dto;

import lombok.Data;

@Data
public class CartDTO {

    // 商品数量
    private Integer productQuantity;

    // 商品名称
    private String productId;

    public CartDTO(Integer productQuantity, String productId) {
        this.productQuantity = productQuantity;
        this.productId = productId;
    }

    public CartDTO() {}
}
