package com.kaven.weixinsell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品信息类
 * */

@Entity
@DynamicUpdate
@Data
public class ProductInfo {

    // 商品 id
    @Id
    private String productId;

    // 商品名称
    private String productName;

    // 商品单价
    private BigDecimal productPrice;

    // 商品库存
    private Integer productStock;

    // 商品描述
    private String productDescription;

    // 商品小图片
    private String productIcon;

    // 商品状态 ，0 表示商品正常出售，1 表示商品下架
    private Integer productStatus;

    // 商品类目编号
    private Integer categoryType;

    public ProductInfo(){}
}
