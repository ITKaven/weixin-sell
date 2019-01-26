package com.kaven.weixinsell.dataobject;

import com.kaven.weixinsell.enums.OrderStatusEnum;
import com.kaven.weixinsell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class OrderMaster {

    // 订单 id
    @Id
    private String orderId;

    // 买家名字
    private String buyerName;

    // 买家电话
    private String buyerPhone;

    // 买家地址
    private String buyerAddress;

    // 买家微信openid
    private String buyerOpenid;

    // 订单总金额
    private BigDecimal orderAmount;

    // 订单状态
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    // 支付状态
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    // 创建订单时间
    private Date createTime;

    // 更新订单时间
    private Date updateTime;

    public OrderMaster() {}
}
