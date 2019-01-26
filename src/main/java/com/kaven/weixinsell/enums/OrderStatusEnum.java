package com.kaven.weixinsell.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    NEW(0,"新订单"),
    FINISHED(1,"已完结"),
    CANCEL(2,"已取消"),
    ;

    private String msg;

    private Integer code;

    OrderStatusEnum(Integer code , String msg){
        this.code = code;
        this.msg = msg;
    }

}
