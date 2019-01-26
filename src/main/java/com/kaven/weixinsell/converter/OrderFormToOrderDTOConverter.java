package com.kaven.weixinsell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaven.weixinsell.dataobject.OrderDetail;
import com.kaven.weixinsell.dto.OrderDTO;
import com.kaven.weixinsell.enums.ResultEnum;
import com.kaven.weixinsell.exception.SellException;
import com.kaven.weixinsell.from.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFormToOrderDTOConverter {

    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems() ,
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch(Exception e){
            log.error("【对象转换】 Json参数错误 items={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
